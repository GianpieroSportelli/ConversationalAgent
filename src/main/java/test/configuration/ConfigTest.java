package test.configuration;

import configuration.Config;

public class ConfigTest {

	public static void main(String[] args) {
		System.out.println("debug.dm="+Config.getDialogManagerDebug());
		System.out.println("debug.deep.dm="+Config.getDialogManagerDeepDebug());
		System.out.println("debug.db="+Config.getDBDebug());
		System.out.println("debug.nlg="+Config.getNLGDebug());
		System.out.println("debug.opener="+Config.getopeNERDebug());
		System.out.println("debug.semantic.pool="+Config.getSemanticPoolDebug());
		System.out.println("debug.token.allign="+Config.getTokenAllignDebug());
		System.out.println("debug.deep.token.allign="+Config.getTokenAllignDeepDebug());
		System.out.println("debug.translate.token="+Config.getTranslateTokenDebug());
		System.out.println("debug.deep.translate.token="+Config.getTranslateTokenDeepDebug());
		System.out.println("path.semantic.net="+Config.getPathSemanticNet());
		System.out.println("path.example="+Config.getPathExample());
                System.out.println("token.telegram="+Config.TELEGRAM_TOKEN);
	}

}
