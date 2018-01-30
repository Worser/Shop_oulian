package tst.project.script;

import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsFindBuyBean;
import tst.project.utils.JDBCUtils;
import tst.project.utils.TimeUtils;

public class GoodsFindBuyRanking {
	public static void main(String[] args) {
		Connection conn = null;
		String url = "jdbc:mysql://106.15.35.162:3306/shop_oulian1";
		String sql = "select count(*) as count,a.goods_name "
				+ "from tst_goods_find_buy as a "
				+ "inner join tst_goods as b on a.goods_name=b.goods_name "
				+ "and b.is_delete='0' and b.merchants_id='1' and b.goods_type='2' "
				+ "where  a.is_delete='0' "
				+ "group by a.goods_name order by count desc  "
				+ "limit 0,5";
		try {
			conn = JDBCUtils.startConn(conn, url);
			Statement st = (Statement) conn.createStatement();// 创建sql执行对象
			
			Statement st5 = (Statement) conn.createStatement();
			int num = st5.executeUpdate(" update tst_goods_find_buy set ranking_no ='0' ");
			
			String result = JDBCUtils.queryArray(conn, sql);
			List<GoodsFindBuyBean> goodsFindBuyBeans = new Gson().fromJson(result,new TypeToken<List<GoodsFindBuyBean>>() {}.getType());
			if(goodsFindBuyBeans != null){
				for (int i = 0; i < goodsFindBuyBeans.size(); i++) {
					System.out.println(goodsFindBuyBeans.get(i).getGoods_name());
					
					String sqlG="select a.find_id,b.member_id from tst_goods_find_buy as a "
							+ "inner join tst_member  as b "
							+ "on a.member_id = b.member_id and b.is_delete='0'"
							+ " where a.goods_name=\""+goodsFindBuyBeans.get(i).getGoods_name()+"\" and a.is_delete='0'"
							+ " order by b.member_level desc "
							+ " limit 0,1";
					
					String resultG=JDBCUtils.queryObject(conn, sqlG);
					
					GoodsFindBuyBean goodsFindBuyBean = new Gson().fromJson(resultG, GoodsFindBuyBean.class);
					
					if(goodsFindBuyBean != null){
						Statement st6 = (Statement) conn.createStatement();
						num = st6.executeUpdate(" update tst_goods_find_buy set ranking_no ="+(5-i)+" where find_id = "+goodsFindBuyBean.getFind_id()+" ");
					}
				}
			}
			
			JDBCUtils.closeConn(st, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

