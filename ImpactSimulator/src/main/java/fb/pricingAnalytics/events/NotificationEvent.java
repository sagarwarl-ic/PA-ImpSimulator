package fb.pricingAnalytics.events;

public class NotificationEvent {
	
	private String action;
	private int brandid;
	private int reportid;
	private int currentreportstage;
	private int newreportstage;
	private int reporttype;
	private int userid;
	private String username;
	private String requestedOnBehalf;
	
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
	public int getReportid() {
		return reportid;
	}
	public void setReportid(int reportid) {
		this.reportid = reportid;
	}
	public int getCurrentreportstage() {
		return currentreportstage;
	}
	public void setCurrentreportstage(int currentreportstage) {
		this.currentreportstage = currentreportstage;
	}
	public int getNewreportstage() {
		return newreportstage;
	}
	public void setNewreportstage(int newreportstage) {
		this.newreportstage = newreportstage;
	}
	public int getReporttype() {
		return reporttype;
	}
	public void setReporttype(int reporttype) {
		this.reporttype = reporttype;
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
	public String getRequestedOnBehalf() {
		return requestedOnBehalf;
	}
	public void setRequestedOnBehalf(String requestedOnBehalf) {
		this.requestedOnBehalf = requestedOnBehalf;
	}
	
	

}
