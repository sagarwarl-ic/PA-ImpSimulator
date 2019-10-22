package fb.pricingAnalytics.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fb.pricingAnalytics.utils.AuthUtils;
import fb.pricingAnalytics.utils.FBRestResponse;

/**
 * @Fishbowl
 * @author abhay
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	 private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
			
	private static List<String> publicProtectedlist = new ArrayList<String>(Arrays.asList(
			new String[] {"/pp/health/check"}));  
	
	private static List<String> fishbowlProtectedlist = new ArrayList<String>(Arrays.asList(
			new String[] {"/pa/userAuth/ssoLogin"}));
	
	private static List<String> fishbowlNPaProtectedlist = new ArrayList<String>(Arrays.asList(
			new String[] {"/pa/user/updateBrandId","/pa/user/brandlist","/pa/session/logout","/pa/user/getUserDetails"}));
	


	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("--------------------preHandle start--------------------------------");
		String requestPath = request.getRequestURI();
		logger.info("--------------------preHandle requestPath--------------------------------" + requestPath);
		boolean isCookiesPresent=false;
		logger.info("--------------------requestPath-------------------------------- " +requestPath);
		
		if(AuthUtils.validateProtectedPaths(requestPath, publicProtectedlist)){
			isCookiesPresent=true;
			logger.info("-------------------validateProtectedPaths--------------------------------" + isCookiesPresent);
		}
		else if(AuthUtils.validateProtectedPaths(requestPath, fishbowlProtectedlist)){
			 isCookiesPresent= AuthUtils.isFishbowlCookiesPresent(request.getCookies(), request.getServerName());
			 logger.info("-------------------isFishbowlCookiesPresent--------------------------------" + isCookiesPresent);
		}else if(AuthUtils.validateProtectedPaths(requestPath, fishbowlNPaProtectedlist)){
			 isCookiesPresent=  (AuthUtils.isFishbowlCookiesPresent(request.getCookies(), request.getServerName()) &&AuthUtils.isPACookiesPresent(request.getCookies(), request.getServerName()));
			 logger.info("-------------------isFishbowlCookiesPresent&&isPACookiesPresent--------------------------------" + isCookiesPresent);
		}else{
			 isCookiesPresent=  (AuthUtils.isFishbowlCookiesPresent(request.getCookies(), request.getServerName()) &&
					 AuthUtils.isPACookiesPresent(request.getCookies(), request.getServerName())
					 &&
					 AuthUtils.isBrandIDCookiesPresent(request.getCookies(), request.getServerName()));
			
			 logger.info("-------------------isFishbowlCookiesPresent&&isPACookiesPresent&&isBrandIDCookiesPresent--------------------------------" + isCookiesPresent);
		}
		if(!isCookiesPresent){
//			response.sendRedirect();
			 response=AuthUtils.deleteCookies(request, response);
			 AuthUtils.getJsonFromObjectWithResponse(response, new FBRestResponse(true, "https://"+AuthUtils.getParentAppUrl(request.getServerName())+"/Public/Login.aspx"), 401);
		}
		logger.info("--------------------preHandle end--------------------------------");
		return isCookiesPresent;
	}
	
}