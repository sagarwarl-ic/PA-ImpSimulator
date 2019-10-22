package fb.pricingAnalytics.dao;

import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.MenuPricingVo;

public interface MenuPricingDAO {

	public List<MenuPricingVo> getMenuPricing() throws SQLException,Exception;
}
