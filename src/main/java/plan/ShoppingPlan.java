/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import Utils.JSON_utils;
import configuration.Config;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import knowledge.SemanticNet;
import knowledge.Vocabulary;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class ShoppingPlan implements ActionPlan {

    private JSONObject error = new JSONObject().accumulate("category", "dialog").accumulate("name", "errorReg");
    private Management managment;

    @Override
    public String getName() {
        return (new JSONObject().accumulate("class", ShoppingPlan.class.getName())).toString();
    }

    @Override
    public List<JSONObject> execute(JSONObject sem, SemanticNet net, Config conf, int epoch, String id_user) {
        List<JSONObject> result = new ArrayList<>();
        //System.out.println(sem.toString(4));
        List<JSONObject> qt = speechAct(sem, conf, epoch, id_user);
        if (qt != null) {
            result.addAll(qt);
        } else if (!net.incomplete(sem)) {
            JSONObject domain = sem.getJSONObject("domain");
            boolean up = false;
            JSONArray properties = JSON_utils.convertJSONArray(domain.get("property").toString());
            for (int i = 0; i < properties.length(); i++) {
                JSONObject prop = properties.getJSONObject(i);
                String prop_name = prop.getString("name");
                if (prop_name.equals("bill")) {
                    if (prop.has("number")) {
                        double valueD = Double.valueOf(prop.getJSONObject("number").get("token").toString());
                        try {
                            String url = conf.PATH_CONTABILITA + "-" + id_user + "." + conf.CONTABILITA_EXT;
                            managment = new Management().read(url);
                            managment.addTransaction(up, valueD);
                            managment.write(url, managment);
                            JSONObject rok = new JSONObject();
                            rok.accumulate("category", "dialog");
                            rok.accumulate("name", "registerOK");
                            prop.accumulate(Vocabulary.NO_MESSAGE, true);
                            domain.put("property", properties);
                            rok.accumulate("domain", domain);
                            result.add(rok);
                        } catch (FileNotFoundException nf) {
                            error.put(Vocabulary.MESSAGE, "errore interno, non riesco a trovare il file: ShoppingPlan");
                            result.add(error);
                            Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, nf);
                        } catch (IOException ex) {
                            error.put(Vocabulary.MESSAGE, "errore interno, Input-Output: ShoppingPlan");
                            result.add(error);
                            Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            error.put(Vocabulary.MESSAGE, "errore interno, Non trovo la classe Managment: ShoppingPlan");
                            result.add(error);
                            Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        JSONObject response = new JSONObject();
                        response.accumulate("category", "dialog");
                        response.accumulate("name", "howMuch");
                        result.add(response);
                    }
                }
            }
        } else if (net.incomplete(sem, 0)) {
            JSONObject response = new JSONObject();
            response.accumulate("category", "dialog");
            response.accumulate("name", "message");
            response.accumulate(Vocabulary.MESSAGE, "quanto hai speso?");
            result.add(response);
        } else if (net.incomplete(sem, 1)) {
            JSONObject obj = sem.getJSONObject("domain");
            if (obj.has("property")) {
                obj.remove("property");
            }
            JSONObject response = new JSONObject();
            response.accumulate("category", "dialog");
            response.accumulate("name", "registerNotBill");
            obj.accumulate(response.getString("category"), response);
            result.add(obj);
        }
        return result;
    }

    private List<JSONObject> speechAct(JSONObject sem, Config conf, int epoch, String id_user) {
        List<JSONObject> result = null;
        if (sem.has(Vocabulary.speechActClassName)) {
            JSONObject sa = sem.getJSONObject(Vocabulary.speechActClassName);
            //System.out.println("SPEECH ACT: " + sa);
            if (sa.getInt("epoch") < epoch) {
                if (sa.getString("name").equals("quantifies")) {
                    try {
                        String url = conf.PATH_CONTABILITA + "-" + id_user + "." + conf.CONTABILITA_EXT;
                        managment = new Management().read(url);
                        if (managment.spese > 0) {
                            double amount_d = managment.spese;
                            String amount=""+amount_d;
                            String[] split=amount.split("[.]");
                            String integer=split[0];
                            String floating=split[1];
                            if(floating.length()>2){
                                floating=floating.substring(0, 2);
                            }else if(floating.length()==1){
                                floating=floating+"0";
                            }else{
                                floating="00";
                            }
                            
                            amount=integer+","+floating;
                            JSONObject number = new JSONObject().accumulate("category", "number").accumulate("name", "float").accumulate("token", amount);
                            JSONObject unit = new JSONObject().accumulate("category", "unit").accumulate("name", "euro");
                            JSONObject bill = new JSONObject().accumulate("category", "property").accumulate("name", "bill").accumulate("number", number).accumulate("unit", unit);
                            bill.accumulate(Vocabulary.NO_MESSAGE, true);
                            JSONObject domain = new JSONObject();
                            domain.accumulate("category", "domain");
                            domain.accumulate("name", "shop");
                            domain.put("property", bill);
                            JSONObject qtShop = new JSONObject().accumulate("category", "dialog").accumulate("name", "qtRegSpesa");
                            qtShop.accumulate("domain", domain);
                            result = new ArrayList<>();
                            result.add(qtShop);
                        } else {
                            JSONObject noShop = new JSONObject().accumulate("category", "dialog").accumulate("name", "regNoShop");
                            result = new ArrayList<>();
                            result.add(noShop);
                        }

                    } catch (FileNotFoundException e) {
                        result = new ArrayList<>();
                        error.put(Vocabulary.MESSAGE, "non ho trovato il file: Shopping");
                        result.add(error);
                        Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, e);
                    } catch (IOException ex) {
                        result = new ArrayList<>();
                        result.add(error);
                        Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        result = new ArrayList<>();
                        result.add(error);
                        Logger.getLogger(RegisterPlan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return result;
    }
}
