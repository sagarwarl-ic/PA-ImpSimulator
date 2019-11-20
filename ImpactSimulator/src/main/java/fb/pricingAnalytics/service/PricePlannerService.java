package fb.pricingAnalytics.service;

import java.sql.SQLException;

import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.request.PricePlannerProjectRequest;
import fb.pricingAnalytics.request.PricePlannerScenarioRequest;

public interface PricePlannerService {
	
	public int createProject(PricePlannerProjectRequest projectRequest,String brandId, String userName) throws SQLException,Exception;
	public int updateProject(PricePlannerProjectRequest projectRequest,String brandId, String userName) throws SQLException,Exception;
	public int createScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public int updateScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public PricePlannerVo getProject(String brandId, int projectId) throws SQLException,Exception;


}
