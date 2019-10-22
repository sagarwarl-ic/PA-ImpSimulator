package fb.pricingAnalytics.service;

import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.MenuPricingVo;

public interface MenuPricingService {
	
	public List<MenuPricingVo> getMenuPricing() throws SQLException,Exception;

}
