package test.dialogManager;

import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import configuration.Config;
import dialogManager.DialogManager;
import knowledge.KnowledgeBase_Reader;
import knowledge.KnowledgeBase;

public class ConversationSimulation {
	
	private static Scanner s;
	
	public static void main(String[] args){
		String url = Config.getPathSemanticNet();
		float threshold = 0.9f;
		KnowledgeBase net=new KnowledgeBase(url);
		//net.printModel();
		JSONObject read=KnowledgeBase_Reader.readNLU(net.getModel());
		//System.out.println(read.toString(4));
                Config conf=Config.getInstance();
		DialogManager contex = new DialogManager(net, threshold,read,conf);
		
		String input = "";
		s = new Scanner(System.in);
		
		while (true) {
			System.out.print(">>");
			input = s.nextLine();

//			System.out.println("INPUT: " + input);
			input = input.toLowerCase();
			List<String> output=contex.streamMessage(input);
			for(String x:output){
				System.out.println(x);
			}
		}
	}

}
