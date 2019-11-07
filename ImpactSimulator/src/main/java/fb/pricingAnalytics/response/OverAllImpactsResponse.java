package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.vo.OverAllImpactsVo;


public class OverAllImpactsResponse {
	
	
	List<OverAllImpactsVo> overAllImpact;
	
	
	public List<OverAllImpactsVo> getOverAllImpacts() {
		return overAllImpact;
	}
	public void setOverAllImpacts(List<OverAllImpactsVo> overAllImpact) {
		this.overAllImpact = overAllImpact;
	}
	
	

}
