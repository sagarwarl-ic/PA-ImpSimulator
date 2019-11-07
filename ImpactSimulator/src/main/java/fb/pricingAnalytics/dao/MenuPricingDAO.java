package fb.pricingAnalytics.dao;

import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.model.vo.OverAllImpactsVo;
import fb.pricingAnalytics.model.vo.StoreTierVo;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;
import fb.pricingAnalytics.utils.FBRestResponse;

public interface MenuPricingDAO {

	public List<MenuPricingVo> getMenuPricing( RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public int updateMenuTierPrice(RequestMenuTierPriceUpdate requestMenuTier, String userName) throws SQLException,Exception;
	public List<StoreTierVo> getStoreTierView(RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public FBRestResponse updateStoreTier(String proposedTier, Integer storeCode, String userName) throws SQLException,Exception;
	public List<StoreTierVo> getOtherStoreView(RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public OverAllImpactsVo getOverAllImpacts() throws SQLException,Exception;

}
