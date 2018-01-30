package tst.project.bean.member;

/**
 * @author xingqianji
 *
 */
public class MemberSearchHistoryBean {

	private Integer history_id;
	private String member_id;//用户id
	private String merchants_id; //商家id
	private String search_name; //搜索名称
	private String create_time; //搜索时间
	private String sort; //权重
	private String is_delete; //是否删除
	
	public Integer getHistory_id() {
		return history_id;
	}
	public MemberSearchHistoryBean setHistory_id(Integer history_id) {
		this.history_id = history_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public MemberSearchHistoryBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getMerchants_id() {
		return merchants_id;
	}
	public MemberSearchHistoryBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}
	public String getSearch_name() {
		return search_name;
	}
	public MemberSearchHistoryBean setSearch_name(String search_name) {
		this.search_name = search_name;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public MemberSearchHistoryBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getSort() {
		return sort;
	}
	public MemberSearchHistoryBean setSort(String sort) {
		this.sort = sort;
		return this;
	}
	
	public String getIs_delete() {
		return is_delete;
	}
	public MemberSearchHistoryBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	
}
