package fb.pricingAnalytics.request;

public class UpdateStoreInfoRequest {
	
	private Integer storeCode;
	private String proposedTier;
	
	
	public Integer getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(Integer storeCode) {
		this.storeCode = storeCode;
	}
	public String getProposedTier() {
		return proposedTier;
	}
	public void setProposedTier(String proposedTier) {
		this.proposedTier = proposedTier;
	}
	
	
	

}
