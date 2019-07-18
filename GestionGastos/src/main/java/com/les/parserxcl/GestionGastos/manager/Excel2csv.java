/**
 * 
 */
package com.les.parserxcl.GestionGastos.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author cg00046
 *
 */
@Component
public class Excel2csv {

	
		public boolean transform(String pathxcel, String pathcsv) throws IOException{
			Iterator<Row> rowIterator=null;
			FileInputStream input_document = null;
	        
	        try{
	        	//First we read the Excel file in binary format into FileInputStream
	        	input_document = new FileInputStream(new File(pathxcel));
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
	        	XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
	        	// To iterate over the rows
		        rowIterator = my_worksheet.iterator();
	        }
	        
	        // OpenCSV writer object to create CSV file
	        FileWriter my_csv=new FileWriter(pathcsv);
	        CSVWriter my_csv_output=new CSVWriter(my_csv); 
	        //Loop through rows.
	        while(rowIterator.hasNext()) {
	                Row row = rowIterator.next();
	                System.out.println(row.getLastCellNum());
	                int i=0;//String array
	                //change this depending on the length of your sheet
	                String[] csvdata = new String[row.getLastCellNum()+1];
	                Iterator<Cell> cellIterator = row.cellIterator();
	                        while(cellIterator.hasNext()) {
	                                Cell cell = cellIterator.next(); //Fetch CELL
	                                csvdata[i]= cell.getStringCellValue();
	                                i=i+1;
	                        }
	        my_csv_output.writeNext(csvdata);
	        }
	        my_csv_output.close(); //close the CSV file
	        //we created our file..!!
	        input_document.close(); //close xls
	        return true;
		}
}
