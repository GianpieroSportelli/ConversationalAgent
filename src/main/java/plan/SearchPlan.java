package plan;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import configuration.Config;
import dataBase.DB;
import knowledge.KnowledgeBase;
import knowledge.Ontology;

public class SearchPlan implements ActionPlan {

	@Override
	public String getName() {
		return (new JSONObject().accumulate("class", SearchPlan.class.getName())).toString();
	}

	public static String getClassName() {
		return SearchPlan.class.getName();
	}

	@Override
	public List<JSONObject> execute(JSONObject sem, KnowledgeBase net, Config conf, int epoch, String id_user) {
		DB.init();
		List<String> q = new ArrayList<>();
		q.add(sem.toString());
		List<JSONObject> list = DB.query(q, net);
		JSONObject firstMessage=new JSONObject().accumulate("category", "dialog").accumulate("name", "SearchRespose");
		if(list == null || list.isEmpty()) {
			firstMessage.accumulate(Ontology.MESSAGE, "Non ci sono risultati :(");
			list= new ArrayList<>();
			list.add(firstMessage);
		}else {
			if(list.size()==1) {
				if(!list.get(0).has("result")) {
					firstMessage.accumulate(Ontology.MESSAGE, "Non ci sono risultati :(");
					list= new ArrayList<>();
					list.add(firstMessage);
				}
			}else {
			firstMessage.accumulate(Ontology.MESSAGE, "Ecco ciò che ho trovato:");
			list.add(0, firstMessage);
			}
		}
		return list;
	}

}
