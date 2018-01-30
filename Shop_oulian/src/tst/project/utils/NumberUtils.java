package tst.project.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[+|-]?\\d*\\.?\\d*$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static Double Double(String value) {
		return Double.valueOf(value == null || "".equals(value) ? "0" : value);
	}

	public static int Integer(String value) {
		try {
			return Integer.valueOf(value == null || "".equals(value) ? "0" : value);
		} catch (Exception e) {
			return -1;
		}
	}

	public static float Float(String value) {
		try {
			return Float.valueOf(value == null || "".equals(value) ? "0" : value);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		
	}

	/**
	 * 保留2位小数
	 * 
	 * @param value
	 * @param count
	 * @return
	 */
	public static double KeepDecimal(double value, int count) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(count, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * 保留2位小数
	 * 
	 * @param value
	 * @param count
	 * @return
	 */
	public static int KeepDecimal(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		return bd.intValue();
	}

	/**
	 * 保留2位小数
	 * 
	 * @param value
	 * @param count
	 * @return
	 */
	public static int KeepDecimal(String value, int count) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(count, BigDecimal.ROUND_HALF_UP);
		return bd.intValue();
	}
}
