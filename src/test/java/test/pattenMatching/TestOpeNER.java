package test.pattenMatching;

import java.util.ArrayList;

import nlProcess.OpeNER;
import nlProcess.Term;
import nlProcess.Tree;

public class TestOpeNER {
	public static void main(String[] args) throws Exception {
		String text = "buongiorno, vorrei una macchina del caffè economica per le capsule.";
		ArrayList<Term> output = null;
		//int[] pipe = { 0, 1, 3, 4 };
		try {
			output = OpeNER.posTagging(text);
			for(Term t:output){
				System.out.println(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(output);
//		
//		Tree treeOutput=OpeNER.parseInTree(text);
//		treeOutput.deepStampTree();
	}	
}
