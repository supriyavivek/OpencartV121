package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider1
	
	@DataProvider(name="LoginData")
	public String [][] getData() {
		String path=".\\testData\\opencart_testdata.xlsx"; //taking xl file from test Data
		
		ExcelUtilities xlutil=new ExcelUtilities(path); //creating an object for XlUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcell=xlutil.getCellCount("Sheet1", 1);
		
		String logindata[][]=new String[totalrows][totalcell]; //created for two dimension array which can store data
		
		for(int i=1; i<=totalrows; i++) { //1  //read the data from xl storing in two dimensional array
			for(int j=0; j<totalcell; j++) { //0 i is for rows and j is for columns
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j); //1,0
			}
		}
		return logindata; //returning two dimensional array
	}
}
