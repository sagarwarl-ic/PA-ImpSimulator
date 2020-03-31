package fb.pricingAnalytics.service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import fb.pricingAnalytics.model.Project;
import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.model.vo.ProjectVo;
import fb.pricingAnalytics.request.PricePlannerProjectRequest;
import fb.pricingAnalytics.request.PricePlannerScenarioRequest;
import fb.pricingAnalytics.response.DataEntryResponse;

public interface PricePlannerService {
	
	public Project createProject(PricePlannerProjectRequest projectRequest,String brandId, String userName) throws SQLException,Exception;
	public int updateProject(PricePlannerProjectRequest projectRequest,String brandId, String userName) throws SQLException,Exception;
	public BigInteger createScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public int updateScenario(PricePlannerScenarioRequest scenarioRequest,String brandId, String userName) throws SQLException,Exception;
	public PricePlannerVo getProject(String brandId, BigInteger projectId) throws SQLException,Exception;
	public void copyProjectData(BigInteger projectId, String brandId, String userName);
	public void copyScenarioData(BigInteger projectId, BigInteger scenarioId,String brandId, String userName);
	public List<ProjectVo> getProjectList(String brandId)throws SQLException,Exception;
	public int deleteProject(String brandId, BigInteger projectId) throws SQLException,Exception;
	public int deleteScenario(String brandId, BigInteger projectId,BigInteger scenarioId)throws SQLException,Exception;
	public void duplicateScenarioData(BigInteger projectId,	BigInteger autoGeneratedScenarioId, BigInteger scenarioId,
			String brandId, String userName)throws SQLException,Exception;
	public DataEntryResponse getDataEntry(BigInteger dataEntryId, int brandId)throws SQLException,Exception;


}
