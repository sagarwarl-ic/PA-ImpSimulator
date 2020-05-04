package fb.pricingAnalytics.response;

import java.util.List;

import fb.pricingAnalytics.model.Operator;
import fb.pricingAnalytics.utils.FBRestResponse;

public class OperatorListResponse extends FBRestResponse {

	List<Operator> operators;

	public OperatorListResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OperatorListResponse(boolean successFlag, String message) {
		super(successFlag, message);
		// TODO Auto-generated constructor stub
	}

	public List<Operator> getOperators() {
		return operators;
	}

	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}

}
