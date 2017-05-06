package plan;

import configuration.Config;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import knowledge.SemanticNet;

public class WellPlan implements ActionPlan {

	@Override
	public String getName() {
		
	return (new JSONObject().accumulate("class", InfoPlan.class.getName())).toString();
	}

	@Override
	public List<JSONObject> execute(JSONObject sem, SemanticNet net,Config conf,int epoch,String id_user) {
		List<JSONObject> result=new ArrayList<>();
		result.add(new JSONObject().accumulate("query", sem));
		JSONObject obj=new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "well_done");
		result.add(new JSONObject().accumulate("query", obj));
		return result;
	}
}
