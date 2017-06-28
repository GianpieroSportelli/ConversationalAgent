package plan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Utils.JSON_utils;
import configuration.Config;
import dialogManager.DialogManager;
import knowledge.KnowledgeBase;
import knowledge.Ontology;

public class DesirePlan implements ActionPlan {

	private final String[] property = { "injured", "vehicle", "dead", "call_ambulance", "call_police", "call_tow_truck",
			"location" };

	@Override
	public String getName() {
		return (new JSONObject().accumulate("class", DesirePlan.class.getName())).toString();
	}

	@Override
	public List<JSONObject> execute(JSONObject sem, KnowledgeBase net, Config conf, int current_epoch, String id_user,
			DialogManager dm) {
		System.out.println("PLAN DESIRE ==========================================");
		List<JSONObject> question = init_question();
		List<JSONObject> context = init_context(net, current_epoch);

		List<JSONObject> result = new ArrayList<>();
		if (sem.has("domain")) {
			JSONObject domain = sem.getJSONObject("domain");
			String domName = domain.getString("name");
			if (domName.equals("preventivo")) {
				JSONObject obj = new JSONObject();
				obj.accumulate("category", "dialog");
				obj.accumulate("name", "M_Preventivo");
				obj.accumulate(Ontology.MESSAGE,
						"sono felice che tu voglia fare un preventivo, oggi non posso aiutarti chiedimi domaini...");
				;
			} else if (domName.equals("complaint")) {
				JSONArray properties = null;
				if (domain.has("property")) {
					properties = JSON_utils.convertJSONArray(domain.get("property").toString());
				} else {
					properties = new JSONArray();
				}
				for (int p = 0; p < property.length; p++) {
					String qProp = property[p];
					boolean find = false;
					for (int i = 0; i < properties.length(); i++) {
						JSONObject prop = properties.getJSONObject(i);
						String propName = prop.getString("name");
						if (propName.equals(qProp)) {
							find = true;
							break;
						}
					}

					if (!find) {
						System.out.println("non ho trovato " + qProp
								+ " sull'oggetto del dominio, provo a verificare se questo è una risposta a un messaggio");

						if (sem.has(Ontology.speechActClassName)) {
							System.out.println("c'è uno speech ACT");
							JSONObject speechAct = sem.getJSONObject(Ontology.speechActClassName);
							boolean affermative = speechAct.getString("name").equals("affermative");
							System.out.println("è affermativa?? " + affermative);
							System.out.println(qProp + "==" + property[0] + ":" + qProp.equals(property[0]));
							if (qProp.equals(property[0])) {
								if (!affermative) {
									JSONObject prop = new JSONObject();
									prop.accumulate("category", "property");
									prop.accumulate("name", property[0]);
									prop = new JSONObject(net.enrich(prop.toString()));
									JSONObject number = new JSONObject();
									number.accumulate("category", "number");
									number.accumulate("name", "integer");
									number.accumulate("token", "0");
									number = new JSONObject(net.enrich(number.toString()));
									prop.accumulate("number", number);
									properties.put(prop);
									domain.put("property", properties);
									sem.put("domain", domain);
									sem.remove(Ontology.speechActClassName);
									dm.addDiscourseMemory(sem.toString());
									try {
										dm.writeMemory();
									} catch (IOException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									}
								}
							}

							if (qProp.equals(property[2])) {
								if (!affermative) {
									sem.remove(Ontology.speechActClassName);
									dm.removeFromDiscorseMemory(sem.toString());
									JSONObject prop = new JSONObject();
									prop.accumulate("category", "property");
									prop.accumulate("name", property[2]);
									prop = new JSONObject(net.enrich(prop.toString()));
									JSONObject number = new JSONObject();
									number.accumulate("category", "number");
									number.accumulate("name", "integer");
									number.accumulate("token", "0");
									number = new JSONObject(net.enrich(number.toString()));
									prop.accumulate("number", number);
									properties.put(prop);
									domain.put("property", properties);
									sem.put("domain", domain);
									dm.addDiscourseMemory(sem.toString());
									try {
										dm.writeMemory();
									} catch (IOException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									}

								}
							}
							if (qProp.equals(property[3])) {
								sem.remove(Ontology.speechActClassName);
								dm.removeFromDiscorseMemory(sem.toString());
								JSONObject prop = new JSONObject();
								prop.accumulate("category", "property");
								prop.accumulate("name", property[3]);
								prop = new JSONObject(net.enrich(prop.toString()));
								JSONObject resp = new JSONObject();
								resp.accumulate("category", "value");
								if (affermative) {
									resp.accumulate("name", "yes_call_ambulance");
								} else {
									resp.accumulate("name", "no_call_ambulance");
								}
								resp = new JSONObject(net.enrich(resp.toString()));
								prop.accumulate("value", resp);
								properties.put(prop);
								domain.put("property", properties);
								sem.put("domain", domain);
								dm.addDiscourseMemory(sem.toString());
								try {
									dm.writeMemory();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							if (qProp.equals(property[4])) {
								sem.remove(Ontology.speechActClassName);
								dm.removeFromDiscorseMemory(sem.toString());
								JSONObject prop = new JSONObject();
								prop.accumulate("category", "property");
								prop.accumulate("name", property[4]);
								prop = new JSONObject(net.enrich(prop.toString()));
								JSONObject resp = new JSONObject();
								resp.accumulate("category", "value");
								if (affermative) {
									resp.accumulate("name", "yes_call_police");
								} else {
									resp.accumulate("name", "no_call_police");
								}
								resp = new JSONObject(net.enrich(resp.toString()));
								prop.accumulate("value", resp);
								properties.put(prop);
								domain.put("property", properties);
								sem.put("domain", domain);
								dm.addDiscourseMemory(sem.toString());
								try {
									dm.writeMemory();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if (qProp.equals(property[5])) {
								sem.remove(Ontology.speechActClassName);
								dm.removeFromDiscorseMemory(sem.toString());
								JSONObject prop = new JSONObject();
								prop.accumulate("category", "property");
								prop.accumulate("name", property[5]);
								prop = new JSONObject(net.enrich(prop.toString()));
								JSONObject resp = new JSONObject();
								resp.accumulate("category", "value");
								if (affermative) {
									resp.accumulate("name", "yes_call_tow_truck");
								} else {
									resp.accumulate("name", "no_call_tow_truck");
								}
								resp = new JSONObject(net.enrich(resp.toString()));
								prop.accumulate("value", resp);
								properties.put(prop);
								domain.put("property", properties);
								sem.put("domain", domain);
								dm.addDiscourseMemory(sem.toString());
								try {
									dm.writeMemory();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						} else {
							System.out.println("niente da fare, provo a chiedere.");
							result.add(question.get(p));
							dm.addDiscourseMemory(context.get(p).toString());
							try {
								dm.writeMemory();
							} catch (IOException e) {
								e.printStackTrace();
							}
							return result;
						}
					}
				}
				result.add(sem);
				// } else {
				// dm.addDiscourseMemory(context.get(0).toString());
				// try {
				// dm.writeMemory();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// result.add(question.get(0));
				// }
			}
		} else {
			JSONObject obj = new JSONObject();
			obj.accumulate("category", "dialog");
			obj.accumulate("name", "wathDesire");
			obj.accumulate(Ontology.MESSAGE,
					"cosa vorresti? posso aiutarti solo per le denunce e preventivi auto attualmente");
			result.add(obj);
		}
		return result;
	}

	private List<JSONObject> init_question() {
		List<JSONObject> result = new ArrayList<>();
		JSONObject obj = new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "N_injured");
		obj.accumulate(Ontology.MESSAGE, "ci sono feriti?");
		result.add(obj);
		obj = new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "N_vehicle");
		obj.accumulate(Ontology.MESSAGE, "quanti veicoli sono rimasti coinvolti?");
		result.add(obj);
		obj = new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "N_dead");
		obj.accumulate(Ontology.MESSAGE, "ci sono stati dei decessi?");
		result.add(obj);
		obj = new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "N_call_ambulance");
		obj.accumulate(Ontology.MESSAGE, "vuoi che chiami un'ambulanza?");
		result.add(obj);
		obj = new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "N_call_police");
		obj.accumulate(Ontology.MESSAGE, "vuoi che chiami la polizia?");
		result.add(obj);
		obj = new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "N_call_tow_truck");
		obj.accumulate(Ontology.MESSAGE, "vuoi che chiami un carroattrezzi?");
		result.add(obj);
		obj = new JSONObject();
		obj.accumulate("category", "dialog");
		obj.accumulate("name", "N_location");
		obj.accumulate(Ontology.MESSAGE, "in che via è successo l'incidente?");
		result.add(obj);
		return result;
	}

	private List<JSONObject> init_context(KnowledgeBase net, int current_epoch) {
		List<JSONObject> result = new ArrayList<>();
		JSONObject obj = null;
		for (String prop : property) {
			obj = new JSONObject();
			obj.accumulate("category", "property");
			obj.accumulate("name", prop);
			obj.accumulate("epoch", current_epoch+1);
			obj = new JSONObject(net.enrich(obj.toString()));
			result.add(obj);
		}
		return result;
	}

}
