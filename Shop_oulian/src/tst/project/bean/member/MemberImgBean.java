package tst.project.bean.member;

public class MemberImgBean {

	private Integer member_img_id;
	private String member_id;
	private String member_qualification_id;
	private String member_img;
	private String img_remark; //id_card:身份证照  business_license:营业执照 related_authorization:相关执照
	private String img_type;
	private String is_delete;
	private String create_time;
	
	public String getMember_qualification_id() {
		return member_qualification_id;
	}
	public MemberImgBean setMember_qualification_id(String member_qualification_id) {
		this.member_qualification_id = member_qualification_id;
		return this;
	}
	public Integer getMember_img_id() {
		return member_img_id;
	}
	public MemberImgBean setMember_img_id(Integer member_img_id) {
		this.member_img_id = member_img_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public MemberImgBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getMember_img() {
		return member_img;
	}
	public MemberImgBean setMember_img(String member_img) {
		this.member_img = member_img;
		return this;
	}
	public String getImg_remark() {
		return img_remark;
	}
	public MemberImgBean setImg_remark(String img_remark) {
		this.img_remark = img_remark;
		return this;
	}
	public String getImg_type() {
		return img_type;
	}
	public MemberImgBean setImg_type(String img_type) {
		this.img_type = img_type;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public MemberImgBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public MemberImgBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	
}
