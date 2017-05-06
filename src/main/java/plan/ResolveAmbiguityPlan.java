/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import Utils.JSON_utils;
import configuration.Config;
import java.util.ArrayList;
import java.util.List;
import knowledge.SemanticNet;
import knowledge.Vocabulary;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class ResolveAmbiguityPlan implements ActionPlan{

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JSONObject> execute(JSONObject sem, SemanticNet net, Config conf, int current_epoch, String id_user) {
        List<JSONObject> result=new ArrayList<>();
        JSONObject question=new JSONObject().accumulate("category", "dialog").accumulate("name", "ambiguityQuestion").accumulate(Vocabulary.MESSAGE, "cosa intendevi?");
        if(sem.has("amb")){
            String val=sem.get("amb").toString();
            if(JSON_utils.isJSONArray(val)){
                JSONArray ambiguty=JSON_utils.convertJSONArray(val);
                result.add(question);
                for(int i=0;i<ambiguty.length();i++){
                    result.add(ambiguty.getJSONObject(i));
                }
            }
        }
        return result;
    }
    
}
