package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class RequestPricePlanner {
	
	private Integer brandId;
	private Boolean changed;
	private BigInteger dataEntry_Id;
	private PagingPricePlanner paging;
	private String  product_id;
	private BigInteger project_Id;
	private BigInteger scenario_Id;
	private SearchPricePlanner search; 
	private SortPricePlanner sort;
	public Integer getBrandId() {
		return brandId;
	}
	public Boolean getChanged() {
		return changed;
	}
	public BigInteger getDataEntry_Id() {
		return dataEntry_Id;
	}
	public PagingPricePlanner getPaging() {
		return paging;
	}
	public String getProduct_id() {
		return product_id;
	}
	public BigInteger getProject_Id() {
		return project_Id;
	}
	public BigInteger getScenario_Id() {
		return scenario_Id;
	}
	public SearchPricePlanner getSearch() {
		return search;
	}
	public SortPricePlanner getSort() {
		return sort;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
	public void setDataEntry_Id(BigInteger dataEntry_Id) {
		this.dataEntry_Id = dataEntry_Id;
	}
	public void setPaging(PagingPricePlanner paging) {
		this.paging = paging;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public void setProject_Id(BigInteger project_Id) {
		this.project_Id = project_Id;
	}
	public void setScenario_Id(BigInteger scenario_Id) {
		this.scenario_Id = scenario_Id;
	}

	public void setSearch(SearchPricePlanner search) {
		this.search = search;
	}

	public void setSort(SortPricePlanner sort) {
		this.sort = sort;
	}
	
	
	
}
