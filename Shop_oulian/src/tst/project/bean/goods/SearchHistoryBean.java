package tst.project.bean.goods;

import java.util.List;
import tst.project.bean.member.CustomerServiceBean;

public class SearchHistoryBean
{
  private String member_id;
  private String company_name;
  private String company_remark;
  private String member_account;
  private String legal_person_name;
  private String search_name;
  private String create_time;
  private String start_time;
  private String end_time;
  private List<CustomerServiceBean> customerServiceBeans;

  public String getMember_id()
  {
    return this.member_id;
  }
  public SearchHistoryBean setMember_id(String member_id) {
    this.member_id = member_id;
    return this;
  }
  public String getMember_account() {
    return this.member_account;
  }
  public String getLegal_person_name() {
    return this.legal_person_name;
  }
  public SearchHistoryBean setLegal_person_name(String legal_person_name) {
    this.legal_person_name = legal_person_name;
    return this;
  }
  public SearchHistoryBean setMember_account(String member_account) {
    this.member_account = member_account;
    return this;
  }
  public String getCompany_name() {
    return this.company_name;
  }
  public SearchHistoryBean setCompany_name(String company_name) {
    this.company_name = company_name;
    return this;
  }
  public String getCompany_remark() {
    return this.company_remark;
  }
  public SearchHistoryBean setCompany_remark(String company_remark) {
    this.company_remark = company_remark;
    return this;
  }
  public String getSearch_name() {
    return this.search_name;
  }
  public SearchHistoryBean setSearch_name(String search_name) {
    this.search_name = search_name;
    return this;
  }
  public String getCreate_time() {
    return this.create_time;
  }
  public SearchHistoryBean setCreate_time(String create_time) {
    this.create_time = create_time;
    return this;
  }
  public List<CustomerServiceBean> getCustomerServiceBeans() {
    return this.customerServiceBeans;
  }

  public SearchHistoryBean setCustomerServiceBeans(List<CustomerServiceBean> customerServiceBeans) {
    this.customerServiceBeans = customerServiceBeans;
    return this;
  }
  public String getStart_time() {
    return this.start_time;
  }
  public SearchHistoryBean setStart_time(String start_time) {
    this.start_time = start_time;
    return this;
  }
  public String getEnd_time() {
    return this.end_time;
  }
  public SearchHistoryBean setEnd_time(String end_time) {
    this.end_time = end_time;
    return this;
  }
}