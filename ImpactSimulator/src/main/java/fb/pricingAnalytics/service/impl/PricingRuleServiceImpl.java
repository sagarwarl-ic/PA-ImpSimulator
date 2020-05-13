package fb.pricingAnalytics.service.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fb.pricingAnalytics.dao.PricingRuleDAO;
import fb.pricingAnalytics.model.ScenarioMenuPricingRule;
import fb.pricingAnalytics.model.vo.ScenarioMenuPricingRuleVo;
import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.MenuItem;
import fb.pricingAnalytics.request.PricingRuleRequest;
import fb.pricingAnalytics.response.ApplyRulesStatusListResponse;
import fb.pricingAnalytics.response.ApplyRulesStatusResponse;
import fb.pricingAnalytics.response.OperatorListResponse;
import fb.pricingAnalytics.response.PricingRulesListResponse;
import fb.pricingAnalytics.response.ScenarioMenuRuleListResponse;
import fb.pricingAnalytics.service.PricingRuleService;


@Service
public class PricingRuleServiceImpl implements PricingRuleService{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PricingRuleDAO pricingRuleDao;

	@Override
	public ApplyRulesStatusListResponse applyMenuRules(int brandId, List<ApplyRuleRequest> applyRules,
			String userName) {
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		List<ApplyRulesStatusResponse> responseList = new ArrayList<>();

		List<ApplyRuleRequest> rulesApplicable = applyRules.stream().filter(r -> r.isApplied() == true)
				.collect(Collectors.toList());
		// List<ApplyRuleRequest> rulesNotApplicable =
		// applyRules.stream().filter(r -> r.isApplied() == false)
		// .collect(Collectors.toList());

		// List<ApplyRulesStatusResponse> revertRulesResponse =
		// pricingRuleDao.revertRules(brandId, rulesNotApplicable,
		// userName);
		// responseList.addAll(revertRulesResponse);

		List<ApplyRulesStatusResponse> applyrulesResponse = new ArrayList<>();
		try {
			applyrulesResponse = pricingRuleDao.applymenuRules(brandId, rulesApplicable, userName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseList.addAll(applyrulesResponse);
		response.getApplyRulesStatusResponse().addAll(responseList);

		return response;
	}

	@Transactional
	@Override
	public ApplyRulesStatusListResponse applyPricingRules(int brandId,List<ApplyRuleRequest> applyRules,String userName) throws SQLException, Exception {
		
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		List<ApplyRulesStatusResponse> responseList = new ArrayList<>();
		
		List<ApplyRuleRequest> rulesApplicable =  applyRules.stream().filter(r->r.isApplied()==true).collect(Collectors.toList());
		List<ApplyRuleRequest> rulesNotApplicable =  applyRules.stream().filter(r->r.isApplied()==false).collect(Collectors.toList());
		
		List<ApplyRulesStatusResponse> revertRulesResponse = pricingRuleDao.revertRules(brandId,rulesNotApplicable,userName);
		responseList.addAll(revertRulesResponse);
			
		List<ApplyRulesStatusResponse> applyrulesResponse  = pricingRuleDao.applyRules(brandId,rulesApplicable,userName);
		responseList.addAll(applyrulesResponse);
		response.getApplyRulesStatusResponse().addAll(responseList);
		
		return response;
	}

	@Transactional
	@Override
	public BigInteger createPricingRule(PricingRuleRequest pricingRuleRequest,int brandId, String userName) throws SQLException, Exception {
		return pricingRuleDao.createPricingRule( pricingRuleRequest, brandId,  userName);
	}


	@org.springframework.transaction.annotation.Transactional
	@Override
	public BigInteger createScenarioMenuPricingRule(ScenarioMenuPricingRuleVo pricingRuleRequest, int brandId,
			String userName) {

				try {
					ScenarioMenuPricingRule object = new ScenarioMenuPricingRule(brandId,
							userName,
					Date.from(Instant.now()),
					new ObjectMapper().writeValueAsString(pricingRuleRequest.getDecisiveMenuRuleData()),
					new ObjectMapper().writeValueAsString(pricingRuleRequest.getDependentMenuRuleData()),
					pricingRuleRequest.isApplied(), pricingRuleRequest.isDeleted(), pricingRuleRequest.getOperator(),
							pricingRuleRequest.getPriceChange(), null, pricingRuleRequest
									.getRuleName(),
					pricingRuleRequest.getScenarioId(), userName, Date.from(Instant.now()));
					return pricingRuleDao.createScenarioMenuPricingRule(object, brandId, userName);
				} catch (JsonProcessingException e) {
					logger.error("Exception occured inside while fetching operators from DB");
					logger.error(e.getMessage());
				} catch (SQLException e) {
					logger.error("Exception occured inside while fetching operators from DB");
					logger.error(e.getMessage());
				} catch (Exception e) {
					logger.error("Exception occured inside while fetching operators from DB");
					logger.error(e.getMessage());
				}
				return new BigInteger("0");
	}


	private ApplyRulesStatusResponse deleteMenuRule(int brandId, ApplyRuleRequest deleteRule,
			String userName) {

		ApplyRulesStatusResponse revertRulesResponse = null;
		try {
			revertRulesResponse = pricingRuleDao.deleteMenuRule(brandId, deleteRule,userName);
		} catch (Exception e) {
			logger.error("Exception occured inside while fetching operators from DB");
			logger.error(e.getMessage());
			return new ApplyRulesStatusResponse(deleteRule.getRuleId(), false,
					"Exception occured while deleting rule ");

		}
		return revertRulesResponse;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public ApplyRulesStatusListResponse deleteMenuRules(int brandId, List<ApplyRuleRequest> deleteRules,
			String userName) {

		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		List<ApplyRulesStatusResponse> responseList = new ArrayList<>();
		for (Iterator iterator = deleteRules.iterator(); iterator.hasNext();) {
			ApplyRuleRequest deleteRule = (ApplyRuleRequest) iterator.next();
			responseList.add(this.deleteMenuRule(brandId, deleteRule, userName));

		}
		response.getApplyRulesStatusResponse().addAll(responseList);
		return response;
	}

	@Transactional
	@Override
	public ApplyRulesStatusListResponse deletePricingRules(int brandId,List<ApplyRuleRequest> deleteRules, String userName)throws SQLException, Exception {
		
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		List<ApplyRulesStatusResponse> responseList = new ArrayList<>();
		List<ApplyRulesStatusResponse> revertRulesResponse = pricingRuleDao.deleteRules(brandId,deleteRules,userName);
		responseList.addAll(revertRulesResponse);
		response.getApplyRulesStatusResponse().addAll(responseList);
		return response;
	}

	@Override
	public OperatorListResponse getOperatorList() {

		OperatorListResponse opListResp = new OperatorListResponse();
		try {
			opListResp.setOperators(pricingRuleDao.getOperatorList());
		} catch (Exception e) {
			logger.error("Exception occured inside while fetching operators from DB");
			logger.error(e.getMessage());
			return new OperatorListResponse(false, "Exception Occured while fetching data from DB");

		}
		return opListResp;
	}

	@Transactional
	@Override
	public PricingRulesListResponse getPricingRulesForScenario(BigInteger scenarioId, int brandId)
			throws SQLException, Exception {
		return pricingRuleDao.getPricingRulesForScenario(scenarioId, brandId);
	}

	@Override
	public ScenarioMenuRuleListResponse getScenarioMenuRules(BigInteger scenarioId, int brandId) {
		ScenarioMenuRuleListResponse resultResponse = new ScenarioMenuRuleListResponse(true, "Success");
		try {
			List<ScenarioMenuPricingRule> resultList = pricingRuleDao.getScenarioMenuRules(scenarioId, brandId);
			for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
				ScenarioMenuPricingRule scenarioMenuPricingRule = (ScenarioMenuPricingRule) iterator.next();
				ScenarioMenuPricingRuleVo resultResponseObj = new ScenarioMenuPricingRuleVo(brandId,
						scenarioMenuPricingRule.getCreatedBy(), scenarioMenuPricingRule.getCreatedOn(),
						new ObjectMapper().readValue(scenarioMenuPricingRule.getDecisiveMenuRuleData(), MenuItem.class),
						new ObjectMapper().readValue(scenarioMenuPricingRule.getDependentMenuRuleData(),
								MenuItem.class),
						scenarioMenuPricingRule.isApplied(), scenarioMenuPricingRule.isDeleted(),
						scenarioMenuPricingRule.getOperator(), scenarioMenuPricingRule.getPriceChange(),
						scenarioMenuPricingRule.getRuleId(), scenarioMenuPricingRule.getRuleName(),
						scenarioMenuPricingRule.getScenarioId(), scenarioMenuPricingRule.getUpdatedBy(),
						scenarioMenuPricingRule.getUpdatedOn());
				resultResponse.getScenarioMenuPricingRulelist().add(resultResponseObj);

			}
		} catch (Exception e) {
			logger.error("Exception occured inside while fetching operators from DB");
			logger.error(e.getMessage());
			return new ScenarioMenuRuleListResponse(false, "Exception Occured while fetching data from DB");
		}
		return resultResponse;
	}

	/*@Override
	public ProjectPricingRulesResponse getPricingRulesForProject(BigInteger projectId,int brandId) throws SQLException, Exception {
		return pricingRuleDao.getPricingRulesForProject(projectId,brandId);
	}*/

}
