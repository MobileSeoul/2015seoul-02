package com.it9.project1;

public class CommonUtils {

	public static boolean isEmpty(String value){
		
		boolean isEmpty = false;
		
		if((value == null) || value == "")
			isEmpty = true;
		
		return isEmpty;
	}
	
}
