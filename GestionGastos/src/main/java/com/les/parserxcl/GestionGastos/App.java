package com.les.parserxcl.GestionGastos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.les.parserxcl.GestionGastos.configuration.BaseConfig;
import com.les.parserxcl.GestionGastos.manager.Excel2csv;

/**
 * Hello world!
 *
 */
@Import(value = {BaseConfig.class})
@SpringBootApplication
public class App implements CommandLineRunner
{
	@Autowired
	Excel2csv xcl2csv;
	
    public static void main( String[] args )
    {
        System.out.println( "#### Aplication Starting!!!" );
        SpringApplication.run(App.class, args);
        System.out.println( "#### Aplication Start!!!" );
    }


	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World from Application Runner");
		xcl2csv.walkin();
		//xcl2csv.transform("D:\\LES\\work\\proyectos\\raulbussines\\in\\TGS REPORT.XLSX", "D:\\LES\\work\\proyectos\\raulbussines\\finaltgsreport.csv");
	}
}
