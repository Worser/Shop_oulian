package tst.project.script;

import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import tst.project.bean.goods.GoodsBean;
import tst.project.utils.JDBCUtils;

public class GoodsRanking {


	public static void main(String[] args) {
		Connection conn = null;
		String url = "jdbc:mysql://106.15.35.162:3306/shop_oulian";
		String sql = "select count(*) as count,a.goods_name from tst_goods as a where a.goods_type='2' and a.merchants_id != '1' "
				+ "and a.is_delete='0' and a.goods_state='1' "
				+ "group by a.goods_name "
				+ "order by count desc "
				+ "limit 0,5";

		try {
			conn = JDBCUtils.startConn(conn, url);
			Statement st = (Statement) conn.createStatement();// 创建sql执行对象
			
			Statement st5 = (Statement) conn.createStatement();
			int num = st5.executeUpdate(" update tst_goods set ranking_no ='0' ");
			
			String result = JDBCUtils.queryArray(conn, sql);
			List<GoodsBean> goodsBeans = new Gson().fromJson(result,new TypeToken<List<GoodsBean>>() {}.getType());
			if(goodsBeans != null){
				for (int i = 0; i < goodsBeans.size(); i++) {
					System.out.println(goodsBeans.get(i).getGoods_name());
					
					String sqlG="select a.goods_id from tst_goods as a "
							+ "inner join tst_member as b "
							+ "on a.merchants_id=b.merchants_id "
							+ "and b.is_delete='0'"
							+ "where a.goods_name=\""+goodsBeans.get(i).getGoods_name()+"\" and a.merchants_id != '1' "
							+ "and a.is_delete='0' and a.goods_state='1' and a.goods_type='2' "
							+ "order by b.member_level desc "
							+ "limit 0,1 ";
					
					String resultG=JDBCUtils.queryObject(conn, sqlG);
					
					GoodsBean goodsBean = new Gson().fromJson(resultG, GoodsBean.class);
					
					if(goodsBean != null){
						Statement st6 = (Statement) conn.createStatement();
						num = st6.executeUpdate(" update tst_goods set ranking_no ="+(5-i)+" where goods_id = "+goodsBean.getGoods_id()+" ");
					}
				}
			}
			
			JDBCUtils.closeConn(st, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
