package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.vo.ProjectVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class ProjectListResponse extends FBRestResponse{
	
	private int count;
	private List<ProjectVo> projectVo;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<ProjectVo> getProjectVo() {
		return projectVo;
	}
	public void setProjectVo(List<ProjectVo> projectVo) {
		this.projectVo = projectVo;
	}
	
	

}
