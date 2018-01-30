package tst.project.bean.bbs;

public class CollectionTopicBean {
	private int collection_id;
	private int member_id;
	private String relation_id;
	private String create_time;
	private String collection_state;
	private String is_delete;

	public int getCollection_id() {
		return collection_id;
	}

	public CollectionTopicBean setCollection_id(int collection_id) {
		this.collection_id = collection_id;
		return this;
	}

	public int getMember_id() {
		return member_id;
	}

	public CollectionTopicBean setMember_id(int member_id) {
		this.member_id = member_id;
		return this;
	}

	public String getRelation_id() {
		return relation_id;
	}

	public CollectionTopicBean setRelation_id(String relation_id) {
		this.relation_id = relation_id;
		return this;
	}

	public String getCreate_time() {
		return create_time;
	}

	public CollectionTopicBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}

	public String getCollection_state() {
		return collection_state;
	}

	public CollectionTopicBean setCollection_state(String collection_state) {
		this.collection_state = collection_state;
		return this;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public CollectionTopicBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}

}