package nlProcess;

import java.util.ArrayList;

import patternMatching.Token;

public class Tree {

	ArrayList<Word> words;
	ArrayList<Term> terms;
	ArrayList<Terminal> terminals;
	ArrayList<NonTerminal> nonTerminals;
	ArrayList<Edge> edges;

	NonTerminal root;

	public Tree(ArrayList<Word> WF, ArrayList<Term> TERM, ArrayList<Terminal> T, ArrayList<NonTerminal> NT,
			ArrayList<Edge> EDGE) {
		words = WF;
		terms = TERM;
		terminals = T;
		nonTerminals = NT;
		edges = EDGE;
		createLink();
		for (NonTerminal nt : nonTerminals) {
			if (nt.father == null) {
				root = nt;
				break;
			}
		}
	}

	private void createLink() {
		for (Edge edge : edges) {
			// System.out.println(edge);
			String from = edge.from;
			String to = edge.to;

			NonTerminal nodeFrom = null;
			NonTerminal nodeTo = null;

			for (NonTerminal nt : nonTerminals) {
				if (nt.isIt(from)) {
					nodeFrom = nt;
					from = "";
				}

				if (nt.isIt(to)) {
					nodeTo = nt;
					to = "";
				}

				if (to.equals("") && from.equals("")) {
					break;
				}
			}

			if (!from.equals("")) {
				for (Terminal t : terminals) {
					if (t.isIt(from)) {
						from = "";
						nodeTo.childs.add(t);
						t.father = nodeTo;
						String termTarget = t.target;
						for (Term term : terms) {
							if (term.isIt(termTarget)) {
								t.child = term;
								term.father = t;
								String wfTarget = term.target;
								for (Word word : words) {
									if (word.isIt(wfTarget)) {
										term.child = word;
										word.father = term;
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			} else {
				nodeFrom.father = nodeTo;
				nodeTo.childs.add(nodeFrom);
			}
		}
	}

	public void deepStampTree() {
		if (root != null) {
			ArrayList<NodeTree> level = root.childs;
			System.out.println("0:" + root);
			for (NodeTree n : level) {
				deepStampSubTree(n, 1);
			}
		}else{
			for(Word word:this.words){
				System.out.println(word);
			}
		}
	}

	private void deepStampSubTree(NodeTree r, int l) {
		String space = "";
		for (int i = 0; i < l; i++) {
			space += "	";
		}

		if (r instanceof NonTerminal) {
			System.out.println(space + "" + l + ":" + r);
			NonTerminal root = (NonTerminal) r;
			ArrayList<NodeTree> level = root.childs;
			for (NodeTree n : level) {
				deepStampSubTree(n, l + 1);
			}
		} else if (r instanceof Terminal) {
			System.out.print(space + "" + l + ":" + r);
			Terminal node = (Terminal) r;
			Term child = node.child;
			Word leaf = child.child;
			System.out.print(";" + child);
			space += " ";
			System.out.println(";" + leaf);
		}
	}
	
	public void prune(ArrayList<Token> semanticElement){
		if(root!=null){
			@SuppressWarnings("unchecked")
			ArrayList<Word> newWords=(ArrayList<Word>) words.clone();
			for(Word word:words){
				for(Token tok:semanticElement){
					if(tok.getResult()!=null && word.offset>=tok.getStart() && word.offset<tok.getEnd()){
						pruneBranch(word);
						newWords.remove(word);
						break;
					}
				}
			}
			words=newWords;
		}
	}
	
	private void pruneBranch(Word word){
		boolean go=true;
		Terminal terminal=word.father.father;
		NonTerminal nt=terminal.father;
		NodeTree node=terminal;
		while(go){
			ArrayList<NodeTree> child=nt.childs;
			child.remove(node);
			if(nt.childs.size()==0){
				node=nt;
				nt=nt.father;
				if(nt==null){
					System.out.println("ROOT DETECT");
					go=false;
				}
			}else{
				go=false;
			}
		}
	}
	
	public ArrayList<Word> getWords(){
		return words;
	}
}
