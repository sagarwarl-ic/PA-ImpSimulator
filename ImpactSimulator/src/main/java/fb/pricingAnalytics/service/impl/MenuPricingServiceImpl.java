package fb.pricingAnalytics.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fb.pricingAnalytics.dao.MenuPricingDAO;
import fb.pricingAnalytics.model.vo.FilterData;
import fb.pricingAnalytics.model.vo.MenuItemDistributionVo;
import fb.pricingAnalytics.model.vo.OverAllImpactsVo;
import fb.pricingAnalytics.model.vo.StoreDistributionVo;
import fb.pricingAnalytics.model.vo.StoreTierVo;
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
	
	@Override
	public MenuPricingResponse getMenuPricing( RequestPricePlanner requestPricePlanner)  throws SQLException,Exception {
		
		return menuPricingDAO.getMenuPricing(requestPricePlanner);
	}
	
	@Transactional
	@Override
	public int updateMenuTierPrice(RequestMenuTierPriceUpdate requestMenuTier, String userName) throws SQLException, Exception {

		return menuPricingDAO.updateMenuTierPrice(requestMenuTier,userName);
	}

	@Override
	public StoreTierResponse getStoreTierView(RequestPricePlanner requestPricePlanner) throws SQLException, Exception {
		return menuPricingDAO.getStoreTierView(requestPricePlanner);
	}
	
	@Transactional
	@Override
	public FBRestResponse updateStoreTier(UpdateStoreInfoRequest updateStoreInfoRequest,String userName) throws SQLException, Exception {
		return menuPricingDAO.updateStoreTier(updateStoreInfoRequest,userName);
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
	public List<MenuItemDistributionVo> getMenuItemDistribution(RequestPricePlanner requestPricePlanner)throws SQLException, Exception {
		return menuPricingDAO.getMenuItemDistribution(requestPricePlanner);
	}
	
	@Override
	public FilterData getFilterData(RequestPricePlanner requestPricePlanner) throws SQLException,Exception {
		return menuPricingDAO.getFilterData(requestPricePlanner);
	}
	
	@Override
	public List<Object> getFilterData(String filterParam) throws SQLException,Exception {
		return menuPricingDAO.getFilterData(filterParam);
	}
	
	@Transactional
	@Override
	public FBRestResponse updateStores(List<UpdateStoreInfoRequest> updateStoreInfoRequest,String userName,int tenantId) throws SQLException, Exception {
		return menuPricingDAO.updateStores(updateStoreInfoRequest,userName,tenantId);
	}

	@Transactional
	@Override
	public FBRestResponse updateMenuTierPrices(List<RequestMenuTierPriceUpdate> menuTierPriceUpdateReq,int tenantId,String userName)throws SQLException, Exception {
		return menuPricingDAO.updateMenuTierPrices(menuTierPriceUpdateReq,tenantId,userName);
	}

}
