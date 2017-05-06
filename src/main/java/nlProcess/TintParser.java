/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlProcess;

import eu.fbk.dh.tint.runner.TintPipeline;
import eu.fbk.dh.tint.runner.TintRunner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class TintParser {

    TintPipeline pipeline;

    public TintParser() {
        pipeline = new TintPipeline();
        try {
            pipeline.loadDefaultProperties();
        } catch (IOException ex) {
            Logger.getLogger(TintParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        pipeline.load();
    }

    public TintParser(String pathConf) throws IOException {
        pipeline = new TintPipeline();
        pipeline.loadPropertiesFromFile(new File(pathConf));
        pipeline.load();
    }

    public JSONArray lexicalAnalysis(String input){
        JSONArray result = null;
        try {
            String output;
            InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pipeline.run(stream, out, TintRunner.OutputFormat.JSON);
            output = new String(out.toByteArray());
            JSONObject json = new JSONObject(output);
            result = json.getJSONArray("sentences");
        } catch (Exception e) {
            System.err.println(e);
        }
        return result;
    }

}
