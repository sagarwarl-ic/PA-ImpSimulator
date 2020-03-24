package fb.pricingAnalytics.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DataEntry",schema="ImpactSimulator.dbo")
public class DataEntry {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private BigInteger id;
	
	@Column(name="Quantity_and_Sales_Min_Date")
	private Date quantity_And_Sales_Min_Date;
	
	@Column(name="Quantity_and_Sales_Max_Date")
	private Date quantity_and_Sales_Max_Date;
	
	@Column(name="Product_List_Min_Date")
	private Date product_List_Min_Date;
	
	@Column(name="Product_List_Max_Date")
	private Date product_List_Max_Date;
	
	@Column(name="Current_Avg_Price_Min_Date")
	private Date current_Avg_Price_Min_Date;
	
	@Column(name="Current_Avg_Price_Max_Date")
	private Date current_Avg_Price_Max_Date;
	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Date getQuantity_And_Sales_Min_Date() {
		return quantity_And_Sales_Min_Date;
	}

	public void setQuantity_And_Sales_Min_Date(Date quantity_And_Sales_Min_Date) {
		this.quantity_And_Sales_Min_Date = quantity_And_Sales_Min_Date;
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
