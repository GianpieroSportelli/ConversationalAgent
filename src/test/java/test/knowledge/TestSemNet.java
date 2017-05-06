package test.knowledge;

import java.util.List;

import org.json.JSONObject;

import configuration.Config;
import knowledge.SemanticNet;

public class TestSemNet {

	public static void main(String[] args) {
		String url=Config.getPathSemanticNet();
		SemanticNet net=new SemanticNet(url);
				
		List<String> h=net.getHierarchy();
		for(String x:h){
			System.out.println(x);
		}
		
		JSONObject obj=new JSONObject();
		obj.accumulate("category", "action");
		obj.accumulate("name", "register");
		
		System.out.println(net.enrich(obj.toString()));
		obj=new JSONObject();
		obj.accumulate("category", "domain");
		obj.accumulate("name", "shop");
		
		System.out.println(net.enrich(obj.toString()));

		obj=new JSONObject();
		obj.accumulate("category", "property");
		obj.accumulate("name", "bill");
		
		System.out.println(net.enrich(obj.toString()));

	}

}
