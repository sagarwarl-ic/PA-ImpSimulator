package fb.pricingAnalytics.response;

import fb.pricingAnalytics.model.vo.FilterDataHierarchy;
import fb.pricingAnalytics.utils.FBRestResponse;

public class FilterDataHierarchyResponse  extends FBRestResponse{
	
	FilterDataHierarchy filterDataHierarchy;

	public FilterDataHierarchy getFilterDataHierarchy() {
		return filterDataHierarchy;
	}

	public void setFilterDataHierarchy(FilterDataHierarchy filterDataHierarchy) {
		this.filterDataHierarchy = filterDataHierarchy;
	}

}
