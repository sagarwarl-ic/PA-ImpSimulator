package fb.pricingAnalytics.response;

import fb.pricingAnalytics.model.vo.OverAllImpactsVo;
import fb.pricingAnalytics.utils.FBRestResponse;


public class OverAllImpactsResponse extends FBRestResponse{
	
	
	OverAllImpactsVo overAllImpact;

	public OverAllImpactsVo getOverAllImpact() {
		return overAllImpact;
	}

	public void setOverAllImpact(OverAllImpactsVo overAllImpact) {
		this.overAllImpact = overAllImpact;
	}
	
	
	
	
	

}
