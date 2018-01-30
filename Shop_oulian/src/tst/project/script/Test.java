package tst.project.script;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tst.project.bean.bbs.TopicBean;
import tst.project.bean.member.MemberBean;
import tst.project.service.interfaces.TopicService;
import tst.project.utils.ExcelUtils;
import tst.project.utils.TimeUtils;
import tst.project.webservice.interfaces.TopicInterfaces;

public class Test {	
	public String getPath(){
		return getClass().getResource("/").getFile().toString()+"/test_jiabo.xlsx";
	}
	public void addTopic(HttpServletRequest request){
		
	}
	public static void main(String[] args) throws ParseException {
//		try {
//			ExcelUtils.readExcel(new Test().getPath());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("12");
//		}
		
		System.out.println(TimeUtils.getDayCompareDate(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"),"2017-05-18 22:39:40", "yyyy-MM-dd HH:mm:ss")+"");
	}
}
