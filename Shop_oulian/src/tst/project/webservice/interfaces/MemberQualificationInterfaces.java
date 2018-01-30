package tst.project.webservice.interfaces;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import tst.project.bean.member.MemberBean;
import tst.project.bean.member.MemberImgBean;
import tst.project.bean.member.MemberQualificationBean;
import tst.project.service.interfaces.MemberImgService;
import tst.project.service.interfaces.MemberQualificationService;
import tst.project.service.interfaces.MemberService;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;

@Controller
@RequestMapping("/memberQualificationInterfaces.api")
public class MemberQualificationInterfaces extends BaseController {

	@Resource
	MemberQualificationService mqService;

	@Resource
	MemberService memberService;

	@Resource
	MemberImgService memberImgService;

	/**
	 * 获得用户资质认证信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getMemberQualification", method = RequestMethod.POST)
	public void getMemberQualification(HttpServletRequest request, HttpServletResponse response, MemberBean memberBean,
			MemberQualificationBean memberQualificationBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		MemberQualificationBean memberQualificationBean1 = mqService.getMemberQualification(memberQualificationBean);
		WriteObject(response, memberQualificationBean1);
	}

	/**
	 * 编辑资质认证
	 * 
	 * @param request
	 * @param response
	 * @param memberBean
	 * @param memberQualificationBean
	 * @throws Exception
	 */
	@RequestMapping(params = "verMemberQualification", method = RequestMethod.POST)
	public void verMemberQualification(HttpServletRequest request, HttpServletResponse response, MemberBean memberBean,
			MemberQualificationBean memberQualificationBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		MemberBean memberBean2=mqService.getQualificationByCode(memberBean);

		if(memberBean2!=null && memberBean2.getMember_id()!=memberBean.getMember_id()){
			WriteError(response, "此统一社会信用代码已被其他人使用!");
		}else{
			WriteMsg(response, "ok");
		}
	}
	/**
	 * 编辑资质认证
	 * 
	 * @param request
	 * @param response
	 * @param memberBean
	 * @param memberQualificationBean
	 * @throws Exception
	 */
	@RequestMapping(params = "editorMemberQualification", method = RequestMethod.POST)
	public void addMemberQualification(HttpServletRequest request, HttpServletResponse response, MemberBean memberBean,
			MemberQualificationBean memberQualificationBean) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		String json1 = request.getParameter("json1");
		String json2 = request.getParameter("json2");
		String json3 = request.getParameter("json3");

		List<MemberImgBean> bean1 = new Gson().fromJson(json1, new TypeToken<List<MemberImgBean>>() {
		}.getType());
		List<MemberImgBean> bean2 = new Gson().fromJson(json2, new TypeToken<List<MemberImgBean>>() {
		}.getType());
		List<MemberImgBean> bean3 = new Gson().fromJson(json3, new TypeToken<List<MemberImgBean>>() {
		}.getType());
		
//		MemberQualificationBean memberQualificationBean2=mqService
//				.getQualificationByCode(memberQualificationBean);
//
//		if(memberQualificationBean2!=null && !memberQualificationBean2.getMember_id().equals(memberBean.getMember_id()+"")){
//			WriteError(response, "此营业证号已被其他人使用!");
//			return;
//		}
		
		if (memberQualificationBean.getMember_qualification_id() == null
				|| memberQualificationBean.getMember_qualification_id().equals("")
				|| memberQualificationBean.getMember_qualification_id() == 0) {
			
//			// 查询公司资质认证是否已被添加
//			MemberQualificationBean bean = mqService
//					.getCompanyIsHave(memberQualificationBean);
//			if (bean == null) {
//
//			} else {
//				if (!bean.getMember_id().equals(memberQualificationBean.getMember_id())) {
//					WriteError(response, "该公司已提交过资质认证，若有疑问请与公司账号负责人沟通");
//					return;
//				}
//			}

			memberService.updateMemberDetail(memberBean.setIs_certification_vip("0"));
			int num = mqService.addMemberQualification(
					memberQualificationBean.setIs_delete("0").setMember_id(memberBean.getMember_id() + "")
							.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
							.setQualification_state("wait_audit"));

			if (num > 0) {
				if (bean1.size() > 0) {
					for (int i = 0; i < bean1.size(); i++) {
						int num1 = memberImgService.addMemberImg(bean1.get(i).setImg_type("business_license")
								.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								.setMember_qualification_id(memberQualificationBean.getMember_qualification_id() + "")
								.setMember_id(memberBean.getMember_id() + ""));
						if (num1 < 0) {
							throw new Exception("图片新增失败");
						}
					}
				}

				if (bean2.size() > 0) {
					for (int i = 0; i < bean2.size(); i++) {
						int num1 = memberImgService.addMemberImg(bean2.get(i).setImg_type("id_card").setIs_delete("0")
								.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								.setMember_qualification_id(memberQualificationBean.getMember_qualification_id() + "")
								.setMember_id(memberBean.getMember_id() + ""));
						if (num1 < 0) {
							throw new Exception("图片新增失败");
						}
					}
				}

				if (bean3.size() > 0) {
					for (int i = 0; i < bean3.size(); i++) {
						int num1 = memberImgService.addMemberImg(bean3.get(i).setImg_type("related_authorization")
								.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								.setMember_qualification_id(memberQualificationBean.getMember_qualification_id() + "")
								.setMember_id(memberBean.getMember_id() + ""));
						if (num1 < 0) {
							throw new Exception("图片新增失败");
						}
					}
				}
				WriteMsg(response, "提交成功");
			} else {
				WriteError(response, "提交失败");
			}
		} else {
			int num = mqService.updateMemberQualification(memberQualificationBean
					.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")).setQualification_state("wait_audit"));
			if (num > 0) {
				memberService.updateMemberDetail(memberBean.setIs_certification_vip("0"));
				if(num<=0){
					WriteError(response, "更新失败");
					return;
				}
				memberImgService.deleteQualificationImg(new MemberImgBean().setMember_id(memberBean.getMember_id() + "")
						.setMember_qualification_id(memberQualificationBean.getMember_qualification_id() + ""));
				if (bean1.size() > 0) {
					for (int i = 0; i < bean1.size(); i++) {
						int num1 = memberImgService.addMemberImg(bean1.get(i).setImg_type("business_license")
								.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								.setMember_qualification_id(memberQualificationBean.getMember_qualification_id() + "")
								.setMember_id(memberBean.getMember_id() + ""));
						if (num1 < 0) {
							throw new Exception("图片新增失败");
						}
					}
				}

				if (bean2.size() > 0) {
					for (int i = 0; i < bean2.size(); i++) {
						int num1 = memberImgService.addMemberImg(bean2.get(i).setImg_type("id_card").setIs_delete("0")
								.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								.setMember_qualification_id(memberQualificationBean.getMember_qualification_id() + "")
								.setMember_id(memberBean.getMember_id() + ""));
						if (num1 < 0) {
							throw new Exception("图片新增失败");
						}
					}
				}

				if (bean3.size() > 0) {
					for (int i = 0; i < bean3.size(); i++) {
						int num1 = memberImgService.addMemberImg(bean3.get(i).setImg_type("related_authorization")
								.setIs_delete("0").setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								.setMember_qualification_id(memberQualificationBean.getMember_qualification_id() + "")
								.setMember_id(memberBean.getMember_id() + ""));
						if (num1 < 0) {
							throw new Exception("图片新增失败");
						}
					}
				}
				WriteMsg(response, "修改成功");
			} else {
				WriteError(response, "修改失败");
			}
		}
	}

	/**
	 * 上传有关资质认证图片资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadMemberQualificationImg", method = RequestMethod.POST)
	public void uploadIdCardImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String img_type = request.getParameter("img_type");
		if (img_type == null || img_type.equals("")) {
			img_type = "member";
		}
		String json = uploadFile(request, "/images/" + img_type + "/");
		if (json.equals("-1")) {
			WriteError(response, "文件不可为空");
		} else if (json.equals("-2")) {
			WriteError(response, "上传失败");
		} else {
			WriteMsg(response, json);
		}
	}

	/**
	 * 上传有关资质认证pdf
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadMemberQualificationPdf", method = RequestMethod.POST)
	public void uploadMemberQualificationPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = uploadFile(request, "/pdf/");
		if (json.equals("-1")) {
			WriteError(response, "文件不可为空");
		} else if (json.equals("-2")) {
			WriteError(response, "上传失败");
		} else {
			WriteMsg(response, json);
		}
	}
	
	/**
	 * 修改有关资质认证图片资料
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "updateMemberQualificationImg", method = RequestMethod.POST)
	public void updateMemberQualificationImg(HttpServletRequest request, HttpServletResponse response,
			MemberBean memberBean, MemberQualificationBean memberQualificationBean) throws Exception {

		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}

		String json = request.getParameter("json");
		int num = 0;
		List<MemberImgBean> imgBeans = new Gson().fromJson(json, new TypeToken<List<MemberImgBean>>() {
		}.getType());
		if (imgBeans.size() > 0) {
			for (int i = 0; i < imgBeans.size(); i++) {
				if (imgBeans.get(i).getMember_img_id() == null || imgBeans.get(i).getMember_img_id().equals("")) {
					num = memberImgService.addMemberImg(imgBeans.get(i));
					if (num < 0) {
						throw new Exception("图片新增失败");
					}
				} else {
					num = memberImgService.updateMemberImg(imgBeans.get(i));
					if (num < 0) {
						throw new Exception("图片修改失败");
					}
				}
			}

			int num2 = mqService
					.updateMemberQualification(memberQualificationBean.setQualification_state("wait_audit"));
			if (num2 > 0) {
				WriteMsg(response, "修改资质成功");
			} else {
				WriteError(response, "修改资质失败");
			}
		} else {
			WriteError(response, "图片数组为空");
		}
	}

	/**
	 * 修改资质认证
	 * 
	 * @param request
	 * @param response
	 * @param memberBean
	 * @param memberQualificationBean
	 * @throws Exception
	 * 
	 * @RequestMapping(params = "updateMemberQualification", method =
	 *                        RequestMethod.POST) public void
	 *                        updateMemberQualification(HttpServletRequest
	 *                        request, HttpServletResponse response, MemberBean
	 *                        memberBean, MemberQualificationBean
	 *                        memberQualificationBean) throws Exception { if
	 *                        (memberService.verificationToken(memberBean) ==
	 *                        null) { WritePending(response, "token failed");
	 *                        return; }
	 * 
	 *                        String json1 = request.getParameter("json1");
	 *                        String json2 = request.getParameter("json2");
	 *                        String json3 = request.getParameter("json3");
	 * 
	 *                        List<MemberImgBean> bean1 = new
	 *                        Gson().fromJson(json1, new
	 *                        TypeToken<List<MemberImgBean>>(){}.getType());
	 *                        List<MemberImgBean> bean2 = new
	 *                        Gson().fromJson(json2, new
	 *                        TypeToken<List<MemberImgBean>>(){}.getType());
	 *                        List<MemberImgBean> bean3 = new
	 *                        Gson().fromJson(json3, new
	 *                        TypeToken<List<MemberImgBean>>(){}.getType());
	 * 
	 * 
	 *                        }
	 */

}
