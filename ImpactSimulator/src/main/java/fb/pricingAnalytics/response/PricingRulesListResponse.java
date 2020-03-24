package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.vo.PricingRuleVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class PricingRulesListResponse extends FBRestResponse {
	
	int count_PricingRules;
	List<PricingRuleVo> PricingRules;
	
	public int getCount_PricingRules() {
		return count_PricingRules;
	}
	public void setCount_PricingRules(int count_PricingRules) {
		this.count_PricingRules = count_PricingRules;
	}
	public List<PricingRuleVo> getPricingRules() {
		return PricingRules;
	}
	public void setPricingRules(List<PricingRuleVo> pricingRules) {
		PricingRules = pricingRules;
	}
	
	
}
