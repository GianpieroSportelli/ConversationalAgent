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
	// carattere nullo utilizzato per la sostituzione degli elementi
	// indifividuati nel input
	//char nullChar = TokenAlignment.blank;

	// pos tag che identificano stop word
	// final static String[] stopPos = OpeNER.stop_pos;
	//TintParser parser;

	/**
	 * Costruttore
	 *
	 * @param name
	 */
	public WatsonTranslateToken() {
		knowledge = new ArrayList<>();
//		try {
//			parser = new TintParser(Config.PATH_TINTCONF);
//		} catch (IOException ex) {
//			log.log(Level.SEVERE, "errore nel caricamento delle proprietà del parser, uso quelle di default!!!");
//			log.log(Level.SEVERE, null, ex);
//			parser = new TintParser();
//		}
	}

	/**
	 * Costruttore che estrae categorie, elementi semantici e esempi da un file
	 * RDF
	 *
	 * @param read
	 * @param threshold
	 *            valore di soglia di confidenza
	 */
	public WatsonTranslateToken(JSONObject read, float threshold) {
		knowledge = new ArrayList<>();
//		try {
//			parser = new TintParser(Config.PATH_TINTCONF);
//		} catch (IOException ex) {
//			log.log(Level.SEVERE, "errore nel caricamento delle proprietà del parser, uso quelle di default!!!");
//			log.log(Level.SEVERE, null, ex);
//			parser = new TintParser();
//		}
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

//		try {
//			input = lemming(input);
//			if (DEBUG) {
//				System.out.println("AFTER Lemming: " + input);
//			}
//			originalInput = input;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			System.err.println(e.getMessage());
//		}
		// Inizializzo l'output
//		ArrayList<Token> fragment = fragment(input);
//		ArrayList<Token> number = new ArrayList<>();
//		for (Token t : fragment) {
//			if (t != null) {
//				JSONObject rapp = new JSONObject(t.result);
//				if (rapp.getString("category").equals("number")) {
//					number.add(t);
//				}
//			}
//		}
		ArrayList<Token> result = new ArrayList<Token>();
//		result.addAll(number);
		// X ogni captegoria semantica
		JSONArray analysis=WatsonRealTimeNLP.analysis(input);
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

//			if (second.getStart() == first.getStart() && second.getEnd() == first.getEnd()) {
//				JSONArray newValue = new JSONArray();
//				String value1String = first.getResult();
//				if (value1String.charAt(0) == '[') {
//					newValue = new JSONArray(value1String);
//				} else {
//					JSONObject value1 = new JSONObject(value1String);
//					newValue.put(value1);
//				}
//				JSONObject value2 = new JSONObject(second.getResult());
//				newValue.put(value2);
//				first.setResult(newValue.toString());
//				add = false;
//			} else
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

	/**
	 * Metodo che si occupa di categorizzare come value(text) i frammenti non
	 * ancora categorizzati sfruttando conoscenza linguistica (Morfologia)
	 * fornita da OpeNER
	 *
	 * @param input
	 *            frase originale
	 * @param partial
	 *            elementi individuati
	 * @return
	 */
	/*
	 * private ArrayList<Token> findText(String input, ArrayList<Token> partial)
	 * { if(DEBUG) System.out.println("FIND TEXT"); ArrayList<Token> result =
	 * new ArrayList<>();
	 * 
	 * ArrayList<Term> term = new ArrayList<>();
	 * 
	 * HashMap<Token, ArrayList<Term>> bind = new HashMap<>();
	 * 
	 * try { // Utilizzo opener per effetuare il pos tagging term =
	 * OpeNER.posTagging(input);
	 * 
	 * if (DEBUG) System.out.println("TERM");
	 * 
	 * for (Term t : term) { // Ottengo la parola legata al termine Word word =
	 * t.getChild();
	 * 
	 * if (DEBUG) System.out.println(t + "->" + word); // per ogni token for
	 * (Token tok : partial) { // se il token non è categorizzato e la parola
	 * ricade nel // particolare frammento sottostante il token allora if
	 * (tok.getResult() == null && tok.getStart() <= word.getOffSet() &&
	 * tok.getEnd() > word.getOffSet()) { // Se ho già trovato termini per il
	 * token in esame if(DEBUG){ System.out.println("TOKEN NULL: "+tok); } if
	 * (bind.containsKey(tok)) { ArrayList<Term> termList = bind.get(tok);
	 * termList.add(t); } else { // Altrimenti inizializzo la struttuare dei
	 * termini // del token ArrayList<Term> termList = new ArrayList<>();
	 * termList.add(t); bind.put(tok, termList); } // non ciclo più su i token
	 * ho trovato quello di // interesse break; } } // cambio termine; }
	 * 
	 * // per ogni token che ha categoria null e per cui abbiamo trovato //
	 * termini for (Token tok : bind.keySet()) { // estraggo la lista dei
	 * termini per andare a eliminare dal // testa e dalla coda // le stop word
	 * ArrayList<Term> listTerm = bind.get(tok); boolean go = true;
	 * 
	 * while (go) {
	 * 
	 * go = false; // verifico la testa String pos_start =
	 * listTerm.get(0).getPos(); for (String stop : stopPos) { // se il pos del
	 * termine in esame appartiene alla classe // delle stop word if
	 * (stop.equals(pos_start)) { listTerm.remove(0); go = true; break; } }
	 * 
	 * // se esisto altri termini if (listTerm.size() > 0) { // verifico la coda
	 * String pos_finish = listTerm.get(listTerm.size() - 1).getPos(); for
	 * (String stop : stopPos) { if (stop.equals(pos_finish)) {
	 * listTerm.remove(listTerm.size() - 1); go = true; break; } } }
	 * 
	 * // se la lista è vuota la metto a null if (listTerm.size() == 0) { go =
	 * false; listTerm = null; bind.put(tok, null); } }
	 * 
	 * // se non ho annullato la lista vuol dire che ho indentificato // il
	 * testo if (listTerm != null) { String value = ""; int start = -1; int end
	 * = 0; for (Term t : listTerm) { Word wf = t.getChild(); String word =
	 * wf.getText(); //aggiungo termine con spazio che elimino alla fine del
	 * ciclo value += word + " "; int word_start = wf.getOffSet(); int word_stop
	 * = wf.getEnd()+1;
	 * 
	 * if (start == -1) { start = word_start; } else if (word_start < start) {
	 * start = word_start; }
	 * 
	 * if (word_stop > end) { end = word_stop; } }
	 * 
	 * //Elimino l'ultimo spazio aggiunto value=value.substring(0,
	 * value.length()-1);
	 * 
	 * // elimino eventuali spazi alla fine while
	 * (!Character.isAlphabetic(value.charAt(value.length() - 1)) &&
	 * !Character.isDigit(value.charAt(value.length() - 1))) { value =
	 * value.substring(0, value.length() - 1); end--; }
	 * 
	 * // costruisco il risultato da inserire nel token JSONObject json = new
	 * JSONObject(); json.accumulate("token", value); //N.B cambiare in caso di
	 * classe per descrizione json.accumulate("category", "extracted");
	 * json.accumulate("name", "text");
	 * 
	 * String jsonResult = json.toString();
	 * 
	 * result.add(new Token(start, end, jsonResult,0d)); } }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return result; }
	 */
	/**
	 * Metodo che si occupa di identificare i frammenti del messaggio non
	 * classificati come elementi semantici individua da prima numeri poi
	 * individua gli altri frammenti non categorizzati
	 *
	 * @param input
	 *            dopo la sostituzione delgi elementi semantici individuati
	 * @return lista di token contenti i frammenti non idividuati
	 */
//	private ArrayList<Token> fragment(String input) {
//		// aggiungo un char alla fine per non impazzire con condizioni strane di
//		// terminazione
//
//		input += nullChar;
//
//		ArrayList<Token> result = new ArrayList<>();
//
//		char[] array = input.toCharArray();
//		// Identifico i NUMERI interi o Float
//		String fragment = "";
//		int start = 0;
//		int end = array.length;
//		boolean digit = false;
//		boolean floating = false;
//		String newInput = "";
//
//		for (int index = 0; index < array.length; index++) {
//
//			char now = array[index];
//
//			if (Character.isDigit(now)) {
//
//				fragment += now;
//				now = nullChar;
//				digit = true;
//
//			} else if (digit && !floating && (now == '.' || now == ',') && Character.isDigit(array[index + 1])) {
//
//				fragment += now;
//				floating = true;
//				now = nullChar;
//
//			} else if (digit && floating) {
//
//				end = index;
//				fragment = fragment.replace(',', '.');
//				float value = Float.valueOf(fragment);
//
//				JSONObject token = new JSONObject();
//				token.accumulate("token", value);
//				token.accumulate("category", "number");
//				token.accumulate("name", "float");
//				String jsonResult = token.toString();
//
//				result.add(new Token(start, end, jsonResult, 1d));
//
//				start = index + 1;
//				fragment = "";
//				digit = false;
//				floating = false;
//
//			} else if (digit && !floating) {
//
//				end = index;
//				Integer value = Integer.valueOf(fragment);
//
//				JSONObject token = new JSONObject();
//				token.accumulate("token", value);
//				token.accumulate("category", "number");
//				token.accumulate("name", "integer");
//				String jsonResult = token.toString();
//
//				result.add(new Token(start, end, jsonResult, 1d));
//
//				start = index + 1;
//				fragment = "";
//				digit = false;
//
//			} else {
//				start = index + 1;
//			}
//			newInput += now;
//		}
//
//		// aggiungo al risultato i frammenti contenti testo non classificato
//		// Opener
//		// result.addAll(fragment_text(newInput));
//		return result;
//	}

	/**
	 * Metodo che si occupa di indentificare i frammenti di testo non
	 * classificati
	 *
	 * @param input
	 *            dopo l'individuazione dei numeri e degli elementi semantici
	 * @return lista di token
	 */
	/*
	 * private ArrayList<Token> fragment_text(String input) {
	 * 
	 * ArrayList<Token> result = new ArrayList<>(); char[] array =
	 * input.toCharArray();
	 * 
	 * String t = ""; int lastStart = 0; boolean init = false;
	 * 
	 * for (int index = 0; index < array.length; index++) {
	 * 
	 * if (DEEP_DEBUG) System.out.println("index: " + index + " char: " +
	 * array[index]);
	 * 
	 * if (array[index] != nullChar) {
	 * 
	 * if (Character.isAlphabetic(array[index]) ||
	 * Character.isDigit(array[index])) {
	 * 
	 * if (DEEP_DEBUG) System.out.println("add  init: " + init);
	 * 
	 * t += array[index]; init = true;
	 * 
	 * } else if (init) { if (DEEP_DEBUG)
	 * System.out.println("add  no char init");
	 * 
	 * t += array[index]; } else {
	 * 
	 * if (DEEP_DEBUG) System.out.println("shift start " + index);
	 * 
	 * lastStart = index + 1; }
	 * 
	 * } else if (init) {
	 * 
	 * int end = index;
	 * 
	 * // elimino eventuali spazi alla fine del frammento while
	 * (!Character.isAlphabetic(t.charAt(t.length() - 1)) &&
	 * !Character.isDigit(t.charAt(t.length() - 1))) {
	 * 
	 * t = t.substring(0, t.length() - 1); end--;
	 * 
	 * }
	 * 
	 * if (DEEP_DEBUG) System.out.println("add in list, index: " + lastStart +
	 * " text: " + t);
	 * 
	 * result.add(new Token(lastStart, end, null,1d));
	 * 
	 * lastStart = index + 1; t = ""; init = false; } else { if (DEEP_DEBUG)
	 * System.out.println("shift start " + index);
	 * 
	 * lastStart = index + 1; } } return result; }
	 */
//	private String lemming(String text) throws Exception {
//		String result = "";
//		JSONArray sentences = parser.lexicalAnalysis(text);
//
//		if (sentences != null) {
//			if (DEBUG) {
//				System.out.println("Risultato analisi lessicale: \n" + sentences.toString(4));
//			}
//			for (int i = 0; i < sentences.length(); i++) {
//				String val = sentences.get(i).toString();
//				JSONObject sentence = new JSONObject(val);
//				JSONArray tokens = sentence.getJSONArray("tokens");
//				JSONObject last = null;
//				for (int j = 0; j < tokens.length(); j++) {
//					JSONObject token = tokens.getJSONObject(j);
//					String lemma = token.getString("lemma");
//					if (lemma.charAt(0) == '[') {
//						lemma = token.getString("word");
//					}
//					String add = lemma;
//					if (last != null) {
//						if (last.getInt("characterOffsetEnd") != token.getInt("characterOffsetBegin")) {
//							add = " " + add;
//						}
//					}
//					last = token;
//					result += add;
//				}
//			}
//		}
//
//		return result;
//	}

	/**
	 * Metodo che effettua la sostituzione del pattern nel testo con nullChar
	 *
	 * @param input
	 * @param pattern
	 *            identificato nel testo in forma di char array
	 * @param text
	 *            parzialmente elaborato in forma di char array
	 * @return
	 */
//	private String substitution(String input, JSONObject element) {
//		char[] pattern = (char[]) element.get("pattern");
//		char[] text = (char[]) element.get("text");
//		int start = element.getInt("index");
//		int end = element.getInt("end");
//
//		String result = input;
//
//		if (text.length != result.length()) {
//
//			if (DEEP_DEBUG) {
//				System.out.println("esiste un gap nel testo.");
//				System.out.println("input lenght: " + input.length());
//				System.out.println("text lenght: " + text.length);
//				System.out.println("pattern lenght: " + pattern.length);
//			}
//
//			int diff = Math.abs(text.length - result.length());
//
//			boolean insertion_start = false;
//
//			for (int i = start; i < end; i++) {
//
//				if (text[i] == nullChar && i < pattern.length) {
//
//					insertion_start = (i == start);
//
//					char[] newPattern = new char[pattern.length - 1];
//					for (int x = 0; x < pattern.length; x++) {
//						if (x < i) {
//							newPattern[x] = pattern[x];
//						} else if (x > i) {
//							newPattern[x - 1] = pattern[x];
//						}
//					}
//
//					pattern = newPattern;
//				}
//
//			}
//
//			if (insertion_start) {
//				element.remove("index");
//				element.accumulate("index", start + diff);
//			} else {
//				element.remove("end");
//				element.accumulate("end", end - diff);
//			}
//		}
//
//		for (int i = 0; i < pattern.length && i < result.length(); i++) {
//
//			if (pattern[i] != nullChar) {
//				if (i < input.length() - 1) {
//					result = result.substring(0, i) + nullChar + result.substring(i + 1, result.length());
//				} else {
//					result = result.substring(0, i) + nullChar;
//				}
//			}
//
//		}
//		return result;
//	}
//
//	private String substitutionToken(String input, ArrayList<Token> tokens) {
//		if (DEBUG) {
//			System.out.println("SUB TOKEN");
//			System.out.println("START INPUT: " + input);
//		}
//		for (Token t : tokens) {
//			if (DEBUG) {
//				System.out.println("TOKEN: " + t);
//			}
//			int start = t.getStart();
//			int end = t.getEnd();
//			String nullString = "";
//			for (int i = start; i < end; i++) {
//				nullString += nullChar;
//			}
//			input = input.substring(0, start) + nullString + input.substring(end);
//			if (DEBUG) {
//				System.out.println("INPUT AFTER: " + input);
//			}
//		}
//		return input;
//	}

	public String toString() {
		String result = "{\n";
		for (SemanticPool s : knowledge) {
			result += s.toString() + "\n";
		}
		result += "}";
		return result;
	}
}
