package cn.jtduan.snippets.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
	
	public static final Integer SUCCESS = 0;
	
	public static final Integer FAILURE = 1;
	
	public static final String ERROR_CODE = "errorCode";
	
	public static final String ERROR_MESSAGE = "errorMessage";
	
	public static final String DATA = "data";
	
	public static final String COUNT = "count";

	public static Map<String, Object> badResult(Object cause) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ERROR_CODE, FAILURE);
		result.put(ERROR_MESSAGE, cause);
		return result;
	}
	
	public static Map<String, Object> ok() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ERROR_CODE, SUCCESS);
		result.put(DATA, "success");
		result.put(ERROR_MESSAGE, "success");
		return result;
	}
	
	public static Map<String, Object> ok(Object obj) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ERROR_CODE, SUCCESS);
		result.put(DATA, obj);
		result.put(ERROR_MESSAGE, "success");
		return result;
	}
	
	public static Map<String, Object> okWithCount(Object obj,Integer count) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ERROR_CODE, SUCCESS);
        result.put(DATA, obj);
        result.put(COUNT, count);
        return result;
    }
}
