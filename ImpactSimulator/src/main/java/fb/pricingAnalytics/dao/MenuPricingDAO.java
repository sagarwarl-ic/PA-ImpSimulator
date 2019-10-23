package fb.pricingAnalytics.dao;

import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.request.RequestPricePlanner;

public interface MenuPricingDAO {

	public List<MenuPricingVo> getMenuPricing( RequestPricePlanner requestPricePlanner) throws SQLException,Exception;
}
