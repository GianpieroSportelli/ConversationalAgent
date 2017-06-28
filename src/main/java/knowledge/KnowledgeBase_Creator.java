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

//		// BEGIN------Intenzioni Sociali
//		String[] exGreetings = { "ciao", "buongiorno", "buonasera", "buonanotte", "i miei ossequi", "buondì", "buondi",
//				"ehi", "welà", "wela", "we we", "salve", "compari", "hola", "gutentag", "trmo", "trmò", "trimone",
//				"buoneos dias", "buena noche", "scemo", "buon pomeriggio" };
//		String greetings_name = "greetings";
//
//		String message_not_greetings = "Hola";
//		String message_null = "";
//
//		Node greetings = new Node(exGreetings, message_not_greetings, message_null, message_null, greetings_name,
//				Ontology.dialogClass);
//		list.add(greetings);
//
//		String[] excreator_info = { "chi essere il tuo creatore", "tuo creatore", "chi ti avere fare",
//				"chi ti avere creare" };
//		String creator_info_name = "creator_info";
//
//		String message_not_creator_info = "Sono stato creato da Gianpiero Sportelli.";
//
//		Node creator_info = new Node(excreator_info, message_not_creator_info, message_null, message_null,
//				creator_info_name, Ontology.dialogClass);
//		list.add(creator_info);
//
//		String[] exname_info = { "come ti chiamare", "come ti potere chiamare", "quale essere il tuo nome", "tu essere",
//				"chi essere" };
//		String name_info_name = "name_info";
//
//		String message_not_name_info = "mi chiamo 1+1=3. sono timido non farmi altre domande...";
//
//		Node name_info = new Node(exname_info, message_not_name_info, message_null, message_null, name_info_name,
//				Ontology.dialogClass);
//		list.add(name_info);
//
//		String[] exthanks = { "grazie", "grazia" };
//		String thanks_name = "thanks";
//
//		String message_thanks = "you're welcome!!";
//
//		Node thanks = new Node(exthanks, message_thanks, message_null, message_null, thanks_name, Ontology.dialogClass);
//		list.add(thanks);
//
//		String[] exwell = { "come stare", "come andare" };
//		String well_name = "well";
//
//		String message_well = "bene bene, sto cercando di capire come parlate voi umani...";
//
//		Node well = new Node(exwell, message_well, message_null, message_null, well_name, Ontology.dialogClass);
//		list.add(well);
//
//		String[] exwell_done = {};
//		String well_done_name = "well_done";
//
//		String message_well_done = "tu?";
//
//		Node well_done = new Node(exwell_done, message_well_done, message_null, message_null, well_done_name,
//				Ontology.dialogClass);
//		list.add(well_done);
//
//		String[] exwell_response = { "bene grazie", "bene", "bene grazia", " io pure", "io anche", "andare be",
//				"andare bene" };
//		String well_response_name = "well_response";
//
//		String message_well_response = "grande! allora continuiamo :)";
//
//		Node well_response = new Node(exwell_response, message_well_response, message_null, message_null,
//				well_response_name, Ontology.dialogClass);
//		list.add(well_response);
//
//		String[] exok = { "ok", "va bene", "perfetto", "accordo" };
//		String ok_name = "okNode";
//
//		String message_ok = ":)";
//
//		Node ok = new Node(exok, message_ok, ok_name, Ontology.dialogClass);
//		list.add(ok);
//
//		String[] exaddio = { "addio", "a mai più" };
//		String addio_name = "addioNode";
//
//		String message_addio = "addio per sempre...";
//
//		Node addio = new Node(exaddio, message_addio, addio_name, Ontology.dialogClass);
//		list.add(addio);
//
//		String[] exscongiuro = { "non te la prendere", "no dai", "scherzare" };
//		String scongiuro_name = "scongiuroNode";
//
//		String message_scongiuro = "amici come prima :)";
//
//		Node scongiuro = new Node(exscongiuro, message_scongiuro, scongiuro_name, Ontology.dialogClass);
//		list.add(scongiuro);
//
//		String[] exname_name = {};
//		String name_name_question = "name_question";
//
//		String message_not_name_question = "come posso chiamarti?";
//
//		Node name_name = new Node(exname_name, message_not_name_question, message_null, message_null,
//				name_name_question, Ontology.dialogClass);
//		list.add(name_name);
//
//		String[] excapabilities = { "cosa potere fare", "cosa fare", "quale essere la tua funzione" };
//		String capabilities_name = "capabilities";
//
//		String message_capabilities = "Posso aiutarti a a cercare capsule e macchine da caffè di marca lavazza, chiedimi pure :)";
//
//		Node capabilities = new Node(excapabilities, message_capabilities, capabilities_name, Ontology.dialogClass);
//		list.add(capabilities);
//
//		String[] exNoAnswer = {};
//		String noAnswer_name = "noAnswer";
//
//		String message_noAnswer = "";
//
//		Node noAnswer = new Node(exNoAnswer, message_noAnswer, message_null, message_null, noAnswer_name,
//				Ontology.noAnswer);
//		list.add(noAnswer);
//
//		// END------Intenzioni Sociali
//
//		// BEGIN-----RISPOSTE
//
//		// END------RISPOSTE
//
//		// BEGIN------SPECHACT
//		String speechActInsertName = "insert";
//		Node speechActInsert = new Node(message_null, speechActInsertName, Ontology.speechActClass);
//		list.add(speechActInsert);
//
//		String[] speechActQuantifiesEx = { "quanto", "dimmi" };
//		String speechActQuantifiesName = "quantifies";
//		Node speechActQuantifies = new Node(speechActQuantifiesEx, "", speechActQuantifiesName,
//				Ontology.speechActClass);
//		list.add(speechActQuantifies);
//
//		String[] speechActQuestionEx = { "quale", "?" };
//		String speechActQuestionName = "question";
//		Node speechActQuestion = new Node(speechActQuestionEx, message_null, speechActQuestionName,
//				Ontology.speechActClass);
//		list.add(speechActQuestion);
//
//		String[] speechActHowEx = { "come" };
//		String speechActHowName = "how";
//		Node speechActHow = new Node(speechActHowEx, message_null, speechActHowName, Ontology.speechActClass);
//		list.add(speechActHow);
//
//		String[] speechActWhyEx = { "perchè", "perché", "perche" };
//		String speechActWhyName = "why";
//		Node speechActWhy = new Node(speechActWhyEx, message_null, speechActWhyName, Ontology.speechActClass);
//		list.add(speechActWhy);
//
//		String[] speechActWhatEx = { "cosa" };
//		String speechActWhatName = "what";
//		Node speechActWhat = new Node(speechActWhatEx, message_null, speechActWhatName, Ontology.speechActClass);
//		list.add(speechActWhat);
//
//		// END------SPEECHACT
//
//		// BEGIN------PLAN
//		String name_plan_info = "plan.InfoPlan";
//		Node plan_info = new Node(message_null, name_plan_info, Ontology.planClass);
//		name_info.addRel(Ontology.plan, plan_info);
//		list.add(plan_info);
//
//		String well_plan_info = "plan.WellPlan";
//		Node well_plan = new Node(message_null, well_plan_info, Ontology.planClass);
//		well.addRel(Ontology.plan, well_plan);
//		list.add(well_plan);
//
//		// END------PLAN
//
//		// BEGIN----KNOLEDGE BASE

//		// BEGIN----ACTION
//		String search_name = "search";
//		String message_search = "stai cercando";
//		Node search = new Node(message_search, search_name, Ontology.actionClass);
//		list.add(search);
//
//		// //END----ACTION
//		//
//		// //BEGIN---DOMAIN OBJECT
//
//		String[] ex_capsule = { "capsula", "capsule", "cialda", "caffè" };
//		String capsule_name = "capsule";
//		String message_capsule = "capsule";
//		Node capsule = new Node(ex_capsule, message_capsule, capsule_name, Ontology.domainClass);
//		search.addRel(Ontology.oneToOne, capsule);
//		capsule.addRel(Ontology._default, search);
//		list.add(capsule);
//
//		String[] ex_machine = { "macchina da caffè", "macchina caffè" };
//		String name_machine_domain = "machine";
//		String message_machine = "machine";
//		Node machine = new Node(ex_machine, message_machine, name_machine_domain, Ontology.domainClass);
//		search.addRel(Ontology.oneToOne, machine);
//		machine.addRel(Ontology._default, search);
//		list.add(machine);
//
//		// END----DOMAIN OBJECT
//
//		// BEGIN----PROPERTY
//		String[] exbill = { "prezzo", "costo", "budget", "budjet", "importo" };
//		String bill_name = "bill";
//
//		String message_bill = "importo";
//
//		Node bill = new Node(exbill, message_bill, message_bill, message_bill, bill_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, bill);
//		machine.addRel(Ontology.oneToMany, bill);
//		list.add(bill);
//
//		capsule.addInTemplate(bill);
//		machine.addInTemplate(bill);
//
//		String[] exRoasting = { "tostatura", "tostato" };
//		String roasting_name = "roasting";
//
//		String message_roasting = "con tostatura";
//
//		Node roasting = new Node(exRoasting, message_roasting, roasting_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, roasting);
//		list.add(roasting);
//		capsule.addInTemplate(roasting);
//
//		String cheap_name = "cheap";
//
//		Node cheap = new Node(message_null, cheap_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, cheap);
//		list.add(cheap);
//
//		capsule.addInTemplate(cheap);
//
//		String expensive_name = "expensive";
//
//		Node expensive = new Node(message_null, expensive_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, expensive);
//		list.add(expensive);
//
//		capsule.addInTemplate(expensive);
//
//		String promo_name = "promo";
//
//		Node promo = new Node(message_null, promo_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, promo);
//		list.add(promo);
//
//		capsule.addInTemplate(promo);
//
//		String Dec_name = "dec";
//
//		Node Dec = new Node(message_null, Dec_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, Dec);
//		list.add(Dec);
//
//		capsule.addInTemplate(Dec);
//
//		String bio_name = "bio";
//
//		Node bio = new Node(message_null, bio_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, bio);
//		list.add(bio);
//
//		capsule.addInTemplate(bio);
//
//		String[] exComposition = { "contiene", "miscela", "gusto", "composto", "composizione" };
//		String composition_name = "composition";
//
//		String message_composition_plu = "composte da";
//		String message_composition_sin = "composta da";
//
//		Node composition = new Node(exComposition, message_composition_plu, message_composition_sin,
//				message_composition_plu, composition_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, composition);
//		list.add(composition);
//
//		capsule.addInTemplate(composition);
//
//		String[] exNameCapsule = { "chiama", "nome", "chiamano" };
//		String name_capsule_name = "name_capsule";
//
//		String message_name = "dal nome";
//
//		Node name_capsule = new Node(exNameCapsule, message_name, name_capsule_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, name_capsule);
//		list.add(name_capsule);
//
//		capsule.addInTemplate(name_capsule);
//
//		String[] exQuantity = { "scatola", "confezione", "pacco", "formato" };
//		String quantity_name = "quantity";
//
//		String message_quantity = "con dimensione scatola";
//
//		Node quantity = new Node(exQuantity, message_quantity, quantity_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, quantity);
//		list.add(quantity);
//
//		capsule.addInTemplate(quantity);
//
//		String[] exIntensity = { "intenso", "intensità" };
//		String intensity_name = "intensity";
//
//		String message_intensity = "con intensità";
//
//		Node intensity = new Node(exIntensity, message_intensity, message_intensity, message_intensity, intensity_name,
//				Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, intensity);
//		list.add(intensity);
//
//		capsule.addInTemplate(intensity);
//
//		machine.addRel(Ontology.oneToMany, promo);
//		machine.addRel(Ontology.oneToMany, cheap);
//		machine.addRel(Ontology.oneToMany, expensive);
//		machine.addInTemplate(promo);
//		machine.addInTemplate(cheap);
//		machine.addInTemplate(expensive);
//
//		String fast_name = "fast";
//
//		Node fast = new Node(message_null, fast_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, fast);
//		list.add(fast);
//
//		machine.addInTemplate(fast);
//
//		String slow_name = "slow";
//
//		Node slow = new Node(message_null, slow_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, slow);
//		list.add(slow);
//
//		machine.addInTemplate(slow);
//
//		String heavy_name = "heavy";
//
//		Node heavy = new Node(message_null, heavy_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, heavy);
//		list.add(heavy);
//
//		machine.addInTemplate(heavy);
//
//		String ligth_name = "light";
//
//		Node ligth = new Node(message_null, ligth_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, ligth);
//		list.add(ligth);
//
//		machine.addInTemplate(ligth);
//
//		String autoOFF_name = "autoOFF";
//
//		Node autoOFF = new Node(message_null, autoOFF_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, autoOFF);
//		list.add(autoOFF);
//
//		machine.addInTemplate(autoOFF);
//
//		String programmable_name = "programmable";
//
//		Node programmable = new Node(message_null, programmable_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, programmable);
//		list.add(programmable);
//
//		machine.addInTemplate(programmable);
//
//		String thermoblock_name = "thermoblock";
//
//		Node thermoblock = new Node(message_null, thermoblock_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, thermoblock);
//		list.add(thermoblock);
//
//		machine.addInTemplate(thermoblock);
//
//		String milkProgram_name = "milkProgram";
//
//		Node milkProgram = new Node(message_null, milkProgram_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, milkProgram);
//		list.add(milkProgram);
//
//		machine.addInTemplate(milkProgram);
//
//		String[] exColor = { "colore", "colorazione" };
//		String color_name = "color";
//
//		String message_color = "di colore";
//
//		Node color = new Node(exColor, message_color, color_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, color);
//		list.add(color);
//		machine.addInTemplate(color);
//
//		String[] exTank_capacity = { "capacità serbatoio", "acqua", "serbatoio" };
//		String tank_capacity_name = "tank_capacity";
//
//		String message_tank = "con capacità serbatoio";
//
//		Node tank_capacity = new Node(exTank_capacity, message_tank, tank_capacity_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, tank_capacity);
//		list.add(tank_capacity);
//
//		machine.addInTemplate(tank_capacity);
//
//		String[] exTimeReady = { "tempo preparazione", "tempo per un caffè", "caffè pronto", "caffe pronto" };
//		String TimeReady_name = "time_ready";
//
//		String message_time = "con tempo di preparazione";
//
//		Node TimeReady = new Node(exTimeReady, message_time, TimeReady_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, TimeReady);
//		list.add(TimeReady);
//
//		machine.addInTemplate(TimeReady);
//
//		String[] exWeight = { "peso" };
//		String Weight_name = "weight";
//
//		String message_weight = "con peso";
//
//		Node weight = new Node(exWeight, message_weight, Weight_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, weight);
//		list.add(weight);
//
//		machine.addInTemplate(weight);
//
//		String[] exNameMachine = { "chiama", "nome", "chiamare" };
//		String name_machine_name = "name_machine";
//
//		String message_nameMachine = "dal nome";
//
//		Node name_machine = new Node(exNameMachine, message_nameMachine, name_machine_name, Ontology.propertyClass);
//		machine.addRel(Ontology.oneToMany, name_machine);
//		list.add(name_machine);
//
//		machine.addInTemplate(name_machine);
//
//		String[] exBrand = { "marca", "azienda", "marchio" };
//		String brand_name = "brand";
//
//		String message_brand = "di marca";
//
//		Node brand = new Node(exBrand, message_brand, brand_name, Ontology.propertyClass);
//		capsule.addRel(Ontology.oneToMany, brand);
//		machine.addRel(Ontology.oneToMany, brand);
//		list.add(brand);
//		
//		machine.addInTemplate(brand);
//		capsule.addInTemplate(brand);
//
//		// END----PROPERTY
//
//		String[] exInteger = {};
//		String Integer_name = "integer";
//		String message_token = "{value:token}";
//
//		Node integer = new Node(exInteger, message_token, Integer_name, Ontology.numberClass);
//		list.add(integer);
//
//		String[] exFloat = {};
//		String Float_name = "float";
//
//		Node Float = new Node(exFloat, message_token, Float_name, Ontology.numberClass);
//		list.add(Float);
//
//		bill.addRel(Ontology.oneToOne, integer);
//		bill.addRel(Ontology.oneToOne, Float);
//
//		quantity.addRel(Ontology.oneToOne, Float);
//		quantity.addRel(Ontology.oneToOne, integer);
//
//		intensity.addRel(Ontology.oneToOne, Float);
//		intensity.addRel(Ontology.oneToOne, integer);
//
//		String[] exMore = { "superiore", "più", "non inferiore", "maggiore", "sopra", "minimo" };
//		String more_name = "more";
//
//		String message_more = "maggiore di";
//
//		Node more = new Node(exMore, message_more, message_more, message_more, more_name, Ontology.modClass);
//		bill.addRel(Ontology.oneToStar, more);
//		quantity.addRel(Ontology.oneToStar, more);
//		intensity.addRel(Ontology.oneToStar, more);
//		list.add(more);
//
//		String[] exLess = { "inferiore", "meno", "non superiore", "minore", "sotto", "massimo" };
//		String less_name = "less";
//
//		String message_less = "minore di";
//
//		Node less = new Node(exLess, message_less, message_less, message_less, less_name, Ontology.modClass);
//		bill.addRel(Ontology.oneToStar, less);
//		quantity.addRel(Ontology.oneToStar, less);
//		intensity.addRel(Ontology.oneToStar, less);
//		list.add(less);
//
//		// BEGIN--LITERAL
//		
//		String[] exNespresso = { "nespresso" };
//		String nespresso_name = "nespresso";
//
//		Node nespresso = new Node(exNespresso, nespresso_name, nespresso_name,Ontology.valueClass);
//		brand.addRel(Ontology.oneToOne, nespresso);
//		list.add(nespresso);
//
//		String[] exLavazza = { "lavazza" };
//		String lavazza_name = "lavazza";
//
//		Node lavazza = new Node(exLavazza, lavazza_name, lavazza_name,Ontology.valueClass);
//		brand.addRel(Ontology.oneToOne, lavazza);
//		list.add(lavazza);
//
//		String[] exelectrolux = { "electrolux" };
//		String electrolux_name = "electrolux";
//
//		Node electrolux = new Node(exelectrolux, electrolux_name, electrolux_name,Ontology.valueClass);
//		brand.addRel(Ontology.oneToOne, electrolux);
//		list.add(electrolux);
//		
//		String[] exOther = { "altro marca","non lavazza"};
//		String other_name = "other_brand";
//		
//		String other_message="diversa da lavazza";
//
//		Node other = new Node(exOther, other_message, other_message,Ontology.valueClass);
//		brand.addRel(Ontology.oneToOne, other);
//		list.add(other);
//		
////
////		String[] exall = { "tutte"};
////		String all_name = "all";
////		
////		String all_message="qualsiasi";
////
////		Node all = new Node(exall, all_message, all_message, all_message,all_name,
////				Ontology.valueClass);
////		brand.addRel(Ontology.value, all);
////		list.add(all);
//		
//		String[] excaffitaly = { "caffitaly"};
//		String caffitaly_name = "caffitaly";
//		
//		String caffitaly_message="caffitaly";
//
//		Node caffitaly = new Node(excaffitaly, caffitaly_message,caffitaly_name,Ontology.valueClass);
//		brand.addRel(Ontology.oneToOne, caffitaly);
//		list.add(caffitaly);
//
//		// 1
//		String[] exoneToOneFantasia = { "fantasia", "electrolux fantasia" };
//		String fantasia_name = "fantasia";
//
//		Node fantasia = new Node(exoneToOneFantasia, fantasia_name, fantasia_name, Ontology.valueClass);
//		name_machine.addRel(Ontology.oneToOne, fantasia);
//		list.add(fantasia);
//
//		// 2
//		String[] exoneToOneFantasiaPLUS = { "fantasia plus", "electrolux fantasia plus" };
//		String fantasiaPLUS_name = "fantasia plus";
//
//		Node fantasiaPLUS = new Node(exoneToOneFantasiaPLUS, fantasiaPLUS_name, fantasiaPLUS_name, fantasiaPLUS_name,
//				fantasiaPLUS_name, Ontology.valueClass);
//		name_machine.addRel(Ontology.oneToOne, fantasiaPLUS);
//		list.add(fantasiaPLUS);
//
//		// 3
//		String[] exoneToOneJolie = { "jolie", "lavazza jolie" };
//		String Jolie_name = "jolie";
//
//		Node Jolie = new Node(exoneToOneJolie, Jolie_name, Jolie_name, Jolie_name, Jolie_name, Ontology.valueClass);
//		name_machine.addRel(Ontology.oneToOne, Jolie);
//		list.add(Jolie);
//
//		// 4
//		String[] exoneToOneMinu = { "minù", "lavazza minù", "minu", "lavazza minu" };
//		String minu_name = "minù";
//
//		Node minu = new Node(exoneToOneMinu, minu_name, minu_name, minu_name, minu_name, Ontology.valueClass);
//		name_machine.addRel(Ontology.oneToOne, minu);
//		list.add(minu);
//
//		// 5
//		String[] exoneToOneMinuCaffeLatte = { "minù caffè latte", "lavazza minù caffè latte", "minu caffè latte",
//				"lavazza minu caffè latte" };
//		String minuCaffeLatte_name = "minù caffè latte";
//
//		Node minuCaffeLatte = new Node(exoneToOneMinuCaffeLatte, minuCaffeLatte_name, minuCaffeLatte_name,
//				minuCaffeLatte_name, minuCaffeLatte_name, Ontology.valueClass);
//		name_machine.addRel(Ontology.oneToOne, minuCaffeLatte);
//		list.add(minuCaffeLatte);
//
//		// 6
//		String[] exoneToOneMagiaplus = { "magia plus", "electrolux magia plus" };
//		String magiaplus_name = "magia plus";
//
//		Node magiaplus = new Node(exoneToOneMagiaplus, magiaplus_name, magiaplus_name, magiaplus_name, magiaplus_name,
//				Ontology.valueClass);
//		name_machine.addRel(Ontology.oneToOne, magiaplus);
//		list.add(magiaplus);
//		// // CASO PARTICOLARE
//		// String[] exoneToOneMagia = { "lavazza magia" };
//		// String magia_name = "lavazza magia";
//		//
//		// Node magia = new Node(exoneToOneMagia, magia_name, magia_name,
//		// magia_name, magia_name,
//		// Ontology.valueClass);
//		// name_capsule.addRel(Ontology.oneToOne, magia);
//		// list.add(magia);
//		//
//		// String[] exoneToOneMagiaAmbiguo = { "magia" };
//		// String magia_ambiguo_name = "magia";
//		//
//		// Node magia_ambiguo = new Node(exoneToOneMagiaAmbiguo,
//		// magia_ambiguo_name, magia_ambiguo_name, magia_ambiguo_name,
//		// magia_ambiguo_name, Ontology.valueClass);
//		// name_capsule.addRel(Ontology.oneToOne, magia_ambiguo);
//		// name_machine.addRel(Ontology.oneToOne, magia_ambiguo);
//		// list.add(magia_ambiguo);
//		//
//		// // 7
//		// String[] exoneToOneMagiamachine = { "electrolux magia"};
//		// String magiamachine_name = "electrolux magia";
//		//
//		// Node magiamachine = new Node(exoneToOneMagiamachine,
//		// magiamachine_name, magiamachine_name, magiamachine_name,
//		// magiamachine_name, Ontology.valueClass);
//		// name_machine.addRel(Ontology.oneToOne, magiamachine);
//		// list.add(magiamachine);
//		//
//		// 8
//		String[] exoneToOneEspriaPlus = { "espria plus", "electrolux espria plus" };
//		String espriaplus_name = "espria plus";
//
//		Node espriaplus = new Node(exoneToOneEspriaPlus, espriaplus_name, espriaplus_name, espriaplus_name,
//				espriaplus_name, Ontology.valueClass);
//		name_machine.addRel(Ontology.oneToOne, espriaplus);
//		list.add(espriaplus);
//
//		// COLOR
//
//		String[] exoneToOneOrange = { "arancione", "arancio" };
//		String orange_name = "orange";
//
//		String message_orange = "arancione";
//
//		Node orange = new Node(exoneToOneOrange, message_orange, message_orange, message_orange, orange_name,
//				Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, orange);
//		list.add(orange);
//
//		String[] exoneToOneYellow = { "giallo" };
//		String yellow_name = "yellow";
//
//		String message_yellow = "giallo";
//
//		Node yellow = new Node(exoneToOneYellow, message_yellow, message_yellow, message_yellow, yellow_name,
//				Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, yellow);
//		list.add(yellow);
//
//		String[] exoneToOneBlackColor = { "nero" };
//		String black_name = "black";
//
//		String message_blackcolor = "ner";
//
//		Node black = new Node(exoneToOneBlackColor, message_blackcolor, message_blackcolor, message_blackcolor,
//				black_name, Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, black);
//		list.add(black);
//
//		String[] exoneToOneCyan = { "ciano" };
//		String cyan_name = "cyan";
//
//		String message_cyan = "ciano";
//
//		Node cyan = new Node(exoneToOneCyan, message_cyan, message_cyan, message_cyan, cyan_name, Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, cyan);
//		list.add(cyan);
//
//		String[] exoneToOneGrey = { "grigio", "acciaio" };
//		String grey_name = "grey";
//
//		String message_grey = "grigio";
//
//		Node grey = new Node(exoneToOneGrey, message_grey, message_grey, message_grey, grey_name, Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, grey);
//		list.add(grey);
//
//		String[] exoneToOneLightBlue = { "azzurro" };
//		String lightBlue_name = "light blue";
//
//		String message_lightblue = "azzur";
//
//		Node lightBlue = new Node(exoneToOneLightBlue, message_lightblue, message_lightblue, message_lightblue,
//				lightBlue_name, Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, lightBlue);
//		list.add(lightBlue);
//
//		String[] exoneToOneWhite = { "bianco" };
//		String white_name = "white";
//
//		String message_white = "bianco";
//
//		Node white = new Node(exoneToOneWhite, message_white, message_white, message_white, white_name,
//				Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, white);
//		list.add(white);
//
//		String[] exoneToOneLime = { "lime", "verde acido", "verde limone" };
//		String lime_name = "lime";
//
//		String message_lime = "lime";
//
//		Node lime = new Node(exoneToOneLime, message_lime, message_lime, message_lime, lime_name, Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, lime);
//		list.add(lime);
//
//		String[] exoneToOneRed = { "rosso" };
//		String red_name = "red";
//
//		String message_red = "rosso";
//
//		Node red = new Node(exoneToOneRed, message_red, message_red, message_red, red_name, Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, red);
//		list.add(red);
//
//		String[] exoneToOneblue = { "blu" };
//		String blue_name = "blue";
//
//		String message_blue = "blu";
//
//		Node blue = new Node(exoneToOneblue, message_blue, message_blue, message_blue, blue_name, Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, blue);
//		list.add(blue);
//
//		String[] exoneToOnegreen = { "verde" };
//		String green_name = "green";
//
//		String message_green = "verde";
//
//		Node green = new Node(exoneToOnegreen, message_green, message_green, message_green, green_name,
//				Ontology.valueClass);
//		color.addRel(Ontology.oneToOne, green);
//		list.add(green);
//
//		// Litri
//
//		String[] exLiters = { "litro" };
//		String liters_name = "liters";
//
//		String message_liters = "L";
//
//		Node liters = new Node(exLiters, message_liters, message_liters, message_liters, liters_name,
//				Ontology.unitClass);
//		tank_capacity.addRel(Ontology.oneToOne, liters);
//		tank_capacity.addRel(Ontology.oneToOne, integer);
//		tank_capacity.addRel(Ontology.oneToOne, Float);
//		tank_capacity.addRel(Ontology.oneToStar, less);
//		tank_capacity.addRel(Ontology.oneToStar, more);
//		list.add(liters);
//
//		// secondi
//		String[] exSec = { "secondi", "secondo" };
//		String sec_name = "seconds";
//
//		String message_sec = "sec";
//
//		Node secondi = new Node(exSec, message_sec, message_sec, message_sec, sec_name, Ontology.unitClass);
//		TimeReady.addRel(Ontology.oneToOne, secondi);
//		TimeReady.addRel(Ontology.oneToOne, integer);
//		TimeReady.addRel(Ontology.oneToStar, less);
//		TimeReady.addRel(Ontology.oneToStar, more);
//		list.add(secondi);
//
//		// Kilogrammi
//		String[] exKG = { "kili", "kilo", "kilogrammi", "kilogrammo", "kg" };
//		String KG_name = "kg";
//
//		Node kg = new Node(exKG, KG_name, KG_name, KG_name, KG_name, Ontology.unitClass);
//		weight.addRel(Ontology.oneToOne, kg);
//		weight.addRel(Ontology.oneToOne, integer);
//		weight.addRel(Ontology.oneToOne, Float);
//		weight.addRel(Ontology.oneToStar, less);
//		weight.addRel(Ontology.oneToStar, more);
//		list.add(kg);
//
//		String[] exmilkProgramON = { "cappuccino", "latte", "ricette latte", "cose latte", "vapore" };
//		String milkProgramON_name = "true_milkProgram";
//
//		String message_milkProgramON = "con ricette latte";
//
//		Node milkProgramON = new Node(exmilkProgramON, message_milkProgramON, milkProgramON_name, Ontology.valueClass);
//		milkProgram.addRel(Ontology.oneToOne, milkProgramON);
//		list.add(milkProgramON);
//
//		String[] exmilkProgramOFF = { "non fare il cappuccino", "senza ricetta latte", "senza vapore" };
//		String milkProgramOFF_name = "false_milkProgram";
//
//		String message_milkProgramOFF = "senza ricette latte";
//
//		Node milkProgramOFF = new Node(exmilkProgramOFF, message_milkProgramOFF, milkProgramOFF_name,
//				Ontology.valueClass);
//		milkProgram.addRel(Ontology.oneToOne, milkProgramOFF);
//		list.add(milkProgramOFF);
//
//		String[] exThermoblockON = { "thermoblock", "blocco termico" };
//		String thermoblockON_name = "true_thermoblock";
//
//		String message_thermoblockON = "con thermoblock";
//
//		Node thermoblockON = new Node(exThermoblockON, message_thermoblockON, thermoblockON_name, Ontology.valueClass);
//		thermoblock.addRel(Ontology.oneToOne, thermoblockON);
//		list.add(thermoblockON);
//
//		String[] exThermoblockOFF = { "senza thermoblock", "privo di thermoblock", "senza blocco termico",
//				"privo di blocco termico" };
//		String thermoblockOFF_name = "false_thermoblock";
//
//		String message_thermoblockOFF = "senza thermoblock";
//
//		Node thermoblockOFF = new Node(exThermoblockOFF, message_thermoblockOFF, thermoblockOFF_name,
//				Ontology.valueClass);
//		thermoblock.addRel(Ontology.oneToOne, thermoblockOFF);
//		list.add(thermoblockOFF);
//
//		String[] exprogrammableON = { "programmabile", "programmare l'ora per fare il caffè" };
//		String programmableON_name = "true_programmable";
//
//		String message_programmableON = "con erogazione programmabile";
//
//		Node programmableON = new Node(exprogrammableON, message_programmableON, programmableON_name,
//				Ontology.valueClass);
//		programmable.addRel(Ontology.oneToOne, programmableON);
//		list.add(programmableON);
//
//		String[] exprogrammableOFF = { "non programmabile", "non programmare l’ora per fare il caffè" };
//		String programmableOFF_name = "false_programmable";
//
//		String message_programmableOFF = "con erogazione non programmabile";
//
//		Node programmableOFF = new Node(exprogrammableOFF, message_programmableOFF, programmableOFF_name,
//				Ontology.valueClass);
//		programmable.addRel(Ontology.oneToOne, programmableOFF);
//		list.add(programmableOFF);
//
//		String[] exautoOFFON = { "spegnimento automatico", "autospegnimento" };
//		String autoOFFON_name = "true_autoOFF";
//
//		String message_autoOFFON = "con spegnimento automatico";
//
//		Node autoOFFON = new Node(exautoOFFON, message_autoOFFON, autoOFFON_name, Ontology.valueClass);
//		autoOFF.addRel(Ontology.oneToOne, autoOFFON);
//		list.add(autoOFFON);
//
//		String[] exautoOFFOFF = { "senza spegnimento automatico" };
//		String autoOFFOFF_name = "false_autoOFF";
//
//		String message_autoOFFOFF = "senza spegnimento automatico";
//
//		Node autoOFFOFF = new Node(exautoOFFOFF, message_autoOFFOFF, autoOFFOFF_name, Ontology.valueClass);
//		autoOFF.addRel(Ontology.oneToOne, autoOFFOFF);
//		list.add(autoOFFOFF);
//
//		String[] exligthON = { "leggero" };
//		String ligthON_name = "true_light";
//
//		String message_ligthON = "leggera";
//
//		Node ligthON = new Node(exligthON, message_ligthON, ligthON_name, Ontology.valueClass);
//		ligth.addRel(Ontology.oneToOne, ligthON);
//		list.add(ligthON);
//
//		String[] exligthOFF = { "non leggero", "ne' leggero" };
//		String ligthOFF_name = "false_light";
//
//		String message_ligthOFF = "non leggera";
//
//		Node ligthOFF = new Node(exligthOFF, message_ligthOFF, ligthOFF_name, Ontology.valueClass);
//		ligth.addRel(Ontology.oneToOne, ligthOFF);
//		list.add(ligthOFF);
//
//		String[] exheavyON = { "pesante" };
//		String heavyON_name = "true_heavy";
//
//		String message_heavyON = "pesante";
//
//		Node heavyON = new Node(exheavyON, message_heavyON, heavyON_name, Ontology.valueClass);
//		heavy.addRel(Ontology.oneToOne, heavyON);
//		list.add(heavyON);
//
//		String[] exheavyOFF = { "non pesante", "ne' pesante" };
//		String heavyOFF_name = "false_heavy";
//
//		String message_heavyOFF = "non pesante";
//
//		Node heavyOFF = new Node(exheavyOFF, message_heavyOFF, heavyOFF_name, Ontology.valueClass);
//		heavy.addRel(Ontology.oneToOne, heavyOFF);
//		list.add(heavyOFF);
//
//		String[] exslowON = { "lento" };
//		String slowON_name = "true_slow";
//
//		String message_slowON = "lenta";
//
//		Node slowON = new Node(exslowON, message_slowON, slowON_name, Ontology.valueClass);
//		slow.addRel(Ontology.oneToOne, slowON);
//		list.add(slowON);
//
//		String[] exslowOFF = { "non lento", "ne' lento" };
//		String slowOFF_name = "false_slow";
//
//		String message_slowOFF = "non lenta";
//
//		Node slowOFF = new Node(exslowOFF, message_slowOFF, slowOFF_name, Ontology.valueClass);
//		slow.addRel(Ontology.oneToOne, slowOFF);
//		list.add(slowOFF);
//
//		String[] exfastON = { "veloce" };
//		String fastON_name = "true_fast";
//
//		String message_fastON = "veloce";
//
//		Node fastON = new Node(exfastON, message_fastON, fastON_name, Ontology.valueClass);
//		fast.addRel(Ontology.oneToOne, fastON);
//		list.add(fastON);
//
//		String[] exfastOFF = { "non veloce", "ne' veloce" };
//		String fastOFF_name = "false_fast";
//
//		String message_fastOFF = "non veloce";
//
//		Node fastOFF = new Node(exfastOFF, message_fastOFF, fastOFF_name, Ontology.valueClass);
//		fast.addRel(Ontology.oneToOne, fastOFF);
//		list.add(fastOFF);
//
//		String[] exEuro = { "euro", "€", "\u20ac" };
//		String euro_name = "euro";
//
//		Node euro = new Node(exEuro, euro_name, euro_name, Ontology.unitClass);
//		bill.addRel(Ontology.oneToStar, euro);
//		list.add(euro);
//
//		String[] exValueAverage = { "media" };
//		String valueAverage_name = "average";
//
//		String message_average = "media";
//
//		Node valueAverage = new Node(exValueAverage, message_average, valueAverage_name, Ontology.valueClass);
//		roasting.addRel(Ontology.oneToStar, valueAverage);
//		list.add(valueAverage);
//
//		String[] exValueBlack = { "scura", "forte", "amaro" };
//		String valueBlack_name = "black";
//
//		String message_black = "scura";
//
//		Node valueBlack = new Node(exValueBlack, message_black, valueBlack_name, Ontology.valueClass);
//		roasting.addRel(Ontology.oneToStar, valueBlack);
//		list.add(valueBlack);
//
//		String[] excheapON = { "economico", "più economico" };
//		String cheapON_name = "true_cheap";
//
//		String message_cheapON_sin = "economica";
//		String message_cheapON_plu = "economiche";
//
//		Node cheapON = new Node(excheapON, message_cheapON_plu, message_cheapON_sin, message_cheapON_plu, cheapON_name,
//				Ontology.valueClass);
//		cheap.addRel(Ontology.oneToStar, cheapON);
//		list.add(cheapON);
//
//		String[] excheapOFF = { "non economico", "meno economico", "ne' economico", "nè economico" };
//		String cheapOFF_name = "false_cheap";
//
//		String message_cheapOFF_sin = "non economica";
//		String message_cheapOFF_plu = "non economiche";
//
//		Node cheapOFF = new Node(excheapOFF, message_cheapOFF_plu, message_cheapOFF_sin, message_cheapOFF_plu,
//				cheapOFF_name, Ontology.valueClass);
//		cheap.addRel(Ontology.oneToStar, cheapOFF);
//		list.add(cheapOFF);
//
//		String[] exexpensiveON = { "caro", "più caro", "costoso", "più costoso" };
//		String expensiveON_name = "true_expensive";
//
//		String message_expensiveON_sin = "cara";
//		String message_expensiveON_plu = "care";
//
//		Node expensiveON = new Node(exexpensiveON, message_expensiveON_plu, message_expensiveON_sin,
//				message_expensiveON_plu, expensiveON_name, Ontology.valueClass);
//		expensive.addRel(Ontology.oneToStar, expensiveON);
//		list.add(expensiveON);
//
//		String[] exexpensiveOFF = { "non caro", "meno caro", "non costoso", "meno costoso", "ne' caro", "ne' costoso" };
//		String expensiveOFF_name = "false_expensive";
//
//		String message_expensiveOFF_sin = "non cara";
//		String message_expensiveOFF_plu = "non care";
//
//		Node expensiveOFF = new Node(exexpensiveOFF, message_expensiveOFF_plu, message_expensiveOFF_sin,
//				message_expensiveOFF_plu, expensiveOFF_name, Ontology.valueClass);
//		expensive.addRel(Ontology.oneToStar, expensiveOFF);
//		list.add(expensiveOFF);
//
//		String[] exPromoON = { "in promozione", "in offerta", "sconto", "offerta", "promozione" };
//		String promoON_name = "true_promo";
//
//		String message_promoON = "in promozione";
//
//		Node promoON = new Node(exPromoON, message_promoON, message_promoON, message_promoON, promoON_name,
//				Ontology.valueClass);
//		promo.addRel(Ontology.oneToStar, promoON);
//		list.add(promoON);
//
//		String[] exPromoOFF = { "non in promozione", "non in offerta" };
//		String promoOFF_name = "false_promo";
//
//		String message_promoOFF = "non in promozione";
//
//		Node promoOFF = new Node(exPromoOFF, message_promoOFF, message_promoOFF, message_promoOFF, promoOFF_name,
//				Ontology.valueClass);
//		promo.addRel(Ontology.oneToStar, promoOFF);
//		list.add(promoOFF);
//
//		String[] exDecON = { "dec", "decaffeinato", "senza caffeina" };
//		String DecON_name = "true_dec";
//
//		String message_DecON = "senza caffeina";
//
//		Node DecON = new Node(exDecON, message_DecON, message_DecON, message_DecON, DecON_name, Ontology.valueClass);
//		Dec.addRel(Ontology.oneToStar, DecON);
//		list.add(DecON);
//
//		String[] exDecOFF = { "non decaffeinato", "caffeina" };
//		String DecOFF_name = "false_dec";
//
//		String message_DecOFF = "con caffeina";
//
//		Node DecOFF = new Node(exDecOFF, message_DecOFF, message_DecOFF, message_DecOFF, DecOFF_name,
//				Ontology.valueClass);
//		Dec.addRel(Ontology.oneToStar, DecOFF);
//		list.add(DecOFF);
//
//		String[] exBioON = { "bio", "biodegradabile", "compostabile", "100% biodegradabile" };
//		String bioON_name = "true_bio";
//
//		String message_bioON_plu = "biodegradabili";
//		String message_bioON_sin = "biodegradabile";
//
//		Node bioON = new Node(exBioON, message_bioON_plu, message_bioON_sin, message_bioON_plu, bioON_name,
//				Ontology.valueClass);
//		bio.addRel(Ontology.oneToStar, bioON);
//		list.add(bioON);
//
//		String[] exBioOFF = { "non bio", "non biodegradabile", "non compostabile" };
//		String bioOFF_name = "false_bio";
//
//		String message_bioOFF_plu = "non biodegradabili";
//		String message_bioOFF_sin = "non biodegradabile";
//
//		Node bioOFF = new Node(exBioOFF, message_bioOFF_plu, message_bioOFF_sin, message_bioOFF_plu, bioOFF_name,
//				Ontology.valueClass);
//		bio.addRel(Ontology.oneToStar, bioOFF);
//		list.add(bioOFF);
//
//		String[] exValueMilk = { "latte in polvere" };
//		String valueMilk_name = "milk";
//
//		String message_milk = "latte in polvere";
//
//		Node valueMilk = new Node(exValueMilk, message_milk, valueMilk_name, Ontology.valueClass);
//		composition.addRel(Ontology.oneToStar, valueMilk);
//		list.add(valueMilk);
//
//		String[] exValueTea = { "tè", "the", "tea" };
//		String valueTea_name = "tea";
//
//		String message_tea = "tè";
//
//		Node valueTea = new Node(exValueTea, message_tea, valueTea_name, Ontology.valueClass);
//		composition.addRel(Ontology.oneToStar, valueTea);
//		list.add(valueTea);
//
//		String[] exValueBarley = { "orzo" };
//		String valueBarley_name = "barley";
//
//		String message_barley = "orzo";
//
//		Node valueBarley = new Node(exValueBarley, message_barley, valueBarley_name, Ontology.valueClass);
//		composition.addRel(Ontology.oneToStar, valueBarley);
//		list.add(valueBarley);
//
//		String[] exValueGinseng = { "ginseng" };
//		String valueGinseng_name = "ginseng";
//
//		String message_ginseng = "ginseng";
//
//		Node valueGinseng = new Node(exValueGinseng, message_ginseng, valueGinseng_name, Ontology.valueClass);
//		composition.addRel(Ontology.oneToStar, valueGinseng);
//		list.add(valueGinseng);
//
//		String[] exValueArabic = { "arabico", "100% arabico", "cento per cento arabico" };
//		String valueArabic_name = "arabic";
//
//		String message_arabic = "miscela arabica";
//
//		Node valueArabic = new Node(exValueArabic, message_arabic, valueArabic_name, Ontology.valueClass);
//		composition.addRel(Ontology.oneToStar, valueArabic);
//		list.add(valueArabic);
//
//		String[] exValueRobust = { "robusto" };
//		String valueRobust_name = "robusto";
//
//		String message_robust = "miscela robusta";
//
//		Node valueRobust = new Node(exValueRobust, message_robust, valueRobust_name, Ontology.valueClass);
//		composition.addRel(Ontology.oneToStar, valueRobust);
//		list.add(valueRobust);
//
//		String[] exValueQRossa = { "qualità rossa", "lavazza qualità rossa" };
//		String qRossa_name = "lavazza qualità rossa";
//
//		Node qRossa = new Node(exValueQRossa, qRossa_name, qRossa_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, qRossa);
//		list.add(qRossa);
//
//		String[] exValueSAlta = { "selva alta", "lavazza selva alta" };
//		String sAlta_name = "lavazza selva alta";
//
//		Node sAlta = new Node(exValueSAlta, sAlta_name, sAlta_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, sAlta);
//		list.add(sAlta);
//
//		String[] exValuecPassita = { "cereja passita", "lavazza cereja passita" };
//		String cPassita_name = "lavazza cereja passita";
//
//		Node cPassita = new Node(exValuecPassita, cPassita_name, cPassita_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, cPassita);
//		list.add(cPassita);
//
//		String[] exValueAromatico = { "aromatico", "lavazza aromatico" };
//		String aromatico_name = "lavazza aromatico";
//
//		Node aromatico = new Node(exValueAromatico, aromatico_name, aromatico_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, aromatico);
//		list.add(aromatico);
//
//		String[] exValueRicco = { "ricco", "lavazza ricco" };
//		String ricco_name = "lavazza ricco";
//
//		Node ricco = new Node(exValueRicco, ricco_name, ricco_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, ricco);
//		list.add(ricco);
//
//		String[] exValueTierra = { "¡tierra!", "tierra", "lavazza tierra" };
//		String tierra_name = "lavazza tierra";
//
//		Node tierra = new Node(exValueTierra, tierra_name, tierra_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, tierra);
//		list.add(tierra);
//
//		String[] exValuePassionale = { "passionale", "lavazza passionale" };
//		String passionale_name = "lavazza passionale";
//
//		Node passionale = new Node(exValuePassionale, passionale_name, passionale_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, passionale);
//		list.add(passionale);
//
//		String[] exValueSoave = { "soave", "lavazza soave" };
//		String soave_name = "lavazza soave";
//
//		Node soave = new Node(exValueSoave, soave_name, soave_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, soave);
//		list.add(soave);
//
//		// Semplifico per ora per evitare ambiguità
//		String[] exValueIntenso = { /* "intenso", */"lavazza intenso" };
//		String intenso_name = "lavazza intenso";
//
//		Node intenso = new Node(exValueIntenso, intenso_name, intenso_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, intenso);
//		list.add(intenso);
//
//		String[] exValueDCremoso = { "deck cremoso", "lavazza deck cremoso", "dek cremoso", "lavazza dek cremoso" };
//		String dCremoso_name = "lavazza deck cremoso";
//
//		Node dCremoso = new Node(exValueDCremoso, dCremoso_name, dCremoso_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, dCremoso);
//		list.add(dCremoso);
//
//		String[] exValueDelizioso = { "delizioso", "lavazza delizioso" };
//		String delizioso_name = "lavazza delizioso";
//
//		Node delizioso = new Node(exValueDelizioso, delizioso_name, delizioso_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, delizioso);
//		list.add(delizioso);
//
//		String[] exValueDivino = { "divino", "lavazza divino" };
//		String divino_name = "lavazza divino";
//
//		Node divino = new Node(exValueDivino, divino_name, divino_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, divino);
//		list.add(divino);
//
//		String[] exValueDolce = { "dolce", "lavazza dolce" };
//		String dolce_name = "lavazza dolce";
//
//		Node dolce = new Node(exValueDolce, dolce_name, dolce_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, dolce);
//		list.add(dolce);
//
//		String[] exValuecGinseng = { "caffè ginseng", "lavazza caffè ginseng" };
//		String cGinseng_name = "lavazza caffè ginseng";
//
//		Node cGinseng = new Node(exValuecGinseng, cGinseng_name, cGinseng_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, cGinseng);
//		list.add(cGinseng);
//
//		String[] exValueOrzo = { "lavazza orzo" };
//		String orzo_name = "lavazza orzo";
//
//		Node orzo = new Node(exValueOrzo, orzo_name, orzo_name, Ontology.valueClass);
//		name_capsule.addRel(Ontology.oneToStar, orzo);
//		list.add(orzo);
//
//		// END----LITERAL
//
//		// END LITERAL
//		// END--- KNOWLEDGE BASE
		
		String speechActAffermativeName = "affermative";
		Node speechActAffermative = new Node("", speechActAffermativeName,
				Ontology.speechActClass);
		list.add(speechActAffermative);
		
		String speechActNegativeName = "negative";
		Node speechActNegative = new Node("", speechActNegativeName,
				Ontology.speechActClass);
		list.add(speechActNegative);
		
		String desire_plan_info = "plan.DesirePlan";
		Node desire_plan = new Node("", desire_plan_info, Ontology.planClass);
		list.add(desire_plan);
		
//		String dump_name="dump";
//		Node dump=new Node("", dump_name, Ontology.valueClass);
//		list.add(dump);
		
//		String yes_police_name="yes_police";
//		Node yes_police=new Node("", yes_police_name, Ontology.valueClass);
//		list.add(yes_police);
//		
//		String no_police_name="no_police";
//		Node no_police=new Node("", no_police_name, Ontology.valueClass);
//		list.add(no_police);
		
		String yes_call_police_name="yes_call_police";
		Node yes_call_police=new Node("si", yes_call_police_name, Ontology.valueClass);
		list.add(yes_call_police);
		
		String no_call_police_name="no_call_police";
		Node no_call_police=new Node("no", no_call_police_name, Ontology.valueClass);
		list.add(no_call_police);
		
//		String yes_ambulance_name="yes_ambulance";
//		Node yes_ambulance=new Node("", yes_ambulance_name, Ontology.valueClass);
//		list.add(yes_ambulance);
//		
//		String no_ambulance_name="no_ambulance";
//		Node no_ambulance=new Node("", no_ambulance_name, Ontology.valueClass);
//		list.add(no_ambulance);
		
		String yes_call_ambulance_name="yes_call_ambulance";
		Node yes_call_ambulance=new Node("si", yes_call_ambulance_name, Ontology.valueClass);
		list.add(yes_call_ambulance);
		
		String no_call_ambulance_name="no_call_ambulance";
		Node no_call_ambulance=new Node("no", no_call_ambulance_name, Ontology.valueClass);
		list.add(no_call_ambulance);
		
//		String yes_tow_truck_name="yes_tow_truck";
//		Node yes_tow_truck=new Node("", yes_tow_truck_name, Ontology.valueClass);
//		list.add(yes_tow_truck);
//		
//		String no_tow_truck_name="no_tow_truck";
//		Node no_tow_truck=new Node("", no_tow_truck_name, Ontology.valueClass);
//		list.add(no_tow_truck);
		
		String yes_call_tow_truck_name="yes_call_tow_truck";
		Node yes_call_tow_truck=new Node("si", yes_call_tow_truck_name, Ontology.valueClass);
		list.add(yes_call_tow_truck);
		
		String no_call_tow_truck_name="no_call_tow_truck";
		Node no_call_tow_truck=new Node("no", no_call_tow_truck_name, Ontology.valueClass);
		list.add(no_call_tow_truck);
		
		String desire_name = "desire";
		String message_desire = "vuoi fare";
		Node desire = new Node(message_desire, desire_name, Ontology.actionClass);
		desire.addRel(Ontology.speechAct, speechActAffermative);
		desire.addRel(Ontology.speechAct, speechActNegative);
		desire.addRel(Ontology.plan, desire_plan);
		list.add(desire);
		
		String preventivo_name = "preventivo";
		String message_preventivo = "un preventivo per un'auto";
		Node preventivo = new Node(message_preventivo, preventivo_name, Ontology.domainClass);
		desire.addRel(Ontology.oneToOne, preventivo);
		preventivo.addRel(Ontology._default, desire);
		list.add(preventivo);
		
		String complaint_name = "complaint";
		String message_complaint = "una denuncia sinistro";
		Node complaint = new Node(message_complaint, complaint_name, Ontology.domainClass);
		desire.addRel(Ontology.oneToOne, complaint);
		complaint.addRel(Ontology._default, desire);
		list.add(complaint);
		
		String plateProp_name = "plateProp";
		String plateProp_message = "con targa";
		Node plateProp = new Node(plateProp_message, plateProp_name, Ontology.propertyClass);
		preventivo.addRel(Ontology.oneToMany, plateProp);
		plateProp.addRel(Ontology._default,preventivo);
		preventivo.addInTemplate(plateProp);
		list.add(plateProp);
		
		String plateValue_name="plate";
		String plateValue_message="{value:token}";
		Node plate=new Node(plateValue_message, plateValue_name, Ontology.valueClass);
		plateProp.addRel(Ontology.oneToStar, plate);
		plate.addRel(Ontology._default, plateProp);
		list.add(plate);
		
		String Integer_name = "integer";
		String message_token = "{value:token}";
		Node integer = new Node(message_token, Integer_name, Ontology.numberClass);
		list.add(integer);
		
		String injuredProp_name = "injured";
		String injuredProp_message = "\n feriti:";
		Node injuredProp = new Node(injuredProp_message, injuredProp_name, Ontology.propertyClass);
		complaint.addRel(Ontology.oneToMany, injuredProp);
		injuredProp.addRel(Ontology._default,complaint);
		complaint.addInTemplate(injuredProp);
		injuredProp.addRel(Ontology.oneToOne, integer);
		list.add(injuredProp);
		
//		String ambulanceProp_name = "ambulance";
//		String ambulanceProp_message = "\n richiede ambulanza";
//		Node ambulanceProp = new Node(ambulanceProp_message, ambulanceProp_name, Ontology.propertyClass);
//		complaint.addRel(Ontology.oneToMany, ambulanceProp);
//		ambulanceProp.addRel(Ontology._default,complaint);
//		complaint.addInTemplate(ambulanceProp);
//		ambulanceProp.addRel(Ontology.oneToOne, yes_ambulance);
//		ambulanceProp.addRel(Ontology.oneToOne, no_ambulance);
//		list.add(ambulanceProp);
		
		String call_ambulanceProp_name = "call_ambulance";
		String call_ambulanceProp_message = "\n richiede ambulanza: ";
		Node call_ambulanceProp = new Node(call_ambulanceProp_message, call_ambulanceProp_name, Ontology.propertyClass);
		complaint.addRel(Ontology.oneToMany, call_ambulanceProp);
		call_ambulanceProp.addRel(Ontology._default,complaint);
		complaint.addInTemplate(call_ambulanceProp);
		call_ambulanceProp.addRel(Ontology.oneToOne, yes_call_ambulance);
		call_ambulanceProp.addRel(Ontology.oneToOne, no_call_ambulance);
		list.add(call_ambulanceProp);
		
		String vehicleProp_name = "vehicle";
		String vehicleProp_message = "\n veicoli:";
		Node vehicleProp = new Node(vehicleProp_message, vehicleProp_name, Ontology.propertyClass);
		complaint.addRel(Ontology.oneToMany, vehicleProp);
		vehicleProp.addRel(Ontology._default,complaint);
		complaint.addInTemplate(vehicleProp);
		vehicleProp.addRel(Ontology.oneToOne, integer);
		list.add(vehicleProp);
		
//		String policeProp_name = "police";
//		String policeProp_message = "\n ha chiamato la polizia";
//		Node policeProp = new Node(policeProp_message, policeProp_name, Ontology.propertyClass);
//		complaint.addRel(Ontology.oneToMany, policeProp);
//		policeProp.addRel(Ontology._default,complaint);
//		complaint.addInTemplate(policeProp);
//		policeProp.addRel(Ontology.oneToOne, yes_police);
//		policeProp.addRel(Ontology.oneToOne, no_police);
//		list.add(policeProp);
		
		String call_policeProp_name = "call_police";
		String call_policeProp_message = "\n richiede polizia: ";
		Node call_policeProp = new Node(call_policeProp_message,call_policeProp_name, Ontology.propertyClass);
		complaint.addRel(Ontology.oneToMany,call_policeProp);
		call_policeProp.addRel(Ontology._default,complaint);
		complaint.addInTemplate(call_policeProp);
		call_policeProp.addRel(Ontology.oneToOne, yes_call_police);
		call_policeProp.addRel(Ontology.oneToOne, no_call_police);
		list.add(call_policeProp);
		
		String deadProp_name = "dead";
		String deadProp_message = "\n deceduti:";
		Node deadProp = new Node(deadProp_message, deadProp_name, Ontology.propertyClass);
		complaint.addRel(Ontology.oneToMany, deadProp);
		deadProp.addRel(Ontology._default,complaint);
		complaint.addInTemplate(deadProp);
		deadProp.addRel(Ontology.oneToOne, integer);
		list.add(deadProp);
		
//		String tow_truckProp_name = "tow_truck";
//		String tow_truckProp_message = "\n richiede carroattrezzi";
//		Node tow_truckProp = new Node(tow_truckProp_message, tow_truckProp_name, Ontology.propertyClass);
//		complaint.addRel(Ontology.oneToMany, tow_truckProp);
//		tow_truckProp.addRel(Ontology._default,complaint);
//		complaint.addInTemplate(tow_truckProp);
//		tow_truckProp.addRel(Ontology.oneToOne, yes_tow_truck);
//		tow_truckProp.addRel(Ontology.oneToOne, no_tow_truck);
//		list.add(tow_truckProp);
		
		String call_tow_truckProp_name = "call_tow_truck";
		String call_tow_truckProp_message = "\n richiede carroattrezzi: ";
		Node call_tow_truckProp = new Node(call_tow_truckProp_message, call_tow_truckProp_name, Ontology.propertyClass);
		complaint.addRel(Ontology.oneToMany, call_tow_truckProp);
		call_tow_truckProp.addRel(Ontology._default,complaint);
		complaint.addInTemplate(call_tow_truckProp);
		call_tow_truckProp.addRel(Ontology.oneToOne, yes_call_tow_truck);
		call_tow_truckProp.addRel(Ontology.oneToOne, no_call_tow_truck);
		list.add(call_tow_truckProp);
		
		String address_name = "address";
		String message_address = "{value:token}";
		Node address = new Node(message_address, address_name, Ontology.valueClass);
		list.add(address);
		
		String locationProp_name = "location";
		String locationProp_message = "\n indirizzo:";
		Node locationProp = new Node(locationProp_message, locationProp_name, Ontology.propertyClass);
		complaint.addRel(Ontology.oneToMany, locationProp);
		locationProp.addRel(Ontology._default,complaint);
		complaint.addInTemplate(locationProp);
		locationProp.addRel(Ontology.oneToMany, address);
		list.add(locationProp);
		
		
			
		// create an empty Model
		OntModel model = Ontology.define();

		for (Node n : list) {
			Resource now = model.createResource("http://project.article/" + n.name).addProperty(Ontology.name, n.name)
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
