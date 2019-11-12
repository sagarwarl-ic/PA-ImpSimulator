package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.utils.FBRestResponse;

public class FilterDataIndividualResponse extends FBRestResponse{
	
	
	List<Object> filterData;

	public List<Object> getFilterData() {
		return filterData;
	}

	public void setFilterData(List<Object> filterData) {
		this.filterData = filterData;
	}
	
	

}
