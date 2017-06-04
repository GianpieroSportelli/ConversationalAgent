//package eu.reply.SN;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import com.hp.hpl.jena.ontology.OntModel;
//import com.hp.hpl.jena.rdf.model.Property;
//import com.hp.hpl.jena.rdf.model.RDFNode;
//import com.hp.hpl.jena.rdf.model.Resource;
//import com.hp.hpl.jena.vocabulary.RDF;
//
//import eu.reply.Config.Config;
//
//public class Creator {
//	public static final String url = Config.getPathSemanticNet();
//	public static final String format=null;
//
//	public static void main(String[] args) {
//
//		ArrayList<Node> list = new ArrayList<Node>();
//
//		String[] exGreetings = { "ciao", "buongiorno", "buonasera", "buonanotte", "salve", "i miei ossequi","buondì","buondi" };
//		String greetings_name = "greetings";
//
//		String message_not_greetings = "Ciao";
//		String message_null = "";
//
//		Node greetings = new Node(exGreetings, message_not_greetings, message_null, message_null, greetings_name,
//				Vocabulary.dialogClass);
//		list.add(greetings);
//		
//		String[] exNoAnswer = { };
//		String noAnswer_name = "noAnswer";
//
//		String message_noAnswer = "non ho capito di cosa stai parlando. prova a riformulare la frase sono un po' ignorante.";
//
//		Node noAnswer = new Node(exNoAnswer, message_noAnswer, message_null, message_null, noAnswer_name,
//				Vocabulary.noAnswer);
//		list.add(noAnswer);
//		
//		String[] exconfirmQuestion = { };
//		String confirmQuestion_name = "confirmQuestion";
//
//		String message_confirmQuestion = "si abbiamo";
//
//		Node confirmQuestion = new Node(exconfirmQuestion ,message_null,message_confirmQuestion, message_null, confirmQuestion_name,
//				Vocabulary.confirmQuestion);
//		list.add(confirmQuestion);
//
//		String[] exThanks = { "grazie", "la ringrazio" };
//		String thanks_name = "thanks";
//
//		String message_not_thanks = "Grazie a te";
//
//		Node thanks = new Node(exThanks, message_not_thanks, message_null, message_null, thanks_name,
//				Vocabulary.dialogClass);
//		list.add(thanks);
//
//		// String[] exConfirm = { "si", "va bene", "accetto", "ok" };
//		// String confirm_name = "confirm";
//		//
//		// String message_not_confirm = "Perfetto.";
//		//
//		// Node confirm = new Node(exConfirm, message_not_confirm,
//		// message_null,message_null, confirm_name,
//		// LavazzaVocabularyClasses.questionClass);
//		// list.add(confirm);
//		//
//		// String[] exCancel = { "no", "lascia stare", "annulla" };
//		// String cancel_name = "Cancel";
//		//
//		// String message_not_cancel = "ok.";
//		//
//		// Node cancel = new Node(exCancel, message_not_cancel, message_null,
//		// message_null, cancel_name,
//		// LavazzaVocabularyClasses.questionClass);
//		// list.add(cancel);
//
//		String[] exCourtesy = { "per piacere", "per favore", "cortesemente", "gentilmente" };
//		String courtesy_name = "courtesy";
//
//		Node courtesy = new Node(exCourtesy, message_null, message_null, message_null, courtesy_name,
//				Vocabulary.dialogClass);
//		list.add(courtesy);
//
//		String[] exSearch = { "mi interesserebbe", "quali", "mi interesserebbero", "avete", "vendete", "cercavo",
//				"avrei bisogno", "vorrei comprare", "vorrei acquistare", "voglio", "vorrei", "mi servirebbero",
//				"interessato", "ho bisogno", "mi servono", "mi servirebbe", "quanto", "quante", "tipo", "qual è",
//				"qual'è", "quanta", "vorrei sapere", "quali tipi", "quanti tipi", "come si", "mi piacerebbe",
//				"mi interessa", "disponibili", "disponibile","esistono","ci sono","mi date","sono presenti" };
//		String search_name = "search";
//
//		String message_singular_search = "la";
//		String message_plural_search = "le";
//		String message_not_search = "non abbiamo";
//
//		Node search = new Node(exSearch, message_not_search, message_singular_search, message_plural_search,
//				search_name, Vocabulary.actionClass);
//		list.add(search);
//
//		// String[] exShop = { "aggiungi", "vorrei ordinarle", "vorrei
//		// aggiungerle", "aggiungile", "aggiungili",
//		// "aggiungimele", "aggiungimi", "metti", "inserisci", "vorrei
//		// ordinarlo", "vorrei aggiungerlo",
//		// "aggiungilo", "aggiungimelo", "vorrei aggiungere" };
//		// String shop_name = "shop";
//		//
//		// String message_singular_shop = "Aggiungo al carrello la";
//		// String message_plural_shop = "Aggiungo al carrello le";
//		// String message_not_shop = "Non ci sono ";
//		//
//		// Node shop = new Node(exShop, message_not_shop, message_singular_shop,
//		// message_plural_shop, shop_name,
//		// LavazzaVocabularyClasses.actionClass);
//		// list.add(shop);
//
//		String[] exCapsule = { "prodott","capsule", "capsula", "caffè","caffe","cialda","cialde" };
//		String capsule_name = "capsule";
//
//		String message_singular_capsule = "capsula";
//		String message_plural_capsule = "capsule";
//
//		Node capsule = new Node(exCapsule, message_plural_capsule, message_singular_capsule, message_plural_capsule,
//				capsule_name, Vocabulary.domainClass);
//		search.addRel(Vocabulary.applicable, capsule);
//		// shop.addRel(LavazzaVocabularyClasses.applicable, capsule);
//		list.add(capsule);
//
//		String[] exRoasting = { "tostatura", "tostato" };
//		String roasting_name = "roasting";
//
//		String message_roasting = "con tostatura";
//
//		Node roasting = new Node(exRoasting, message_roasting, message_roasting, message_roasting, roasting_name,
//				Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, roasting);
//		list.add(roasting);
//		capsule.addInTemplate(roasting);
//
//		String[] exValueAverage = { "media" };
//		String valueAverage_name = "average";
//
//		String message_average = "media";
//
//		Node valueAverage = new Node(exValueAverage, message_average, message_average, message_average,
//				valueAverage_name, Vocabulary.valueClass);
//		roasting.addRel(Vocabulary.value, valueAverage);
//		list.add(valueAverage);
//
//		String[] exValueBlack = { "scura","forte","amaro"};
//		String valueBlack_name = "black";
//
//		String message_black = "scura";
//
//		Node valueBlack = new Node(exValueBlack, message_black, message_black, message_black, valueBlack_name,
//				Vocabulary.valueClass);
//		roasting.addRel(Vocabulary.value, valueBlack);
//		list.add(valueBlack);
//		
//		String[] excheap = {};
//		String cheap_name = "cheap";
//
//		Node cheap = new Node(excheap, message_null, message_null, message_null,
//				cheap_name, Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, cheap);
//		list.add(cheap);
//		
//		capsule.addInTemplate(cheap);
//		
//		String[] excheapON={"economic","più economic"};
//		String cheapON_name="true_cheap";
//		
//		String message_cheapON_sin="economica";
//		String message_cheapON_plu="economiche";
//		
//		Node cheapON=new Node(excheapON, message_cheapON_plu, message_cheapON_sin, message_cheapON_plu, cheapON_name, Vocabulary.valueClass);
//		cheap.addRel(Vocabulary.value, cheapON);
//		list.add(cheapON);
//		
//		String[] excheapOFF={"non economic","meno economic","ne' economic","nè economic"};
//		String cheapOFF_name="false_cheap";
//		
//		String message_cheapOFF_sin="non economica";
//		String message_cheapOFF_plu="non economiche";
//		
//		Node cheapOFF=new Node(excheapOFF, message_cheapOFF_plu, message_cheapOFF_sin, message_cheapOFF_plu, cheapOFF_name, Vocabulary.valueClass);
//		cheap.addRel(Vocabulary.value, cheapOFF);
//		list.add(cheapOFF);
//		
//		String[] exExpensive = {};
//		String expensive_name = "expensive";
//
//		Node expensive = new Node(exExpensive, message_null, message_null, message_null,
//				expensive_name, Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, expensive);
//		list.add(expensive);
//		
//		capsule.addInTemplate(expensive);
//		
//		String[] exexpensiveON={"car","più car","costos","più costos"};
//		String expensiveON_name="true_expensive";
//		
//		String message_expensiveON_sin="cara";
//		String message_expensiveON_plu="care";
//		
//		
//		Node expensiveON=new Node(exexpensiveON, message_expensiveON_plu, message_expensiveON_sin, message_expensiveON_plu, expensiveON_name, Vocabulary.valueClass);
//		expensive.addRel(Vocabulary.value, expensiveON);
//		list.add(expensiveON);
//		
//		String[] exexpensiveOFF={"non car","meno car","non costos","meno costos","ne' car","nè car","ne' costos","nè costos"};
//		String expensiveOFF_name="false_expensive";
//		
//		String message_expensiveOFF_sin="non cara";
//		String message_expensiveOFF_plu="non care";
//		
//		Node expensiveOFF=new Node(exexpensiveOFF, message_expensiveOFF_plu, message_expensiveOFF_sin, message_expensiveOFF_plu, expensiveOFF_name, Vocabulary.valueClass);
//		expensive.addRel(Vocabulary.value, expensiveOFF);
//		list.add(expensiveOFF);
//		
//		String[] exPromo = {};
//		String promo_name = "promo";
//
//		Node promo = new Node(exPromo, message_null, message_null, message_null,
//				promo_name, Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, promo);
//		list.add(promo);
//		
//		capsule.addInTemplate(promo);
//		
//		String[] exPromoON={"in promozione","in offerta","sconti","offerte","promozioni","sconto"};
//		String promoON_name="true_promo";
//		
//		String message_promoON="in promozione";
//		
//		Node promoON=new Node(exPromoON, message_promoON, message_promoON, message_promoON, promoON_name, Vocabulary.valueClass);
//		promo.addRel(Vocabulary.value, promoON);
//		list.add(promoON);
//		
//		String[] exPromoOFF={"non in promozione","non in offerta"};
//		String promoOFF_name="false_promo";
//		
//		String message_promoOFF="non in promozione";
//		
//		Node promoOFF=new Node(exPromoOFF, message_promoOFF, message_promoOFF, message_promoOFF, promoOFF_name, Vocabulary.valueClass);
//		promo.addRel(Vocabulary.value, promoOFF);
//		list.add(promoOFF);
//		
//		String[] exDec = {};
//		String Dec_name = "dec";
//
//		Node Dec = new Node(exDec, message_null, message_null, message_null,
//				Dec_name, Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, Dec);
//		list.add(Dec);
//		
//		capsule.addInTemplate(Dec);
//		
//		String[] exDecON={"dec","decaffeinate","decaffeinato","senza caffeina"};
//		String DecON_name="true_dec";
//		
//		String message_DecON="senza caffeina";
//		
//		Node DecON=new Node(exDecON, message_DecON, message_DecON, message_DecON, DecON_name, Vocabulary.valueClass);
//		Dec.addRel(Vocabulary.value, DecON);
//		list.add(DecON);
//		
//		String[] exDecOFF={"non decaffeinato","non decaffeinate","caffeina"};
//		String DecOFF_name="false_dec";
//		
//		String message_DecOFF="con caffeina";
//		
//		Node DecOFF=new Node(exDecOFF, message_DecOFF, message_DecOFF, message_DecOFF, DecOFF_name, Vocabulary.valueClass);
//		Dec.addRel(Vocabulary.value, DecOFF);
//		list.add(DecOFF);
//		
//		String[] exBio = {};
//		String bio_name = "bio";
//
//		Node bio = new Node(exBio, message_null, message_null, message_null,
//				bio_name, Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, bio);
//		list.add(bio);
//		
//		capsule.addInTemplate(bio);
//		
//		String[] exBioON={"bio","biodegradabili","biodegradabile","compostabili","compostabile","100% biodegradabili","100% biodegradabile"};
//		String bioON_name="true_bio";
//		
//		String message_bioON_plu="biodegradabili";
//		String message_bioON_sin="biodegradabile";
//		
//		Node bioON=new Node(exBioON, message_bioON_plu, message_bioON_sin, message_bioON_plu, bioON_name, Vocabulary.valueClass);
//		bio.addRel(Vocabulary.value, bioON);
//		list.add(bioON);
//		
//		String[] exBioOFF={"non bio","non biodegradabili","non biodegradabile","non compostabile","non compostabili"};
//		String bioOFF_name="false_bio";
//		
//		String message_bioOFF_plu="non biodegradabili";
//		String message_bioOFF_sin="non biodegradabile";
//		
//		Node bioOFF=new Node(exBioOFF, message_bioOFF_plu, message_bioOFF_sin, message_bioOFF_plu, bioOFF_name, Vocabulary.valueClass);
//		bio.addRel(Vocabulary.value, bioOFF);
//		list.add(bioOFF);
//
//		String[] exComposition = { "contiene", "miscela","gusto","gusti","compost","composizione","composizioni" };
//		String composition_name = "composition";
//
//		String message_composition_plu = "composte da";
//		String message_composition_sin = "composta da";
//		
//		Node composition = new Node(exComposition, message_composition_plu, message_composition_sin, message_composition_plu,
//				composition_name, Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, composition);
//		list.add(composition);
//
//		capsule.addInTemplate(composition);
//		
//		String[] exValueMilk = { "latte in polvere" };
//		String valueMilk_name = "milk";
//
//		String message_milk = "latte in polvere";
//
//		Node valueMilk = new Node(exValueMilk, message_milk, message_milk, message_milk, valueMilk_name,
//				Vocabulary.valueClass);
//		composition.addRel(Vocabulary.value, valueMilk);
//		list.add(valueMilk);
//		
//		String[] exValueTea = { "tè","the","tea" };
//		String valueTea_name = "tea";
//
//		String message_tea = "tè";
//
//		Node valueTea = new Node(exValueTea, message_tea, message_tea, message_tea, valueTea_name,
//				Vocabulary.valueClass);
//		composition.addRel(Vocabulary.value, valueTea);
//		list.add(valueTea);
//
//		String[] exValueBarley = { "orzo" };
//		String valueBarley_name = "barley";
//
//		String message_barley = "orzo";
//
//		Node valueBarley = new Node(exValueBarley, message_barley, message_barley, message_barley, valueBarley_name,
//				Vocabulary.valueClass);
//		composition.addRel(Vocabulary.value, valueBarley);
//		list.add(valueBarley);
//
//		String[] exValueGinseng = { "ginseng" };
//		String valueGinseng_name = "ginseng";
//
//		String message_ginseng = "ginseng";
//
//		Node valueGinseng = new Node(exValueGinseng, message_ginseng, message_ginseng, message_ginseng,
//				valueGinseng_name, Vocabulary.valueClass);
//		composition.addRel(Vocabulary.value, valueGinseng);
//		list.add(valueGinseng);
//
//		String[] exValueArabic = { "arabic","100% arabic","cento per cento arabic" };
//		String valueArabic_name = "arabic";
//
//		String message_arabic = "miscela arabica";
//
//		Node valueArabic = new Node(exValueArabic, message_arabic, message_arabic, message_arabic, valueArabic_name,
//				Vocabulary.valueClass);
//		composition.addRel(Vocabulary.value, valueArabic);
//		list.add(valueArabic);
//
//		String[] exValueRobust = { "robust" };
//		String valueRobust_name = "robust";
//
//		String message_robust = "miscela robusta";
//
//		Node valueRobust = new Node(exValueRobust, message_robust, message_robust, message_robust, valueRobust_name,
//				Vocabulary.valueClass);
//		composition.addRel(Vocabulary.value, valueRobust);
//		list.add(valueRobust);
//
//		String[] exNameCapsule = { "chiama", "nome", "chiamano" };
//		String name_capsule_name = "name capsule";
//
//		String message_name = "dal nome";
//
//		Node name_capsule = new Node(exNameCapsule, message_name, message_name, message_name, name_capsule_name,
//				Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, name_capsule);
//		list.add(name_capsule);
//
//		capsule.addInTemplate(name_capsule);
//
//		String[] exValueQRossa = { "qualità rossa", "lavazza qualità rossa" };
//		String qRossa_name = "lavazza qualità rossa";
//
//		Node qRossa = new Node(exValueQRossa, qRossa_name, qRossa_name, qRossa_name, qRossa_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, qRossa);
//		list.add(qRossa);
//
//		String[] exValueSAlta = { "selva alta", "lavazza selva alta" };
//		String sAlta_name = "lavazza selva alta";
//
//		Node sAlta = new Node(exValueSAlta, sAlta_name, sAlta_name, sAlta_name, sAlta_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, sAlta);
//		list.add(sAlta);
//
//		String[] exValuecPassita = { "cereja passita", "lavazza cereja passita" };
//		String cPassita_name = "lavazza cereja passita";
//
//		Node cPassita = new Node(exValuecPassita, cPassita_name, cPassita_name, cPassita_name, cPassita_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, cPassita);
//		list.add(cPassita);
//
//		String[] exValueAromatico = { "aromatico", "lavazza aromatico" };
//		String aromatico_name = "lavazza aromatico";
//
//		Node aromatico = new Node(exValueAromatico, aromatico_name, aromatico_name, aromatico_name, aromatico_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, aromatico);
//		list.add(aromatico);
//
//		String[] exValueRicco = { "ricco", "lavazza ricco" };
//		String ricco_name = "lavazza ricco";
//
//		Node ricco = new Node(exValueRicco, ricco_name, ricco_name, ricco_name, ricco_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, ricco);
//		list.add(ricco);
//
//		String[] exValueTierra = { "¡tierra!", "tierra", "lavazza tierra" };
//		String tierra_name = "lavazza tierra";
//
//		Node tierra = new Node(exValueTierra, tierra_name, tierra_name, tierra_name, tierra_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, tierra);
//		list.add(tierra);
//
//		String[] exValuePassionale = { "passionale", "lavazza passionale" };
//		String passionale_name = "lavazza passionale";
//
//		Node passionale = new Node(exValuePassionale, passionale_name, passionale_name, passionale_name,
//				passionale_name, Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, passionale);
//		list.add(passionale);
//
//		String[] exValueSoave = { "soave", "lavazza soave" };
//		String soave_name = "lavazza soave";
//
//		Node soave = new Node(exValueSoave, soave_name, soave_name, soave_name, soave_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, soave);
//		list.add(soave);
//
//		// Semplifico per ora per evitare ambiguità
//		String[] exValueIntenso = { /* "intenso", */"lavazza intenso" };
//		String intenso_name = "lavazza intenso";
//
//		Node intenso = new Node(exValueIntenso, intenso_name, intenso_name, intenso_name, intenso_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, intenso);
//		list.add(intenso);
//
//		String[] exValueDCremoso = { "deck cremoso", "lavazza deck cremoso", "dek cremoso", "lavazza dek cremoso" };
//		String dCremoso_name = "lavazza deck cremoso";
//
//		Node dCremoso = new Node(exValueDCremoso, dCremoso_name, dCremoso_name, dCremoso_name, dCremoso_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, dCremoso);
//		list.add(dCremoso);
//
//		String[] exValueDelizioso = { "delizioso", "lavazza delizioso" };
//		String delizioso_name = "lavazza delizioso";
//
//		Node delizioso = new Node(exValueDelizioso, delizioso_name, delizioso_name, delizioso_name, delizioso_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, delizioso);
//		list.add(delizioso);
//
//		String[] exValueDivino = { "divino", "lavazza divino" };
//		String divino_name = "lavazza divino";
//
//		Node divino = new Node(exValueDivino, divino_name, divino_name, divino_name, divino_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, divino);
//		list.add(divino);
//
//		String[] exValueDolce = { "dolce", "lavazza dolce" };
//		String dolce_name = "lavazza dolce";
//
//		Node dolce = new Node(exValueDolce, dolce_name, dolce_name, dolce_name, dolce_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, dolce);
//		list.add(dolce);
//
//		String[] exValuecGinseng = { "caffè ginseng", "lavazza caffè ginseng" };
//		String cGinseng_name = "lavazza caffè ginseng";
//
//		Node cGinseng = new Node(exValuecGinseng, cGinseng_name, cGinseng_name, cGinseng_name, cGinseng_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, cGinseng);
//		list.add(cGinseng);
//
//		String[] exValueOrzo = { "lavazza orzo" };
//		String orzo_name = "lavazza orzo";
//
//		Node orzo = new Node(exValueOrzo, orzo_name, orzo_name, orzo_name, orzo_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, orzo);
//		list.add(orzo);
//
//		String[] exQuantity = { "scatola", "confezione", "pacco", "scatole", "confezioni", "pacchi","formati","formato" };
//		String quantity_name = "quantity";
//
//		String message_quantity = "con dimensione scatola";
//
//		Node quantity = new Node(exQuantity, message_quantity, message_quantity, message_quantity, quantity_name,
//				Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, quantity);
//		list.add(quantity);
//
//		capsule.addInTemplate(quantity);
//
//		String[] exInteger = {};
//		String Integer_name = "integer";
//		String message_token = "{value:token}";
//
//		Node integer = new Node(exInteger, message_token, message_token, message_token, Integer_name,
//				Vocabulary.numberClass);
//		list.add(integer);
//
//		String[] exFloat = {};
//		String Float_name = "float";
//
//		Node Float = new Node(exFloat, message_token, message_token, message_token, Float_name,
//				Vocabulary.numberClass);
//		list.add(Float);
//
//		String[] exIntensity = { "intenso", "intensità", "intense" };
//		String intensity_name = "intensity";
//
//		String message_intensity = "con intensità";
//
//		Node intensity = new Node(exIntensity, message_intensity, message_intensity, message_intensity, intensity_name,
//				Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, intensity);
//		list.add(intensity);
//
//		capsule.addInTemplate(intensity);
//
//		intensity.addRel(Vocabulary.value, integer);
//
//		String[] exText = {};
//		String text_name = "text";
//
//		Node text = new Node(exText, message_null, message_null, message_null, text_name,
//				Vocabulary.extractedClass);
//		list.add(text);
//
//		quantity.addRel(Vocabulary.value, integer);
//
//		String[] exPrice = { "costa", "prezzo", "costano", "costo","budget","budjet" };
//		String price_name = "price";
//
//		String message_price = "con prezzo";
//
//		Node price = new Node(exPrice, message_price, message_price, message_price, price_name,
//				Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, price);
//		price.addRel(Vocabulary.number, integer);
//		price.addRel(Vocabulary.number, Float);
//		list.add(price);
//
//		capsule.addInTemplate(price);
//
//		String[] exLess = { "inferiore", "meno", "non superiore", "minore", "sotto","minori","massimo" };
//		String less_name = "less";
//
//		String message_less = "minore di";
//
//		Node less = new Node(exLess, message_less, message_less, message_less, less_name,
//				Vocabulary.modClass);
//		price.addRel(Vocabulary.mod, less);
//		quantity.addRel(Vocabulary.mod, less);
//		intensity.addRel(Vocabulary.mod, less);
//		list.add(less);
//
//		String[] exEuro = { "euro", "€" };
//		String euro_name = "euro";
//
//		Node euro = new Node(exEuro, euro_name, euro_name, euro_name, euro_name, Vocabulary.unitClass);
//		price.addRel(Vocabulary.unit, euro);
//		list.add(euro);
//
//		String[] exMore = { "superiore", "più", "non inferiore", "maggiore", "sopra","minimo" };
//		String more_name = "more";
//
//		String message_more = "maggiore di";
//
//		Node more = new Node(exMore, message_more, message_more, message_more, more_name,
//				Vocabulary.modClass);
//		price.addRel(Vocabulary.mod, more);
//		quantity.addRel(Vocabulary.mod, more);
//		intensity.addRel(Vocabulary.mod, more);
//		list.add(more);
//
//		// descrizione non utilizzata attualemente
//		// String[] exDescription = {};
//		// String descriprion_name = "description";
//		//
//		// Node description = new Node(exDescription, descriprion_name,
//		// LavazzaVocabularyClasses.propertyClass);
//		// description.addRel(LavazzaVocabularyClasses.value, text);
//		// capsule.addRel(LavazzaVocabularyClasses.hasA, description);
//		// list.add(description);
//
//		// EXCEPTION
//		String[] exFeatures = { "caratteristiche", "proprietà","informazioni","informazione","panoramica" };
//		String features_name = "features";
//
//		String message_singular_feature = "ha caratteristiche";
//		String message_plural_feature = "hanno caratteristiche";
//
//		Node features = new Node(exFeatures, message_null, message_singular_feature, message_plural_feature,
//				features_name, Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, features);
//
//		String dump_name = "dump";
//		Node dump = new Node(new String[0], message_null, message_null, message_null, dump_name,
//				Vocabulary.valueClass);
//		features.addRel(Vocabulary.value, dump);
//		list.add(dump);
//		list.add(features);
//
//		String[] exCompatible = { "compatibile", "compatibili","per la","essere inserit","per le" };
//		String compatible_name = "compatible";
//
//		String message_compatible = "compatibili con";
//
//		Node compatible = new Node(exCompatible, message_compatible, message_compatible, message_compatible,
//				compatible_name, Vocabulary.propertyClass);
//		compatible.addRel(Vocabulary.value, dump);
//		list.add(compatible);
//		capsule.addRel(Vocabulary.compatible, compatible);
//
//		// MACCHINETTE
//
//		// DOMAIN
//		String[] exMachine = { "prodott","macchina", "macchinetta", "macchina da caffè", "macchina da caffe", "macchinetta da caffè","macchinetta da caffe", "macchinette",
//				"macchine", "macchinette da caffè","macchinette da caffe", "macchine da caffè","macchine da caffe","macchine per il caffè","macchine per il caffe",
//				"macchinette per il caffè","macchinette per il caffe","macchina per il caffè","macchina per il caffe","macchinetta per il caffè","macchinetta per il caffe","macchine del caffè","macchine del caffe",
//				"macchinette del caffè","macchinette del caffe","macchina del caffè","macchina del caffe","macchinetta del caffè","macchinetta del caffe"};
//		String machine_name = "machine";
//
//		String message_singular_machine = "macchina da caffè";
//		String message_plural_machine = "macchine da caffè";
//
//		Node machine = new Node(exMachine, message_plural_machine, message_singular_machine, message_plural_machine,
//				machine_name, Vocabulary.domainClass);
//		search.addRel(Vocabulary.applicable, machine);
//		// QUANTIFY REL
//		// quantify.addRel(LavazzaVocabularyClasses.applicable, capsule);
//		// shop.addRel(LavazzaVocabularyClasses.applicable, machine);
//		machine.addRel(Vocabulary.hasA, price);
//		machine.addRel(Vocabulary.hasA, promo);
//		machine.addRel(Vocabulary.hasA, cheap);
//		machine.addRel(Vocabulary.hasA, expensive);
//		machine.addInTemplate(price);
//		machine.addInTemplate(promo);
//		machine.addInTemplate(cheap);
//		machine.addInTemplate(expensive);
//		machine.addRel(Vocabulary.hasA, features);
//		machine.addRel(Vocabulary.compatible, compatible);
//		list.add(machine);
//		
//		String[] exfast= {};
//		String fast_name = "fast";
//
//		Node fast = new Node(exfast, message_null, message_null, message_null,
//				fast_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, fast);
//		list.add(fast);
//		
//		machine.addInTemplate(fast);
//		
//		String[] exfastON={"veloc"};
//		String fastON_name="true_fast";
//		
//		String message_fastON_sin="veloce";
//		String message_fastON_plu="veloci";
//		
//		Node fastON=new Node(exfastON, message_fastON_plu, message_fastON_sin, message_fastON_plu, fastON_name, Vocabulary.valueClass);
//		fast.addRel(Vocabulary.value, fastON);
//		list.add(fastON);
//		
//		String[] exfastOFF={"non veloc","ne' veloc","nè veloc"};
//		String fastOFF_name="false_fast";
//		
//		String message_fastOFF_sin="non veloce";
//		String message_fastOFF_plu="non veloci";
//		
//		Node fastOFF=new Node(exfastOFF, message_fastOFF_plu, message_fastOFF_sin, message_fastOFF_plu, fastOFF_name, Vocabulary.valueClass);
//		fast.addRel(Vocabulary.value, fastOFF);
//		list.add(fastOFF);
//		
//		String[] exslow= {};
//		String slow_name = "slow";
//
//		Node slow = new Node(exslow, message_null, message_null, message_null,
//				slow_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, slow);
//		list.add(slow);
//		
//		machine.addInTemplate(slow);
//		
//		String[] exslowON={"lent"};
//		String slowON_name="true_slow";
//		
//		String message_slowON_sin="lenta";
//		String message_slowON_plu="lente";
//		
//		Node slowON=new Node(exslowON, message_slowON_plu, message_slowON_sin, message_slowON_plu, slowON_name, Vocabulary.valueClass);
//		slow.addRel(Vocabulary.value, slowON);
//		list.add(slowON);
//		
//		String[] exslowOFF={"non lent","ne' lent","nè lent"};
//		String slowOFF_name="false_slow";
//		
//		String message_slowOFF_sin="non lenta";
//		String message_slowOFF_plu="non lente";
//		
//		Node slowOFF=new Node(exslowOFF, message_slowOFF_plu, message_slowOFF_sin, message_slowOFF_plu, slowOFF_name, Vocabulary.valueClass);
//		slow.addRel(Vocabulary.value, slowOFF);
//		list.add(slowOFF);
//		
//		String[] exheavy= {};
//		String heavy_name = "heavy";
//
//		Node heavy = new Node(exheavy, message_null, message_null, message_null,
//				heavy_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, heavy);
//		list.add(heavy);
//		
//		machine.addInTemplate(heavy);
//		
//		String[] exheavyON={"pesant"};
//		String heavyON_name="true_heavy";
//		
//		String message_heavyON_sin="pesante";
//		String message_heavyON_plu="pesanti";
//		
//		Node heavyON=new Node(exheavyON, message_heavyON_plu, message_heavyON_sin, message_heavyON_plu, heavyON_name, Vocabulary.valueClass);
//		heavy.addRel(Vocabulary.value, heavyON);
//		list.add(heavyON);
//		
//		String[] exheavyOFF={"non pesant","ne' pesant","nè pensat"};
//		String heavyOFF_name="false_heavy";
//		
//		String message_heavyOFF_sin="non pesante";
//		String message_heavyOFF_plu="non pesanti";
//		
//		Node heavyOFF=new Node(exheavyOFF, message_heavyOFF_plu, message_heavyOFF_sin, message_heavyOFF_plu, heavyOFF_name, Vocabulary.valueClass);
//		heavy.addRel(Vocabulary.value, heavyOFF);
//		list.add(heavyOFF);
//		
//		String[] exlight= {};
//		String ligth_name = "light";
//
//		Node ligth = new Node(exlight, message_null, message_null, message_null,
//				ligth_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, ligth);
//		list.add(ligth);
//		
//		machine.addInTemplate(ligth);
//		
//		String[] exligthON={"legger"};
//		String ligthON_name="true_light";
//		
//		String message_ligthON_sin="leggera";
//		String message_ligthON_plu="leggere";
//		
//		Node ligthON=new Node(exligthON, message_ligthON_plu, message_ligthON_sin, message_ligthON_plu, ligthON_name, Vocabulary.valueClass);
//		ligth.addRel(Vocabulary.value, ligthON);
//		list.add(ligthON);
//		
//		String[] exligthOFF={"non legger","ne' legger","nè legger"};
//		String ligthOFF_name="false_light";
//		
//		String message_ligthOFF_sin="non leggera";
//		String message_ligthOFF_plu="non leggere";
//		
//		Node ligthOFF=new Node(exligthOFF, message_ligthOFF_plu, message_ligthOFF_sin, message_ligthOFF_plu, ligthOFF_name, Vocabulary.valueClass);
//		ligth.addRel(Vocabulary.value, ligthOFF);
//		list.add(ligthOFF);
//		
//		String[] exautoOFF= {};
//		String autoOFF_name = "autoOFF";
//
//		Node autoOFF = new Node(exautoOFF, message_null, message_null, message_null,
//				autoOFF_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, autoOFF);
//		list.add(autoOFF);
//		
//		machine.addInTemplate(autoOFF);
//		
//		String[] exautoOFFON={"spegnimento automatico","autospegnimento"};
//		String autoOFFON_name="true_autoOFF";
//		
//		String message_autoOFFON="con spegnimento automatico";
//		
//		Node autoOFFON=new Node(exautoOFFON, message_autoOFFON, message_autoOFFON, message_autoOFFON, autoOFFON_name, Vocabulary.valueClass);
//		autoOFF.addRel(Vocabulary.value, autoOFFON);
//		list.add(autoOFFON);
//		
//		String[] exautoOFFOFF={"senza spegnimento automatico"};
//		String autoOFFOFF_name="false_autoOFF";
//		
//		String message_autoOFFOFF="senza spegnimento automatico";
//		
//		Node autoOFFOFF=new Node(exautoOFFOFF, message_autoOFFOFF, message_autoOFFOFF, message_autoOFFOFF, autoOFFOFF_name, Vocabulary.valueClass);
//		autoOFF.addRel(Vocabulary.value, autoOFFOFF);
//		list.add(autoOFFOFF);
//		
//		String[] exProgrammable= {};
//		String programmable_name = "programmable";
//
//		Node programmable = new Node(exProgrammable, message_null, message_null, message_null,
//				programmable_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, programmable);
//		list.add(programmable);
//		
//		machine.addInTemplate(programmable);
//		
//		String[] exprogrammableON={"programmabil","programmare l'ora per fare il caffè"};
//		String programmableON_name="true_programmable";
//		
//		String message_programmableON="con erogazione programmabile";
//		
//		Node programmableON=new Node(exprogrammableON, message_programmableON, message_programmableON, message_programmableON, programmableON_name, Vocabulary.valueClass);
//		programmable.addRel(Vocabulary.value, programmableON);
//		list.add(programmableON);
//		
//		String[] exprogrammableOFF={"non programmabil","non programmare l’ora per fare il caffè"};
//		String programmableOFF_name="false_programmable";
//		
//		String message_programmableOFF="con erogazione non programmabile";
//		
//		Node programmableOFF=new Node(exprogrammableOFF, message_programmableOFF, message_programmableOFF, message_programmableOFF, programmableOFF_name, Vocabulary.valueClass);
//		programmable.addRel(Vocabulary.value, programmableOFF);
//		list.add(programmableOFF);
//		
//		String[] exThermoblock = {};
//		String thermoblock_name = "thermoblock";
//
//		Node thermoblock = new Node(exThermoblock, message_null, message_null, message_null,
//				thermoblock_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, thermoblock);
//		list.add(thermoblock);
//		
//		machine.addInTemplate(thermoblock);
//		
//		String[] exThermoblockON={"thermoblock","blocco termico"};
//		String thermoblockON_name="true_thermoblock";
//		
//		String message_thermoblockON="con thermoblock";
//		
//		Node thermoblockON=new Node(exThermoblockON, message_thermoblockON, message_thermoblockON, message_thermoblockON, thermoblockON_name, Vocabulary.valueClass);
//		thermoblock.addRel(Vocabulary.value, thermoblockON);
//		list.add(thermoblockON);
//		
//		String[] exThermoblockOFF={"senza thermoblock","prive di thermoblock","priva di thermoblock","senza blocco termico","prive di blocco termico","priva di blocco termico"};
//		String thermoblockOFF_name="false_thermoblock";
//		
//		String message_thermoblockOFF="senza thermoblock";
//		
//		Node thermoblockOFF=new Node(exThermoblockOFF, message_thermoblockOFF, message_thermoblockOFF, message_thermoblockOFF, thermoblockOFF_name, Vocabulary.valueClass);
//		thermoblock.addRel(Vocabulary.value, thermoblockOFF);
//		list.add(thermoblockOFF);
//		
//		String[] exmilkProgram = {};
//		String milkProgram_name = "milkProgram";
//
//		Node milkProgram = new Node(exmilkProgram, message_null, message_null, message_null,
//				milkProgram_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, milkProgram);
//		list.add(milkProgram);
//		
//		machine.addInTemplate(milkProgram);
//		
//		String[] exmilkProgramON={"cappuccin","latte","ricette latte","cose latte","vapore"};
//		String milkProgramON_name="true_milkProgram";
//		
//		String message_milkProgramON="con ricette latte";
//		
//		Node milkProgramON=new Node(exmilkProgramON, message_milkProgramON, message_milkProgramON, message_milkProgramON, milkProgramON_name, Vocabulary.valueClass);
//		milkProgram.addRel(Vocabulary.value, milkProgramON);
//		list.add(milkProgramON);
//		
//		String[] exmilkProgramOFF={"non fanno il cappuccino","senza ricette latte","senza vapore"};
//		String milkProgramOFF_name="false_milkProgram";
//		
//		String message_milkProgramOFF="senza ricette latte";
//		
//		Node milkProgramOFF=new Node(exmilkProgramOFF, message_milkProgramOFF, message_milkProgramOFF, message_milkProgramOFF, milkProgramOFF_name, Vocabulary.valueClass);
//		milkProgram.addRel(Vocabulary.value, milkProgramOFF);
//		list.add(milkProgramOFF);
//
//		String[] exColor = { "colore", "colorazione", "colori" };
//		String color_name = "color";
//
//		String message_color = "di colore";
//
//		// PROPERTY
//		Node color = new Node(exColor, message_color, message_color, message_color, color_name,
//				Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, color);
//		list.add(color);
//		machine.addInTemplate(color);
//
//		String[] exTank_capacity = { "capacità serbatoio", "acqua", "serbatoio" };
//		String tank_capacity_name = "tank capacity";
//
//		String message_tank = "con capacità serbatoio";
//
//		Node tank_capacity = new Node(exTank_capacity, message_tank, message_tank, message_tank, tank_capacity_name,
//				Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, tank_capacity);
//		list.add(tank_capacity);
//
//		machine.addInTemplate(tank_capacity);
//
//		String[] exTimeReady = { "tempo preparazione", "tempo per un caffè","caffè pronto","caffe pronto" };
//		String TimeReady_name = "time ready";
//
//		String message_time = "con tempo di preparazione";
//
//		Node TimeReady = new Node(exTimeReady, message_time, message_time, message_time, TimeReady_name,
//				Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, TimeReady);
//		list.add(TimeReady);
//
//		machine.addInTemplate(TimeReady);
//
//		String[] exWeight = { "pesa", "peso", "pesi" };
//		String Weight_name = "weight";
//
//		String message_weight = "con peso";
//
//		Node weight = new Node(exWeight, message_weight, message_weight, message_weight, Weight_name,
//				Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, weight);
//		list.add(weight);
//
//		machine.addInTemplate(weight);
//
//		String[] exNameMachine = { "chiama", "nome", "chiamano" };
//		String name_machine_name = "name machine";
//
//		String message_nameMachine = "dal nome";
//
//		Node name_machine = new Node(exNameMachine, message_nameMachine, message_nameMachine, message_nameMachine,
//				name_machine_name, Vocabulary.propertyClass);
//		machine.addRel(Vocabulary.hasA, name_machine);
//		list.add(name_machine);
//
//		machine.addInTemplate(name_machine);
//
//		// VALUE
//		// NAME
//		// 1
//		String[] exValueFantasia = { "fantasia", "electrolux fantasia" };
//		String fantasia_name = "fantasia";
//
//		Node fantasia = new Node(exValueFantasia, fantasia_name, fantasia_name, fantasia_name, fantasia_name,
//				Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, fantasia);
//		list.add(fantasia);
//
//		// 2
//		String[] exValueFantasiaPLUS = { "fantasia plus", "electrolux fantasia plus" };
//		String fantasiaPLUS_name = "fantasia plus";
//
//		Node fantasiaPLUS = new Node(exValueFantasiaPLUS, fantasiaPLUS_name, fantasiaPLUS_name, fantasiaPLUS_name,
//				fantasiaPLUS_name, Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, fantasiaPLUS);
//		list.add(fantasiaPLUS);
//
//		// 3
//		String[] exValueJolie = { "jolie", "lavazza jolie" };
//		String Jolie_name = "jolie";
//
//		Node Jolie = new Node(exValueJolie, Jolie_name, Jolie_name, Jolie_name, Jolie_name,
//				Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, Jolie);
//		list.add(Jolie);
//
//		// 4
//		String[] exValueMinu = { "minù", "lavazza minù", "minu", "lavazza minu" };
//		String minu_name = "minù";
//
//		Node minu = new Node(exValueMinu, minu_name, minu_name, minu_name, minu_name,
//				Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, minu);
//		list.add(minu);
//
//		// 5
//		String[] exValueMinuCaffeLatte = { "minù caffè latte", "lavazza minù caffè latte", "minu caffè latte",
//				"lavazza minu caffè latte" };
//		String minuCaffeLatte_name = "minù caffè latte";
//
//		Node minuCaffeLatte = new Node(exValueMinuCaffeLatte, minuCaffeLatte_name, minuCaffeLatte_name,
//				minuCaffeLatte_name, minuCaffeLatte_name, Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, minuCaffeLatte);
//		list.add(minuCaffeLatte);
//
//		// 6
//		String[] exValueMagiaplus = { "magia plus", "electrolux magia plus" };
//		String magiaplus_name = "magia plus";
//
//		Node magiaplus = new Node(exValueMagiaplus, magiaplus_name, magiaplus_name, magiaplus_name, magiaplus_name,
//				Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, magiaplus);
//		list.add(magiaplus);
//		// CASO PARTICOLARE
//		String[] exValueMagia = { "lavazza magia" };
//		String magia_name = "lavazza magia";
//
//		Node magia = new Node(exValueMagia, magia_name, magia_name, magia_name, magia_name,
//				Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, magia);
//		list.add(magia);
//
//		String[] exValueMagiaAmbiguo = { "magia" };
//		String magia_ambiguo_name = "magia";
//
//		Node magia_ambiguo = new Node(exValueMagiaAmbiguo, magia_ambiguo_name, magia_ambiguo_name, magia_ambiguo_name,
//				magia_ambiguo_name, Vocabulary.valueClass);
//		name_capsule.addRel(Vocabulary.value, magia_ambiguo);
//		name_machine.addRel(Vocabulary.value, magia_ambiguo);
//		list.add(magia_ambiguo);
//
//		// 7
//		String[] exValueMagiamachine = { "electrolux magia"};
//		String magiamachine_name = "electrolux magia";
//
//		Node magiamachine = new Node(exValueMagiamachine, magiamachine_name, magiamachine_name, magiamachine_name,
//				magiamachine_name, Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, magiamachine);
//		list.add(magiamachine);
//
//		// 8
//		String[] exValueEspriaPlus = { "espria plus", "electrolux espria plus" };
//		String espriaplus_name = "espria plus";
//
//		Node espriaplus = new Node(exValueEspriaPlus, espriaplus_name, espriaplus_name, espriaplus_name,
//				espriaplus_name, Vocabulary.valueClass);
//		name_machine.addRel(Vocabulary.value, espriaplus);
//		list.add(espriaplus);
//
//		// COLOR
//
//		String[] exValueOrange = { "aranc" };
//		String orange_name = "orange";
//
//		String message_orange = "arancione";
//
//		Node orange = new Node(exValueOrange, message_orange, message_orange, message_orange, orange_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, orange);
//		list.add(orange);
//
//		String[] exValueYellow = { "giall"};
//		String yellow_name = "yellow";
//
//		String message_yellow = "giallo";
//
//		Node yellow = new Node(exValueYellow, message_yellow, message_yellow, message_yellow, yellow_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, yellow);
//		list.add(yellow);
//
//		String[] exValueBlackColor = { "nero", "nera" };
//		String black_name = "black";
//
//		String message_blackcolor = "ner";
//
//		Node black = new Node(exValueBlackColor, message_blackcolor, message_blackcolor, message_blackcolor, black_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, black);
//		list.add(black);
//
//		String[] exValueCyan = { "cian" };
//		String cyan_name = "cyan";
//
//		String message_cyan = "ciano";
//
//		Node cyan = new Node(exValueCyan, message_cyan, message_cyan, message_cyan, cyan_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, cyan);
//		list.add(cyan);
//
//		String[] exValueGrey = { "grigi","acciaio" };
//		String grey_name = "grey";
//
//		String message_grey = "grigio";
//
//		Node grey = new Node(exValueGrey, message_grey, message_grey, message_grey, grey_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, grey);
//		list.add(grey);
//
//		String[] exValueLightBlue = { "azzurr" };
//		String lightBlue_name = "light blue";
//
//		String message_lightblue = "azzur";
//
//		Node lightBlue = new Node(exValueLightBlue, message_lightblue, message_lightblue, message_lightblue,
//				lightBlue_name, Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, lightBlue);
//		list.add(lightBlue);
//
//		String[] exValueWhite = { "bianc" };
//		String white_name = "white";
//
//		String message_white = "bianco";
//
//		Node white = new Node(exValueWhite, message_white, message_white, message_white, white_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, white);
//		list.add(white);
//
//		String[] exValueLime = { "lime", "verde acido", "verde limone" };
//		String lime_name = "lime";
//
//		String message_lime = "lime";
//
//		Node lime = new Node(exValueLime, message_lime, message_lime, message_lime, lime_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, lime);
//		list.add(lime);
//
//		String[] exValueRed = { "ross" };
//		String red_name = "red";
//
//		String message_red = "rosso";
//
//		Node red = new Node(exValueRed, message_red, message_red, message_red, red_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, red);
//		list.add(red);
//		
//		String[] exValueblue = { "blu" };
//		String blue_name = "blue";
//
//		String message_blue = "blu";
//
//		Node blue = new Node(exValueblue, message_blue, message_blue, message_blue, blue_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, blue);
//		list.add(blue);
//		
//		String[] exValuegreen = { "verd" };
//		String green_name = "green";
//
//		String message_green = "verde";
//
//		Node green = new Node(exValuegreen, message_green, message_green, message_green, green_name,
//				Vocabulary.valueClass);
//		color.addRel(Vocabulary.value, green);
//		list.add(green);
//
//		// Litri
//
//		String[] exLiters = { "litri", "litro" };
//		String liters_name = "liters";
//
//		String message_liters = "L";
//
//		Node liters = new Node(exLiters, message_liters, message_liters, message_liters, liters_name,
//				Vocabulary.unitClass);
//		tank_capacity.addRel(Vocabulary.unit, liters);
//		tank_capacity.addRel(Vocabulary.number, integer);
//		tank_capacity.addRel(Vocabulary.number, Float);
//		tank_capacity.addRel(Vocabulary.mod, less);
//		tank_capacity.addRel(Vocabulary.mod, more);
//		list.add(liters);
//
//		// secondi
//		String[] exSec = { "secondi", "secondo" };
//		String sec_name = "seconds";
//
//		String message_sec = "sec";
//
//		Node secondi = new Node(exSec, message_sec, message_sec, message_sec, sec_name,
//				Vocabulary.unitClass);
//		TimeReady.addRel(Vocabulary.unit, secondi);
//		TimeReady.addRel(Vocabulary.number, integer);
//		TimeReady.addRel(Vocabulary.mod, less);
//		TimeReady.addRel(Vocabulary.mod, more);
//		list.add(secondi);
//
//		// Kilogrammi
//		String[] exKG = { "kili", "kilo", "kilogrammi", "kilogrammo", "kg" };
//		String KG_name = "kg";
//
//		Node kg = new Node(exKG, KG_name, KG_name, KG_name, KG_name, Vocabulary.unitClass);
//		weight.addRel(Vocabulary.unit, kg);
//		weight.addRel(Vocabulary.number, integer);
//		weight.addRel(Vocabulary.number, Float);
//		weight.addRel(Vocabulary.mod, less);
//		weight.addRel(Vocabulary.mod, more);
//		list.add(kg);
//
//		String[] exBrand = { "marca", "azienda","marche" };
//		String brand_name = "brand";
//
//		String message_brand = "di marca";
//
//		Node brand = new Node(exBrand, message_brand, message_brand, message_brand, brand_name,
//				Vocabulary.propertyClass);
//		capsule.addRel(Vocabulary.hasA, brand);
//		machine.addRel(Vocabulary.hasA, brand);
//		list.add(brand);
//
//		machine.addInTemplate(brand);
//		capsule.addInTemplate(brand);
//
//		String[] exNespresso = { "nespresso" };
//		String nespresso_name = "nespresso";
//
//		Node nespresso = new Node(exNespresso, nespresso_name, nespresso_name, nespresso_name, nespresso_name,
//				Vocabulary.valueClass);
//		brand.addRel(Vocabulary.value, nespresso);
//		list.add(nespresso);
//
//		String[] exLavazza = { "lavazza" };
//		String lavazza_name = "lavazza";
//
//		Node lavazza = new Node(exLavazza, lavazza_name, lavazza_name, lavazza_name, lavazza_name,
//				Vocabulary.valueClass);
//		brand.addRel(Vocabulary.value, lavazza);
//		list.add(lavazza);
//
//		String[] exelectrolux = { "electrolux" };
//		String electrolux_name = "electrolux";
//
//		Node electrolux = new Node(exelectrolux, electrolux_name, electrolux_name, electrolux_name, electrolux_name,
//				Vocabulary.valueClass);
//		brand.addRel(Vocabulary.value, electrolux);
//		list.add(electrolux);
//		
//		String[] exOther = { "altre marche","non lavazza"};
//		String other_name = "other_brand";
//		
//		String other_message="diversa da lavazza";
//
//		Node other = new Node(exOther, other_message, other_message, other_message,other_name,
//				Vocabulary.valueClass);
//		brand.addRel(Vocabulary.value, other);
//		list.add(other);
//		
////
////		String[] exall = { "tutte"};
////		String all_name = "all";
////		
////		String all_message="qualsiasi";
////
////		Node all = new Node(exall, all_message, all_message, all_message,all_name,
////				Vocabulary.valueClass);
////		brand.addRel(Vocabulary.value, all);
////		list.add(all);
//		
//		String[] excaffitaly = { "caffitaly"};
//		String caffitaly_name = "caffitaly";
//		
//		String caffitaly_message="caffitaly";
//
//		Node caffitaly = new Node(excaffitaly, caffitaly_message, caffitaly_message, caffitaly_message,caffitaly_name,
//				Vocabulary.valueClass);
//		brand.addRel(Vocabulary.value, caffitaly);
//		list.add(caffitaly);
//
//		machine.addInTemplate(features);
//		machine.addInTemplate(compatible);
//
//		capsule.addInTemplate(features);
//		capsule.addInTemplate(compatible);
//
//		// create an empty Model
//		OntModel model = Vocabulary.define();
//
//		for (Node n : list) {
//			Resource now = model.createResource("http://eu.reply.it/" + n.name)
//					.addProperty(Vocabulary.name, n.name).addProperty(RDF.type, n.rdf_semClass);
//			for (String ex : n.example) {
//				now.addProperty(Vocabulary.example, ex);
//			}
//			now.addProperty(Vocabulary.message_singular, n.singular);
//			now.addProperty(Vocabulary.message_plural, n.plural);
//			now.addProperty(Vocabulary.message_not, n.not);
//			n.addResource(now);
//		}
//
//		for (Node n : list) {
//			HashMap<Node, Property> rel = n.rel;
//			for (Node rel_n : rel.keySet()) {
//				Resource res = rel_n.rdf;
//				Property prop = rel.get(rel_n);
//				n.rdf.addProperty(prop, res);
//			}
//
//			RDFNode[] rdf_list = n.getRDFTemplate();
//			if (rdf_list.length > 0) {
//				n.rdf.addProperty(Vocabulary.template, model.createList(rdf_list));
//			}
//		}
//
//		// System.out.println("----------------Turtle-------------------");
//		// model.write(System.out, "Turtle");
//		System.out.println("----------------XML----------------------");
//		model.write(System.out,format);
//		try {
//			model.write(new FileOutputStream(url), format);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
