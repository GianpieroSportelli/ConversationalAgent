package test.plan;

import knowledge.KnowledgePlan;
import plan.InfoPlan;

public class PlanTest {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		System.out.println(KnowledgePlan.plan(InfoPlan.getClassName()).getName());
	}

}
