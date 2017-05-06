package dataBase;

import org.json.JSONObject;

public class Machine extends Domain {
	String name;
	String[] colors;
	double time_ready = Double.MIN_VALUE;
	double tank_capacity = Double.MIN_VALUE;;
	double price = Double.MIN_VALUE;;
	double weight = Double.MIN_VALUE;
	String brand;
	String compatible = null;
	boolean promo;
	boolean set_promo;
	boolean thermoblock;
	boolean set_thermoblock;
	boolean milkProgram;
	boolean set_milkProgram;
	boolean cheap;
	boolean set_cheap;
	boolean expensive;
	boolean set_expensive;
	boolean programmable;
	boolean set_programmable;
	boolean autoOFF;
	boolean set_autoOFF;
	boolean light;
	boolean set_light;
	boolean heavy;
	boolean set_heavy;
	boolean fast;
	boolean set_fast;
	boolean slow;
	boolean set_slow;
	
	public Machine(String name, String[] colors, double time, double tank, double price, double weight, String brand,
			boolean promo, boolean thermoblock, boolean milkProgram, boolean cheap, boolean expensive,
			boolean programmable, boolean autoOFF,boolean light,boolean heavy,boolean fast, boolean slow) {
		this.name = name;
		this.colors = colors;
		tank_capacity = tank;
		time_ready = time;
		this.price = price;
		this.weight = weight;
		this.brand = brand;
		compatible = "lavazza";
		set_promo = true;
		this.promo = promo;
		this.thermoblock = thermoblock;
		this.set_thermoblock = true;
		this.milkProgram = milkProgram;
		this.set_milkProgram = true;
		this.cheap = cheap;
		set_cheap = true;
		this.expensive = expensive;
		set_expensive = true;
		this.programmable = programmable;
		set_programmable = true;
		this.autoOFF = autoOFF;
		set_autoOFF = true;
		this.light = light;
		set_light = true;
		this.heavy = heavy;
		set_heavy = true;
		this.fast = fast;
		set_fast = true;
		this.slow = slow;
		set_slow = true;
	}

	public Machine() {
		// TODO Auto-generated constructor stub
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		result.accumulate("name", name);
		result.accumulate("color", colors);
		if (time_ready != Double.MIN_VALUE)
			result.accumulate("time ready", time_ready);
		if (tank_capacity != Double.MIN_VALUE)
			result.accumulate("tank capacity", tank_capacity);
		if (price != Double.MIN_VALUE)
			result.accumulate("price", price);
		if (weight != Double.MIN_VALUE)
			result.accumulate("weight", weight);
		result.accumulate("brand", brand);
		result.accumulate("compatible", compatible);
		if (set_promo)
			result.accumulate("promo", promo);
		if (set_thermoblock)
			result.accumulate("thermoblock", thermoblock);
		if (set_milkProgram)
			result.accumulate("milkProgram", milkProgram);
		if (set_cheap)
			result.accumulate("cheap", cheap);
		if (set_expensive)
			result.accumulate("expensive", expensive);
		if (set_programmable)
			result.accumulate("programmable", programmable);
		if (set_autoOFF)
			result.accumulate("autoOFF", autoOFF);
		if (set_light)
			result.accumulate("light", light);
		if (set_heavy)
			result.accumulate("heavy", heavy);
		if (set_fast)
			result.accumulate("fast", fast);
		if (set_slow)
			result.accumulate("slow", slow);

		return result;
	}

	@Override
	public Domain clone() {
		return new Machine(name, colors, time_ready, tank_capacity, price, weight, brand, promo, thermoblock,
				milkProgram, cheap, expensive, programmable, autoOFF,light,heavy,fast,slow);
	}
}
