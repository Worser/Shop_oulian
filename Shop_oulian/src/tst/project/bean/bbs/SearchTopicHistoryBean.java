package tst.project.bean.bbs;

public class SearchTopicHistoryBean {
	private int history_id;
	private int id;
	private int member_id;
	private String search_name;
	private String create_time;
	private String is_delete;

	public int getHistory_id() {
		return history_id;
	}

	public SearchTopicHistoryBean setHistory_id(int history_id) {
		this.history_id = history_id;
		return this;
	}

	public int getId() {
		return id;
	}

	public SearchTopicHistoryBean setId(int id) {
		this.id = id;
		return this;
	}

	public int getMember_id() {
		return member_id;
	}

	public SearchTopicHistoryBean setMember_id(int member_id) {
		this.member_id = member_id;
		return this;
	}

	public String getSearch_name() {
		return search_name;
	}

	public SearchTopicHistoryBean setSearch_name(String search_name) {
		this.search_name = search_name;
		return this;
	}

	public String getCreate_time() {
		return create_time;
	}

	public SearchTopicHistoryBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public SearchTopicHistoryBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}

}