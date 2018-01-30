package tst.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import tst.project.bean.others.HtmlStyleBean;

public class HtmlUtils {
	public static boolean writeHtml(HttpServletRequest request,String fileName,String desc){
		return writeHtml(request,fileName,desc,true);
	}
	

	public static String readHtml(HttpServletRequest request,String fileName) {
		try {
			String basePath = request.getSession().getServletContext()
					.getRealPath("/");

			// 模板路径
			String filePath = basePath + "/"+fileName;
			System.out.print(filePath);
			String templateContent = "";
			FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes);
			
			
			return templateContent;
		} catch (Exception e) {
			System.out.print(e.toString());
			return "";
		}
	}
	public  static boolean writeHtml(HttpServletRequest request,String fileName,String desc,HtmlStyleBean htmlStyleBean) {
		try {
			String basePath = request.getSession().getServletContext()
					.getRealPath("/");
			// 模板路径
			String filePath = basePath + "/"+fileName;
			FileOutputStream fileoutputstream = new FileOutputStream(filePath);// 建立文件输出流
			
			OutputStreamWriter writer = new OutputStreamWriter(
					fileoutputstream, "utf-8");
			String style="";

			if(htmlStyleBean==null){
				style=desc;
			}else{
				style=htmlStyleBean.getStyle_desc();
				int start=desc.indexOf("<tst>");
				int end=desc.indexOf("</tst>");
				
				if(start>0&&end>0){
					style=style.replace("[desc]", desc.substring(start+5,end));
				}else{
					style=style.replace("[desc]", desc);
				}
			}
			byte tag_bytes[] = style.getBytes();
			
			fileoutputstream.write(tag_bytes);
			
			writer.flush();
			fileoutputstream.close();
			writer.close();

			return true;
		} catch (Exception e) {
			System.out.print(e.toString());
			return false;
		}
	}

	
	public static boolean writeHtml(HttpServletRequest request,String fileName,String desc,boolean is_style) {
		try {
			String basePath = request.getSession().getServletContext()
					.getRealPath("/");
			// 模板路径
			String filePath = basePath + "/"+fileName;
			FileOutputStream fileoutputstream = new FileOutputStream(filePath);// 建立文件输出流
			
			OutputStreamWriter writer = new OutputStreamWriter(
					fileoutputstream, "utf-8");
			String a= "<!DOCTYPE html><html>"
					+ "<head>"
					+ "<title>about_our.html</title>"
					+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
					+ "<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">"
					+ "<meta http-equiv=\"description\" content=\"this is my page\">"
					+ "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">";
			
			if(is_style){
				a+="<style type=\"text/css\">"
						+ "img{width:100% !important;height:auto !important;}"
						+ "p{line-height: 0;margin: 0}"
					+ "</style>";
			}
			a+="</head><body>";
			byte tag_bytes[] = (a+desc+"</body></html>").getBytes();
			
			fileoutputstream.write(tag_bytes);
			
			writer.flush();
			fileoutputstream.close();
			writer.close();

			return true;
		} catch (Exception e) {
			System.out.print(e.toString());
			return false;
		}
	}
	
	public static String html(HttpServletRequest request, String src) {
		try {
			String basePath = request.getSession().getServletContext()
					.getRealPath("/");

			// 模板路径
			String filePath = basePath + "/MileageBudget.html";
			System.out.print(filePath);
			String templateContent = "";
			FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes);
			templateContent = templateContent.replaceAll("####src####", src);
			// 根据时间得文件名
			Calendar calendar = Calendar.getInstance();
			String filename = "MileageBudget_"
					+ String.valueOf(calendar.getTimeInMillis()) + ".html";
			filename = "/" + filename;// 生成的html文件保存路径。
			String path = "/html/MileageBudget";
			File file = new File(basePath + path);
			if (!file.exists()) {
				file.mkdirs();
			}
			FileOutputStream fileoutputstream = new FileOutputStream(basePath
					+ path + filename);// 建立文件输出流
			OutputStreamWriter writer = new OutputStreamWriter(
					fileoutputstream, "UTF-8");
			writer.append(templateContent);
			/*
			 * byte tag_bytes[] = templateContent.getBytes();
			 * fileoutputstream.write(tag_bytes);
			 */
			writer.flush();
			fileoutputstream.close();
			writer.close();

			return path + filename;
		} catch (Exception e) {
			System.out.print(e.toString());
			return "";
		}
	}
}
