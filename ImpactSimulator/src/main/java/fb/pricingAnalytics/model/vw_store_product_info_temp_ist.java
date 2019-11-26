package fb.pricingAnalytics.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vw_store_product_info_temp_ist",schema="ImpactSimulator.EPL")
public class vw_store_product_info_temp_ist implements Serializable {

	@Id
	@Column(name="Store_Code")
	private int Store_Code;
	
	@Id
	@Column(name="Product_ID")
	private String Product_ID;
	
	@Column(name="Product_Name")
	private String Product_Name;
	
	@Column(name="Cat1")
	private String Cat1;
	
	@Column(name="Cat2")
	private String Cat2;
	
	@Column(name="Cat3")
	private String Cat3;
	
	@Column(name="Current_Tier")
	private String Current_Tier;
	
	@Column(name="Proposed_Tier")
	private String Proposed_Tier;
	
	@Column(name="BrandId")
	private BigDecimal Sales_Gross_TY;
	
	@Column(name="Quantity_TY")
	private int Quantity_TY;
	
	@Column(name="Sales_Gross_LY")
	private BigDecimal Sales_Gross_LY;
	
	@Column(name="Quantity_LY")
	private int Quantity_LY;
	
	@Column(name="Product_Price")
	private float Product_Price;
	
	@Column(name="New_Price")
	private float New_Price;
	
	@Column(name="Price_Change_Per")
	private float Price_Change_Per;
	
	@Column(name="Sales_Impact")
	private float Sales_Impact;
	
	@Column(name="Store_Name")
	private String Store_Name;
	
	@Column(name="Market_Name")
	private String Market_Name;
	
	@Column(name="Pricing_Power")
	private String Pricing_Power;
	
	@Column(name="Product_Price_Sensitivity")
	private String Product_Price_Sensitivity;
	
	@Column(name="Store_Sensitivity")
	private float Store_Sensitivity;

	public int getStore_Code() {
		return Store_Code;
	}

	public void setStore_Code(int store_Code) {
		Store_Code = store_Code;
	}

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

	public String getCurrent_Tier() {
		return Current_Tier;
	}

	public void setCurrent_Tier(String current_Tier) {
		Current_Tier = current_Tier;
	}

	public String getProposed_Tier() {
		return Proposed_Tier;
	}

	public void setProposed_Tier(String proposed_Tier) {
		Proposed_Tier = proposed_Tier;
	}

	public BigDecimal getSales_Gross_TY() {
		return Sales_Gross_TY;
	}

	public void setSales_Gross_TY(BigDecimal sales_Gross_TY) {
		Sales_Gross_TY = sales_Gross_TY;
	}

	public int getQuantity_TY() {
		return Quantity_TY;
	}

	public void setQuantity_TY(int quantity_TY) {
		Quantity_TY = quantity_TY;
	}

	public BigDecimal getSales_Gross_LY() {
		return Sales_Gross_LY;
	}

	public void setSales_Gross_LY(BigDecimal sales_Gross_LY) {
		Sales_Gross_LY = sales_Gross_LY;
	}

	public int getQuantity_LY() {
		return Quantity_LY;
	}

	public void setQuantity_LY(int quantity_LY) {
		Quantity_LY = quantity_LY;
	}

	public float getProduct_Price() {
		return Product_Price;
	}

	public void setProduct_Price(float product_Price) {
		Product_Price = product_Price;
	}

	public float getNew_Price() {
		return New_Price;
	}

	public void setNew_Price(float new_Price) {
		New_Price = new_Price;
	}

	public float getPrice_Change_Per() {
		return Price_Change_Per;
	}

	public void setPrice_Change_Per(float price_Change_Per) {
		Price_Change_Per = price_Change_Per;
	}

	public float getSales_Impact() {
		return Sales_Impact;
	}

	public void setSales_Impact(float sales_Impact) {
		Sales_Impact = sales_Impact;
	}

	public String getStore_Name() {
		return Store_Name;
	}

	public void setStore_Name(String store_Name) {
		Store_Name = store_Name;
	}

	public String getMarket_Name() {
		return Market_Name;
	}

	public void setMarket_Name(String market_Name) {
		Market_Name = market_Name;
	}

	public String getPricing_Power() {
		return Pricing_Power;
	}

	public void setPricing_Power(String pricing_Power) {
		Pricing_Power = pricing_Power;
	}

	public String getProduct_Price_Sensitivity() {
		return Product_Price_Sensitivity;
	}

	public void setProduct_Price_Sensitivity(String product_Price_Sensitivity) {
		Product_Price_Sensitivity = product_Price_Sensitivity;
	}

	public float getStore_Sensitivity() {
		return Store_Sensitivity;
	}

	public void setStore_Sensitivity(float store_Sensitivity) {
		Store_Sensitivity = store_Sensitivity;
	}

	@Override
	public String toString() {
		return "MenuPrice [Store_Code=" + Store_Code + ", Product_ID=" + Product_ID + ", Product_Name=" + Product_Name
				+ ", Cat1=" + Cat1 + ", Cat2=" + Cat2 + ", Cat3=" + Cat3 + ", Current_Tier=" + Current_Tier
				+ ", Proposed_Tier=" + Proposed_Tier + ", Sales_Gross_TY=" + Sales_Gross_TY + ", Quantity_TY="
				+ Quantity_TY + ", Sales_Gross_LY=" + Sales_Gross_LY + ", Quantity_LY=" + Quantity_LY
				+ ", Product_Price=" + Product_Price + ", New_Price=" + New_Price + ", Price_Change_Per="
				+ Price_Change_Per + ", Sales_Impact=" + Sales_Impact + ", Store_Name=" + Store_Name + ", Market_Name="
				+ Market_Name + ", Pricing_Power=" + Pricing_Power + ", Product_Price_Sensitivity="
				+ Product_Price_Sensitivity + ", Store_Sensitivity=" + Store_Sensitivity + "]";
	}
	

	
}
