package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class MenuPricingResponse extends FBRestResponse{
	
	int count;
	List<MenuPricingVo> menuPrice;
	
	public MenuPricingResponse() {
		super();
	}

	public MenuPricingResponse(int count, List<MenuPricingVo> menuPrice) {
		super();
		this.count = count;
		this.menuPrice = menuPrice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<MenuPricingVo> getMenuPrice() {
		return menuPrice;
	}

	public void setMenuPrice(List<MenuPricingVo> menuPrice) {
		this.menuPrice = menuPrice;
	}

	
	
}
