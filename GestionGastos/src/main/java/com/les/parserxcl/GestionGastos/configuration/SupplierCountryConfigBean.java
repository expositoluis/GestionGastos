/**
 * 
 */
package com.les.parserxcl.GestionGastos.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cg00046
 *
 */
@Component
@ConfigurationProperties(prefix="suppliercountry")
public class SupplierCountryConfigBean {
	
	private Map<Integer, String> deumapkeyvalue = new HashMap<Integer, String>();
	private Map<Integer, String> othermapkeyvalue = new HashMap<Integer, String>();
	private Integer position = new Integer(0);
	private String countrydeu;
	
	
	public Map<Integer, String> getDeu() {
		return deumapkeyvalue;
	}
	public void setDeu(Map<Integer, String> constant) {
		this.deumapkeyvalue = constant;
	}
	public Map<Integer, String> getOther() {
		return othermapkeyvalue;
	}
	public void setoTHER(Map<Integer, String> constant) {
		this.othermapkeyvalue = constant;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getCountrydeu() {
		return countrydeu;
	}
	public void setCountrydeu(String valor) {
		this.countrydeu = valor;
	}

	public HashMap<Integer, String> solveConfiguration(String cellvalue){
		HashMap<Integer, String> retvalue = new HashMap<Integer, String>();
		switch(cellvalue.toUpperCase()){
		case "DEU" :
				retvalue = (HashMap<Integer, String>) deumapkeyvalue;
				break;
		default:
			retvalue = (HashMap<Integer, String>) othermapkeyvalue;
			break;
		}
			
		return retvalue;
	}
	
}
