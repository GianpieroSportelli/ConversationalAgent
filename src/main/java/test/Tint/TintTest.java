/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Tint;

import configuration.Config;
import edu.stanford.nlp.pipeline.Annotation;
import eu.fbk.dh.tint.runner.TintPipeline;
import eu.fbk.dh.tint.runner.TintRunner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author SPORT
 */
public class TintTest {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);

        String input = "alle 4 di domaini mattina, ora, tra 1 minuto, per 2 ore, mercoledi prossimo, questa settimana, giovedi scorso, la prossima settimana, il prossimo mese ho speso 200 euro";
        String output = "";
        TintPipeline pipeline = new TintPipeline();
        //pipeline.loadDefaultProperties();
        System.out.println(Config.PATH_TINTCONF);
        pipeline.loadPropertiesFromFile(new File(Config.PATH_TINTCONF));
        pipeline.load();
        while (!input.equals("STOP_TINT")) {
            System.out.print(">>");
            input = s.nextLine();
            InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pipeline.run(stream, out, TintRunner.OutputFormat.JSON);

            output = new String(out.toByteArray());
            JSONObject json = new JSONObject(output);
            System.out.println("Complete Output: \n" + output);
            JSONArray tokens = json.getJSONArray("sentences").getJSONObject(0).getJSONArray("tokens");
            String input_lem = "";
            JSONObject last=null;
            for (int i = 0; i < tokens.length(); i++) {
                JSONObject token=tokens.getJSONObject(i);
                String add=token.getString("lemma");
                if(last!=null){
                    if(last.getInt("characterOffsetEnd")!=token.getInt("characterOffsetBegin")){
                        add=" "+add;
                    }
                }
                last=token;
                input_lem+=add;
            }
            //System.out.println("JSON:\n"+tokens.toString(4));
            System.out.println(input_lem);
        }
    }

}
