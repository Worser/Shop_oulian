package tst.project.script;

import java.util.List;
import java.util.UUID;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import tst.project.utils.JDBCUtils;


/**
 * 别名替换会员发布的不同格式型号
 * @author sjb
 *
 */
public class RefreshGoodsName {
	public static void main(String[] args) {
		String uuid=UUID.randomUUID().toString();
		Connection conn = null;
		String url = "jdbc:mysql://106.15.35.162:3306/shop_oulian1";
		try {
			conn = JDBCUtils.startConn(conn, url);
			Statement st3 = (Statement) conn.createStatement();
			int num = st3.executeUpdate("insert into tst_goods_backup "
					+ "(goods_id,goods_name,goods_alias,create_time,backup_no,goods_origin_name) "
					+ "select a.goods_id,c.goods_name,c.goods_alias,now(),'"+uuid+"',a.goods_name as goods_origin_name "
					+ "from tst_goods as a inner join tst_goods as c "
					+ "on FIND_IN_SET(a.goods_name,c.goods_alias) "
					+ "and c.is_delete='0' and c.merchants_id='1' "
					+ "and c.goods_alias is not null "
					+ "and c.goods_alias != ''"
					+ " left join tst_goods as b  on a.goods_name = b.goods_name and b.merchants_id='1' "
					+ "and b.is_delete='0' "
					+ "where a.merchants_id != '1' and a.is_delete='0' "
					+ "and b.goods_name is null");

			Statement st4 = (Statement) conn.createStatement();
			st4.executeUpdate("update tst_goods_backup as a "
					+ "inner join tst_goods as b on a.goods_id=b.goods_id "
					+ "set b.goods_name=a.goods_name "
					+ "where a.backup_no='"+uuid+"' and a.is_delete='0'");
			
			JDBCUtils.closeConn(st3, conn);
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
	}
}
