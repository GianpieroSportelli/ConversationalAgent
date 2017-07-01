package knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import Utils.JSON_utils;

public class KnowledgeBase {

    OntModel model;
    Map<String, Resource> cache;
    Map<Resource, List<Resource>> cache_in;
    Map<Resource, List<Resource>> cache_out;
    Map<String, List<String>> template;
    Map<String, List<String>> cacheSpeechAct;
    List<String> hierarchy;

    public void common() {
        cache = new HashMap<>();
        cache_in = new HashMap<>();
        cache_out = new HashMap<>();
        template = new HashMap<>();
        cacheSpeechAct = new HashMap<>();
        hierarchy = relationClass();
    }

    public KnowledgeBase(String url) {
        this.model = KnowledgeBase_Reader.readModel(url);
        common();

    }

    public KnowledgeBase(OntModel model) {
        this.model = model;
        common();
    }

    public OntModel getModel() {
        return model;
    }

    public void printModel() {
        model.write(System.out);
    }

    public JSONArray rel_out(JSONObject obj) {
        JSONArray result = null;
        if (has_rel_out(obj)) {
            String rel_out = obj.get("rel_out").toString();
            if (JSON_utils.isJSONArray(rel_out)) {
                result = enrichJSONArray(new JSONArray(rel_out));
            } else {
                result = enrichJSONArray(new JSONArray('[' + rel_out + ']'));
            }
        } else {
            result = new JSONArray();
        }
        return result;
    }

    public JSONArray rel_in(JSONObject obj) {
        JSONArray result = null;
        if (has_rel_in(obj)) {
            String rel_in = obj.get("rel_in").toString();
            if (JSON_utils.isJSONArray(rel_in)) {
                result = enrichJSONArray(new JSONArray(rel_in));
            } else {
                result = enrichJSONArray(new JSONArray('[' + rel_in + ']'));
            }
        } else {
            result = new JSONArray();
        }
        return result;
    }

    public boolean has_rel_out(JSONObject obj) {
        return (obj).has("rel_out");
    }

    public boolean has_rel_in(JSONObject obj) {
        return (obj).has("rel_in");
    }

    public boolean isTerminal(JSONObject obj) {
        return !has_rel_out(obj);
    }

    public JSONArray enrichJSONArray(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            array.put(i, new JSONObject(enrich(array.get(i).toString())));
        }
        return array;
    }

    public JSONObject cleanEnrich(JSONObject obj) {
        if (obj.has("rel_in")) {
            obj.remove("rel_in");
        }

        if (obj.has("rel_out")) {
            String out = obj.get("rel_out").toString();
            if (out.charAt(0) != '[') {
                out = '[' + out + ']';
            }
            JSONArray rel_out = new JSONArray(out);
            obj.remove("rel_out");
            for (int i = 0; i < rel_out.length(); i++) {
                JSONObject out_obj = rel_out.getJSONObject(i);
                String category = out_obj.getString("category");
                if (obj.has(category)) {
                    if (obj.get(category) instanceof JSONObject) {
                        cleanEnrich(obj.getJSONObject(category));
                    } else if (obj.get(category) instanceof JSONArray) {
                        JSONArray array = obj.getJSONArray(category);
                        for (int k = 0; k < array.length(); k++) {
                            cleanEnrich(array.getJSONObject(k));
                        }
                    }
                }
            }
        }
        return obj;
    }

    public String enrich(String message) {
        if (JSON_utils.isJSONArray(message)) {
            JSONArray ambiguos_message = JSON_utils.convertJSONArray(message);
            for (int i = 0; i < ambiguos_message.length(); i++) {
                JSONObject part = ambiguos_message.getJSONObject(i);
                ambiguos_message.put(i, new JSONObject(enrich(part.toString())));
            }
            return ambiguos_message.toString();
        } else {
            JSONObject jsonMessage = new JSONObject(message);
            Resource res = getResource(jsonMessage);
            List<Resource> in_rel = in_Relation(res);
            List<Resource> out_rel = out_Relation(res);
            for (Resource in : in_rel) {
                String name = getName(in);
                String category = getCategory(in);
                JSONObject in_json = new JSONObject();
                in_json.accumulate("name", name);
                in_json.accumulate("category", category);
                jsonMessage.accumulate("rel_in", in_json);
            }
            for (Resource out : out_rel) {
                String name = getName(out);
                String category = getCategory(out);
                JSONObject out_json = new JSONObject();
                out_json.accumulate("name", name);
                out_json.accumulate("category", category);
                jsonMessage.accumulate("rel_out", out_json);
            }
            JSONObject def = getDefault(res);
            if (def != null) {
                jsonMessage.accumulate(Ontology._defaultPropertyName, def);
            }
            String plan = getPlan(res);
            if (plan != null) {
                jsonMessage.accumulate(Ontology.planName, plan);
            }
            return jsonMessage.toString();
        }
    }

    private String getName(Resource res) {
        String result = null;
        if (res.hasProperty(Ontology.name)) {
            result = ((Literal) res.getProperty(Ontology.name).getObject()).getString();
        }
        return result;
    }

    private String getCategory(Resource res) {
        String result = null;
        if (res.hasProperty(RDF.type)) {
            RDFNode ontClassNode = res.getProperty(RDF.type).getObject();
            Resource ontClass = model.getResource(ontClassNode.toString());
            result = getName(ontClass);
        }
        return result;
    }

    private Resource getResource(JSONObject message) {
        Resource result = null;
        JSONObject json = new JSONObject(message.toString());
        String category = json.getString("category");
        String semElement = json.getString("name");

        if (!cache.containsKey(json.toString())) {
            String queryString = "SELECT ?n \n WHERE{?n <" + RDF.type + "> ?category.\n ?category <" + Ontology.name
                    + "> \"" + category + "\".\n?n <" + Ontology.name + "> \"" + semElement + "\"}";

            Query query = QueryFactory.create(queryString);

            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                QuerySolution soln = results.nextSolution();
                Resource n = soln.getResource("n");
                return n;
            }
        } else {
            result = cache.get(json.toString());
            // if (DEBUG)
            // System.out.println("GET res of " + json + " IN CACHE");
        }
        return result;
    }

    private List<Resource> in_Relation(Resource res) {
        List<Resource> result = null;
        if (cache_in.containsKey(res)) {
            result = cache_in.get(res);
        } else {
            result = new ArrayList<>();
            for (ObjectProperty p : Ontology.listProperty) {
                String queryString = null;
                queryString = "SELECT ?n \n WHERE {?n <" + p + "> <" + res + ">}";
                Query query = QueryFactory.create(queryString);
                QueryExecution qexec = QueryExecutionFactory.create(query, model);
                ResultSet results = qexec.execSelect();
                for (; results.hasNext();) {
                    QuerySolution soln = results.nextSolution();
                    Resource r = soln.getResource("n");
                    result.add(r);
                }
            }
            cache_in.put(res, result);
        }
        return result;
    }

    private List<Resource> out_Relation(Resource res) {
        List<Resource> result = null;
        if (cache_out.containsKey(res)) {
            result = cache_out.get(res);
        } else {
            result = new ArrayList<>();
            for (ObjectProperty p : Ontology.listProperty) {
                String queryString = null;
                queryString = "SELECT ?n \n WHERE {<" + res + "> <" + p + "> ?n}";
                Query query = QueryFactory.create(queryString);
                QueryExecution qexec = QueryExecutionFactory.create(query, model);
                ResultSet results = qexec.execSelect();
                for (; results.hasNext();) {
                    QuerySolution soln = results.nextSolution();
                    Resource r = soln.getResource("n");
                    result.add(r);
                }
            }
            cache_out.put(res, result);
        }
        return result;
    }

    public JSONObject getDefault(Resource res) {
        JSONObject result = null;
        String queryString = null;
        queryString = "SELECT ?n \n WHERE{<" + res + "> <" + Ontology._default + "> ?n}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            Resource r = soln.getResource("n");
            result = new JSONObject();
            result.accumulate("category", getCategory(r));
            result.accumulate("name", getName(r));
            break;
        }
        return result;
    }

    public String getPlan(Resource res) {
        String result = null;
        String queryString = null;
        queryString = "SELECT ?n \n WHERE {<" + res + "> <" + Ontology.plan + "> ?n}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            Resource r = soln.getResource("n");
            result = getName(r);
            break;
        }
        return result;
    }

    public String getPlan(JSONObject obj) {
        String result = null;
//        System.out.println("FIND PLAN: \n"+obj.toString(4));
//        System.out.println(Ontology.planName);
        if (obj.has(Ontology.planName)) {
            result = obj.getString(Ontology.planName);
        }
        return result;
    }

    public String get_message_not(JSONObject obj) {
        String result = null;
        JSONObject json = new JSONObject();
        json.accumulate("name", obj.get("name"));
        json.accumulate("category", obj.get("category"));

        Resource res = getResource(json);
        NodeIterator itrNot = model.listObjectsOfProperty(res, Ontology.message_not);
        while (itrNot.hasNext()) {
            RDFNode NodeEx = itrNot.next();
            result = ((Literal) NodeEx).getString();
        }
        if (JSON_utils.isJSONObject(result)) {
            String field = (new JSONObject(result)).getString("value");
            result = obj.get(field).toString();
        }
        return result;
    }

    public String get_message_singular(JSONObject obj) {
        String result = null;
        JSONObject json = new JSONObject();
        json.accumulate("name", obj.get("name"));
        json.accumulate("category", obj.get("category"));

        Resource res = getResource(json);
        NodeIterator itrNot = model.listObjectsOfProperty(res, Ontology.message_singular);
        while (itrNot.hasNext()) {
            RDFNode NodeEx = itrNot.next();
            result = ((Literal) NodeEx).getString();
        }
        if (JSON_utils.isJSONObject(result)) {
            String field = (new JSONObject(result)).getString("value");
            result = obj.get(field).toString();
        }
        return result;
    }

    public String get_message_plural(JSONObject obj) {
        String result = null;
        JSONObject json = new JSONObject();
        json.accumulate("name", obj.get("name"));
        json.accumulate("category", obj.get("category"));

        Resource res = getResource(json);
        NodeIterator itrNot = model.listObjectsOfProperty(res, Ontology.message_plural);
        while (itrNot.hasNext()) {
            RDFNode NodeEx = itrNot.next();
            result = ((Literal) NodeEx).getString();
        }
        if (JSON_utils.isJSONObject(result)) {
            String field = (new JSONObject(result)).getString("value");
            result = obj.get(field).toString();
        }
        return result;
    }

    public List<String> relationClass() {
        List<String> result = new ArrayList<>();
        result = readChild(null, 0);
        return result;

    }

    public List<String> readChild(Resource father, int deep) {
        List<String> result = new ArrayList<>();
        List<Resource> listR = new ArrayList<>();
        String queryString = null;
        if (father == null) {
            queryString = "SELECT ?n \n WHERE{ ?n <" + RDF.type + "> <" + RDFS.Class + ">.\n OPTIONAL{?a <"
                    + Ontology.relation + "> ?n .}\n  FILTER(!bound(?a))}";
        } else {
            queryString = "SELECT ?n \n WHERE { ?n <" + Ontology.name + "> ?name.\n <" + father + "> <"
                    + Ontology.relation + "> ?n }";
        }
         //System.out.println("Query: " + queryString);
        Query query = QueryFactory.create(queryString);

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            // RDFNode rel = soln.get("rel") ; // Get a result variable by name.
            Resource r = soln.getResource("n"); // Get a result variable -
            String name = getName(r);

            if (name != null && !result.contains(name)) {
                 //System.out.println("class"+deep+": " + name);
                result.add(name);
                listR.add(r);
            }
        }

        for (Resource r : listR) {
        	List<String> result1=readChild(r, deep + 1);
            for(String r1:result1){
            	if(!result.contains(r1)){
            		result.add(r1);
            	}
            }
        }
        return result;
    }
    public List<String> getHierarchy() {
        return hierarchy;
    }

    public int getMinCardinality(JSONObject sup, JSONObject obj) {
        Resource resSup = getResource(sup);
        Resource resObj = getResource(obj);
        String queryString = "SELECT ?n \n WHERE{<" + resSup + ">  ?rel <" + resObj + ">.\n ?rel <"
                + Ontology.min_card + "> ?n }";
        //System.out.println("Query Min Cardinality:"+queryString);
        Query query = QueryFactory.create(queryString);

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            // RDFNode rel = soln.get("rel") ; // Get a result variable by name.
            // Resource rel = soln.getResource("sup"); // Get a result variable
            // -
            // must be a resource
            Literal n = soln.getLiteral("n");
            if (n != null) {
                int number = Ontology.cardinality().get(n.toString());
                return number;
            }
        }
        return -1;
    }

    public int getMaxCardinality(JSONObject sup, JSONObject obj) {
        Resource resSup = getResource(sup);
        Resource resObj = getResource(obj);
        String queryString = "SELECT ?n \n WHERE{<" + resSup + ">  ?rel <" + resObj + ">.\n ?rel <"
                + Ontology.min_card + "> ?n }";
        Query query = QueryFactory.create(queryString);

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            // RDFNode rel = soln.get("rel") ; // Get a result variable by name.
            Resource rel = soln.getResource("sup"); // Get a result variable -
            // must be a resource
            Literal n = soln.getLiteral("n");
            if (n != null) {
                int number = Ontology.cardinality().get(n.toString());
                return number;
            }
        }
        return -1;
    }

    public boolean incomplete(JSONObject obj) {
        boolean result = false;
        if (has_rel_out(obj)) {
            JSONArray children = JSON_utils.convertJSONArray(obj.get("rel_out").toString());
            for (int i = 0; i < children.length() && !result; i++) {
                JSONObject child = children.getJSONObject(i);
                int min = getMinCardinality(obj, child);
                if (min != 0) {
                    String child_category = child.getString("category");
                    if (obj.has(child_category)) {
                        JSONArray category = JSON_utils.convertJSONArray(obj.get(child_category).toString());
                        for (int j = 0; j < category.length() && !result; j++) {
                            child = category.getJSONObject(j);
                            result = result || incomplete(child);
                        }
                    } else {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public boolean incomplete(JSONObject obj, int level) {
        boolean result = false;
        if (has_rel_out(obj)) {
            JSONArray children = JSON_utils.convertJSONArray(obj.get("rel_out").toString());
            for (int i = 0; i < children.length() && !result; i++) {
                JSONObject child = children.getJSONObject(i);
                int min = getMinCardinality(obj, child);
                if (min != 0) {
                    String child_category = child.getString("category");
                    if (obj.has(child_category)) {
                        if (level > 0) {
                            JSONArray category = JSON_utils.convertJSONArray(obj.get(child_category).toString());
                            for (int j = 0; j < category.length() && !result; j++) {
                                child = category.getJSONObject(j);
                                result = result || incomplete(child, level - 1);
                            }
                        }
                    } else {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public JSONArray remove_incomplete(JSONArray array) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            if (!incomplete(obj)) {
                result.put(obj);
            }
        }
        return result;
    }

    public List<String> getTemplate(String name) {
        List<String> result;
        if (template.containsKey(name)) {
            result = template.get(name);
        } else {
            result = new ArrayList<>();
            StmtIterator iterClass = model.listStatements(new SimpleSelector(null, Ontology.name, name));
            while (iterClass.hasNext()) {
                Statement stmClass = iterClass.next();
                Resource res = stmClass.getSubject();
                Statement listTemplate = res.getProperty(Ontology.template);
                if (listTemplate != null) {
                    Resource list = (Resource) listTemplate.getObject();
                    while (list != null && list.hasProperty(RDF.first)) {
                        Resource first = (Resource) list.getProperty(RDF.first).getObject();
                        result.add(getName(first));
                        if (list.hasProperty(RDF.rest)) {
                            list = (Resource) list.getProperty(RDF.rest).getObject();
                        } else {
                            list = null;
                        }
                    }
                }
            }
            template.put(name, result);
        }
        return result;
    }

    public boolean onlyOne(JSONObject sup, JSONObject obj) {
        Boolean result;
        result = getMaxCardinality(sup, obj) == 1;
        return result;
    }

    public List<String> getInfluence(JSONObject obj) {
        List<String> result = null;
        Resource res = getResource(obj);
        String queryString = "SELECT ?n \n WHERE{?n <" + Ontology.speechAct + "> <" + res + "> }";
        if (!cacheSpeechAct.containsKey(obj.toString())) {
            Query query = QueryFactory.create(queryString);

            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
                QuerySolution soln = results.nextSolution();
                Resource n = soln.getResource("n");
                JSONObject act = new JSONObject();
                String name = getName(n);
                String category = getCategory(n);
                act.accumulate("name", name);
                act.accumulate("category", category);
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(act.toString());
            }
            cacheSpeechAct.put(obj.toString(), result);
        } else {
            result = cacheSpeechAct.get(obj.toString());
            // if (DEBUG)
            // System.out.println("GET res of " + json + " IN CACHE");
        }
        return result;
    }

}
