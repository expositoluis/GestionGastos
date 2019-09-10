/**
 * 
 */
package com.les.parserxcl.GestionGastos.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.les.parserxcl.GestionGastos.beans.OutputBean;
import com.les.parserxcl.GestionGastos.configuration.BaseConfig;
import com.les.parserxcl.GestionGastos.configuration.GeneralConfig;
import com.les.parserxcl.GestionGastos.configuration.SupplierCountryConfigBean;
import com.les.parserxcl.GestionGastos.configuration.TGRConfigBean;
import com.les.parserxcl.GestionGastos.configuration.TGSConfigBean;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author cg00046
 *
 */
@Component
public class Excel2csv {

	/*

	*/
		@Autowired
		BaseConfig baseconfig;
		@Autowired
		TGSConfigBean tgsconfigbean;
		@Autowired
		TGRConfigBean tgrconfigbean;
		@Autowired
		SupplierCountryConfigBean suppliercountryconfigbean;

		@Autowired
		OutputBean outputbean;

		@Autowired
		GeneralConfig generalconfig;
		
		@Autowired
		EmailSender emailsender;
	
	    public void walkin() throws IOException {
	    	File dirin = new File(generalconfig.getFilepathorigen());
	        File listFile[] = dirin.listFiles();
	         
	        if (listFile != null) {
	            for (int j = 0; j < listFile.length; j++) {
	                if (listFile[j].isDirectory()) {
	                	System.out.println("dir : " + listFile[j].getPath());
	                } else {
	                	System.out.println("Fichero : "+listFile[j].getPath());
	                	try{
	                		transform(listFile[j].getPath(), generalconfig.getFilepathcsvgenerados()+listFile[j].getName()+System.currentTimeMillis()+".csv");
	                		moveprocessedfiles(listFile[j]);
	                	}catch (Exception e){
	                		System.out.println("Error " +  e);
	                		System.out.println("Error");
	                		throw e;
	                	}
	                }
	            }
	        }
	    }
	
		private void moveprocessedfiles(File dir){
	        //File listFile[] = dir.listFiles();

	        if (dir != null) {
	            //for (int j = 0; j < listFile.length; j++) {
	                if (dir.isDirectory()) {
	                	System.out.println("dir : " + dir.getPath());
	                	//log.info("Directorio : "+listFile[j].getPath());
	                	//String pathdeentrada = listFile[j].getPath();
	                	//String diferenciapath = pathdeentrada.substring(dir.getAbsolutePath().length(), pathdeentrada.length());
	                	//System.out.println(diferenciapath);
	                	
	                	//listFile[j].renameTo(dest)
	                	//listFile[j].renameTo(new File(FOLDERPROCESADOS+diferenciapath+System.currentTimeMillis()));
	                	//moveprocessedfiles(listFile[j]);
	                	
	                } else {
	                	System.out.println("Fichero : "+dir.getPath());
	                	//contadorfilanueva = 1;
	                	String pathdeentrada = dir.getPath();
	                	String diferenciapath = pathdeentrada.substring(dir.getAbsolutePath().length(), pathdeentrada.length());
	                	dir.renameTo(new File(generalconfig.getFilepathtratados()+dir.getName()));
	                	//moveprocessedfiles(listFile[j]);
	                }
	            }

	        }

	    
		public boolean transform(String pathxcel, String pathcsv) throws IOException{
			Iterator<Row> rowIterator=null;
			FileInputStream input_document = null;
        	//First we read the Excel file in binary format into FileInputStream
        	File f = new File(pathxcel);
        	input_document = new FileInputStream(f);
        	String filename = f.getName();
        	String entity = filename.substring(0, 3).equalsIgnoreCase("TGS")?"TGS":"TGR";
	        try{
		        // Read workbook into HSSFWorkbook
		        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
		        // Read worksheet into HSSFSheet
		        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
		     // To iterate over the rows
		        rowIterator = my_worksheet.iterator();
	        }catch(org.apache.poi.poifs.filesystem.OfficeXmlFileException oxmlfe){
	        	//First we read the Excel file in binary format into FileInputStream
		        input_document = new FileInputStream(new File(pathxcel));
	        	XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
	        	XSSFSheet my_worksheet = my_xls_workbook.getSheet("Detail");
	        	// To iterate over the rows
		        rowIterator = my_worksheet.iterator();
	        }
	        Map<Integer,List<Integer>> mapaconcatvalueslistavalores = baseconfig.getConcatListValuesFrom();
	        
	        // OpenCSV writer object to create CSV file
	        FileWriter my_csv=new FileWriter(pathcsv);
	        CSVWriter my_csv_output=new CSVWriter(my_csv, ';',CSVWriter.NO_QUOTE_CHARACTER);
	        
	        int availablerow = 0;
	        
	        //Loop through rows.
	        while(rowIterator.hasNext()) {
	                Row row = rowIterator.next();
	                if(isAvailable(row)){
	                	availablerow++;
		                OutputBean outputbean = OutputBean.OutputBeanFactory();
		                outputbean.setNumberofvalues(baseconfig.getNumberofvalues());
		                outputbean.setEmptyValues(baseconfig.getEmptylistvalues());
		                outputbean.setConstantValues(baseconfig.getConstant());
		                
		                if(entity.equalsIgnoreCase("TGS")){
		                	outputbean.setConstantValues(tgsconfigbean.getConstant());
		                }else{
		                	outputbean.setConstantValues(tgrconfigbean.getConstant());
		                }

		                Map<Integer, Integer> movetofrommapkeyvalue = baseconfig.getMovetofrom();
		                movetofrommapkeyvalue.forEach((k,v)-> {	                	
		                	outputbean.setcsvdatalineposition(k.intValue(),getCellValueFromCell(row, v));
		        		});
		                mapaconcatvalueslistavalores.forEach((k,lv)->{
		                	String valorconcatenado = new String();
		                	for(Integer position : lv){
		                		String cellvalue = getCellValueFromCell(row, position);
		                		valorconcatenado = valorconcatenado.concat(cellvalue).concat(baseconfig.getConcatseparator());
		                	}
		                	outputbean.setcsvdatalineposition(k, valorconcatenado);
		                });
		                
		                String valuesuppliercountry = getCellValueFromCell(row, suppliercountryconfigbean.getPosition());
		                HashMap<Integer, String> valuesbysuppliercountry = suppliercountryconfigbean.solveConfiguration(valuesuppliercountry);
		                valuesbysuppliercountry.forEach((k,v)-> {	                	
		                	outputbean.setcsvdatalineposition(k.intValue(),v);
		        		});
		                outputbean.setFranumberreportdate(getCellValueFromCell(row, baseconfig.getFranumberreportdate()),
		                		entity, availablerow, baseconfig.getFranumberposition());
		    	        String[] myoutputbean = outputbean.getCsvdataline();
		    	        my_csv_output.writeNext(myoutputbean);
	                }

	        }
	        my_csv_output.close(); //close the CSV file
	        //we created our file..!!
	        input_document.close(); //close xls
	        
	        emailsender.sendEmail();
	        return true;
		}
		
		private String getCellValueFromCell(Row row, int position){
			CellType  ct = row.getCell(position).getCellType();
			Cell cell = row.getCell(position);
			String datavalue = new String();
            // Set the cell data value
            switch (ct) {
                case BLANK:
                	datavalue = (new Boolean(cell.getBooleanCellValue())).toString();
                    break;
                case ERROR:
                	datavalue = "ERROR";
                    break;
                case FORMULA:
                	datavalue = cell.getCellFormula();
                    break;
                case NUMERIC:
                	datavalue = new Double(cell.getNumericCellValue()).toString();
                    break;
                case STRING:
                	datavalue = cell.getRichStringCellValue().getString();
                    break;
            }
            return datavalue;
		}
		
		
		private boolean isAvailable(Row row){
			boolean retval = false;
			Cell cell = row.getCell(0);
			 if (cell != null ) {
					CellType  ct = cell.getCellType();
					String datavalue = new String();
		            // Set the cell data value
		            switch (ct) {
		                case BLANK:
		                	datavalue = (new Boolean(cell.getBooleanCellValue())).toString();
		                    break;
		                case ERROR:
		                	datavalue = "ERROR";
		                    break;
		                case FORMULA:
		                	datavalue = cell.getCellFormula();
		                    break;
		                case NUMERIC:
		                	datavalue = new Double(cell.getNumericCellValue()).toString();
		                    break;
		                case STRING:
		                	datavalue = cell.getRichStringCellValue().getString();
		                	retval=datavalue.equalsIgnoreCase("CORPORATE")?true:false;
		                    break;
		            }

			 }
            return retval;			
		}
}
