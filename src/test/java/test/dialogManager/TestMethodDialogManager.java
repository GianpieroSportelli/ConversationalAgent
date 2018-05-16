package test.dialogManager;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import configuration.Config;
import knowledge.SemanticNet;


public class TestMethodDialogManager {

	public static void main(String[] args) {
		SemanticNet net=new SemanticNet(Config.getPathSemanticNet());
		List<String> classes=new ArrayList<>();
		classes.add("action");
		classes.add("domain");
		classes.add("property");
		classes.add("mod");
		classes.add("value");
		classes.add("number");
		classes.add("unit");
		
		for(String c:classes){
			JSONObject obj=new JSONObject();
			obj.accumulate("category", c);
			//System.out.println(c+":"+net.onlyOne(obj));
		}

	}

}
