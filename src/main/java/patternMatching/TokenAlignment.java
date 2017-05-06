package patternMatching;

import org.json.JSONObject;

import configuration.Config;

/**
 * Algoritmo di allineamento basato su programmazione dinamica
 * 
 * @author Gianpiero Sportelli
 *
 */
public class TokenAlignment {
	static final boolean DEBUG = Config.getTokenAllignDebug();
	static final boolean DEEP_DEBUG = Config.getTokenAllignDeepDebug();

	final int match = 2;
	final int mismatch = -2;
	final int open_gap = -2;

	final String str_diag = "di";
	final String str_sx = "sx";
	final String str_up = "up";
	final String str_stop = "sp";
	final String str_start = "ST";
	public static final char blank = '~';
	private static final char fake_blank = '-';

	int[][] V;
	String[][] back_track;

	String pattern;
	String text;
	char[] array_p;
	char[] array_t;

	public TokenAlignment(String text, String pattern) {
		this.text = text;
		if(text.contains(""+blank)){
			text.replaceAll(""+blank, ""+fake_blank);
		}
		
		this.pattern = pattern;
		array_p = pattern.toCharArray();
		array_t = text.toCharArray();
		V = new int[array_p.length + 1][array_t.length + 1];

// Per non pesare un gap al inizio.
//		for (int i = 1; i < V.length; i++) {
//			V[i][0] = open_gap;
//		}
//
//		for (int j = 1; j < V[0].length; j++) {
//			V[0][j] = open_gap;
//		}

		back_track = new String[array_p.length + 1][array_t.length + 1];

		back_track[0][0] = str_start;

		for (int i = 1; i < back_track.length; i++) {
			back_track[i][0] = str_up;
		}

		for (int j = 1; j < V[0].length; j++) {
			back_track[0][j] = str_sx;
		}
	}

	public JSONObject score() {
		JSONObject result = new JSONObject();

		int scoreA = allignmentScore();

		if (DEBUG) {
			System.out.println("Valore score: " + scoreA);
		}

		int start_position = -1;
		int end_position = -2;

		char[][] allignment = allignment();

		char[] textAllign = allignment[0];
		char[] patternAllign = allignment[1];

		float newScore = scoreA;
		float max = maxScore();

		if (DEBUG) {
			System.out.println("max Score: " + max);
		}
		float score = newScore / max;
		if(max==0){
			score=0;
		}
		for (int i = 0; i < patternAllign.length; i++) {
			if (patternAllign[i] != blank) {
				start_position = i;
				break;
			}
		}

		for (int i = textAllign.length - 1; i >= 0; i--) {
			if (patternAllign[i] != blank && textAllign[i] != blank) {
				end_position = i;
				break;
			}
		}

		result.accumulate("text", allignment[0]);
		result.accumulate("pattern", patternAllign);
		result.accumulate("score", score);
		result.accumulate("index", start_position);
		result.accumulate("end", end_position + 1);
		result.accumulate("size",pattern.length());
		result.accumulate("token", pattern);

		if (DEBUG)
			System.out.println("Valore score pesato: " + result);
		return result;
	}

	private float maxScore() {
		float result = 0f;
		int x = 1;
		for (int i = 0; i < pattern.length(); i++) {
			result += x * match;
			x++;
			if (pattern.charAt(i) == ' ') {
				x = 1;
			}
		}
		return result;
	}

	private int allignmentScore() {
		int result = 0;
		int n_d = array_p.length + array_t.length;

		if (DEBUG) {
			System.out.println("Text: " + text);
			System.out.println("Pattern: " + pattern);
			System.out.println("numero diagonali: " + n_d);
		}

		for (int d = 2; d <= n_d; d++) {

			if (DEEP_DEBUG)
				System.out.println("Diagonale: " + d);

			for (int row = Math.max(1, d - array_t.length); row < Math.min(d, array_p.length + 1); row++) {
				int coloumn = d - row;

				if (DEEP_DEBUG)
					System.out.println("Riempio cella: " + row + ";" + coloumn);

				int v1 = V[row - 1][coloumn - 1] + match(array_p[row - 1], array_t[coloumn - 1]);
				if (match(array_p[row - 1], array_t[coloumn - 1]) == match) {
					int back = 1;
					int partial = 0;
					while (back_track[row - back][coloumn - back].equals(str_diag)) {
						if (row - back - 1 >= 0 && array_p[row - back - 1] == ' ') {
							break;
						} else {
							partial++;
						}
						back++;
					}
					v1 += partial * match;
				}

				int v2 = V[row - 1][coloumn];

				if (!back_track[row - 1][coloumn].equals(str_up)) {
					if (back_track[row - 1][coloumn].equals(str_diag) && row - 2 >= 0 /*&& array_p[row - 2] != ' '*/) {
						v2 += open_gap;
					}
				}

				int v3 = V[row][coloumn - 1];
				if (row != V.length - 1 && row != 0) {
					if (!back_track[row][coloumn - 1].equals(str_sx)) {
						if (back_track[row][coloumn - 1].equals(str_diag) && coloumn - 2 >= 0 /*&& array_t[coloumn - 2] != ' '*/) {
							v3 += open_gap;
						}
					}
				}
				int max = Math.max(v1, Math.max(v2, v3));

				if (max == v1) {
					back_track[row][coloumn] = str_diag;
				} else if (max == v2) {
					back_track[row][coloumn] = str_up;
				} else if (max == v3) {
					back_track[row][coloumn] = str_sx;
				}

				V[row][coloumn] = max;
			}
		}
		if (DEBUG) {
			stampV();
			System.out.println("BACK TRACK MATRIX");
			stampBack();
			System.out.println("ALIGNMENT");
			stampAllign();
		}

		result = V[array_p.length][array_t.length];
		return result;
	}

	private int match(char s, char t) {
		if (s == t) {
			return match;
		} else {
			return mismatch;
		}
	}

	private void stampV() {
		System.out.print("        ;");
		for (char x : array_t) {
			System.out.print(x + ";   ");
		}
		System.out.println("");
		for (int i = 0; i < V.length; i++) {
			for (int j = 0; j < V[i].length; j++) {
				if (j == 0 && i != 0) {
					System.out.print(array_p[i - 1]+";");
				} else if (j == 0 && i == 0) {
					System.out.print(" ");
				}
				String campo = "    ";
				String num = "" + V[i][j];
				campo = campo.substring(0, campo.length() - num.length()) + num;
				System.out.print(campo+";");
			}
			System.out.println("");
		}
	}

	private void stampBack() {
		System.out.print(" ;    ;");
		for (char x : array_t) {
			System.out.print("" + x + "  ;");
		}
		System.out.println(";");
		for (int i = 0; i < back_track.length; i++) {
			for (int j = 0; j < back_track[i].length; j++) {
				if (j == 0 && i != 0) {
					System.out.print(array_p[i - 1] + ";");
				} else if (j == 0 && i == 0) {
					System.out.print("  ;");
				}
				System.out.print(back_track[i][j] + " ;");
			}
			System.out.println(";");
		}
	}

	private void stampAllign() {
		char[][] a = allignment();

		for (char x : a[0]) {
			System.out.print(x + " ");
		}
		System.out.println("");

		for (char x : a[1]) {
			System.out.print(x + " ");
		}
		System.out.println("");
	}

	private char[][] allignment() {
		char[][] result = new char[2][0];
		String t = "";
		String s = "";
		String last = back_track[back_track.length - 1][back_track[0].length - 1];
		int index_t = back_track[0].length - 2;
		int index_s = back_track.length - 2;
		boolean stop = false;
		while (!stop) {
			if (last.equals(str_diag)) {
				t = text.charAt(index_t--) + t;
				s = pattern.charAt(index_s--) + s;
			} else if (last.equals(str_sx)) {
				t = text.charAt(index_t--) + t;
				s = blank + s;
			} else if (last.equals(str_up)) {
				t = blank + t;
				s = pattern.charAt(index_s--) + s;
			} else if (last.equals(str_start)) {
				stop = true;
			}
			last = back_track[index_s + 1][index_t + 1];
		}
		result[0] = t.toCharArray();
		result[1] = s.toCharArray();
		return result;
	}
}
