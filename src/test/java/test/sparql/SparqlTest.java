package test.sparql;

import org.json.JSONObject;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.RDF;

import configuration.Config;
import knowledge.Reader;
import knowledge.Vocabulary;

public class SparqlTest {
		public static void main(String[] args){
			OntModel model =Reader.readModel(Config.getPathSemanticNet());
			//System.out.println(Vocabulary.example);
			  //String queryString = "SELECT ?rel WHERE{?a ?rel ?b}" ;
			String queryString="SELECT ?a ?b \n WHERE{?a <"+Vocabulary.oneToOne+"> ?b. \n ?b <"+Vocabulary.name+"> \"capsule\"}";
			System.out.println(queryString);
			  Query query = QueryFactory.create(queryString) ;
			  try{
				QueryExecution qexec = QueryExecutionFactory.create(query, model);
			    ResultSet results = qexec.execSelect() ;
			    for ( ; results.hasNext() ; )
			    {
			      QuerySolution soln = results.nextSolution() ;
			      System.out.println(soln);
			      //RDFNode rel = soln.get("rel") ;       // Get a result variable by name.
			      Resource a = soln.getResource("a") ; // Get a result variable - must be a resource
			      Resource b= soln.getResource("b");
			      // Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
			      System.out.println(a+"-->"+b);
			    }
			    JSONObject obj=new JSONObject();
			    obj.put("category", "domain");
			    obj.put("name", "capsule");
			    
			    JSONObject sup=new JSONObject();
			    sup.put("category", "action");
			    sup.put("name", "search");
			    
			    queryString="SELECT ?n ?sup \n WHERE{?obj <"+RDF.type+"> ?obj_class.\n "
			    		+ "?obj_class <"+Vocabulary.name+"> \""+obj.getString("category")+"\".\n "
			    		+ "?obj <"+Vocabulary.name+"> \""+obj.getString("name")+"\".\n "
			    		+ "?sup <"+RDF.type+"> ?sup_class.\n "
			    		+ "?sup_class <"+Vocabulary.name+"> \""+sup.getString("category")+"\".\n "
			    		+ "?sup <"+Vocabulary.name+"> \""+sup.getString("name")+"\".\n "
			    		+ "?sup ?rel ?obj.\n "
			    		+ "?rel <"+Vocabulary.min_card+"> ?n }";
			    
				System.out.println(queryString);
				  query = QueryFactory.create(queryString) ;
				  
					qexec = QueryExecutionFactory.create(query, model);
				    results = qexec.execSelect() ;
				    for ( ; results.hasNext() ; )
				    {
				      QuerySolution soln = results.nextSolution() ;
				      System.out.println(soln);
				      //RDFNode rel = soln.get("rel") ;       // Get a result variable by name.
				      Resource rel = soln.getResource("sup") ; // Get a result variable - must be a resource
				      Literal n= soln.getLiteral("n");
				      int number=Vocabulary.cardinality().get(n.toString());
				      // Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
				      System.out.println(rel+" min cardinality:"+number);
				    }
				    
				    queryString="SELECT ?name ?category \n"
				    		+ "WHERE{?obj <"+RDF.type+"> ?obj_class.\n "
				    		+ "?obj_class <"+Vocabulary.name+"> \""+obj.getString("category")+"\".\n "
				    		+ "?obj <"+Vocabulary.name+"> \""+obj.getString("name")+"\".\n "
				    		+ "?obj <"+Vocabulary._default+"> ?sup.\n "
				    		+ "?sup <"+RDF.type+"> ?sup_class.\n "
				    		+ "?sup_class <"+Vocabulary.name+"> ?category. \n"
				    		+ "?sup <"+Vocabulary.name+"> ?name}";
				    
					System.out.println(queryString);
					  query = QueryFactory.create(queryString) ;
					  
						qexec = QueryExecutionFactory.create(query, model);
					    results = qexec.execSelect() ;
					    for ( ; results.hasNext() ; )
					    {
					      QuerySolution soln = results.nextSolution() ;
					      System.out.println(soln);
					      //RDFNode rel = soln.get("rel") ;       // Get a result variable by name.
					      Literal category = soln.getLiteral("category") ; // Get a result variable - must be a resource
					      Literal name= soln.getLiteral("name");
					      System.out.println("{\"category\":\""+category.getValue()+"\",\"name\":\""+name.getValue()+"\"}");
					    }
			  }catch(Exception e){
				  e.printStackTrace();
			  }
		}
	
}
