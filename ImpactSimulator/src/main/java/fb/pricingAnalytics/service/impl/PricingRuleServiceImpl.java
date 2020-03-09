package fb.pricingAnalytics.service.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fb.pricingAnalytics.dao.PricingRuleDAO;
import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.PricingRuleRequest;
import fb.pricingAnalytics.response.ApplyRulesStatusListResponse;
import fb.pricingAnalytics.response.ApplyRulesStatusResponse;
import fb.pricingAnalytics.response.PricingRulesListResponse;
import fb.pricingAnalytics.service.PricingRuleService;


@Service
public class PricingRuleServiceImpl implements PricingRuleService{
	
	
	@Autowired
	PricingRuleDAO pricingRuleDao;

	@Transactional
	@Override
	public BigInteger createPricingRule(PricingRuleRequest pricingRuleRequest,int brandId, String userName) throws SQLException, Exception {
		return pricingRuleDao.createPricingRule( pricingRuleRequest, brandId,  userName);
	}

	@Transactional
	@Override
	public PricingRulesListResponse getPricingRulesForScenario(BigInteger scenarioId,int brandId) throws SQLException, Exception {
		return pricingRuleDao.getPricingRulesForScenario(scenarioId,brandId);
	}

	@Transactional
	@Override
	public ApplyRulesStatusListResponse applyPricingRules(int brandId,List<ApplyRuleRequest> applyRules,String userName) throws SQLException, Exception {
		
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		List<ApplyRulesStatusResponse> responseList = new ArrayList<ApplyRulesStatusResponse>();
		
		List<ApplyRuleRequest> rulesApplicable =  applyRules.stream().filter(r->r.isApplied()==true).collect(Collectors.toList());
		List<ApplyRuleRequest> rulesNotApplicable =  applyRules.stream().filter(r->r.isApplied()==false).collect(Collectors.toList());
		
		List<ApplyRulesStatusResponse> revertRulesResponse = pricingRuleDao.revertRules(brandId,rulesNotApplicable,userName);
		responseList.addAll(revertRulesResponse);
			
		List<ApplyRulesStatusResponse> applyrulesResponse  = pricingRuleDao.applyRules(brandId,rulesApplicable,userName);
		responseList.addAll(applyrulesResponse);
		response.getApplyRulesStatusResponse().addAll(responseList);
		
		return response;
	}

	@Transactional
	@Override
	public ApplyRulesStatusListResponse deletePricingRules(int brandId,List<ApplyRuleRequest> deleteRules, String userName)throws SQLException, Exception {
		
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		List<ApplyRulesStatusResponse> responseList = new ArrayList<ApplyRulesStatusResponse>();
		List<ApplyRulesStatusResponse> revertRulesResponse = pricingRuleDao.deleteRules(brandId,deleteRules,userName);
		responseList.addAll(revertRulesResponse);
		response.getApplyRulesStatusResponse().addAll(responseList);
		return response;
	}

	/*@Override
	public ProjectPricingRulesResponse getPricingRulesForProject(BigInteger projectId,int brandId) throws SQLException, Exception {
		return pricingRuleDao.getPricingRulesForProject(projectId,brandId);
	}*/

}
