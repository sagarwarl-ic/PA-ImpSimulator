package fb.pricingAnalytics.model.vo;

import fb.pricingAnalytics.request.MenuItem;
import fb.pricingAnalytics.request.StoreTier;

public class PricingRuleData {
	
	MenuItem menuItem;
	StoreTier storeTier;
	
	public MenuItem getMenuItem() {
		return menuItem;
	}
	public StoreTier getStoreTier() {
		return storeTier;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public void setStoreTier(StoreTier storeTier) {
		this.storeTier = storeTier;
	}

	@Override
	public String toString() {
		return "PricingRuleData [menuItem=" + menuItem + ", storeTier=" + storeTier + "]";
	}
	
	

}
