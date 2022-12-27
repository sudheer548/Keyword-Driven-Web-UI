package KeywordExcelRead;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import BasePackage.Base;

public class Keywordread {

	public WebDriver driver;
	public Properties prop;
	public static Workbook book;
	public static Sheet sheet;
    public WebElement element;
	public Base base;



	public final String EXCEL_SHEET_PATH = "F:\\java demo\\KeywordDrivenFramework\\src\\main\\java\\MyScenarios\\TestCases.xlsx";

	public void startExecution(String sheetName) {
		String locatorName = null;
		String locatorValue = null;

		FileInputStream file = null;
		try {
			file = new FileInputStream(EXCEL_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		int k=0;
		for(int i=0; i < sheet.getLastRowNum(); i++) {
		
			String locatorColVal = sheet.getRow( i+1).getCell(k+1).toString().trim();
			if(!locatorColVal.equalsIgnoreCase("NA")) {
				locatorName = locatorColVal.split("=")[0].trim();//id
				locatorValue = locatorColVal.split("=")[1].trim();//username			
			}
			String action = sheet.getRow(i+1).getCell(k+2).toString().trim();
			String value = sheet.getRow(i+1).getCell(k+3).toString().trim();

			switch (action) {
			case "open browser" : base = new Base();
			                      prop = base.init_properties();
			                      if(value.isEmpty() || value.equals("NA")){
				                  driver = base.init_driver(prop.getProperty("browser"));  
			                      }else{
				                    driver = base.init_driver(value);
			                       }
			                       break;

			case "enter url" : 
			                   if(value.isEmpty() || value.equals("NA")){
				               driver.get(prop.getProperty("url"));  
			                    }else{
				               driver.get(value);
			                    }
			                   break;
			                   
			case "quit" : driver.quit();
			                   break;
            	default : break;               
			}
			try {
			switch (locatorName) {
			case "id" : element = driver.findElement(By.id(locatorValue));
			                     if(action.equalsIgnoreCase("sendKeys")) {
			                    	 element.clear();
			                    	 element.sendKeys(value);
			                     }else if(action.equalsIgnoreCase("click")) {
			                    	 element.click();
			                     }
			                     locatorName=null;
			                       break;
			case "linktext" : element = driver.findElement(By.linkText(locatorValue));
                        	   element.click();
                        	   locatorName=null;
                               break;
            	default : break;               
			}
	}
			catch(Exception e) {
				
			}
		}
	}



}
