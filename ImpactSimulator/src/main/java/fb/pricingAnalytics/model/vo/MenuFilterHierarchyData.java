package fb.pricingAnalytics.model.vo;

public class MenuFilterHierarchyData {
	
	private String  Cat1;
	
	private String  Cat2;
	
	private String Cat3;
	
	private String Current_Tier;
	
	private String Product_Price_Sensitivity;

	public MenuFilterHierarchyData(String cat1, String cat2, String cat3, String current_Tier,
			String product_Price_Sensitivity) {
		super();
		Cat1 = cat1;
		Cat2 = cat2;
		Cat3 = cat3;
		Current_Tier = current_Tier;
		Product_Price_Sensitivity = product_Price_Sensitivity;
	}

	public String getCat1() {
		return Cat1;
	}

	public String getCat2() {
		return Cat2;
	}

	public String getCat3() {
		return Cat3;
	}

	public String getCurrent_Tier() {
		return Current_Tier;
	}

	public String getProduct_Price_Sensitivity() {
		return Product_Price_Sensitivity;
	}

	public void setCat1(String cat1) {
		Cat1 = cat1;
	}

	public void setCat2(String cat2) {
		Cat2 = cat2;
	}

	public void setCat3(String cat3) {
		Cat3 = cat3;
	}

	public void setCurrent_Tier(String current_Tier) {
		Current_Tier = current_Tier;
	}

	public void setProduct_Price_Sensitivity(String product_Price_Sensitivity) {
		Product_Price_Sensitivity = product_Price_Sensitivity;
	}
	

}
