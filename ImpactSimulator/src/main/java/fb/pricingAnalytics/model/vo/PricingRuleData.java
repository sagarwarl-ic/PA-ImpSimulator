package fb.pricingAnalytics.model.vo;

import fb.pricingAnalytics.request.MenuItem;
import fb.pricingAnalytics.request.StoreTier;

public class PricingRuleData {
	
	MenuItem menuItem;
	StoreTier storeTier;
	
	public MenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public StoreTier getStoreTier() {
		return storeTier;
	}
	public void setStoreTier(StoreTier storeTier) {
		this.storeTier = storeTier;
	}
	
	

}
