package com.qa.api.gorest.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	//public static String TESTDATA_SHEET_PATH = "C:\\Ayesha\\api_automation\\RestAssuredFW\\src\\main\\java\\com\\qa\\api\\gorest\\testdata\\GorestTestData.xlsx";
	public static String TESTDATA_SHEET_PATH = ".\\src\\main\\java\\com\\qa\\api\\gorest\\testdata\\GorestTestData.xlsx";
	public static Workbook book;
	public static Sheet sheet;
	
	/**
	 * @param sheetName
	 * @return
	 */
	public static Object[][] getTestData(String sheetName) {
		try {
			//Creates a connection with the xls file
			FileInputStream fis = new FileInputStream(TESTDATA_SHEET_PATH);
			//Creates a local copy of the excel file inside the java memory	so that it can be read properly
			book=WorkbookFactory.create(fis);
			sheet=book.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[][] data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for(int i=0;i<sheet.getLastRowNum();i++) {
			for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
				data[i][j]=sheet.getRow(i+1).getCell(j).getStringCellValue();	
			}
		}
		return data;
	}
}
