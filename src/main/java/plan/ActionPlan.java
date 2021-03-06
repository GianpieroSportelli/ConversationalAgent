package plan;

import configuration.Config;
import java.util.List;
import knowledge.SemanticNet;

import org.json.JSONObject;

public interface ActionPlan {
	public String getName();
        public List<JSONObject> execute(JSONObject sem,SemanticNet net,Config conf,int current_epoch,String id_user);
}
