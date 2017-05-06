package dataBase;

import org.json.JSONObject;

public class Capsule extends Domain {
	String name;
	String composition;
	String roasting;
	int intensity = Integer.MIN_VALUE;
	int nConf = 0;
	int[] quantity = new int[nConf];
	double[] price = new double[nConf];
	String brand = null;
	String compatible = null;
	boolean promo;
	boolean set_promo;
	boolean bio;
	boolean set_bio;
	boolean dec;
	boolean set_dec;
	boolean cheap;
	boolean set_cheap;
	boolean expensive;
	boolean set_expensive;

	public Capsule(String name, String comp, String roasting, int intensity, int qta, double p, boolean promo,boolean bio,boolean dec,boolean cheap,boolean expensive) {
		this.name = name;
		this.composition = comp;
		this.roasting = roasting;
		this.intensity = intensity;
		addConfiguration(qta, p);
		compatible = "all";
		brand = "lavazza";
		this.promo = promo;
		set_promo = true;
		this.bio=bio;
		set_bio=true;
		this.dec=dec;
		set_dec=true;
		this.cheap=cheap;
		set_cheap=true;
		this.expensive=expensive;
		set_expensive=true;
	}

	public Capsule() {
		// TODO Auto-generated constructor stub
	}

	public void addConfiguration(int qta, double p) {
		nConf++;
		int[] newQuantity = new int[nConf];
		double[] newPrice = new double[nConf];

		for (int i = 0; i < nConf - 1; i++) {
			newQuantity[i] = quantity[i];
			newPrice[i] = price[i];
		}

		newQuantity[nConf - 1] = qta;
		newPrice[nConf - 1] = p;

		quantity = newQuantity;
		price = newPrice;
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		result.accumulate("name", name);
		result.accumulate("composition", composition);
		result.accumulate("roasting", roasting);
		if (intensity != Integer.MIN_VALUE) {
			result.accumulate("intensity", intensity);
		}
		for (int i = 0; i < nConf; i++) {
			JSONObject pack = new JSONObject();
			pack.accumulate("quantity", quantity[i]);
			pack.accumulate("price", price[i]);
			result.accumulate("pack", pack);
		}
		result.accumulate("brand", brand);
		result.accumulate("compatible", compatible);
		if (set_promo)
			result.accumulate("promo", promo);
		if(set_bio)
			result.accumulate("bio", bio);
		if(set_dec)
			result.accumulate("dec", dec);
		return result;
	}

	@Override
	public Domain clone() {
		Capsule c = new Capsule(name, composition, roasting, intensity, quantity[0], price[0], promo,bio,dec,cheap,expensive);
		if (quantity.length > 1) {
			for (int i = 1; i < quantity.length; i++) {
				c.addConfiguration(quantity[i], price[i]);
			}
		}
		return c;
	}

}
