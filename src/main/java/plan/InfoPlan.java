package plan;

import configuration.Config;
import java.util.ArrayList;
import java.util.List;
import knowledge.SemanticNet;

import org.json.JSONObject;

public class InfoPlan implements ActionPlan {

	@Override
	public String getName() {
		return (new JSONObject().accumulate("class", InfoPlan.class.getName())).toString();
	}
	
	public static String getClassName(){
		return InfoPlan.class.getName();
	}

	@Override
	public List<JSONObject> execute(JSONObject sem,SemanticNet net,Config conf,int epoch,String id_user) {
		JSONObject result=new JSONObject();
		result.accumulate("category", "dialog");
		result.accumulate("name", "name_question");
		List<JSONObject> list=new ArrayList<>();
		JSONObject querySem=new JSONObject();
		querySem.accumulate("query", sem);
		
		JSONObject queryR=new JSONObject();
		queryR.accumulate("query", result);
		list.add(querySem);
		list.add(queryR);
		return list;
	}

}
