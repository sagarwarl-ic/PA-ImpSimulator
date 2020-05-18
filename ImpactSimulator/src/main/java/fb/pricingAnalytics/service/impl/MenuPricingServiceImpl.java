package fb.pricingAnalytics.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fb.pricingAnalytics.dao.MenuPricingDAO;
import fb.pricingAnalytics.dao.PricingRuleDAO;
import fb.pricingAnalytics.entity.dao.ScenarioPricingRuleDao;
import fb.pricingAnalytics.model.ISTProductTierInfo;
import fb.pricingAnalytics.model.ScenarioMenuPricingRule;
import fb.pricingAnalytics.model.vo.FilterData;
import fb.pricingAnalytics.model.vo.FilterDataHierarchy;
import fb.pricingAnalytics.model.vo.MenuItemDistributionVo;
import fb.pricingAnalytics.model.vo.OverAllImpactsVo;
import fb.pricingAnalytics.model.vo.StoreDistributionVo;
import fb.pricingAnalytics.model.vo.StoreTierVo;
import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;
import fb.pricingAnalytics.request.UpdateStoreInfoRequest;
import fb.pricingAnalytics.response.MenuPricingResponse;
import fb.pricingAnalytics.response.StoreTierResponse;
import fb.pricingAnalytics.service.MenuPricingService;
import fb.pricingAnalytics.utils.FBRestResponse;


@Service
public class MenuPricingServiceImpl implements MenuPricingService{

	
	@Autowired
	MenuPricingDAO menuPricingDAO;
	
	@Autowired
	PricingRuleDAO pricingRuleDAO;
	
	@Autowired
	ScenarioPricingRuleDao scenarioPricingRuleDao;
	
	@Override
	public FilterData getFilterData(RequestPricePlanner requestPricePlanner) throws SQLException,Exception {
		return menuPricingDAO.getFilterData(requestPricePlanner);
	}
	
	@Override
	public List<Object> getFilterData(String filterParam) throws SQLException,Exception {
		return menuPricingDAO.getFilterData(filterParam);
	}

	@Override
	public FilterDataHierarchy getFilterDataHierarchy(RequestPricePlanner requestPricePlanner)
			throws SQLException, Exception {
		// TODO Auto-generated method stub
		return menuPricingDAO.getFilterDataHierarchy(requestPricePlanner);
	}
	
	@Override
	public List<MenuItemDistributionVo> getMenuItemDistribution(RequestPricePlanner requestPricePlanner)throws SQLException, Exception {
		return menuPricingDAO.getMenuItemDistribution(requestPricePlanner);
	}
	
	@Override
	public MenuPricingResponse getMenuPricing( RequestPricePlanner requestPricePlanner)  throws SQLException,Exception {
		
		return menuPricingDAO.getMenuPricing(requestPricePlanner);
	}

	@Override
	public FilterDataHierarchy getMenuRulesFilterDataHierarchy(RequestPricePlanner requestPricePlanner)
			throws SQLException, Exception {

		return menuPricingDAO.getMenuRulesFilterDataHierarchy(requestPricePlanner);
	}

	@Override
	public List<StoreTierVo> getOtherStoreView(RequestPricePlanner requestPricePlanner) throws SQLException, Exception {
		return menuPricingDAO.getOtherStoreView(requestPricePlanner);
	}

	@Override
	public OverAllImpactsVo getOverAllImpacts(RequestPricePlanner requestPricePlanner) throws SQLException,Exception {
		return menuPricingDAO.getOverAllImpacts(requestPricePlanner);
	}
	
	@Override
	public List<StoreDistributionVo> getStoreDistribution(RequestPricePlanner requestPricePlanner)throws SQLException, Exception {
		return menuPricingDAO.getStoreDistribution(requestPricePlanner);
	}
	
	@Override
	public StoreTierResponse getStoreTierView(RequestPricePlanner requestPricePlanner) throws SQLException, Exception {
		return menuPricingDAO.getStoreTierView(requestPricePlanner);
	}
	
	@Transactional
	@Override
	public int updateMenuTierPrice(RequestMenuTierPriceUpdate requestMenuTier, String userName) throws SQLException, Exception {

		int resultObjects = menuPricingDAO.updateMenuTierPrice(requestMenuTier,userName);
		if(resultObjects>0){
			applyMenuPricesOnManualChange(requestMenuTier,requestMenuTier.getBrandId(),userName);
		}
		return resultObjects;
	}

	@Transactional
	@Override
	public FBRestResponse updateMenuTierPrices(List<RequestMenuTierPriceUpdate> menuTierPriceUpdateReq,int tenantId,String userName)throws SQLException, Exception {
		boolean success=false;
		for(RequestMenuTierPriceUpdate priceUpdateRequest : menuTierPriceUpdateReq){
			priceUpdateRequest.setBrandId(tenantId);
			int resultObjects =menuPricingDAO.updateMenuTierPrice(priceUpdateRequest,userName);
			if(resultObjects>0){
				success=true;
				applyMenuPricesOnManualChange(priceUpdateRequest,tenantId,userName);
			}
		}
		return new FBRestResponse(success, "Tier Price Updated Successfully");
	}
	private void applyMenuPricesOnManualChange(RequestMenuTierPriceUpdate priceUpdateRequest,int tenantId,String userName) throws SQLException, Exception{
		ISTProductTierInfo resultObject=menuPricingDAO.getRecordByProductIdTier(priceUpdateRequest);
		if(resultObject.getAssociateRuleId()!=null&&resultObject.getAssociateRuleId().intValue()>0){
			ScenarioMenuPricingRule object=scenarioPricingRuleDao.getScenarioMenuRule(resultObject.getAssociateRuleId());
			if(object!=null&&object.isApplied()&&object.isDeleted()==false){
				ApplyRuleRequest applyRuleRequest= new ApplyRuleRequest();
				applyRuleRequest.setApplied(true);
				applyRuleRequest.setDeleted(false);
				applyRuleRequest.setProjectId(priceUpdateRequest.getProject_Id());
				applyRuleRequest.setRuleId(object.getRuleId());
				applyRuleRequest.setScenarioId(priceUpdateRequest.getScenario_Id());
				List<ApplyRuleRequest> rulesApplicable= new ArrayList<>();
				rulesApplicable.add(applyRuleRequest);
				pricingRuleDAO.applymenuRules(tenantId, rulesApplicable, userName);
			}
			
		}
	}
	@Transactional
	@Override
	public FBRestResponse updateStores(List<UpdateStoreInfoRequest> updateStoreInfoRequest,String userName,int tenantId) throws SQLException, Exception {
		return menuPricingDAO.updateStores(updateStoreInfoRequest,userName,tenantId);
	}

	@Transactional
	@Override
	public FBRestResponse updateStoreTier(UpdateStoreInfoRequest updateStoreInfoRequest, String userName)
			throws SQLException, Exception {
		return menuPricingDAO.updateStoreTier(updateStoreInfoRequest, userName);
	}

}
