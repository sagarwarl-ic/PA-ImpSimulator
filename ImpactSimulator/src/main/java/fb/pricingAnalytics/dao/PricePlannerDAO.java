package fb.pricingAnalytics.dao;

import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.request.PricePlannerProjectRequest;
import fb.pricingAnalytics.request.PricePlannerScenarioRequest;

public interface PricePlannerDAO {
	
	public int createProject(PricePlannerProjectRequest projectRequest, String brandId, String userName) throws SQLException,Exception;
	public int updateProject(PricePlannerProjectRequest projectRequest, String brandId, String userName) throws SQLException,Exception;
	public int createScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public int updateScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public List<PricePlannerVo> getProject(String brandId, int projectId) throws SQLException,Exception;

}
