package tst.project.service.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.Flags.Flag;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.finance.CashApplyBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.member.BillBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.EnterpriseAdvantageBean;
import tst.project.bean.member.IntegralBean;
import tst.project.bean.member.MainBrandBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.member.MemberProfitBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.bean.order.OrderProfitBean;
import tst.project.bean.others.CodeBean;
import tst.project.dao.interfaces.MemberDao;
import tst.project.page.PageBean;
import tst.project.service.controller.MerchantsService;
import tst.project.service.controller.SystemService;
import tst.project.utils.MD5Util;
import tst.project.utils.NumberUtils;


@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {
	@Resource
	MemberDao memberDao;

	@Resource
	CodeService codeService;
	
	@Resource
	SystemService systemService;
	
	@Resource
	MerchantsService merchantsService;

	@Resource
	MainBrandService mainBrandService;
	
	@Resource
	MemberImgService memberImgService;
	
	@Resource
	EnterpriseAdvantageService eaService;
	
	@Resource
	MemberQualificationService mqService;
	
	/**
	 * 平台前十销售占比
	 * @param memberBean
	 * @return
	 */
	public List<Map> getAllMerchantsStatistics(MemberBean memberBean){
		float all_sales=memberDao.getAllMerchantsSales(memberBean);
		Map map=new HashMap<String, Object>();
		map.put("all_sales", all_sales);
		List<Map> maps=memberDao.getAllMerchantsStatistics(map);
		return maps;
	}
	
	/**
	 * 平台用户星级统计
	 * @param memberBean
	 * @return
	 */
	public Map getMemberLevelStatistics(MemberBean memberBean){
		int num=memberDao.getAllMemberCount(memberBean);
		Map map=new HashMap<String, Object>();
		map.put("all_count", num);
		
		Map map2=memberDao.getMemberLevelStatistics(map);
		map2.put("all_count", num);
		return map2;
	}
	
	/**
	 * 单个星级权益详情
	 * @param memberLevelBean
	 * @return
	 */
	public MemberLevelBean getMemberLevelDetail(MemberLevelBean memberLevelBean){
		return memberDao.getMemberLevelDetail(memberLevelBean);
	}
	
	/**
	 * 用户收益
	 * @param memberProfitBean
	 * @return
	 */
	public MemberProfitBean getMemberProfitCurMonth(MemberProfitBean memberProfitBean){
		return memberDao.getMemberProfitCurMonth(memberProfitBean);
	}
	
	/**
	 * 用户收益
	 * @param memberProfitBean
	 * @return
	 */
	public int insertMemberProfit(MemberProfitBean memberProfitBean){
		return memberDao.insertMemberProfit(memberProfitBean);
	}
	
	/**
	 * 用户收益
	 * @param memberProfitBean
	 * @return
	 */
	public int updateMemberProfit(MemberProfitBean memberProfitBean){
		return memberDao.updateMemberProfit(memberProfitBean);
	}
	
	/**
	 * 修改自己的用户余额支付密码
	 * @return
	 */
	public int updateMemberBalancePassword(MemberBean memberBean){
		return memberDao.updateMemberBalancePassword(memberBean);
	}
	
	/**
	 * 修改自己的用户信用支付密码
	 * @return
	 */
	public int updateMemberTrustPassword(MemberBean memberBean){
		return memberDao.updateMemberTrustPassword(memberBean);
	}
	
	/**
	 * 修改用户的归属
	 * @return
	 */
	public int updateMemberAttach(MemberBean memberBean){
		return memberDao.updateMemberAttach(memberBean);
	}
	
	/**
	 * 添加用户积分详情
	 * @param integralBean
	 * @return
	 */
	public int insertMemberIntegral(IntegralBean integralBean){
		return memberDao.insertMemberIntegral(integralBean);
	}

	/**
	 * 用户余额记录
	 * @param billBean
	 * @param pageBean
	 * @return
	 */
	public List<BillBean> getMemberBalanceRecord(BillBean billBean,PageBean pageBean){
		return memberDao.getMemberBalanceRecord(billBean, pageBean);
	}
	

	/**
	 * 用户余额记录
	 * @param billBean
	 * @param pageBean
	 * @return
	 */
	public List<BillBean> getMemberTrustRecord(BillBean billBean,PageBean pageBean){
		return memberDao.getMemberTrustRecord(billBean, pageBean);
	}
	
	/**
	 * 用户积分使用情况
	 * @return
	 */
	public List<IntegralBean> getMemberIntegral(IntegralBean integralBean,PageBean pageBean){
		return memberDao.getMemberIntegral(integralBean, pageBean);
	}
	
	/**
	 * 申请提现列表
	 * @return
	 */
	public List<CashApplyBean> getApplyCashs(CashApplyBean cashApplyBean,PageBean pageBean){
		return memberDao.getApplyCashs(cashApplyBean,pageBean);
	}
	
	/**
	 * 最近的一次申请提现
	 * @return
	 */
	public CashApplyBean getLastApplyCash(CashApplyBean cashApplyBean){
		return memberDao.getLastApplyCash(cashApplyBean);
	}
	/**
	 * 申请提现
	 * @return
	 */
	public int applyCash(CashApplyBean cashApplyBean){
		return memberDao.applyCash(cashApplyBean);
	}
	
	/**
	 * 用户订单分成收益
	 * @param orderProfitBean
	 * @return
	 */
	public List<OrderProfitBean> getOrderPorfit(OrderProfitBean orderProfitBean){
		return memberDao.getOrderPorfit(orderProfitBean);
	}
	/**
	 * 更新用户归属 家纺
	 * 
	 * @return
	 */
	public int updateMemberBusiness(MemberBean memberBean) {
		return memberDao.updateMemberBusiness(memberBean);
	}

	/**
	 * 验证用户token
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean verificationToken(MemberBean memberBean) {
		return memberDao.verificationToken(memberBean);
	}

	/**
	 * 绑定手机号
	 * 
	 * @param memberBean
	 * @param codeBean
	 * @return
	 */
	public int memberBindMobile(MemberBean memberBean, CodeBean codeBean) {
		try {
			if(memberBean.getPassword()!=null&&!memberBean.getPassword().equals("")){
				memberBean.setPassword(MD5Util.md5Encode(memberBean.getPassword()));				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		int num = memberDao.memberBindMobile(memberBean);
		if (num > 0) {
			codeService.deleteCodeByMobileAndCode(codeBean);
		}
		return num;
	}

	/**
	 * 绑定手机号
	 * 
	 * @param memberBean
	 * @param codeBean
	 * @return
	 * @throws Exception
	 */
	public int memberBindMobileZSSG(MemberBean memberBean, CodeBean codeBean) throws Exception {
		memberBean.setPassword(MD5Util.md5EncodeOrigin(memberBean.getPassword()));
		UUID uuid = UUID.randomUUID();
		int num = wxPubMemberRegisterZSSG(memberBean.setMember_token(uuid.toString()));
		if (num > 0){
			codeService.deleteCodeByMobileAndCode(codeBean);
		}
//		
//		memberBean.setPassword(MD5Util.md5EncodeOrigin(memberBean.getPassword()));
//		int num = memberDao.memberBindMobileZSSG(memberBean);
//		if (num > 0) {
//			codeService.deleteCodeByMobileAndCode(codeBean);
//		} else {
//			throw new Exception("绑定失败");
//		}
		return num;
	}
	/**
	 * 绑定手机号
	 * 
	 * @param memberBean
	 * @param codeBean
	 * @return
	 * @throws Exception
	 */
	public int memberBindMobileZSSG1(MemberBean memberBean, CodeBean codeBean) throws Exception {
		int num =memberDao.memberBindMobileZSSG(memberBean);
		if (num > 0){
			codeService.deleteCodeByMobileAndCode(codeBean);
		}
		return num;
	}
	/**
	 * 忘记密码
	 * 
	 * @return
	 */
	public int memberForgetPassword(MemberBean memberBean, CodeBean codeBean) {
		try {
			memberBean.setPassword(MD5Util.md5Encode(memberBean.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		int num = memberDao.memberForgetPassword(memberBean);
		if (num > 0) {
			codeService.deleteCodeByMobileAndCode(codeBean);
		}
		return num;
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	public int memberRegister(MemberBean memberBean, CodeBean codeBean) {
		try {
			memberBean.setPassword(MD5Util.md5Encode(memberBean.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		int num = memberDao.memberRegister(memberBean);
		if (num > 0) {
			codeService.deleteCodeByMobileAndCode(codeBean);
		}
		return num;
	}

	/**
	 * 通过手机号 获得用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByMobile(MemberBean memberBean) {
		return memberDao.getMemberByMobile(memberBean);
	}

	/**
	 * 通过手机号 获得用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByMobileZSSG(MemberBean memberBean) {
		return memberDao.getMemberByMobileZSSG(memberBean);
	}

	/**
	 * 通过用户邀请码 获得用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByInvitation_code(MemberBean memberBean) {
		return memberDao.getMemberByInvitation_code(memberBean);
	}

	/**
	 * 通过用户邀请码 获得用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByInvitation_codeZSSG(MemberBean memberBean) {
		return memberDao.getMemberByInvitation_codeZSSG(memberBean);
	}


	/**
	 * 通过用户ID 获得用户信息（不完整信息）
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByIDNoFull(MemberBean memberBean) {
		MemberBean memberBean1=memberDao.getMemberByID(memberBean);
		return memberBean1;
	}
	
	
	/**
	 * 通过用户ID 获得用户信息  （完整信息）
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByID(MemberBean memberBean) {
		MemberBean memberBean1=memberDao.getMemberByID(memberBean);
		if(memberBean1!=null)
		{
			List<GoodsBean> goodsSalesBeans=memberDao.getMemberSalesGoods(new MemberProfitBean()
					.setMerchants_id(memberBean1.getMerchants_id()).setMember_id(memberBean.getMember_account()));
			memberBean1.setGoodsSalesBeans(goodsSalesBeans);
			
			MemberLevelBean memberLevelBean=memberDao.getMemberLevelDetail(new MemberLevelBean()
					.setLevel_id(NumberUtils.Integer(memberBean1.getMember_level())));
			memberBean1.setMemberLevelBean(memberLevelBean);
			
			//资质认证状态
			MemberQualificationBean memberQualificationBean1 = mqService.getMemberQualification(new MemberQualificationBean()
			                        .setMember_id(memberBean.getMember_id()+""));
			if(memberQualificationBean1!=null)
			{
				memberBean1.setQualification_state(memberQualificationBean1.getQualification_state());
				memberBean1.setMemberQualificationBean(memberQualificationBean1);
			}
			
			//主营品牌
			List<MainBrandBean> list1 = mainBrandService.getMainBrandList(new MainBrandBean()
			                            .setMember_id(String.valueOf(memberBean1.getMember_id())));
			
			//公司图片
			List<MemberImgBean> list2 = memberImgService.getMemberImgList(new MemberImgBean()
			                            .setMember_id(String.valueOf(memberBean1.getMember_id()))
			                            .setImg_type("member_company"));
			
			//公司优势
			List<EnterpriseAdvantageBean> list3 = eaService.getEnterpriseAdvantageList(new EnterpriseAdvantageBean()
			                               .setMember_id(String.valueOf(memberBean1.getMember_id())));
			
			List<CustomerServiceBean> customerServiceBeans=memberDao
					.getServiceQQByMerchantsId(new CustomerServiceBean().setMerchants_id(memberBean1.getMerchants_id()));		
			memberBean1.setCustomerServiceBeans(customerServiceBeans);	
			
			memberBean1.setMainBeanList(list1);
			memberBean1.setMemberImgList(list2);
			memberBean1.setAdvantageList(list3);
		}
		return memberBean1;
	}

	/**
	 * 通过用户ID 获得用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByIDZSSG(MemberBean memberBean) {
		return memberDao.getMemberByIDZSSG(memberBean);
	}
	/**
	 * 通过用户ID 获得用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByOpenidZSSG(MemberBean memberBean){
		return memberDao.getMemberByOpenidZSSG(memberBean);
	}

	/**
	 * 用户登录
	 * 
	 * @param memberBean
	 * @return
	 * @throws Exception
	 */
	public MemberBean memberLogin(MemberBean memberBean) {
		MemberBean memberBean1 = null;
		try {
			memberBean.setPassword(MD5Util.md5Encode(memberBean.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		int num = memberDao.updateMemberToken(memberBean);
		if (num > 0) {
			memberBean1 = memberDao.memberLogin(memberBean);
			if(memberBean1!=null)
			{
				//修改商家账户表中token，并查出信息
			    MerchantsAccountBean merchantsAccountBean = new MerchantsAccountBean();
			    systemService.updateToken(merchantsAccountBean.setMerchants_token(memberBean1.getMember_token())
			    		       .setMerchants_account_id(Integer.valueOf(memberBean1.getMerchants_account_id())));
			    memberBean1.setMerchantsAccountBean(merchantsService.getMerchantsAccounts(merchantsAccountBean
			    		    .setMerchants_id(memberBean1.getMerchants_id())).get(0));
			}else
			{
				return null;
			}
			
		} else {
			return null;
		}
		return memberBean1;
	}

	/**
	 * 验证用户token
	 * 
	 * @param memberBean
	 * @return
	 */
	public MemberBean verificationTokenZSSG(MemberBean memberBean) {
		return memberDao.verificationTokenZSSG(memberBean);
	}

	/**
	 * 微信公众号登录
	 * 
	 * @return
	 */
	public MemberBean wxPubMemberLoginZSSG(MemberBean memberBean) {
		return memberDao.wxPubMemberLoginZSSG(memberBean);
	}

	/**
	 * 微信公众号登录
	 * 
	 * @return
	 */
	public MemberBean wxPubMemberLogin(MemberBean memberBean) {
		return memberDao.wxPubMemberLogin(memberBean);
	}

	/**
	 * 微信公众号注册
	 * 
	 * @param memberBean
	 * @return
	 */
	public int wxPubMemberRegister(MemberBean memberBean) {
		return memberDao.wxPubMemberRegister(memberBean);
	}

	/**
	 * 微信公众号 更新
	 * @param memberBean
	 * @return
	 */
	public int wxPubMemberUpdate(MemberBean memberBean){
		return memberDao.wxPubMemberUpdate(memberBean);
	}
	/**
	 * 微信公众号注册
	 * 
	 * @param memberBean
	 * @return
	 */
	public int wxPubMemberRegisterZSSG(MemberBean memberBean) {
		return memberDao.wxPubMemberRegisterZSSG(memberBean);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public int updateMemberDetail(MemberBean memberBean) {
		return memberDao.updateMemberDetail(memberBean);
	}

	/**
	 * 完善资料变vip
	 * @return
	 */
	public int updateMemberDetailVip(MemberBean memberBean){
		return memberDao.updateMemberDetailVip(memberBean);
	}
	/**
	 * 修改用户信息
	 * 
	 * @param memberBean
	 * @return
	 */
	public int updateMemberDetailZSSG(MemberBean memberBean) {
		return memberDao.updateMemberDetailZSSG(memberBean);
	}

	/**
	 * 根据商户id查询用户
	 * @param memberBean
	 * @return
	 */
	public MemberBean getMemberByMerchants(MemberBean memberBean)
	{
		MemberBean memberBean2= memberDao.getMemberByMerchants(memberBean);
		if(memberBean2!=null){
			List<CustomerServiceBean> customerServiceBeans=memberDao
					.getServiceQQByMerchantsId(new CustomerServiceBean().setMerchants_id(memberBean2.getMerchants_id()));		
			memberBean2.setCustomerServiceBeans(customerServiceBeans);	
			
			MemberLevelBean memberLevelBean=memberDao.getMemberLevelDetail(new MemberLevelBean()
					.setLevel_id(NumberUtils.Integer(memberBean2.getMember_level())));
			memberBean2.setMemberLevelBean(memberLevelBean);
		}
		return memberBean2;
	}
	
	/**
	 * 推荐会员（首页）
	 */
	public List<MemberBean> getMemberRecommend(MemberBean memberBean)
	{
		List<MemberBean> memberBeans = memberDao.getMemberRecommend(memberBean);
		if(memberBeans.size()>0)
		{
			for (int i = 0; i < memberBeans.size(); i++) {
				memberBeans.get(i).setSatisfied_score("100");
				//主营品牌
				List<MainBrandBean> mainBeanList = mainBrandService.getMainBrandList(new MainBrandBean()
				                            .setMember_id(String.valueOf(memberBeans.get(i).getMember_id())));
				memberBeans.get(i).setMainBeanList(mainBeanList);
				
				//资质认证状态
				MemberQualificationBean memberQualificationBean1 = mqService.getMemberQualification(new MemberQualificationBean()
				                        .setMember_id(memberBeans.get(i).getMember_id()+""));
				if(memberQualificationBean1!=null)
				{
					memberBeans.get(i).setQualification_state(memberQualificationBean1.getQualification_state());
					memberBeans.get(i).setMemberQualificationBean(memberQualificationBean1);
				}
				
			}
		}
		return memberBeans;
	}
	
	/**
	 * 查询供应商列表 
	 */
	public List<MemberBean> getMemberList(MemberBean memberBean)
	{
		List<MemberBean> memberBeans = new ArrayList<MemberBean>();
		if(memberBean.getBrand_name()!=null &&
		   !memberBean.getBrand_name().equals(""))
		{
			if(memberBean.getIs_authorization()!=null &&
			   memberBean.getIs_authorization().equals("1"))
			{
				//查询品牌下授权的供应商
				memberBeans = memberDao.getBrandAuthorizationMember(memberBean);
				return memberBeans;
			}else if(memberBean.getIs_authorization()!=null &&
					 memberBean.getIs_authorization().equals("0"))
			{
				//获得该品牌没有授权的供应商
				memberBeans = memberDao.getBrandNotAuthorizationMember(memberBean);
				return memberBeans;
			}else
			{
				//查询该品牌下所有供应商（只有供应了该品牌的才能查到）
				memberBeans = memberDao.getBrandAllMember(memberBean);
				return memberBeans;
			}
			
		}else
		{
			return memberDao.getMemberList(memberBean);
		}
		
	}
	
	/**
	 * 获得该品牌授权的供应商
	 */
	public List<MemberBean> getBrandAuthorizationMember(MemberBean memberBean)
	{
		return memberDao.getBrandAuthorizationMember(memberBean);
	}
	
	/**
	 * 设置客服QQ
	 */
	@Transactional
	public int addCustomerServiceQQ(CustomerServiceBean customerServiceBean)
	{
		return memberDao.addCustomerServiceQQ(customerServiceBean);
	}
	
	/**
	 * 查询供应商所设置客服QQ
	 */
	public List<CustomerServiceBean> getMemberCustomerServiceQQ(CustomerServiceBean customerServiceBean)
	{
		return memberDao.getMemberCustomerServiceQQ(customerServiceBean);
	}
	
	/**
	 * 根据商户id查询供应商客服QQ 
	 */
	public List<CustomerServiceBean> getServiceQQByMerchantsId(CustomerServiceBean customerServiceBean)
	{
		return memberDao.getServiceQQByMerchantsId(customerServiceBean);
	}
	
	/**
	 * 根据等级查询会员权益
	 */
	public MemberLevelBean getPrivilegeByLevel(MemberLevelBean memberLevelBean)
	{
		return memberDao.getPrivilegeByLevel(memberLevelBean);
	}
	
	/**
	 * 删除供应商所设置QQ
	 */
	@Transactional
	public int deleteServiceQQ(CustomerServiceBean customerServiceBean)
	{
		return memberDao.deleteServiceQQ(customerServiceBean);
	}
	/**
	 * 
	 * @param memberBean
	 * @return
	 */
	public List<MemberBean> getMemberInformation(MemberBean memberBean)
	  {
	    List<MemberBean> memberBeans = memberDao
	      .getMemberInformation(memberBean);
	    if (memberBeans.size() > 0) {
	      for (int i = 0; i < memberBeans.size(); i++)
	      {
	        List<MainBrandBean> mainBeanList = mainBrandService
	          .getMainBrandList(new MainBrandBean()
	          .setMember_id(String.valueOf(((MemberBean)memberBeans.get(i))
	          .getMember_id())));
	        ((MemberBean)memberBeans.get(i)).setMainBeanList(mainBeanList);

	        List<CustomerServiceBean> getMemberCustomerServiceQQ = memberDao
	          .getMemberCustomerServiceQQ(new CustomerServiceBean()
	          .setMember_id(String.valueOf(((MemberBean)memberBeans.get(i))
	          .getMember_id())));
	        memberBeans.get(i).setCustomerServiceBeans(
	          getMemberCustomerServiceQQ);
	      }
	    }
	    return memberBeans;
	  }
	/**
	 * 
	 * @param memberBean
	 * @return
	 */
	  public MemberBean getMemberByComments(MemberBean memberBean)
	  {
	    MemberBean memberBean2 = memberDao.getMemberByComments(memberBean);
	    return memberBean2;
	  }
	  /**
	   * 获得所有的供应商
	   * @param memberBean
	   * @return
	   */
	  public List<MemberBean> getAllSupplier(MemberBean memberBean){
		  List<MemberBean> memberBeans = memberDao.getAllSupplier(memberBean);
		  return memberBeans;
	  }
	
}
