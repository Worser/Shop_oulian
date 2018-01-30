package tst.project.script;

import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import tst.project.bean.order.OrderBean;
import tst.project.bean.order.OrderGoodsBean;
import tst.project.utils.JDBCUtils;

public class RefuseOrder {

	public static void main(String[] args) {
		String url = "jdbc:mysql://106.15.35.162:3306/shop_oulian1";
		Connection conn = null;
		String sql = "select a.order_id from tst_order as a " +
				"where FIND_IN_SET(a.order_state,'wait_review') " +
				"and TIMESTAMPDIFF(MINUTE,a.create_time,now())>2";
		try {
			conn = JDBCUtils.startConn(conn, url);
			String json = JDBCUtils.queryArray(conn, sql);
			List<OrderBean> orderBeans = new Gson().fromJson(json, new TypeToken<List<OrderBean>>() {
			}.getType());

			if (orderBeans != null) {
				for (int i = 0; i < orderBeans.size(); i++) {
					OrderBean orderBean = orderBeans.get(i);
					System.out.println(orderBean.getOrder_id()+"==========");

					Statement st3 = (Statement) conn.createStatement();
					int num = st3.executeUpdate(
							" update tst_order set order_state='cancel',cancel_time=now() " 
							+ "where order_id=" + orderBean.getOrder_id() + "  ");
					String sqlGoods = " select * from tst_order_goods where order_id="+orderBean.getOrder_id()+" ";
					String jsonGoods = JDBCUtils.queryArray(conn, sql);
					List<OrderGoodsBean> orderGoodsBeans= new Gson().fromJson(jsonGoods, new TypeToken<List<OrderGoodsBean>>() {}.getType());
					if(orderGoodsBeans!=null){
						for (int j = 0; j < orderGoodsBeans.size(); j++) {
							OrderGoodsBean orderGoodsBean=orderGoodsBeans.get(j);
							Statement st4 = (Statement) conn.createStatement();
							num = st4.executeUpdate("update tst_goods_supply_data set goods_stock=goods_stock+"+orderGoodsBean.getGoods_num()+" "
									+ "where supply_id="+orderGoodsBean.getSupply_id()+" ");
							
						}
					}
				}
			}

			JDBCUtils.closeConn(null, null, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
