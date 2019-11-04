package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.vo.StoreTierVo;

public class StoreTierResponse {

	int count;
	List<StoreTierVo> storeTier;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<StoreTierVo> getStoreTier() {
		return storeTier;
	}
	public void setStoreTier(List<StoreTierVo> storeTier) {
		this.storeTier = storeTier;
	}
	
	
}
