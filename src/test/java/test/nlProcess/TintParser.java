package test.nlProcess;


//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//
//import edu.stanford.nlp.pipeline.Annotation;
//import eu.fbk.dh.tint.runner.TintPipeline;
//import eu.fbk.dh.tint.runner.TintRunner;

public class TintParser {
//	public static void main(String[] args) throws IOException{
//		// Initialize the Tint pipeline
//		TintPipeline pipeline = new TintPipeline();
//
//		// Load the default properties
//		// see https://github.com/dhfbk/tint/blob/master/tint-runner/src/main/resources/default-config.properties
//		//pipeline.loadDefaultProperties();
//		pipeline.loadPropertiesFromFile(new File("C:\\Users\\SPORT\\workspace\\ConversationalAgent\\src\\main\\resources\\tintConf.properties"));
//		// Add a custom property
//		// pipeline.setProperty("my_property", "my_value");
//
//		// Load the models
//		pipeline.load();
//		
//		String text = "I topi non avevano nipoti.";
//
//		// Get the original Annotation (Stanford CoreNLP)
//		Annotation stanfordAnnotation = pipeline.runRaw(text);
//
//		// **or**
//
//		// Get the JSON
//		// (optionally getting the original Stanford CoreNLP Annotation as return value)
//		InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
//		Annotation annotation = pipeline.run(stream, System.out, TintRunner.OutputFormat.JSON);
//	}
}
