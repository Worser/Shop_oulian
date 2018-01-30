package tst.project.bean.member;

import java.util.List;

public class MemberQualificationBean {

	private Integer member_qualification_id;
	private String member_id;
	private String company_name; //公司名称
	private String business_license_number; //营业执照注册号
	private String organization_code; //组织机构代码
	private String legal_person_name; //法人名称
	private String legal_person_sex; //法人性别
	private String legal_person_sex_show;
	private String qualification_state; //wait_audit 待审核 through 通过 refused 拒绝
	private String qualification_state_show;
	private String is_delete;
	private String create_time;
	private String pdf_url;
	
	//一些资质图片list
	private List<MemberImgBean> businessBeans;
	private List<MemberImgBean> idCardBeans;
	private List<MemberImgBean> authorizationBeans;
	
	private String member_qualification_ids;
	
	public String getPdf_url() {
		return pdf_url;
	}
	public MemberQualificationBean setPdf_url(String pdf_url) {
		this.pdf_url = pdf_url;
		return this;
	}
	public String getLegal_person_sex_show() {
		return legal_person_sex_show;
	}
	public MemberQualificationBean setLegal_person_sex_show(String legal_person_sex_show) {
		this.legal_person_sex_show = legal_person_sex_show;
		return this;
	}
	public String getMember_qualification_ids() {
		return member_qualification_ids;
	}
	public MemberQualificationBean setMember_qualification_ids(String member_qualification_ids) {
		this.member_qualification_ids = member_qualification_ids;
		return this;
	}
	public String getQualification_state_show() {
		return qualification_state_show;
	}
	public MemberQualificationBean setQualification_state_show(String qualification_state_show) {
		this.qualification_state_show = qualification_state_show;
		return this;
	}
	public Integer getMember_qualification_id() {
		return member_qualification_id;
	}
	public MemberQualificationBean setMember_qualification_id(Integer member_qualification_id) {
		this.member_qualification_id = member_qualification_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public MemberQualificationBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getCompany_name() {
		return company_name;
	}
	public MemberQualificationBean setCompany_name(String company_name) {
		this.company_name = company_name;
		return this;
	}
	public String getBusiness_license_number() {
		return business_license_number;
	}
	public MemberQualificationBean setBusiness_license_number(String business_license_number) {
		this.business_license_number = business_license_number;
		return this;
	}
	public String getOrganization_code() {
		return organization_code;
	}
	public MemberQualificationBean setOrganization_code(String organization_code) {
		this.organization_code = organization_code;
		return this;
	}
	public String getLegal_person_name() {
		return legal_person_name;
	}
	public MemberQualificationBean setLegal_person_name(String legal_person_name) {
		this.legal_person_name = legal_person_name;
		return this;
	}
	public String getLegal_person_sex() {
		return legal_person_sex;
	}
	public MemberQualificationBean setLegal_person_sex(String legal_person_sex) {
		this.legal_person_sex = legal_person_sex;
		this.legal_person_sex_show = "1".equals(legal_person_sex)?"男":"女";
		return this;
	}
	public String getQualification_state() {
		return qualification_state;
	}
	public MemberQualificationBean setQualification_state(String qualificationState) {
		this.qualification_state = qualificationState;
		this.qualification_state_show = "wait_audit".equals(qualification_state)?"待审核":
				("through".equals(qualification_state)?"通过":
					("refused".equals(qualification_state)?"拒绝":"其他"));
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public MemberQualificationBean setIs_delete(String isDelete) {
		this.is_delete = isDelete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public MemberQualificationBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public List<MemberImgBean> getBusinessBeans() {
		return businessBeans;
	}
	public MemberQualificationBean setBusinessBeans(List<MemberImgBean> businessBeans) {
		this.businessBeans = businessBeans;
		return this;
	}
	public List<MemberImgBean> getIdCardBeans() {
		return idCardBeans;
	}
	public MemberQualificationBean setIdCardBeans(List<MemberImgBean> idCardBeans) {
		this.idCardBeans = idCardBeans;
		return this;
	}
	public List<MemberImgBean> getAuthorizationBeans() {
		return authorizationBeans;
	}
	public MemberQualificationBean setAuthorizationBeans(List<MemberImgBean> authorizationBeans) {
		this.authorizationBeans = authorizationBeans;
		return this;
	}
	
}
