package tst.project.script;

import java.util.List;

import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.member.MemberProfitBean;
import tst.project.utils.JDBCUtils;
import tst.project.utils.NumberUtils;
import tst.project.utils.TimeUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MerchantsRanking {

	public static void main(String[] args) {
		String time = TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 6));
		String profit_time = TimeUtils.getAchTime(TimeUtils.getLastMonthBegin("yyyy-MM-01 00:00:00", 3));
		Connection conn = null;
		String url = "jdbc:mysql://106.15.35.162:3306/shop_oulian1";
		String sqlSales = "select b.member_profit_id,a.merchants_id,b.profit_money,b.profit_count from tst_member as a "
				+ "left join tst_profit_member as b " + "on a.merchants_id=b.merchants_id and b.profit_type='total'"
				+ " and b.profit_time=\"" + profit_time + "\"  " + "and b.is_delete='0' "
				+ "where a.merchants_id is not null " + "order by b.profit_money desc,b.profit_count desc";

		String sqlLevel="select level_id,deal_number,deal_price from tst_member_level order by level_id desc";
		try {
			conn = JDBCUtils.startConn(conn, url);
			Statement st = (Statement) conn.createStatement();// 创建sql执行对象
			
			String resultLevel = JDBCUtils.queryArray(conn, sqlLevel);
			String resultSales = JDBCUtils.queryArray(conn, sqlSales);
			List<MemberProfitBean> memberProfitBeans1 = new Gson().fromJson(resultSales,new TypeToken<List<MemberProfitBean>>() {}.getType());
			List<MemberLevelBean> memberLevelBeans=new Gson().fromJson(resultLevel, new TypeToken<List<MemberLevelBean>>() {}.getType());
			if (memberProfitBeans1 != null) {
				int sales_no = 0;
				for (int i = 0; i < memberProfitBeans1.size(); i++) {
					Statement st3 = (Statement) conn.createStatement();
					if (memberProfitBeans1.get(i).getMember_profit_id() == 0) {
						int num = st3.executeUpdate("insert into tst_profit_member "
								+ "(member_id,order_id,profit_time,create_time,profit_money,merchants_id,profit_type,profit_count,sales_no)"
								+ "values " + "(0,0,\"" + profit_time + "\",now(),0,"
								+ memberProfitBeans1.get(i).getMerchants_id() + ",'total','0'," + (sales_no + 1) + ")");
					} else {
						if (memberProfitBeans1.get(i).getProfit_money()==null
								||"".equals(memberProfitBeans1.get(i).getProfit_money())
								||"0".equals(memberProfitBeans1.get(i).getProfit_money())) {
							int num = st3.executeUpdate(" update tst_profit_member set sales_no=" + (sales_no+1) + " "
									+ "where member_profit_id = " + memberProfitBeans1.get(i).getMember_profit_id() + " ");
						}else{
							sales_no = i + 1;
							int num = st3.executeUpdate(" update tst_profit_member set sales_no=" + sales_no + " "
									+ "where member_profit_id = " + memberProfitBeans1.get(i).getMember_profit_id() + " ");
						}
					}
					
					boolean is_hava=false;
					for (int j = 0; j < memberLevelBeans.size(); j++) {
						if(NumberUtils.Float(memberProfitBeans1.get(i).getProfit_money())
								>=NumberUtils.Float(memberLevelBeans.get(j).getDeal_price())
						  &&NumberUtils.Float(memberProfitBeans1.get(i).getProfit_count())
							>=NumberUtils.Float(memberLevelBeans.get(j).getDeal_number())){
							is_hava=true;
							Statement st5 = (Statement) conn.createStatement();
							int num = st5.executeUpdate(" update tst_member set member_level=" + memberLevelBeans.get(j).getLevel_id() + " "
									+ "where merchants_id = " + memberProfitBeans1.get(i).getMerchants_id() + " ");
							break;
						}
					}
					
					if(!is_hava){
						Statement st5 = (Statement) conn.createStatement();
						int num = st5.executeUpdate(" update tst_member set member_level=0 "
								+ "where merchants_id = " + memberProfitBeans1.get(i).getMerchants_id() + " ");
					}
				}
			}

			String sqlCount = "select * from tst_profit_member where profit_type='total' " + "and profit_time=\""
					+ profit_time + "\" " + "and is_delete='0' order by profit_count desc,profit_money desc";
			String resultCount = JDBCUtils.queryArray(conn, sqlCount);
			List<MemberProfitBean> memberProfitBeans2 = new Gson().fromJson(resultCount,
					new TypeToken<List<MemberProfitBean>>() {}.getType());

			if (memberProfitBeans2 != null) {
				int count_no = 0;
				for (int i = 0; i < memberProfitBeans2.size(); i++) {
					Statement st3 = (Statement) conn.createStatement();
					if ("0".equals(memberProfitBeans2.get(i).getProfit_count())
							|| "".equals(memberProfitBeans2.get(i).getProfit_count())) {
						int num = st3.executeUpdate(" update tst_profit_member set count_no=" + (count_no + 1) + " "
								+ "where member_profit_id = " + memberProfitBeans2.get(i).getMember_profit_id() + " ");
					} else {
						count_no = i + 1;
						int num = st3.executeUpdate(" update tst_profit_member set count_no=" + (i + 1) + " "
								+ "where member_profit_id = " + memberProfitBeans2.get(i).getMember_profit_id() + " ");
					}
				}
			}
			Statement st4 = (Statement) conn.createStatement();

			int num = st4.executeUpdate("update tst_profit_member as a "
					+ "left join tst_profit_member as b on a.merchants_id=b.merchants_id "
					+ "and b.profit_type='total' and b.profit_time=\"" + time + "\" " + "and b.is_delete='0' "
					+ "set a.progress_no=(case when b.sales_no is null then 0 else a.sales_no-b.sales_no end) "
					+ "where a.profit_type='total' and a.profit_time=\"" + profit_time + "\" and a.is_delete='0' ");

			JDBCUtils.closeConn(st, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
