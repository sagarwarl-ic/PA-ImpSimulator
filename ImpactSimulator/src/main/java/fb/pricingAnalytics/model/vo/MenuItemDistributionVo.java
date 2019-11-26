package fb.pricingAnalytics.model.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MenuItemDistributionVo {
	
	private String Product_Price_Sensitivity;
	
	private BigInteger Product_Count;
	
	private int Quantity;
	
	private BigDecimal Original_Sales;
	
	

	public MenuItemDistributionVo(String product_Price_Sensitivity,
			BigInteger product_Count) {
		super();
		Product_Price_Sensitivity = product_Price_Sensitivity;
		Product_Count = product_Count;
	}
	
	

	public MenuItemDistributionVo(String product_Price_Sensitivity,
			BigInteger product_Count, int quantity, BigDecimal original_Sales) {
		super();
		Product_Price_Sensitivity = product_Price_Sensitivity;
		Product_Count = product_Count;
		Quantity = quantity;
		Original_Sales = original_Sales;
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

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public BigDecimal getOriginal_Sales() {
		return Original_Sales;
	}

	public void setOriginal_Sales(BigDecimal original_Sales) {
		Original_Sales = original_Sales;
	}
	
	

}
