package patternMatching;

public class Token implements Comparable<Token>{
	Integer start_index;
	Integer end_index;
	String result;
	double score;
	
	public Token(Integer start,Integer end,String jsonResult,double score){
		start_index=start;
		end_index=end;
		result=jsonResult;
		this.score=score;
	}
	
	public String getResult(){
		return result;
	}
	
	public double getScore(){
		return score;
	}
	
	public void setResult(String value){
		result=value;
	}
	public Integer getStart(){
		return start_index;
	}
	public Integer getEnd(){
		return end_index;
	}
	
	public void setEnd(Integer end){
		this.end_index=end;
	}
	
	public int compareTo(Token o) {
		return start_index.compareTo(o.getStart());
	}
	
	public String toString(){
		return "{start: "+start_index+" end: "+end_index+" result: "+result+"}";
	}

}
