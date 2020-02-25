package com.cg.drinkdelight.dao;

import java.util.logging.Logger;

public class UtilityClass {
	
	public void showMessage(String msg,int code) {
		Logger log=Logger.getLogger(UtilityClass.class.getName());
		if(code==1) {
			System.out.println(msg);
		}
		if(code==2) {
			log.info(msg);
		}
	}

}
