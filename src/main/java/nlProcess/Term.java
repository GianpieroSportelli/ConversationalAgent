package nlProcess;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Term{
	String lemma;
	String morphIT;
	String pos;
	String type;
	String id;
	String target;
	
	Terminal father;
	Word child;
	
	public Term(Node x) {
		NodeList child = x.getChildNodes();
		Node span = null;
		for (int j = 0; j < child.getLength(); j++) {
			Node y = child.item(j);
			// System.out.println(y);
			if (y.getNodeName().equals("span")) {
				span = y;
			}
		}
		// System.out.println(span);
		Node target = null;
		if (span != null) {
			child = span.getChildNodes();
			for (int j = 0; j < child.getLength(); j++) {
				Node y = child.item(j);
				// System.out.println(y);
				if (y.getNodeName().equals("target")) {
					target = y;
				}
			}
		}
		// System.out.println(target);
		if (target != null) {
			NamedNodeMap attributesTarget = target.getAttributes();
			for (int i = 0; i < attributesTarget.getLength(); i++) {
				Node attribute = attributesTarget.item(i);
				String name = attribute.getNodeName();
				String value = attribute.getNodeValue();
				//System.out.println("a target " + name + ":" + value);
				if (name.endsWith("id")) {
					this.target = value;
				}
			}
		}
		NamedNodeMap attributes = x.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			String name = attribute.getNodeName();
			String value = attribute.getNodeValue();
			if (name.equals("lemma")) {
				lemma = value;
			}
			if (name.equals("pos")) {
				pos = value;
			}
			if (name.equals("morphofeat")) {
				morphIT = value;
			}
			if (name.equals("tid")) {
				id = value;
			}
			if (name.equals("type")) {
				type = value;
			}
		}
	}

	public String toString() {
		return "{term:" + id + " lemma:" + lemma + " morphIT:" + morphIT + " pos:" + pos + " type:" + type + " target:"
				+ target + "}";
	}
	
	public boolean isIt(String id){
		return this.id.equals(id);
	}
	
	public String getTarget(){
		return target;
	}
	
	public String getLemma(){
		return lemma;
	}
	
	public void setChild(Word child){
		this.child=child;
	}
	
	public Word getChild(){
		return child;
	}
	
	public String getPos(){
		return pos;
	}

}
