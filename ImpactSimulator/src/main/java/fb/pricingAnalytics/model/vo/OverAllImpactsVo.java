package fb.pricingAnalytics.model.vo;

public class OverAllImpactsVo {

	private Double transaction_Risk;
	
	private String sales_Impact;
	
	private Double sales_Impact_Percent;
	
	private Double net_Impact_Percent;
	
	

	public OverAllImpactsVo(Double transaction_Risk, String sales_Impact,
			Double sales_Impact_Percent, Double net_Impact_Percent) {
		super();
		this.transaction_Risk = transaction_Risk;
		this.sales_Impact = sales_Impact;
		this.sales_Impact_Percent = sales_Impact_Percent;
		this.net_Impact_Percent = net_Impact_Percent;
	}

	public Double getTransaction_Risk() {
		return transaction_Risk;
	}

	public void setTransaction_Risk(Double transaction_Risk) {
		this.transaction_Risk = transaction_Risk;
	}

	public String getSales_Impact() {
		return sales_Impact;
	}

	public void setSales_Impact(String sales_Impact) {
		this.sales_Impact = sales_Impact;
	}

	public Double getSales_Impact_Percent() {
		return sales_Impact_Percent;
	}

	public void setSales_Impact_Percent(Double sales_Impact_Percent) {
		this.sales_Impact_Percent = sales_Impact_Percent;
	}

	public Double getNet_Impact_Percent() {
		return net_Impact_Percent;
	}

	public void setNet_Impact_Percent(Double net_Impact_Percent) {
		this.net_Impact_Percent = net_Impact_Percent;
	}
	
	
	

}
