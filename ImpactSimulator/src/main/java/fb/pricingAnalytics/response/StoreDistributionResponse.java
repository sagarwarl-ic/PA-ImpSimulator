package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.vo.StoreDistributionVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class StoreDistributionResponse extends FBRestResponse{
	
	List<StoreDistributionVo> StoreDistributionVo ;

	public List<StoreDistributionVo> getStoreDistributionVo() {
		return StoreDistributionVo;
	}

	public void setStoreDistributionVo(List<StoreDistributionVo> storeDistributionVo) {
		StoreDistributionVo = storeDistributionVo;
	}
	
	

}
