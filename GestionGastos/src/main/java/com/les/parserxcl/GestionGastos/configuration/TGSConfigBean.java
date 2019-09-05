/**
 * 
 */
package com.les.parserxcl.GestionGastos.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cg00046
 *
 */
@Component
@ConfigurationProperties(prefix="tgs")
public class TGSConfigBean {
	
	private Map<Integer, String> constantmapkeyvalue = new HashMap<Integer, String>();
	
	public Map<Integer, String> getConstant() {
		return constantmapkeyvalue;
	}
	public void setConstant(Map<Integer, String> constant) {
		this.constantmapkeyvalue = constant;
	}

}
