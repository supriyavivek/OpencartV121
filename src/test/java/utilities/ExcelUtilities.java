package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	private String path;

	public ExcelUtilities(String path) {
		this.path=path;
	}
	
	public int getRowCount(String sheetName) {
		int rowCount = 0;
		try {
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetName);
			rowCount=sheet.getLastRowNum();
			workbook.close();
			fi.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	
	public int getCellCount(String sheetName, int rownum) {
		int cellCount = 0;
		try {
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetName);
			row=sheet.getRow(rownum);
			cellCount=row.getLastCellNum();
			workbook.close();
			fi.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellCount;
	}
	
	public String getCellData(String sheetName, int rownum, int cellnum) {
		String data = null;
		DataFormatter formatter;
		try {
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetName);
			row=sheet.getRow(rownum);
			cell=row.getCell(cellnum);
			
			formatter=new DataFormatter();
			data=formatter.formatCellValue(cell); //Returns the formatted value of a cell as a string regardless of the cell type.
			
			workbook.close();
			fi.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void setCellData(String sheetName, int rownum, int cellnum, String data) {
		try {
			File xlfile=new File(path);
			if(!xlfile.exists()) { //if file not exists then create new file
				workbook=new XSSFWorkbook();
				fo=new FileOutputStream(path);
				workbook.write(fo);
			}
			
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			
			if(workbook.getSheetIndex(sheetName)==-1)  { //if sheet not exists then create new sheet
				workbook.createSheet(sheetName);
			}
			sheet=workbook.getSheet(sheetName);
			
			if(sheet.getRow(rownum)==null) { //if row not exists then create new row
				sheet.createRow(rownum);
			}
			row=sheet.getRow(rownum);
			
			cell=row.createCell(cellnum);
			cell.setCellValue(data);
			
			workbook.write(fo);
			workbook.close();
			fi.close();
			fo.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillGreenColor(String sheetname, int rownum, int cellnum) {
		try {
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetname);
			
			row=sheet.getRow(rownum);
			cell=row.getCell(cellnum);
			
			style=workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			cell.setCellStyle(style);
			workbook.write(fo);
			workbook.close();
			fi.close();
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillRedColor(String sheetname, int rownum, int cellnum) {
		try {
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetname);
			
			row=sheet.getRow(rownum);
			cell=row.getCell(cellnum);
			
			style=workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			cell.setCellStyle(style);
			workbook.write(fo);
			workbook.close();
			fi.close();
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
