package knowledge;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import configuration.Config;

public class KnowledgeBase_Reader {

    public static void main(String[] args) {
        System.out.print(readNLU(readModel(Config.getPathSemanticNet())));
    }

    public static JSONObject readNLU(OntModel model) {
        JSONObject result = new JSONObject();

        Map<Resource, Integer> map = classQuery(model);
        for (Resource r : map.keySet()) {

            String Classname = resName(r);
            Integer salience = map.get(r);

            //System.out.println(Classname+" salience: "+map.get(r));
            JSONObject ontoClass = new JSONObject();
            ontoClass.accumulate("name", Classname);
            ontoClass.accumulate("salience", salience);
            boolean hasElement = false;
            List<Resource> elements = semElementQuery(model, r);
            for (Resource e : elements) {

                String Elementname = resName(e);

                //System.out.println("		"+Elementname);
                JSONObject element = new JSONObject();
                element.accumulate("name", Elementname);

                List<String> examples = ExampleQuery(model, e);
                if (examples.size() > 0) {
                    for (String ex : examples) {
                        element.accumulate("examples", ex);
                        //System.out.println("			"+ex);
                    }
                    hasElement = true;
                    ontoClass.accumulate("elements", element);
                }
            }
            if (hasElement) {
                result.accumulate("result", ontoClass);
            }
        }

        return result;

    }

    public static OntModel readModel(String url) {
        OntModel model = ModelFactory.createOntologyModel();
        InputStream in = FileManager.get().open(url);
        if (in == null) {
            throw new IllegalArgumentException("File: " + url + " not found");
        }

        // read the RDF/XML file
        model.read(in, KnowledgeBase_Creator.format);
        return model;
    }

    private static String resName(Resource res) {
        String result = null;
        if (res.hasProperty(Ontology.name)) {
            result = ((Literal) res.getProperty(Ontology.name).getObject()).getString();
        }
        return result;
    }

    private static Map<Resource, Integer> classQuery(OntModel model) {
        Map<Resource, Integer> map = new HashMap<>();
        StmtIterator iterClass = model.listStatements(new SimpleSelector(null, RDF.type, RDFS.Class));
        while (iterClass.hasNext()) {
            Statement stmClass = iterClass.next();
            Resource res = stmClass.getSubject();
            Statement resName = res.getProperty(Ontology.name);
            if (resName != null) {
                Statement stmSalience = res.getProperty(Ontology.salience);
                Integer salience = ((Literal) stmSalience.getObject()).getInt();
                map.put(res, salience);
            }
        }
        return map;
    }

    private static List<Resource> semElementQuery(Model model, Resource c) {
        List<Resource> list = new ArrayList<>();
        StmtIterator iterElement = model.listStatements(new SimpleSelector(null, RDF.type, c));
        while (iterElement.hasNext()) {
            Statement stmElement = iterElement.next();
            Resource res = stmElement.getSubject();
            list.add(res);
        }
        return list;
    }

    private static List<String> ExampleQuery(Model model, Resource c) {
        List<String> list = new ArrayList<>();
        if (c.hasProperty(Ontology.example)) {
            NodeIterator itrExample = model.listObjectsOfProperty(c, Ontology.example);
            while (itrExample.hasNext()) {
                RDFNode NodeEx = itrExample.next();
                String ex = ((Literal) NodeEx).getString();
                list.add(ex);
            }
        }
        return list;
    }

}
