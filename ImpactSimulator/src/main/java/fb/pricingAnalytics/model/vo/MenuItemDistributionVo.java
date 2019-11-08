package fb.pricingAnalytics.model.vo;

import java.math.BigInteger;

public class MenuItemDistributionVo {
	
	private String Product_Price_Sensitivity;
	
	private BigInteger Product_Count;

	public MenuItemDistributionVo(String product_Price_Sensitivity,
			BigInteger product_Count) {
		super();
		Product_Price_Sensitivity = product_Price_Sensitivity;
		Product_Count = product_Count;
	}

	public String getProduct_Price_Sensitivity() {
		return Product_Price_Sensitivity;
	}

	public void setProduct_Price_Sensitivity(String product_Price_Sensitivity) {
		Product_Price_Sensitivity = product_Price_Sensitivity;
	}

	public BigInteger getProduct_Count() {
		return Product_Count;
	}

	public void setProduct_Count(BigInteger product_Count) {
		Product_Count = product_Count;
	}
	
	

}
