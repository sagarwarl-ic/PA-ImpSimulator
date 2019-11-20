package fb.pricingAnalytics.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fb.pricingAnalytics.model.vo.PricePlannerVo;
import fb.pricingAnalytics.utils.FBRestResponse;

public class PricePlannerResponse extends FBRestResponse{
	
	@JsonInclude(Include.NON_NULL)
	private Integer result;
	
	@JsonInclude(Include.NON_NULL)
	private List<PricePlannerVo> resultList;

	
	@JsonInclude(Include.NON_NULL)
	private PricePlannerVo resultObject;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public List<PricePlannerVo> getResultList() {
		return resultList;
	}

	public void setResultList(List<PricePlannerVo> resultList) {
		this.resultList = resultList;
	}

	public PricePlannerVo getResultObject() {
		return resultObject;
	}

	public void setResultObject(PricePlannerVo resultObject) {
		this.resultObject = resultObject;
	}

}
