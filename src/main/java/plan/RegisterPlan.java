/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import configuration.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import knowledge.SemanticNet;
import knowledge.Vocabulary;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class RegisterPlan implements ActionPlan {

    private JSONObject error = new JSONObject().accumulate("category", "dialog").accumulate("name", "errorReg");
    private Management managment;

    @Override
    public String getName() {
        return (new JSONObject().accumulate("class", RegisterPlan.class.getName())).toString();
    }

    @Override
    public List<JSONObject> execute(JSONObject sem, SemanticNet net, Config conf, int epoch, String id_user) {
        List<JSONObject> result = new ArrayList<>();
        //System.out.println(sem.toString(4));
        if (!net.incomplete(sem)) {
            JSONObject domain = sem.getJSONObject("domain");
            String domain_name = domain.getString("name");
            if (domain_name.equals("balance")) {
                try {
                    String url = conf.PATH_CONTABILITA + "-" + id_user + "." + conf.CONTABILITA_EXT;
                    managment = new Management().read(url);
                    double amount_d = managment.getBilancio();
                    String amount = "" + amount_d;
                    String[] split = amount.split("[.]");
                    String integer = split[0];
                    String floating = split[1];
                    if (floating.length() > 2) {
                        floating = floating.substring(0, 2);
                    } else if (floating.length() == 1) {
                        floating = floating + "0";
                    } else {
                        floating = "00";
                    }

                    amount = integer + "," + floating;
                    JSONObject number = new JSONObject().accumulate("category", "number").accumulate("name", "float").accumulate("token", amount);
                    JSONObject unit = new JSONObject().accumulate("category", "unit").accumulate("name", "euro");
                    JSONObject bill = new JSONObject().accumulate("category", "property").accumulate("name", "bill").accumulate("number", number).accumulate("unit", unit);
                    bill.accumulate(Vocabulary.NO_MESSAGE, true);
                    domain.put("property", bill);
                    JSONObject qtBalance = new JSONObject().accumulate("category", "dialog").accumulate("name", "qtRegBalance");
                    qtBalance.accumulate("domain", domain);
                    result.add(qtBalance);
                } catch (IOException ex) {
                    result.add(error);
                    Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    result.add(error);
                    Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
}
