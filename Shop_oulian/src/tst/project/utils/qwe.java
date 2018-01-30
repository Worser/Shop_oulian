package tst.project.utils;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.google.gson.Gson;
public class qwe {
	
	public static void main(String[] args) throws Exception {
		String tkey = TimeUtils.getCurrentTime("yyyyMMddHHmmss");
		System.out.print(MD5Util.getMD5(MD5Util.getMD5("8sorVjvv") + tkey)+"==="+tkey);
		
	}
//	public static void main(String[] args) throws Exception {
//		// HBRUtils.SvcardBalance();//储值卡余额
//		// HBRUtils.registerMember();// 注册会员
//		// HBRUtils.upInsider();//会员资料修改
//		// HBRUtils.getInsiderInfo();// 会员资料查询
//		// HBRUtils.cardBinding();//存储卡开通
//		// HBRUtils.cardDealData();//存储卡交易
//		// HBRUtils.refundOrder();//存储卡退款
//		// HBRUtils.getScore();//会员积分查询（接口）
//		// HBRUtils.selectScore();//会员积分对账明细查询
//		// HBRUtils.updateScore();//积分兑换使用（接口）
//		// HBRUtils.svcardDeal();// 储值卡交易明细查询（接口）
//		// HBRUtils.svcardPrepay();//储值卡充值（接口）
//		// HBRUtils.modifyIntegral(); //销售等增加积分(接口)/销售退货减少积分
//
//		ElasticsearchUtils es = new ElasticsearchUtils("elasticsearch", "139.196.178.64");
//		String indexName = "school";// 索引名称
//		String typeName = "student";// 类型名称
//		String id = "1";
//		String jsonData = "{" + "\"name\":\"kimchy\"," + "\"birth\":\"1995-01-30\"," + "\"email\":\"kimchy@163.com\""+"}";// json数据
//		// 1.创建索引(ID可自定义也可以自动创建，此处使用自定义ID)
//		//es.createIndex(indexName, typeName, id, jsonData);
//
//		// 2.执行查询
//		// (1)创建查询条件
//		QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "kimchy");// 搜索name为kimchy的数据
//		// (2)执行查询
//		SearchResponse searchResponse = es.searcher(indexName, typeName, queryBuilder);
//		// (3)解析结果
//		SearchHits hits = searchResponse.getHits();
//		SearchHit[] searchHits = hits.getHits();
//		for (SearchHit searchHit : searchHits) {
//			String name = (String) searchHit.getSource().get("name");
//			String birth = (String) searchHit.getSource().get("birth");
//			String email = (String) searchHit.getSource().get("email");
//			System.out.println(name+"===="+birth+"====="+email+"======"+new Gson().toJson(searchHit.getSource()));
//		}
////
////		// 3.更新数据
////		jsonData = "{" + "\"name\":\"jack\"," + "\"birth\":\"1996-01-30\"," + "\"email\":\"jack@163.com\"" + "}";// json数据
////		es.updateIndex(indexName, typeName, id, jsonData);
////
////		// 4.删除数据
////		es.deleteIndex(indexName, typeName, id);
//	}



}