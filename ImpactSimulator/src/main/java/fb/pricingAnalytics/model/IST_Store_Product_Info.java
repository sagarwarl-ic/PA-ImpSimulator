package fb.pricingAnalytics.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IST_Store_Product_Info",schema="ImpactSimulator.dbo")

public class IST_Store_Product_Info implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="IstStoreProductId")
	private BigInteger istStoreProductId;
	
	@Column(name="BrandId")
	private int brandId;
	
	@Id
	@Column(name="Project_Id")
	private BigInteger projectId;
	
	@Id
	@Column(name="Store_Code")
	private Integer storeCode;
	
	@Id
	@Column(name="Product_ID")
	private String productId;
	
	@Id
	@Column(name="Product_Name")
	private String productName;
	
	@Column(name="Cat1")
	private String Cat1;
	
	@Column(name="Cat2")
	private String Cat2;
	
	@Column(name="Cat3")
	private String Cat3;
	
	@Column(name="Current_Tier")
	private String Current_Tier;
	
	@Column(name="Sales_Gross_TY")
	private BigDecimal Sales_Gross_TY;
	
	@Column(name="Quantity_TY")
	private int Quantity_TY;
	
	@Column(name="Sales_Gross_LY")
	private BigDecimal Sales_Gross_LY;
	
	@Column(name="Quantity_LY")
	private int Quantity_LY;
	
	@Column(name="Transaction_TY")
	private int Transaction_TY;
	
	@Column(name="Current_Price")
	private float Current_Price;
	
/*	@Column(name="Product_Price")
	private float Product_Price;
	
	@Column(name="New_Price")
	private float New_Price;*/
	
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
	
	@Column(name="CreatedOn")
	private Date createdOn;
	
	@Column(name="CreatedBy")
	private String createdBy;

	public BigInteger getIstStoreProductId() {
		return istStoreProductId;
	}

	public void setIstStoreProductId(BigInteger istStoreProductId) {
		this.istStoreProductId = istStoreProductId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public BigInteger getProjectId() {
		return projectId;
	}

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}

	public Integer getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(Integer storeCode) {
		this.storeCode = storeCode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public int getTransaction_TY() {
		return Transaction_TY;
	}

	public void setTransaction_TY(int transaction_TY) {
		Transaction_TY = transaction_TY;
	}

	public float getCurrent_Price() {
		return Current_Price;
	}

	public void setCurrent_Price(float current_Price) {
		Current_Price = current_Price;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	

}
