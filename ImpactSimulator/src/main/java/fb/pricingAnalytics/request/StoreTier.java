package fb.pricingAnalytics.request;


public class StoreTier {
	
	private String currentTier;
	private String pricingPower;
	private String storeSensitivity;
	
	public String getCurrentTier() {
		return currentTier;
	}
	public String getPricingPower() {
		return pricingPower;
	}
	public String getStoreSensitivity() {
		return storeSensitivity;
	}
	public void setCurrentTier(String currentTier) {
		this.currentTier = currentTier;
	}
	public void setPricingPower(String pricingPower) {
		this.pricingPower = pricingPower;
	}
	public void setStoreSensitivity(String storeSensitivity) {
		this.storeSensitivity = storeSensitivity;
	}

	@Override
	public String toString() {
		return "StoreTier [currentTier=" + currentTier + ", storeSensitivity=" + storeSensitivity + ", pricingPower="
				+ pricingPower + "]";
	}
	
	
	
	

}
