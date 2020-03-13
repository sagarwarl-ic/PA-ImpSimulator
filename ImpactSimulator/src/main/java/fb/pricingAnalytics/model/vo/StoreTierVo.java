package fb.pricingAnalytics.model.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StoreTierVo {

	private String Store_Sensitivity;
	
	private String Tier_Change;
	
	private String Current_Tier;
	
	private String Market_Name;
	
	private String Pricing_Power;
	
	private String Proposed_Tier;
	
	private Integer Store_Code;
	
	private String Store_Name;
	
	private Double Sales_Impact;
	
	private Double New_Sales;
	
	private Double Sales_Impact_Percentage;
	
	private BigDecimal Original_Sales;
	
	private BigInteger Quantity;
	
	private Integer Original_Transaction;
	
	private Double New_Transaction;
	
	private Double Transaction_Impact;
	
	private Double Transaction_Risk;
	
	private Double Net_Sales_Impact;
	
	private Double Net_Sales_Impact_Percentage;
	
	
	public StoreTierVo(String store_Sensitivity, String tier_Change,
			String current_Tier, String market_Name, String pricing_Power,
			String proposed_Tier, Integer store_Code, String store_Name,
			Double sales_Impact, Double new_Sales,
			Double sales_Impact_Percentage, BigDecimal original_Sales,
			BigInteger quantity,Integer original_Transaction,
			Double new_Transaction, Double transaction_Impact,
			Double transaction_Risk, Double net_Sales_Impact,
			Double net_Sales_Impact_Percentage) {
		super();
		Store_Sensitivity = store_Sensitivity;
		Tier_Change = tier_Change;
		Current_Tier = current_Tier;
		Market_Name = market_Name;
		Pricing_Power = pricing_Power;
		Proposed_Tier = proposed_Tier;
		Store_Code = store_Code;
		Store_Name = store_Name;
		Sales_Impact = sales_Impact;
		New_Sales = new_Sales;
		Sales_Impact_Percentage = sales_Impact_Percentage;
		Original_Sales = original_Sales;
		Quantity = quantity;
		Original_Transaction = original_Transaction;
		New_Transaction = new_Transaction;
		Transaction_Impact = transaction_Impact;
		Transaction_Risk = transaction_Risk;
		Net_Sales_Impact = net_Sales_Impact;
		Net_Sales_Impact_Percentage = net_Sales_Impact_Percentage;
	}


	

	public StoreTierVo(String store_Sensitivity, String tier_Change, String current_Tier, String market_Name, String pricing_Power,
			String proposed_Tier, Integer store_Code, String store_Name, Double sales_Impact, Double new_Sales,
			Double sales_Impact_Percentage, BigDecimal original_Sales, BigInteger quantity) {
		super();
		Store_Sensitivity = store_Sensitivity;
		Tier_Change = tier_Change;
		Current_Tier = current_Tier;
		Market_Name = market_Name;
		Pricing_Power = pricing_Power;
		Proposed_Tier = proposed_Tier;
		Store_Code = store_Code;
		Store_Name = store_Name;
		Sales_Impact = sales_Impact;
		New_Sales = new_Sales;
		Sales_Impact_Percentage = sales_Impact_Percentage;
		Original_Sales = original_Sales;
		Quantity = quantity;
	}
	
	
	

	public StoreTierVo(Integer store_Code,String proposed_Tier,String current_Tier,String store_Sensitivity,
			String pricing_Power ) {
		super();
		Store_Sensitivity = store_Sensitivity;
		Current_Tier = current_Tier;
		Pricing_Power = pricing_Power;
		Proposed_Tier = proposed_Tier;
		Store_Code = store_Code;
	}


	


	


	public String getStore_Sensitivity() {
		return Store_Sensitivity;
	}

	public void setStore_Sensitivity(String store_Sensitivity) {
		Store_Sensitivity = store_Sensitivity;
	}

	public String getTier_Change() {
		return Tier_Change;
	}

	public void setTier_Change(String tier_Change) {
		Tier_Change = tier_Change;
	}

	public String getCurrent_Tier() {
		return Current_Tier;
	}

	public void setCurrent_Tier(String current_Tier) {
		Current_Tier = current_Tier;
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

	public String getProposed_Tier() {
		return Proposed_Tier;
	}

	public void setProposed_Tier(String proposed_Tier) {
		Proposed_Tier = proposed_Tier;
	}

	public Integer getStore_Code() {
		return Store_Code;
	}

	public void setStore_Code(Integer store_Code) {
		Store_Code = store_Code;
	}

	public String getStore_Name() {
		return Store_Name;
	}

	public void setStore_Name(String store_Name) {
		Store_Name = store_Name;
	}

	public Double getSales_Impact() {
		return Sales_Impact;
	}

	public void setSales_Impact(Double sales_Impact) {
		Sales_Impact = sales_Impact;
	}

	public Double getNew_Sales() {
		return New_Sales;
	}

	public void setNew_Sales(Double new_Sales) {
		New_Sales = new_Sales;
	}

	public Double getSales_Impact_Percentage() {
		return Sales_Impact_Percentage;
	}

	public void setSales_Impact_Percentage(Double sales_Impact_Percentage) {
		Sales_Impact_Percentage = sales_Impact_Percentage;
	}

	public BigDecimal getOriginal_Sales() {
		return Original_Sales;
	}

	public void setOriginal_Sales(BigDecimal original_Sales) {
		Original_Sales = original_Sales;
	}

	public BigInteger getQuantity() {
		return Quantity;
	}

	public void setQuantity(BigInteger quantity) {
		Quantity = quantity;
	}

	public Integer getOriginal_Transaction() {
		return Original_Transaction;
	}

	public void setOriginal_Transaction(Integer original_Transaction) {
		Original_Transaction = original_Transaction;
	}

	public Double getNew_Transaction() {
		return New_Transaction;
	}

	public void setNew_Transaction(Double new_Transaction) {
		New_Transaction = new_Transaction;
	}

	public Double getTransaction_Impact() {
		return Transaction_Impact;
	}


	public void setTransaction_Impact(Double transaction_Impact) {
		Transaction_Impact = transaction_Impact;
	}


	public Double getTransaction_Risk() {
		return Transaction_Risk;
	}


	public void setTransaction_Risk(Double transaction_Risk) {
		Transaction_Risk = transaction_Risk;
	}

	public Double getNet_Sales_Impact() {
		return Net_Sales_Impact;
	}

	public void setNet_Sales_Impact(Double net_Sales_Impact) {
		Net_Sales_Impact = net_Sales_Impact;
	}

	public Double getNet_Sales_Impact_Percentage() {
		return Net_Sales_Impact_Percentage;
	}

	public void setNet_Sales_Impact_Percentage(Double net_Sales_Impact_Percentage) {
		Net_Sales_Impact_Percentage = net_Sales_Impact_Percentage;
	}
	

}
