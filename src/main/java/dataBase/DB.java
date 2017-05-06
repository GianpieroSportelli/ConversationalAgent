package dataBase;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Utils.JSON_utils;
import configuration.Config;
import knowledge.SemanticNet;

public class DB {
	static ArrayList<Domain> capsule;
	static ArrayList<Domain> machine;
	static final boolean DEBUG = Config.getDBDebug();

	public static void init() {
		capsule = new ArrayList<>();
		// 0,374375 a capsula
		Capsule c1 = new Capsule("lavazza qualità rossa", "mix", "average", 10, 16, 5.99, true, false, false, true,
				false);
		capsule.add(c1);

		// 0,4158 a capsula
		Capsule c2 = new Capsule("lavazza selva alta", "arabic", "average", 7, 12, 4.99, false, false, false, false,
				true);
		capsule.add(c2);

		// 0,4158 a capsula
		Capsule c3 = new Capsule("lavazza cereja passita", "arabic", "average", 9, 12, 4.99, false, false, false, false,
				true);
		capsule.add(c3);

		// 0,415 a capsula
		Capsule c4 = new Capsule("lavazza aromatico", "arabic", "average", 6, 6, 2.49, false, true, false, false,
				false);
		capsule.add(c4);

		// 0,415 a capsula
		Capsule c5 = new Capsule("lavazza ricco", "arabic", "black", 7, 6, 2.49, true, true, false, false, false);
		capsule.add(c5);

		// 0,405 a capsula
		Capsule c6 = new Capsule("lavazza tierra", "arabic", "average", 9, 16, 6.49, false, false, false, false, false);
		capsule.add(c6);

		// 0,405 a capsula
		Capsule c7 = new Capsule("lavazza magia", "arabic", "average", 8, 16, 6.49, false, false, false, false, false);
		capsule.add(c7);

		// 0,374375 a capsula
		Capsule c8 = new Capsule("lavazza passionale", "arabic", "black", 11, 16, 5.99, false, false, false, true,
				false);
		c8.addConfiguration(36, 11.99);
		capsule.add(c8);

		// 0,374375 a capsula
		Capsule c9 = new Capsule("lavazza soave", "arabic", "average", 5, 16, 5.99, true, false, false, true, false);
		capsule.add(c9);

		// 0,374375 a capsula
		Capsule c10 = new Capsule("lavazza intenso", "mix", "average", 13, 16, 5.99, false, false, false, true, false);
		c10.addConfiguration(36, 11.99);
		capsule.add(c10);

		// 0,374375 a capsula
		Capsule c11 = new Capsule("lavazza deck cremoso", "arabic", "average", 7, 16, 5.99, false, false, true, true,
				false);
		capsule.add(c11);

		// 0,374375 a capsula
		Capsule c12 = new Capsule("lavazza delizioso", "arabic", "average", 8, 16, 5.99, false, false, false, true,
				false);
		c12.addConfiguration(36, 11.99);
		capsule.add(c12);

		// 0,405 a capsula
		Capsule c13 = new Capsule("lavazza divino", "mix", "black", 11, 16, 6.49, false, false, false, false, false);
		capsule.add(c13);

		// 0,374375 a capsula
		Capsule c14 = new Capsule("lavazza dolce", "arabic", "average", 6, 16, 5.99, true, false, false, true, false);
		capsule.add(c14);

		// 0,4158 a capsula
		Capsule c15 = new Capsule("lavazza caffè ginseng", "ginseng", "no tostato", -1, 12, 4.99, false, false, true, false,
				true);
		capsule.add(c15);

		// 0,4158 a capsula
		Capsule c16 = new Capsule("lavazza orzo", "barley", "no tostato", -1, 12, 4.49, false, false, true, false,
				true);
		capsule.add(c16);

		machine = new ArrayList<>();
		Machine m1 = new Machine("fantasia", new String[] { "red", "white", "black" }, 35, 1.2, 179.90, 4.15,
				"electrolux", false, true, true, false, true, false, false, false, true, false, false);
		machine.add(m1);
		Machine m2 = new Machine("fantasia plus", new String[] { "white" }, 35, 1.2, 199.90, 4.15, "electrolux", false,
				true, true, false, true, false, true, false, true, false, false);
		machine.add(m2);
		Machine m3 = new Machine("jolie", new String[] { "red", "white", "light blue", "lime" }, 25, 0.6, 79.90, 2.5,
				"lavazza", true, true, false, true, false, false, true, true, false, true, false);
		machine.add(m3);
		Machine m4 = new Machine("minù", new String[] { "red", "white", "orange", "yellow", "light blue" }, 60, 0.5,
				69.90, 2.92, "lavazza", true, true, false, true, false, false, true, true, false, false, true);
		machine.add(m4);
		Machine m5 = new Machine("minù caffè latte", new String[] { "red", "white", "cyan" }, 60, 0.5, 119.90, 4.2,
				"lavazza", false, true, true, false, false, false, false, false, true, false, true);
		machine.add(m5);
		Machine m6 = new Machine("magia", new String[] { "red", "white", "black" }, 35, 0.85, 129.90, 3.5, "electrolux",
				false, true, false, false, false, false, true, false, false, false, false);
		machine.add(m6);
		Machine m7 = new Machine("magia plus", new String[] { "white" }, 35, 0.85, 149.90, 3.5, "electrolux", false,
				true, false, false, false, false, true, false, false, false, false);
		machine.add(m7);
		Machine m8 = new Machine("espria plus", new String[] { "grey" }, 40, 0.8, 119.90, 4.27, "electrolux", false,
				true, false, false, false, false, true, false, true, false, false);
		machine.add(m8);
	}

	public static List<JSONObject> query(List<String> queries, SemanticNet net) {
		List<JSONObject> result = new ArrayList<>();
		ArrayList<Domain> obj_result = new ArrayList<>();

		for (int x = 0; x < queries.size(); x++) {

			JSONObject result_json = new JSONObject();
			String query_str = queries.get(x);
			JSONObject query = new JSONObject(query_str);

			if (query.has("category")) {
				if (query.getString("category").equals("action")) {
					
					String name_action = query.getString("name");
					
					if (name_action.equals("search") && query.has("domain")) {
						
						JSONObject domain = query.getJSONObject("domain");
						
						if (domain.getString("name").equals("capsule")) {

							if (domain.has("property")) {
								// ci sono proprità
								String str_property = domain.get("property").toString();
								JSONArray property = JSON_utils.convertJSONArray(str_property);

								JSONObject select = new JSONObject();
								JSONObject proj = new JSONObject();
								JSONArray sub = null;

								for (int i = 0; i < property.length(); i++) {
									JSONObject current = property.getJSONObject(i);
									String current_name = current.getString("name");
									boolean compatible = current_name.equals("compatible");

									if ((net.incomplete(current) || current_name.equals("features")) && !compatible) {
										proj.accumulate("element", current);

									} else if (compatible) {

										boolean has_sub = false;
										for (int y = x + 1; y < queries.size(); y++) {
											String sub_query_str = queries.get(y);
											JSONObject sub_query = new JSONObject(sub_query_str);
											if (sub_query.has("domain") && sub_query.getJSONObject("domain")
													.getString("name").equals("machine")) {
												has_sub = true;
												JSONObject machine = sub_query.getJSONObject("domain");
												JSONObject brand = new JSONObject();
												brand.accumulate("category", "property");
												brand.accumulate("name", "brand");
												brand = new JSONObject(net.enrich(brand.toString()));
												if (machine.has("property")) {
													JSONArray macchine_property = JSON_utils
															.convertJSONArray(machine.get("property").toString());
													boolean find_brand = false;
													for (int k = 0; k < macchine_property.length()
															&& !find_brand; k++) {
														find_brand = macchine_property.getJSONObject(k)
																.getString("name").equals("brand");
													}
													if (!find_brand) {
														machine.accumulate("property", brand);
													}
												} else {
													machine.accumulate("property", brand);
												}
												List<String> sub_list = new ArrayList<>();
												sub_list.add(sub_query.toString());
												List<JSONObject> result_sub = query(sub_list, net);
												if (DEBUG) {
													System.out.println(
															"SUB QUERY --------------------------------------------------------------------<<");
													for (JSONObject sub_res : result_sub) {
														System.out.println(sub_res.toString(4));
													}
												}
												JSONObject result_sub_json = result_sub.get(0);
												if (result_sub_json.has("result")) {
													sub = JSON_utils
															.convertJSONArray(result_sub_json.get("result").toString());
												} else {
													sub = new JSONArray();
												}
												query.accumulate("domain", machine);
												queries.remove(y);
												break;
											}
										}

										if (!has_sub) {
											property.remove(i--);
											domain.remove("property");
											for (int k = 0; k < property.length(); k++) {
												domain.accumulate("property", property.get(k));
											}
											query.put("domain", domain);
											queries.remove(x);
											queries.add(x, query.toString());
										}

									} else {
										select.accumulate("element", current);
									}
								}

								obj_result = cloneList(capsule);
								obj_result = select(obj_result, select, sub);
								result_json = projection(obj_result, proj, domain);
								result_json.accumulate("query", query);
							} else {
								// non ci sono properietà
								obj_result = cloneList(capsule);
								result_json = projection(obj_result, new JSONObject(), null);
								result_json.accumulate("query", query);
							}
						} else if (domain.getString("name").equals("machine")) {
							if (domain.has("property")) {
								// ci sono proprità
								String str_property = domain.get("property").toString();

								JSONArray property = JSON_utils.convertJSONArray(str_property);
								JSONObject select = new JSONObject();
								JSONObject proj = new JSONObject();
								JSONArray sub = null;

								for (int i = 0; i < property.length(); i++) {
									JSONObject current = property.getJSONObject(i);
									String current_name = current.getString("name");
									boolean compatible = current_name.equals("compatible");

									if ((net.incomplete(current) || current_name.equals("features")) && !compatible) {
										proj.accumulate("element", current);
									} else if (compatible) {
										boolean has_sub = false;
										for (int y = x + 1; y < queries.size(); y++) {
											String sub_query_str = queries.get(y);
											JSONObject sub_query = new JSONObject(sub_query_str);
											if (sub_query.has("domain") && sub_query.getJSONObject("domain")
													.getString("name").equals("capsule")) {
												has_sub = true;
												JSONObject capsule = sub_query.getJSONObject("domain");
												JSONObject brand = new JSONObject();
												brand.accumulate("category", "property");
												brand.accumulate("name", "brand");
												brand = new JSONObject(net.enrich(brand.toString()));
												if (capsule.has("property")) {
													JSONArray capsule_property = JSON_utils
															.convertJSONArray(capsule.get("property").toString());
													boolean find_brand = false;
													for (int k = 0; k < capsule_property.length() && !find_brand; k++) {
														find_brand = capsule_property.getJSONObject(k).getString("name")
																.equals("brand");
													}
													if (!find_brand) {
														capsule.accumulate("property", brand);
													}
												} else {
													capsule.accumulate("property", brand);
												}
												List<String> sub_list = new ArrayList<>();
												sub_list.add(sub_query.toString());
												List<JSONObject> result_sub = query(sub_list, net);
												if (DEBUG) {
													System.out.println(
															"SUB QUERY --------------------------------------------------------------------<<");
													for (JSONObject sub_res : result_sub) {
														System.out.println(sub_res.toString(4));
													}
												}
												JSONObject result_sub_json = result_sub.get(0);
												if (result_sub_json.has("result")) {
													sub = JSON_utils
															.convertJSONArray(result_sub_json.get("result").toString());
												} else {
													sub = new JSONArray();
												}
												query.accumulate("domain", capsule);
												queries.remove(y);
												break;
											}
										}
										if (!has_sub) {
											property.remove(i);
											domain.remove("property");
											for (int k = 0; k < property.length(); k++) {
												domain.accumulate("property", property.get(k));
											}
											query.put("domain", domain);
											queries.remove(x);
											queries.add(x, query.toString());
										}

									} else {
										select.accumulate("element", current);
									}
								}
								obj_result = cloneList(machine);
								obj_result = select(obj_result, select, sub);
								result_json = projection(obj_result, proj, domain);
								result_json.accumulate("query", query);

							} else {
								// non ci sono properietà
								obj_result = cloneList(machine);
								result_json = projection(obj_result, new JSONObject(), null);
								result_json.accumulate("query", query);

							}
						}
					}
				}
			}

			if (!result_json.has("query")) {
				result_json.accumulate("query", query);
			}

			result.add(result_json);
		}

		return result;
	}

	private static JSONObject projection(ArrayList<Domain> obj_result, JSONObject proj, JSONObject domain) {
		JSONObject result = new JSONObject();
		if (obj_result.size() > 0) {
			for (Domain d : obj_result) {
				if (d instanceof Capsule) {
					Capsule c = (Capsule) d;
					String str_element = "[]";

					if (proj.has("element")) {
						str_element = proj.get("element").toString();
						if (str_element.charAt(0) != '[') {
							str_element = '[' + str_element + ']';
						}
					}

					JSONArray property = new JSONArray(str_element);
					Capsule newCap = new Capsule();
					newCap.name = c.name;

					for (int i = 0; i < property.length(); i++) {
						String prop_name = property.getJSONObject(i).getString("name");
						if (prop_name.equals("composition")) {
							newCap.composition = c.composition;
						} else if (prop_name.equals("brand")) {
							newCap.brand = c.brand;
						} else if (prop_name.equals("compatible")) {
							newCap.compatible = c.compatible;
						} else if (prop_name.equals("roasting")) {
							newCap.roasting = c.roasting;
						} else if (prop_name.equals("intensity")) {
							newCap.intensity = c.intensity;
						} else if (prop_name.equals("quantity") && newCap.quantity.length == 0
								|| prop_name.equals("price") && newCap.price.length == 0) {
							for (int j = 0; j < c.nConf; j++) {
								newCap.addConfiguration(c.quantity[j], c.price[j]);
							}
						} else if (prop_name.equals("features")) {
							if (obj_result.size() != 1) {
								if (domain.has("rel_out")) {
									String val_out = domain.get("rel_out").toString();
									JSONArray array_out = JSON_utils.convertJSONArray(val_out);
									for (int j = 0; j < array_out.length(); j++) {
										JSONObject out = array_out.getJSONObject(j);
										String name = out.getString("name");
										if (!name.equals("features") && !name.equals("compatible")) {
											result.accumulate("result", new JSONObject("{\"name\":\"" + name + "\"}"));
										}
									}
									return result;
								}
							} else {
								result.accumulate("result", c.toJSON());
								return result;
							}
						}
					}
					result.accumulate("result", newCap.toJSON());

				} else if (d instanceof Machine) {
					Machine m = (Machine) d;
					String str_element = "[]";
					if (proj.has("element")) {
						str_element = proj.get("element").toString();
					}
					JSONArray property = JSON_utils.convertJSONArray(str_element);
					Machine newMac = new Machine();
					newMac.name = m.name;

					for (int i = 0; i < property.length(); i++) {
						String prop_name = property.getJSONObject(i).getString("name");
						if (prop_name.equals("color")) {
							newMac.colors = m.colors;
						} else if (prop_name.equals("brand")) {
							newMac.brand = m.brand;
						} else if (prop_name.equals("compatible")) {
							newMac.compatible = m.compatible;
						} else if (prop_name.equals("time ready")) {
							newMac.time_ready = m.time_ready;
						} else if (prop_name.equals("tank capacity")) {
							newMac.tank_capacity = m.tank_capacity;
						} else if (prop_name.equals("price")) {
							newMac.price = m.price;
						} else if (prop_name.equals("weight")) {
							newMac.weight = m.weight;
						} else if (prop_name.equals("features")) {
							if (obj_result.size() != 1) {
								if (domain.has("rel_out")) {
									String val_out = domain.get("rel_out").toString();
									if (val_out.charAt(0) != '[') {
										val_out = '[' + val_out + ']';
									}
									JSONArray array_out = new JSONArray(val_out);
									for (int j = 0; j < array_out.length(); j++) {
										JSONObject out = array_out.getJSONObject(j);
										String name = out.getString("name");
										if (!name.equals("features") && !name.equals("compatible")) {
											result.accumulate("result", new JSONObject("{\"name\":\"" + name + "\"}"));
										}
									}
									// result.accumulate("result", features);
									return result;
								}
							} else {
								result.accumulate("result", m.toJSON());
								return result;
							}
						}
					}
					result.accumulate("result", newMac.toJSON());
					// } else {
					// result.accumulate("result", m.toJSON());
					// }
				}
			}
		}
		return result;
	}

	private static ArrayList<Domain> select(ArrayList<Domain> obj_result, JSONObject select, JSONArray sub) {
		if (select.has("element")) {
			String str_select = select.get("element").toString();
			if (str_select.charAt(0) != '[') {
				str_select = '[' + str_select + ']';
			}
			JSONArray condition = new JSONArray(str_select);
			for (int i = 0; i < condition.length(); i++) {
				JSONObject current = condition.getJSONObject(i);
				obj_result = select_condition(obj_result, current);
			}
		}

		if (sub != null) {
			if (sub.length() == 0) {
				obj_result = new ArrayList<>();
			} else {
				for (int i = 0; i < obj_result.size(); i++) {
					Domain d = obj_result.get(i);
					if (d instanceof Capsule) {
						Capsule c = (Capsule) d;
						if (!c.compatible.equals("all")) {
							boolean find = false;
							for (int j = 0; j < sub.length() && !find; j++) {
								String brand = sub.getJSONObject(j).getString("brand");
								find = c.compatible.equals(brand);
							}
							if (!find) {
								obj_result.remove(i--);
							}
						}
					} else if (d instanceof Machine) {
						Machine m = (Machine) d;
						if (!m.compatible.equals("all")) {
							boolean find = false;
							for (int j = 0; j < sub.length() && !find; j++) {
								String brand = sub.getJSONObject(j).getString("brand");
								find = m.compatible.equals(brand);
							}
							if (!find) {
								obj_result.remove(i--);
							}
						}
					}
				}
			}
		}
		return obj_result;
	}

	private static ArrayList<Domain> select_condition(ArrayList<Domain> obj_result, JSONObject current) {
		JSONObject max = max(obj_result);
		JSONObject min = min(obj_result);
		if (DEBUG) {
			System.out.println(max.toString(4));
			System.out.println(min.toString(4));
			System.out.println(current.toString(4));
		}
		if (obj_result.size() > 0) {
			String condition_name = current.getString("name");
			for (int i = 0; i < obj_result.size(); i++) {

				if (obj_result.get(i) instanceof Capsule) {
					Capsule c = (Capsule) obj_result.get(i);

					if (condition_name.equals("name capsule")) {
						String value = current.getJSONObject("value").getString("name");
						if (!c.name.equals(value)) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("brand")) {
						String value = current.getJSONObject("value").getString("name");
						if (!c.brand.equals(value)) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("composition")) {
						String value = current.getJSONObject("value").getString("name");
						if (!c.composition.equals(value)) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("roasting")) {
						String value = current.getJSONObject("value").getString("name");
						if (!c.roasting.equals(value)) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("intensity")) {
						if (current.has("number")) {
							int n = current.getJSONObject("number").getInt("token");
							if (current.has("mod")) {
								String name_mod = current.getJSONObject("mod").getString("name");
								if (name_mod.equals("less") && c.intensity > n) {
									obj_result.remove(i--);
								} else if (name_mod.equals("more") && c.intensity < n) {
									obj_result.remove(i--);
								}
							} else {
								if (c.intensity != n) {
									obj_result.remove(i--);
								}
							}
						}
					} else if (condition_name.equals("quantity")) {
						if (current.has("number")) {
							int n = current.getJSONObject("number").getInt("token");
							if (current.has("mod")) {
								String name_mod = current.getJSONObject("mod").getString("name");
								ArrayList<Integer> remove = new ArrayList<>();
								for (int j = 0; j < c.nConf; j++) {
									if (name_mod.equals("less") && c.quantity[j] > n) {
										remove.add(j);
									} else if (name_mod.equals("more") && c.quantity[j] < n) {
										remove.add(j);
									}
								}
								int rem = remove.size();
								if (rem > 0) {
									int[] qta = c.quantity;
									double[] pr = c.price;
									c.quantity = new int[qta.length - rem];
									c.price = new double[pr.length - rem];
									int index = 0;
									for (int j = 0; j < qta.length; j++) {
										if (!remove.contains(j)) {
											c.quantity[index] = qta[j];
											c.price[index] = pr[j];
											index++;
										} else {
											c.nConf--;
										}
									}

								}
								if (c.nConf == 0) {
									obj_result.remove(i--);
								}
							} else {
								ArrayList<Integer> remove = new ArrayList<>();
								for (int j = 0; j < c.nConf; j++) {
									if (c.quantity[j] != n) {
										remove.add(j);
									}
								}
								int rem = remove.size();
								if (rem > 0) {
									int[] qta = c.quantity;
									double[] pr = c.price;
									c.quantity = new int[qta.length - rem];
									c.price = new double[pr.length - rem];
									int index = 0;
									for (int j = 0; j < qta.length; j++) {
										if (!remove.contains(j)) {
											c.quantity[index] = qta[j];
											c.price[index] = pr[j];
											index++;
										} else {
											c.nConf--;
										}
									}

								}
								if (c.nConf == 0) {
									obj_result.remove(i--);
								}
							}
						}
					} else if (condition_name.equals("price")) {
						if (current.has("number")) {
							double n = current.getJSONObject("number").getDouble("token");
							if (current.has("mod")) {
								String name_mod = current.getJSONObject("mod").getString("name");
								ArrayList<Integer> remove = new ArrayList<>();
								for (int j = 0; j < c.nConf; j++) {
									if (name_mod.equals("less") && c.price[j] > n) {
										remove.add(j);
									} else if (name_mod.equals("more") && c.price[j] < n) {
										remove.add(j);
									}
								}
								int rem = remove.size();
								if (rem > 0) {
									int[] qta = c.quantity;
									double[] pr = c.price;
									c.quantity = new int[qta.length - rem];
									c.price = new double[pr.length - rem];
									int index = 0;
									for (int j = 0; j < qta.length; j++) {
										if (!remove.contains(j)) {
											c.quantity[index] = qta[j];
											c.price[index] = pr[j];
											index++;
										} else {
											c.nConf--;
										}
									}

								}
								if (c.nConf == 0) {
									obj_result.remove(i--);
								}
							} else {
								ArrayList<Integer> remove = new ArrayList<>();
								for (int j = 0; j < c.nConf; j++) {
									if (c.price[j] != n) {
										remove.add(j);

									}
								}
								int rem = remove.size();
								if (rem > 0) {
									int[] qta = c.quantity;
									double[] pr = c.price;
									c.quantity = new int[qta.length - rem];
									c.price = new double[pr.length - rem];
									int index = 0;
									for (int j = 0; j < qta.length; j++) {
										if (!remove.contains(j)) {
											c.quantity[index] = qta[j];
											c.price[index] = pr[j];
											index++;
										} else {
											c.nConf--;
										}
									}

								}
								if (c.nConf == 0) {
									obj_result.remove(i--);
								}
							}
						}
					} else if (condition_name.equals("promo")) {
						String value = current.getJSONObject("value").getString("name");
						boolean promo = value.contains("true");
						if (c.promo != promo) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("bio")) {
						String value = current.getJSONObject("value").getString("name");
						boolean bio = value.contains("true");
						if (c.bio != bio) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("dec")) {
						String value = current.getJSONObject("value").getString("name");
						boolean dec = value.contains("true");
						if (c.dec != dec) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("cheap")) {
						String value = current.getJSONObject("value").getString("name");
						boolean cheap = value.contains("true");
						if (c.cheap != cheap) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("expensive")) {
						String value = current.getJSONObject("value").getString("name");
						boolean expensive = value.contains("true");
						if (c.expensive != expensive) {
							obj_result.remove(i--);
						}
					}
				} else if (obj_result.get(i) instanceof Machine) {
					Machine m = (Machine) obj_result.get(i);

					if (condition_name.equals("name machine")) {
						String value = current.getJSONObject("value").getString("name");
						if (!m.name.equals(value)) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("brand")) {
						String value = current.getJSONObject("value").getString("name");
						if (!m.brand.equals(value)) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("color")) {
						String value = current.getJSONObject("value").getString("name");
						boolean find = false;
						for (int k = 0; k < m.colors.length && !find; k++) {
							find = m.colors[k].equals(value);
						}
						if (!find) {
							obj_result.remove(i--);
						}

					} else if (condition_name.equals("tank capacity")) {
						if (current.has("number")) {
							double n = current.getJSONObject("number").getDouble("token");
							if (current.has("mod")) {
								String name_mod = current.getJSONObject("mod").getString("name");
								if (name_mod.equals("less") && m.tank_capacity > n) {
									obj_result.remove(i--);
								} else if (name_mod.equals("more") && m.tank_capacity < n) {
									obj_result.remove(i--);
								}
							} else if (m.tank_capacity != n) {
								obj_result.remove(i--);
							}
						}

					} else if (condition_name.equals("time ready")) {
						if (current.has("number")) {
							double n = current.getJSONObject("number").getDouble("token");
							if (current.has("mod")) {
								String name_mod = current.getJSONObject("mod").getString("name");
								if (name_mod.equals("less") && m.time_ready > n) {
									obj_result.remove(i--);
								} else if (name_mod.equals("more") && m.time_ready < n) {
									obj_result.remove(i--);
								}
							} else if (m.time_ready != n) {
								obj_result.remove(i--);
							}
						}
					} else if (condition_name.equals("weight")) {
						if (current.has("number")) {
							double n = current.getJSONObject("number").getDouble("token");
							if (current.has("mod")) {
								String name_mod = current.getJSONObject("mod").getString("name");
								if (name_mod.equals("less") && m.weight > n) {
									obj_result.remove(i--);
								} else if (name_mod.equals("more") && m.weight < n) {
									obj_result.remove(i--);
								}
							} else if (m.weight != n) {
								obj_result.remove(i--);
							}
						}
						// else if (current.has("mod")) {
						// String name_mod =
						// current.getJSONObject("mod").getString("name");
						// if (name_mod.equals("less")) {
						// if (m.weight>min.getDouble("weight")) {
						// obj_result.remove(i--);
						// }
						// } else if (name_mod.equals("more")) {
						// if (m.weight<max.getDouble("weight")) {
						// obj_result.remove(i--);
						// }
						// }
						// }
					} else if (condition_name.equals("price")) {
						if (current.has("number")) {
							double n = current.getJSONObject("number").getDouble("token");
							if (current.has("mod")) {
								String name_mod = current.getJSONObject("mod").getString("name");
								if (name_mod.equals("less") && m.price > n) {
									obj_result.remove(i--);
								} else if (name_mod.equals("more") && m.price < n) {
									obj_result.remove(i--);
								}
							} else if (m.price != n) {
								obj_result.remove(i--);
							}
						}
					} else if (condition_name.equals("promo")) {
						String value = current.getJSONObject("value").getString("name");
						boolean promo = value.contains("true");
						if (m.promo != promo) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("thermoblock")) {
						String value = current.getJSONObject("value").getString("name");
						boolean thermoblock = value.contains("true");
						if (m.thermoblock != thermoblock) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("milkProgram")) {
						String value = current.getJSONObject("value").getString("name");
						boolean milkProgram = value.contains("true");
						if (m.milkProgram != milkProgram) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("cheap")) {
						String value = current.getJSONObject("value").getString("name");
						boolean cheap = value.contains("true");
						if (m.cheap != cheap) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("expensive")) {
						String value = current.getJSONObject("value").getString("name");
						boolean expensive = value.contains("true");
						if (m.expensive != expensive) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("programmable")) {
						String value = current.getJSONObject("value").getString("name");
						boolean programmable = value.contains("true");
						if (m.programmable != programmable) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("autoOFF")) {
						String value = current.getJSONObject("value").getString("name");
						boolean autoOFF = value.contains("true");
						if (m.autoOFF != autoOFF) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("light")) {
						String value = current.getJSONObject("value").getString("name");
						boolean light = value.contains("true");
						if (m.light != light) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("heavy")) {
						String value = current.getJSONObject("value").getString("name");
						boolean heavy = value.contains("true");
						if (m.heavy != heavy) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("fast")) {
						String value = current.getJSONObject("value").getString("name");
						boolean fast = value.contains("true");
						if (m.fast != fast) {
							obj_result.remove(i--);
						}
					} else if (condition_name.equals("slow")) {
						String value = current.getJSONObject("value").getString("name");
						boolean slow = value.contains("true");
						if (m.slow != slow) {
							obj_result.remove(i--);
						}
					}

				}
			}

		}
		return obj_result;
	}

	private static JSONObject max(ArrayList<Domain> obj_result) {
		JSONObject result = new JSONObject();
		for (Domain d : obj_result) {
			if (d instanceof Capsule) {
				Capsule c = (Capsule) d;
				if (result.has("intensity")) {
					int intensity = result.getInt("intensity");
					if (c.intensity > intensity) {
						result.put("intensity", c.intensity);
					}
				} else {
					result.put("intensity", c.intensity);
				}

				if (result.has("price")) {
					double price = result.getDouble("price");
					for (int k = 0; k < c.price.length; k++) {
						if (c.price[k] > price) {
							result.put("price", c.price[k]);
							price = c.price[k];
						}
					}
				} else {
					double price = c.price[0];
					result.put("price", c.price[0]);
					for (int k = 1; k < c.price.length; k++) {
						if (c.price[k] > price) {
							result.put("price", c.price[k]);
							price = c.price[k];
						}
					}
				}

				if (result.has("quantity")) {
					int qta = result.getInt("quantity");
					for (int k = 0; k < c.quantity.length; k++) {
						if (c.quantity[k] > qta) {
							result.put("quantity", c.quantity[k]);
							qta = c.quantity[k];
						}
					}
				} else {
					int qta = c.quantity[0];
					result.put("quantity", c.quantity[0]);
					for (int k = 1; k < c.quantity.length; k++) {
						if (c.quantity[k] > qta) {
							result.put("quantity", c.quantity[k]);
							qta = c.quantity[k];
						}
					}
				}
			} else if (d instanceof Machine) {
				Machine m = (Machine) d;
				if (result.has("tank capacity")) {
					double tank_capacity = result.getDouble("tank capacity");
					if (m.tank_capacity > tank_capacity) {
						result.put("tank capacity", m.tank_capacity);
					}
				} else {
					result.put("tank capacity", m.tank_capacity);
				}

				if (result.has("time ready")) {
					double time_ready = result.getDouble("time ready");
					if (m.time_ready > time_ready) {
						result.put("time ready", m.time_ready);
					}
				} else {
					result.put("time ready", m.time_ready);
				}

				if (result.has("price")) {
					double price = result.getDouble("price");
					if (m.price > price) {
						result.put("price", m.price);
					}
				} else {
					result.put("price", m.price);
				}

				if (result.has("weight")) {
					double weight = result.getDouble("weight");
					if (m.weight > weight) {
						result.put("weight", m.weight);
					}
				} else {
					result.put("weight", m.weight);
				}
			}
		}
		return result;
	}

	private static JSONObject min(ArrayList<Domain> obj_result) {
		JSONObject result = new JSONObject();
		for (Domain d : obj_result) {
			if (d instanceof Capsule) {
				Capsule c = (Capsule) d;
				if (result.has("intensity")) {
					int intensity = result.getInt("intensity");
					if (c.intensity < intensity) {
						result.put("intensity", c.intensity);
					}
				} else {
					result.put("intensity", c.intensity);
				}

				if (result.has("price")) {
					double price = result.getDouble("price");
					for (int k = 0; k < c.price.length; k++) {
						if (c.price[k] < price) {
							result.put("price", c.price[k]);
							price = c.price[k];
						}
					}
				} else {
					double price = c.price[0];
					result.put("price", c.price[0]);
					for (int k = 1; k < c.price.length; k++) {
						if (c.price[k] < price) {
							result.put("price", c.price[k]);
							price = c.price[k];
						}
					}
				}

				if (result.has("quantity")) {
					int qta = result.getInt("quantity");
					for (int k = 0; k < c.quantity.length; k++) {
						if (c.quantity[k] < qta) {
							result.put("quantity", c.quantity[k]);
							qta = c.quantity[k];
						}
					}
				} else {
					int qta = c.quantity[0];
					result.put("quantity", c.quantity[0]);
					for (int k = 1; k < c.quantity.length; k++) {
						if (c.quantity[k] < qta) {
							result.put("quantity", c.quantity[k]);
							qta = c.quantity[k];
						}
					}
				}
			} else if (d instanceof Machine) {
				Machine m = (Machine) d;
				if (result.has("tank capacity")) {
					Double tank_capacity = result.getDouble("tank capacity");
					if (m.tank_capacity < tank_capacity) {
						result.put("tank capacity", m.tank_capacity);
					}
				} else {
					result.put("tank capacity", m.tank_capacity);
				}

				if (result.has("time ready")) {
					Double time_ready = result.getDouble("time ready");
					if (m.time_ready < time_ready) {
						result.put("time ready", m.time_ready);
					}
				} else {
					result.put("time ready", m.time_ready);
				}

				if (result.has("price")) {
					Double price = result.getDouble("price");
					if (m.price < price) {
						result.put("price", m.price);
					}
				} else {
					result.put("price", m.price);
				}

				if (result.has("weight")) {
					Double weight = result.getDouble("weight");
					if (m.weight < weight) {
						result.put("weight", m.weight);
					}
				} else {
					result.put("weight", m.weight);
				}
			}
		}
		return result;
	}

	public static ArrayList<Domain> cloneList(ArrayList<Domain> list) {
		ArrayList<Domain> clone = new ArrayList<>(list.size());
		for (Domain item : list) {
			clone.add(item.clone());
		}
		return clone;
	}
}
