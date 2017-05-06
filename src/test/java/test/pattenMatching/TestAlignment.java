package test.pattenMatching;

import org.json.JSONObject;

import patternMatching.TokenAlignment;



public class TestAlignment {
	
	static char nullChar = TokenAlignment.blank;
	
	
	public static void main(String[] args){
		String text="quanto costa una scatola di lavazza divino?";
		String pattern="in una scatola";
		
		TokenAlignment allign=new TokenAlignment(text, pattern);
		JSONObject result=allign.score();
		System.out.println(result);
		System.out.println(substitution(text, (char[])result.get("pattern"), (char[])result.get("text")));
		}
	
	private static String substitution(String input, char[] pattern, char[] text) {
		String result = input;
		if (text.length != input.length()) {
			System.out.println("esiste un gap nel testo.");
			System.out.println("input lenght: " + input.length());
			System.out.println("text lenght: " + text.length);
			System.out.println("pattern lenght: " + pattern.length);
			for (int i = 0; i < text.length; i++) {
				if (text[i] == nullChar && i<pattern.length) {
					char[] newPattern = new char[pattern.length - 1];
					for (int x = 0; x < pattern.length; x++) {
						if (x < i) {
							newPattern[x] = pattern[x];
						} else if (x > i) {
							newPattern[x - 1] = pattern[x];
						}
					}
					pattern = newPattern;
				}
			}
		}
		for (int i = 0; i < pattern.length && i < input.length(); i++) {
			if (pattern[i] != nullChar) {
				if (i < input.length() - 1) {
					result = result.substring(0, i) + nullChar + result.substring(i + 1, result.length());
				} else {
					result = result.substring(0, i) + nullChar;
				}
			}
		}
		return result;
	}
}
