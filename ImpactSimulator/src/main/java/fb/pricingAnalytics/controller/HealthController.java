package fb.pricingAnalytics.controller;
import java.io.IOException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fb.pricingAnalytics.utils.FBRestResponse;

@RestController
@RequestMapping("/pp/health")
public class HealthController {

	@RequestMapping(value="/check", method = RequestMethod.GET)
	public ResponseEntity<?> check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		return new ResponseEntity<FBRestResponse>(new FBRestResponse(true, "OK"),HttpStatus.OK);

	}
}


