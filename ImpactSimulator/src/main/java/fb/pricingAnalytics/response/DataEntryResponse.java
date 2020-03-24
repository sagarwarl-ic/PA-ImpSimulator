package fb.pricingAnalytics.response;

import fb.pricingAnalytics.model.vo.DataEntryVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class DataEntryResponse  extends FBRestResponse{
	
	private DataEntryVo dataEntry ;

	public DataEntryVo getDataEntry() {
		return dataEntry;
	}

	public void setDataEntry(DataEntryVo dataEntry) {
		this.dataEntry = dataEntry;
	}
	
	

}
