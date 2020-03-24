package fb.pricingAnalytics.model.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

public class MenuPricingVo {

	//@SerializedName("tierChange")
	//private String Tier_Change;
	
	//@SerializedName("cat1")
	private String Cat1;
	
	//@SerializedName("cat2")
	private String Cat2;
	
	//@SerializedName("cat3")
	private String Cat3;
	
	//@SerializedName("currentTier")
	//private String Current_Tier;
	
	//@SerializedName("productID")
	private String Product_ID;
	
	//@SerializedName("productName")
	private String Product_Name;
	
	//@SerializedName("productPriceSensitivity")
	private String Product_Price_Sensitivity;
	
	//@SerializedName("proposedTier")
	private String Proposed_Tier;
	
	//@SerializedName("salesImpact")
	private Double Sales_Impact;
	
	//@SerializedName("newSales")
	private String New_Sales;
	
	//@SerializedName("salesImpactPercentage")
	private Double Sales_Impact_Percentage;
	
	//@SerializedName("originalSales")
	private String Original_Sales;
	
	//@SerializedName("priceChangePercent")
	private Double Price_Change_Percent;
	
	//@SerializedName("priceChange")
	private Double Price_Change;
	
	//@SerializedName("newPrice")
	private Double New_Price;
	
	//@SerializedName("productPrice")
	private Double Product_Price;
	
	//@SerializedName("productPrice")
	private Double Current_Price;
	
	//@SerializedName("quantityTY")
	private String Quantity_TY;
	
	//@SerializedName("salesImpact")
	private BigDecimal Total_Sales_Gross;
	
	
	//@SerializedName("salesImpactPercentage")
	private Double Total_Impact_Percent;
	
	
	private Boolean Is_Changed;
	
	
	/*public MenuPricingVo(String tier_Change, String cat1, String cat2, String cat3,String current_Tier, String product_ID,
			String product_Name, String product_Price_Sensitivity, String proposed_Tier, Double sales_Impact,
			Double new_Sales, Double sales_Impact_Percentage, BigDecimal original_Sales, Double price_Change_Percent,
			Double price_Change, Double new_Price, Double product_Price, BigInteger quantity_TY) {
		super();
		Tier_Change = tier_Change;
		Cat1 = cat1;
		Cat2 = cat2;
		Cat3 = cat3;
		Current_Tier = current_Tier;
		Product_ID = product_ID;
		Product_Name = product_Name;
		Product_Price_Sensitivity = product_Price_Sensitivity;
		Proposed_Tier = proposed_Tier;
		Sales_Impact = sales_Impact;
		New_Sales = new_Sales;
		Sales_Impact_Percentage = sales_Impact_Percentage;
		Original_Sales = original_Sales;
		Price_Change_Percent = price_Change_Percent;
		Price_Change = price_Change;
		New_Price = new_Price;
		Product_Price = product_Price;
		Quantity_TY = quantity_TY;
	}*/
	
	
	/*public MenuPricingVo(String cat1, String cat2, String cat3,
			String product_ID, String product_Name,
			String product_Price_Sensitivity, String proposed_Tier,
			Double sales_Impact, Double new_Sales,
			Double sales_Impact_Percentage, BigDecimal original_Sales,
			Double price_Change_Percent, Double price_Change, Double new_Price,
			Double current_Price, BigInteger quantity_TY) {
		super();
		Cat1 = cat1;
		Cat2 = cat2;
		Cat3 = cat3;
		Product_ID = product_ID;
		Product_Name = product_Name;
		Product_Price_Sensitivity = product_Price_Sensitivity;
		Proposed_Tier = proposed_Tier;
		Sales_Impact = sales_Impact;
		New_Sales = new_Sales;
		Sales_Impact_Percentage = sales_Impact_Percentage;
		Original_Sales = original_Sales;
		Price_Change_Percent = price_Change_Percent;
		Price_Change = price_Change;
		New_Price = new_Price;
		Current_Price = current_Price;
		Quantity_TY = quantity_TY;
	}*/
	
	public MenuPricingVo(String cat1, String cat2, String cat3,
			String product_ID, String product_Name,
			String product_Price_Sensitivity, String proposed_Tier,
			Double sales_Impact, String new_Sales,
			Double sales_Impact_Percentage, String original_Sales,
			Double price_Change_Percent, Double price_Change, Double new_Price,
			Double current_Price, String quantity_TY,Boolean is_Changed,BigDecimal total_Sales_Gross,Double total_Impact_Percent) {
		super();
		Cat1 = cat1;
		Cat2 = cat2;
		Cat3 = cat3;
		Product_ID = product_ID;
		Product_Name = product_Name;
		Product_Price_Sensitivity = product_Price_Sensitivity;
		Proposed_Tier = proposed_Tier;
		Sales_Impact = sales_Impact;
		New_Sales = new_Sales;
		Sales_Impact_Percentage = sales_Impact_Percentage;
		Original_Sales = original_Sales;
		Price_Change_Percent = price_Change_Percent;
		Price_Change = price_Change;
		New_Price = new_Price;
		Current_Price = current_Price;
		Quantity_TY = quantity_TY;
		Total_Sales_Gross = total_Sales_Gross;
		Total_Impact_Percent = total_Impact_Percent;
		Is_Changed = is_Changed;
	}

	

	public MenuPricingVo(String cat1, String cat2, String cat3,
			String product_ID, String proposed_Tier,Double current_Price,String product_Price_Sensitivity
			) {
		super();
		Cat1 = cat1;
		Cat2 = cat2;
		Cat3 = cat3;
		Product_ID = product_ID;
		Product_Price_Sensitivity = product_Price_Sensitivity;
		Proposed_Tier = proposed_Tier;
		Current_Price = current_Price;
	}



	/*public String getTier_Change() {
		return Tier_Change;
	}
	public void setTier_Change(String tier_Change) {
		Tier_Change = tier_Change;
	}*/
	public String getCat1() {
		return Cat1;
	}
	public void setCat1(String cat1) {
		Cat1 = cat1;
	}
	public String getCat2() {
		return Cat2;
	}
	public void setCat2(String cat2) {
		Cat2 = cat2;
	}
	public String getCat3() {
		return Cat3;
	}
	public void setCat3(String cat3) {
		Cat3 = cat3;
	}
	/*public String getCurrent_Tier() {
		return Current_Tier;
	}
	public void setCurrent_Tier(String current_Tier) {
		Current_Tier = current_Tier;
	}*/
	public String getProduct_ID() {
		return Product_ID;
	}
	public void setProduct_ID(String product_ID) {
		Product_ID = product_ID;
	}
	public String getProduct_Name() {
		return Product_Name;
	}
	public void setProduct_Name(String product_Name) {
		Product_Name = product_Name;
	}
	public String getProduct_Price_Sensitivity() {
		return Product_Price_Sensitivity;
	}
	public void setProduct_Price_Sensitivity(String product_Price_Sensitivity) {
		Product_Price_Sensitivity = product_Price_Sensitivity;
	}
	public String getProposed_Tier() {
		return Proposed_Tier;
	}
	public void setProposed_Tier(String proposed_Tier) {
		Proposed_Tier = proposed_Tier;
	}
	public Double getSales_Impact() {
		return Sales_Impact;
	}
	public void setSales_Impact(Double sales_Impact) {
		Sales_Impact = sales_Impact;
	}
	
	public Double getSales_Impact_Percentage() {
		return Sales_Impact_Percentage;
	}
	public void setSales_Impact_Percentage(Double sales_Impact_Percentage) {
		Sales_Impact_Percentage = sales_Impact_Percentage;
	}
	
	public Double getPrice_Change_Percent() {
		return Price_Change_Percent;
	}
	public void setPrice_Change_Percent(Double price_Change_Percent) {
		Price_Change_Percent = price_Change_Percent;
	}
	public Double getPrice_Change() {
		return Price_Change;
	}
	public void setPrice_Change(Double price_Change) {
		Price_Change = price_Change;
	}
	public Double getNew_Price() {
		return New_Price;
	}
	public void setNew_Price(Double new_Price) {
		New_Price = new_Price;
	}
	public Double getProduct_Price() {
		return Product_Price;
	}
	public void setProduct_Price(Double product_Price) {
		Product_Price = product_Price;
	}
	

	public Double getCurrent_Price() {
		return Current_Price;
	}

	public void setCurrent_Price(Double current_Price) {
		Current_Price = current_Price;
	}

	public BigDecimal getTotal_Sales_Gross() {
		return Total_Sales_Gross;
	}

	public void setTotal_Sales_Gross(BigDecimal total_Sales_Gross) {
		Total_Sales_Gross = total_Sales_Gross;
	}

	public Double getTotal_Impact_Percent() {
		return Total_Impact_Percent;
	}

	public void setTotal_Impact_Percent(Double total_Impact_Percent) {
		Total_Impact_Percent = total_Impact_Percent;
	}

	public Boolean getIs_Changed() {
		return Is_Changed;
	}
	public void setIs_Changed(Boolean is_Changed) {
		Is_Changed = is_Changed;
	}



	public String getNew_Sales() {
		return New_Sales;
	}



	public void setNew_Sales(String new_Sales) {
		New_Sales = new_Sales;
	}



	public String getOriginal_Sales() {
		return Original_Sales;
	}



	public void setOriginal_Sales(String original_Sales) {
		Original_Sales = original_Sales;
	}



	public String getQuantity_TY() {
		return Quantity_TY;
	}



	public void setQuantity_TY(String quantity_TY) {
		Quantity_TY = quantity_TY;
	}
	
	

}
