package fb.pricingAnalytics.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import fb.pricingAnalytics.dao.PricePlannerDAO;
import fb.pricingAnalytics.model.Project;
import fb.pricingAnalytics.model.Scenario;
import fb.pricingAnalytics.model.vo.DataEntryVo;
import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.model.vo.ProjectVo;
import fb.pricingAnalytics.request.PricePlannerProjectRequest;
import fb.pricingAnalytics.request.PricePlannerScenarioRequest;
import fb.pricingAnalytics.response.DataEntryResponse;

@Repository
public class PricePlannerDAOImpl implements PricePlannerDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(PricePlannerDAOImpl.class);
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public BigInteger createProject(PricePlannerProjectRequest projectRequest, String brandId, String userName) throws SQLException, Exception {

		logger.debug("PricePlannerDAOImpl createProject function starts ::: ");
		Project project = new Project();
		Session session =  entityManager.unwrap(Session.class);
		
		project.setBrandId(Integer.parseInt(brandId));
		project.setCreatedBy(userName);
		project.setUpdatedBy(userName);
		project.setCreatedOn(Date.from(Instant.now()));
		project.setUpdatedOn(Date.from(Instant.now()));
		project.setStatus(projectRequest.getStatusId());
		project.setProjectName(projectRequest.getProjectName());
		
		logger.info("Hibernate will save the project object ::: "+project.toString());
		
		session.save(project);
		
		return project.getProjectId();
	
		
	}

	@Override
	public int updateProject(PricePlannerProjectRequest projectRequest, String brandId, String userName)
			throws SQLException, Exception {
		StringBuilder sb =  new StringBuilder ("UPDATE Project P SET P.updatedOn =:updated_on, P.updatedBy =:updated_by ");
		
		if(projectRequest.getProjectName()!=null) {
			sb.append(" ,P.projectName =: proj_name");
		}
		if(projectRequest.getStatusId()!=null) {
			sb.append(" ,P.status =: status");
		}
		if(projectRequest.isDeleted()) {
			sb.append(" ,P.deleted =: deleted");
		}
		
		sb.append(" WHERE P.projectId =:id and P.brandId=:brandId");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("updated_on",Date.from(Instant.now()));
		query.setParameter("updated_by",userName);	
		query.setParameter("id",projectRequest.getProjectId());	
		query.setParameter("brandId",Integer.parseInt(brandId));	
		
		if(projectRequest.getProjectName()!=null) {
			query.setParameter("proj_name",projectRequest.getProjectName());	
		}
		if(projectRequest.getStatusId()!=null) {
			query.setParameter("status",projectRequest.getStatusId());	
		}
		if(projectRequest.isDeleted()) {
			query.setParameter("deleted",projectRequest.isDeleted());	
		}
		
		int resultObjects = query.executeUpdate();
		return resultObjects;
	}

	@Override
	public BigInteger createScenario(PricePlannerScenarioRequest scenarioRequest, String brandId, String userName)
			throws SQLException, Exception {

		logger.debug("PricePlannerDAOImpl createScenario function starts ::: ");
		Scenario scenario = new Scenario();
		Session session =  entityManager.unwrap(Session.class);
		
		scenario.setBrandId(Integer.parseInt(brandId));
		scenario.setCreatedBy(userName);
		scenario.setUpdatedBy(userName);
		scenario.setCreatedOn(Date.from(Instant.now()));
		scenario.setUpdatedOn(Date.from(Instant.now()));
		scenario.setScenarioName(scenarioRequest.getScenarioName());
		scenario.setProjectId(scenarioRequest.getProjectId());
		scenario.setIsLockedBy(userName);
		//scenario.setComment(null);
		scenario.setDeleted(false);

		
		logger.info("Hibernate will save the scenario object ::: "+scenario.toString());
		session.save(scenario);
		
		return scenario.getScenarioId();
	
		
	}

	@Override
	public int updateScenario(PricePlannerScenarioRequest scenarioRequest, String brandId, String userName)
			throws SQLException, Exception {
		StringBuilder sb =  new StringBuilder ("UPDATE Scenario S SET S.updatedOn =:updated_on, S.updatedBy =:updated_by ");
		
		if(scenarioRequest.getScenarioName()!=null) {
			sb.append(" ,S.scenarioName =: scenario_name");
		}
		if(scenarioRequest.getProjectId()!=null) {
			sb.append(" ,S.projectId =: proj_id");
		}
		
		sb.append(" WHERE S.scenarioId =:id and S.brandId=:brandId");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("updated_on",Date.from(Instant.now()));
		query.setParameter("updated_by",userName);	
		query.setParameter("id",scenarioRequest.getScenarioId());	
		query.setParameter("brandId",Integer.parseInt(brandId));
		
		if(scenarioRequest.getScenarioName()!=null) {
			query.setParameter("scenario_name",scenarioRequest.getScenarioName());	
		}
		if(scenarioRequest.getProjectId()!=null) {
			query.setParameter("proj_id",scenarioRequest.getProjectId());	
		}

		
		int resultObjects = query.executeUpdate();
		return resultObjects;
	}

	@Override
	public List<PricePlannerVo> getProject(String brandId, BigInteger projectId) throws SQLException, Exception {
		StringBuilder sb = new StringBuilder ("SELECT NEW fb.pricingAnalytics.model.vo.PricePlannerVo(PR.projectId, PR.brandId, PR.projectName, PR.status, PR.deleted, PR.createdOn, PR.createdBy, PR.updatedOn, PR.updatedBy, "
				+ "SC.scenarioId, SC.scenarioName, SC.createdOn, SC.createdBy, SC.updatedOn, SC.updatedBy, SC.deleted)"
				+ " FROM Project PR LEFT JOIN Scenario SC ON SC.projectId = PR.projectId WHERE PR.projectId= :project_id AND PR.brandId =: brand_id ");
		
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("project_id",projectId);
		query.setParameter("brand_id",Integer.parseInt(brandId));
		List<PricePlannerVo> resultObjects  = query.list();
		return resultObjects;
	}

	@Override
	public void copyProjectData(BigInteger projectId, String brandId,String userName) {
	
		try{
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("[ImpactSimulator].[dbo].[CopyProjectData]");
	
		query.registerStoredProcedureParameter(0, Integer.class , ParameterMode.IN);
		query.setParameter(0, Integer.valueOf(brandId));
		query.registerStoredProcedureParameter(1, BigInteger.class , ParameterMode.IN);
		query.setParameter(1, projectId);
		query.registerStoredProcedureParameter(2, String.class , ParameterMode.IN);
		query.setParameter(2, userName);
		query.execute();
		/*List<Object[]> rows = query.getResultList();
		if(rows.size() > 0){
			logger.info("Project Data Copied Successfully from Store_Product_Info to IST_Store_Product_Info ... ");
		}*/
		}catch(Exception ex){
			logger.info("Exception occured while copying Project Data from Store_Product_Info to IST_Store_Product_Info ... ");
		}
	}

	@Override
	public void copyScenarioData(BigInteger projectId, BigInteger scenarioId,
			String brandId, String userName)  {
		
		try{
			
		
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("[ImpactSimulator].[dbo].[CopyScenarioData]");
		query.registerStoredProcedureParameter(0, Integer.class , ParameterMode.IN);
		query.setParameter(0, Integer.valueOf(brandId));
		query.registerStoredProcedureParameter(1, BigInteger.class , ParameterMode.IN);
		query.setParameter(1, projectId);
		query.registerStoredProcedureParameter(2, BigInteger.class , ParameterMode.IN);
		query.setParameter(2, scenarioId);
		query.registerStoredProcedureParameter(3, String.class , ParameterMode.IN);
		query.setParameter(3, userName);
		query.execute();
		/*List<Object[]> rows = query.getResultList();
		if(rows.size() > 0){
			logger.info("Scenario Data Copied Successfully from Store_Product_Info to IST_Store_Info and IST_Product_Tier_Info ... ");
		}*/
		}catch(Exception ex){
			logger.info("Exception occured while copying Scenario data from Store_Product_Info to IST_Store_Info and IST_Product_Tier_Info ... ");
		}
	}

	@Override
	public List<ProjectVo> getProjectList(String brandId) throws SQLException,Exception {
		StringBuilder sb = new StringBuilder("SELECT NEW fb.pricingAnalytics.model.vo.ProjectVo(PR.projectId,PR.brandId,PR.projectName,PR.status,"
				+ "PR.comment,PR.deleted,PR.createdOn,PR.createdBy,PR.updatedOn,PR.updatedBy) FROM Project PR where PR.brandId =:brand_id and PR.deleted=0 order by PR.updatedOn DESC");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("brand_id",Integer.parseInt(brandId));
		System.out.println("Query : "+query);
		List<ProjectVo> resultObjects  = query.list();
		return resultObjects;
		
	}

	@Override
	public int deleteProject(String brandId, BigInteger projectId)throws SQLException, Exception {
		StringBuilder sb = new StringBuilder("UPDATE Project PR SET PR.deleted = 1 where PR.projectId=:project_id and PR.brandId=:brand_id ");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("project_id",projectId);
		query.setParameter("brand_id",Integer.parseInt(brandId));
		int rowsUpdated = query.executeUpdate();
		return rowsUpdated;
	}

	@Override
	public int deleteScenario(String brandId, BigInteger projectId,BigInteger scenarioId) throws SQLException, Exception {		
		StringBuilder sb = new StringBuilder("UPDATE Scenario SC SET SC.deleted = 1 where SC.projectId=:project_id and SC.scenarioId=:scenario_id and SC.brandId=:brand_id ");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("project_id",projectId);
		query.setParameter("scenario_id",scenarioId);
		query.setParameter("brand_id",Integer.parseInt(brandId));
		int rowsUpdated = query.executeUpdate();
		return rowsUpdated;
	}
	
	@Override
	public void duplicateScenarioData(BigInteger projectId,BigInteger autoGeneratedScenarioId, BigInteger existingScenarioId,
			String brandId, String userName) throws SQLException, Exception {
		
		try{
			StoredProcedureQuery query = entityManager
					.createStoredProcedureQuery("[ImpactSimulator].[dbo].[DuplicateScenarioData]");
			query.registerStoredProcedureParameter(0, Integer.class , ParameterMode.IN);
			query.setParameter(0, Integer.valueOf(brandId));
			query.registerStoredProcedureParameter(1, BigInteger.class , ParameterMode.IN);
			query.setParameter(1, projectId);
			query.registerStoredProcedureParameter(2, BigInteger.class , ParameterMode.IN);
			query.setParameter(2, autoGeneratedScenarioId);
			query.registerStoredProcedureParameter(3, BigInteger.class , ParameterMode.IN);
			query.setParameter(3, existingScenarioId);
			query.registerStoredProcedureParameter(4, String.class , ParameterMode.IN);
			query.setParameter(4, userName);
			query.execute();
		}catch(Exception ex){
			logger.info("Exception occured while copying Scenario data from Store_Product_Info to IST_Store_Info and IST_Product_Tier_Info ... ");
		}
		
	}

	@Override
	public DataEntryResponse getDataEntry(BigInteger dataEntryId)throws SQLException, Exception {
		
		DataEntryResponse response = new DataEntryResponse();
	
		StringBuilder sb = new StringBuilder("SELECT NEW fb.pricingAnalytics.model.vo.DataEntryVo(DE.id,DE.quantity_And_Sales_Min_Date,DE.quantity_and_Sales_Max_Date,DE.product_List_Min_Date,DE.product_List_Max_Date,"
				+ "DE.current_Avg_Price_Min_Date,DE.current_Avg_Price_Max_Date) FROM DataEntry DE where DE.id=:data_Entry_Id");
		
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("data_Entry_Id",dataEntryId);
		List<DataEntryVo> resultObjects  = query.list();
		if(null != resultObjects && !resultObjects.isEmpty()){
			response.setDataEntry(resultObjects.get(0));
		}
		return response;
	
	}

}
