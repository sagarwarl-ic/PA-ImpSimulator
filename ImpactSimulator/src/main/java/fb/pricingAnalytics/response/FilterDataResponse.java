package fb.pricingAnalytics.response;

import fb.pricingAnalytics.model.vo.FilterData;
import fb.pricingAnalytics.utils.FBRestResponse;

public class FilterDataResponse  extends FBRestResponse{
	
	FilterData FilterData;

	public FilterData getFilterData() {
		return FilterData;
	}

	public void setFilterData(FilterData filterData) {
		FilterData = filterData;
	}

}
