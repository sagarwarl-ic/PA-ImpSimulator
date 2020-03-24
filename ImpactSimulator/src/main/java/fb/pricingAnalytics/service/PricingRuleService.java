package fb.pricingAnalytics.service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.PricingRuleRequest;
import fb.pricingAnalytics.response.ApplyRulesStatusListResponse;
import fb.pricingAnalytics.response.PricingRulesListResponse;
import fb.pricingAnalytics.utils.FBRestResponse;

public interface PricingRuleService {
	
	public BigInteger createPricingRule(PricingRuleRequest pricingRuleRequest,int brandId, String userName) throws SQLException,Exception;

	public PricingRulesListResponse getPricingRulesForScenario(BigInteger scenarioId, int brandId)throws SQLException,Exception;

	public ApplyRulesStatusListResponse applyPricingRules(int brandId,List<ApplyRuleRequest> applyRules, String userName)throws SQLException,Exception;

	public ApplyRulesStatusListResponse deletePricingRules(int brandId,List<ApplyRuleRequest> applyRules, String userName)throws SQLException,Exception;

	//public ProjectPricingRulesResponse getPricingRulesForProject(BigInteger projectId,	int brandId)throws SQLException,Exception;
	

}
