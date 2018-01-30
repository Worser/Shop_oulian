package tst.project.bean.order;

public class LogisticsBean {
	private int logistics_id;
	private String logistics_name;
	private String logistics_pinyin;
	private String is_delete;
	private String create_time;
	private String logistics_state;
	private String logistics_free_price;
	private String logistics_common;
	private String logistics_fast;
	private String sort;

	public String getSort() {
		return sort;
	}
	public LogisticsBean setSort(String sort) {
		this.sort = sort;
		return this;
	}
	public String getLogistics_state() {
		return logistics_state;
	}
	public LogisticsBean setLogistics_state(String logistics_state) {
		this.logistics_state = logistics_state;
		return this;
	}
	public String getLogistics_free_price() {
		return logistics_free_price;
	}
	public LogisticsBean setLogistics_free_price(String logistics_free_price) {
		this.logistics_free_price = logistics_free_price;
		return this;
	}
	public String getLogistics_common() {
		return logistics_common;
	}
	public LogisticsBean setLogistics_common(String logistics_common) {
		this.logistics_common = logistics_common;
		return this;
	}
	public String getLogistics_fast() {
		return logistics_fast;
	}
	public LogisticsBean setLogistics_fast(String logistics_fast) {
		this.logistics_fast = logistics_fast;
		return this;
	}
	public int getLogistics_id() {
		return logistics_id;
	}
	public LogisticsBean setLogistics_id(int logistics_id) {
		this.logistics_id = logistics_id;
		return this;
	}
	public String getLogistics_name() {
		return logistics_name;
	}
	public LogisticsBean setLogistics_name(String logistics_name) {
		this.logistics_name = logistics_name;
		return this;
	}
	public String getLogistics_pinyin() {
		return logistics_pinyin;
	}
	public LogisticsBean setLogistics_pinyin(String logistics_pinyin) {
		this.logistics_pinyin = logistics_pinyin;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public LogisticsBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public LogisticsBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	
}
