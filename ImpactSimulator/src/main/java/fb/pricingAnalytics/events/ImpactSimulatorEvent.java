package fb.pricingAnalytics.events;

import java.math.BigInteger;

public class ImpactSimulatorEvent {
	
	private String action;
	private int brandid;
	private int userid;
	private String username;
	private BigInteger projectid;
	private BigInteger scenarioid;
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getBrandid() {
		return brandid;
	}
	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigInteger getProjectid() {
		return projectid;
	}
	public void setProjectid(BigInteger projectid) {
		this.projectid = projectid;
	}
	public BigInteger getScenarioid() {
		return scenarioid;
	}
	public void setScenarioid(BigInteger scenarioid) {
		this.scenarioid = scenarioid;
	}
	
	
}
