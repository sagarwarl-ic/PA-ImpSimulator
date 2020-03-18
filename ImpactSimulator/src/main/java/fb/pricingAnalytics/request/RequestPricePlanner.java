package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class RequestPricePlanner {
	
	private SearchPricePlanner search;
	private SortPricePlanner sort;
	private PagingPricePlanner paging;
	private BigInteger scenario_Id;
	private BigInteger project_Id;
	private Integer brandId;
	private Boolean changed; 
	
	public SearchPricePlanner getSearch() {
		return search;
	}
	public void setSearch(SearchPricePlanner search) {
		this.search = search;
	}
	public SortPricePlanner getSort() {
		return sort;
	}
	public void setSort(SortPricePlanner sort) {
		this.sort = sort;
	}
	public PagingPricePlanner getPaging() {
		return paging;
	}
	public void setPaging(PagingPricePlanner paging) {
		this.paging = paging;
	}
	public BigInteger getScenario_Id() {
		return scenario_Id;
	}
	public void setScenario_Id(BigInteger scenario_Id) {
		this.scenario_Id = scenario_Id;
	}
	public BigInteger getProject_Id() {
		return project_Id;
	}
	public void setProject_Id(BigInteger project_Id) {
		this.project_Id = project_Id;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Boolean getChanged() {
		return changed;
	}
	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
	
	
	
}
