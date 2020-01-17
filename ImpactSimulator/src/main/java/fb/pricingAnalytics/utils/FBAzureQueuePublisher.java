package fb.pricingAnalytics.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;



import fb.pricingAnalytics.events.ImpactSimulatorEvent;
import fb.pricingAnalytics.model.auth.UserAuth;
import fb.pricingAnalytics.queue.QueuePublisher;
import fb.pricingAnalytics.queue.impl.QueuePublisherImpl;

public class FBAzureQueuePublisher {
	
	private static Logger logger = LoggerFactory.getLogger(FBAzureQueuePublisher.class);
	
	public void sendEventToQueue(ImpactSimulatorEvent event,UserAuth userAuth,HttpServletRequest request){
		String queueName = null;
		try{
			QueuePublisher queuePublisher = new QueuePublisherImpl();
			
			if(request.getServerName().contains("qa")){
				queueName = FBConstants.PA_QA_QUEUE+userAuth.getBrandId();
			}else if(request.getServerName().contains("stg") || request.getServerName().contains("staging")){
				queueName = FBConstants.PA_STG_QUEUE+userAuth.getBrandId();
			}else{
				queueName = FBConstants.PA_PRD_QUEUE+userAuth.getBrandId();
			}
			
			logger.info("---- queueName ---- " + queueName);
			Gson gson = new Gson();
			String json = gson.toJson(event);
			logger.info("----- json -----  " + json);
			String queueURL = queuePublisher.createQueue(queueName);
			logger.info("queueURL : " + queueURL);
			queuePublisher.sendEventToQueue(json);
		}catch(Exception ex){
			logger.info("Exception occured while sendinf event to queue");
		}
	}

}
