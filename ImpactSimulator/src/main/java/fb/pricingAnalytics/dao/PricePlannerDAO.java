package fb.pricingAnalytics.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.model.vo.ProjectVo;
import fb.pricingAnalytics.request.PricePlannerProjectRequest;
import fb.pricingAnalytics.request.PricePlannerScenarioRequest;

public interface PricePlannerDAO {
	
	public BigInteger createProject(PricePlannerProjectRequest projectRequest, String brandId, String userName) throws SQLException,Exception;
	public int updateProject(PricePlannerProjectRequest projectRequest, String brandId, String userName) throws SQLException,Exception;
	public BigInteger createScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public int updateScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public List<PricePlannerVo> getProject(String brandId, int projectId) throws SQLException,Exception;
	public void copyProjectData(BigInteger projectId, String brandId,String userName);
	public void copyScenarioData(BigInteger projectId, BigInteger scenarioId,String brandId, String userName);
	public List<ProjectVo> getProjectList(String brandId)throws SQLException,Exception;

}
