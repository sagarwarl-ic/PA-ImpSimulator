package fb.pricingAnalytics.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fb.pricingAnalytics.model.auth.UserAuth;
import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;
import fb.pricingAnalytics.response.MenuPricingResponse;
import fb.pricingAnalytics.service.MenuPricingService;
import fb.pricingAnalytics.utils.AuthUtils;
import fb.pricingAnalytics.utils.FBRestResponse;

@RestController
@RequestMapping("/pp/scenario")
public class MenuPricingController {

	private static Logger logger = LoggerFactory.getLogger(MenuPricingController.class);

	@Autowired
	MenuPricingService menuPricingService;

	/** This is to test whether the api is working or not **/
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String testMethod(ModelMap model) {
		return "TestPass";
	}

	@RequestMapping(value = "/getMenuPricing", method = RequestMethod.POST)
	public ResponseEntity<?> getMenuPricing(HttpServletRequest request,
			@RequestBody RequestPricePlanner requestPricePlanner) {

		logger.debug("MenuPricingController getMenuPricing function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		/*
		 * if(null == requestPricePlanner ){ return new
		 * ResponseEntity<FBRestResponse>(new FBRestResponse(false,
		 * "Request Object is null"), HttpStatus.BAD_REQUEST); }
		 * 
		 * SearchPricePlanner search = requestPricePlanner.getSearch(); SortPricePlanner
		 * sort = requestPricePlanner.getSort(); PagingPricePlanner pagination =
		 * requestPricePlanner.getPaging();
		 * 
		 * if(null == pagination) { return new ResponseEntity<FBRestResponse>(new
		 * FBRestResponse(false, FBConstants.PAGINATION_ERROR), HttpStatus.BAD_REQUEST);
		 * }else if(null == pagination.getPageNo() || pagination.getPageNo() == 0 ||
		 * null == pagination.getPageSize() || pagination.getPageSize() == 0){ return
		 * new ResponseEntity<FBRestResponse>(new FBRestResponse(false,
		 * FBConstants.PAGINATION_ERROR), HttpStatus.BAD_REQUEST); }
		 * 
		 * String freeText = search==null ? null: search.getFreeText(); if(search==null)
		 * { return new ResponseEntity<FBRestResponse>(new FBRestResponse(false,
		 * FBConstants.SEARCH_OBJECT_ERROR), HttpStatus.BAD_REQUEST); }else {
		 * logger.info("Search Text  ::: "+freeText +" "+
		 * "Stage ::: "+search.getStage()+" "+
		 * "Last Updated Date ::: "+search.getLastUpdated()+" "+
		 * "Requested Date From ::: "+search.getRequestedDateFrom()+" "+
		 * "Requested Date To ::: "+search.getRequestedDateTo()+" "+
		 * "Available Date From ::: "+search.getAvailableDateFrom()+" "+
		 * "Available Date To ::: "+search.getAvailableDateTo()+" "+
		 * "Pagination Page No ::: "+pagination.getPageNo()+" "+
		 * "Pagination Page Size ::: "+pagination.getPageSize()+" "+
		 * "Sort Direction ::: "+sort.getDirection()+" "+
		 * "Sort Field ::: "+sort.getField()); }
		 * 
		 * 
		 * if(freeText !=null) { boolean checkSpecialChar =
		 * FBUtils.serachTextPattern(freeText);
		 * 
		 * 
		 * This code is written so to check for special characters other than space in
		 * the search text and hence avoid SQL Injection Attach
		 * 
		 * if(checkSpecialChar == true) { return new ResponseEntity<FBRestResponse>(new
		 * FBRestResponse(false, "The search text contains special characters"),
		 * HttpStatus.BAD_REQUEST); } }
		 */

		// return new ResponseEntity<FBRestResponse>(new FBRestResponse(true,
		// FBConstants.PAGINATION_ERROR),
		@SuppressWarnings("unchecked")
		MenuPricingResponse response = new MenuPricingResponse();
		try {
			List<MenuPricingVo> list = menuPricingService.getMenuPricing(requestPricePlanner);
			response.setMenuPrice(list);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(true, "SQL exception occured"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
			return new ResponseEntity<FBRestResponse>(
					new FBRestResponse(false, "Exception Occured, Please check the log files"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		/*
		 * response.setResponse(true, FBConstants.SUCCESS); response.setResult(reports);
		 * response.setReportsCount(reports.size());
		 */
		return new ResponseEntity<MenuPricingResponse>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value="/updateMenuTierPrice", method = RequestMethod.POST)
	public ResponseEntity<?> updateMenuTierPrice(HttpServletRequest request,@RequestBody RequestMenuTierPriceUpdate requestMenuTier) {
		logger.debug("MenuPricingController updateMenuTierPrice function starts :::");
		int updatedRows = -1;
		if(null == requestMenuTier || requestMenuTier.getProductId()== null || requestMenuTier.getTier() == null){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "The request object is NOT correct"),
				    HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Product Id  ::: "+requestMenuTier.getProductId()+" "+
				"Tier  ::: "+requestMenuTier.getTier() +" "+
				"Price ::: "+requestMenuTier.getPrice() );
		
		UserAuth userAuth=AuthUtils.getUserAuthData(request);
		String userName = userAuth.getUserName();
		try {
			updatedRows = menuPricingService.updateMenuTierPrice(requestMenuTier,userName);
		}catch(Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "Exception Occured, Please check the log files"),
				    HttpStatus.BAD_REQUEST);
		}
		if(updatedRows<=0) {
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "No rows updated. Table does not contain the required record"),
				    HttpStatus.BAD_REQUEST);
		}else {
			
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(true, "Price updated Successfully"),
			    HttpStatus.OK);
		}
	}

}
