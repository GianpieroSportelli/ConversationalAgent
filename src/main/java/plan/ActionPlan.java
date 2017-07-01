package plan;

import configuration.Config;
import dialogManager.DialogManager;

import java.util.List;
import knowledge.KnowledgeBase;

import org.json.JSONObject;

public interface ActionPlan {
	public String getName();
        public List<JSONObject> execute(JSONObject sem,KnowledgeBase net,Config conf,int current_epoch,String id_user,DialogManager dm,boolean DEBUG);
}
