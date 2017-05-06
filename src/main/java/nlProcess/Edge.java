package nlProcess;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Edge {
	boolean head;
	String from;
	String to;
	String id;
	
	public Edge(Node x){
		NamedNodeMap attributes=x.getAttributes();
		 for(int i=0;i<attributes.getLength();i++){
		 Node attribute=attributes.item(i);
		 String name=attribute.getNodeName();
		 String value=attribute.getNodeValue();
			if (name.equals("id")) {
				id = value;
			}
			if (name.equals("from")) {
				from = value;
			}
			if (name.equals("to")) {
				to = value;
			}
			if (name.equals("head")) {
				head=true;
			}
		 }
	}
	
	public String toString(){
		return "{id:"+id+" from:"+from+" to:"+to+" head:"+head+"}";
	}
}
