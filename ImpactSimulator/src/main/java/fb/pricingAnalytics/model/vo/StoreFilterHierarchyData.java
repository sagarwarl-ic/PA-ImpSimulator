package fb.pricingAnalytics.model.vo;

public class StoreFilterHierarchyData {
	
	private String Current_Tier;
	
	private String Pricing_Power;

	private String Store_Sensitivity;

	public StoreFilterHierarchyData(String current_Tier, String store_Sensitivity, String pricing_Power) {
		super();
		Current_Tier = current_Tier;
		Store_Sensitivity = store_Sensitivity;
		Pricing_Power = pricing_Power;
	}

	public String getCurrent_Tier() {
		return Current_Tier;
	}

	public String getPricing_Power() {
		return Pricing_Power;
	}

	public String getStore_Sensitivity() {
		return Store_Sensitivity;
	}

	public void setCurrent_Tier(String current_Tier) {
		Current_Tier = current_Tier;
	}

	public void setPricing_Power(String pricing_Power) {
		Pricing_Power = pricing_Power;
	}

	public void setStore_Sensitivity(String store_Sensitivity) {
		Store_Sensitivity = store_Sensitivity;
	}
	

}
