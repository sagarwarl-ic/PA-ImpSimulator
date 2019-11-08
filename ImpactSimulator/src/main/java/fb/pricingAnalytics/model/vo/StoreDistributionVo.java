package fb.pricingAnalytics.model.vo;

import java.math.BigInteger;

public class StoreDistributionVo {
	
	private String Store_Sensitivity;
	
	private String Pricing_Power;
	
	private BigInteger Store_Count;

	public StoreDistributionVo(String store_Sensitivity, String pricing_Power,
			BigInteger row) {
		super();
		Store_Sensitivity = store_Sensitivity;
		Pricing_Power = pricing_Power;
		Store_Count = row;
	}

	public String getStore_Sensitivity() {
		return Store_Sensitivity;
	}

	public void setStore_Sensitivity(String store_Sensitivity) {
		Store_Sensitivity = store_Sensitivity;
	}

	public String getPricing_Power() {
		return Pricing_Power;
	}

	public void setPricing_Power(String pricing_Power) {
		Pricing_Power = pricing_Power;
	}

	public BigInteger getStore_Count() {
		return Store_Count;
	}

	public void setStore_Count(BigInteger store_Count) {
		Store_Count = store_Count;
	}
	

}
