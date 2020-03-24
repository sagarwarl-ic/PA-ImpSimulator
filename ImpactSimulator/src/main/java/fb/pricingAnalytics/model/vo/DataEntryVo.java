package fb.pricingAnalytics.model.vo;

import java.math.BigInteger;
import java.util.Date;

public class DataEntryVo {
	
	BigInteger  dataEntryId;
	Date quantity_and_Sales_Min_Date;
	Date quantity_and_Sales_Max_Date;
	Date product_List_Min_Date;
	Date product_List_Max_Date;
	Date current_Avg_Price_Min_Date;
	Date current_Avg_Price_Max_Date;
	public DataEntryVo(BigInteger dataEntryId,
			Date quantity_and_Sales_Min_Date, Date quantity_and_Sales_Max_Date,
			Date product_List_Min_Date, Date product_List_Max_Date,
			Date current_Avg_Price_Min_Date, Date current_Avg_Price_Max_Date) {
		super();
		this.dataEntryId = dataEntryId;
		this.quantity_and_Sales_Min_Date = quantity_and_Sales_Min_Date;
		this.quantity_and_Sales_Max_Date = quantity_and_Sales_Max_Date;
		this.product_List_Min_Date = product_List_Min_Date;
		this.product_List_Max_Date = product_List_Max_Date;
		this.current_Avg_Price_Min_Date = current_Avg_Price_Min_Date;
		this.current_Avg_Price_Max_Date = current_Avg_Price_Max_Date;
	}
	public BigInteger getDataEntryId() {
		return dataEntryId;
	}
	public void setDataEntryId(BigInteger dataEntryId) {
		this.dataEntryId = dataEntryId;
	}
	public Date getQuantity_and_Sales_Min_Date() {
		return quantity_and_Sales_Min_Date;
	}
	public void setQuantity_and_Sales_Min_Date(Date quantity_and_Sales_Min_Date) {
		this.quantity_and_Sales_Min_Date = quantity_and_Sales_Min_Date;
	}
	public Date getQuantity_and_Sales_Max_Date() {
		return quantity_and_Sales_Max_Date;
	}
	public void setQuantity_and_Sales_Max_Date(Date quantity_and_Sales_Max_Date) {
		this.quantity_and_Sales_Max_Date = quantity_and_Sales_Max_Date;
	}
	public Date getProduct_List_Min_Date() {
		return product_List_Min_Date;
	}
	public void setProduct_List_Min_Date(Date product_List_Min_Date) {
		this.product_List_Min_Date = product_List_Min_Date;
	}
	public Date getProduct_List_Max_Date() {
		return product_List_Max_Date;
	}
	public void setProduct_List_Max_Date(Date product_List_Max_Date) {
		this.product_List_Max_Date = product_List_Max_Date;
	}
	public Date getCurrent_Avg_Price_Min_Date() {
		return current_Avg_Price_Min_Date;
	}
	public void setCurrent_Avg_Price_Min_Date(Date current_Avg_Price_Min_Date) {
		this.current_Avg_Price_Min_Date = current_Avg_Price_Min_Date;
	}
	public Date getCurrent_Avg_Price_Max_Date() {
		return current_Avg_Price_Max_Date;
	}
	public void setCurrent_Avg_Price_Max_Date(Date current_Avg_Price_Max_Date) {
		this.current_Avg_Price_Max_Date = current_Avg_Price_Max_Date;
	}
	
	
	

}
