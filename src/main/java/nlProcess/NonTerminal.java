package nlProcess;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NonTerminal extends NodeTree{
	String id;
	String label;
	
	NonTerminal father;
	ArrayList<NodeTree> childs;
	
	public NonTerminal(Node x){
		
		childs=new ArrayList<>();
		
		NamedNodeMap attributes=x.getAttributes();
		 for(int i=0;i<attributes.getLength();i++){
		 Node attribute=attributes.item(i);
		 String name=attribute.getNodeName();
		 String value=attribute.getNodeValue();
			if (name.equals("id")) {
				id = value;
			}
			if (name.equals("label")) {
				label = value;
			}
		 }
	}
	
	public String toString(){
		return "{nt:"+id+" label:"+label+"}";
	}
	
	public boolean isIt(String id){
		return this.id.equals(id);
	}
}
