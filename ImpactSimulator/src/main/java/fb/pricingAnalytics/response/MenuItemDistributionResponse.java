package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.vo.MenuItemDistributionVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class MenuItemDistributionResponse extends FBRestResponse{
	
	List<MenuItemDistributionVo> menuItemDistributionVo;

	public List<MenuItemDistributionVo> getMenuItemDistributionVo() {
		return menuItemDistributionVo;
	}

	public void setMenuItemDistributionVo(
			List<MenuItemDistributionVo> menuItemDistributionVo) {
		this.menuItemDistributionVo = menuItemDistributionVo;
	}
	
	

}
