package knowledge;

import plan.ActionPlan;

public class KnowledgePlan {
	
	public static ActionPlan plan(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> act = Class.forName(className);
		return (ActionPlan) act.newInstance();
	}

}
