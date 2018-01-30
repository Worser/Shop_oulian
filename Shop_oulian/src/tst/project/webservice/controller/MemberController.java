package tst.project.webservice.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import tst.project.bean.ExcelBean;
import tst.project.bean.HostBean;
import tst.project.bean.member.EnterpriseAdvantageBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.others.QrcodeBean;
import tst.project.page.PageBean;
import tst.project.service.controller.EnterpriseAdvantageServiceC;
import tst.project.service.controller.MemberImgServiceC;
import tst.project.service.controller.MemberServiceC;
import tst.project.service.controller.MerchantsService;
import tst.project.service.controller.OthersServiceC;
import tst.project.service.controller.SystemService;
import tst.project.service.interfaces.MemberService;
import tst.project.utils.CreateRandom;
import tst.project.utils.ExcelUtils;
import tst.project.utils.MD5Util;
import tst.project.utils.QRCodeUtils;
import tst.project.utils.TimeUtils;

@Controller
@RequestMapping("/memberController.api")
public class MemberController extends BaseController{
	@Resource
	SystemService systemService;
	
	@Resource
	MemberServiceC memberServiceC;
	
	@Resource
	MemberService memberServiceI;
	
	@Resource
	MemberImgServiceC imgServiceC;
	
	@Resource
	EnterpriseAdvantageServiceC eaServiceC;
	
	@Resource
	OthersServiceC otherServiceC;
	
	@Resource
	MerchantsService merchantsService;
	
	/**
	 * 获得分销的用户列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getDistributionMembers", method = RequestMethod.POST)
	public void getDistributionMembers(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		String user_id=request.getParameter("user_id");
		MemberBean memberBean2=memberServiceC.getOneMemberDetailZSSG(new MemberBean().setMember_id(Integer.valueOf(user_id)));
				
		WriteObject(response, memberServiceC.
				getDistributionMembers(memberBean.setInvitation_code(memberBean2.getInvitation_code()),pageBean),pageBean.getTotal());		
	}
	/**
	 * 修改用户图片信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateMemberImg", method = RequestMethod.POST)
	public void updateMemberImg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String json=uploadFile(request, "/images/member/");
		if(json.equals("-1")){
			WriteError(response, "文件不可为空");
		}else if(json.equals("-2")){
			WriteError(response, "上传失败");	
		}else{
			WriteMsg(response, json);
		}
	}
	/**
	 * 修改用户详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateMemberDetail", method = RequestMethod.POST)
	public void updateMemberDetail(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}

		int num= memberServiceC.updateMemberDetail(memberBean);
		if(num>0){
			WriteMsg(response, "修改成功");
		}else{
			WriteMsg(response, "修改失败");
		}		
	}
	
	/**
	 * 单个用户详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneMemberDetail", method = RequestMethod.POST)
	public void getOneMemberDetail(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberServiceC.getOneMemberDetail(memberBean));
	}
	
	/**
	 * 单个用户详情
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getOneMemberDetailZSSG", method = RequestMethod.POST)
	public void getOneMemberDetailZSSG(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberServiceC.getOneMemberDetailZSSG(memberBean));
	}
	
	
	/**
	 * 获得所有普通用户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllMembersCount", method = RequestMethod.POST)
	public void getAllMembersCount(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberServiceC.getAllMembersCount(memberBean));
	}
	
	
	
	/**
	 * 获得所有普通用户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllMembers", method = RequestMethod.POST)
	public void getAllMembers(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}

		WriteObject(response, memberServiceC.getAllMembers(memberBean,pageBean),pageBean.getTotal());
	}
	
	/**
	 * 获得所有普通用户信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getAllMembersZSSG", method = RequestMethod.POST)
	public void getAllMembersZSSG(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,PageBean pageBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberServiceC.getAllMembersZSSG(memberBean,pageBean),pageBean.getTotal());
	}
	
	/**
	 * 解冻或冻结账号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateIsFreeze", method = RequestMethod.POST)
	public void updateIsFreeze(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = memberServiceC.updateIsFreeze(memberBean);
		if(num > 0)
		{
			WriteObject(response, "操作成功");
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
	/**
	 * 查询用户等级
	 */
	@RequestMapping(params = "getMemberLevel", method = RequestMethod.POST)
	public void getMemberLevel(MerchantsAccountBean merchantsAccountBean,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, memberServiceC.getMemberLevel());
	}
	
	/**
	 * 获得用户图片
	 */
	@RequestMapping(params = "getMemberImgList", method = RequestMethod.POST)
	public void getMemberImgList(MerchantsAccountBean merchantsAccountBean,
			HttpServletRequest request,
			HttpServletResponse response,
			MemberImgBean memberImgBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, imgServiceC.getMemberImgList(memberImgBean));
	}
	
	/**
	 * 获得用户企业优势
	 */
	@RequestMapping(params = "getEnterpriseAdvantageList", method = RequestMethod.POST)
	public void getEnterpriseAdvantageList(MerchantsAccountBean merchantsAccountBean,
			HttpServletRequest request,
			HttpServletResponse response,
			EnterpriseAdvantageBean enterpriseAdvantageBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, eaServiceC.getEnterpriseAdvantageList(enterpriseAdvantageBean));
	}
	
	/**
	 * 添加会员
	 */
	@RequestMapping(params = "addMember", method = RequestMethod.POST)
	public void addMember(MerchantsAccountBean merchantsAccountBean,
			HttpServletRequest request,
			HttpServletResponse response,
			MemberBean memberBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		if(memberServiceI.getMemberByMobile(memberBean)!=null){
			WriteError(response, "该账号已注册过");
			return;
		}
		
		memberBean.setPhone(memberBean.getMember_account());
		memberBean.setIs_vip("1");
		memberBean.setIs_certification_vip("0");
		
		memberBean.setPassword(MD5Util.md5Encode(memberBean.getPassword()));
		String pass= MD5Util.md5Encode(memberBean.getPassword());
		String t=CreateRandom.createRandom(true, 6);
		String token = UUID.randomUUID().toString();
		
		HostBean hostBean=otherServiceC.getHost(new HostBean().setHost_type("1"));
		
		memberBean.setNick_name(hostBean.getCompany_name()+CreateRandom.createRandom(true, 6));
		
		String qrcode_img=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true, 6)+".png";
		
		String invitation_code=UUID.randomUUID().toString();

		QrcodeBean qrcodeBean=otherServiceC.getQrcodeSetting(new QrcodeBean().setQrcode_type("member"));
	
		if(qrcodeBean!=null){
			boolean is_success=QRCodeUtils.CreateQrcode(request, "/images/qrcode/business_goods/"+qrcode_img, 
					hostBean.getHost_url()+qrcodeBean.getQrcode_desc()+invitation_code);
			if(!is_success){
				WriteError(response, "二维码注册失败!");
				return;
			}	
		}
		    MerchantsBean merchantsBean = new MerchantsBean();
		    int num1 = merchantsService.insertMerchantDetail(request, merchantsBean.setMerchants_name(memberBean.getMember_account())
		    		   .setMerchants_state("1").setMerchants_type("3"));
		    if(num1>0)
		    {
		    	MerchantsAccountBean merchantsAccountBean1 = new MerchantsAccountBean();
		    	int num2 = merchantsService.insertMerchantsAccount(merchantsAccountBean1.setMerchants_id(String.valueOf(merchantsBean.getMerchants_id()))
		    			   .setMerchants_name(memberBean.getMember_account()).setMerchants_account(memberBean.getMember_account())
		    			   .setPassword(pass).setMerchants_type("3").setRole_id("1")
		    			   .setMerchants_token(token), request);
		    	if(num2>0)
		    	{
		    		int num=memberServiceC.addMember(memberBean.setHx_account(memberBean.getMember_account()+t)
							.setHx_pass(pass)
							.setHx_nick_name(memberBean.getMember_account()+t)
							.setInvitation_code(invitation_code)
							.setQrcode_img("/images/qrcode/business_goods/"+qrcode_img)
							.setMerchants_id(String.valueOf(merchantsBean.getMerchants_id()))
							.setMerchants_account_id(String.valueOf(merchantsAccountBean1.getMerchants_account_id()))
							.setMember_token(token));
					
					if(num>0){
						WriteObject(response, "添加成功");
					}else{
						WriteError(response, "添加失败");
					}	
		    	}else
		    	{
		    		WriteError(response, "商户账户表生成记录失败");
		    	}
		    	
		    }else
		    {
		    	WriteError(response, "商户表中生成记录失败");
		    }
	}
	
	/**
	 * 修改用户等级详情
	 */
	@RequestMapping(params = "updateMemberLevel", method = RequestMethod.POST)
	public void updateMemberLevel(MerchantsAccountBean merchantsAccountBean,MemberLevelBean memberLevelBean,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num=memberServiceC.updateMemberLevel(memberLevelBean);
		if(num>0){
			WriteMsg(response, "更新成功");
		}else{
			WriteError(response, "更新失败");
		}
	}
	
	/**
	 * 导出用户excel
	 */
	@RequestMapping(params = "exportMemberExcel", method = RequestMethod.POST)
	public void exportMemberExcel(MerchantsAccountBean merchantsAccountBean,
			MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("公司名称").setType("company_name"));
		excelBeans.add(new ExcelBean().setName("公司等级").setType("member_level_show"));
		excelBeans.add(new ExcelBean().setName("手机号").setType("member_account"));
		excelBeans.add(new ExcelBean().setName("上门认证").setType("is_door_certification_show"));
		excelBeans.add(new ExcelBean().setName("电话认证").setType("is_mobile_certification_show"));
		excelBeans.add(new ExcelBean().setName("三证齐全").setType("is_certification_vip_show"));
		excelBeans.add(new ExcelBean().setName("账号余额").setType("balance"));
		excelBeans.add(new ExcelBean().setName("是否冻结").setType("is_freeze_show"));
		excelBeans.add(new ExcelBean().setName("法人名称").setType("legal_person_name"));
		excelBeans.add(new ExcelBean().setName("法人性别").setType("sex"));
		
		excelBeans.add(new ExcelBean().setName("邮箱").setType("email_account"));
		excelBeans.add(new ExcelBean().setName("统一社会信用代码").setType("invise_no"));
		excelBeans.add(new ExcelBean().setName("注册地址").setType("register_assress"));
		excelBeans.add(new ExcelBean().setName("注册电话").setType("register_phone"));
		excelBeans.add(new ExcelBean().setName("开户银行").setType("bank_name"));
		excelBeans.add(new ExcelBean().setName("银行卡号").setType("bank_no"));
		excelBeans.add(new ExcelBean().setName("发票地址").setType("invise_address"));
		excelBeans.add(new ExcelBean().setName("固话").setType("fixed_telephone"));
		
		excelBeans.add(new ExcelBean().setName("客服QQ").setType("service_qq"));
		excelBeans.add(new ExcelBean().setName("注册时间").setType("create_time"));
		
		List<Object> memberBeans = memberServiceC.exportMemberExcel(memberBean);
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,memberBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
	}
	
}
