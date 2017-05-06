/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Tint;

import edu.stanford.nlp.pipeline.Annotation;
import eu.fbk.dh.tint.runner.TintPipeline;
import eu.fbk.dh.tint.runner.TintRunner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class TintTest {

    public static void main(String[] args) throws IOException {
        String input = "ho speso 5 euro?";
        String output="";
        TintPipeline pipeline = new TintPipeline();
        pipeline.loadDefaultProperties();
        pipeline.load();
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        Annotation annotation=pipeline.run(stream, out, TintRunner.OutputFormat.JSON);
        
        output=new String(out.toByteArray());
        JSONObject json=new JSONObject(output);
        System.out.println(output);
        System.out.println("JSON:\n"+json.getJSONArray("sentences").getJSONObject(0).getJSONArray("tokens").toString(4));
    }

}
