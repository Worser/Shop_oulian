package tst.project.bean.bbs;

import java.util.List;

import tst.project.utils.StringHandler;

public class CategoryBean {
	private int id; // 类别ID
	private String categoryName; // 类别名称
	private String categoryInfo; // 类别描述
	private int categoryOrder; // 类别排列次序
	private String categoryFoundTime; // 类别创建时间
	private List masters; // 类别的所有类主名称

	public int getId() {
		return id;
	}

	public CategoryBean setId(int id) {
		this.id = id;
		return this;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public CategoryBean setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		return this;
	}

	public String getCategoryInfo() {
		return categoryInfo;
	}

	public CategoryBean setCategoryInfo(String categoryInfo) {
		this.categoryInfo = categoryInfo;
		return this;
	}

	public int getCategoryOrder() {
		return categoryOrder;
	}

	public CategoryBean setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
		return this;
	}

	public String getCategoryFoundTime() {
		return categoryFoundTime;
	}

	public CategoryBean setCategoryFoundTime(String categoryFoundTime) {
		this.categoryFoundTime = categoryFoundTime;
		return this;
	}

	public List getMasters() {
		return masters;
	}

	public CategoryBean setMasters(List masters) {
		this.masters = masters;
		return this;
	}

}
