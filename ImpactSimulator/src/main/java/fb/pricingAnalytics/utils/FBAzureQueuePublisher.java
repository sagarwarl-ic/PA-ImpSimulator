package fb.pricingAnalytics.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


import fb.pricingAnalytics.events.ImpactSimulatorEvent;
import fb.pricingAnalytics.model.auth.UserAuth;
import fb.pricingAnalytics.queue.QueuePublisher;
import fb.pricingAnalytics.queue.impl.QueuePublisherImpl;

public class FBAzureQueuePublisher {
	
	private static Logger logger = LoggerFactory.getLogger(FBAzureQueuePublisher.class);
	
	public void sendEventToQueue(ImpactSimulatorEvent event,UserAuth userAuth){
		try{
			QueuePublisher queuePublisher = new QueuePublisherImpl();
			String queueName = FBConstants.PA_QUEUE+userAuth.getBrandId();
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
