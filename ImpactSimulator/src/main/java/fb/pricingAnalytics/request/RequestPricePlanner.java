package fb.pricingAnalytics.request;

public class RequestPricePlanner {
	
	private SearchPricePlanner search;
	private SortPricePlanner sort;
	private PagingPricePlanner paging;
	
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
	
}
