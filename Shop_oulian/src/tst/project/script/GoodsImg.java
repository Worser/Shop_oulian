package tst.project.script;

import java.io.File;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import tst.project.bean.goods.GoodsBean;
import tst.project.bean.member.MemberLevelBean;
import tst.project.bean.member.MemberProfitBean;
import tst.project.utils.FileUtils;
import tst.project.utils.JDBCUtils;
import tst.project.utils.NumberUtils;
import tst.project.utils.TimeUtils;

public class GoodsImg {
	
	public static void main(String[] args) {
		System.out.println("开始");
		
		File file = new File(System.getProperty("user.dir"));
        String parentPath = file.getParent(); 
		File file1 = new File(parentPath);
		String path=file1.getParent();

		Connection conn = null;
		String url = "jdbc:mysql://106.15.35.162:3306/shop_oulian1";
		String sql = " select goods_id,goods_img from tst_goods where (is_goods_img='0' or is_goods_img is null) and merchants_id='1' and is_delete='0' and goods_type='2'";

		try {
			conn = JDBCUtils.startConn(conn, url);
			Statement st = (Statement) conn.createStatement();// 创建sql执行对象
			
			String result = JDBCUtils.queryArray(conn, sql);
			List<GoodsBean> goodsBeans = new Gson().fromJson(result,new TypeToken<List<GoodsBean>>() {}.getType());
			if (goodsBeans != null) {
				for (int i = 0; i < goodsBeans.size(); i++) {
					GoodsBean goodsBean=goodsBeans.get(i);					
					System.out.println(i+"============"+path+"/"+goodsBean.getGoods_img());
					if(FileUtils.isFileExists(path+"/"+goodsBean.getGoods_img())){
						System.out.println(goodsBean.getGoods_id()+"-------");
						Statement st3 = (Statement) conn.createStatement();
						int num = st3.executeUpdate("update tst_goods set is_goods_img='1' where goods_id = "+goodsBean.getGoods_id()+" ");
					}
				}
			}

			JDBCUtils.closeConn(st, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("结束");

	}
}
