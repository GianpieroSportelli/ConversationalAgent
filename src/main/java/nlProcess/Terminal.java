package nlProcess;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Terminal extends NodeTree{
	String id;
	String target;
	
	NonTerminal father;
	Term child;
	
	public Terminal(Node x){
		NodeList child = x.getChildNodes();
		Node span = null;
		for (int j = 0; j < child.getLength(); j++) {
			Node y = child.item(j);

			if (y.getNodeName().equals("span")) {
				span = y;
			}
		}

		Node target = null;
		if (span != null) {
			child = span.getChildNodes();
			for (int j = 0; j < child.getLength(); j++) {
				Node y = child.item(j);
				if (y.getNodeName().equals("target")) {
					target = y;
				}
			}
		}

		if (target != null) {
			NamedNodeMap attributesTarget = target.getAttributes();
			for (int i = 0; i < attributesTarget.getLength(); i++) {
				Node attribute = attributesTarget.item(i);
				String name = attribute.getNodeName();
				String value = attribute.getNodeValue();
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
	
			if (name.equals("id")) {
				id = value;
			}
		}
	}
	
	public String toString(){
		return "{t:"+id+" target:"+target+"}";
	}
	
	public boolean isIt(String id){
		return this.id.equals(id);
	}
}
