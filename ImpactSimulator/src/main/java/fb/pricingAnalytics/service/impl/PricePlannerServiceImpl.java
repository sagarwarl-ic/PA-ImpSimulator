package fb.pricingAnalytics.service.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fb.pricingAnalytics.dao.PricePlannerDAO;
import fb.pricingAnalytics.model.Project;
import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.model.vo.ProjectVo;
import fb.pricingAnalytics.model.vo.ScenarioVo;
import fb.pricingAnalytics.request.PricePlannerProjectRequest;
import fb.pricingAnalytics.request.PricePlannerScenarioRequest;
import fb.pricingAnalytics.response.DataEntryResponse;
import fb.pricingAnalytics.service.PricePlannerService;
import fb.pricingAnalytics.utils.FBRestResponse;

@Service
public class PricePlannerServiceImpl implements PricePlannerService{
	
	@Autowired
	PricePlannerDAO pricePlannerDAO;

	@Transactional
	@Override
	public Project createProject(PricePlannerProjectRequest projectRequest, String brandId, String userName) throws SQLException, Exception {
		
		Project project= pricePlannerDAO.createProject(projectRequest, brandId, userName);
		BigInteger latestDataEntryId = pricePlannerDAO.getDataEntryIdInStoreProductInfo(Integer.valueOf(brandId));
		if((latestDataEntryId.intValue() < project.getDataEntryId().intValue())){
			pricePlannerDAO.copyProjectData(project.getDataEntryId(),brandId,userName);
		}
		return project;
		
	}

	@Transactional
	@Override
	public int updateProject(PricePlannerProjectRequest projectRequest, String brandId, String userName)
			throws SQLException, Exception {
		
		return pricePlannerDAO.updateProject(projectRequest, brandId, userName);
	}

	@Transactional
	@Override
	public BigInteger createScenario(PricePlannerScenarioRequest scenarioRequest, String brandId, String userName)
			throws SQLException, Exception {

		return pricePlannerDAO.createScenario(scenarioRequest, brandId, userName);
	}

	@Transactional
	@Override
	public int updateScenario(PricePlannerScenarioRequest scenarioRequest, String brandId, String userName)
			throws SQLException, Exception {

		return pricePlannerDAO.updateScenario(scenarioRequest, brandId, userName);
	}

	@Override
	public PricePlannerVo getProject(String brandId, BigInteger projectId) throws SQLException, Exception {

		List<PricePlannerVo> projectList =  pricePlannerDAO.getProject(brandId, projectId);
		if(projectList!=null && projectList.size()>0) {
			List<ScenarioVo> scenarioList = new ArrayList<ScenarioVo>();
			
			for(PricePlannerVo pricePlannerVo: projectList) {
				if(pricePlannerVo.getScenarioId()!=null && !pricePlannerVo.getScenarioDeleted()) {
				ScenarioVo scenario = new ScenarioVo();
				scenario.setId(pricePlannerVo.getScenarioId());
				scenario.setSceanrioName(pricePlannerVo.getScenarioName());
				scenario.setProjectId(pricePlannerVo.getId());
				scenario.setCreatedOn(pricePlannerVo.getScenarioCreatedOn());
				scenario.setCreatedBy(pricePlannerVo.getScenarioCreatedBy());
				scenario.setUpdatedOn(pricePlannerVo.getScenarioUpdatedOn());
				scenario.setUpdatedBy(pricePlannerVo.getScenarioUpdatedBy());
				scenario.setBrandId(Integer.parseInt(brandId));
				scenarioList.add(scenario);
				}
			}
			PricePlannerVo pricePlannerRequestObj = new PricePlannerVo(projectList.get(0).getId(),projectList.get(0).getBrandId(),projectList.get(0).getName(),projectList.get(0).getStatus(),projectList.get(0).isDeleted(),projectList.get(0).getCreatedOn(),projectList.get(0).getCreatedBy(),projectList.get(0).getUpdatedOn(),projectList.get(0).getUpdatedBy(),projectList.get(0).getDataEntryId());
			pricePlannerRequestObj.setScenarioList(scenarioList);
			return pricePlannerRequestObj;
		}
		else {
			return null;
		}
	}

	@Override
	@Transactional
	public void copyProjectData(BigInteger dataEntryId, String brandId, String userName){
		 pricePlannerDAO.copyProjectData(dataEntryId, brandId, userName);
	}

	@Override
	@Transactional
	public void copyScenarioData(BigInteger businessRuleScenarioId,BigInteger projectId, BigInteger scenarioId,String brandId, String userName) {
		pricePlannerDAO.copyScenarioData(businessRuleScenarioId,projectId, scenarioId,brandId, userName);
		
	}

	@Override
	public List<ProjectVo> getProjectList(String brandId) throws SQLException,Exception {
		return pricePlannerDAO.getProjectList(brandId);
	}

	@Transactional
	@Override
	public int deleteProject(String brandId, BigInteger projectId)throws SQLException, Exception {
		return pricePlannerDAO.deleteProject(brandId,projectId);
	}

	@Transactional
	@Override
	public int deleteScenario(String brandId, BigInteger projectId,BigInteger scenarioId) throws SQLException, Exception {
		return pricePlannerDAO.deleteScenario(brandId,projectId,scenarioId);
	}
	
	@Transactional
	@Override
	public void duplicateScenarioData(BigInteger projectId,BigInteger autoGeneratedScenarioId, BigInteger existingScenarioId,
			String brandId, String userName) throws SQLException, Exception {
		pricePlannerDAO.duplicateScenarioData(projectId,autoGeneratedScenarioId,existingScenarioId,brandId, userName);
		
	}

	@Override
	public DataEntryResponse getDataEntry(BigInteger dataEntryId,int brandId)throws SQLException, Exception {
		return pricePlannerDAO.getDataEntry(dataEntryId,brandId);
	}

	@Override
	public BigInteger getDataEntryIdInStoreProductInfo(int brandId) throws SQLException, Exception {
		return pricePlannerDAO.getDataEntryIdInStoreProductInfo(brandId);
	}

	@Override
	@Transactional
	public void updateProjectRecommendedData(BigInteger dataEntryId, String brandId, String userName) {
		 pricePlannerDAO.updateProjectRecommendedData(dataEntryId, brandId, userName);
		
	}

}
