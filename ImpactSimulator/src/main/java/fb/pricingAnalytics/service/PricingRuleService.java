package fb.pricingAnalytics.service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.ScenarioMenuPricingRuleVo;
import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.PricingRuleRequest;
import fb.pricingAnalytics.response.ApplyRulesStatusListResponse;
import fb.pricingAnalytics.response.OperatorListResponse;
import fb.pricingAnalytics.response.PricingRulesListResponse;
import fb.pricingAnalytics.response.ScenarioMenuRuleListResponse;

public interface PricingRuleService {
	
	public ApplyRulesStatusListResponse applyMenuRules(int brandId, List<ApplyRuleRequest> applyRules, String userName)
			throws SQLException, Exception;

	public ApplyRulesStatusListResponse applyPricingRules(int brandId,List<ApplyRuleRequest> applyRules, String userName)throws SQLException,Exception;

	public BigInteger createPricingRule(PricingRuleRequest pricingRuleRequest,int brandId, String userName) throws SQLException,Exception;

	public BigInteger createScenarioMenuPricingRule(ScenarioMenuPricingRuleVo pricingRuleRequest, int brandId,
			String userName);

	public ApplyRulesStatusListResponse deleteMenuRules(int brandId, List<ApplyRuleRequest> applyRules,
			String userName) throws SQLException, Exception;

	public ApplyRulesStatusListResponse deletePricingRules(int brandId,List<ApplyRuleRequest> applyRules, String userName)throws SQLException,Exception;

	public OperatorListResponse getOperatorList();

	public PricingRulesListResponse getPricingRulesForScenario(BigInteger scenarioId, int brandId)throws SQLException,Exception;

	public ScenarioMenuRuleListResponse getScenarioMenuRules(BigInteger scenarioId, int brandId);
	
	public ScenarioMenuRuleListResponse applyScenarioMenuRules(BigInteger projectId,BigInteger scenarioId, int brandId,String userName)throws SQLException, Exception ;

	//public ProjectPricingRulesResponse getPricingRulesForProject(BigInteger projectId,	int brandId)throws SQLException,Exception;
	

}
