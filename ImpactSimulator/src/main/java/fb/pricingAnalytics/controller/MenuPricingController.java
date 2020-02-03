package fb.pricingAnalytics.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fb.pricingAnalytics.events.ImpactSimulatorEvent;
import fb.pricingAnalytics.model.auth.UserAuth;
import fb.pricingAnalytics.model.vo.FilterData;
import fb.pricingAnalytics.model.vo.MenuItemDistributionVo;
import fb.pricingAnalytics.model.vo.OverAllImpactsVo;
import fb.pricingAnalytics.model.vo.StoreDistributionVo;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;
import fb.pricingAnalytics.request.UpdateStoreInfoRequest;
import fb.pricingAnalytics.response.FilterDataIndividualResponse;
import fb.pricingAnalytics.response.FilterDataResponse;
import fb.pricingAnalytics.response.MenuItemDistributionResponse;
import fb.pricingAnalytics.response.MenuPricingResponse;
import fb.pricingAnalytics.response.OverAllImpactsResponse;
import fb.pricingAnalytics.response.StoreDistributionResponse;
import fb.pricingAnalytics.response.StoreTierResponse;
import fb.pricingAnalytics.service.MenuPricingService;
import fb.pricingAnalytics.utils.AuthUtils;
import fb.pricingAnalytics.utils.FBAzureQueuePublisher;
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
		requestPricePlanner.setBrandId(Integer.valueOf(tenantId));
		if(!validateInputRequest(requestPricePlanner)){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "ProjcetId and ScenarioId are required fields"),
					HttpStatus.BAD_REQUEST);
		}
		
		@SuppressWarnings("unchecked")
		MenuPricingResponse response = new MenuPricingResponse();
		try {
			response = menuPricingService.getMenuPricing(requestPricePlanner);
			//response.setMenuPrice(list);
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
		UserAuth userAuth=AuthUtils.getUserAuthData(request);
		String userName = userAuth.getUserName();
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		requestMenuTier.setBrandId(Integer.valueOf(tenantId));
		if(null == requestMenuTier || requestMenuTier.getProductId()== null || requestMenuTier.getTier() == null || 
				requestMenuTier.getProject_Id()==null || requestMenuTier.getScenario_Id()==null){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "The request object is NOT correct"),
				    HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Product Id  ::: "+requestMenuTier.getProductId()+" "+
				"Tier  ::: "+requestMenuTier.getTier() +" "+
				"Price ::: "+requestMenuTier.getPrice() );
		
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
		requestPricePlanner.setBrandId(Integer.valueOf(tenantId));
		if(!validateInputRequest(requestPricePlanner)){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "ProjcetId and ScenarioId are required fields"),
					HttpStatus.BAD_REQUEST);
		}
		@SuppressWarnings("unchecked")
		StoreTierResponse response = new StoreTierResponse();
		try {
			response = menuPricingService.getStoreTierView(requestPricePlanner);
			//response.setStoreTier(list);
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
		updateStoreInfoRequest.setBrandId(Integer.valueOf(tenantId));
		FBRestResponse response = null;
		
		if(null == updateStoreInfoRequest || updateStoreInfoRequest.getStoreCode()== null || updateStoreInfoRequest.getProposedTier()== null || 
				updateStoreInfoRequest.getProject_Id()==null || updateStoreInfoRequest.getScenario_Id()==null){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "The request object is NOT correct"),
				    HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			response = menuPricingService.updateStoreTier(updateStoreInfoRequest,userAuth.getUserName());
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
	
	@RequestMapping(value = "/getOverAllImpacts", method = RequestMethod.POST)
	public ResponseEntity<?> getOverAllImpacts(HttpServletRequest request,@RequestBody RequestPricePlanner requestPricePlanner) {

		logger.debug("MenuPricingController getOverAllImpacts function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		requestPricePlanner.setBrandId(Integer.valueOf(tenantId));
		if(!validateInputRequest(requestPricePlanner)){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "ProjcetId and ScenarioId are required fields"),
					HttpStatus.BAD_REQUEST);
		}
		@SuppressWarnings("unchecked")
		OverAllImpactsResponse response = new OverAllImpactsResponse();
		try {
			OverAllImpactsVo overAllImpactsVo = menuPricingService.getOverAllImpacts(requestPricePlanner);
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
	
	
	@RequestMapping(value = "/getStoreDistribution", method = RequestMethod.POST)
	public ResponseEntity<?> getStoreDistribution(HttpServletRequest request,@RequestBody RequestPricePlanner requestPricePlanner) {

		logger.debug("MenuPricingController getStoreDistribution function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		requestPricePlanner.setBrandId(Integer.valueOf(tenantId));
		if(requestPricePlanner.getProject_Id()==null || requestPricePlanner.getProject_Id().intValue()<= 0 ){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "ProjcetId  is required field"),
					HttpStatus.BAD_REQUEST);
		}
		@SuppressWarnings("unchecked")
		StoreDistributionResponse response = new StoreDistributionResponse();
		try {
			List<StoreDistributionVo> storeDistributionVo = menuPricingService.getStoreDistribution(requestPricePlanner);
			response.setStoreDistributionVo(storeDistributionVo);
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
		return new ResponseEntity<StoreDistributionResponse>(response, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/getMenuItemDistribution", method = RequestMethod.POST)
	public ResponseEntity<?> getMenuItemDistribution (HttpServletRequest request,@RequestBody RequestPricePlanner requestPricePlanner) {

		logger.debug("MenuPricingController getMenuItemDistribution function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		requestPricePlanner.setBrandId(Integer.valueOf(tenantId));
		if(requestPricePlanner.getProject_Id()==null || requestPricePlanner.getProject_Id().intValue()<= 0 ){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "ProjcetId  is required field"),
					HttpStatus.BAD_REQUEST);
		}
		@SuppressWarnings("unchecked")
		MenuItemDistributionResponse response = new MenuItemDistributionResponse();
		try {
			List<MenuItemDistributionVo> menuItemDistributionVo = menuPricingService.getMenuItemDistribution(requestPricePlanner);
			response.setMenuItemDistributionVo(menuItemDistributionVo);
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
		return new ResponseEntity<MenuItemDistributionResponse>(response, HttpStatus.OK);

	}
	
	
	
	@RequestMapping(value = "/getFilterData", method = RequestMethod.POST)
	public ResponseEntity<?> getFilterData(HttpServletRequest request,@RequestBody RequestPricePlanner requestPricePlanner) {
		logger.debug("MenuPricingController getFilterData function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		Integer tenantId = Integer.valueOf(userAuth.getBrandId());
		logger.info("tenantId = " + tenantId);
		requestPricePlanner.setBrandId(tenantId);

		if(requestPricePlanner.getProject_Id()==null || requestPricePlanner.getProject_Id().intValue()<= 0 ){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "ProjcetId  is required field"),
					HttpStatus.BAD_REQUEST);
		}
		
		@SuppressWarnings("unchecked")
		FilterDataResponse response = new FilterDataResponse();
		try {
			FilterData filterData = menuPricingService.getFilterData(requestPricePlanner);
			response.setFilterData(filterData);
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
		return new ResponseEntity<FilterDataResponse>(response, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/getIndividualFilterData/{filterParam}", method = RequestMethod.GET)
	public ResponseEntity<?> getFilterData(HttpServletRequest request,@PathVariable("filterParam") String filterParam) {
		logger.debug("MenuPricingController getFilterData function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		String tenantId = userAuth.getBrandId();
		logger.info("tenantId = " + tenantId);
		
		@SuppressWarnings("unchecked")
		FilterDataIndividualResponse response = new FilterDataIndividualResponse();
		try {
			List<Object> filterDataList = menuPricingService.getFilterData(filterParam);
			response.setFilterData(filterDataList);
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
		return new ResponseEntity<FilterDataIndividualResponse>(response, HttpStatus.OK);
		
	}
	

	
	private boolean validateInputRequest(RequestPricePlanner requestPricePlanner) {
		
		if(requestPricePlanner.getProject_Id()==null || requestPricePlanner.getProject_Id().intValue()< 0 || requestPricePlanner.getScenario_Id()==null
				|| requestPricePlanner.getScenario_Id().intValue() < 0){
			return false;
		}
		return true;
	}
	
	
	@RequestMapping(value = "/postImpSimulatorEvent", method = RequestMethod.POST)
	public ResponseEntity<?> postImpSimulatorEvent(HttpServletRequest request,@RequestBody RequestPricePlanner requestPricePlanner) {
		logger.debug("MenuPricingController postImpSimulatorEvent function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		Integer tenantId = Integer.valueOf(userAuth.getBrandId());
		logger.info("tenantId = " + tenantId);
		requestPricePlanner.setBrandId(tenantId);

		if(!validateInputRequest(requestPricePlanner)){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "ProjcetId and ScenarioId are required fields"),
					HttpStatus.BAD_REQUEST);
		}
		
		FBRestResponse response = new FBRestResponse();
		try {
			ImpactSimulatorEvent event = new ImpactSimulatorEvent();
			event.setAction("impactsimulatorevent");
			event.setBrandid(Integer.valueOf(userAuth.getBrandId()));
			event.setUserid(Integer.valueOf(userAuth.getUserId()));
			//event.setUsername(userAuth.getUserName());
			event.setProjectid(requestPricePlanner.getProject_Id());
			event.setScenarioid(requestPricePlanner.getScenario_Id());
			logger.info("start sending reportrequestevent to queue");
			new FBAzureQueuePublisher().sendEventToQueue(event, userAuth,request);
			logger.info("finished sending reportrequestevent to queue");
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
			return new ResponseEntity<FBRestResponse>(
					new FBRestResponse(false, "Exception Occured, Please check the log files"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setResponse(true, FBConstants.SUCCESS);
		return new ResponseEntity<FBRestResponse>(response, HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(value = "/updateStores", method = RequestMethod.POST)
	public ResponseEntity<?> updateStores(HttpServletRequest request, @RequestBody List<UpdateStoreInfoRequest> updateStoreInfoRequest) {

		logger.debug("MenuPricingController getMenuPricing function starts :::");
		UserAuth userAuth = AuthUtils.getUserAuthData(request);
		int tenantId = Integer.valueOf(userAuth.getBrandId());
		String userName = userAuth.getUserName();
		logger.info("TenantId = " + tenantId);
		logger.info("UserName = " + userName);
		FBRestResponse response = null;
		
		if(null == updateStoreInfoRequest || updateStoreInfoRequest.isEmpty()){
			return new ResponseEntity<FBRestResponse>(new FBRestResponse(false, "The request object is NOT correct"),
				    HttpStatus.BAD_REQUEST);
		}
		
		try {
			response = menuPricingService.updateStores(updateStoreInfoRequest,userName,tenantId);
	
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
