package fb.pricingAnalytics.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fb.pricingAnalytics.dao.MenuPricingDAO;
import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.model.vo.StoreTierVo;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;
import fb.pricingAnalytics.service.MenuPricingService;
import fb.pricingAnalytics.utils.FBRestResponse;


@Service
public class MenuPricingServiceImpl implements MenuPricingService{

	
	@Autowired
	MenuPricingDAO menuPricingDAO;
	@Override
	public List<MenuPricingVo> getMenuPricing( RequestPricePlanner requestPricePlanner)  throws SQLException,Exception {
		
		return menuPricingDAO.getMenuPricing(requestPricePlanner);
	}
	
	@Transactional
	@Override
	public int updateMenuTierPrice(RequestMenuTierPriceUpdate requestMenuTier, String userName) throws SQLException, Exception {

		return menuPricingDAO.updateMenuTierPrice(requestMenuTier,userName);
	}

	@Override
	public List<StoreTierVo> getStoreTierView(RequestPricePlanner requestPricePlanner) throws SQLException, Exception {
		return menuPricingDAO.getStoreTierView(requestPricePlanner);
	}
	
	@Transactional
	@Override
	public FBRestResponse updateStoreTier(String proposedTier,Integer storeCode) throws SQLException, Exception {
		return menuPricingDAO.updateStoreTier(proposedTier,storeCode);
	}
	
	@Override
	public List<StoreTierVo> getOtherStoreView(RequestPricePlanner requestPricePlanner) throws SQLException, Exception {
		return menuPricingDAO.getOtherStoreView(requestPricePlanner);
	}


}
