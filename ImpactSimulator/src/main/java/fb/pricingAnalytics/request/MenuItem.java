package fb.pricingAnalytics.request;

public class MenuItem {
	
	private String cat1;
	private String cat2;
	private String cat3;
	private String priceSensitivity;
	private String productId;
	private String tier;
	
	public String getCat1() {
		return cat1;
	}

	public String getCat2() {
		return cat2;
	}
	public String getCat3() {
		return cat3;
	}
	public String getPriceSensitivity() {
		return priceSensitivity;
	}
	public String getProductId() {
		return productId;
	}
	public String getTier() {
		return tier;
	}
	public void setCat1(String cat1) {
		this.cat1 = cat1;
	}
	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}
	public void setCat3(String cat3) {
		this.cat3 = cat3;
	}
	public void setPriceSensitivity(String priceSensitivity) {
		this.priceSensitivity = priceSensitivity;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}

	@Override
	public String toString() {
		return "MenuItem [cat1=" + cat1 + ", cat2=" + cat2 + ", cat3=" + cat3 + ", tier=" + tier + ", priceSensitivity="
				+ priceSensitivity + "]";
	}
	
	

}
