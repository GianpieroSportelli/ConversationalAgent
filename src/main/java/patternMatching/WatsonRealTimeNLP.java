package patternMatching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import Utils.JSON_utils;

public class WatsonRealTimeNLP {

	public static JSONArray analysis(String text) throws IOException{
		JSONArray result=null;
		text=text.replaceAll(" ", "%20");
		String url="http://localhost:8393/api/v10/analysis/text?collection=bot_unipol&language=it&output=application/json&text="+text;
		URL call=new URL(url);
		URLConnection connection=call.openConnection();
		connection.connect();
		BufferedReader buff=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String content="";
		String line=null;
		while((line=buff.readLine())!=null){
			content+=line+"\n";
		}
		JSONObject response=new JSONObject(content);
		if(response.has("metadata") && response.getJSONObject("metadata").has("textfacets")){
			String textfacets=response.getJSONObject("metadata").get("textfacets").toString();
			result=JSON_utils.convertJSONArray(textfacets);
		}
		//System.out.println(result.toString(4));
		buff.close();
		return result;
	}
	
	
}
//{/cube misspelling} 

