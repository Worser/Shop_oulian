package tst.project.bean.goods;

public class StoreHouseBean {
	private int storehouse_id;
	private String storehouse_name;
	private int is_delete;
	private String create_time;
	
	
	public int getStorehouse_id() {
		return storehouse_id;
	}
	public StoreHouseBean setStorehouse_id(int storehouse_id) {
		this.storehouse_id = storehouse_id;
		return this;
	}
	public String getStorehouse_name() {
		return storehouse_name;
	}
	public StoreHouseBean setStorehouse_name(String storehouse_name) {
		this.storehouse_name = storehouse_name;
		return this;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public StoreHouseBean setIs_delete(int is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public StoreHouseBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	
}
