package nlProcess;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Word {
	String text;
	int lenght;
	int offset;
	int para;
	int sent;
	String id;
	
	Term father;
	
	public Word(Node x){
		text=x.getTextContent();
		NamedNodeMap attributes=x.getAttributes();
		 for(int i=0;i<attributes.getLength();i++){
		 Node attribute=attributes.item(i);
		 String name=attribute.getNodeName();
		 String value=attribute.getNodeValue();
			if (name.equals("length")) {
				lenght = Integer.valueOf(value);
			}
			if (name.equals("offset")) {
				offset = Integer.valueOf(value);
			}
			if (name.equals("para")) {
				para = Integer.valueOf(value);
			}
			if (name.equals("sent")) {
				sent = Integer.valueOf(value);
			}
			if (name.equals("wid")) {
				id = value;
			}
		 }
	}
	public String toString(){
		 return "{wf:"+id+" word:"+text+" lenght:"+lenght+" offset:"+offset+" para:"+para+" sent:"+sent+"}";
	 }
	
	public boolean isIt(String id){
		return this.id.equals(id);
	}
	
	public Term getFather(){
		return father;
	}
	
	public int getOffSet(){
		return offset;
	}
	
	public String getText(){
		return text;
	}
	
	public int getEnd(){
		return offset+lenght;
	}
}
