package fb.pricingAnalytics.service;

import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.request.RequestPricePlanner;

public interface MenuPricingService {
	
	public List<MenuPricingVo> getMenuPricing(RequestPricePlanner requestPricePlanner) throws SQLException,Exception;

}
