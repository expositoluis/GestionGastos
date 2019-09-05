/**
 * 
 */
package com.les.parserxcl.GestionGastos.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author cg00046
 *
 */

@Component
@ConfigurationProperties(prefix="outputbean")
public class BaseConfig {
	
	@Value("${outputbean.numberofvalues}")
	private Integer numberofvalues;
	
	@Value("${outputbean.concatseparator}")
	private String concatseparator;
	

	@Value("${outputbean.empty}")
	private String outputbeanempty;
	
	@Value("${outputbean.franumberreportdate}")
	private Integer franumberreportdate;
	
	@Value("${outputbean.franumberposition}")
	private Integer franumberposition;
	
	private Map<Integer, String> constantmapkeyvalue = new HashMap<Integer, String>();
	private Map<Integer, Integer> movetofrommapkeyvalue = new HashMap<Integer, Integer>();
	private Map<Integer, String> concatfromvalue = new HashMap<Integer, String>();
	private Map<Integer,List<Integer>> concatfromvaluesposition = new HashMap<Integer, List<Integer>>();
	
	
	public String getConcatseparator() {
		return concatseparator;
	}
	public void setConcatseparator(String concatseparator) {
		this.concatseparator = concatseparator;
	}

	public Integer getNumberofvalues() {
		return numberofvalues;
	}
	public void setNumberofvalues(Integer numberofvalues) {
		this.numberofvalues = numberofvalues;
	}
	public Map<Integer, String> getConstant() {
		return constantmapkeyvalue;
	}
	public void setConstant(Map<Integer, String> constant) {
		this.constantmapkeyvalue = constant;
	}
	
	public Map<Integer, Integer> getMovetofrom() {
		return movetofrommapkeyvalue;
	}
	public void setMovetofrom(Map<Integer, Integer> movetofrom) {
		this.movetofrommapkeyvalue = movetofrom;
	}

	public Map<Integer, String> getConcatfrom() {
		return concatfromvalue;
	}
	public void setConcatfrom(Map<Integer, String> concatconfigmap) {
		this.concatfromvalue = concatconfigmap;
	}

	public Integer getFranumberreportdate() {
		return franumberreportdate;
	}
	public void setFranumberreportdate(Integer franumberreportdate) {
		this.franumberreportdate = franumberreportdate;
	}
	public Integer getFranumberposition() {
		return franumberposition;
	}
	public void setFranumberposition(Integer franumberposition) {
		this.franumberposition = franumberposition;
	}

	
	public int[] getEmptylistvalues(){
		String[] listvalues = outputbeanempty.split(",");		
		int[] listaemptylistvalues = new int[listvalues.length];
		int idx = 0;
		for(String valorstr : listvalues){
			listaemptylistvalues[idx] = new Integer(valorstr).intValue();
			idx++;
		}
		return listaemptylistvalues;
	}
	
	public Map<Integer,List<Integer>> getConcatListValuesFrom(){
		
        concatfromvalue.forEach((k,v)->{
        	String valorescomcatfromlinea = concatfromvalue.get(k);
    		String[] listavalores = valorescomcatfromlinea.split(",");
    		List<Integer> listavaloresinteger = new ArrayList<Integer>(listavalores.length);
    		for(String s : listavalores){
    			listavaloresinteger.add(new Integer(s));
    		}
    		concatfromvaluesposition.put(k, listavaloresinteger);
        });
        
		return concatfromvaluesposition;
	}
}
