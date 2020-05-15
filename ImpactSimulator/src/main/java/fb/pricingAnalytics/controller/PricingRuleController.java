package fb.pricingAnalytics.controller;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fb.pricingAnalytics.model.auth.UserAuth;
import fb.pricingAnalytics.model.vo.ScenarioMenuPricingRuleVo;
import fb.pricingAnalytics.request.ApplyRuleRequest;
import fb.pricingAnalytics.request.PricingRuleRequest;
import fb.pricingAnalytics.response.ApplyRulesStatusListResponse;
import fb.pricingAnalytics.response.OperatorListResponse;
import fb.pricingAnalytics.response.PricingRulesListResponse;
import fb.pricingAnalytics.response.RuleCreationResponse;
import fb.pricingAnalytics.service.PricingRuleService;
import fb.pricingAnalytics.utils.AuthUtils;
import fb.pricingAnalytics.utils.FBRestResponse;

@RestController
@RequestMapping("/pp/pricingrule")
public class PricingRuleController {
	
	private static Logger logger = LoggerFactory.getLogger(PricingRuleController.class);
	
	@Autowired
	PricingRuleService pricingRuleService;
	
	@RequestMapping(value = "/applyMenuRules", method = RequestMethod.POST)
	public ResponseEntity<?> applyMenuRules(HttpServletRequest request , @RequestBody List<ApplyRuleRequest> applyRules){

		logger.debug("PricingRuleController applyMenuRules function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		String userName = userAuth.getUserName();

		logger.info("Brand Id ::: " + brandId + " UserName  ::: " + userName);
		if ((null == applyRules) || applyRules.isEmpty()) {
			return new ResponseEntity<>(new FBRestResponse(false, "ApplyRuleRequest object is not present"),
					HttpStatus.BAD_REQUEST);
		}
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();

		try {
			response = pricingRuleService.applyMenuRules(brandId, applyRules, userName);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		response.setMessage("success");
		response.setSuccessFlag(true);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/applyrules", method = RequestMethod.POST)
	public ResponseEntity<?> applyPricingRules(HttpServletRequest request,
			@RequestBody List<ApplyRuleRequest> applyRules) {

		logger.debug("PricingRuleController applyPricingRules function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		String userName = userAuth.getUserName();
		logger.info("Brand Id ::: " + brandId + " UserName  ::: " + userName);
		if ((null == applyRules) || applyRules.isEmpty()) {
			return new ResponseEntity<>(new FBRestResponse(false, "ApplyRuleRequest object is not present"),
					HttpStatus.BAD_REQUEST);
		}
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		try {
			response = pricingRuleService.applyPricingRules(brandId, applyRules, userName);
			response.setMessage("success");
			response.setSuccessFlag(true);
		} catch (Exception ex) {
			return new ResponseEntity<>(new FBRestResponse(true, "exception occured"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/createMenuRule", method = RequestMethod.POST)
	public ResponseEntity<?> createMenuRule(HttpServletRequest request,
			@RequestBody ScenarioMenuPricingRuleVo pricingRuleRequest) {

		logger.debug("PricingRuleController createMenuRule function starts :::");

		if (null == pricingRuleRequest) {
			return new ResponseEntity<>(new FBRestResponse(false, "Request Object is null"), HttpStatus.BAD_REQUEST);
		}

		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		String userName = userAuth.getUserName();
		logger.info("Brand Id ::: " + brandId + " UserName  ::: " + userName);

		/*
		 * adding Lines for testing
		 */

		brandId = 1036;
		userName = "abhinavAU";

		/*
		 * ending lines for testing
		 */
		RuleCreationResponse response = new RuleCreationResponse();

		try {
			BigInteger pricingRuleId = pricingRuleService.createScenarioMenuPricingRule(pricingRuleRequest, brandId,
					userName);
			if (pricingRuleId.intValue() == 0) {
				return new ResponseEntity<>(new FBRestResponse(true, "SQL exception occured"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setRuleId(pricingRuleId);
			response.setSuccessFlag(true);
			response.setMessage("success");
		} catch (Exception e) {
			return new ResponseEntity<>(new FBRestResponse(false, "Exception Occured, Please check the log files"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<FBRestResponse>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/createPricingRule", method = RequestMethod.POST)
	public ResponseEntity<?> createPricingRule(HttpServletRequest request,
			@RequestBody PricingRuleRequest pricingRuleRequest) {

		logger.debug("PricingRuleController createPricingRule function starts :::");

		if (null == pricingRuleRequest) {
			return new ResponseEntity<>(new FBRestResponse(false, "Request Object is null"), HttpStatus.BAD_REQUEST);
		}

		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		String userName = userAuth.getUserName();
		logger.info("Brand Id ::: " + brandId + " UserName  ::: " + userName);

		RuleCreationResponse response = new RuleCreationResponse();

		try {
			BigInteger pricingRuleId = pricingRuleService.createPricingRule(pricingRuleRequest, brandId, userName);
			if (pricingRuleId.intValue() == 0) {
				return new ResponseEntity<>(new FBRestResponse(true, "SQL exception occured"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.setRuleId(pricingRuleId);
			response.setSuccessFlag(true);
			response.setMessage("success");
		} catch (Exception e) {
			return new ResponseEntity<>(new FBRestResponse(false, "Exception Occured, Please check the log files"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<FBRestResponse>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/deleteMenuRule", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMenuRule(HttpServletRequest request,
			@RequestBody List<ApplyRuleRequest> deleteRules) {
		
		logger.debug("PricingRuleController deleteMenuRule function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		String userName = userAuth.getUserName();
		logger.info("Brand Id ::: " + brandId + " UserName  ::: " + userName);

		/*
		 * adding Lines for testing
		 */

		brandId = 1036;
		userName = "abhinavAU";

		/*
		 * ending lines for testing
		 */
		if ((null == deleteRules) || deleteRules.isEmpty()) {
			return new ResponseEntity<>(new FBRestResponse(false, "ApplyRuleRequest object is not present"),
					HttpStatus.BAD_REQUEST);
		}
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		try {
			response = pricingRuleService.deleteMenuRules(brandId, deleteRules, userName);
			response.setMessage("success");
			response.setSuccessFlag(true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(new FBRestResponse(true, "exception occured"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}


		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value="/deleteRules",method=RequestMethod.POST)
	public ResponseEntity<?> deletePricingRules(HttpServletRequest request,@RequestBody List<ApplyRuleRequest> deleteRules){
		
		logger.debug("PricingRuleController deletePricingRules function starts :::");
		UserAuth userAuth=AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		String userName = userAuth.getUserName();
		logger.info("Brand Id ::: "+ brandId +" UserName  ::: "+userName);
		if((null == deleteRules) || deleteRules.isEmpty()){
			return new ResponseEntity<>(new FBRestResponse(false, "ApplyRuleRequest object is not present"),
				    HttpStatus.BAD_REQUEST);
		}
		ApplyRulesStatusListResponse response = new ApplyRulesStatusListResponse();
		try{
			response = pricingRuleService.deletePricingRules(brandId,deleteRules,userName);
			response.setMessage("success");
			response.setSuccessFlag(true);
		}catch(Exception ex){
			return new ResponseEntity<>(new FBRestResponse(true, "exception occured"),
				    HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/operators", method = RequestMethod.GET)
	public ResponseEntity<?> getOperatorList(HttpServletRequest request){
		OperatorListResponse opResp = pricingRuleService.getOperatorList();
		return new ResponseEntity<FBRestResponse>(opResp, HttpStatus.OK);
		
	}

	@RequestMapping(value="/menuRules",method=RequestMethod.GET)
	public ResponseEntity<?> getScenarioMenuRules(HttpServletRequest request,@RequestParam("scenarioId") BigInteger scenarioid){
		logger.debug("PricingRuleController getScenarioMenuRules function starts :::");
		PricingRulesListResponse response = new PricingRulesListResponse();
		
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		logger.info("Brand Id ::: " + brandId);
		
		
		/*
		 * adding Lines for testing
		 */

		brandId = 1036;

		/*
		 * ending lines for testing
		 */
		
		
		
		if((null == scenarioid) || (scenarioid.intValue()<=0)){
			return new ResponseEntity<>(new FBRestResponse(false, "ScenarioId is required field"),
				    HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<FBRestResponse>(pricingRuleService.getScenarioMenuRules(scenarioid, brandId),
				HttpStatus.OK);
	}

	@RequestMapping(value="/rules",method=RequestMethod.GET)
	public ResponseEntity<?> getScenarioPricingRules(HttpServletRequest request,@RequestParam("scenarioId") BigInteger scenarioid){
		logger.debug("PricingRuleController getScenarioPricingRules function starts :::");
		PricingRulesListResponse response = new PricingRulesListResponse();
		
		UserAuth userAuth=AuthUtils.getUserAuthData(request);
		int brandId = Integer.valueOf(userAuth.getBrandId());
		logger.info("Brand Id ::: "+ brandId );
		
		if((null == scenarioid) || (scenarioid.intValue()<=0)){
			return new ResponseEntity<>(new FBRestResponse(false, "ScenarioId is required field"),
				    HttpStatus.BAD_REQUEST);
		}
		try{
			response = pricingRuleService.getPricingRulesForScenario(scenarioid,brandId);
		}catch(Exception ex){
			
		}
		return new ResponseEntity<FBRestResponse>(response,HttpStatus.OK);
	}

}
