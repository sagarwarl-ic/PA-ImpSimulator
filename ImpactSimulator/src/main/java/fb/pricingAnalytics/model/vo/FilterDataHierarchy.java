package fb.pricingAnalytics.model.vo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FilterDataHierarchy {

	private BigInteger dataEntryId;

	List<MenuFilterHierarchyData> menuFilterHierarchyData = new ArrayList<MenuFilterHierarchyData>();

	List<StoreFilterHierarchyData> storeFilterHierarchyData = new ArrayList<StoreFilterHierarchyData>();


	public BigInteger getDataEntryId() {
		return dataEntryId;
	}

	public List<MenuFilterHierarchyData> getMenuFilterHierarchyData() {
		return menuFilterHierarchyData;
	}


	public List<StoreFilterHierarchyData> getStoreFilterHierarchyData() {
		return storeFilterHierarchyData;
	}

	public void setDataEntryId(BigInteger dataEntryId) {
		this.dataEntryId = dataEntryId;
	}

	public void setMenuFilterHierarchyData(List<MenuFilterHierarchyData> menuFilterHierarchyData) {
		this.menuFilterHierarchyData = menuFilterHierarchyData;
	}

	public void setStoreFilterHierarchyData(List<StoreFilterHierarchyData> storeFilterHierarchyData) {
		this.storeFilterHierarchyData = storeFilterHierarchyData;
	}

}
