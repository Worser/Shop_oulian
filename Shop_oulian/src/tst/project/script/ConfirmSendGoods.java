package tst.project.script;

import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import tst.project.bean.merchants.MerchantsBean;
import tst.project.bean.order.OrderBean;
import tst.project.bean.others.PercentBean;
import tst.project.utils.JDBCUtils;
import tst.project.utils.NumberUtils;

public class ConfirmSendGoods {
	public static void main(String[] args) {
		String url = "jdbc:mysql://106.15.35.162:3306/shop_oulian1";
		Connection conn = null;
		String sql = "select a.* from tst_order as a  " +
				"where FIND_IN_SET(a.order_state,'wait_receive') " +
				"and TIMESTAMPDIFF(day,a.send_time,now())>6";
		try {
			conn = JDBCUtils.startConn(conn, url);
			String json = JDBCUtils.queryArray(conn, sql);
			List<OrderBean> orderBeans = new Gson().fromJson(json, new TypeToken<List<OrderBean>>() {
			}.getType());

			if (orderBeans != null) {
				for (int i = 0; i < orderBeans.size(); i++) {
					OrderBean orderBean = orderBeans.get(i);
					
					Statement st3 = (Statement) conn.createStatement();
					int num = st3.executeUpdate(
							" update tst_order set order_state='wait_assessment' , receive_time = now() " 
							+ "where order_id=" + orderBean.getOrder_id() + "  ");
					
				}
			}

			JDBCUtils.closeConn(null, null, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}