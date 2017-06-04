package nlGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import Utils.JSON_utils;
import configuration.Config;
import knowledge.KnowledgeBase;
import knowledge.Ontology;

public class NLG {

    KnowledgeBase net;
    static final boolean DEBUG = Config.getNLGDebug();
    private static final int NO_RESULT = 1;
    private static final int SINGULAR = 2;
    private static final int PLURAL = 3;

    public NLG(KnowledgeBase net) {
        this.net = net;
    }

    public String getComposedMessage(List<JSONObject> query_res) {
        String message = "";
        for (JSONObject res : query_res) {
            message += message(res);
        }
        boolean no_mess = true;
        char[] message_array = message.toCharArray();
        for (char x : message_array) {
            if (Character.isAlphabetic(x) || Character.isDigit(x)) {
                no_mess = false;
                break;
            }
        }
        if (no_mess) {
            JSONObject noAnswer = new JSONObject();
            noAnswer.accumulate("category", "noAnswer");
            noAnswer.accumulate("name", "noAnswer");
            message = net.get_message_not(noAnswer);
        }
        return message;
    }

    public String message(JSONObject queryResult) {
        if (DEBUG) {
            System.out.println(queryResult.toString(4));
        }
        if (queryResult == null) {
            JSONObject noAnswer = new JSONObject();
            noAnswer.accumulate("category", "noAnswer");
            noAnswer.accumulate("name", "noAnswer");
            return net.get_message_not(noAnswer);
        }
        int n = 0;
        String message;
        JSONArray result = null;
        if (queryResult.has("result")) {
            result = JSON_utils.convertJSONArray(queryResult.get("result").toString());
            n = result.length();
        }
        JSONObject query = null;
        if (queryResult.has("query")) {
            query = queryResult.getJSONObject("query");
        } else if (queryResult.has("category")) {
            query = queryResult;
        }

        switch (n) {
            case 0:
                message = getMessage(result, query, new ArrayList<String>(), NO_RESULT);
                break;
            case 1:
                message = getMessage(result, query, new ArrayList<String>(), SINGULAR);
                break;
            default:
                message = getMessage(result, query, new ArrayList<String>(), PLURAL);

        }
        return message;
    }

    private String getMessage(JSONArray result, JSONObject query, ArrayList<String> incomplete, int type) {
        if (DEBUG) {
            System.out.println("get Message:\n" + query.toString(4));
        }

        String message = "";
        String m = "";
        if (query.has(Ontology.MESSAGE)) {
            m = query.getString(Ontology.MESSAGE);
        } else if (!query.has(Ontology.NO_MESSAGE)) {
            switch (type) {
                case NO_RESULT:
                    m = net.get_message_not(query);
                    break;
                case SINGULAR:
                    m += net.get_message_singular(query);
                    break;
                case PLURAL:
                    m = net.get_message_plural(query);
                    break;
            }
        }
        if (DEBUG) {
            System.out.println("message: " + m);
        }
        if (m.length() > 0) {
            message += m + " ";
        }

        String query_category = query.getString("category");
        String query_name = query.getString("name");
        List<String> template_category = net.getTemplate(query_category);
        for (String category : template_category) {
            if (query.has(category)) {
                JSONArray category_array = JSON_utils.convertJSONArray(query.get(category).toString());
                List<String> template_name = net.getTemplate(query_name);
                if (template_name.isEmpty()) {
                    for (int j = 0; j < category_array.length(); j++) {
                        JSONObject sub_query = category_array.getJSONObject(j);
                        if (DEBUG) {
                            System.out.println("sub query by category:\n" + sub_query.toString(4));
                        }
//                        if (net.incomplete(sub_query)) {
//                            incomplete.add("{\"name\":\"" + sub_query.getString("name") + "\",\"category\":\""
//                                    + sub_query.getString("category") + "\"}");
//                        } else {
                        message += getMessage(null, sub_query, incomplete, type);
//                        }
                    }
                } else {
                    for (String name : template_name) {
                        for (int j = 0; j < category_array.length(); j++) {
                            JSONObject sub_query = category_array.getJSONObject(j);

                            if (sub_query.getString("name").equals(name)) {
                                if (DEBUG) {
                                    System.out.println("sub query by name:\n" + sub_query.toString(4));
                                }
//                                if (net.incomplete(sub_query) && false) {
//                                    incomplete.add("{\"name\":\"" + sub_query.getString("name")
//                                            + "\",\"category\":\"" + sub_query.getString("category") + "\"}");
//                                } else {
                                message += getMessage(null, sub_query, incomplete, type);
//                                }
                            }
                        }
                    }
                }
            }
        }

        if (type == SINGULAR) {
            String top = confirmQuestion(result, incomplete, query);
            if (top != null) {
                message = top + message + ".\n";
                result = null;
            }
        }

        if (result != null && type != 1) {
            message += ".\n";
            message += resultMessage(result, incomplete);
        }
        return message;
    }

    private String resultMessage(JSONArray result, List<String> incomplete) {
        String message = "";
        for (int i = 0; i < result.length(); i++) {
            JSONObject obj = result.getJSONObject(i);
            if (incomplete.size() > 0 && incomplete.get(0).equals("*")) {
                String feature = obj.toString(4);
                message += "     " + feature + "\n";
            } else if (obj.has("name")) {
                message += "  " + obj.getString("name") + "\n";
                if (incomplete.isEmpty()) {
                    //Map<String, Object> res = obj.toMap();
                    Map<String, Object> res = JSON_utils.toMap(obj);
                    for (String key : res.keySet()) {
                        if (!key.equals("compatible") && !key.equals("name")) {
                            message += "     " + key + ":" + obj.get(key) + "\n";
                        }
                    }
                } else {
                    for (String prop : incomplete) {
                        JSONObject predicate = new JSONObject(prop);
                        JSONArray part = contains_predicate(predicate, obj);
                        if (part != null) {
                            for (int j = 0; j < part.length(); j++) {
                                String sub_message = net.get_message_singular(predicate) + " "
                                        + part.getJSONObject(j).get(predicate.getString("name"));
                                message += "     " + sub_message + "\n";
                            }
                        }
                    }
                }
            }
        }
        return message;
    }

    private JSONArray contains_predicate(JSONObject json, JSONObject obj) {
        String name = json.getString("name");
        JSONArray result = new JSONArray();
        if (obj.has(name)) {
            result.put(obj);
            return result;
        } else {
            Map<String, Object> map = JSON_utils.toMap(obj);
            for (String key : map.keySet()) {
                String val = obj.get(key).toString();
                if (JSON_utils.isJSONObject(val)) {
                    return contains_predicate(json, new JSONObject(val));
                } else if (JSON_utils.isJSONArray(val)) {
                    JSONArray array = new JSONArray(val);
                    for (int i = 0; i < array.length(); i++) {
                        String sub = array.get(i).toString();
                        if (JSON_utils.isJSONObject(sub)) {
                            JSONArray result_sub = contains_predicate(json, new JSONObject(sub));
                            if (result_sub != null) {
                                for (int j = 0; j < result_sub.length(); j++) {
                                    result.put(result_sub.get(j));
                                }
                            }
                        }
                    }
                    return result;
                }
            }
        }
        return null;
    }

    private JSONArray contains_node(JSONObject json, JSONObject obj_origin) {
        JSONObject obj = net.cleanEnrich(obj_origin);
        String name = json.getString("name");
        String category = json.getString("category");
        JSONArray result = null;
        if (obj.getString("name").equals(name) && obj.getString("category").equals(category)) {
            result = new JSONArray();
            result.put(obj);
            return result;
        } else {
            Map<String, Object> map = JSON_utils.toMap(obj);
            for (String key : map.keySet()) {
                String val = obj.get(key).toString();
                if (JSON_utils.isJSONObject(val)) {
                    result = JSON_utils.merge(result, contains_node(json, new JSONObject(val)));

                } else if (JSON_utils.isJSONArray(val)) {

                    JSONArray array = new JSONArray(val);

                    for (int i = 0; i < array.length(); i++) {

                        String sub = array.get(i).toString();

                        JSONArray sub_array = JSON_utils.convertJSONArray(sub);

                        for (int j = 0; j < sub_array.length(); j++) {

                            String sub_value = sub_array.get(j).toString();
                            if (JSON_utils.isJSONObject(sub_value)) {
                                JSONArray result_sub = contains_node(json, new JSONObject(sub_value));
                                result = JSON_utils.merge(result, result_sub);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private String confirmQuestion(JSONArray result, List<String> incomplete, JSONObject query) {
        if (result != null && incomplete.isEmpty()) {
            JSONObject name_tag = new JSONObject();
            name_tag.accumulate("category", "property");
            name_tag.accumulate("name", "name capsule");
            JSONArray contains = new JSONArray();
            JSONArray contains_capsule = contains_node(name_tag, query);
            if (contains_capsule != null) {
                for (int i = 0; i < contains_capsule.length(); i++) {
                    contains.put(contains_capsule.getJSONObject(i));
                }
            }
            name_tag.put("name", "name machine");
            JSONArray contains_machine = contains_node(name_tag, query);
            if (contains_machine != null) {
                for (int i = 0; i < contains_machine.length(); i++) {
                    contains.put(contains_machine.getJSONObject(i));
                }
            }
            if (contains.length() > 0) {
                boolean complete = true;
                for (int i = 0; i < contains.length() && complete; i++) {
                    complete = !net.incomplete(contains.getJSONObject(i));
                }
                if (complete) {
                    JSONObject confirm = new JSONObject();
                    confirm.accumulate("category", "confirmQuestion");
                    confirm.accumulate("name", "confirmQuestion");
                    return net.get_message_singular(confirm) + " ";
                }
            }
        }
        return null;
    }

}
