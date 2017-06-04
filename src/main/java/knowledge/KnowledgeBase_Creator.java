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

public class KnowledgeBase_Creator {

    public static final String url = Config.getPathSemanticNet();
    public static final String format = null;

    public static void main(String[] args) {

        ArrayList<Node> list = new ArrayList<>();

        //BEGIN------Intenzioni Sociali
        String[] exGreetings = {"ciao", "buongiorno", "buonasera", "buonanotte", "i miei ossequi", "buondì",
            "buondi", "ehi", "welà", "wela", "we we", "salve", "compari", "hola", "gutentag", "trmo", "trmò", "trimone", "buoneos dias", "buena noche", "scemo", "caro", "buon pomeriggio"};
        String greetings_name = "greetings";

        String message_not_greetings = "Hola";
        String message_null = "";

        Node greetings = new Node(exGreetings, message_not_greetings, message_null, message_null, greetings_name,
                Ontology.dialogClass);
        list.add(greetings);

        String[] excreator_info = {"chi essere il tuo creatore", "tuo creatore", "chi ti avere fare",
            "chi ti avere creare"};
        String creator_info_name = "creator_info";

        String message_not_creator_info = "Sono stato creato da Gianpiero Sportelli.";

        Node creator_info = new Node(excreator_info, message_not_creator_info, message_null, message_null,
                creator_info_name, Ontology.dialogClass);
        list.add(creator_info);

        String[] exname_info = {"come ti chiamare", "come ti potere chiamare", "quale essere il tuo nome",
            "tu essere", "chi essere"};
        String name_info_name = "name_info";

        String message_not_name_info = "mi chiamo 1+1=3. sono timido non farmi altre domande...";

        Node name_info = new Node(exname_info, message_not_name_info, message_null, message_null, name_info_name,
                Ontology.dialogClass);
        list.add(name_info);

        String[] exthanks = {"grazie", "grazia"};
        String thanks_name = "thanks";

        String message_thanks = "you're welcome!!";

        Node thanks = new Node(exthanks, message_thanks, message_null, message_null, thanks_name,
                Ontology.dialogClass);
        list.add(thanks);

        String[] exwell = {"come stare", "come andare"};
        String well_name = "well";

        String message_well = "bene bene, sto cercando di capire come parlate voi umani...";

        Node well = new Node(exwell, message_well, message_null, message_null, well_name,
                Ontology.dialogClass);
        list.add(well);

        String[] exwell_done = {};
        String well_done_name = "well_done";

        String message_well_done = "tu?";

        Node well_done = new Node(exwell_done, message_well_done, message_null, message_null, well_done_name,
                Ontology.dialogClass);
        list.add(well_done);

        String[] exwell_response = {"bene grazie", "bene", "bene grazia", " io pure", "io anche", "andare be", "andare bene"};
        String well_response_name = "well_response";

        String message_well_response = "grande! allora continuiamo :)";

        Node well_response = new Node(exwell_response, message_well_response, message_null, message_null, well_response_name,
                Ontology.dialogClass);
        list.add(well_response);

        String[] exok = {"ok", "va bene", "perfetto", "accordo"};
        String ok_name = "okNode";

        String message_ok = ":)";

        Node ok = new Node(exok, message_ok, ok_name,
                Ontology.dialogClass);
        list.add(ok);

        String[] exaddio = {"addio", "a mai più"};
        String addio_name = "addioNode";

        String message_addio = "addio per sempre...";

        Node addio = new Node(exaddio, message_addio, addio_name,
                Ontology.dialogClass);
        list.add(addio);

        String[] exscongiuro = {"non te la prendere", "no dai", "scherzare"};
        String scongiuro_name = "scongiuroNode";

        String message_scongiuro = "amici come prima :)";

        Node scongiuro = new Node(exscongiuro, message_scongiuro, scongiuro_name,
                Ontology.dialogClass);
        list.add(scongiuro);

        String[] exname_name = {};
        String name_name_question = "name_question";

        String message_not_name_question = "come posso chiamarti?";

        Node name_name = new Node(exname_name, message_not_name_question, message_null, message_null,
                name_name_question, Ontology.dialogClass);
        list.add(name_name);

        String[] excapabilities = {"cosa potere fare", "cosa fare", "quale essere la tua funzione"};
        String capabilities_name = "capabilities";

        String message_capabilities = "Posso aiutarti a a cercare capsule e macchine da caffè di marca lavazza, chiedimi pure :)";

        Node capabilities = new Node(excapabilities, message_capabilities,
                capabilities_name, Ontology.dialogClass);
        list.add(capabilities);

        String[] exNoAnswer = {};
        String noAnswer_name = "noAnswer";

        String message_noAnswer = "";

        Node noAnswer = new Node(exNoAnswer, message_noAnswer, message_null, message_null, noAnswer_name,
                Ontology.noAnswer);
        list.add(noAnswer);

        //END------Intenzioni Sociali
        //BEGIN-----RISPOSTE

        //END------RISPOSTE
        //BEGIN------SPECHACT
        String speechActInsertName = "insert";
        Node speechActInsert = new Node(message_null, speechActInsertName, Ontology.speechActClass);
        list.add(speechActInsert);

        String[] speechActQuantifiesEx = {"quanto", "dimmi"};
        String speechActQuantifiesName = "quantifies";
        Node speechActQuantifies = new Node(speechActQuantifiesEx, message_null,
                speechActQuantifiesName, Ontology.speechActClass);
        list.add(speechActQuantifies);

        String[] speechActQuestionEx = {"quale", "?"};
        String speechActQuestionName = "question";
        Node speechActQuestion = new Node(speechActQuestionEx, message_null,
                speechActQuestionName, Ontology.speechActClass);
        list.add(speechActQuestion);

        String[] speechActHowEx = {"come"};
        String speechActHowName = "how";
        Node speechActHow = new Node(speechActHowEx, message_null,
                speechActHowName, Ontology.speechActClass);
        list.add(speechActHow);

        String[] speechActWhyEx = {"perchè", "perché", "perche"};
        String speechActWhyName = "why";
        Node speechActWhy = new Node(speechActWhyEx, message_null,
                speechActWhyName, Ontology.speechActClass);
        list.add(speechActWhy);

        String[] speechActWhatEx = {"cosa"};
        String speechActWhatName = "what";
        Node speechActWhat = new Node(speechActWhatEx, message_null,
                speechActWhatName, Ontology.speechActClass);
        list.add(speechActWhat);

        //END------SPEECHACT
        //BEGIN------PLAN
        String name_plan_info = "plan.InfoPlan";
        Node plan_info = new Node(message_null, name_plan_info, Ontology.planClass);
        //name_info.addRel(Ontology.plan, plan_info);
        list.add(plan_info);

        String well_plan_info = "plan.WellPlan";
        Node well_plan = new Node(message_null, well_plan_info, Ontology.planClass);
        well.addRel(Ontology.plan, well_plan);
        list.add(well_plan);

        //END------PLAN
        
        //BEGIN----KNOLEDGE BASE
        
        //BEGIN----ACTION
        String search_name = "search";
        String message_search = "stai cercando";
        Node search = new Node(message_search, search_name, Ontology.actionClass);
        list.add(search);
       
//        //END----ACTION
//        
//        //BEGIN---DOMAIN OBJECT

        String[] ex_capsule = {"capsula", "capsule", "cialda", "caffè"};
        String capsule_name = "capsule";
        String message_capsule = "capsule";
        Node capsule = new Node(ex_capsule, message_capsule, capsule_name, Ontology.domainClass);
        search.addRel(Ontology.oneToMany, capsule);
        capsule.addRel(Ontology._default, search);
        list.add(capsule);

        String[] ex_machine = {"macchina da caffè", "macchina caffè"};
        String name_machine = "machine";
        String message_machine = "machine";
        Node machine = new Node(ex_machine, message_machine, name_machine, Ontology.domainClass);
        search.addRel(Ontology.oneToMany, machine);
        machine.addRel(Ontology._default, search);
        list.add(machine);
        
        //END----DOMAIN OBJECT
        
        //BEGIN----PROPERTY
        String[] exbill = {"prezzo", "costo", "budget", "budjet"};
        String bill_name = "bill";

        String message_bill = "importo";

        Node bill = new Node(exbill, message_bill, message_bill, message_bill, bill_name, Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, bill);
        machine.addRel(Ontology.oneToOne, bill);
        list.add(bill);
        
        capsule.addInTemplate(bill);
        machine.addInTemplate(bill);
        
        String[] exRoasting = {"tostatura", "tostato"};
        String roasting_name = "roasting";

        String message_roasting = "con tostatura";

        Node roasting = new Node(exRoasting, message_roasting, roasting_name,
                Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, roasting);
        list.add(roasting);
        capsule.addInTemplate(roasting);
        
        String cheap_name = "cheap";

        Node cheap = new Node(message_null,cheap_name, Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, cheap);
        list.add(cheap);

        capsule.addInTemplate(cheap);
        
        String expensive_name = "expensive";

        Node expensive = new Node(message_null,expensive_name, Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, expensive);
        list.add(expensive);

        capsule.addInTemplate(expensive);
        
        String promo_name = "promo";

        Node promo = new Node(message_null,
                promo_name, Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, promo);
        list.add(promo);

        capsule.addInTemplate(promo);
        
        String Dec_name = "dec";

        Node Dec = new Node(message_null,
                Dec_name, Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, Dec);
        list.add(Dec);

        capsule.addInTemplate(Dec);
        
         String bio_name = "bio";

        Node bio = new Node(message_null,
                bio_name, Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, bio);
        list.add(bio);
        
        capsule.addInTemplate(bio);
        
        
        
        String[] exComposition = {"contiene", "miscela", "gusto", "gusti", "compost", "composizione", "composizioni"};
        String composition_name = "composition";

        String message_composition_plu = "composte da";
        String message_composition_sin = "composta da";

        Node composition = new Node(exComposition, message_composition_plu, message_composition_sin, message_composition_plu,
                composition_name, Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, composition);
        list.add(composition);

        capsule.addInTemplate(composition);
        
        String[] exNameCapsule = {"chiama", "nome", "chiamano"};
        String name_capsule_name = "name_capsule";

        String message_name = "dal nome";

        Node name_capsule = new Node(exNameCapsule, message_name, name_capsule_name,
                Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, name_capsule);
        list.add(name_capsule);

        capsule.addInTemplate(name_capsule);
        
        String[] exQuantity = {"scatola", "confezione", "pacco", "scatole", "formato"};
        String quantity_name = "quantity";

        String message_quantity = "con dimensione scatola";

        Node quantity = new Node(exQuantity, message_quantity, quantity_name,
                Ontology.propertyClass);
        capsule.addRel(Ontology.oneToOne, quantity);
        list.add(quantity);

        capsule.addInTemplate(quantity);
        
        //END----PROPERTY
        
        //BEGIN--LITERAL
        String[] exInteger = {};
        String Integer_name = "integer";
        String message_token = "{value:token}";

        Node integer = new Node(exInteger, message_token, Integer_name,
                Ontology.numberClass);
        list.add(integer);

        String[] exFloat = {};
        String Float_name = "float";

        Node Float = new Node(exFloat, message_token, Float_name, Ontology.numberClass);
        list.add(Float);
        
        bill.addRel(Ontology.oneToStar, integer);
        bill.addRel(Ontology.oneToStar, Float);
        
        quantity.addRel(Ontology.oneToStar,Float);
        quantity.addRel(Ontology.oneToStar,integer);
        
        
        String[] exEuro = {"euro", "€", "\u20ac"};
        String euro_name = "euro";

        Node euro = new Node(exEuro, euro_name, euro_name, Ontology.unitClass);
        bill.addRel(Ontology.oneToStar, euro);
        list.add(euro);
        
        String[] exValueAverage = {"media"};
        String valueAverage_name = "average";

        String message_average = "media";

        Node valueAverage = new Node(exValueAverage, message_average,
                valueAverage_name, Ontology.valueClass);
        roasting.addRel(Ontology.oneToStar, valueAverage);
        list.add(valueAverage);
        
        String[] exValueBlack = {"scura", "forte", "amaro"};
        String valueBlack_name = "black";

        String message_black = "scura";

        Node valueBlack = new Node(exValueBlack, message_black, valueBlack_name,
                Ontology.valueClass);
        roasting.addRel(Ontology.oneToStar, valueBlack);
        list.add(valueBlack);
        
        String[] excheapON = {"economic", "più economic"};
        String cheapON_name = "true_cheap";

        String message_cheapON_sin = "economica";
        String message_cheapON_plu = "economiche";

        Node cheapON = new Node(excheapON, message_cheapON_plu, message_cheapON_sin, message_cheapON_plu, cheapON_name, Ontology.valueClass);
        cheap.addRel(Ontology.oneToStar, cheapON);
        list.add(cheapON);

        String[] excheapOFF = {"non economic", "meno economic", "ne' economic", "nè economic"};
        String cheapOFF_name = "false_cheap";

        String message_cheapOFF_sin = "non economica";
        String message_cheapOFF_plu = "non economiche";

        Node cheapOFF = new Node(excheapOFF, message_cheapOFF_plu, message_cheapOFF_sin, message_cheapOFF_plu, cheapOFF_name, Ontology.valueClass);
        cheap.addRel(Ontology.oneToStar, cheapOFF);
        list.add(cheapOFF);
        
        String[] exexpensiveON = {"car", "più car", "costos", "più costos"};
        String expensiveON_name = "true_expensive";

        String message_expensiveON_sin = "cara";
        String message_expensiveON_plu = "care";

        Node expensiveON = new Node(exexpensiveON, message_expensiveON_plu, message_expensiveON_sin, message_expensiveON_plu, expensiveON_name, Ontology.valueClass);
        expensive.addRel(Ontology.oneToStar, expensiveON);
        list.add(expensiveON);

        String[] exexpensiveOFF = {"non car", "meno car", "non costos", "meno costos", "ne' car", "nè car", "ne' costos", "nè costos"};
        String expensiveOFF_name = "false_expensive";

        String message_expensiveOFF_sin = "non cara";
        String message_expensiveOFF_plu = "non care";

        Node expensiveOFF = new Node(exexpensiveOFF, message_expensiveOFF_plu, message_expensiveOFF_sin, message_expensiveOFF_plu, expensiveOFF_name, Ontology.valueClass);
        expensive.addRel(Ontology.oneToStar, expensiveOFF);
        list.add(expensiveOFF);
        
        String[] exPromoON = {"in promozione", "in offerta", "sconti", "offerte", "promozioni", "sconto"};
        String promoON_name = "true_promo";

        String message_promoON = "in promozione";

        Node promoON = new Node(exPromoON, message_promoON, message_promoON, message_promoON, promoON_name, Ontology.valueClass);
        promo.addRel(Ontology.oneToStar, promoON);
        list.add(promoON);

        String[] exPromoOFF = {"non in promozione", "non in offerta"};
        String promoOFF_name = "false_promo";

        String message_promoOFF = "non in promozione";

        Node promoOFF = new Node(exPromoOFF, message_promoOFF, message_promoOFF, message_promoOFF, promoOFF_name, Ontology.valueClass);
        promo.addRel(Ontology.oneToStar, promoOFF);
        list.add(promoOFF);
        
         String[] exDecON = {"dec", "decaffeinate", "decaffeinato", "senza caffeina"};
        String DecON_name = "true_dec";

        String message_DecON = "senza caffeina";

        Node DecON = new Node(exDecON, message_DecON, message_DecON, message_DecON, DecON_name, Ontology.valueClass);
        Dec.addRel(Ontology.oneToStar, DecON);
        list.add(DecON);

        String[] exDecOFF = {"non decaffeinato", "non decaffeinate", "caffeina"};
        String DecOFF_name = "false_dec";

        String message_DecOFF = "con caffeina";

        Node DecOFF = new Node(exDecOFF, message_DecOFF, message_DecOFF, message_DecOFF, DecOFF_name, Ontology.valueClass);
        Dec.addRel(Ontology.oneToStar, DecOFF);
        list.add(DecOFF);
        
        String[] exBioON = {"bio", "biodegradabili", "biodegradabile", "compostabili", "compostabile", "100% biodegradabili", "100% biodegradabile"};
        String bioON_name = "true_bio";

        String message_bioON_plu = "biodegradabili";
        String message_bioON_sin = "biodegradabile";

        Node bioON = new Node(exBioON, message_bioON_plu, message_bioON_sin, message_bioON_plu, bioON_name, Ontology.valueClass);
        bio.addRel(Ontology.oneToStar, bioON);
        list.add(bioON);

        String[] exBioOFF = {"non bio", "non biodegradabili", "non biodegradabile", "non compostabile", "non compostabili"};
        String bioOFF_name = "false_bio";

        String message_bioOFF_plu = "non biodegradabili";
        String message_bioOFF_sin = "non biodegradabile";

        Node bioOFF = new Node(exBioOFF, message_bioOFF_plu, message_bioOFF_sin, message_bioOFF_plu, bioOFF_name, Ontology.valueClass);
        bio.addRel(Ontology.oneToStar, bioOFF);
        list.add(bioOFF);

        String[] exValueMilk = {"latte in polvere"};
        String valueMilk_name = "milk";

        String message_milk = "latte in polvere";

        Node valueMilk = new Node(exValueMilk, message_milk, valueMilk_name,
                Ontology.valueClass);
        composition.addRel(Ontology.oneToStar, valueMilk);
        list.add(valueMilk);

        String[] exValueTea = {"tè", "the", "tea"};
        String valueTea_name = "tea";

        String message_tea = "tè";

        Node valueTea = new Node(exValueTea, message_tea, valueTea_name,
                Ontology.valueClass);
        composition.addRel(Ontology.oneToStar, valueTea);
        list.add(valueTea);

        String[] exValueBarley = {"orzo"};
        String valueBarley_name = "barley";

        String message_barley = "orzo";

        Node valueBarley = new Node(exValueBarley, message_barley, valueBarley_name,
                Ontology.valueClass);
        composition.addRel(Ontology.oneToStar, valueBarley);
        list.add(valueBarley);

        String[] exValueGinseng = {"ginseng"};
        String valueGinseng_name = "ginseng";

        String message_ginseng = "ginseng";

        Node valueGinseng = new Node(exValueGinseng, message_ginseng,
                valueGinseng_name, Ontology.valueClass);
        composition.addRel(Ontology.oneToStar, valueGinseng);
        list.add(valueGinseng);

        String[] exValueArabic = {"arabic", "100% arabic", "cento per cento arabic"};
        String valueArabic_name = "arabic";

        String message_arabic = "miscela arabica";

        Node valueArabic = new Node(exValueArabic, message_arabic, valueArabic_name,
                Ontology.valueClass);
        composition.addRel(Ontology.oneToStar, valueArabic);
        list.add(valueArabic);

        String[] exValueRobust = {"robust"};
        String valueRobust_name = "robust";

        String message_robust = "miscela robusta";

        Node valueRobust = new Node(exValueRobust, message_robust, valueRobust_name,
                Ontology.valueClass);
        composition.addRel(Ontology.oneToStar, valueRobust);
        list.add(valueRobust);   

        String[] exValueQRossa = {"qualità rossa", "lavazza qualità rossa"};
        String qRossa_name = "lavazza qualità rossa";

        Node qRossa = new Node(exValueQRossa, qRossa_name,qRossa_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, qRossa);
        list.add(qRossa);

        String[] exValueSAlta = {"selva alta", "lavazza selva alta"};
        String sAlta_name = "lavazza selva alta";

        Node sAlta = new Node(exValueSAlta, sAlta_name, sAlta_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, sAlta);
        list.add(sAlta);

        String[] exValuecPassita = {"cereja passita", "lavazza cereja passita"};
        String cPassita_name = "lavazza cereja passita";

        Node cPassita = new Node(exValuecPassita, cPassita_name, cPassita_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, cPassita);
        list.add(cPassita);

        String[] exValueAromatico = {"aromatico", "lavazza aromatico"};
        String aromatico_name = "lavazza aromatico";

        Node aromatico = new Node(exValueAromatico, aromatico_name, aromatico_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, aromatico);
        list.add(aromatico);

        String[] exValueRicco = {"ricco", "lavazza ricco"};
        String ricco_name = "lavazza ricco";

        Node ricco = new Node(exValueRicco, ricco_name, ricco_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, ricco);
        list.add(ricco);

        String[] exValueTierra = {"¡tierra!", "tierra", "lavazza tierra"};
        String tierra_name = "lavazza tierra";

        Node tierra = new Node(exValueTierra, tierra_name, tierra_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, tierra);
        list.add(tierra);

        String[] exValuePassionale = {"passionale", "lavazza passionale"};
        String passionale_name = "lavazza passionale";

        Node passionale = new Node(exValuePassionale, passionale_name,
                passionale_name, Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, passionale);
        list.add(passionale);

        String[] exValueSoave = {"soave", "lavazza soave"};
        String soave_name = "lavazza soave";

        Node soave = new Node(exValueSoave, soave_name,soave_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, soave);
        list.add(soave);

        // Semplifico per ora per evitare ambiguità
        String[] exValueIntenso = { /* "intenso", */"lavazza intenso"};
        String intenso_name = "lavazza intenso";

        Node intenso = new Node(exValueIntenso, intenso_name, intenso_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, intenso);
        list.add(intenso);

        String[] exValueDCremoso = {"deck cremoso", "lavazza deck cremoso", "dek cremoso", "lavazza dek cremoso"};
        String dCremoso_name = "lavazza deck cremoso";

        Node dCremoso = new Node(exValueDCremoso, dCremoso_name, dCremoso_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, dCremoso);
        list.add(dCremoso);

        String[] exValueDelizioso = {"delizioso", "lavazza delizioso"};
        String delizioso_name = "lavazza delizioso";

        Node delizioso = new Node(exValueDelizioso, delizioso_name, delizioso_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, delizioso);
        list.add(delizioso);

        String[] exValueDivino = {"divino", "lavazza divino"};
        String divino_name = "lavazza divino";

        Node divino = new Node(exValueDivino, divino_name, divino_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, divino);
        list.add(divino);

        String[] exValueDolce = {"dolce", "lavazza dolce"};
        String dolce_name = "lavazza dolce";

        Node dolce = new Node(exValueDolce, dolce_name, dolce_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, dolce);
        list.add(dolce);

        String[] exValuecGinseng = {"caffè ginseng", "lavazza caffè ginseng"};
        String cGinseng_name = "lavazza caffè ginseng";

        Node cGinseng = new Node(exValuecGinseng, cGinseng_name, cGinseng_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, cGinseng);
        list.add(cGinseng);

        String[] exValueOrzo = {"lavazza orzo"};
        String orzo_name = "lavazza orzo";

        Node orzo = new Node(exValueOrzo, orzo_name, orzo_name,
                Ontology.valueClass);
        name_capsule.addRel(Ontology.oneToStar, orzo);
        list.add(orzo);

        //END----LITERAL
        
        

        //END LITERAL
        //END--- KNOWLEDGE BASE
        // create an empty Model
        OntModel model = Ontology.define();

        for (Node n : list) {
            Resource now = model.createResource("http://eu.reply.it/" + n.name).addProperty(Ontology.name, n.name)
                    .addProperty(RDF.type, n.rdf_semClass);
            for (String ex : n.example) {
                now.addProperty(Ontology.example, ex);
            }
            now.addProperty(Ontology.message_singular, n.singular);
            now.addProperty(Ontology.message_plural, n.plural);
            now.addProperty(Ontology.message_not, n.not);
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
                n.rdf.addProperty(Ontology.template, model.createList(rdf_list));
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
