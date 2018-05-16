package knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class Node {
	String[] example;
	String name;
	String semClass;
	HashMap<Node, Property> rel;
	Resource rdf;
	Resource rdf_semClass;
	List<Node> template;

	String singular;
	String plural;
	String not;

	public Node(String[] example, String message_not, String message_singular, String message_plural, String name,
			Resource semClass) {
		this.example = example;
		this.name = name;
		rdf_semClass = semClass;
		singular = message_singular;
		plural = message_plural;
		not = message_not;
		rel = new HashMap<>();
		template = new ArrayList<>();
	}
        
        public Node(String message,String name,Resource semClass){
            this.example = new String[0];
		this.name = name;
		rdf_semClass = semClass;
		singular = message;
		plural = message;
		not = message;
		rel = new HashMap<>();
		template = new ArrayList<>();
        }
        
        public Node(String[] example,String message,String name,Resource semClass){
            this.example = example;
		this.name = name;
		rdf_semClass = semClass;
		singular = message;
		plural = message;
		not = message;
		rel = new HashMap<>();
		template = new ArrayList<>();
        }

	public void addRel(Property rel_Name, Node target) {
		rel.put(target, rel_Name);
	}

	public void addResource(Resource x) {
		rdf = x;
	}

	public void addInTemplate(Node n) {
		template.add(n);
	}

	public RDFNode[] getRDFTemplate() {
		RDFNode[] template_list = new RDFNode[template.size()];
		for (int i = 0; i < template.size(); i++) {
			template_list[i] = template.get(i).rdf;
		}
		return template_list;
	}

}
