package fb.pricingAnalytics.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import fb.pricingAnalytics.dao.PricingRuleDAO;
import fb.pricingAnalytics.model.Operator;
import fb.pricingAnalytics.model.ScenarioMenuPricingRule;
import fb.pricingAnalytics.model.ScenarioPricingRule;
import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.model.vo.PricingRuleData;
import fb.pricingAnalytics.model.vo.PricingRuleVo;
import fb.pricingAnalytics.model.vo.StoreTierVo;
import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.MenuItem;
import fb.pricingAnalytics.request.PricingRuleRequest;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.StoreTier;
import fb.pricingAnalytics.request.UpdateStoreInfoRequest;
import fb.pricingAnalytics.response.ApplyRulesStatusResponse;
import fb.pricingAnalytics.response.MenuPricingResponse;
import fb.pricingAnalytics.response.PricingRulesListResponse;
import fb.pricingAnalytics.response.StoreTierResponse;
import fb.pricingAnalytics.utils.FBConstants;
import fb.pricingAnalytics.utils.FBRestResponse;

@Repository
public class PricingRuleDAOImpl implements PricingRuleDAO {

	private final static Logger logger = LoggerFactory.getLogger(PricingRuleDAOImpl.class);

	public final static String UPDATE_IST_PRODUCT_TIER_PRICE_FROM_MENU_RULE_QUERY = "UPDATE ISTProductTierInfo as IST SET IST.price =:price, IST.isChanged=:isChanged,IST.isEditable=:isEditable,AssociateRuleId=:associateRuleId,ChangeType=:changeType,IST.updatedOn =:lastUpdated_date, IST.updatedBy =:lastUpdated_by WHERE IST.projectId=:project_Id and IST.scenarioId=:scenario_Id and IST.brandId=:brand_Id and IST.productId =:product_id AND IST.tier =:tier";
	 public final static String UPDATE_IST_PRODUCT_TIER_PRICE_FROM_MENU_RULE_ID_QUERY = "UPDATE ISTProductTierInfo as IST SET AssociateRuleId=:associateRuleId,IST.updatedOn =:lastUpdated_date, IST.updatedBy =:lastUpdated_by WHERE IST.projectId=:project_Id and IST.scenarioId=:scenario_Id and IST.brandId=:brand_Id and IST.productId =:product_id AND IST.tier =:tier";

	public final static String UPDATE_IST_PRODUCT_TIER_PRICE_FROM_PRICING_RULE_QUERY = "UPDATE ISTProductTierInfo as IST SET IST.price =:price, IST.isChanged=:isChanged,AssociateRuleId=:associateRuleId,ChangeType=:changeType,IST.updatedOn =:lastUpdated_date, IST.updatedBy =:lastUpdated_by WHERE IST.projectId=:project_Id and IST.scenarioId=:scenario_Id and IST.brandId=:brand_Id and IST.productId =:product_id AND IST.tier =:tier and  IST.isEditable=true";

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	@Override
	public List<ApplyRulesStatusResponse> applymenuRules(int brandId, List<ApplyRuleRequest> rulesApplicable,
			String userName) throws SQLException, Exception {

		List<ApplyRulesStatusResponse> applyRulesResponseList = new ArrayList<>();

		for (ApplyRuleRequest ruleRequest : rulesApplicable) {
			ScenarioMenuPricingRule menuRule = fetchMenuRule(brandId, ruleRequest.getRuleId());
			ApplyRulesStatusResponse response = applyOrRevertMenuRule(ruleRequest, menuRule, brandId, userName);
			applyRulesResponseList.add(response);
		}

		return applyRulesResponseList;
	}

	private ApplyRulesStatusResponse applyOrRevertMenuRule(ApplyRuleRequest ruleRequest,
			ScenarioMenuPricingRule pricingRule, int brandId, String userName) throws SQLException, Exception {

		ApplyRulesStatusResponse response = null;
		MenuPricingResponse responseDecisiveRuleDataList = new MenuPricingResponse();
		MenuItem decisiveRuleData = new ObjectMapper().readValue(pricingRule.getDecisiveMenuRuleData(), MenuItem.class);
		logger.info("decisiveRuleData  " + decisiveRuleData);
		MenuItem dependentRuleData = new ObjectMapper().readValue(pricingRule.getDependentMenuRuleData(),
				MenuItem.class);
		logger.info("dependentRuleData  " + dependentRuleData);
		MenuPricingResponse responseDependentRuleDataList = getMenuItemRecordsForRule(dependentRuleData, ruleRequest,
				brandId);
		if ((pricingRule.getOperator() > 1)
				|| ((pricingRule.getOperator() == 1) && (pricingRule.getPriceChange() == 0))) {
			responseDecisiveRuleDataList = getMenuItemRecordsForRule(decisiveRuleData, ruleRequest, brandId);
		}

		if ((responseDependentRuleDataList.getCount() == 0)
				|| ((responseDecisiveRuleDataList.getCount() == 0) && ((pricingRule.getOperator() > 1)
						|| ((pricingRule.getOperator() == 1) && (pricingRule.getPriceChange() == 0))))) {
			logger.info("responseList count is 0  ");
			response = new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
					"There are no records present for the associated rule criteria");
		} else {
			logger.info("updating rulescenariopricing table");
			response = updateMenuRuleMenuTierPrice(ruleRequest, brandId, responseDependentRuleDataList,
					responseDecisiveRuleDataList, pricingRule, userName);
			logger.info("update rulescenariopricing table");
			if (response.isRuleApplied()) {
				updateMenuRuleScenarioPricing(ruleRequest, brandId);
			}
		}

		return response;
	}

	private ApplyRulesStatusResponse applyOrRevertRule(ApplyRuleRequest ruleRequest, ScenarioPricingRule pricingRule,
			int brandId, String userName) {

		ApplyRulesStatusResponse response = null;
		try {
			String ruleType = pricingRule.getRuleType();
			logger.info("ruleType  " + ruleType);
			if (ruleType.equalsIgnoreCase("menuitem")) {
				PricingRuleData ruleData = new ObjectMapper().readValue(pricingRule.getRuleData(),
						PricingRuleData.class);
				logger.info("ruleData  " + ruleData);
				MenuPricingResponse responseList = getMenuItemRecordsForRule(ruleData.getMenuItem(), ruleRequest,
						brandId);
				if (responseList.getCount() == 0) {
					logger.info("responseList count is 0  ");
					response = new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
							"There are no records present for the associated rule criteria");
				} else {
					logger.info("updating rulescenariopricing table");
					response = updateMenuTierPrice(ruleRequest, brandId, responseList, pricingRule, userName);
					logger.info("update rulescenariopricing table");
					if (response.isRuleApplied()) {
						updateScenarioPricing(ruleRequest, brandId);
					}
				}
			} else {
				PricingRuleData ruleData = new ObjectMapper().readValue(pricingRule.getRuleData(),
						PricingRuleData.class);
				StoreTierResponse responseList = getStoreTierRecordsForRule(ruleData.getStoreTier(), ruleRequest,
						brandId);

				if (responseList.getCount() == 0) {
					response = new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
							"There are no records present for the associated rule type criteria");
				} else {
					response = updateStoreTier(ruleRequest, brandId, responseList, pricingRule, userName);
					logger.info("update rulescenariopricing table");
					if (response.isRuleApplied()) {
						updateScenarioPricing(ruleRequest, brandId);
					}
				}
			}
		} catch (Exception ex) {
			response = new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
					"Exception occured while processing the Rule");
		}
		return response;
	}

	@Override
	public FBRestResponse applyPricingRules(int brandId, List<ApplyRuleRequest> applyRules)
			throws SQLException, Exception {

		for (ApplyRuleRequest applyRuleRequest : applyRules) {

			try {
				StringBuilder sb = new StringBuilder(
						"UPDATE ScenarioPricingRule SET IsApplied=:is_Applied where BrandId=:brand_Id and RuleId=:rule_Id and ScenarioId=:scenario_Id");
				Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
				query.setParameter("brand_Id", brandId);
				query.setParameter("scenario_Id", applyRuleRequest.getScenarioId());
				query.setParameter("is_Applied", applyRuleRequest.isApplied());
				query.setParameter("rule_Id", applyRuleRequest.getRuleId());
				query.executeUpdate();
			} catch (Exception ex) {
				logger.info("Updation failed");
				return new FBRestResponse(false, "Updation Failed");
			}

		}
		return new FBRestResponse(true, "success");
	}

	@Override
	public List<ApplyRulesStatusResponse> applyRules(int brandId, List<ApplyRuleRequest> rulesApplicable,
			String userName) throws SQLException, Exception {

		List<ApplyRulesStatusResponse> applyRulesResponseList = new ArrayList<>();

		for (ApplyRuleRequest ruleRequest : rulesApplicable) {
			ScenarioPricingRule pricingRule = fetchPricingRule(brandId, ruleRequest.getRuleId());
			ApplyRulesStatusResponse response = applyOrRevertRule(ruleRequest, pricingRule, brandId, userName);
			applyRulesResponseList.add(response);
		}

		return applyRulesResponseList;
	}

	@Override
	public BigInteger createPricingRule(PricingRuleRequest pricingRuleRequest, int brandId, String userName)
			throws SQLException, Exception {

		BigInteger ruleId = new BigInteger("0");

		try {
			ScenarioPricingRule pricingRule = new ScenarioPricingRule();

			Session session = entityManager.unwrap(Session.class);
			DecimalFormat df = new DecimalFormat("0.00");
			pricingRule.setScenarioId(pricingRuleRequest.getScenarioId());
			pricingRule.setBrandId(brandId);
			pricingRule.setApplied(false);
			pricingRule.setDeleted(false);
			pricingRule.setCreatedOn(Date.from(Instant.now()));
			pricingRule.setCreatedBy(userName);
			pricingRule.setRuleType(pricingRuleRequest.getType());
			pricingRule.setTierUpdate(pricingRuleRequest.getTierUpdate());
			pricingRule.setPriceChange(null);
			pricingRule.setRuleName(pricingRuleRequest.getRuleName());
			pricingRule.setPriceChangeByPercentage(pricingRuleRequest.getPriceChangeByPercentage());
			pricingRule.setPriceBarrierOption(pricingRuleRequest.getPriceBarrierOption());
			if (null != pricingRuleRequest.getPriceChange()) {
				pricingRule.setPriceChange(pricingRuleRequest.getPriceChange());
			}
			pricingRule.setRuleData(new ObjectMapper().writeValueAsString(pricingRuleRequest));

			ruleId = (BigInteger) session.save(pricingRule);

		} catch (Exception ex) {
			logger.info("Exception occured while saving Rule data into DB");
			return ruleId;
		}
		return ruleId;
	}

	@Override
	public BigInteger createScenarioMenuPricingRule(ScenarioMenuPricingRule scenarioMenuPricingRuleObject, int brandId,
			String userName) throws SQLException, Exception {
		entityManager.persist(scenarioMenuPricingRuleObject);
		return scenarioMenuPricingRuleObject.getRuleId();
	}

	@Override
	public ApplyRulesStatusResponse deleteMenuRule(int brandId, ApplyRuleRequest deleteRule, String userName)
			throws SQLException, Exception {
		ArrayList<ApplyRuleRequest> applyRequestList = new ArrayList<>();
		applyRequestList.add(deleteRule);
		List<ApplyRulesStatusResponse> revertRulesResponseList = revertMenuRules(brandId, applyRequestList, userName);
		javax.persistence.Query typeUpdateDeeleteQuery = entityManager.createNamedQuery("UpdateDeleteQuery");
		typeUpdateDeeleteQuery.setParameter("brand_Id", brandId);
		typeUpdateDeeleteQuery.setParameter("scenario_Id", deleteRule.getScenarioId());
		typeUpdateDeeleteQuery.setParameter("is_Deleted", deleteRule.isDeleted());
		typeUpdateDeeleteQuery.setParameter("rule_Id", deleteRule.getRuleId());
		int isUpdated = typeUpdateDeeleteQuery.executeUpdate();
		if (isUpdated == 0) {

			return new ApplyRulesStatusResponse(deleteRule.getRuleId(), revertRulesResponseList.get(0).getRuleName(),
					false, "Exception occured while deleting rule ");
		} else {
			return new ApplyRulesStatusResponse(deleteRule.getRuleId(), revertRulesResponseList.get(0).getRuleName(),
					true, "Rule deleted successfully");
		}

	}

	@Override
	public List<ApplyRulesStatusResponse> deleteRules(int brandId, List<ApplyRuleRequest> deleteRules,
			String userName) {

		List<ApplyRulesStatusResponse> applyRulesResponseList = new ArrayList<>();

		try {
			applyRulesResponseList = revertRules(brandId, deleteRules, userName);
		} catch (Exception e) {
			logger.info("Excption occured while reverting rule ");
			applyRulesResponseList.add(new ApplyRulesStatusResponse(deleteRules.get(0).getRuleId(),
					applyRulesResponseList.get(0).getRuleName(), false, "Exception occured while deleting rule "));
			return applyRulesResponseList;
		}

		ApplyRuleRequest applyRuleRequest = deleteRules.get(0);
		try {
			StringBuilder sb = new StringBuilder(
					"UPDATE ScenarioPricingRule SET IsDeleted=:is_Deleted where BrandId=:brand_Id and RuleId=:rule_Id and ScenarioId=:scenario_Id");
			Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
			query.setParameter("brand_Id", brandId);
			query.setParameter("scenario_Id", applyRuleRequest.getScenarioId());
			query.setParameter("is_Deleted", applyRuleRequest.isDeleted());
			query.setParameter("rule_Id", applyRuleRequest.getRuleId());
			query.executeUpdate();
		} catch (Exception ex) {
			applyRulesResponseList.add(new ApplyRulesStatusResponse(deleteRules.get(0).getRuleId(),
					applyRulesResponseList.get(0).getRuleName(), false, "Exception occured while deleting rule "));
			return applyRulesResponseList;
		}

		List<ApplyRulesStatusResponse> applyRulesResponseSuccessList = new ArrayList<>();
		applyRulesResponseSuccessList.add(new ApplyRulesStatusResponse(deleteRules.get(0).getRuleId(),
				applyRulesResponseList.get(0).getRuleName(), true, "Rule deleted successfully"));
		return applyRulesResponseSuccessList;
	}

	private ScenarioMenuPricingRule fetchMenuRule(int brandId, BigInteger ruleId) {

		StringBuilder sb = new StringBuilder(
				"from ScenarioMenuPricingRule where brandId=:brand_Id and ruleId=:rule_Id ");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("brand_Id", brandId);
		query.setParameter("rule_Id", ruleId);
		List<Object> rows = query.list();
		ScenarioMenuPricingRule menuRule = (ScenarioMenuPricingRule) rows.get(0);
		return menuRule;
	}

	private ScenarioPricingRule fetchPricingRule(int brandId, BigInteger ruleId) {

		StringBuilder sb = new StringBuilder("from ScenarioPricingRule where brandId=:brand_Id and ruleId=:rule_Id ");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("brand_Id", brandId);
		query.setParameter("rule_Id", ruleId);
		List<Object> rows = query.list();
		ScenarioPricingRule pricingRule = (ScenarioPricingRule) rows.get(0);
		return pricingRule;
	}

	private BigInteger getDataEntryIdFromProjectId(Integer brandId, BigInteger projectId) {

		BigInteger dataEntryId = new BigInteger("0");
		try {

			StringBuilder sb = new StringBuilder(
					"SELECT  dataEntryId from Project where brandId =:brand_id and projectId=:project_id");

			Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
			query.setParameter("brand_id", brandId);
			query.setParameter("project_id", projectId);
			List resultObjects = query.list();
			dataEntryId = (BigInteger) resultObjects.get(0);
		} catch (Exception ex) {

		}
		return dataEntryId;
	}

	private MenuPricingResponse getMenuItemRecordsForRule(MenuItem menuItem, ApplyRuleRequest ruleRequest,
			int brandId) {

		BigInteger dataEntryId = getDataEntryIdFromProjectId(brandId, ruleRequest.getProjectId());

		MenuPricingResponse response = new MenuPricingResponse();
		response.setCount(0);

		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("[ImpactSimulator].[dbo].[MenuitemSelectProcForSearch_NEW]");

		query.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
		query.setParameter(0, 0);
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.setParameter(1, 100000);

		if ((menuItem != null) && (menuItem.getCat1() != null) && !(menuItem.getCat1().isEmpty())) {
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.setParameter(2, menuItem.getCat1());
		} else {
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.setParameter(2, null);
		}
		if ((menuItem != null) && (menuItem.getCat2() != null) && !(menuItem.getCat2().isEmpty())) {
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.setParameter(3, menuItem.getCat2());
		} else {
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.setParameter(3, null);
		}
		if ((menuItem != null) && (menuItem.getCat3() != null) && !(menuItem.getCat3().isEmpty())) {
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.setParameter(4, menuItem.getCat3());
		} else {
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.setParameter(4, null);
		}
		if ((menuItem != null) && (menuItem.getTier() != null) && !(menuItem.getTier().isEmpty())) {
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			query.setParameter(5, menuItem.getTier());
		} else {
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			query.setParameter(5, null);
		}
		if ((menuItem != null) && (menuItem.getPriceSensitivity() != null)
				&& !(menuItem.getPriceSensitivity().isEmpty())) {
			query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
			query.setParameter(6, menuItem.getPriceSensitivity());
		} else {
			query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
			query.setParameter(6, null);
		}
		query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
		query.setParameter(7, "Product_ID");

		query.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
		query.setParameter(8, "ASC");

		query.registerStoredProcedureParameter(9, BigInteger.class, ParameterMode.IN);
		query.setParameter(9, ruleRequest.getScenarioId());

		query.registerStoredProcedureParameter(10, BigInteger.class, ParameterMode.IN);
		query.setParameter(10, ruleRequest.getProjectId());

		query.registerStoredProcedureParameter(11, Integer.class, ParameterMode.IN);
		query.setParameter(11, brandId);

		query.registerStoredProcedureParameter(12, BigInteger.class, ParameterMode.IN);
		query.setParameter(12, dataEntryId);

		if ((menuItem != null) && (menuItem.getProductId() != null) && !(menuItem.getProductId().isEmpty())) {
			query.registerStoredProcedureParameter(13, String.class, ParameterMode.IN);
			query.setParameter(13, menuItem.getProductId());
		} else {
			query.registerStoredProcedureParameter(13, String.class, ParameterMode.IN);
			query.setParameter(13, null);
		}
		query.execute();

		List<Object[]> rows = query.getResultList();

		if ((rows != null) && (rows.size() > 0)) {
			List<MenuPricingVo> result = new ArrayList<>(rows.size());
			for (Object[] row : rows) {
				// result.add(new
				// MenuPricingVo((String)row[2],(String)row[3],(String)row[4],(String)row[5],(String)row[6],
				// (Double)row[5],(String)row[6]));
				result.add(new MenuPricingVo((String) row[2], (String) row[3], (String) row[4], (String) row[5],
						(String) row[6], (String) row[9], (Double) row[11], (Double) row[12],(Integer) row[13],(BigInteger) row[14]));
			}
			Integer count = (Integer) (rows.get(0))[15];
			response.setCount(count);
			response.setMenuPrice(result);
		}
		return response;

	}

	@Override
	public List<Operator> getOperatorList() throws SQLException, Exception {
		TypedQuery<Operator> allOperatorQuery = entityManager.createNamedQuery("OperatorList", Operator.class);
		List<Operator> operatorList = allOperatorQuery.getResultList();

		return operatorList;
	}

	@Override
	public PricingRulesListResponse getPricingRulesForScenario(BigInteger scenarioId, int brandId)
			throws SQLException, Exception {

		PricingRulesListResponse response = new PricingRulesListResponse();
		try {
			StringBuilder sb = new StringBuilder(
					"from ScenarioPricingRule where brandId=:brand_Id and scenarioId=:scenario_Id and isDeleted=:deleted");
			Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
			query.setParameter("brand_Id", brandId);
			query.setParameter("scenario_Id", scenarioId);
			query.setParameter("deleted", false);
			List<Object[]> rows = query.list();

			List<PricingRuleVo> result = new ArrayList<>(rows.size());
			for (Object row : rows) {
				ScenarioPricingRule pricingRule = (ScenarioPricingRule) row;
				PricingRuleData ruleData = new ObjectMapper().readValue(pricingRule.getRuleData(),
						PricingRuleData.class);
				result.add(new PricingRuleVo(pricingRule.getRuleId(), pricingRule.getRuleName(),
						pricingRule.getScenarioId(), pricingRule.getBrandId(), pricingRule.isApplied(),
						pricingRule.isDeleted(), pricingRule.getCreatedOn(), pricingRule.getCreatedBy(),
						pricingRule.getRuleType(), pricingRule.getTierUpdate(), pricingRule.getPriceChange(), ruleData,
						pricingRule.isPriceChangeByPercentage(),pricingRule.getPriceBarrierOption()));
			}
			response.setCount_PricingRules(rows.size());
			response.setPricingRules(result);
			response.setSuccessFlag(true);
		} catch (Exception ex) {
			logger.debug("Exception occured inside while fetching Pricing Rules from DB");
			return (PricingRulesListResponse) new FBRestResponse(false,
					"Exception Occured while fetching data from DB");
		}
		return response;

	}

	@Override
	public List<ScenarioMenuPricingRule> getScenarioMenuRules(BigInteger scenarioId, int brandId)
			throws SQLException, Exception {
		TypedQuery<ScenarioMenuPricingRule> scMenuRoleTypedQuery = entityManager.createNamedQuery("SelectAllMenuQuery",
				ScenarioMenuPricingRule.class);

		scMenuRoleTypedQuery.setParameter("brand_Id", brandId);
		scMenuRoleTypedQuery.setParameter("scenario_Id", scenarioId);
		scMenuRoleTypedQuery.setParameter("is_Deleted", false);
		return scMenuRoleTypedQuery.getResultList();

	}
	
	public ScenarioMenuPricingRule getScenarioMenuRule(BigInteger ruleId)
			throws SQLException, Exception {
		 return entityManager.find(ScenarioMenuPricingRule.class, ruleId);


	}

	private StoreTierResponse getStoreTierRecordsForRule(StoreTier storeTier, ApplyRuleRequest ruleRequest,
			int brandId) {

		BigInteger dataEntryId = getDataEntryIdFromProjectId(brandId, ruleRequest.getProjectId());

		StoreTierResponse response = new StoreTierResponse();
		response.setCount(0);

		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("[ImpactSimulator].[dbo].[StoreTierViewProcForSearch_NEW]");

		query.registerStoredProcedureParameter(0, Integer.class, ParameterMode.IN);
		query.setParameter(0, 0);
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.setParameter(1, 100000);
		if ((storeTier != null) && (storeTier.getCurrentTier() != null) && !(storeTier.getCurrentTier().isEmpty())) {
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.setParameter(2, storeTier.getCurrentTier());
		} else {
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.setParameter(2, null);
		}
		if ((storeTier != null) && (storeTier.getStoreSensitivity() != null)
				&& !(storeTier.getStoreSensitivity().isEmpty())) {
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.setParameter(3, storeTier.getStoreSensitivity());
		} else {
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.setParameter(3, null);
		}
		if ((storeTier != null) && (storeTier.getPricingPower() != null) && !(storeTier.getPricingPower().isEmpty())) {
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.setParameter(4, storeTier.getPricingPower());
		} else {
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.setParameter(4, null);
		}
		query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
		query.setParameter(5, "Store_Code");
		query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
		query.setParameter(6, "ASC");
		query.registerStoredProcedureParameter(7, BigInteger.class, ParameterMode.IN);
		query.setParameter(7, ruleRequest.getScenarioId());
		query.registerStoredProcedureParameter(8, BigInteger.class, ParameterMode.IN);
		query.setParameter(8, ruleRequest.getProjectId());
		query.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);
		query.setParameter(9, brandId);
		query.registerStoredProcedureParameter(10, BigInteger.class, ParameterMode.IN);
		query.setParameter(10, dataEntryId);

		query.execute();

		List<Object[]> rows = query.getResultList();

		if ((rows != null) && (rows.size() > 0)) {
			List<StoreTierVo> result = new ArrayList<>(rows.size());
			for (Object[] row : rows) {
				result.add(new StoreTierVo((Integer) row[0], (String) row[1], (String) row[2], (String) row[3],
						(String) row[4]));
			}
			Integer count = (Integer) (rows.get(0))[5];
			response.setCount(count);
			response.setStoreTier(result);
		}

		return response;
	}

	@Override
	public List<ApplyRulesStatusResponse> revertMenuRules(int brandId, List<ApplyRuleRequest> rulesNotApplicable,
			String userName) throws SQLException, Exception {

		List<ApplyRulesStatusResponse> revertRulesResponseList = new ArrayList<>();

		for (ApplyRuleRequest revertRule : rulesNotApplicable) {
			ScenarioMenuPricingRule pricingRule = fetchMenuRule(brandId, revertRule.getRuleId());
			ApplyRulesStatusResponse response = applyOrRevertMenuRule(revertRule, pricingRule, brandId, userName);
			revertRulesResponseList.add(response);
		}

		return revertRulesResponseList;
	}

	@Override
	public List<ApplyRulesStatusResponse> revertRules(int brandId, List<ApplyRuleRequest> rulesNotApplicable,
			String userName) throws SQLException, Exception {

		List<ApplyRulesStatusResponse> revertRulesResponseList = new ArrayList<>();

		for (ApplyRuleRequest revertRule : rulesNotApplicable) {
			ScenarioPricingRule pricingRule = fetchPricingRule(brandId, revertRule.getRuleId());
			ApplyRulesStatusResponse response = applyOrRevertRule(revertRule, pricingRule, brandId, userName);
			revertRulesResponseList.add(response);
		}

		return revertRulesResponseList;
	}

	private ApplyRulesStatusResponse updateMenuRuleMenuTierPrice(ApplyRuleRequest ruleRequest, int brandId,
			MenuPricingResponse responseDependentRuleDataList, MenuPricingResponse responseDecisiveRuleDataList,
			ScenarioMenuPricingRule pricingRule, String userName) throws SQLException, Exception {

		boolean isChanged = true;
		int resultCount = 0;
		logger.info("In  updateMenuRuleMenuTierPrice method ,ApplyRuleRequest = " + ruleRequest);

		List<MenuPricingVo> dependentProductList = responseDependentRuleDataList.getMenuPrice();
		for (Iterator iterator = dependentProductList.iterator(); iterator.hasNext();) {
			MenuPricingVo dependentProduct = (MenuPricingVo) iterator.next();
			RequestMenuTierPriceUpdate menuTierPriceUpdateReq = new RequestMenuTierPriceUpdate();
			menuTierPriceUpdateReq.setBrandId(brandId);
			menuTierPriceUpdateReq.setScenario_Id(ruleRequest.getScenarioId());
			menuTierPriceUpdateReq.setProject_Id(ruleRequest.getProjectId());
			menuTierPriceUpdateReq.setProductId(dependentProduct.getProduct_ID());
			RequestMenuTierPriceUpdate menuTierRuleIDUpdate = new RequestMenuTierPriceUpdate();
			if (!ruleRequest.isApplied() || ruleRequest.isDeleted()) {
				menuTierPriceUpdateReq.setAssociateRuleId(null);
				menuTierPriceUpdateReq.setChangeType(FBConstants.PriceChangeType.MANUAL.ordinal());
			} else {
				menuTierPriceUpdateReq.setAssociateRuleId(ruleRequest.getRuleId());
				menuTierPriceUpdateReq.setChangeType(FBConstants.PriceChangeType.MENURULE.ordinal());
			}

			int operator = pricingRule.getOperator();
			float priceChange = pricingRule.getPriceChange();
			if ((operator == 1) && (priceChange > 0)) {
				if (!ruleRequest.isApplied() || ruleRequest.isDeleted()) {
					if (!(Double.compare(dependentProduct.getNew_Price(), priceChange) == 0)) {
						continue;
					}
					isChanged = false;
					menuTierPriceUpdateReq.setPrice(Double.valueOf(dependentProduct.getCurrent_Price()));

				} else {
					menuTierPriceUpdateReq.setPrice((double) priceChange);
				}
				menuTierPriceUpdateReq.setTier(dependentProduct.getProposed_Tier());
				resultCount = updateMenuTierPrice(UPDATE_IST_PRODUCT_TIER_PRICE_FROM_MENU_RULE_QUERY,
						menuTierPriceUpdateReq, userName, isChanged);
			} else {
				List<MenuPricingVo> decisiveProductList = responseDecisiveRuleDataList.getMenuPrice();
				for (Iterator iterator2 = decisiveProductList.iterator(); iterator2.hasNext();) {
					MenuPricingVo decisiveProduct = (MenuPricingVo) iterator2.next();
					if (decisiveProduct.getProposed_Tier().equalsIgnoreCase(dependentProduct.getProposed_Tier())) {

						Double decisveNewPrice = decisiveProduct.getNew_Price();
						if (operator <= 3) {
							double newPrice = decisveNewPrice + priceChange;
							if (!ruleRequest.isApplied() || ruleRequest.isDeleted()) {
								if (!(Double.compare(dependentProduct.getNew_Price(), newPrice) == 0)) {
									continue;
								}
								isChanged = false;
								menuTierPriceUpdateReq.setPrice(Double.valueOf(dependentProduct.getCurrent_Price()));
							} else {
								menuTierPriceUpdateReq.setPrice(newPrice);
							}

						} else {
							double newPrice = decisveNewPrice - priceChange;
							newPrice = newPrice > 0 ? newPrice : decisveNewPrice;
							if (!ruleRequest.isApplied() || ruleRequest.isDeleted()) {
								if (!(Double.compare(dependentProduct.getNew_Price(), newPrice) == 0)) {
									continue;
								}
								isChanged = false;
								menuTierPriceUpdateReq.setPrice(Double.valueOf(dependentProduct.getCurrent_Price()));
							} else {
								menuTierPriceUpdateReq.setPrice(newPrice);
							}

						}

						menuTierPriceUpdateReq.setTier(dependentProduct.getProposed_Tier());
						resultCount = updateMenuTierPrice(UPDATE_IST_PRODUCT_TIER_PRICE_FROM_MENU_RULE_QUERY,
								menuTierPriceUpdateReq, userName, isChanged);
						menuTierRuleIDUpdate=menuTierPriceUpdateReq;
						menuTierRuleIDUpdate.setProductId(decisiveProduct.getProduct_ID());
						updateMenuRuleID(menuTierRuleIDUpdate, userName, isChanged);
						break;
					}

				}
			}

		}
		if (!ruleRequest.isApplied()) {
			if (resultCount == 0) {

				return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
						"No rows updated. Table does not contain the required record.");
			} else {

				return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), true,
						"Rule reverted successfully ");
			}
		}
		if (resultCount == 0) {
			return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
					"No rows updated. Table does not contain the required record.");
		} else {
			return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), true,
					"Rule applied successfully to data");
		}

	}

	private void updateMenuRuleScenarioPricing(ApplyRuleRequest applyRuleRequest, int brandId) {

		StringBuilder sb = new StringBuilder(
				"UPDATE ScenarioMenuPricingRule SET IsApplied=:is_Applied where BrandId=:brand_Id and RuleId=:rule_Id and ScenarioId=:scenario_Id");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("brand_Id", brandId);
		query.setParameter("scenario_Id", applyRuleRequest.getScenarioId());
		query.setParameter("is_Applied", applyRuleRequest.isApplied());
		query.setParameter("rule_Id", applyRuleRequest.getRuleId());
		query.executeUpdate();

	}

	private ApplyRulesStatusResponse updateMenuTierPrice(ApplyRuleRequest ruleRequest, int brandId,
			MenuPricingResponse responseList, ScenarioPricingRule pricingRule, String userName) {

		boolean isChanged = true;
		int resultCount = 0;
		logger.info("In  updateMenuTierPrice method ,ApplyRuleRequest = " + ruleRequest);
		try {
			for (MenuPricingVo menuPricingVo : responseList.getMenuPrice()) {
				RequestMenuTierPriceUpdate menuTierPriceUpdateReq = new RequestMenuTierPriceUpdate();
				menuTierPriceUpdateReq.setBrandId(brandId);
				menuTierPriceUpdateReq.setScenario_Id(ruleRequest.getScenarioId());
				menuTierPriceUpdateReq.setProject_Id(ruleRequest.getProjectId());
				menuTierPriceUpdateReq.setProductId(menuPricingVo.getProduct_ID());
				if(!ruleRequest.isApplied() || ruleRequest.isDeleted()){
					menuTierPriceUpdateReq.setAssociateRuleId(menuPricingVo.getChangeRuleId());
					menuTierPriceUpdateReq.setChangeType(FBConstants.PriceChangeType.MANUAL.ordinal());
				}else{
					menuTierPriceUpdateReq.setAssociateRuleId(menuPricingVo.getChangeRuleId());
					menuTierPriceUpdateReq.setChangeType(FBConstants.PriceChangeType.PRICERULE.ordinal());
				}
				
				// menuTierPriceUpdateReq.setPrice(Double.valueOf(pricingRule.getPriceChange().toString()));

				if (!ruleRequest.isApplied() || ruleRequest.isDeleted()) {
					logger.info("!ruleRequest.isApplied() || ruleRequest.isDeleted()");
					if (pricingRule.isPriceChangeByPercentage()) {
						Double priceChange = ((menuPricingVo.getCurrent_Price() * pricingRule.getPriceChange()) / 100);
						Double newPrice = menuPricingVo.getCurrent_Price() + priceChange;
						if (!(Double.compare(newPrice, menuPricingVo.getNew_Price()) == 0)) {
							continue;
						}
					} else {
						Double newPrice = menuPricingVo.getCurrent_Price() + pricingRule.getPriceChange();
						logger.info("menuPricingVo.getCurrent_Price()+pricingRule.getPriceChange() " + newPrice);
						logger.info("menuPricingVo.getNew_Price()" + menuPricingVo.getNew_Price());
						if (!(Double.compare(menuPricingVo.getNew_Price(), newPrice) == 0)) {
							logger.info("menuPricingVo.getNew_Price()==newprice not equal");
							continue;
						}
					}
					isChanged = false;
					menuTierPriceUpdateReq.setPrice(Double.valueOf(menuPricingVo.getCurrent_Price()));
				}
				if (ruleRequest.isApplied() && !ruleRequest.isDeleted()) {
					logger.info("ruleRequest.isApplied() && !ruleRequest.isDeleted()");
					if (Double.compare(menuPricingVo.getCurrent_Price(), menuPricingVo.getNew_Price()) == 0) {
						if (pricingRule.isPriceChangeByPercentage()) {
							Double priceChange = ((menuPricingVo.getCurrent_Price() * pricingRule.getPriceChange())
									/ 100);
							Double newPrice = menuPricingVo.getCurrent_Price() + priceChange;
							menuTierPriceUpdateReq.setPrice(newPrice);
						} else {
							menuTierPriceUpdateReq.setPrice(menuPricingVo.getCurrent_Price()
									+ Double.valueOf(pricingRule.getPriceChange().toString()));
						}
					} else {
						logger.info("Skipping from updation as the price is changed manually");
						continue;
					}
				}
				// menuTierPriceUpdateReq.setPrice(menuPricingVo.getCurrent_Price()+Double.valueOf(pricingRule.getPriceChange().toString()));

				menuTierPriceUpdateReq.setTier(menuPricingVo.getProposed_Tier());
				resultCount = updateMenuTierPrice(UPDATE_IST_PRODUCT_TIER_PRICE_FROM_PRICING_RULE_QUERY,
						menuTierPriceUpdateReq, userName, isChanged);
				if(resultCount>0&&menuPricingVo.getChangeRuleId()!=null&&menuPricingVo.getChangeRuleId().intValue()>0){
					ScenarioMenuPricingRule object=getScenarioMenuRule(menuPricingVo.getChangeRuleId());
					if(object!=null&&object.isApplied()&&object.isDeleted()==false){
						ApplyRuleRequest applyRuleRequest= new ApplyRuleRequest();
						applyRuleRequest.setApplied(true);
						applyRuleRequest.setDeleted(false);
						applyRuleRequest.setProjectId(ruleRequest.getProjectId());
						applyRuleRequest.setRuleId(menuPricingVo.getChangeRuleId());
						applyRuleRequest.setScenarioId(ruleRequest.getScenarioId());
						List<ApplyRuleRequest> rulesApplicable= new ArrayList<>();
						rulesApplicable.add(applyRuleRequest);
						applymenuRules(brandId, rulesApplicable, userName);
					}
					
				}
			}
		} catch (Exception ex) {
			logger.info("Excption occured while updating Menu Tier Price");
			return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
					"Exception occured while applying rule to data");
		}

		if (!ruleRequest.isApplied()) {
			if (resultCount == 0) {

				return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
						"No rows updated. Table does not contain the required record.");
			} else {

				return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), true,
						"Rule reverted successfully ");
			}
		}
		if (resultCount == 0) {
			return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
					"No rows updated. Table does not contain the required record");
		} else {
			return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), true,
					"Rule applied successfully to data");
		}

	}

	public int updateMenuTierPrice(String queryString, RequestMenuTierPriceUpdate requestMenuTier, String userName,
			boolean isChanged) throws SQLException, Exception {
		StringBuilder sb = new StringBuilder(queryString);
		logger.info("requestMenuTier  =  " + requestMenuTier);
		logger.info("isChanged  =  " + isChanged);
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("price", requestMenuTier.getPrice());
		query.setParameter("product_id", requestMenuTier.getProductId());
		query.setParameter("tier", requestMenuTier.getTier());
		query.setParameter("lastUpdated_date", Date.from(Instant.now()));
		query.setParameter("lastUpdated_by", userName);
		query.setParameter("project_Id", requestMenuTier.getProject_Id());
		query.setParameter("scenario_Id", requestMenuTier.getScenario_Id());
		query.setParameter("brand_Id", requestMenuTier.getBrandId());
		query.setParameter("isChanged", isChanged);
		query.setParameter("associateRuleId", requestMenuTier.getAssociateRuleId());
		query.setParameter("changeType", requestMenuTier.getChangeType());
		
		if (queryString == UPDATE_IST_PRODUCT_TIER_PRICE_FROM_MENU_RULE_QUERY) {
			query.setParameter("isEditable", !isChanged);
		}
		logger.info("query  =  " + query.toString());
		int resultObjects = query.executeUpdate();
		return resultObjects;
	}
	
	public int updateMenuRuleID(RequestMenuTierPriceUpdate requestMenuTier, String userName,
			boolean isChanged) throws SQLException, Exception {
		StringBuilder sb = new StringBuilder(UPDATE_IST_PRODUCT_TIER_PRICE_FROM_MENU_RULE_ID_QUERY);
		logger.info("requestMenuTier  =  " + requestMenuTier);
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("associateRuleId", requestMenuTier.getAssociateRuleId());
		query.setParameter("lastUpdated_date", Date.from(Instant.now()));
		query.setParameter("lastUpdated_by", userName);
		
		query.setParameter("project_Id", requestMenuTier.getProject_Id());
		query.setParameter("scenario_Id", requestMenuTier.getScenario_Id());
		query.setParameter("brand_Id", requestMenuTier.getBrandId());
		query.setParameter("product_id", requestMenuTier.getProductId());
		query.setParameter("tier", requestMenuTier.getTier());
		
		
		logger.info("query  =  " + query.toString());
		int resultObjects = query.executeUpdate();
		return resultObjects;
	}

	private void updateScenarioPricing(ApplyRuleRequest applyRuleRequest, int brandId) {

		StringBuilder sb = new StringBuilder(
				"UPDATE ScenarioPricingRule SET IsApplied=:is_Applied where BrandId=:brand_Id and RuleId=:rule_Id and ScenarioId=:scenario_Id");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("brand_Id", brandId);
		query.setParameter("scenario_Id", applyRuleRequest.getScenarioId());
		query.setParameter("is_Applied", applyRuleRequest.isApplied());
		query.setParameter("rule_Id", applyRuleRequest.getRuleId());
		query.executeUpdate();

	}

	private ApplyRulesStatusResponse updateStoreTier(ApplyRuleRequest ruleRequest, int brandId,
			StoreTierResponse responseList, ScenarioPricingRule pricingRule, String userName) {

		boolean isChanged = true;

		try {
			for (StoreTierVo storeTierVo : responseList.getStoreTier()) {
				UpdateStoreInfoRequest updateStoreInfoRequest = new UpdateStoreInfoRequest();
				updateStoreInfoRequest.setBrandId(brandId);
				updateStoreInfoRequest.setProject_Id(ruleRequest.getProjectId());
				updateStoreInfoRequest.setScenario_Id(ruleRequest.getScenarioId());
				updateStoreInfoRequest.setProposedTier(pricingRule.getTierUpdate());
				if (ruleRequest.isApplied()) {
					if (!(storeTierVo.getProposed_Tier().equalsIgnoreCase(storeTierVo.getCurrent_Tier()))) {
						continue;
					}
				}

				if (!ruleRequest.isApplied() || ruleRequest.isDeleted()) {
					if (!pricingRule.getTierUpdate().equalsIgnoreCase(storeTierVo.getProposed_Tier())) {
						continue;
					}
					isChanged = false;
					updateStoreInfoRequest.setProposedTier(storeTierVo.getCurrent_Tier());
				}
				updateStoreInfoRequest.setStoreCode(storeTierVo.getStore_Code());
				updateStoreTier(updateStoreInfoRequest, userName, isChanged);
			}

		} catch (Exception ex) {
			logger.info("Excption occured while updating Menu Tier Price");
			return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), false,
					"Exception occured while applying rule to data");
		}
		if (!ruleRequest.isApplied()) {
			return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), true,
					"Rule reverted successfully ");
		}
		return new ApplyRulesStatusResponse(ruleRequest.getRuleId(), pricingRule.getRuleName(), true,
				"Rule applied successfully to data");
	}

	public FBRestResponse updateStoreTier(UpdateStoreInfoRequest updateStoreInfoRequest, String userName,
			boolean isChanged) throws SQLException, Exception {

		StringBuilder sb = new StringBuilder(
				"update IST_Store_Info set  Proposed_Tier=:proposed_Tier,isChanged=:isChanged,"
						+ "CreatedOn=createdOn,UpdatedOn=:updatedOn,UpdatedBy=:updatedBy where BrandId=:brand_Id and Project_Id =:project_Id and Scenario_Id=:scenario_Id and Store_Code =:store_Code");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("proposed_Tier", updateStoreInfoRequest.getProposedTier());
		query.setParameter("store_Code", updateStoreInfoRequest.getStoreCode());
		query.setParameter("isChanged", isChanged);
		query.setParameter("updatedOn", Date.from(Instant.now()));
		query.setParameter("updatedBy", userName);
		query.setParameter("project_Id", updateStoreInfoRequest.getProject_Id());
		query.setParameter("scenario_Id", updateStoreInfoRequest.getScenario_Id());
		query.setParameter("brand_Id", updateStoreInfoRequest.getBrandId());
		int resultObjects = query.executeUpdate();
		if (resultObjects >= 1) {
			return new FBRestResponse(true, "Store Tier Updated Successfully");
		}
		return new FBRestResponse(false,
				"There are no records to be updated for the provided store code : "
						+ updateStoreInfoRequest.getStoreCode() + " and proposed tier :"
						+ updateStoreInfoRequest.getProposedTier());
	}

}
