package patternMatching;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import configuration.Config;

/**
 * Un SemanticPool è un insieme di SemanticElement che ricadono sotto la stessa
 * categoria semantica (Es. Creare è un instanza di Intento quindi andrà nel
 * pool INTENT)
 * 
 * @author Gianpiero Sportelli
 *
 */
public class SemanticPool implements Serializable,Comparable<SemanticPool> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7338704668513255445L;

	static final boolean DEBUG = Config.getSemanticPoolDebug();

	// Insieme di SemanticElement che costituiscono il pool
	private HashSet<SemanticElement> pool;

	// Nome del pool inteso come categoria
	private String category;

	// Soglia di accettazione, se la confidenza è inferiore alla soglia il
	// SemanticElement non è stato individuato
	private float threshold;
	
	//la salience è un valore di importanza del semantic pool (Categoria Semantica)
	private int salience;

	/**
	 * Metodo costruttore del SemanticPool
	 * 
	 * @param category
	 *            nome della categoria semantica sottesa dal pool
	 * @param threshold
	 *            valore soglia di confidenza
	 */
	public SemanticPool(String category, float threshold,int salience) {
		this.category = category;
		this.threshold = threshold;
		pool = new HashSet<SemanticElement>();
		this.salience=salience;
	}

	/**
	 * Crea un SemanticElement nel pool on insieme di esempi vuoto
	 * 
	 * @param tag
	 *            nome del SemanticElement che si sta creando
	 * @return esito della creazione
	 */
	public boolean createSemanticElement(String tag) {
		HashSet<String> set = new HashSet<String>();
		SemanticElement element = new SemanticElement(tag, set);
		return pool.add(element);
	}

	/**
	 * Aggiunge un esempio a un SemanticElement già creato
	 * 
	 * @param tag
	 *            nome del SemanticElement a cui si vuole aggiungere l'esempio
	 * @param example
	 *            frase d'esempio
	 * @return
	 */
	public boolean addExample(String tag, String example) {
		boolean result = false;
		for (SemanticElement e : pool) {
			if (e.getTag().equals(tag)) {
				result = e.addExample(example);
				break;
			}
		}
		return result;
	}

	/**
	 * Metodo che si occupa di individuare un possibile SemanticElement presente
	 * nella frase della particolare categoria
	 * 
	 * @param input
	 *            frase di input
	 * @return tag del SemanticElement se persente, null altrimenti
	 */
	public JSONObject bestSemanticElement(String input) {
		JSONObject result = null;
		double bestScore = 0d;
		int bestPatternSize=0;
		int bestStart=-1;
		int bestStop=-1;
		//x ogni elemento del pool
		for (SemanticElement e : pool) {
			//ottengo il miglior pattern
			JSONObject confidence = e.confidence(input);
			//se esite un pattern
			if (confidence != null) {
				//estraggo score e pattern size
				double score=confidence.getDouble("score");
				int patternSize=confidence.getInt("size");
				int start=confidence.getInt("index");
				int stop=confidence.getInt("end");
				if (score == bestScore && score > threshold && start==bestStart && stop==bestStop) {
					confidence.accumulate("tag",e.getTag());
					result.accumulate("result", confidence);
				}else
				//se lo score è uguale al migliore verifico la lunghezza del miglior pattern
				if (score == bestScore && score > threshold && patternSize>bestPatternSize) {
					if (DEBUG) {
						System.out.println("change best for length " + confidence + " " + e.getTag() + " treshold " + threshold);
					}
					bestScore = score;
					bestPatternSize= patternSize;
					confidence.accumulate("tag",e.getTag());
					result=new JSONObject();
					result.accumulate("result", confidence);
					bestStart=start;
					bestStop=stop;
				}else if (score > bestScore && score > threshold) {
					//se lo score è maggiore cambio a prescindere dalla lunghezza
					if (DEBUG) {
						System.out.println("change best for score " + confidence + " " + e.getTag() + " treshold " + threshold);
					}
					bestScore = score;
					bestPatternSize= patternSize;
					confidence.accumulate("tag",e.getTag());
					result=new JSONObject();
					result.accumulate("result", confidence);
					bestStart=start;
					bestStop=stop;
				}
			}
		}
		return result;
	}

	public String getCategory() {
		return category;
	}

	public Set<SemanticElement> getSemanticElement() {
		return pool;
	}

	public float getTreshold() {
		return threshold;
	}
	
	@Override
	public boolean equals(Object obj){
		boolean result=false;
		try{
			SemanticPool p=(SemanticPool)obj;
			result=p.category.equals(this.category);
		}catch(ClassCastException ex){
			System.err.println("Error cast SemanticPool method .equals(Object obj).");
			ex.printStackTrace();
		}
		return result;
		
	}
	@Override
	public String toString(){
		String result="{\n";
		result+="CATEGORY: "+category+"\n";
		result+="SALIENCE: "+salience+"\n";
		result+="THRESHOLD: "+threshold+"\n";
		for(SemanticElement el:pool){
			result+=el.toString();
		}
		result+="}\n";
		return result;
	}

	@Override
	public int compareTo(SemanticPool o) {
		Integer s=salience;
		Integer s1=o.salience;
		return -1*s.compareTo(s1);
	}

}
