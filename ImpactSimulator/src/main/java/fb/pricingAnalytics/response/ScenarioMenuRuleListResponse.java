package fb.pricingAnalytics.response;

import java.util.ArrayList;
import java.util.List;

import fb.pricingAnalytics.model.vo.ScenarioMenuPricingRuleVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class ScenarioMenuRuleListResponse extends FBRestResponse {

	List<ScenarioMenuPricingRuleVo> scenarioMenuPricingRulelist = new ArrayList<>();

	public ScenarioMenuRuleListResponse() {
		super();
	}

	public ScenarioMenuRuleListResponse(boolean successFlag, String message) {
		super(successFlag, message);
	}

	public ScenarioMenuRuleListResponse(List<ScenarioMenuPricingRuleVo> scenarioMenuPricingRulelist) {
		super();
		this.scenarioMenuPricingRulelist = scenarioMenuPricingRulelist;
	}

	public List<ScenarioMenuPricingRuleVo> getScenarioMenuPricingRulelist() {
		return scenarioMenuPricingRulelist;
	}

	public void setScenarioMenuPricingRulelist(List<ScenarioMenuPricingRuleVo> scenarioMenuPricingRulelist) {
		this.scenarioMenuPricingRulelist = scenarioMenuPricingRulelist;
	}


}
