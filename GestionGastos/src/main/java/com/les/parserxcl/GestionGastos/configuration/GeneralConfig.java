/**
 * 
 */
package com.les.parserxcl.GestionGastos.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cg00046
 *
 */
@Component
@ConfigurationProperties(prefix="generalconfig")
public class GeneralConfig {

	private String filepathorigen;
	private String filepathtratados;
	private String filepathcsvgenerados;

	public String getFilepathorigen() {
		return filepathorigen;
	}

	public void setFilepathorigen(String filepathorigen) {
		this.filepathorigen = filepathorigen;
	}

	public String getFilepathtratados() {
		return filepathtratados;
	}

	public void setFilepathtratados(String filepathtratados) {
		this.filepathtratados = filepathtratados;
	}

	public String getFilepathcsvgenerados() {
		return filepathcsvgenerados;
	}

	public void setFilepathcsvgenerados(String filepathcsvgenerados) {
		this.filepathcsvgenerados = filepathcsvgenerados;
	}
	
	
	
}
