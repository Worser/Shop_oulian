package tst.project.bean.entrust;

public class EntrustBean {

	private int entrust_id;
	private String entrust_number;
	private String goods_name;
	private String goods_num;
	private String target_unit_price;
	private String data_categories;
	private String end_time;
	private String create_time;
	private String is_delete;
	private String min_buy_num;
	private String min_pack_num;
	private String give_time;
	
	private String data_categories_show;
	
	
	public String getGive_time() {
		return give_time;
	}
	public EntrustBean setGive_time(String give_time) {
		this.give_time = give_time;
		return this;
	}
	public String getMin_buy_num() {
		return min_buy_num;
	}
	public EntrustBean setMin_buy_num(String min_buy_num) {
		this.min_buy_num = min_buy_num;
		return this;
	}
	public String getMin_pack_num() {
		return min_pack_num;
	}
	public EntrustBean setMin_pack_num(String min_pack_num) {
		this.min_pack_num = min_pack_num;
		return this;
	}
	public String getData_categories_show() {
		return data_categories_show;
	}
	public EntrustBean setData_categories_show(String data_categories_show) {
		this.data_categories_show = data_categories_show;
		return this;
	}
	public int getEntrust_id() {
		return entrust_id;
	}
	public EntrustBean setEntrust_id(int entrust_id) {
		this.entrust_id = entrust_id;
		return this;
	}
	public String getEntrust_number() {
		return entrust_number;
	}
	public EntrustBean setEntrust_number(String entrust_number) {
		this.entrust_number = entrust_number;
		return this;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public EntrustBean setGoods_name(String goods_name) {
		this.goods_name = goods_name;
		return this;
	}
	public String getGoods_num() {
		return goods_num;
	}
	public EntrustBean setGoods_num(String goods_num) {
		this.goods_num = goods_num;
		return this;
	}
	public String getTarget_unit_price() {
		return target_unit_price;
	}
	public EntrustBean setTarget_unit_price(String target_unit_price) {
		this.target_unit_price = target_unit_price;
		return this;
	}
	public String getData_categories() {
		return data_categories;
	}
	public EntrustBean setData_categories(String data_categories) {
		this.data_categories = data_categories;
		this.data_categories_show = "1".equals(data_categories)?"现货":
			 ("2".equals(data_categories)?"期货":
				 ("3".equals(data_categories)?"促销":null));
		return this;
	}
	public String getEnd_time() {
		return end_time;
	}
	public EntrustBean setEnd_time(String end_time) {
		this.end_time = end_time;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public EntrustBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public EntrustBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	
}
