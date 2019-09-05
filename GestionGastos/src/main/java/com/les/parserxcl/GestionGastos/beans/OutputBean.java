/**
 * 
 */
package com.les.parserxcl.GestionGastos.beans;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.les.parserxcl.GestionGastos.configuration.BaseConfig;

/**
 * @author cg00046
 *
 */
@Component
public class OutputBean {
	

	Integer numberofvalues=0;
	private String[] csvdataline = null;;
	
	
	public Integer getNumberofvalues() {
		return numberofvalues;
	}

	public void setNumberofvalues(Integer numberofvalues) {
		this.numberofvalues = numberofvalues;
		csvdataline = new String[numberofvalues];
	}

	public String[] getCsvdataline(){
		return this.csvdataline;
	}
	
	public void setCsvdataline(){
		return;
	}
	
	public void setcsvdatalineposition(int position, String value){
		csvdataline[position]= value;
		return;
	}

	public void setEmptyValues(int[] emptypositions){
		for(int emptyposition : emptypositions){
			this.setcsvdatalineposition(emptyposition, "");
		}
		return;
	}
	
	public void setConstantValues(Map<Integer,String> mapaclavevalor){
		mapaclavevalor.forEach((k,v)-> {
			setcsvdatalineposition(k,v);
		});
	}
	
	
	public void setConcatValuesFomPositionList(int position, List<String> listavaluesfrom){
		String valoresconcatenados = new String();
		listavaluesfrom.stream().forEach((v)-> {
			valoresconcatenados.concat("|" + v);
		});
		setcsvdatalineposition(position,valoresconcatenados);
	}
	
	public void setFranumberreportdate(String cellValue, String entityname ,int fralinenumber, Integer position){
		String franumber = new String();
		String[] valuesplitter = cellValue.split("/");
		franumber = valuesplitter[0]+valuesplitter[2]+entityname+fralinenumber;
		setcsvdatalineposition(position,franumber);
	}
	
	public static OutputBean OutputBeanFactory(){
		return new OutputBean();
	}
	
}
