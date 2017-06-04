package test.pattenMatching;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

import configuration.Config;
import knowledge.KnowledgeBase_Reader;
import patternMatching.Token;
import patternMatching.TranslateToken;

public class TestTranslateToken {

	private static Scanner s;

	public static void main(String[] args) {
		String url = Config.getPathSemanticNet();
		float threshold=0.9f;
		JSONObject read=KnowledgeBase_Reader.readNLU(KnowledgeBase_Reader.readModel(url));
		TranslateToken translater=new TranslateToken(read, threshold);
		System.out.println(translater);
		String input = "";
		s = new Scanner(System.in);
		while (true) {
			System.out.print(">>");
			input = s.nextLine();

			System.out.println("INPUT: " + input);
			input = input.toLowerCase();

			

			ArrayList<Token> result = translater.parse(input);

			String semanticMessage="";

			for (Token tok : result) {
//				System.out.println(tok);
//				String frag = input.substring(tok.getIndex(), tok.getEnd());
//				System.out.println("FRAGMENT: " + frag);
				semanticMessage+=tok.getResult();
			}
			System.out.println("SEMANTIC REPRESENTATION: "+semanticMessage);
		}
	}

}
