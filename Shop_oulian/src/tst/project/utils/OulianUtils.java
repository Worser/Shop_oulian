package tst.project.utils;

public class OulianUtils {

	/**
	 * 替换供应excle中的字段，
	 * @return
	 * @throws Exception
	 */
	public static String replaceSupplyField(String result) throws Exception
	{
		result = result.replaceAll(" ", "");
		
		result = result.replaceAll("Part_Number", "goods_name");
		result = result.replaceAll("PPQ", "min_pack_num");
		result = result.replaceAll("Stock_quantity", "goods_stock");
		result = result.replaceAll("MOQ", "min_buy_num");
		result = result.replaceAll("Price", "goods_unit_price");
		result = result.replaceAll("Type/stocking_futures_Special_price_Coordination", "data_categories");
		result = result.replaceAll("L/T", "give_time");
		
		result = result.replaceAll("现货", "1");
		result = result.replaceAll("期货", "2");
		result = result.replaceAll("促销", "3");
		result = result.replaceAll("调货", "4");
		
		return result;
	}
	
	/**
	 * 替换求购excle中的字段，
	 * @return
	 * @throws Exception
	 */
	public static String replaceFindBuyField(String result) throws Exception
	{
		 result = result.replaceAll(" ", "");
		
		 result = result.replaceAll("Part_Number", "goods_name");
		 result = result.replaceAll("Quantiy", "goods_num");
		 result = result.replaceAll("Target_Price", "target_unit_price");
	 	 result = result.replaceAll("Type/stocking_futures_Special_price_Coordination", "data_categories");
	     result = result.replaceAll("Effective_Time", "end_time");
		 
	     result = result.replaceAll("现货", "1");
		 result = result.replaceAll("期货", "2");
		 result = result.replaceAll("促销", "3");
		 result = result.replaceAll("调货", "4");
		 
		return result;
	}
	
	/**
	 * 替换委托excle中的字段，
	 * @return
	 * @throws Exception
	 */
	public static String replaceEntrustField(String result) throws Exception
	{
		 result = result.replaceAll(" ", "");
		
		 result = result.replaceAll("Part_Number", "goods_name");
		 result = result.replaceAll("Quantiy", "goods_num");
		 result = result.replaceAll("Target_Price", "target_unit_price");
	 	 result = result.replaceAll("Type/stocking_futures_Special_price_Coordination", "data_categories");
	     result = result.replaceAll("Effective_Time", "end_time");
	     result = result.replaceAll("PPQ", "min_pack_num");
	     result = result.replaceAll("MOQ", "min_buy_num");
	     result = result.replaceAll("Stock_quantity", "goods_num");
	     result = result.replaceAll("L/T", "give_time");
	     
	     result = result.replaceAll("现货", "1");
		 result = result.replaceAll("期货", "2");
		 result = result.replaceAll("促销", "3");
		 result = result.replaceAll("调货", "4");
		 
		return result;
	}
}
