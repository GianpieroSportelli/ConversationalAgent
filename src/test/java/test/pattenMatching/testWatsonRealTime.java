package test.pattenMatching;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import patternMatching.WatsonRealTimeNLP;

public class testWatsonRealTime {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		WatsonRealTimeNLP.analysis("vorrei 2,6 preventivo");
//		String path="C:/Users/gi.sportelli/Desktop/morph-it_048.csv";
//		File csv=new File(path);
//		BufferedReader buff=new BufferedReader(new FileReader(csv));
//		String line=null;
//		HashMap<String,HashMap<String,ArrayList<String>>> map=new HashMap<>();
//		while((line=buff.readLine())!=null){
//			String[] split=line.split(";");
//			if(split.length==3){
//				if(!map.containsKey(split[1])){
//					map.put(split[1], new HashMap<String,ArrayList<String>>());
//				}
//				
//				HashMap<String,ArrayList<String>> map2=map.get(split[1]);
//				
//				if(!map2.containsKey(split[2])){
//					map2.put(split[2], new ArrayList<String>());
//				}
//				
//				ArrayList<String> list=map2.get(split[2]);
//				
//				if(!list.contains(split[0])){
//					list.add(split[0]);
//				}
//			}
//		}
//		buff.close();
//		
//		path="C:/Users/gi.sportelli/Desktop/morph-it_formatted.csv";
//		csv=new File(path);
//		FileWriter out=new FileWriter(csv);
//		for(String lemma:map.keySet()){
//			HashMap<String, ArrayList<String>> map2=map.get(lemma);
//			for(String pos:map2.keySet()){
//				ArrayList<String> list=map2.get(pos);
//				String surface=null;
//				for(String s:list){
//					if(surface==null){
//						surface=s;
//					}else{
//						surface+="|"+s;
//					}
//				}
//				line=lemma+";"+surface+";"+pos+"\n";
//				out.write(line);
//			}
//		}
//		
//		out.flush();
//		out.close();
	}

}
