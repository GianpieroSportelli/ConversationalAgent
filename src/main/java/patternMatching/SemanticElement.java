package patternMatching;

import java.io.Serializable;
import java.util.HashSet;

import org.json.JSONObject;

public class SemanticElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1021667723897985274L;
	
	//Nome del Entità/Intenzione sottesa dalla frasi di esempio (Es. Creare oppure Sveglia)
	private String tag;
	//Insieme di frasi di esempio che sottendono l'elemento semantico
	private HashSet<String> example;
	
	/**
	 * Costruttore SemanticElement
	 * @param tag Nome del elemento semantico sotteso
	 * @param example insieme di esempi
	 */
	public SemanticElement(String tag,HashSet<String> example){
		this.tag=tag;
		this.example=example;
	}
	
	/**
	 * Metodo che data in input una frase restitusce un valore di confidenza sulla presenza del SemanticElement
	 * @param input frase di input
	 * @return valore di confidenza
	 */
//	public JSONObject confidence(String input){
//		double max=0d;
//		JSONObject result=null;
//		String best_pattern="";
//		//per ogni esempio presente nel insieme
//		for(String pattern:example){
//			//Imposto il modello di allineamento
//			TokenAlignment allign=new TokenAlignment(input, pattern);
//			JSONObject resultAllign=allign.score();
//			double score=resultAllign.getDouble("score");
//			if(score>max){
//				max=score;
//				result=resultAllign;
//				best_pattern=pattern;
//			}else if(score==max && pattern.length()>best_pattern.length()){
//				max=score;
//				result=resultAllign;
//				best_pattern=pattern;
//			}
//		}
//		return result;
//	}
	/**
	 * Metodo che permette l'inserimento di nuove frasi d'esempio
	 * @param example frase d'esempio
	 * @return esito inserimento
	 */
	public boolean addExample(String example){
		return this.example.add(example);
	}
	
	/**
	 * Modifica del metodo equal, l'uguaglianza è data solo dal tag del SemanticElement
	 */
	@Override
	public boolean equals(Object obj){
		boolean result=false;
		try{
			SemanticElement e=(SemanticElement)obj;
			result=e.tag.equals(this.tag);
		}catch(ClassCastException ex){
			System.err.println("Error cast SemanticElement method .equals(Object obj).");
			ex.printStackTrace();
		}
		return result;
		
	}

	public String getTag() {
		return tag;
	}
	@Override
	public String toString(){
		String result="";
		result+="TAG: "+tag+"{\n";
		for(String ex:example){
			result+=ex+"\n";
		}
		result+="}\n";
		return result;
		
	}
	
}
