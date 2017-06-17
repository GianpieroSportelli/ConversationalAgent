package knowledge;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.*;

import configuration.Config;

public class Ontology {

	private static String url_onto = Config.getPathOntology();
	public static final String NO_MESSAGE = "NO_MESSAGE";
        public static final String MESSAGE = "MESSAGE";

	private static OntModel m_model = ModelFactory.createOntologyModel();

	public static final String NS = "http://project.article#";

	public static Map<String, Integer> cardinality = new HashMap<>();

	// Priorità tra categorie semantiche a livello di Pattern Matching
	public static final Property salience = m_model.createProperty(NS + "salience");

	public static final Property min_card = m_model.createProperty(NS + "minCardinality");
	public static final Property max_card = m_model.createProperty(NS + "maxCardinality");

	// oggetto legato
	public static final ObjectProperty oneToOne = m_model.createObjectProperty(NS + "oneToOne");

	public static final ObjectProperty oneToMany = m_model.createObjectProperty(NS + "oneToMany");

	public static final ObjectProperty oneToStar = m_model.createObjectProperty(NS + "oneToStar");

	// DEFAULT properity
	public static final String _defaultPropertyName = NS + "default";
	public static final ObjectProperty _default = m_model.createObjectProperty(_defaultPropertyName);

	public static final String planName = NS + "planLink";
	public static final ObjectProperty plan = m_model.createObjectProperty(planName);

	public static final String speechActName = NS + "speechAct";
	public static final ObjectProperty speechAct = m_model.createObjectProperty(speechActName);

	// Proprietà necessaria per la definizione di completezza
	// public static final Property complete = m_model.createProperty(NS +
	// "complete");

	// definizione della prorpietà che lega classi e istanze ai relativi
	// template
	public static final OntProperty template = m_model.createOntProperty(NS + "template");

	// definizione della proprietà che lega classi e istanze a un letterale che
	// rappresenta il nome
	public static final ObjectProperty name = m_model.createObjectProperty(NS + "name");

	// definizione di una proprietà necessaria per limitare il numero di
	// elementi legabili (1 Action -> 1 Domain)
	// public static final ObjectProperty onlyOne =
	// m_model.createObjectProperty(NS + "onlyOne");

	public static final OntClass actionClass = (OntClass) m_model.createClass(NS + "class/action")
			.addLiteral(salience, 100).addLiteral(name, "action");

	public static final OntClass domainClass = (OntClass) m_model.createClass(NS + "class/domain")
			.addLiteral(salience, 90).addLiteral(name, "domain");

	public static final OntClass propertyClass = (OntClass) m_model.createClass(NS + "class/property")
			.addLiteral(salience, 80).addLiteral(name, "property");

	public static final OntClass modClass = (OntClass) m_model.createClass(NS + "class/mod").addLiteral(salience, 70)
			.addLiteral(name, "mod");

	public static final OntClass unitClass = (OntClass) m_model.createClass(NS + "class/unit").addLiteral(salience, 60)
			.addLiteral(name, "unit");

	public static final OntClass numberClass = (OntClass) m_model.createClass(NS + "class/number")
			.addLiteral(salience, 50).addLiteral(name, "number");

	public static final OntClass valueClass = (OntClass) m_model.createClass(NS + "class/value")
			.addLiteral(salience, 40).addLiteral(name, "value");

	public static final OntClass dialogClass = (OntClass) m_model.createClass(NS + "class/dialog")
			.addLiteral(salience, 20).addLiteral(name, "dialog");

	public static final OntClass planClass = (OntClass) m_model.createClass(NS + "class/plan").addLiteral(salience, 0)
			.addLiteral(name, "plan");

	public static final String speechActClassName = "speechAct";
	public static final OntClass speechActClass = (OntClass) m_model.createClass(NS + "class/speechAct")
			.addLiteral(salience, 0).addLiteral(name, speechActClassName);

	// // solo perchè non considero attualmente la descrizione
	// public static final OntClass extractedClass = (OntClass)
	// m_model.createClass(NS + "class/extracted")
	// .addLiteral(salience, -1).addLiteral(name, "extracted");
	//
	public static final OntClass noAnswer = (OntClass) m_model.createClass(NS + "class/noAnswer")
			.addLiteral(salience, -1).addLiteral(name, "noAnswer");

	public static final ObjectProperty example = m_model.createObjectProperty(NS + "input/example");

	public static final ObjectProperty message_singular = m_model.createObjectProperty(NS + "output/message_singular");

	public static final ObjectProperty message_plural = m_model.createObjectProperty(NS + "output/message_plural");

	public static final ObjectProperty message_not = m_model.createObjectProperty(NS + "output/message_not");

	public static final OntProperty relation = m_model.createOntProperty(NS + "classRelation");

	public static final ObjectProperty[] listProperty = { oneToMany, oneToOne, oneToStar };

	public static final OntClass[] listClass = { dialogClass, actionClass, domainClass, propertyClass, valueClass,
			numberClass, unitClass, modClass, speechActClass };

	private static RDFList template_action = m_model.createList(new RDFNode[] { domainClass });
	private static RDFList template_domain = m_model.createList(new RDFNode[] { propertyClass, dialogClass });
	private static RDFList template_property = m_model
			.createList(new RDFNode[] { valueClass, modClass, numberClass, unitClass });
	private static RDFList template_value = m_model.createList(new RDFNode[] {});
	private static RDFList template_mod = m_model.createList(new RDFNode[] {});
	private static RDFList template_number = m_model.createList(new RDFNode[] {});
	private static RDFList template_unit = m_model.createList(new RDFNode[] {});
        private static final RDFList template_dialog = m_model.createList(new RDFNode[] {domainClass});

	public static OntModel define() {
		oneToMany.addProperty(min_card, "0");
		oneToMany.addProperty(max_card, "N");

		oneToOne.addProperty(min_card, "1");
		oneToOne.addProperty(max_card, "1");

		oneToStar.addProperty(min_card, "0");
		oneToStar.addProperty(max_card, "1");

		/* Template definition */
		actionClass.addProperty(template, template_action);
		domainClass.addProperty(template, template_domain);
		propertyClass.addProperty(template, template_property);
		valueClass.addProperty(template, template_value);
		modClass.addProperty(template, template_mod);
		numberClass.addProperty(template, template_number);
		unitClass.addProperty(template, template_unit);
                dialogClass.addProperty(template, template_dialog);

		m_model.add(speechActClass, relation, actionClass).add(actionClass, relation, domainClass).add(domainClass, relation, propertyClass)
				.add(propertyClass, relation, modClass).add(modClass, relation, numberClass)
				.add(numberClass, relation, unitClass).add(unitClass, relation, valueClass);

		try {
			m_model.write(new FileOutputStream(url_onto));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m_model;
	}

	public static Map<String, Integer> cardinality() {
		cardinality.put("0", 0);
		cardinality.put("1", 1);
		cardinality.put("N", Integer.MAX_VALUE);
		return cardinality;
	}
}
