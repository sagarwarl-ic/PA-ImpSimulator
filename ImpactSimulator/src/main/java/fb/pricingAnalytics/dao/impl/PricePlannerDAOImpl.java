package fb.pricingAnalytics.dao.impl;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import fb.pricingAnalytics.dao.PricePlannerDAO;
import fb.pricingAnalytics.model.Project;
import fb.pricingAnalytics.model.Scenario;
import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.request.PricePlannerProjectRequest;
import fb.pricingAnalytics.request.PricePlannerScenarioRequest;

@Repository
public class PricePlannerDAOImpl implements PricePlannerDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(PricePlannerDAOImpl.class);
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public int createProject(PricePlannerProjectRequest projectRequest, String brandId, String userName) throws SQLException, Exception {

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
		
		return project.getId();
	
		
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
		if(projectRequest.getDeleted()!=null) {
			sb.append(" ,P.deleted =: deleted");
		}
		
		sb.append(" WHERE P.id =:id");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("updated_on",Date.from(Instant.now()));
		query.setParameter("updated_by",userName);	
		query.setParameter("id",projectRequest.getProjectId());	
		
		if(projectRequest.getProjectName()!=null) {
			query.setParameter("proj_name",projectRequest.getProjectName());	
		}
		if(projectRequest.getStatusId()!=null) {
			query.setParameter("status",projectRequest.getStatusId());	
		}
		if(projectRequest.getDeleted()!=null) {
			query.setParameter("deleted",projectRequest.getDeleted());	
		}
		
		int resultObjects = query.executeUpdate();
		return resultObjects;
	}

	@Override
	public int createScenario(PricePlannerScenarioRequest scenarioRequest, String brandId, String userName)
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

		
		logger.info("Hibernate will save the scenario object ::: "+scenario.toString());
		session.save(scenario);
		
		return scenario.getId();
	
		
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
		
		sb.append(" WHERE S.id =:id");
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("updated_on",Date.from(Instant.now()));
		query.setParameter("updated_by",userName);	
		query.setParameter("id",scenarioRequest.getScenarioId());	
		
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
	public List<PricePlannerVo> getProject(String brandId, int projectId) throws SQLException, Exception {
		StringBuilder sb = new StringBuilder ("SELECT NEW fb.pricingAnalytics.model.vo.PricePlannerVo(PR.id, PR.brandId, PR.projectName, PR.status, PR.deleted, PR.createdOn, PR.createdBy, PR.updatedOn, PR.updatedBy, "
				+ "SC.id, SC.scenarioName, SC.createdOn, SC.createdBy, SC.updatedOn, SC.updatedBy)"
				+ " FROM Project PR LEFT JOIN Scenario SC ON SC.projectId = PR.id WHERE PR.id= :project_id AND PR.brandId =: brand_id");
		
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("project_id",projectId);
		query.setParameter("brand_id",Integer.parseInt(brandId));
		List<PricePlannerVo> resultObjects  = query.list();
		return resultObjects;
	}


}
