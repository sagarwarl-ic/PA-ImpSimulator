package fb.pricingAnalytics.model.vo;

import java.util.ArrayList;
import java.util.List;

public class FilterDataHierarchy {

	List<MenuFilterHierarchyData> menuFilterHierarchyData = new ArrayList<MenuFilterHierarchyData>();

	List<StoreFilterHierarchyData> storeFilterHierarchyData = new ArrayList<StoreFilterHierarchyData>();

	public List<MenuFilterHierarchyData> getMenuFilterHierarchyData() {
		return menuFilterHierarchyData;
	}

	public List<StoreFilterHierarchyData> getStoreFilterHierarchyData() {
		return storeFilterHierarchyData;
	}

	public void setMenuFilterHierarchyData(List<MenuFilterHierarchyData> menuFilterHierarchyData) {
		this.menuFilterHierarchyData = menuFilterHierarchyData;
	}

	public void setStoreFilterHierarchyData(List<StoreFilterHierarchyData> storeFilterHierarchyData) {
		this.storeFilterHierarchyData = storeFilterHierarchyData;
	}


}
