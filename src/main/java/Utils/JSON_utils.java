package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON_utils {

	public static JSONArray convertJSONArray(String val) {
		if (isJSONArray(val)) {
			return new JSONArray(val);
		} else {
			return (new JSONArray()).put(new JSONObject(val));
		}
	}

	public static boolean isJSONArray(String val) {
		return val.charAt(0) == '[';
	}

	public static boolean isJSONObject(String val) {
		JSONObject test = null;
		try {
			test = new JSONObject(val);
		} catch (Exception ex) {

		}
		return test != null;
	}

	public static JSONArray merge(JSONArray x, JSONArray y) {
		if (x == null && y == null) {
			return null;
		} else if (x != null && y == null) {
			return x;
		} else if (x == null && y != null) {
			return y;
		} else {
			for (int i = 0; i < y.length(); i++) {
				x.put(y.get(i));
			}
			return x;
		}
	}
	
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}

	private static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public static boolean semanticEquals(JSONObject obj1, JSONObject obj2){
		return obj1.toString().equals(obj2.toString());
	}

}
