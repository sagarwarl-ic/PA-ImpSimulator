package fb.pricingAnalytics.controller;

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
import org.springframework.web.bind.annotation.RestController;

import fb.pricingAnalytics.model.auth.UserAuth;
import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.model.vo.OverAllImpactsVo;
import fb.pricingAnalytics.model.vo.StoreTierVo;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;
import fb.pricingAnalytics.request.UpdateStoreInfoRequest;
import fb.pricingAnalytics.response.MenuPricingResponse;
import fb.pricingAnalytics.response.OverAllImpactsResponse;
import fb.pricingAnalytics.response.StoreTierResponse;
import fb.pricingAnalytics.service.MenuPricingService;
import fb.pricingAnalytics.utils.AuthUtils;
import fb.pricingAnalytics.utils.FBConstants;
import fb.pricingAnalytics.utils.FBRestResponse;

@RestController
@RequestMapping("/pp/scenario")
public class MenuPricingController {

	private static Logger logger = LoggerFactory.getLogger(MenuPricingController.class);

	@Autowired
	MenuPricingService menuPricingService;


	@RequestMapping(value = "/getMenuPricing", method = RequestMethod.POST)
	public ResponseEntity<?> getMenuPricing(HttpServletRequest request,	@RequestBody RequestPricePlanner requestPricePlanner) {

		logger.debug("MenuPricingController getMenuPricing function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		
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
	
	@RequestMapping(value = "/getStoreTierView", method = RequestMethod.POST)
	public ResponseEntity<?> getStoreTierView(HttpServletRequest request,@RequestBody RequestPricePlanner requestPricePlanner) {

		logger.debug("MenuPricingController getMenuPricing function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		
		@SuppressWarnings("unchecked")
		StoreTierResponse response = new StoreTierResponse();
		try {
			List<StoreTierVo> list = menuPricingService.getStoreTierView(requestPricePlanner);
			response.setStoreTier(list);
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

		return new ResponseEntity<StoreTierResponse>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/updateStoreTier", method = RequestMethod.POST)
	public ResponseEntity<?> updateStoreTier(HttpServletRequest request, @RequestBody UpdateStoreInfoRequest updateStoreInfoRequest) {

		logger.debug("MenuPricingController getMenuPricing function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		FBRestResponse response = null;
		Integer storeCode = updateStoreInfoRequest.getStoreCode();
		String proposedTier = updateStoreInfoRequest.getProposedTier();
		try {
			response = menuPricingService.updateStoreTier(proposedTier,storeCode,userAuth.getUserName());
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

		return new ResponseEntity<FBRestResponse>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/getOverAllImpacts", method = RequestMethod.GET)
	public ResponseEntity<?> getOverAllImpacts(HttpServletRequest request) {

		logger.debug("MenuPricingController getOverAllImpacts function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		
		@SuppressWarnings("unchecked")
		OverAllImpactsResponse response = new OverAllImpactsResponse();
		try {
			OverAllImpactsVo overAllImpactsVo = menuPricingService.getOverAllImpacts();
			response.setOverAllImpact(overAllImpactsVo);;
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
		response.setResponse(true, FBConstants.SUCCESS);
		return new ResponseEntity<OverAllImpactsResponse>(response, HttpStatus.OK);

	}
	

	/*@RequestMapping(value = "/getOtherStoreView", method = RequestMethod.POST)
	public ResponseEntity<?> getOtherStoreView(HttpServletRequest request,@RequestBody RequestPricePlanner requestPricePlanner) {

		logger.debug("MenuPricingController getMenuPricing function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		
		@SuppressWarnings("unchecked")
		StoreTierResponse response = new StoreTierResponse();
		try {
			List<StoreTierVo> list = menuPricingService.getStoreTierView(requestPricePlanner);
			response.setStoreTier(list);
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

		return new ResponseEntity<StoreTierResponse>(response, HttpStatus.OK);

	}*/

}
