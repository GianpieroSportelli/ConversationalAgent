package dialogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

import Utils.JSON_utils;
import configuration.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import knowledge.KnowledgePlan;
import knowledge.KnowledgeBase;
import knowledge.Ontology;
import nlGeneration.NLG;
import nlProcess.TintParser;
import patternMatching.Token;
import patternMatching.TranslateToken;
import plan.ActionPlan;
import plan.ResolveAmbiguityPlan;

public class DialogManager {

    Config conf;
    private static final boolean DEBUG = Config.getDialogManagerDebug();
    private static final boolean DEEP_DEBUG = Config.getDialogManagerDeepDebug();

    KnowledgeBase net;
    String last_input;
    List<Token> phrase;

    List<String> hierarchy;

    // String url;
    TranslateToken translater;
    float threshold;
    int epoch = 0;

    Map<String, String> workingMemory;
    Map<String, List<String>> discourseMemory;
    String garbageToken;
    List<String> resultToken;

    private final int memoryLenght = 9;
    TintParser parser;
    public String id_user;

    public DialogManager(KnowledgeBase net, float t, JSONObject read, Config conf) {
        id_user = "DEVELOP";
        this.conf = conf;
        threshold = t;
        this.net = net;
        translater = new TranslateToken(read, t);
        workingMemory = new HashMap<>();
        discourseMemory = new HashMap<>();
        hierarchy = net.getHierarchy();
        if (DEBUG) {
            for (int i = 0; i < hierarchy.size(); i++) {
                System.out.println(i + " " + hierarchy.get(i));
            }
        }
    }

    public DialogManager(KnowledgeBase net, float t, JSONObject read, Config conf, String user_id) {
        this.id_user = user_id;
        this.conf = conf;
        threshold = t;
        this.net = net;
        translater = new TranslateToken(read, t);
        workingMemory = new HashMap<>();
        discourseMemory = new HashMap<>();
        hierarchy = net.getHierarchy();
        if (DEBUG) {
            for (int i = 0; i < hierarchy.size(); i++) {
                System.out.println(i + " " + hierarchy.get(i));
            }
        }
    }

    public List<String> streamMessage(String input) {

        try {
            //workingMemory = new HashMap<>();
            readMemory();
        } catch (IOException ex) {
            resultToken.add((new JSONObject().accumulate("name", "errorReadMemory").accumulate("category", "dialog").accumulate(Ontology.MESSAGE, "errore nella lettura delle memorie da file il contesto verrà perso :( chiedi spiegazioni al mio creatore")).toString());
            Logger.getLogger(DialogManager.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(DialogManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<String> resList = new ArrayList<>();
        input = input.toLowerCase();
        last_input = input;
        phrase = translater.parse(input);
        String semanticMessage = "";
        String relMessage = "";
        resultToken = new ArrayList<>();
        if (DEBUG) {
            for (Token token : phrase) {
                String message = token.getResult();
                semanticMessage += message + "\n";
                message = net.enrich(message);
                token.setResult(message);
                relMessage += message + "\n";
            }

            System.out.println("---------SEMANTIC REPRESENTATION----------");
            System.out.println(semanticMessage);

            System.out.println("-----SEMANTIC REPRESENTATION enrich-----");
            System.out.println(relMessage);
        }

        reasoning(phrase);
        if (DEBUG) {
            System.out.println("-------------MESSAGE RESPONSE--------------");
        }

        NLG nlg = new NLG(net);
        if (resultToken.size() > 0) {
            for (String res : resultToken) {
                if (JSON_utils.isJSONArray(res)) {
                    JSONObject sem = new JSONObject();
                    sem.accumulate("amb", res);
                    ActionPlan plan = new ResolveAmbiguityPlan();
                    List<JSONObject> resultplan = plan.execute(sem, net, conf, epoch, id_user);
                    int i = 0;
                    for (JSONObject r : resultplan) {
                        if (DEBUG) {
                            System.out.println("plan step" + i++ + ")\n" + r.toString(4));
                        }
                        String message = nlg.message(r);
                        resList.add(message);
                    }
                    JSONArray ambiguty = JSON_utils.convertJSONArray(res);
                    String category = ambiguty.getJSONObject(0).getString("category");
                    workingMemory.put(category, res);
                } else {
                    JSONObject sem = new JSONObject(res);
                    String planClass = net.getPlan(sem);
                    if (planClass != null) {
                        System.out.println("c'è un piano!!!");
                        try {
                            ActionPlan plan = KnowledgePlan.plan(planClass);
                            List<JSONObject> resultplan = plan.execute(sem, net, conf, epoch, id_user);
                            int i = 0;
                            for (JSONObject r : resultplan) {
                                if (DEBUG) {
                                    System.out.println("plan step" + i++ + ")\n" + r.toString(4));
                                }
                                String message = nlg.message(r);
                                resList.add(message);
                            }
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                            System.out.println("azione di default");
                            JSONObject querySem = new JSONObject();
                            querySem.accumulate("query", sem);
                            resList.add(nlg.message(querySem));
                        }

                    } else {
                        System.out.println("niente piano");
                        JSONObject querySem = new JSONObject();
                        querySem.accumulate("query", sem);
                        resList.add(nlg.message(querySem));
                    }
                }
            }
        } else {
            resList.add(nlg.message(null));
        }
        if (DEEP_DEBUG) {
            printDiscourseMemory();
        }

        try {
            writeMemory();
        } catch (IOException ex) {
            resultToken.add((new JSONObject().accumulate("name", "errorWriteMemory").accumulate("category", "dialog").accumulate(Ontology.MESSAGE, "errore nella scrittuare delle memorie su file il contesto verrà perso :( chiedi spiegazioni al mio creatore")).toString());
            Logger.getLogger(DialogManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resList;
    }

    public void setUser(String id) {
        id_user = id;
    }

    public String compose(String input) {
        input = input.toLowerCase();
        String output = "";
        last_input = input;
        phrase = translater.parse(input);
        String semanticMessage = "";
        String relMessage = "";
        resultToken = new ArrayList<>();
        for (Token token : phrase) {
            String message = token.getResult();
            semanticMessage += message + "\n";
            message = net.enrich(message);
            token.setResult(message);
            relMessage += message + "\n";
        }

        if (DEBUG) {
            System.out.println("---------SEMANTIC REPRESENTATION----------");
            System.out.println(semanticMessage);
        }
        if (DEBUG) {
            System.out.println("-----SEMANTIC REPRESENTATION enrich-----");
            System.out.println(relMessage);
        }
        reasoning(phrase);
        if (DEBUG) {
            System.out.println("-------------AFTER REASONING--------------");
        }
        for (String s : resultToken) {
            JSONObject obj = net.cleanEnrich(new JSONObject(s));
            if (DEBUG) {
                System.out.println(obj.toString(4));
            }
            output += obj.toString();
        }
        if (DEEP_DEBUG) {
            printDiscourseMemory();
        }
        return output;
    }

    /**
     * Metodo che si occupa di effetuare il ragionamento sul ultimo messaggio
     * considerando anche la conversazione
     *
     * @param list
     * @return
     */
    private void reasoning(List<Token> list) {
        if (discourseMemory.containsKey(Ontology.speechActClassName)) {
            discourseMemory.remove(Ontology.speechActClassName);
        }

        boolean last_in = false;
        boolean last_out = false;

        // per tutti i token del ultimo messaggio
        for (Token token : list) {
            epoch++;
            forgetDiscourseMemory();

            if (DEBUG) {
                System.out.println("TOKEN IN ESAME: " + token);
            }
            // ottengo il messaggio JSON
            String message = token.getResult();

            JSONObject json;
            if (JSON_utils.isJSONArray(message)) {
                // messaggio ambiguo
                JSONArray ambigous_message = JSON_utils.convertJSONArray(message);
                if (DEBUG) {
                    System.out.println("Ambiguos Message:");
                    System.out.println(ambigous_message.toString(4));
                }
                json = ambigous_message.getJSONObject(0);
            } else {
                // converto in JSON
                json = new JSONObject(message);
            }

            // Estraggo la category
            String category = json.getString("category");

            // Pulisco la categoria -> faccio salire tutti i valori più in basso
            // nella gerarchia
            // schiacciandoli in un unico livello
            clean(category);

            boolean rel_in = net.has_rel_in(json);
            boolean rel_out = net.has_rel_out(json);

            if (!(rel_in || rel_out) && !(last_in || last_out)) {
                if (DEBUG) {
                    System.out.println("esporto in result il token poichè non è utile nella memoria");
                }
                resultToken.add(message);
            } else // Se nella memoria di lavoro è presente qualcosa
             if (workingMemory.containsKey(category)) {
                    boolean insert = false;
                    // Prendo ciò che è contenuto nella memoria di lavoro
                    String val_category = workingMemory.get(category);

                    if (DEBUG) {
                        System.out.println("categoria presente workingMemory, verifico compatibilità.");
                        System.out.println(val_category);
                    }

                    // se questo è ambiguo provo a risolvere l'ambiguità
                    // basandomi
                    // solo
                    // sulla conversazione
                    if (JSON_utils.isJSONArray(val_category)) {
                        if (DEBUG) {
                            System.out.println("elemento in memoria ambiguo, provo a risolvere l'ambiguità.");
                        }
                        val_category = resolve_ambiguity(val_category);
                    }

                    if (!insert) {
                        // Converto in JSONArray
                        JSONArray array = JSON_utils.convertJSONArray(val_category);
                        String element = null;

                        JSONArray ambiguos_message = JSON_utils.convertJSONArray(message);
                        // cerco di risolvere l'ambiguità con il nuovo messaggio
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String amb_name = obj.getString("name");

                            for (int j = 0; j < ambiguos_message.length(); j++) {
                                JSONObject single_message = ambiguos_message.getJSONObject(j);
                                String name = single_message.getString("name");
                                // se esiste un elemento nel set di ambiguità con lo
                                // stesso
                                // nome
                                // del messaggio allora esiste una possibilità
                                if (amb_name.equals(name) && !net.isTerminal(obj)) {
                                    // se l'ambiugità è stata inferita allora è
                                    // possibile
                                    // risolvere
                                    // if (!obj.has("token")) {
                                    element = obj.toString();
                                    break;
                                }
                            }
                        }

                        // se il token in esame risolve l'ambiguità
                        if (element != null) {
                            if (DEBUG) {
                                System.out.println(
                                        "elemento in memoria e quello attuale sono compatibili sostituisco in memoria "
                                        + category + ":" + element);
                            }
                            // inserisco l'elemento disambiguato nella memoria
                            workingMemory.put(category, element);
                        } else {
                            if (DEBUG) {
                                System.out.println("elemento in memoria non compatibile con l'attuale. elevo " + category);
                            }
                            // ambiguty non risolta
                            elevate_category(category);
                            if (DEBUG) {
                                System.out.println("inserisco in " + category + " il token " + message);
                            }
                            // lascia lo spazio per il nuovo messaggio
                            workingMemory.put(category, message);
                        }
                    }
                } else {
                    // non presente
                    if (DEBUG) {
                        System.out.println(
                                "nessun conflitto in memoria, inserisco in " + category + " il token " + message);
                    }
                    workingMemory.put(category, message);

                }
            //           aggiungo il token al discorso
            if (!JSON_utils.isJSONArray(message)) {
                addDiscourseMemory(message);
            }

            last_in = rel_in;
            last_out = rel_out;
        }

        // compongo il contenuto della memoria di lavoro
        compose();

    }

    public void readMemory() throws IOException {
        String real_path = Config.PATH_MEMORY + "-" + id_user + "." + Config.MEMORY_EXT;
        File memory = new File(real_path);
        if (memory.exists()) {
            BufferedReader buff = null;
            buff = new BufferedReader(new FileReader(memory));
            String json = "";
            String line = null;
            while ((line = buff.readLine()) != null) {
                json += line;
            }
            JSONObject json_memory = new JSONObject(json);
            if (json_memory.has("wm")) {
                workingMemory = new HashMap<>();
                JSONObject wm = json_memory.getJSONObject("wm");
                for (String key : wm.keySet()) {
                    String val = wm.get(key).toString();
                    workingMemory.put(key, val);
                }
            } else {
                workingMemory = new HashMap<>();
            }

            if (json_memory.has("dm")) {
                discourseMemory = new HashMap<>();
                JSONObject dm = json_memory.getJSONObject("dm");
                for (String key : dm.keySet()) {
                    JSONArray array = JSON_utils.convertJSONArray(dm.get(key).toString());
                    List<String> level = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        level.add(i, array.get(i).toString());
                    }
                    discourseMemory.put(key, level);
                }
            } else {
                discourseMemory = new HashMap<>();
            }

            if (json_memory.has("epoch")) {
                epoch = json_memory.getInt("epoch");
            }

            buff.close();

        } else {
            workingMemory = new HashMap<>();
            discourseMemory = new HashMap<>();
        }
    }

    public void writeMemory() throws IOException {
        String real_path = Config.PATH_MEMORY + "-" + id_user + "." + Config.MEMORY_EXT;
        File memory = new File(real_path);
        if (!memory.exists()) {
            memory.createNewFile();
        }
        JSONObject write_memory = new JSONObject();
        JSONObject wm = new JSONObject();
        JSONObject dm = new JSONObject();

        for (String key : workingMemory.keySet()) {
            wm.accumulate(key, workingMemory.get(key));
        }

        for (String key : discourseMemory.keySet()) {
            List<String> level = discourseMemory.get(key);
            for (String level_element : level) {
                dm.accumulate(key, level_element);
            }
        }

        write_memory.accumulate("wm", wm);
        write_memory.accumulate("dm", dm);
        write_memory.accumulate("epoch", epoch);

        FileOutputStream out = new FileOutputStream(memory);
        out.write(write_memory.toString().getBytes());
        out.close();
    }

    /**
     * cerca di risolvere l'ambiguità analizzando il livello superiore e
     * componendo con un nodo interno
     *
     * @param val_category
     * @return
     */
    private boolean completeWithSuperior(String val_category) {
        // cercare di dare un ordine alle proprietà che vengono ispezionante
        // mediante la memoria di discorso
        if (DEBUG) {
            System.out.println("Cerco di risolvere con l'oggetto di livello superiore!!!");
        }
        JSONArray array = JSON_utils.convertJSONArray(val_category);
        boolean find = false;
        for (int i = 0; i < array.length() && !find; i++) {
            // estraggo l'oggetto ambiguo

            JSONObject obj = array.getJSONObject(i);
            String category = obj.getString("category");
            String name = obj.getString("name");

            if (DEBUG) {
                System.out.println("esamino l'oggetto facente parte dell'ambiguità: " + name);
            }
            if (net.has_rel_in(obj)) {
                if (DEBUG) {
                    System.out.println("l'oggetto è legato a livello superiore");
                }
                // estraggo le relazioni di input
                JSONArray rel_in = net.rel_in(obj);
                // per ogni relazione di input
                for (int j = 0; j < rel_in.length() && !find; j++) {
                    // estraggo la relazione di input

                    JSONObject obj_in = rel_in.getJSONObject(j);
                    String category_in = obj_in.getString("category");
                    String name_in = obj_in.getString("name");
                    if (DEBUG) {
                        System.out.println("Cerco di risolvere cercando l'oggetto " + name_in);
                    }
                    // se in memoria ho la categoria della relazione di input
                    if (workingMemory.containsKey(category_in)) {
                        if (DEBUG) {
                            System.out.println("è presente qualcosa a livello superiore!!! " + category_in);
                        }
                        // estraggo l'elemento dalla memoria
                        String element_in = workingMemory.get(category_in);
                        if (JSON_utils.isJSONArray(element_in)) {
                            if (DEBUG) {
                                System.out.println("il contenuto del livello superiore è ambiguo, provo a risolverlo prima, non so se è la scelta giusta...");
                            }
                            element_in = resolve_ambiguity(element_in);
                            workingMemory.put(category_in, element_in);
                        }

                        if (!JSON_utils.isJSONArray(element_in)) {
                            // se l'elemento non è ambiguo
                            JSONObject real_in = new JSONObject(element_in);
                            // se il nome del oggetto non ambiguo è uguale a
                            // quello della relazione
                            if (DEBUG) {
                                System.out.println("Oggetto del livello superiore!!!\n" + real_in.toString(4));
                            }
                            if (real_in.getString("name").equals(name_in)) {
                                if (DEBUG) {
                                    System.out.println("L'oggetto può risolvere l'ambiguità!!!");
                                }
                                // se l'oggetto del livello superiore contiene
                                // la categoria
                                if (real_in.has(category)) {
                                    if (DEBUG) {
                                        System.out.println("Sfortunatamente contine già " + category + " provo a vedere se è compatibile...");
                                    }
                                    String category_val = real_in.get(category).toString();
                                    JSONArray category_array = JSON_utils.convertJSONArray(category_val);

                                    // per ogni elemento della categoria che
                                    // vogliamo disambiguare contenuta nel
                                    // oggetto di livello superiore
                                    for (int k = 0; k < category_array.length() && !find; k++) {
                                        JSONObject potential = category_array.getJSONObject(k);
                                        if (potential.getString("name").equals(name)) {
                                            // se il nome del elemento contenuto
                                            // è uguale a quello che vogliamo
                                            // inserire
                                            // JSONObject merge =
                                            // compatibile(potential, obj);
                                            JSONObject merge = compatibile(potential, obj);
                                            if (merge != null) {
                                                // category_array.remove(k);
                                                category_array.put(k, merge);
                                                find = true;
                                            }
                                        }
                                    }
                                    if (find) {
                                        if (DEBUG) {
                                            System.out.println("Trovato lo aggiungo!!!");
                                        }
                                        real_in.remove(category);
                                        for (int k = 0; k < category_array.length(); k++) {
                                            real_in.accumulate(category, category_array.get(k));
                                        }
                                        workingMemory.put(category_in, real_in.toString());
                                    }
                                } else {
                                    if (DEBUG) {
                                        System.out.println("non contine " + category + " perfetto!!! lo aggiungo ed esco");
                                    }
                                    find = true;
                                    real_in.accumulate(category, obj);
                                    workingMemory.put(category_in, real_in.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        return find;
    }

    /**
     * Metodo che cerca di liberare una categoria
     *
     * @param category
     */
    private void elevate_category(String category) {
        // se la categoria è occupata
        if (workingMemory.containsKey(category)) {
            if (DEBUG) {
                System.out.println("ho qualcosa in " + category);
            }
            // estrai il valore
            String val = workingMemory.get(category);
            // se c'è ambiguità non risolta cerca di risolverla
            // eventualmente chiedendo
            if (JSON_utils.isJSONArray(val)) {
                if (DEBUG) {
                    System.out.println("cateogria ambigua, provo a risolvere con livello superiore.");
                    System.out.println(val);
                }
                JSONObject one = JSON_utils.convertJSONArray(val).getJSONObject(0);
                if (one.has("rel_in")) {
                    JSONArray rel_in = JSON_utils.convertJSONArray(one.get("rel_in").toString());
                    String sup_category = rel_in.getJSONObject(0).getString("category");
                    if (DEBUG) {
                        System.out.println("AMBIGUITY============================");
                        System.out.println(new JSONArray(val).toString(4));
                        System.out.println("workingMemory: " + workingMemory.containsKey(sup_category));
                        System.out.println(sup_category);
                        if (workingMemory.containsKey(sup_category)) {
                            System.out.println("level: " + workingMemory.get(sup_category));
                        }
                        System.out.println("END--AMBIGUITY============================");
                    }
                    if (!workingMemory.containsKey(sup_category)) {
                        if (DEBUG) {
                            System.out.println("riduco l'ambiguità e la propago");
                        }
                        val = resolve_ambiguity(val);
                        workingMemory.put(sup_category, propagateAmbiguity(val));
                        workingMemory.remove(category);
                        return;
                    } else if (!completeWithSuperior(val)) {
                        if (DEBUG) {
                            System.out.println(
                                    "Ambiguità non risolta. provo a risolvere con discourseMemory se non riesco chiedo al utente.");
                        }
                        elevate_category(sup_category);
                        val = resolve_ambiguity(val);
                        if (!JSON_utils.isJSONArray(val)) {
                            addDiscourseMemory(val);
                        }
                        workingMemory.put(sup_category, propagateAmbiguity(val));
                        workingMemory.remove(category);
                        return;
                    } else {
                        if (DEBUG) {
                            System.out.println("ambiguità risolta, " + category + " elevata.");
                        }
                        workingMemory.remove(category);
                        return;
                    }
                } else {
                    val = resolve_ambiguity(val);
                    if (DEBUG) {
                        System.out.println("La categoria " + category + "non ha livello superiore, la metto nei risultati con eventuale ambiguità!");
                    }
                    if (!JSON_utils.isJSONArray(val)) {
                        addDiscourseMemory(val);
                    }
                    resultToken.add(val);
                    workingMemory.remove(category);
                    return;
                    //addDiscourseMemory(val);
                }
            }

            // ricostruisco il json
            JSONObject obj = new JSONObject(val);
            // prendo il nome
            String name = obj.getString("name");
            if (DEBUG) {
                System.out.println("esamino relazioni di input.");
            }
            // se l'oggetto ammette relazioni di input
            // esistono istanze che lo prendono in input
            if (net.has_rel_in(obj)) {

                // estraggo le relazioni di input arrichendole
                JSONArray in_array = net.rel_in(obj);
                if (DEBUG) {
                    System.out.println("esistono relazioni di input.");
                }
                // Prevede che le relazioni di input siano uguali, tutti con
                // la stessa classe
                String in_category = in_array.getJSONObject(0).getString("category");
                // ASSUNZIONE FORTE

                // flag ritrovamento oggetto che può accettare quello che sto
                // elevando
                boolean find = false;
                // se trovo un oggetto che può prenderlo in input ma risulta
                // incompatibile
                // con l'oggetto che sto elevando, es. contiene già un istanza
                // di uno simile
                boolean hard_break = false;
                // vettore che conterrà il livello successivo, quello in cui
                // stiamo elevando l'ogetto
                JSONArray next_level = new JSONArray();

                // se in memoria di lavoro è prensente la categoria in cui
                // vogliamo elevare l'oggetto
                if (DEBUG) {
                    System.out.println("verifico che in workingMemory ci sia la categoria di livello superiore.");
                }
                if (workingMemory.containsKey(in_category)) {
                    if (DEBUG) {
                        System.out.println("esiste la categoria.");
                    }
                    // ottengo il valore della categoria superiore
                    String sup_val = workingMemory.get(in_category);
                    // ricostruisco il vettore del livello superiore
                    JSONArray sup_array = JSON_utils.convertJSONArray(sup_val);

                    if (DEBUG) {
                        System.out.println("analizzo il livello superiore.");
                    }
                    // verifico tutte le relazioni di input
                    for (int j = 0; j < in_array.length() && !hard_break; j++) {

                        // ottengo una relazione di input
                        JSONObject in_obj = in_array.getJSONObject(j);

                        // prendo il nome della relazione in esame
                        String in_name = in_obj.getString("name");

                        // verifico il livello superiore
                        for (int k = 0; k < sup_array.length() && !hard_break; k++) {

                            // prendo un elemento del livello superiore
                            JSONObject sup_obj = sup_array.getJSONObject(k);

                            // ottengo il nome del oggetto del livello superiore
                            String sup_name = sup_obj.getString("name");

                            // se il nome del oggetto superiore è uguale a
                            // quello della
                            // relazione di input che sto esaminando
                            if (in_name.equals(sup_name)) {
                                if (DEBUG) {
                                    System.out.println(
                                            "esiste un oggetto del livello superiore compatibile con la categoria che sto elevando. "
                                            + sup_name);
                                }
                                // se contine già la categoria in cui dovremmo
                                // mettere
                                // l'oggetto che stiamo elevando
                                if (DEBUG) {
                                    System.out.println(
                                            "analizzo l'oggetto del livello superiore per individuare eventuali incompatibilità.");
                                }
                                boolean onlyOne = net.onlyOne(sup_obj, obj);
                                if (sup_obj.has(category)) {
                                    if (DEBUG) {
                                        System.out.println("l'oggetto contiene la cateogria che vogliamo elevare.");
                                    }
                                    // flag che indica se contiene già un
                                    // oggetto uguale
                                    boolean has = false;
                                    // prendo il valore della categoria in cui
                                    // dovremo mettere
                                    // l'oggetto che stiamo elevando del oggetto
                                    // del livello superiore
                                    // in esame
                                    String val_category = sup_obj.get(category).toString();

                                    // ricostruisco l'array della categoria
                                    // contenuta nel oggetto di livello
                                    // superiore che stiamo considerando
                                    JSONArray array_category = JSON_utils.convertJSONArray(val_category);

                                    // verifico su tutti gli elementi del
                                    // livello superiore
                                    for (int i = 0; i < array_category.length() && !has; i++) {

                                        // ottengo un oggetto contenuto nella
                                        // categoria del oggetto di livello
                                        // superiore in esame
                                        JSONObject obj_category = array_category.getJSONObject(i);

                                        // ottengo il suo nome
                                        String name_category = obj_category.getString("name");

                                        boolean terminal = net.isTerminal(obj_category);
                                        // se il nome del oggetto contenuto nel
                                        // campo categoria del oggetto di di
                                        // livello
                                        // superiore in esame ha stesso nome del
                                        // oggetto che si sta cercando di
                                        // elevare
                                        if (name_category.equals(name) && !terminal) {
                                            if (DEBUG) {
                                                System.out.println(
                                                        "l'oggetto di livello superiore contiene un omonimo dell'oggetto della categoria che vogliamo elevare. che non è terminale");
                                            }
                                            // lo abbiamo, quindi setto il flag
                                            // a true
                                            has = true;
                                            // provo a verificare la
                                            // compatibilità
                                            // il metodo mi restituisce null se
                                            // non sono compatibili
                                            // altrimenti l'unione
                                            if (DEBUG) {
                                                System.out.println("provo a unire i due oggetti: " + name);
                                            }
                                            JSONObject merge = compatibile(obj_category, obj);

                                            // se sono compatibili
                                            if (merge != null) {
                                                if (DEBUG) {
                                                    System.out.println("sono riuscito ad unire i due oggetti.");
                                                }
                                                // inserisco l'unione
                                                array_category.put(i, merge);
                                            } else {
                                                if (DEBUG) {
                                                    System.out
                                                            .println("non è possibile unire i due oggetti. HARD BREAK");
                                                }
                                                // hard_break a true, devo
                                                // elevare il livello superiore
                                                // per fare spazio
                                                hard_break = true;
                                            }
                                            break;
                                        } else if (terminal) {
                                            if (DEBUG) {
                                                System.out.println(
                                                        "l'oggetto che stiamo elevando è terminale quindi bisogna elevare la categoria. HARD BREAK");
                                            }
                                            hard_break = true;
                                            has = true;
                                            break;
                                        }
                                    }

                                    // se l'oggetto del livello superiore
                                    // compatibile con l'oggetto che vogliamo
                                    // elevare
                                    // non ha nel campo categoria un oggetto con
                                    // stesso nome di quello che stiamo elevando
                                    if (!has && !onlyOne) {
                                        if (DEBUG) {
                                            System.out.println(
                                                    "a livello superiore è presente la categoria ma non un istanza del oggetto che vogliamo elevare. lo aggiungo alla categoria "
                                                    + category + " del oggetto " + sup_name);
                                        }
                                        // inserisco l'oggetto che stiamo
                                        // elevando nell'array del oggetto di
                                        // livello supriore
                                        // compatibile
                                        array_category.put(obj);
                                    } else if (!has && onlyOne) {
                                        if (DEBUG) {
                                            System.out.println(
                                                    "a livello superiore è presente la categoria ma non un istanza del oggetto che vogliamo elevare. non posso aggiungerlo alla categoria "
                                                    + category + " del oggetto " + sup_name
                                                    + " perchè l'oggetto può contenere una soloa instanza di "
                                                    + category);
                                        }

                                        hard_break = true;
                                    }

                                    // se non è scattato un hard_break quindi
                                    // non bisogna liberare il livello superiore
                                    if (!hard_break) {
                                        // posso scrivere il vettore degli
                                        // oggetti appartenenti alla categoria
                                        // del oggetto che vogliamo
                                        // elevare al interno del oggetto di
                                        // livello superiore compatibile
                                        if (array_category.length() == 1) {
                                            // se è un solo oggetto scrivo solo
                                            // quello
                                            JSONObject new_obj = array_category.getJSONObject(0);
                                            sup_obj.remove(category);
                                            sup_obj.accumulate(category, new_obj);
                                        } else {
                                            // altrimenti devo scrivere tutti
                                            // gli oggetti contenuti
                                            sup_obj.remove(category);
                                            for (int i = 0; i < array_category.length(); i++) {
                                                JSONObject new_obj = array_category.getJSONObject(i);
                                                sup_obj.accumulate(category, new_obj);
                                            }
                                            if (DEBUG) {
                                                System.out.println("aggiorno l'oggetto di livello superiore.");
                                            }
                                        }

                                        // se invece è scattato un hard_break
                                    } else {
                                        // allora devo elevare il livello
                                        // superiore
                                        if (DEBUG) {
                                            System.out.println(
                                                    "elevo la categoria " + in_category + " a causa del HARD BREAK");
                                        }
                                        elevate_category(in_category);
                                        // e ripovare con il livello che ha
                                        // fatto scattare l'hard_break
                                        if (DEBUG) {
                                            System.out.println("riprovo ad elevare la categoria " + category);
                                        }
                                        elevate_category(category);
                                    }

                                    // se l'oggetto del livello superiore
                                    // compatibile con l'oggetto che vogliamo
                                    // elevare
                                    // non ha un campo category
                                } else {

                                    if (DEBUG) {
                                        System.out.println("l'oggetto di livello superiore non contiene " + category
                                                + " quindi l'aggiungo");
                                    }
                                    // l'oggetto che stiamo elevando è il primo
                                    // e lo possiamo inserire senza problemi
                                    sup_obj.accumulate(category, obj);
                                }
                                // inserirsco l'oggetto del livello superiore
                                // nel array del prossimo livello
                                if (DEBUG) {
                                    System.out.println("aggiorno il livello superiore.");
                                }
                                next_level.put(sup_obj);
                                // settando a true il flag che indica che
                                // abbiamo trovato un oggetto del livello
                                // superiore compatibile
                                find = true;

                                // FINE oggetto superiore compatibile con
                                // oggetto che vogliamo elevare
                            }
                            // FINE ciclo su oggetti del livello superiore
                        }
                        // FINE ciclo sulle relazioni di input con l'oggetto da
                        // elevare
                    }

                    // FINE sezione workingMemory contiene la categoria
                    // superiore
                    // al oggetto che si vuole elevare
                }

                // inizializzo la stringa che conterrà gli elementi del livello
                // superiore
                String add = null;

                // se abbiamo trovato un oggetto di livello superiore
                // compatibile
                if (find) {
                    // aggiungiamo tutto il livello superiore che abbiamo
                    // costruito
                    add = next_level.toString();
                    // se contiene solo un elemento
                    if (next_level.length() == 1) {
                        // aggiungo solo l'oggetto di interesse
                        add = next_level.getJSONObject(0).toString();
                    }
                    // se non abbiamo trovato un oggetto di livello superiore e
                    // non è avvenuto un hard_break
                } else if (!hard_break) {
                    if (DEBUG) {
                        System.out.println(
                                "non ho trovato niente di interessante a livello superiore, lo aggiorno aggiungendo ambiguità derivante da "
                                + category);
                    }
                    // eleviamo tutta la categoria superiore per fare spazio
                    elevate_category(in_category);
                    // inizializzo il vettore di ambiguità per il nuovo livello
                    // superiore
                    JSONArray add_ambiguity = generateAmbiguity(obj);

                    // new JSONArray();
                    //
                    // // aggiungo un oggetto per ogni relazione di input al
                    // // oggetto che vogliamo elevare
                    // for (int j = 0; j < in_array.length(); j++) {
                    // // ottengo la relazione
                    // JSONObject in_obj = in_array.getJSONObject(j);
                    // // inserisco l'oggetto che stiamo elevando
                    // in_obj.accumulate(category, obj);
                    // // metto il nuovo oggetto nel array del livello
                    // // superiore
                    // add_ambiguity.put(in_obj);
                    // }
                    add = add_ambiguity.toString();

                }

                if (!hard_break) {
                    if (DEBUG) {
                        System.out.println("aggiorno le due memorie.");
                    }
                    workingMemory.put(in_category, add);
                    // inserisco l'oggetto che stiamo elevando nella memoria di
                    // discorso
                    addDiscourseMemory(val);
                    // pulisco il livello category
                    workingMemory.remove(category);
                }
                // se non ha relazioni di input
            } else {
                if (DEBUG) {
                    System.out
                            .println("non esistono relazioni di input. la categoria è pronta ad uscire dalla memoria?");
                }
                resultToken.add(exit(obj).toString());
                // pulisco il livello category
                workingMemory.remove(obj.getString("category"));
            }
            if (DEEP_DEBUG) {
                printDiscourseMemory();
                printworkingMemory();
            }
        }

    }

    private JSONArray generateAmbiguity(JSONObject obj) {
        JSONArray add_ambiguity = new JSONArray();
        String category = obj.getString("category");
        JSONArray in_array = net.enrichJSONArray(JSON_utils.convertJSONArray(obj.get("rel_in").toString()));
        // aggiungo un oggetto per ogni relazione di input al
        // oggetto che vogliamo elevare
        for (int j = 0; j < in_array.length(); j++) {
            // ottengo la relazione
            JSONObject in_obj = in_array.getJSONObject(j);
            // inserisco l'oggetto che stiamo elevando
            in_obj.accumulate(category, obj);
            // metto il nuovo oggetto nel array del livello
            // superiore
            add_ambiguity.put(in_obj);
        }
        return add_ambiguity;
    }

    private String propagateAmbiguity(String val) {
        JSONArray array = JSON_utils.convertJSONArray(val);
        JSONArray result = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            JSONArray ambiguity = generateAmbiguity(obj);
            if (result.length() > 0) {
                for (int k = 0; k < ambiguity.length(); k++) {
                    JSONObject obj_amb = ambiguity.getJSONObject(k);
                    boolean find = false;
                    for (int j = 0; j < result.length(); j++) {
                        JSONObject obj_result = result.getJSONObject(j);
                        if (JSON_utils.semanticEquals(obj_result, obj_amb)) {
                            find = true;
                            break;
                        }
                    }
                    if (!find) {
                        result.put(obj_amb);
                    }
                }

            } else {
                result = ambiguity;
            }
        }
        return result.toString();

    }

    private JSONObject compatibile(JSONObject obj_category, JSONObject obj) {
        JSONObject result = null;
        if (net.has_rel_out(obj_category)) {

            JSONArray rel_out = net.rel_out(obj_category);

            for (int i = 0; i < rel_out.length(); i++) {

                JSONObject out = rel_out.getJSONObject(i);

                String category_out = out.getString("category");
                String name_out = out.getString("name");

                boolean terminal = net.isTerminal(out);

                if (obj.has(category_out)) {
                    String val_out = obj.get(category_out).toString();

                    JSONArray array_out = JSON_utils.convertJSONArray(val_out);

                    for (int j = 0; j < array_out.length(); j++) {

                        JSONObject obj_out = array_out.getJSONObject(j);

                        if (obj_out.getString("name").equals(name_out) || terminal) {

                            if (obj_category.has(category_out)) {

                                String val_category_out = obj_category.get(category_out).toString();

                                JSONArray array_cateogry_out = JSON_utils.convertJSONArray(val_category_out);

                                boolean find = false;

                                for (int k = 0; k < array_cateogry_out.length(); k++) {

                                    JSONObject obj_category_out = array_cateogry_out.getJSONObject(k);

                                    if (obj_category_out.getString("name").equals(name_out) && !terminal) {

                                        JSONObject comp = compatibile(obj_category_out, obj_out);

                                        find = true;

                                        if (comp == null) {
                                            return null;
                                        } else {
                                            obj_category.remove(category_out);
                                            obj_category.accumulate(category_out, comp);
                                        }

                                    } else if (/*
												 * obj_category_out.getString(
												 * "name").equals(name_out) &&
                                             */terminal) {
                                        find = true;
                                        return null;
                                    }
                                }

                                if (!find) {
                                    obj_category.accumulate(category_out, obj_out);
                                    break;
                                }
                            } else {
                                obj_category.accumulate(category_out, obj_out);
                            }
                        }
                    }
                }
            }
            result = obj_category;
        }
        return result;
    }

    private String resolve_ambiguity(String val) {
        String result = null;
        JSONArray array = new JSONArray(val);
//        boolean find = false;
//        JSONObject def_find = null;
//        if (DEBUG) {
//            printDiscourseMemory();
//        }
        HashMap<JSONObject, Integer> score = new HashMap<>();
        int max_score = -1;
        for (int i = 0; i < array.length(); i++) {

            JSONObject obj = array.getJSONObject(i);
            int score_value = -1;

            if (DEBUG) {
                System.out.println("uno degli elementi facenti parte l'ambiguità:\n" + obj.toString(4));
            }
            String category = obj.getString("category");
            String name = obj.getString("name");

            if (DEBUG) {
                System.out.println("cerco in discourseMemory se è possibile direttamente risolvere l'ambiguità!!");
            }

            if (net.has_rel_in(obj)) {
                if (DEBUG) {
                    System.out.println("l'elemento ambiguo ha relazioni in input, provo a cercare in workingMemory");
                }
                JSONArray rel_in = JSON_utils.convertJSONArray(obj.get("rel_in").toString());
                for (int j = 0; j < rel_in.length(); j++) {
                    JSONObject sup_obj = rel_in.getJSONObject(j);
                    sup_obj = new JSONObject(net.enrich(sup_obj.toString()));
                    String sup_category = sup_obj.getString("category");
                    String sup_name = sup_obj.getString("name");
                    if (workingMemory.containsKey(sup_category)) {
                        if (DEBUG) {
                            System.out.println("in memoria è presente il livello sup dell'ambiguo: " + sup_category);
                        }
                        String sup_val = workingMemory.get(sup_category);
                        JSONArray sup_level = JSON_utils.convertJSONArray(sup_val);
                        for (int k = 0; k < sup_level.length(); k++) {
                            JSONObject real_sup = sup_level.getJSONObject(k);
                            String real_name = real_sup.getString("name");
                            if (sup_name.equals(real_name)) {
                                if (DEBUG) {
                                    System.out.println("è presente un elemento compatibile con l'ambiguo quindi assegno uno score elevato!!");
                                }
                                score_value = epoch + 1;
                                break;
                            }
                        }
                    } else if (discourseMemory.containsKey(sup_category)) {
                        List<String> supDisc = discourseMemory.get(sup_category);
                        for (String sup_val : supDisc) {
                            JSONArray sup_level = JSON_utils.convertJSONArray(sup_val);
                            for (int k = 0; k < sup_level.length(); k++) {
                                JSONObject real_sup = sup_level.getJSONObject(k);
                                String real_name = real_sup.getString("name");
                                if (sup_name.equals(real_name)) {
                                    if (DEBUG) {
                                        System.out.println("è presente un elemento compatibile con l'ambiguo quindi assegno uno score elevato!!");
                                    }
                                    int epoch = real_sup.getInt("epoch");
                                    if (score_value < epoch) {
                                        score_value = epoch;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (discourseMemory.containsKey(category)) {
                // guardo il discorso
                if (DEBUG) {
                    System.out.println("in discourseMemory è presente la categoria " + category);
                }
                List<String> memory = discourseMemory.get(category);
                for (String element : memory) {
                    if (JSON_utils.isJSONArray(element)) {
                        System.err.println("ambiguità nella memoria di discorso, elevate category method");
                    } else {
                        JSONObject memory_obj = new JSONObject(element);
                        if (DEBUG) {
                            System.out.println("provo con " + memory_obj.toString(4));
                        }
                        String memory_name = memory_obj.getString("name");
                        if (name.equals(memory_name)) {
                            String merge = merge(memory_obj, obj);
                            if (merge != null) {
                                JSONObject merge_obj = new JSONObject(merge);
                                int remeber = 0;
                                if (merge_obj.has("epoch")) {
                                    remeber = merge_obj.getInt("epoch");
                                }
                                if (DEBUG) {
                                    System.out.println("trovato con " + merge_obj.toString(4));
                                }
                                if (score_value < remeber) {
                                    score_value = remeber;
                                }
                                break;

                            } else if (DEBUG) {
                                System.out.println("fallito con " + memory_obj.toString(4));
                            }
                        }
                    }
                }
            } else if (DEBUG) {
                System.out.println("in discourseMemory NON è presente la cateogoria " + category);
            }

            if (!net.isTerminal(obj)) {
                String val_rel_out = obj.get("rel_out").toString();
                JSONArray rel_out = JSON_utils.convertJSONArray(val_rel_out);

                for (int k = 0; k < rel_out.length(); k++) {
                    JSONObject proto_out = rel_out.getJSONObject(k);
                    if (obj.has(proto_out.getString("category"))) {
                        String val_out = obj.get(proto_out.getString("category")).toString();
                        if (JSON_utils.isJSONObject(val_out)) {
                            JSONObject obj_out = new JSONObject(val_out);
                            if (obj_out.has(Ontology._defaultPropertyName)) {
                                JSONObject def_obj = obj_out.getJSONObject(Ontology._defaultPropertyName);
                                String def_name = def_obj.getString("name");
                                String def_category = def_obj.getString("category");
                                if (name.equals(def_name) && category.equals(def_category)) {
                                    if (score_value < 0) {
                                        score_value = 0;
                                    }
                                    if (DEBUG) {
                                        System.out.println("trovato un valore di default, \n" + def_obj.toString(4));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            score.put(obj, score_value);
            if (score_value > max_score) {
                max_score = score_value;
            }
        }
        array = new JSONArray();
        for (JSONObject ambiguty : score.keySet()) {
            int score_value = score.get(ambiguty);
            if (score_value == max_score) {
                array.put(ambiguty);
            }
        }

        //Modifica random ambiguity superior
        if (array.length() > 1 && false) {
            Map<JSONObject, Integer> superior_discourse = new HashMap<>();
            int best_epoch = -1;
            for (int i = 0; i < array.length(); i++) {
                int epoch = -1;
                JSONObject obj = array.getJSONObject(i);
                superior_discourse.put(obj, epoch);
                if (obj.has("rel_in")) {
                    if (DEBUG) {
                        System.out.println("ambiguità ancora non risolta, vedo se il livello superiore in discourseMemory può aiutare");
                    }
                    JSONArray rel_in = JSON_utils.convertJSONArray(obj.get("rel_in").toString());
                    Map<String, String> rel_in_candidate = new HashMap<>();
                    for (int ri = 0; ri < rel_in.length(); ri++) {
                        JSONObject proto_in = rel_in.getJSONObject(ri);
                        String proto_category = proto_in.getString("category");
                        String proto_name = proto_in.getString("name");
                        if (discourseMemory.containsKey(proto_category)) {
                            List<String> discourse_category = discourseMemory.get(proto_category);
                            for (String element : discourse_category) {
                                JSONObject real_in = new JSONObject(element);
                                String real_name = real_in.getString("name");
                                if (real_name.equals(proto_name)) {
                                    if (!real_in.has(obj.getString("category"))) {
                                        String merge = real_in.accumulate(obj.getString("category"), obj).toString();
                                        rel_in_candidate.put(proto_in.toString(), merge);
                                        break;
                                    } else {
                                        String merge = merge(real_in, obj);
                                        if (merge != null) {
                                            rel_in_candidate.put(proto_in.toString(), merge);
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {

                        }
                    }

                    for (String candidate : rel_in_candidate.keySet()) {
                        JSONObject candidate_obj = new JSONObject(rel_in_candidate.get(candidate));
                        int candidate_epoch = candidate_obj.getInt("epoch");
                        if (epoch < candidate_epoch) {
                            epoch = candidate_epoch;
                        }
                    }
                    if (best_epoch < epoch) {
                        best_epoch = epoch;
                    }
                    superior_discourse.put(obj, epoch);
                }
            }

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                int epoch = superior_discourse.get(obj);
                if (DEBUG) {
                    System.out.println("VALUTAZIONE CON LIV SUP=================\n" + obj.toString(4) + "\n SCORE: " + epoch);
                }
                if (epoch < best_epoch) {
                    array.remove(i--);
                }
            }

        }

        if (array.length() == 1) {
            result = array.getJSONObject(0).toString();
//        } else if (ask) {
//            int length = array.length();
//            int idx = ThreadLocalRandom.current().nextInt(0, length);
//            System.out.println("random choice: " + idx);
//            result = array.get(idx).toString();
        } else {
            result = array.toString();
            if (DEBUG) {
                System.out.println("AMBIGUITY not resolved");
                System.out.println(val);
            }
        }
        return result;
    }

    private String merge(JSONObject memory_obj, JSONObject obj) {
        String result = null;
        if (net.has_rel_out(obj)) {
            JSONArray array_out = net.rel_out(obj);
            for (int i = 0; i < array_out.length(); i++) {
                JSONObject obj_out = array_out.getJSONObject(i);
                obj_out = new JSONObject(net.enrich(obj_out.toString()));
                // indica se l'oggetto è un terminale, cioè un nodo foglia che
                // non può essere duplicato nella categoria
                // un solo valore, un solo numero, ecc.
                boolean terminal = !obj_out.has("rel_out");
                String category_out = obj_out.getString("category");
                String name_out = obj_out.getString("name");
                boolean onlyOne = net.onlyOne(obj, obj_out);

                if (obj.has(category_out) && memory_obj.has(category_out) && !terminal && !onlyOne) {

                    String obj_cat = obj.get(category_out).toString();
                    String memo_cat = memory_obj.get(category_out).toString();

                    boolean find = false;
                    JSONArray array_cat = JSON_utils.convertJSONArray(obj_cat);
                    JSONArray memo_array_cat = JSON_utils.convertJSONArray(memo_cat);

                    for (int j = 0; j < array_cat.length() && !find; j++) {
                        JSONObject val_cat = array_cat.getJSONObject(j);
                        String val_name = val_cat.getString("name");

                        if (val_name.equals(name_out)) {
                            if (net.incomplete(val_cat)) {
                                memo_array_cat = net.remove_incomplete(memo_array_cat);
                            }
                            boolean addElement = true;
                            for (int k = 0; k < memo_array_cat.length(); k++) {
                                JSONObject memo_val_cat = memo_array_cat.getJSONObject(k);
                                String memo_val_name = memo_val_cat.getString("name");
                                if (memo_val_name.equals(val_name)) {
                                    memo_array_cat.remove(k);
                                    memo_array_cat.put(val_cat);
                                    find = true;
                                    addElement = false;
                                    break;
                                }
                            }

                            if (addElement) {
                                memo_array_cat.put(val_cat);
                            }
                        }
                    }

                    Object value = null;
                    if (memo_array_cat.length() == 1) {
                        value = memo_array_cat.get(0);
                    } else {
                        value = memo_array_cat;
                    }
                    memory_obj.put(category_out, value);

                    if (find) {
                        break;
                    }
                } else if (obj.has(category_out) && memory_obj.has(category_out) && (terminal || onlyOne)) {
                    memory_obj.remove(category_out);
                    memory_obj.accumulate(category_out, obj.get(category_out));
                    break;

                } else if (obj.has(category_out)) {
                    String obj_cat = obj.get(category_out).toString();

                    JSONArray array_cat = JSON_utils.convertJSONArray(obj_cat);

                    for (int j = 0; j < array_cat.length(); j++) {
                        JSONObject val_cat = array_cat.getJSONObject(j);
                        String val_name = val_cat.getString("name");

                        if (val_name.equals(name_out)) {
                            memory_obj.accumulate(category_out, val_cat);
                            break;
                        }
                    }
                }
            }
            result = memory_obj.toString();
        }
        return result;
    }

    private void addDiscourseMemory(String message) {
        JSONObject obj = new JSONObject(message);
        String category = obj.getString("category");
        obj.put("epoch", epoch);
        JSONObject sa = null;

        if (!discourseMemory.containsKey(category)) {
            List<String> list = new ArrayList<>();
            discourseMemory.put(category, list);
        }

        String sActClass = Ontology.speechActClassName;
        if (category.equals(sActClass)) {
            if (DEBUG) {
                System.out.println(
                        "Ho ricevuto in addDiscourseMemory uno SpeechAct, provo a modificare gli elementi presenti nei riaultati\n" + obj.toString());
            }
            List<String> element = net.getInfluence(obj);
            if (element != null) {
                if (DEBUG) {
                    System.out.println("Influenza dello speechact");
                    for (String e : element) {
                        System.out.println(e);
                    }
                }
                for (int i = 0; i < resultToken.size(); i++) {
                    String r = resultToken.get(i);
                    JSONObject result_obj = new JSONObject(r);
                    JSONObject proj_r = new JSONObject();
                    proj_r.accumulate("category", result_obj.getString("category"));
                    proj_r.accumulate("name", result_obj.getString("name"));

                    if (element.contains(proj_r.toString())) {
                        if (DEBUG) {
                            System.out.println("trovato un candidato..");
                        }
                        if (!result_obj.has(Ontology.speechActClassName)) {
                            result_obj.accumulate(Ontology.speechActClassName, obj);
                            resultToken.remove(i);
                            resultToken.add(i, result_obj.toString());
                        }
                    }
                }
            }
        }
        List<String> list = discourseMemory.get(category);
        list.add(0, obj.toString());
    }

    private void compose() {
        // compose last element
        List<String> elementClean = new ArrayList<>();
        for (int i = hierarchy.size() - 1; i >= 0; i--) {
            String category = hierarchy.get(i);
            clean(category);
            if (workingMemory.containsKey(category)) {
                elevate_category(category);
            }
            elementClean.add(0, category);
        }
        Set<String> keySet = new HashSet<>(workingMemory.keySet());
        for (String key : keySet) {
            if (DEBUG) {
                System.out.println("element " + key + " in workingMemory: " + workingMemory.get(key));
            }
            if (!elementClean.contains(key)) {
                if (workingMemory.containsKey(key)) {
                    elevate_category(key);
                }
            }
        }
    }

    private void clean(String category) {
        if (hierarchy.contains(category)) {
            int index = hierarchy.indexOf(category);
            for (int i = hierarchy.size() - 1; i > index; i--) {
                String last = hierarchy.get(i);
                elevate_category(last);
            }
        }
    }

    private void printDiscourseMemory() {
        System.out.println("------------------------------------------------------------------");
        System.out.println("|                        DISCOURSE MEMORY                        |");
        System.out.println("------------------------------------------------------------------");
        for (String key : discourseMemory.keySet()) {
            List<String> entry = discourseMemory.get(key);
            System.out.println("----------------------KEY:" + key + "----------------------");
            for (String e : entry) {
                JSONObject o = new JSONObject(e);
                System.out.println(o.toString(1));
            }
            System.out.println("--------------------END KEY:" + key + "--------------------");
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("|                      END DISCOURSE MEMORY                      |");
        System.out.println("------------------------------------------------------------------");
    }

    private void printworkingMemory() {
        System.out.println("------------------------------------------------------------------");
        System.out.println("|                           WORK MEMORY                          |");
        System.out.println("------------------------------------------------------------------");
        for (String key : workingMemory.keySet()) {
            String entry = workingMemory.get(key);
            System.out.println("----------------------KEY:" + key + "----------------------");
            if (JSON_utils.isJSONObject(entry)) {
                JSONObject o = new JSONObject(entry);
                System.out.println(o.toString(1));
            } else {
                JSONArray a = JSON_utils.convertJSONArray(entry);
                System.out.println(a.toString(1));
            }
            System.out.println("--------------------END KEY:" + key + "--------------------");
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("|                        END WORK MEMORY                         |");
        System.out.println("------------------------------------------------------------------");

    }

    public JSONObject getWorkingMemory() {
        return new JSONObject(workingMemory);
    }

    public JSONObject getDiscorseMemory() {
        return new JSONObject(discourseMemory);
    }

    public void setWorkingMemory(JSONObject wm) {
        workingMemory = new HashMap<>();
        for (String key : wm.keySet()) {
            workingMemory.put(key, wm.get(key).toString());
        }
    }

    public void setDiscorseMemory(JSONObject dm) {
        discourseMemory = new HashMap<>();
        for (String key : dm.keySet()) {
            List<String> list = new ArrayList<>();
            JSONArray array = JSON_utils.convertJSONArray(dm.get(key).toString());
            for (int i = 0; i < array.length(); i++) {
                list.add(array.get(i).toString());
            }
            discourseMemory.put(key, list);
        }
    }

    public JSONObject exit(JSONObject obj) {
        // è pronto per uscire
        if (discourseMemory.containsKey(Ontology.speechActClassName)) {
            if (DEBUG) {
                System.out.println("ho quancosa in DiscourseMemory per SpeechAct");
            }
            List<String> speechMemory = discourseMemory.get(Ontology.speechActClassName);
            for (String sa : speechMemory) {
                if (DEBUG) {
                    System.out.println("esamino lo speechAct:\n" + sa);
                }
                JSONObject saObj = new JSONObject(sa);
                if (saObj.getInt("epoch") < epoch) {
                    List<String> influence = net.getInfluence(saObj);
                    JSONObject cand = new JSONObject().accumulate("category", obj.getString("category")).accumulate("name", obj.getString("name"));

                    if (influence.contains(cand.toString())) {
                        if (!obj.has(Ontology.speechActClassName) || obj.getJSONObject(Ontology.speechActClassName).getInt("epoch") < epoch) {
                            obj.put(Ontology.speechActClassName, saObj);
                        }
                    }
                    break;
                }
            }
        }
        // lo aggiungo alla memoria di discorso
        addDiscourseMemory(obj.toString());

        return obj;

    }

    private void forgetDiscourseMemory() {
        for (String category : discourseMemory.keySet()) {
            List<String> list = discourseMemory.get(category);
            for (int z = list.size() - 1; z >= 0; z--) {
                JSONObject memory = new JSONObject(list.get(z));
                int memory_ep = memory.getInt("epoch");
                if (epoch - memory_ep > memoryLenght) {
                    list.remove(z);
                } else {
                    break;
                }
            }
        }
    }

}
