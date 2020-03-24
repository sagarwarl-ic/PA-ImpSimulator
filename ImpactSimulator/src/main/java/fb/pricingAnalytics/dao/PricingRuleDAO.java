package fb.pricingAnalytics.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.PricingRuleRequest;
import fb.pricingAnalytics.response.ApplyRulesStatusResponse;
import fb.pricingAnalytics.response.PricingRulesListResponse;
import fb.pricingAnalytics.utils.FBRestResponse;

public interface PricingRuleDAO {
	
	public BigInteger createPricingRule(PricingRuleRequest pricingRuleRequest,int brandId, String userName) throws SQLException,Exception;

	public PricingRulesListResponse getPricingRulesForScenario(BigInteger scenarioId,int brandId)throws SQLException,Exception;

	public FBRestResponse applyPricingRules(int brandId,List<ApplyRuleRequest> applyRules)throws SQLException,Exception;

	public List<ApplyRulesStatusResponse> revertRules(int brandId,List<ApplyRuleRequest> rulesNotApplicable,String userName)throws SQLException,Exception;

	public List<ApplyRulesStatusResponse> applyRules(int brandId,List<ApplyRuleRequest> rulesApplicable,String userName)throws SQLException,Exception;

	public List<ApplyRulesStatusResponse> deleteRules(int brandId,List<ApplyRuleRequest> deleteRules, String userName);

	

	//public ProjectPricingRulesResponse getPricingRulesForProject(BigInteger projectId,int brandId);

	


}
