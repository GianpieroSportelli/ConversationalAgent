package patternMatching;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import configuration.Config;
import nlProcess.WatsonRealTimeNLP;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WatsonTranslateToken {

	static final boolean DEBUG = Config.getTranslateTokenDebug();
	static final boolean DEEP_DEBUG = Config.getTranslateTokenDeepDebug();
	private static Logger log = Logger.getLogger(WatsonTranslateToken.class.getName());
	// Categorie semantiche di interesse
	ArrayList<SemanticPool> knowledge;
	String hostname;
	String port;
	String collection;

	/**
	 * Costruttore
	 *
	 * @param name
	 */
	public WatsonTranslateToken(String hostname,String port,String collection) {
		knowledge = new ArrayList<>();
		this.hostname=hostname;
		this.collection=collection;
		this.port=port;
		
	}

	/**
	 * Costruttore che estrae categorie, elementi semantici e esempi da un file
	 * RDF
	 *
	 * @param read
	 * @param threshold
	 *            valore di soglia di confidenza
	 */
	public WatsonTranslateToken(JSONObject read, float threshold,String hostname,String port,String collection) {
		knowledge = new ArrayList<>();
		this.hostname=hostname;
		this.collection=collection;
		this.port=port;
		if (DEBUG) {
			System.out.println(read.toString(4));
		}
		if (read.has("result")) {

			JSONArray result = null;

			if (read.get("result") instanceof JSONArray) {
				result = read.getJSONArray("result");
			} else {
				result = new JSONArray();
				result.put(read.getJSONObject("result"));
			}

			for (int i = 0; i < result.length(); i++) {
				JSONObject category = result.getJSONObject(i);
				int salience = category.getInt("salience");
				String category_name = category.getString("name");
				if (category.has("elements")) {
					this.createSemanticPool(category_name, threshold, salience);

					JSONArray elements = null;

					if (category.get("elements") instanceof JSONArray) {
						elements = category.getJSONArray("elements");
					} else {
						elements = new JSONArray();
						elements.put(category.getJSONObject("elements"));
					}

					for (int j = 0; j < elements.length(); j++) {

						JSONObject element = elements.getJSONObject(j);
						String element_name = element.getString("name");
						this.addSemanticElement(category_name, element_name);

						if (element.has("examples")) {

							JSONArray examples = null;

							if (element.get("examples") instanceof JSONArray) {
								examples = element.getJSONArray("examples");
							} else {
								examples = new JSONArray();
								examples.put(element.getString("examples"));
							}

							for (int k = 0; k < examples.length(); k++) {
								String example = examples.getString(k);
								this.addExample(category_name, element_name, example);
							}
						}
					}
				}
			}
		}

	}

	/**
	 * Metodo che si occupa di creare un nuovo SemanticPool, una nuova categoria
	 * semantica
	 *
	 * @param name
	 *            Nome della categoria semantica es. INTENT
	 * @param threshold
	 *            valore di soglia sulla confidenza della particolare categoria
	 *            semantica
	 * @return esito creazione
	 */
	public boolean createSemanticPool(String name, float threshold, int salience) {
		boolean result = false;
		SemanticPool pool = new SemanticPool(name, threshold, salience);
		result = knowledge.add(pool);
		Collections.sort(knowledge);
		return result;
	}

	/**
	 * Metodo che si occupa di creare un nuovo SemanticElement al interno di un
	 * SemanticPool
	 *
	 * @param pool_name
	 *            Nome categoria semantica (es. INTENT)
	 * @param element_name
	 *            Nome elemento semantico (es. Creare)
	 * @return esito creazione
	 */
	public boolean addSemanticElement(String pool_name, String element_name) {
		boolean result = false;
		for (SemanticPool pool : knowledge) {
			if (pool.getCategory().equals(pool_name)) {
				result = pool.createSemanticElement(element_name);
				break;
			}
		}
		return result;
	}

	/**
	 * Metodo che permette di aggiungere un esempio ad un SemanticElement già
	 * creato
	 *
	 * @param pool_name
	 *            nome categoria semantica (Es. INTENT)
	 * @param element_name
	 *            nome elemento semantico (Es. Creare)
	 * @param example
	 *            frase d'esempio (Es. vorrei creare)
	 * @return esito creazione
	 */
	public boolean addExample(String pool_name, String element_name, String example) {
		boolean result = false;
		for (SemanticPool pool : knowledge) {
			if (pool.getCategory().equals(pool_name)) {
				result = pool.addExample(element_name, example);
				break;
			}
		}
		return result;
	}

	public ArrayList<Token> parse(String input) throws IOException {
		// Memorizzo l'input originale che verrà modificato dai passi successivi
		input = input.replaceAll("€", "euro");
		String originalInput = input;
		if (DEBUG) {
			log.log(Level.INFO, "PRIMA DEL LEMMING: " + input);
		}
		ArrayList<Token> result = new ArrayList<Token>();
		// X ogni captegoria semantica
		JSONArray analysis=WatsonRealTimeNLP.analysis(input,hostname,port,collection);
		for (SemanticPool category : knowledge) {

			// Estraggo il nome della categoria semantica
			String category_name = category.getCategory();
			
			for(int i=0;i<analysis.length();i++){
				JSONObject obj=analysis.getJSONObject(i);
				JSONArray path=obj.getJSONArray("path");
				if(path.length()==1){
					String cat_analysis=path.getString(0);
					if(cat_analysis.equals(category_name)){
						JSONObject element=new JSONObject();
						int begin=obj.getInt("begin");
						int end=obj.getInt("end");
						element.accumulate("category", category_name);
						element.accumulate("name", obj.getString("keyword"));
						element.accumulate("token", input.substring(begin, end));
						Token token=new Token(begin,end ,element.toString() , 1);
						result.add(token);
					}
				}
			}

			if (DEBUG) {
				System.out.println("Category: " + category_name);
			}

			
					
		}

		Collections.sort(result);

		result = refine(result);

		return result;
	}

	/**
	 * Metodo di raffinamento che lavora su i token individuati, esimina i token
	 * inclusi in altri
	 *
	 * @param list
	 *            lista di Token
	 * @return Lista aggiornata
	 */
	private ArrayList<Token> refine(ArrayList<Token> list) {
		if (DEBUG) {
			System.out.println("REFINE");
		}

		ArrayList<Token> result = new ArrayList<>();

		boolean add = true;

		for (int i = 0; i < list.size() - 1; i++) {

			Token first = list.get(i);
			Token second = list.get(i + 1);

			if (DEBUG) {
				System.out.println("FIRST: " + first);
				System.out.println("SECOND: " + second);
			}

			if (add) {
				if (DEBUG) {
					System.out.println("first add in result");
				}
				result.add(first);
			}
			if (DEBUG) {
				System.out.println("-----------------------------------------");
			}

				if (second.getStart() > first.getStart() && second.getEnd() <= first.getEnd()
					&& second.getScore() <= first.getScore()) {
				add = false;
			} else if (second.getStart() >= first.getStart() && second.getEnd() < first.getEnd()
					&& second.getScore() <= first.getScore()) {
				add = false;
			} else {
				add = true;
			}

			if (first.getStart() == second.getStart() && first.getEnd() < second.getEnd()) {
				if (DEBUG) {
					System.out.println("remove first sovrapposizione parziale");
				}
				result.remove(first);
			} else if (first.getStart() < second.getStart() && first.getEnd() > second.getEnd()
					&& second.getScore() > first.getScore()) {
				if (DEBUG) {
					System.out.println("remove first score minore");
				}
				result.remove(first);
			}
		}

		if (add && list.size() > 0) {
			if (DEBUG) {
				System.out.println("add last in result");
			}
			result.add(list.get(list.size() - 1));
		}

		return result;
	}


	public String toString() {
		String result = "{\n";
		for (SemanticPool s : knowledge) {
			result += s.toString() + "\n";
		}
		result += "}";
		return result;
	}
}
