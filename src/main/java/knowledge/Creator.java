package knowledge;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import configuration.Config;

public class Creator {

    public static final String url = Config.getPathSemanticNet();
    public static final String format = null;

    public static void main(String[] args) {

        ArrayList<Node> list = new ArrayList<>();

        String[] exGreetings = {"ciao", "buongiorno", "buonasera", "buonanotte", "i miei ossequi", "buondì",
            "buondi", "ehi", "welà","wela","we we", "hi", "salve", "compari", "hola", "gutentag", "trmo", "trmò", "trimone", "buoneos dias", "buena noche", "scemo", "caro", "buon pomeriggio"};
        String greetings_name = "greetings";

        String message_not_greetings = "Hola";
        String message_null = "";

        Node greetings = new Node(exGreetings, message_not_greetings, message_null, message_null, greetings_name,
                Vocabulary.dialogClass);
        list.add(greetings);

        String[] excreator_info = {"chi essere il tuo creatore", "tuo creatore", "chi ti avere fare",
            "chi ti avere creare"};
        String creator_info_name = "creator_info";

        String message_not_creator_info = "Sono stato creato da Gianpiero Sportelli.";

        Node creator_info = new Node(excreator_info, message_not_creator_info, message_null, message_null,
                creator_info_name, Vocabulary.dialogClass);
        list.add(creator_info);

        String[] exname_info = {"come ti chiamare", "come ti potere chiamare", "quale essere il tuo nome",
            "tu essere", "chi essere"};
        String name_info_name = "name_info";

        String message_not_name_info = "mi chiamo 1+1=3. sono timido non farmi altre domande...";

        Node name_info = new Node(exname_info, message_not_name_info, message_null, message_null, name_info_name,
                Vocabulary.dialogClass);
        list.add(name_info);

        String[] exthanks = {"grazie", "grazia"};
        String thanks_name = "thanks";

        String message_thanks = "you're welcome!!";

        Node thanks = new Node(exthanks, message_thanks, message_null, message_null, thanks_name,
                Vocabulary.dialogClass);
        list.add(thanks);

        String[] exwell = {"come stare","come andare"};
        String well_name = "well";

        String message_well = "bene bene, sto cercando di capire come parlate voi umani...";

        Node well = new Node(exwell, message_well, message_null, message_null, well_name,
                Vocabulary.dialogClass);
        list.add(well);

        String[] exwell_done = {};
        String well_done_name = "well_done";

        String message_well_done = "tu?";

        Node well_done = new Node(exwell_done, message_well_done, message_null, message_null, well_done_name,
                Vocabulary.dialogClass);
        list.add(well_done);

        String[] exwell_response = {"bene grazie", "bene", "bene grazia", " io pure", "io anche", "andare be", "andare bene"};
        String well_response_name = "well_response";

        String message_well_response = "grande! allora continuiamo :)";

        Node well_response = new Node(exwell_response, message_well_response, message_null, message_null, well_response_name,
                Vocabulary.dialogClass);
        list.add(well_response);

        String[] exok = {"ok", "va bene", "perfetto", "accordo"};
        String ok_name = "okNode";

        String message_ok = ":)";

        Node ok = new Node(exok, message_ok, ok_name,
                Vocabulary.dialogClass);
        list.add(ok);

        String[] exaddio = {"addio", "a mai più"};
        String addio_name = "addioNode";

        String message_addio = "addio per sempre...";

        Node addio = new Node(exaddio, message_addio, addio_name,
                Vocabulary.dialogClass);
        list.add(addio);

        String[] exscongiuro = {"non te la prendere", "no dai", "scherzare"};
        String scongiuro_name = "scongiuroNode";

        String message_scongiuro = "amici come prima :)";

        Node scongiuro = new Node(exscongiuro, message_scongiuro, scongiuro_name,
                Vocabulary.dialogClass);
        list.add(scongiuro);

        String[] exname_name = {};
        String name_name_question = "name_question";

        String message_not_name_question = "come posso chiamarti?";

        Node name_name = new Node(exname_name, message_not_name_question, message_null, message_null,
                name_name_question, Vocabulary.dialogClass);
        list.add(name_name);

        String nameRND = "registerNotDomain";

        String message_RND = "no so cosa registare, puo ripeterlo?";

        Node rdn = new Node(message_RND, nameRND, Vocabulary.dialogClass);
        list.add(rdn);

        String nameRNB = "registerNotBill";

        String message_RNB = "non è completa, dimmi l'importo in formato numerico. mi sto allenando per diventare impeccabile :)";

        Node rdb = new Node(message_RNB, nameRNB, Vocabulary.dialogClass);
        list.add(rdb);

        String nameROK = "registerOK";

        String message_ROK = "ho preso nota: ";

        Node rok = new Node(message_ROK, nameROK, Vocabulary.dialogClass);
        list.add(rok);

        String nameerrorReg = "errorReg";

        String message_errorReg = "ops... errre nel piano di contabilità. guarda il log sul Raspberry";

        Node errorReg = new Node(message_errorReg, nameerrorReg, Vocabulary.dialogClass);
        list.add(errorReg);

        String nameQtSpesa = "qtRegSpesa";

        String message_QtSpesa = "l'ammontare della";

        Node QtSpesa = new Node(message_QtSpesa, nameQtSpesa, Vocabulary.dialogClass);
        list.add(QtSpesa);

        String nameQtBalance = "qtRegBalance";

        String message_QtBalance = "il";

        Node QtBalance = new Node(message_QtBalance, nameQtBalance, Vocabulary.dialogClass);
        list.add(QtBalance);

        String namehowMuch = "howMuch";

        String message_howMuch = "quanti euri? scrivilo a numeri pls";

        Node howMuch = new Node(message_howMuch, namehowMuch, Vocabulary.dialogClass);
        list.add(howMuch);

        String nameqtgain = "qtRegGain";

        String message_qtgain = "l'";

        Node qtgain = new Node(message_qtgain, nameqtgain, Vocabulary.dialogClass);
        list.add(qtgain);

        String namenoShop = "regNoShop";

        String message_noShop = "non hai speso nulla ;)";

        Node noShop = new Node(message_noShop, namenoShop, Vocabulary.dialogClass);
        list.add(noShop);

        String namenoGain = "regNoGain";

        String message_noGain = "non ci sono entrate";

        Node noGain = new Node(message_noGain, namenoGain, Vocabulary.dialogClass);
        list.add(noGain);

        String namehowReg = "howReg";

        String message_howReg = "registro tutto su un file dentro di me XD";

        Node howReg = new Node(message_howReg, namehowReg, Vocabulary.dialogClass);
        list.add(howReg);

        String namewhyReg = "whyReg";

        String message_whyReg = "è la mia ragione di vita";

        Node whyReg = new Node(message_whyReg, namewhyReg, Vocabulary.dialogClass);
        list.add(whyReg);

        String namewhatReg = "whatReg";

        String message_whatReg = "gestisco il tuo bilancio mediante 3 entità";

        Node whatReg = new Node(message_whatReg, namewhatReg, Vocabulary.dialogClass);
        list.add(whatReg);

        String detail_name = "detail";

        String message_detail = "Ancora non posso gestire le informazioni su";

        Node detail = new Node(message_detail, detail_name, Vocabulary.dialogClass);
        list.add(detail);

        String questionConfirm_name = "questionConfirm";

        String message_questionConfirm = "so che chiedi qualcosa, ci sto lavorando...";

        Node questionConfirm = new Node(message_questionConfirm, questionConfirm_name, Vocabulary.dialogClass);
        list.add(questionConfirm);

        String speechActInsertName = "insert";
        Node speechActInsert = new Node(message_null, speechActInsertName, Vocabulary.speechActClass);
        list.add(speechActInsert);

        String[] speechActQuantifiesEx = {"quanto", "dimmi"};
        String speechActQuantifiesName = "quantifies";
        Node speechActQuantifies = new Node(speechActQuantifiesEx, message_null,
                speechActQuantifiesName, Vocabulary.speechActClass);
        list.add(speechActQuantifies);

        String[] speechActQuestionEx = {"quale", "?"};
        String speechActQuestionName = "question";
        Node speechActQuestion = new Node(speechActQuestionEx, message_null,
                speechActQuestionName, Vocabulary.speechActClass);
        list.add(speechActQuestion);

        String[] speechActHowEx = {"come"};
        String speechActHowName = "how";
        Node speechActHow = new Node(speechActHowEx, message_null,
                speechActHowName, Vocabulary.speechActClass);
        list.add(speechActHow);

        String[] speechActWhyEx = {"perchè", "perché", "perche"};
        String speechActWhyName = "why";
        Node speechActWhy = new Node(speechActWhyEx, message_null,
                speechActWhyName, Vocabulary.speechActClass);
        list.add(speechActWhy);

        String[] speechActWhatEx = {"cosa"};
        String speechActWhatName = "what";
        Node speechActWhat = new Node(speechActWhatEx, message_null,
                speechActWhatName, Vocabulary.speechActClass);
        list.add(speechActWhat);

        String name_plan_info = "plan.InfoPlan";
        Node plan_info = new Node(message_null, name_plan_info, Vocabulary.planClass);
        //name_info.addRel(Vocabulary.plan, plan_info);
        list.add(plan_info);

        String well_plan_info = "plan.WellPlan";
        Node well_plan = new Node(message_null, well_plan_info, Vocabulary.planClass);
        well.addRel(Vocabulary.plan, well_plan);
        list.add(well_plan);

        String[] excapabilities = {"cosa potere fare", "cosa fare", "quale essere la tua funzione"};
        String capabilities_name = "capabilities";

        String message_not_capabilities = "Posso aiutarti a gestire i tuoi conti, prendo nota delle spese e degli accrediti e ti fornisco sempre il bilancio aggiornato";

        Node capabilities = new Node(excapabilities, message_not_capabilities, message_null, message_null,
                capabilities_name, Vocabulary.dialogClass);
        list.add(capabilities);

        String[] exNoAnswer = {};
        String noAnswer_name = "noAnswer";

        String message_noAnswer = "";

        Node noAnswer = new Node(exNoAnswer, message_noAnswer, message_null, message_null, noAnswer_name,
                Vocabulary.noAnswer);
        list.add(noAnswer);

        String[] exregister = {"registrare", "ricordare", "memorizzare", "contabilizzare", "fare di conto", "contabilità","fornire"};
        String register_name = "contability";

        String message_register = "contabilità";
        

        Node register = new Node(exregister, message_register,
                register_name, Vocabulary.actionClass);

        String name_plan_register = "plan.RegisterPlan";
        Node plan_register = new Node(new String[0], message_null, message_null, message_null, name_plan_register,
                Vocabulary.planClass);
        register.addRel(Vocabulary.plan, plan_register);
        register.addRel(Vocabulary.speechAct, speechActQuestion);
        register.addRel(Vocabulary.speechAct, speechActQuantifies);
        register.addRel(Vocabulary.speechAct, speechActHow);
        register.addRel(Vocabulary.speechAct, speechActWhat);
        register.addRel(Vocabulary.speechAct, speechActWhy);
        list.add(plan_register);

        list.add(register);

        String[] exshopping = {"avere spendere","aggiungere"};
        String shopping_name = "shopping";

        String message_shopping = "hai speso";

        Node shopping = new Node(exshopping, message_shopping,
                shopping_name, Vocabulary.actionClass);

        String name_plan_shopping = "plan.ShoppingPlan";
        Node plan_shopping = new Node(message_null, name_plan_shopping,
                Vocabulary.planClass);
        shopping.addRel(Vocabulary.plan, plan_shopping);
        shopping.addRel(Vocabulary.speechAct, speechActQuestion);
        shopping.addRel(Vocabulary.speechAct, speechActQuantifies);
        shopping.addRel(Vocabulary.speechAct, speechActHow);
        shopping.addRel(Vocabulary.speechAct, speechActWhat);
        shopping.addRel(Vocabulary.speechAct, speechActWhy);
        list.add(plan_shopping);

        list.add(shopping);
        
        
        String[] exadding = {"avere avere","avere accreditare","avere incassare","avere guadagnare","avere accumulare","avere ricevere","aggiungere"};
        String adding_name = "adding";

        String message_adding = "hai accreditato";

        Node adding = new Node(exadding, message_adding,
                adding_name, Vocabulary.actionClass);

        String name_plan_adding = "plan.AddingPlan";
        Node plan_adding = new Node(message_null, name_plan_adding,
                Vocabulary.planClass);
        adding.addRel(Vocabulary.plan, plan_adding);
        adding.addRel(Vocabulary.speechAct, speechActQuestion);
        adding.addRel(Vocabulary.speechAct, speechActQuantifies);
        adding.addRel(Vocabulary.speechAct, speechActHow);
        adding.addRel(Vocabulary.speechAct, speechActWhat);
        adding.addRel(Vocabulary.speechAct, speechActWhy);
        list.add(plan_adding);

        list.add(adding);

        String[] exshop = {"spesa", "uscita"};
        String shop_name = "shop";

        String messageShop = "spesa";

        Node shop = new Node(exshop, messageShop, shop_name, Vocabulary.domainClass);
        shopping.addRel(Vocabulary.oneToOne, shop);
        shop.addRel(Vocabulary._default, shopping);
        rok.addInTemplate(shop);
        QtSpesa.addInTemplate(shop);
        list.add(shop);

        String[] exgain = {"guadagno", "entrata","stipendio"};
        String gain_name = "gain";

        String message_gain = "entrata";

        Node gain = new Node(exgain, message_gain, gain_name,
                Vocabulary.domainClass);
        adding.addRel(Vocabulary.oneToOne, gain);
        gain.addRel(Vocabulary._default, adding);
        qtgain.addInTemplate(gain);
        rok.addInTemplate(gain);
        list.add(gain);

        String[] exbalance = {"saldo", "resto", "totale", "bilancio","storico"};
        String balance_name = "balance";

        String messageBalance = "bilancio";

        Node balance = new Node(exbalance, messageBalance, messageBalance, messageBalance, balance_name, Vocabulary.domainClass);
        register.addRel(Vocabulary.oneToOne, balance);
        balance.addRel(Vocabulary._default, register);
        rok.addInTemplate(balance);
        list.add(balance);

        String[] exInteger = {};
        String Integer_name = "integer";
        String message_token = "{value:token}";

        Node integer = new Node(exInteger, message_token, message_token, message_token, Integer_name,
                Vocabulary.numberClass);
        list.add(integer);

        String[] exFloat = {};
        String Float_name = "float";

        Node Float = new Node(exFloat, message_token, message_token, message_token, Float_name, Vocabulary.numberClass);
        list.add(Float);

        String[] exbill = {"prezzo", "costo", "budget", "budjet"};
        String bill_name = "bill";

        String message_bill = "importo";

        Node bill = new Node(exbill, message_bill, message_bill, message_bill, bill_name, Vocabulary.propertyClass);
        shop.addRel(Vocabulary.oneToOne, bill);
        bill.addRel(Vocabulary.oneToStart, integer);
        bill.addRel(Vocabulary.oneToStart, Float);
        gain.addRel(Vocabulary.oneToOne, bill);
        //balance.addRel(Vocabulary.oneToStart, bill);
        //bill.addRel(Vocabulary._default, shop);
        list.add(bill);
        
        
//        String[] exdate = {"data"};
//        String date_name = "date";
//
//        String message_date = "data";
//
//        Node date = new Node(exdate, message_date, message_date, message_date, date_name, Vocabulary.propertyClass);
//        shop.addRel(Vocabulary.oneToOne, date);
//        gain.addRel(Vocabulary.oneToOne, date);
//        balance.addRel(Vocabulary.oneToStart, date);
//        list.add(date);
        
//        String[] exTempExp = {"oggi"};
//        String tempexp_name = "tempexp";
//        String message_tempexp="oggi";
//
//        Node tempexp = new Node(exTempExp, message_tempexp, tempexp_name, Vocabulary.unitClass);
//        date.addRel(Vocabulary.oneToStart, tempexp);
//        list.add(tempexp);
        
        String[] exEuro = {"euro", "€", "\u20ac"};
        String euro_name = "euro";

        Node euro = new Node(exEuro, euro_name, euro_name, euro_name, euro_name, Vocabulary.unitClass);
        bill.addRel(Vocabulary.oneToStart, euro);
        list.add(euro);
        // create an empty Model
        OntModel model = Vocabulary.define();

        for (Node n : list) {
            Resource now = model.createResource("http://eu.reply.it/" + n.name).addProperty(Vocabulary.name, n.name)
                    .addProperty(RDF.type, n.rdf_semClass);
            for (String ex : n.example) {
                now.addProperty(Vocabulary.example, ex);
            }
            now.addProperty(Vocabulary.message_singular, n.singular);
            now.addProperty(Vocabulary.message_plural, n.plural);
            now.addProperty(Vocabulary.message_not, n.not);
            n.addResource(now);
        }

        for (Node n : list) {
            HashMap<Node, Property> rel = n.rel;
            for (Node rel_n : rel.keySet()) {
                Resource res = rel_n.rdf;
                Property prop = rel.get(rel_n);
                n.rdf.addProperty(prop, res);
            }

            RDFNode[] rdf_list = n.getRDFTemplate();
            if (rdf_list.length > 0) {
                n.rdf.addProperty(Vocabulary.template, model.createList(rdf_list));
            }
        }

        // System.out.println("----------------Turtle-------------------");
        // model.write(System.out, "Turtle");
        System.out.println("----------------XML----------------------");
        model.write(System.out, format);
        try {
            model.write(new FileOutputStream(url), format);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
