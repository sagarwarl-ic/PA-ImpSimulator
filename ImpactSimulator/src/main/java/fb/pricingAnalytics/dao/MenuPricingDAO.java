package fb.pricingAnalytics.dao;

import java.sql.SQLException;
import java.util.List;

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
import fb.pricingAnalytics.utils.FBRestResponse;

public interface MenuPricingDAO {

	//public List<MenuPricingVo> getMenuPricing( RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public MenuPricingResponse getMenuPricing( RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public int updateMenuTierPrice(RequestMenuTierPriceUpdate requestMenuTier, String userName) throws SQLException,Exception;
	public StoreTierResponse getStoreTierView(RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public FBRestResponse updateStoreTier(UpdateStoreInfoRequest updateStoreInfoRequest, String userName) throws SQLException,Exception;
	public List<StoreTierVo> getOtherStoreView(RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public OverAllImpactsVo getOverAllImpacts(RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
	public List<StoreDistributionVo> getStoreDistribution(RequestPricePlanner requestPricePlanner)throws SQLException,Exception;
	public List<MenuItemDistributionVo> getMenuItemDistribution(RequestPricePlanner requestPricePlanner)throws SQLException,Exception;
	public FilterData getFilterData(RequestPricePlanner requestPricePlanner)throws SQLException,Exception;
	public List<Object> getFilterData(String filterParam)throws SQLException,Exception;
	public FBRestResponse updateStores(List<UpdateStoreInfoRequest> updateStoreInfoRequest,String userName, int tenantId)throws SQLException,Exception;
	public FBRestResponse updateMenuTierPrices(List<RequestMenuTierPriceUpdate> menuTierPriceUpdateReq,int tenantId, String userName)throws SQLException,Exception;

}
