package tst.project.webservice.interfaces;

import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Select;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.zxing.qrcode.encoder.QRCode;
import com.sun.swing.internal.plaf.basic.resources.basic;

import jxl.demo.XML;
import tst.project.bean.HostBean;
import tst.project.bean.finance.CashApplyBean;
import tst.project.bean.member.BankBean;
import tst.project.bean.member.BillBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.EnterpriseAdvantageBean;
import tst.project.bean.member.IntegralBean;
import tst.project.bean.member.MainBrandBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.merchants.MerchantsImgBean;
import tst.project.bean.order.OrderProfitBean;
import tst.project.bean.others.CodeBean;
import tst.project.bean.others.QrcodeBean;
import tst.project.bean.wx.WXPubBean;
import tst.project.bean.wx.WXSetingBean;
import tst.project.page.PageBean;
import tst.project.service.controller.MerchantsService;
import tst.project.service.interfaces.BankService;
import tst.project.service.interfaces.CodeService;
import tst.project.service.interfaces.EnterpriseAdvantageService;
import tst.project.service.interfaces.MainBrandService;
import tst.project.service.interfaces.MemberImgService;
import tst.project.service.interfaces.MemberQualificationService;
import tst.project.service.interfaces.MemberService;
import tst.project.service.interfaces.OthersService;
import tst.project.utils.CreateRandom;
import tst.project.utils.HBRUtils;
import tst.project.utils.HuanXinUtils;
import tst.project.utils.MD5Util;
import tst.project.utils.QRCodeUtils;
import tst.project.utils.TimeUtils;
import tst.project.utils.WXUtils;
import tst.project.utils.XmlUtils;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/memberInterfaces.api")
public class MemberInterfaces extends BaseController{
	@Resource
	MemberService memberService;
	
	@Resource
	CodeService codeService;
	
	@Resource
	OthersService othersService;
	
	@Resource
	BankService bankService;
	
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
	 * 单个星级权益详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberLevelDetail", method = RequestMethod.POST)
	public void getMemberLevelDetail(MemberLevelBean memberLevelBean,BillBean billBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		WriteObject(response, memberService.getMemberLevelDetail(memberLevelBean));
	}
	
	
	/**
	 * 平台前十销售占比
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllMerchantsStatistics", method = RequestMethod.POST)
	public void getAllMerchantsStatistics(MemberBean memberBean,BillBean billBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		WriteObject(response, memberService.getAllMerchantsStatistics(memberBean));
	}
	
	
	
	/**
	 * 平台用户星级统计
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberLevelStatistics", method = RequestMethod.POST)
	public void getMemberLevelStatistics(MemberBean memberBean,BillBean billBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		WriteObject(response, memberService.getMemberLevelStatistics(memberBean));
	}
	
	
	/**
	 * 用户积分列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOthersMemberDetail", method = RequestMethod.POST)
	public void getOthersMemberDetail(MemberBean memberBean,BillBean billBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id=request.getParameter("id");
		WriteObject(response, memberService.getMemberByID(memberBean.setMember_account(id)));
	}
	
	
	/**
	 * 用户积分列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberBalanceRecord", method = RequestMethod.POST)
	public void getMemberBalanceRecord(MemberBean memberBean,BillBean billBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		WriteObject(response, memberService
				.getMemberBalanceRecord(billBean,pageBean),pageBean.getTotal());
	}
	
	/**
	 * 用户积分列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberTrustRecord", method = RequestMethod.POST)
	public void getMemberTrustRecord(MemberBean memberBean,BillBean billBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		WriteObject(response, memberService
				.getMemberTrustRecord(billBean,pageBean),pageBean.getTotal());
	}
	
	/**
	 * 修改余额支付密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateMemberBalancePassword", method = RequestMethod.POST)
	public void updateMemberBalancePassword(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		MemberBean memberBean1=memberService.verificationToken(memberBean) ;
		if (memberBean1== null) {
			WritePending(response, "token failed");
			return;
		}		
		
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean.getMember_account()).setCode_type("balance_passwrod").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}
		
		String pass= MD5Util.md5Encode(memberBean.getBalance_password());
		
		int num=memberService
				.updateMemberBalancePassword(memberBean.setBalance_password(pass));
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	
	

	/**
	 * 修改信用支付密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateMemberTrustPassword", method = RequestMethod.POST)
	public void updateMemberTrustPassword(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		MemberBean memberBean1=memberService.verificationToken(memberBean) ;
		if (memberBean1== null) {
			WritePending(response, "token failed");
			return;
		}		
		
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean.getMember_account()).setCode_type("trust_passwrod").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}
		
		String pass= MD5Util.md5Encode(memberBean.getTrust_password());
		
		int num=memberService
				.updateMemberTrustPassword(memberBean.setTrust_password(pass));
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	
	/**
	 * 修改用户的归属
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateMemberAttach", method = RequestMethod.POST)
	public void updateMemberAttach(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		MemberBean memberBean1=memberService.verificationToken(memberBean) ;
		if (memberBean1== null) {
			WritePending(response, "token failed");
			return;
		}		
		
		String attach_id=request.getParameter("attach_id");
		if(attach_id==null||attach_id.equals("")){
			WriteError(response, "参数错误");
			return;
		}
		
		if(attach_id.equals(memberBean.getMember_id()+"")){
			WriteError(response, "不可更改自己为归属");
			return;
		}
		
		MemberBean memberBean2=memberService.getMemberByID(new MemberBean().setMember_id(Integer.valueOf(attach_id)));
		if(memberBean2==null){
			WriteError(response, "该会员不存在");
			return;
		}
		
		if(memberBean1.getInvitation_code().equals(memberBean2.getFill_invitation_code())){
			WriteError(response, "不可让下级会员,做为自己的归属");
			return;
		}
		
		int num=memberService.updateMemberAttach(memberBean.setFill_invitation_code(memberBean2.getInvitation_code()));
		if(num>0){
			WriteMsg(response, "绑定成功");
		}else{
			WriteError(response, "绑定失败");
		}
	}
	
	
	/**
	 * 用户积分列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberIntegral", method = RequestMethod.POST)
	public void getMemberIntegral(MemberBean memberBean,IntegralBean integralBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		
		WriteObject(response, memberService
				.getMemberIntegral(integralBean,pageBean),pageBean.getTotal());
	}
	
	
	
	/**
	 * 申请提现列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getApplyCashs", method = RequestMethod.POST)
	public void getApplyCashs(MemberBean memberBean,CashApplyBean cashApplyBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}		
		WriteObject(response, memberService
				.getApplyCashs(cashApplyBean.setMerchants_id(memberBean.getMember_id()+""),pageBean),pageBean.getTotal());
	}
	
	
	/**
	 * 申请提现
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "applyCash", method = RequestMethod.POST)
	public void applyCash(MemberBean memberBean,BankBean bankBean,CodeBean codeBean,CashApplyBean cashApplyBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{		
		
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		if(codeBean.getCode()!=null){
			if(codeService.getCodeBeanByMobileAndCode(codeBean
					.setMobile(memberBean2.getMember_account()).setCode_type("apply_cash")
					.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
				WriteError(response, "此验证码已过期");
				return;
			}	
			
			if(memberBean2.getBalance_password()==null||"".equals(memberBean2.getBalance_password())){
				WriteError(response, "请先设置支付密码");
				return;
			} 
			
			if(!memberBean2.getBalance_password().equals(MD5Util.md5Encode(memberBean.getBalance_password()))){
				WriteError(response, "支付密码错误");
				return;
			}
		}
		
		if(bankBean.getBank_id()!=0){
			BankBean bankBean2=bankService.getMemberDefaultBank(bankBean);
			if(bankBean2==null){
				WriteError(response, "银行卡不存在");
				return;
			}	
			cashApplyBean
			.setMerchants_id(memberBean.getMember_id()+"")
			.setCash_type("member")
			.setBrank_code(bankBean2.getBank_code())
			.setBrank_name(bankBean2.getBank_name())
			.setBrank_open_mobile(bankBean2.getBank_mobile())
			.setBrank_open_usr(bankBean.getBank_user_name())
			.setBrank_open_name(bankBean.getBank_open_name());
		}
		
		HostBean hostBean=othersService.getHost(new HostBean().setHost_type("1"));
		if("fuzhuang".equals(hostBean.getCompany_name())){
			CashApplyBean cashApplyBean2=memberService.getLastApplyCash(cashApplyBean.setMerchants_id(memberBean.getMember_id()+""));
			if(cashApplyBean2!=null){
				if(TimeUtils
						.getDayCompareDate(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"), cashApplyBean2.getCreate_time(), "yyyy-MM-dd HH:mm:ss")<=7){
					WriteError(response, "7天内不可以重复申请");
					return;
				}
			}
		}
		
		int num=memberService.applyCash(cashApplyBean.setMerchants_id(memberBean.getMember_id()+""));
		if(num>0){
			WriteMsg(response, "申请成功");
		}else{
			WriteError(response, "申请失败");
		}
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOrderPorfit", method = RequestMethod.POST)
	public void getOrderPorfit(MemberBean memberBean,OrderProfitBean orderProfitBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberService.getOrderPorfit(orderProfitBean));
	}
	
	
	/**
	 * 会员申请（家纺）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "applyMember", method = RequestMethod.POST)
	public void applyMember(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if ( memberBean2== null) {
			WritePending(response, "token failed");
			return;
		}
		
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean.getPhone()).setCode_type("apply_member").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}
		
		HostBean hostBean = othersService.getHost(new HostBean().setHost_type("1"));
		if (hostBean != null && hostBean.getCompany_name() != null
				&&"hbr".equals(hostBean.getCompany_name())) {
			String xml="";
			xml=HBRUtils.upInsider(memberBean.setMember_code(memberBean2.getMember_code()));
			WriteOnlyMsg(response, xml);
		}

		int num=memberService.updateMemberDetailVip(memberBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	
	/**
	 * 绑定储值卡号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "bindStoredCode", method = RequestMethod.POST)
	public void bindStoredCode(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		MemberBean memberBean2=memberService.verificationToken(memberBean);
		if (memberBean2 == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String xml=HBRUtils.cardBinding(memberBean.setMember_code(memberBean2.getMember_account()));
		Document document=XmlUtils.getDocumentByXml(xml);
		String result=XmlUtils.getValueByTagName(document, "success");
		WriteOnlyMsg(response, result);
	}
	
	
	
	/**
	 * 获得用户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberDetail", method = RequestMethod.POST)
	public void getMemberDetail(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		MemberBean memberBean1 = memberService.getMemberByID(memberBean);
		
		WriteObject(response, memberBean1);
	}
	
	/**
	 * 获得用户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberDetailZSSG", method = RequestMethod.POST)
	public void getMemberDetailZSSG(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if (memberService.verificationTokenZSSG(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberService.getMemberByIDZSSG(memberBean));
	}
	
	
	/**
	 * 绑定手机号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "memberBindMobile", method = RequestMethod.POST)
	public void memberBindMobile(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{	    
		if(memberService.verificationToken(memberBean)==null){
			WritePending(response, "token failed");
			return;
		}
		
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean.getMember_account())
				.setCode_type("bind_mobile").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}	
		
		MemberBean memberBean2=memberService.getMemberByMobile(memberBean);
		if(memberBean2!=null){
			WriteError(response, "该手机号已注册过");
			return;
		}
		
		int num=memberService.memberBindMobile(memberBean,codeBean);
		if(num>0){
			
			WriteMsg(response, "绑定成功");
		}else{
			WriteError(response, "绑定失败");
		}	
	}
	
	
	
	/**
	 * 绑定手机号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "memberBindMobileZSSG", method = RequestMethod.POST)
	public void memberBindMobileZSSG(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{	    
//		if(memberService.verificationTokenZSSG(memberBean)==null){
//			WritePending(response, "token failed");
//			return;
//		}
		
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean.getMember_account())
				.setCode_type("bind_mobile").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}	
		
		MemberBean memberBean2=memberService.getMemberByMobileZSSG(memberBean);
		if(memberBean2!=null&&memberBean2.getWx_pub_openid()!=null
				&&!"".equals(memberBean2.getWx_pub_openid())){
			WriteError(response, "该手机号已注册过");				
			return;
		}
		
		MemberBean memberBean3=memberService.getMemberByOpenidZSSG(memberBean);
		if(memberBean3!=null&&memberBean3.getMobile_phone()!=null&&!"".equals(memberBean3.getMobile_phone())){
			WriteError(response, "该微信号已绑定过手机");				
			return;
		}
		
		int num=0;
		if(memberBean2==null&&memberBean3==null){
			memberBean.setNick_name(memberBean.getNick_name()==null||"".equals(memberBean.getNick_name())?"...":memberBean.getNick_name()
					.replaceAll("[\ue000-\uefff]","")
					.replaceAll("[\ud83c\udc00-\ud83c\udfff]","")
					.replaceAll("[\ud83d\udc00-\ud83d\udfff]","")
					.replaceAll("[\u2600-\u27ff]",""));
			memberBean.setUser_name(memberBean.getUser_name()==null||"".equals(memberBean.getUser_name())?"...":memberBean.getUser_name()
					.replaceAll("[\ue000-\uefff]","")
					.replaceAll("[\ud83c\udc00-\ud83c\udfff]","")
					.replaceAll("[\ud83d\udc00-\ud83d\udfff]","")
					.replaceAll("[\u2600-\u27ff]",""));
			num=memberService.memberBindMobileZSSG(memberBean,codeBean);	
		}else{
			num=memberService.memberBindMobileZSSG1(memberBean,codeBean);
		}
		
		if(num>0){
			WriteObject(response, memberService.wxPubMemberLoginZSSG(memberBean));
		}else{
			WriteError(response, "绑定失败");
		}	
	}
	
	
	/**
	 * 忘记密码(手机号密码注册)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "memberForgetPassword", method = RequestMethod.POST)
	public void memberForgetPassword(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		MemberBean memberBean2=memberService.getMemberByMobile(memberBean);
		if(memberBean2==null){
			WriteError(response, "该手机号未注册");
			return;
		}
		
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean.getMember_account()).setCode_type("forget_passwrod").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}
		
		if(memberBean.getPassword().length()<6 || memberBean.getPassword().length()>18)
		{
			WriteError(response, "密码必须在6位到18位之间");
			return;
		}
		
		if(!memberBean.getPassword().matches("^[a-zA-Z0-9_]+$"))
		{
			WriteError(response, "密码存在特殊字符");
			return;
		}
		
		int num=memberService.memberForgetPassword(memberBean,codeBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}	
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "memberRegister", method = RequestMethod.POST)
	public void memberRegister(MemberBean memberBean,CodeBean codeBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		if(codeService.getCodeBeanByMobileAndCode(codeBean.setMobile(memberBean.getMember_account()).setCode_type("member_register").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")))==null){
			WriteError(response, "此验证码已过期");
			return;
		}
		
		if(memberService.getMemberByMobile(memberBean)!=null){
			WriteError(response, "该账号已注册过");
			return;
		}
		
		if(memberBean.getPassword().length()<6 || memberBean.getPassword().length()>18)
		{
			WriteError(response, "密码必须在6位到18位之间");
			return;
		}
		
		if(!memberBean.getPassword().matches("^[a-zA-Z0-9_]+$"))
		{
			WriteError(response, "密码存在特殊字符");
			return;
		}
		
		
		
		memberBean.setPhone(memberBean.getMember_account());
		memberBean.setIs_vip("1");
		memberBean.setIs_certification_vip("0");
		
		
		String pass= MD5Util.md5Encode(memberBean.getPassword());
		String t=CreateRandom.createRandom(true, 6);
		String token = UUID.randomUUID().toString();
		
		HostBean hostBean=othersService.getHost(new HostBean().setHost_type("1"));
		
		memberBean.setNick_name(hostBean.getCompany_name()+CreateRandom.createRandom(true, 6));
		
		String qrcode_img=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true, 6)+".png";
		
		String invitation_code=UUID.randomUUID().toString();

		QrcodeBean qrcodeBean=othersService.getQrcodeSetting(new QrcodeBean().setQrcode_type("member"));
	
		if(qrcodeBean!=null){
			boolean is_success=QRCodeUtils.CreateQrcode(request, "/images/qrcode/business_goods/"+qrcode_img, 
					hostBean.getHost_url()+qrcodeBean.getQrcode_desc()+invitation_code);
			if(!is_success){
				WriteError(response, "二维码注册失败!");
				return;
			}	
		}

		/*if (hostBean != null && hostBean.getCompany_name() != null
				&&"hbr".equals(hostBean.getCompany_name())) {
			String xml="";
			xml=HBRUtils.registerMember(memberBean);
			String success=XmlUtils.getValueByTagName(XmlUtils.getDocumentByXml(xml), "success");
			String arr[]=success.split(",");
			
			int index=arr[0].indexOf("注册成功！会员卡号:");
			if(index>=0){//新会员注册
				String member_code=arr[0].substring("注册成功！会员卡号:".length(),arr[0].length());			
				String stored_code=arr[1].substring("储值卡卡号:".length(), arr[1].length());
				memberBean.setMember_code(member_code);
				memberBean.setStored_code(stored_code);
				memberBean.setIs_vip("0");
			}else{
				String member_code=arr[1].substring("会员卡号为:".length(),arr[1].length());
				xml=HBRUtils.getInsiderInfo(member_code);
				
				Document document=XmlUtils.getDocumentByXml(xml);
				memberBean.setSex("1".equals(XmlUtils.getValueByTagName(document, "SEX"))?"m":"w");
				memberBean.setReal_name(XmlUtils.getValueByTagName(document, "INSIDERNAME"));
				memberBean.setNick_name(XmlUtils.getValueByTagName(document, "WEBUSERNAME"));
				memberBean.setAge(XmlUtils.getValueByTagName(document, "LUNARBIRTHDAY"));
				memberBean.setPhone(XmlUtils.getValueByTagName(document, "MOBILE"));
				memberBean.setCard_id(XmlUtils.getValueByTagName(document, "IDCARD"));				
				memberBean.setIs_vip("1");
				memberBean.setMember_code(XmlUtils.getValueByTagName(document, "INSIDERCARDNO"));
			}
		}*/
	
//		if(HuanXinUtils.registerOneUser(memberBean.getMember_account()+t,        
//				pass, memberBean.getMember_account()+t)){
		    //因为用户即是买家也是卖家，那么在商户表中给生成记录
		    MerchantsBean merchantsBean = new MerchantsBean();
		    int num1 = merchantsService.insertMerchantDetail(request, merchantsBean.setMerchants_name(memberBean.getMember_account())
		    		   .setMerchants_state("1").setMerchants_type("3"));
		    if(num1>0)
		    {
		    	MerchantsAccountBean merchantsAccountBean = new MerchantsAccountBean();
		    	int num2 = merchantsService.insertMerchantsAccount(merchantsAccountBean.setMerchants_id(String.valueOf(merchantsBean.getMerchants_id()))
		    			   .setMerchants_name(memberBean.getMember_account()).setMerchants_account(memberBean.getMember_account())
		    			   .setPassword(pass).setMerchants_type("3").setRole_id("1")
		    			   .setMerchants_token(token), request);
		    	if(num2>0)
		    	{
		    		int num=memberService.memberRegister(memberBean.setHx_account(memberBean.getMember_account()+t)
							.setHx_pass(pass)
							.setHx_nick_name(memberBean.getMember_account()+t)
							.setInvitation_code(invitation_code)
							.setQrcode_img("/images/qrcode/business_goods/"+qrcode_img)
							.setMerchants_id(String.valueOf(merchantsBean.getMerchants_id()))
							.setMerchants_account_id(String.valueOf(merchantsAccountBean.getMerchants_account_id()))
							.setMember_token(token),codeBean);
					
					if(num>0){
						MemberBean memberBean2=memberService.getMemberByID(memberBean);	
						//将商户这一角色信息也查出来
						MerchantsAccountBean merchantsAccountBean1 = merchantsService.getMerchantsAccounts(merchantsAccountBean).get(0);
						memberBean2.setMerchantsAccountBean(merchantsAccountBean1);
						WriteObject(response,memberBean2);
					}else{
						WriteError(response, "注册失败");
					}	
		    	}else
		    	{
		    		WriteError(response, "商户账户表生成记录失败");
		    	}
		    	
		    }else
		    {
		    	WriteError(response, "商户表中生成记录失败");
		    }
			
//		}else{
//			WriteError(response, "环信聊天异常");
//		}		
	}
	
	/**
	 * 手机号登录
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "memberLogin",method=RequestMethod.POST)
	public void memberLogin(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UUID uuid=UUID.randomUUID();
		MemberBean memberBean1=memberService.memberLogin(memberBean.setMember_token(uuid.toString()));
		if(memberBean1!=null){
			if(memberBean1.getIs_freeze().equals("1"))
			{
				WriteError(response, "账户已被冻结,请与平台联系");
				return;
			}else
			{
				WriteObject(response,memberBean1);
				return;
			}
		}else{
			WriteError(response, "用户名或者密码错误");
			return;
		}	
	}
	
	/**
	 * 微信公众号登录
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "wxPubMemberLogin",method=RequestMethod.POST)
	public void wxPubMemberLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String code=request.getParameter("code");
		String fill_invitation_code=request.getParameter("fill_invitation_code");
		WXSetingBean wxSetingBean=othersService.getWXSeting(new WXSetingBean().setWeixin_type("1"));
		
		WXPubBean wxPubBean=WXUtils.getWXUserInfo(wxSetingBean.getWeixin_appid(),wxSetingBean.getWeixin_secret(),code,request);
		if(wxPubBean==null){
			WriteError(response, "微信code有误");
			return;
		}
		UUID uuid=UUID.randomUUID();
		MemberBean memberBean=new MemberBean().setWx_pub_openid(wxPubBean.getOpenid())
												.setNick_name(wxPubBean.getNickname())
												.setHead_path(wxPubBean.getHeadimgurl())
												.setMember_token(uuid.toString())
												.setFill_invitation_code(fill_invitation_code);
		
		MemberBean memberBean1=memberService.wxPubMemberLogin(memberBean);
		if(memberBean1!=null){
			int num=memberService.wxPubMemberUpdate(memberBean.setMember_token(uuid.toString()));
			MemberBean memberBean2=memberService.wxPubMemberLogin(memberBean1);
			WriteObject(response,memberBean2);	
		}else{
			
			QrcodeBean qrcodeBean=othersService.getQrcodeSetting(new QrcodeBean().setQrcode_type("member"));
			HostBean hostBean=othersService.getHost(new HostBean().setHost_type("1"));

//			boolean is_success=QRCodeUtils.CreateQrcode(request, "/images/qrcode/business_goods/"+qrcode_img, 
//					hostBean.getHost_url()+"hbrH5/register.html?"
//					+ "fill_invitation_code="+invitation_code);
			String invitation_code=UUID.randomUUID().toString();
			String qrcode_img=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".png";
			if(qrcodeBean!=null){	

				boolean is_success=QRCodeUtils.CreateQrcode(request, "/images/qrcode/business_goods/"+qrcode_img, 
						hostBean.getHost_url()+qrcodeBean.getQrcode_desc()+invitation_code);
				
				if(!is_success){
					WriteError(response, "二维码注册失败!");
					return;
				}	
			}
			int num=memberService
					.wxPubMemberRegister(memberBean.setMember_token(uuid.toString())
					.setInvitation_code(invitation_code)
					.setQrcode_img("/images/qrcode/business_goods/"+qrcode_img));
			if(num>0){
				WriteObject(response, memberService.wxPubMemberLogin(memberBean.setMember_token(uuid.toString())));
			}else{
				WriteError(response, "注册失败");
			}
		}	
	}
	
	
	/**
	 * 微信公众号登录
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "wxPubMemberLoginZSSG",method=RequestMethod.POST)
	public void wxPubMemberLoginZSSG(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code=request.getParameter("code");
		String fill_invitation_code=request.getParameter("fill_invitation_code");
		WXSetingBean wxSetingBean=othersService.getWXSeting(new WXSetingBean().setWeixin_type("1"));
		WXPubBean wxPubBean=WXUtils.getWXUserInfo(wxSetingBean.getWeixin_appid(),wxSetingBean.getWeixin_secret(),code,request);
		if(wxPubBean==null){
			WriteError(response, "微信code有误");
			return;
		}
		UUID uuid=UUID.randomUUID();
		MemberBean memberBean=new MemberBean().setWx_pub_openid(wxPubBean.getOpenid())
												.setNick_name(wxPubBean.getNickname())
												.setUser_name(wxPubBean.getNickname())
												.setHead_path(wxPubBean.getHeadimgurl())
												.setHeadimg(wxPubBean.getHeadimgurl())
												.setMember_token(uuid.toString())
												.setFill_invitation_code(fill_invitation_code);
		MemberBean memberBean1=memberService.wxPubMemberLoginZSSG(memberBean);
		if(memberBean1!=null){
			WriteObject(response,memberBean1);	
		}else{
			WriteObject(response, memberBean);	
//			int num=memberService.wxPubMemberRegisterZSSG(memberBean.setMember_token(uuid.toString()));
//			if(num>0){
//				WriteObject(response, memberService.wxPubMemberLoginZSSG(memberBean.
//				setMember_token(uuid.toString())));
//			}else{
//				WriteError(response, "注册失败");
//			}
		}	
	}
	
	
	/**
	 * 修改用户信息
	 * @param memberBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateMemberDetail",method=RequestMethod.POST)
	public void updateMemberDetail(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		
		MemberBean memberBean2=mqService.getQualificationByCode(memberBean);

		if(memberBean2!=null && memberBean2.getMember_id()!=memberBean.getMember_id()){
			WriteError(response, "此统一社会信用代码已被其他人使用!");
			return;
		}
		
		String json1 = request.getParameter("json1");
		String json2 = request.getParameter("json2");
		String json3 = request.getParameter("json3");
		
		List<MainBrandBean> brandBeans = new Gson().fromJson(json1, new TypeToken<List<MainBrandBean>>(){}.getType());
		List<MemberImgBean>  memberImgBeans= new Gson().fromJson(json2, new TypeToken<List<MemberImgBean>>(){}.getType());
		List<EnterpriseAdvantageBean> advantageBeans = new Gson().fromJson(json3, new TypeToken<List<EnterpriseAdvantageBean>>(){}.getType());
		
		int num=memberService.updateMemberDetail(memberBean);
		if(num>0){
			if(brandBeans!=null)
			{
				mainBrandService.deleteMainBrand(new MainBrandBean().setMember_id(memberBean.getMember_id()+""));
				for (int i = 0; i < brandBeans.size(); i++) {
					int a = mainBrandService.addMainBrand(brandBeans.get(i).setMember_id(memberBean.getMember_id()+"")
							.setIs_delete("0"));
					if(a<0)
					{
						throw new Exception("主营品牌添加失败");
					}
				}
			}
			if(memberImgBeans!=null)
			{
				memberImgService.deleteMemberImg(new MemberImgBean().setMember_id(memberBean.getMember_id()+"")
						        .setImg_type("member_company"));
				for (int i = 0; i < memberImgBeans.size(); i++) {
					if(memberImgBeans.get(i).getMember_img_id()==null ||
					   memberImgBeans.get(i).getMember_img_id().equals(""))
					{
						int a = memberImgService.addMemberImg(memberImgBeans.get(i).setMember_id(memberBean.getMember_id()+"")
								.setIs_delete("0"));
						if(a<0)
						{
							throw new Exception("公司图片添加失败");
						}
					}else
					{
						int a = memberImgService.updateMemberImg(memberImgBeans.get(i));
						if(a<0)
						{
							throw new Exception("公司图片修改失败");
						}
					}
				}
			}
			if(advantageBeans!=null)
			{
				eaService.deleteEnterpriseAdvantage(new EnterpriseAdvantageBean().setMember_id(memberBean.getMember_id()+""));
				for (int i = 0; i < advantageBeans.size(); i++) {
					int a = eaService.addEnterpriseAdvantage(advantageBeans.get(i).setMember_id(memberBean.getMember_id()+"")
							.setIs_delete("0"));
					if(a<0)
					{
						throw new Exception("企业优势添加失败");
					}
				}
			}
			WriteMsg(response, "修改成功");
		}else{
			WriteError(response, "修改失败");
		}
	}
	
	
	/**
	 * 上传用户头像图片
	 * @param merchantsBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadMemberImg")
	public void uploadMemberImg(HttpServletRequest request,
			HttpServletResponse response,MemberBean memberBean) throws Exception{	
		
//		String json=getObjectJson(request, "/images/member/");
//		
//		if(json.equals("-1")){
//			WriteError(response, "文件不可为空");
//		}else if(json.equals("-2")){
//			WriteError(response, "上传失败");	
//		}else{
//			MemberBean memberBean=new Gson().fromJson(json, MemberBean.class);	
			if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
			}

			int num=memberService.updateMemberDetail(memberBean);
			if(num>0){
				WriteMsg(response, memberBean.getHead_path());				
			}else{
				WriteError(response, "入库失败");		
			}
//		}
	}
	
	/**
	 * 上传用户公司图片资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadMemberCompanyImg", method = RequestMethod.POST)
	public void uploadMerchantsImg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		String json=uploadFile(request, "/images/company/");
		if(json.equals("-1")){
			WriteError(response, "文件不可为空");
		}else if(json.equals("-2")){
			WriteError(response, "上传失败");	
		}else{
			WriteMsg(response, json);
		}
	}
	
	//欧联
	/**
	 * 会员推荐（首页）
	 */
	@RequestMapping(params = "getMemberRecommend", method = RequestMethod.POST)
	public void getMemberRecommend(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		WriteObject(response, memberService.getMemberRecommend(new MemberBean()));
	}
	
	
	/**
	 * 查询供应商列表
	 */
	@RequestMapping(params = "getMemberList", method = RequestMethod.POST)
	public void getMemberList(HttpServletRequest request,
			HttpServletResponse response,
			MemberBean memberBean) throws Exception
	{
		WriteObject(response, memberService.getMemberList(memberBean));
	}
	
	/**
	 * 设置客服QQ
	 */
	@RequestMapping(params = "addCustomerServiceQQ", method = RequestMethod.POST)
	public void addCustomerServiceQQ(HttpServletRequest request,
			HttpServletResponse response,
			MemberBean memberBean,
			CustomerServiceBean customerServiceBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int count = 0;
		
		MemberLevelBean  levelBean= memberService.getPrivilegeByLevel(new MemberLevelBean()
                          .setLevel_id(Integer.valueOf(memberBean.getMember_level())));
		if(levelBean!=null)
		{
			count = Integer.valueOf(levelBean.getService_qq_num());
		}
		
		String json = request.getParameter("json");
		List<CustomerServiceBean> customerServiceBeans = new Gson().fromJson(json, new TypeToken<List<CustomerServiceBean>>(){}.getType());
		if(customerServiceBeans!=null)
		{
			memberService.deleteServiceQQ(customerServiceBean);
			for (int i = 0; i < customerServiceBeans.size(); i++) {
				
				List<CustomerServiceBean> beans = memberService.getMemberCustomerServiceQQ(new CustomerServiceBean()
				                               .setMember_id(memberBean.getMember_id()+""));
				if(beans.size() >= count)
				{
					WriteError(response, "不可超出限制范围");
					return;
				}
				
				CustomerServiceBean customerServiceBean1 = customerServiceBeans.get(i);
				
				int num = memberService.addCustomerServiceQQ(customerServiceBean1.setIs_delete("0"));
				if(num < 0)
				{
					throw new Exception("设置客服QQ失败");
				}
			}
			WriteObject(response, "设置成功");
		}else
		{
			WriteError(response, "无任何客服QQ数据");
		}
	}
	
	/**
	 * 查询供应商所设置客服QQ
	 */
	@RequestMapping(params = "getMemberCustomerServiceQQ", method = RequestMethod.POST)
	public void getMemberCustomerServiceQQ(HttpServletRequest request,
			HttpServletResponse response,
			MemberBean memberBean,
			CustomerServiceBean customerServiceBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberService.getMemberCustomerServiceQQ(customerServiceBean));
	}
	
	
	/**
	 * 根据商户id查询供应商客服QQ 
	 */
	@RequestMapping(params = "getServiceQQByMerchantsId", method = RequestMethod.POST)
	public void getServiceQQByMerchantsId(HttpServletRequest request,
			HttpServletResponse response,
			CustomerServiceBean customerServiceBean) throws Exception
	{
		
		WriteObject(response, memberService.getServiceQQByMerchantsId(customerServiceBean));
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param customerServiceBean
	 * @throws Exception
	 */
	@RequestMapping(params="getMemeberDetialByTopics", method=RequestMethod.POST)
	  public void getMemeberDetialByTopics(HttpServletRequest request, HttpServletResponse response, CustomerServiceBean customerServiceBean)
	    throws Exception
	  {
	    WriteObject(response, 
	      memberService.getServiceQQByMerchantsId(customerServiceBean));
	  }
	/**
	 * 获得所有的供应商
	 * @param request
	 * @param response
	 * @param memberBean
	 * @throws Exception
	 */
	@RequestMapping(params="getAllSupplier", method=RequestMethod.POST)
	  public void getAllSupplier(HttpServletRequest request, HttpServletResponse response, 
			  MemberBean memberBean)
	    throws Exception
	  {
	    WriteObject(response, 
	      memberService.getAllSupplier(memberBean));
	  }
}
