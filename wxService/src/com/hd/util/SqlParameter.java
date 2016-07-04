package com.hd.util;

import java.util.ArrayList;

public class SqlParameter extends ArrayList<Object> {
	
	public SqlParameter() {
		// TODO Auto-generated constructor stub
	}
	
	public SqlParameter(Object... objects) {
		for(Object obj:objects){
			this.add(obj);
		}
		// TODO Auto-generated constructor stub
	}
}
