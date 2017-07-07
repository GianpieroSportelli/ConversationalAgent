package nlProcess;

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

	public static JSONArray analysis(String text,String hostname,String port,String collection) throws IOException{
		JSONArray result=null;
		text=text.replaceAll(" ", "%20");
		addInLog(text,hostname,port,collection);
		String url="http://"+hostname+":"+port+"/api/v10/analysis/text?collection="+collection+"&language=it&output=application/json&text="+text;
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

	private static void addInLog(String text,String hostname,String port,String collection) throws IOException {
		String url="http://"+hostname+":"+port+"/api/v10/search?collection="+collection+"&output=application/json&query="+text;
		URL call=new URL(url);
		URLConnection connection=call.openConnection();
		connection.connect();
		BufferedReader buff=new BufferedReader(new InputStreamReader(connection.getInputStream()));
//		String content="";
//		String line=null;
//		while((line=buff.readLine())!=null){
//			content+=line+"\n";
//		}
//		JSONObject response=new JSONObject(content);
//		System.out.println(response.toString());
		buff.close();
	}
	
	
}
//{/cube misspelling} 

