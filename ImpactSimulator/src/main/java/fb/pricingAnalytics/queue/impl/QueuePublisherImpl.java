
package fb.pricingAnalytics.queue.impl;


import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.apache.log4j.Logger;


import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;

import fb.pricingAnalytics.queue.QueuePublisher;



@Service
public class QueuePublisherImpl implements QueuePublisher {
	private static Logger logger = Logger.getLogger(QueuePublisherImpl.class);
	public CloudStorageAccount azureStorageAccount = null;
	public CloudQueueClient queueClient = null;
	public CloudQueue queue=null;
	public String queueUrl;
	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=pricinganalytics;AccountKey=JlfskDyWUtDcxxHpiBwep/R518XmsR2E40yWyxKuYasHKkTKQIhGwnO4irof0MNhPF7ZDtpi7YnsgqvWIkM5tQ==;EndpointSuffix=core.windows.net";
	
	public QueuePublisherImpl() {
		super();
		initEventQueue();
	}
	
	private CloudStorageAccount createColudStorageAccount(){
		CloudStorageAccount storageAccount= null;
		try {
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
        }
        catch (IllegalArgumentException|URISyntaxException e) {
        	logger.debug("AZURE Storage connection Error" +e.getMessage());
        }
        catch (InvalidKeyException e) {
        	logger.debug("AZURE Storage connection Error" +e.getMessage());
        }
		return storageAccount;

	}
	@Override
	public void initEventQueue() {
		azureStorageAccount=this.createColudStorageAccount();
		if(azureStorageAccount !=null){
			queueClient= azureStorageAccount.createCloudQueueClient();
		}
	}

	@Override
	public String createQueue(String queueName) {
		// Create a queue
		try {
		    CloudQueue cloudQueue = queueClient.getQueueReference(queueName.toLowerCase());
		    cloudQueue.createIfNotExists();
		    queue = cloudQueue;
		    queueUrl = queue.getUri().toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Queue not created : " + e.fillInStackTrace()); //$NON-NLS-1$
		}
		return queueUrl;
	}

	@Override
	public void sendEventToQueue(String message) {
		try {
			logger.info("sending azure queue message : " +queueUrl+ "  message  "+message);
			queue.addMessage(new CloudQueueMessage(message));
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
	}
	

	
}
