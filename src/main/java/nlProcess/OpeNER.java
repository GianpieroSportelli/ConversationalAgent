package nlProcess;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import configuration.Config;

public class OpeNER {
	static final boolean DEBUG = Config.getopeNERDebug();

	public static final String[] stop_pos = { "O"/*NUM PREP*/, "Q"/*PROP*/, "C"/*CONG*/, "D"/*DET*/ };

	static String url_ln_identifier = "http://opener.olery.com/language-identifier"; // step:0
	static String url_token = "http://opener.olery.com/tokenizer";// step:1
	static String url_pos_tagger = "http://opener.olery.com/pos-tagger";// step:2
	static String url_tree_tagger = "http://opener.olery.com/tree-tagger";// step:3
	static String url_costituent_parser = "http://opener.olery.com/constituent-parser";// step:4
	static String url_ner = "http://opener.olery.com/ner";// step:5
	static String url_ned = "http://opener.olery.com/ned";// step:6
	static String url_coreference = "http://opener.olery.com/coreference";// step:7
	static String url_polarity_tagger = "http://opener.olery.com/polarity-tagger";// step:8
	static String url_property_tagger = "http://opener.olery.com/property-tagger";// step:9
	static String url_opinion_detector = "http://opener.olery.com/opinion-detector";// step:10
	static String url_opinion_detector_basic = "http://opener.olery.com/opinion-detector-basic";// step:11
	static String url_kaf2json = "http://opener.olery.com/kaf2json";// step:12

	private static HttpURLConnection Connection(int step) throws Exception {

		URL url = null;
		switch (step) {
		case 0:
			url = new URL(url_ln_identifier);
			break;
		case 1:
			url = new URL(url_token);
			break;
		case 2:
			url = new URL(url_pos_tagger);
			break;
		case 3:
			url = new URL(url_tree_tagger);
			break;
		case 4:
			url = new URL(url_costituent_parser);
			break;
		case 5:
			url = new URL(url_ner);
			break;
		case 6:
			url = new URL(url_ned);
			break;
		case 7:
			url = new URL(url_coreference);
			break;
		case 8:
			url = new URL(url_polarity_tagger);
			break;
		case 9:
			url = new URL(url_property_tagger);
			break;
		case 10:
			url = new URL(url_opinion_detector);
			break;
		case 11:
			url = new URL(url_opinion_detector_basic);
			break;
		case 12:
			url = new URL(url_kaf2json);
			break;
		default:
			throw new Exception("step non esistente");
		}

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true);

		conn.setRequestProperty("Content-Type", "multipart/form-data");
		return conn;
	}

	public static String parsing(String text, int[] pipe) throws Exception {
		String result = "";
		for (int step : pipe) {
			Calendar cal = Calendar.getInstance();
			long start = cal.getTimeInMillis();

			result = "";
			String input = "input=" + text + "";
			HttpURLConnection connection = Connection(step);
			OutputStream os = (OutputStream) connection.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line = br.readLine();
			while (line != null) {
				result += line + "\n";
				line = br.readLine();
			}
			connection.disconnect();
			cal = Calendar.getInstance();
			long stop = cal.getTimeInMillis();
			if (DEBUG) {
				System.out.println("STEP " + step + " TIME: " + (stop - start));
				System.out.println("Step " + step + ": " + result);
			}
			text = result;
		}
		return result;
	}

	public static ArrayList<Term> posTagging(String text) throws Exception {
		int[] pipe = { 0, 1, 3 };
		ArrayList<Term> result = new ArrayList<>();

		ArrayList<Node> listWF = new ArrayList<>();
		ArrayList<Node> listTerm = new ArrayList<>();
		ArrayList<Word> WF = new ArrayList<>();
		String output = parsing(text, pipe);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append(output);

		ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		Document document = builder.parse(input);
		Element root = document.getDocumentElement();

		if (DEBUG) {
			String lang = root.getAttribute("xml:lang");
			System.out.println("Lingua: " + lang);
		}

		NodeList list = root.getChildNodes();

		Node textNode = null;

		Node termsNode = null;

		for (int i = 0; i < list.getLength(); i++) {
			Node x = list.item(i);
			String nodeName = x.getNodeName();
			if (nodeName.equals("text")) {
				textNode = x;
			}
			if (nodeName.equals("terms")) {
				termsNode = x;
			}

		}

		if (textNode != null) {
			NodeList childTextNode = textNode.getChildNodes();

			for (int i = 0; i < childTextNode.getLength(); i++) {
				Node x = childTextNode.item(i);
				String nodeName = x.getNodeName();
				if (nodeName.equals("wf")) {
					Node first = x.getFirstChild();
					String value = first.getNodeValue();
					x.setTextContent(value);
					listWF.add(x);
				}
			}
		}

		if (termsNode != null) {
			NodeList childTermsNode = termsNode.getChildNodes();

			for (int i = 0; i < childTermsNode.getLength(); i++) {
				Node x = childTermsNode.item(i);

				String nodeName = x.getNodeName();
				if (nodeName.equals("term")) {
					listTerm.add(x);
				}
			}
		}

		if (DEBUG)
			System.out.println("WF");

		for (Node wf : listWF) {
			Word obj = new Word(wf);
			WF.add(obj);
			if (DEBUG)
				System.out.println(obj);
		}

		if (DEBUG)
			System.out.println("Term");

		for (Node term : listTerm) {
			Term obj = new Term(term);
			result.add(obj);
			String target = obj.getTarget();
			for (Word word : WF) {
				if (word.isIt(target)) {
					obj.setChild(word);
					break;
				}
			}
			if (DEBUG)
				System.out.println(obj);
		}

		return result;
	}

	public static Tree parseInTree(String text) throws Exception {
		int[] pipe = { 0, 1, 3, 4 };
		Tree result = null;
		String output = parsing(text, pipe);
		boolean ok = false;

		ArrayList<Node> listWF = new ArrayList<>();
		ArrayList<Node> listNT = new ArrayList<>();
		ArrayList<Node> listT = new ArrayList<>();
		ArrayList<Node> listEDGE = new ArrayList<>();
		ArrayList<Node> listTerm = new ArrayList<>();

		ArrayList<Word> WF = new ArrayList<>();
		ArrayList<NonTerminal> NT = new ArrayList<>();
		ArrayList<Terminal> T = new ArrayList<>();
		ArrayList<Edge> EDGE = new ArrayList<>();
		ArrayList<Term> TERM = new ArrayList<>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append(output);

		ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
		Document document = builder.parse(input);
		Element root = document.getDocumentElement();

		if (DEBUG) {
			String lang = root.getAttribute("xml:lang");
			System.out.println("Lingua: " + lang);
		}

		NodeList list = root.getChildNodes();

		Node textNode = null;

		Node termsNode = null;

		Node constituencyNode = null;

		for (int i = 0; i < list.getLength(); i++) {
			Node x = list.item(i);
			String nodeName = x.getNodeName();
			if (nodeName.equals("text")) {
				textNode = x;
			}
			if (nodeName.equals("terms")) {
				termsNode = x;
			}
			if (nodeName.equals("constituency")) {
				constituencyNode = x;
			}
		}

		if (textNode != null) {
			NodeList childTextNode = textNode.getChildNodes();

			for (int i = 0; i < childTextNode.getLength(); i++) {
				Node x = childTextNode.item(i);
				String nodeName = x.getNodeName();
				if (nodeName.equals("wf")) {
					Node first = x.getFirstChild();
					String value = first.getNodeValue();
					x.setTextContent(value);
					listWF.add(x);
				}
			}
		}

		if (termsNode != null) {
			NodeList childTermsNode = termsNode.getChildNodes();

			for (int i = 0; i < childTermsNode.getLength(); i++) {
				Node x = childTermsNode.item(i);

				String nodeName = x.getNodeName();
				if (nodeName.equals("term")) {
					listTerm.add(x);
				}
			}
		}

		if (constituencyNode != null) {

			NodeList childConstituencyNode = constituencyNode.getChildNodes();
			Node tree = null;

			for (int i = 0; i < childConstituencyNode.getLength(); i++) {
				Node x = childConstituencyNode.item(i);
				String nodeName = x.getNodeName();
				if (nodeName.equals("tree")) {
					tree = x;
				}
			}

			if (tree != null) {

				NodeList treeElement = tree.getChildNodes();

				for (int i = 0; i < treeElement.getLength(); i++) {
					Node x = treeElement.item(i);
					String nodeName = x.getNodeName();
					if (nodeName.equals("nt")) {
						x.setTextContent("");
						listNT.add(x);
					}
					if (nodeName.equals("t")) {
						listT.add(x);
					}
					if (nodeName.equals("edge")) {
						x.setTextContent("");
						listEDGE.add(x);
					}
				}
				ok = true;
			}
		}
		if (ok) {
			if (DEBUG)
				System.out.println("WF");

			for (Node wf : listWF) {
				Word obj = new Word(wf);
				WF.add(obj);
				if (DEBUG)
					System.out.println(obj);
			}

			if (DEBUG)
				System.out.println("Term");

			for (Node term : listTerm) {
				Term obj = new Term(term);
				TERM.add(obj);
				if (DEBUG)
					System.out.println(obj);
			}

			if (DEBUG)
				System.out.println("Terminal");

			for (Node t : listT) {
				Terminal obj = new Terminal(t);
				T.add(obj);
				if (DEBUG)
					System.out.println(obj);
			}

			if (DEBUG)
				System.out.println("NonTerminal");

			for (Node nt : listNT) {
				NonTerminal obj = new NonTerminal(nt);
				NT.add(obj);
				if (DEBUG)
					System.out.println(obj);
			}

			if (DEBUG)
				System.out.println("Edge");

			for (Node e : listEDGE) {
				Edge obj = new Edge(e);
				EDGE.add(obj);
				if (DEBUG)
					System.out.println(obj);
			}

			result = new Tree(WF, TERM, T, NT, EDGE);
		}
		return result;
	}

}
