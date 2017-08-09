/**
 * This object class accepts all of the table data gathered and outputs it to a single Excel file.
 */

package nv.bpdc;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

public class BPDC_XLSXWriter {
	
	//-----------------------------------------------------------------//
	
	/** Declare and initialize final variables **/
	
	private final BPDC_Dialog_Error errorDialog = new BPDC_Dialog_Error();
	
	private final String f1String = "F1";
	private final String st1String = "ST1";
	private final String st6String = "ST6";
	
	//-----------------------------------------------------------------//
	
	/** Declare fields **/
	
	private String outputFile;
	
	private ArrayList<ArrayList<BPDC_TableDataRow>> venusDataList;
	private ArrayList<ArrayList<BPDC_TableDataRow>> nimbusDataList;
	private ArrayList<ArrayList<BPDC_TableDataRow>> cramerDataList;
	private ArrayList<ArrayList<BPDC_TableDataRow>> activationDataList;
	private ArrayList<ArrayList<BPDC_TableDataRow>> mediationDataList;
	
	private XSSFWorkbook workbook;
	
	//-----------------------------------------------------------------//
	
	/** Constructors **/
	
	protected BPDC_XLSXWriter(ArrayList<ArrayList<BPDC_TableDataRow>> inc_venusDataList, ArrayList<ArrayList<BPDC_TableDataRow>> inc_nimbusDataList, 
							  ArrayList<ArrayList<BPDC_TableDataRow>> inc_cramerDataList, ArrayList<ArrayList<BPDC_TableDataRow>> inc_activationDataList, 
							  ArrayList<ArrayList<BPDC_TableDataRow>> inc_mediationDataList, 
							  int inc_numberOfDataRows) {
		outputFile = "DB Tables Comparison - " + getDateString() + ".xlsx";
		
		venusDataList = inc_venusDataList;
		nimbusDataList = inc_nimbusDataList;
		cramerDataList = inc_cramerDataList;
		activationDataList = inc_activationDataList;
		mediationDataList = inc_mediationDataList;
		
		workbook = new XSSFWorkbook();
	}
	
	//-----------------------------------------------------------------//
	
	/** Abstract methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Implemented methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Accessor methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Mutator methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Protected methods **/
	
	protected void buildXLSFile() {
		buildSheet(venusDataList);
 		buildSheet(nimbusDataList);
		buildSheet(cramerDataList);
		buildSheet(activationDataList);
		buildSheet(mediationDataList);
		
		try {
			FileOutputStream outStream = new FileOutputStream(outputFile);
			workbook.write(outStream);
			workbook.close();
		}
		catch (Exception e) {
			errorDialog.setExceptionDialog(null, e);
		}
	}
	
	//-----------------------------------------------------------------//
	
	/** Private methods **/
	
	private String getDateString() {
		String formattedDateString;
		DateTime datetime = new DateTime();
		formattedDateString = datetime.toString("MM") + "-" + datetime.toString("dd") + "-" + datetime.toString("yy");
		return formattedDateString;
	}
	
	private void buildSheet(ArrayList<ArrayList<BPDC_TableDataRow>> inc_dataList) {
		XSSFSheet sheet = workbook.createSheet(inc_dataList.get(0).get(0).getOwner());		// This sets the sheet name as the schema name.
		int actualTableComparisonLimit = Math.min(inc_dataList.get(0).size(), Math.min(inc_dataList.get(1).size(), Math.min(inc_dataList.get(2).size(), inc_dataList.get(3).size())));
		
		// Create header row
		buildHeaderRow(sheet);
		
		// Create data rows
		buildDataRows(sheet, actualTableComparisonLimit);
		
		// Create PROD section
		fillPRODDataRows(sheet, inc_dataList.get(0), actualTableComparisonLimit);
		
		// Create F1 section
		fillLabDataRows(sheet, inc_dataList.get(1), f1String, actualTableComparisonLimit);
		
		// Create ST1 section
		fillLabDataRows(sheet, inc_dataList.get(2), st1String, actualTableComparisonLimit);
		
		// Create ST6 section
		fillLabDataRows(sheet, inc_dataList.get(3), st6String, actualTableComparisonLimit);
		
		// Format sheet
		formatSheet(sheet, actualTableComparisonLimit);
		
		// Set column widths
		setColumnWidths(sheet);
	}
	
	private void buildHeaderRow(XSSFSheet inc_sheet) {
		XSSFCellStyle headerRowStyle = workbook.createCellStyle();
		XSSFFont headerRowFont = workbook.createFont();
		headerRowFont.setFontName("Calibri");
		headerRowFont.setFontHeightInPoints((short) 8);
		headerRowStyle.setFont(headerRowFont);
		
		Row headerRow = inc_sheet.createRow(0);
		Cell cell;
		cell = headerRow.createCell(0);
		cell.setCellValue("OWNER");
		cell = headerRow.createCell(1);
		cell.setCellValue("TABLE_NAME");
		cell = headerRow.createCell(2);
		cell.setCellValue("PROD_RANK");
		cell = headerRow.createCell(3);
		cell.setCellValue("PROD_NUM_ROWS");
		cell = headerRow.createCell(4);
		cell.setCellValue("PROD_SIZE(MB)");
		cell = headerRow.createCell(5);
		cell.setCellValue("F1_RANK");
		cell = headerRow.createCell(6);
		cell.setCellValue("F1_NUM_ROWS");
		cell = headerRow.createCell(7);
		cell.setCellValue("F1_SIZE(MB)");
		cell = headerRow.createCell(8);
		cell.setCellValue("F1-PROD_ROW_DELTA");
		cell = headerRow.createCell(9);
		cell.setCellValue("ST1_RANK");
		cell = headerRow.createCell(10);
		cell.setCellValue("ST1_NUM_ROWS");
		cell = headerRow.createCell(11);
		cell.setCellValue("ST1_SIZE(MB)");
		cell = headerRow.createCell(12);
		cell.setCellValue("ST1-PROD_ROW_DELTA");
		cell = headerRow.createCell(13);
		cell.setCellValue("ST6_RANK");
		cell = headerRow.createCell(14);
		cell.setCellValue("ST6_NUM_ROWS");
		cell = headerRow.createCell(15);
		cell.setCellValue("ST6_SIZE(MB)");
		cell = headerRow.createCell(16);
		cell.setCellValue("ST6-PROD_ROW_DELTA");
		
		headerRow.setRowStyle(headerRowStyle);
	}
	
	private void buildDataRows(XSSFSheet inc_sheet, int inc_tableComparisonLimit) {
		XSSFCellStyle defaultRowStyle = workbook.createCellStyle();
		XSSFFont defaultRowFont = workbook.createFont();
		defaultRowFont.setFontName("Calibri");
		defaultRowFont.setFontHeightInPoints((short) 8);
		defaultRowStyle.setFont(defaultRowFont);
		
		for (int i = 1; i <= inc_tableComparisonLimit; i++) {
			inc_sheet.createRow(i);
		}
		
		for (int i = 1; i <= inc_tableComparisonLimit; i++) {
			inc_sheet.getRow(i).setRowStyle(defaultRowStyle);
		}
	}
	
	private void fillPRODDataRows(XSSFSheet inc_sheet, ArrayList<BPDC_TableDataRow> inc_tableData, int inc_tableComparisonLimit) {
		Row row;
		Cell cell;
		int column = 0;
		
		for (int i = 0; i < inc_tableComparisonLimit; i++) {
			row = inc_sheet.getRow(i + 1);
			
			cell = row.createCell(column);
			cell.setCellValue(inc_tableData.get(i).getOwner());
			
			cell = row.createCell(column + 1);
			cell.setCellValue(inc_tableData.get(i).getTableName());
			
			cell = row.createCell(column + 2);
			if (inc_tableData.get(i).getRank().equals("DNE")) {
				cell.setCellValue(inc_tableData.get(i).getRank());
			}
			else {
				cell.setCellValue(Integer.parseInt(inc_tableData.get(i).getRank()));
			}
			
			cell = row.createCell(column + 3);
			cell.setCellValue(inc_tableData.get(i).getNumRows());
			
			cell = row.createCell(column + 4);
			cell.setCellValue(inc_tableData.get(i).getSize());
		}
	}
	
	private void fillLabDataRows(XSSFSheet inc_sheet, ArrayList<BPDC_TableDataRow> inc_tableData, String inc_environment, int inc_tableComparisonLimit) {
		Row row;
		Cell cell;
		int column;
		
		if (inc_environment.equals(f1String)) {
			column = 5;
		}
		else if (inc_environment.equals(st1String)) {
			column = 9;
		}
		else {		// inc_environment.equals(st6String)
			column = 13;
		}
		
		for (int i = 0; i < inc_tableComparisonLimit; i++) {
			row = inc_sheet.getRow(i + 1);
			
			cell = row.createCell(column);
			if (inc_tableData.get(i).getRank().equals("DNE")) {
				cell.setCellValue(inc_tableData.get(i).getRank());
			}
			else {
				cell.setCellValue(Integer.parseInt(inc_tableData.get(i).getRank()));
			}
			
			cell = row.createCell(column + 1);
			cell.setCellValue(inc_tableData.get(i).getNumRows());
			
			cell = row.createCell(column + 2);
			cell.setCellValue(inc_tableData.get(i).getSize());
			
			cell = row.createCell(column + 3);
			cell.setCellValue(inc_tableData.get(i).getNumRows() - row.getCell(3).getNumericCellValue());
		}
	}
	
	private void formatSheet(XSSFSheet inc_sheet, int inc_tableComparisonLimit) {
		Row row;
		Cell cell;
		
		// Default font
		XSSFFont defaultFont = workbook.createFont();
		defaultFont.setFontName("Calibri");
		defaultFont.setFontHeightInPoints((short) 8);
		defaultFont.setColor(new XSSFColor(new Color(0, 0, 0)));
		
		// Bold font for the header row
		XSSFFont headerFont = workbook.createFont();
		headerFont.setFontName("Calibri");
		headerFont.setFontHeightInPoints((short) 8);
		headerFont.setColor(new XSSFColor(new Color(0, 0, 0)));
		headerFont.setBold(true);
		
		// Green font for negative delta values
		XSSFFont greenFont = workbook.createFont();
		greenFont.setFontName("Calibri");
		greenFont.setFontHeightInPoints((short) 8);
		greenFont.setColor(new XSSFColor(new Color(0, 176, 80)));
		
		// Red font for positive delta values
		XSSFFont redFont = workbook.createFont();
		redFont.setFontName("Calibri");
		redFont.setFontHeightInPoints((short) 8);
		redFont.setColor(new XSSFColor(new Color(192, 0, 0)));
		
		// Style for header font with N, W, and S borders
		XSSFCellStyle headerNWS_Style = workbook.createCellStyle();
		headerNWS_Style.setFont(headerFont);
		headerNWS_Style.setBorderTop(BorderStyle.THIN);
		headerNWS_Style.setBorderLeft(BorderStyle.THIN);
		headerNWS_Style.setBorderBottom(BorderStyle.THIN);
		
		// Style for header font with N and S borders
		XSSFCellStyle headerNS_Style = workbook.createCellStyle();
		headerNS_Style.setFont(headerFont);
		headerNS_Style.setBorderTop(BorderStyle.THIN);
		headerNS_Style.setBorderBottom(BorderStyle.THIN);
		
		// Style for header font with N, S, and E borders
		XSSFCellStyle headerNSE_Style = workbook.createCellStyle();
		headerNSE_Style.setFont(headerFont);
		headerNSE_Style.setBorderTop(BorderStyle.THIN);
		headerNSE_Style.setBorderBottom(BorderStyle.THIN);
		headerNSE_Style.setBorderRight(BorderStyle.THIN);
		
		
		// Style for default font with N border
		XSSFCellStyle defaultN_Style = workbook.createCellStyle();
		defaultN_Style.setFont(defaultFont);
		defaultN_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultN_Style.setBorderTop(BorderStyle.THIN);
		
		// Style for default font with N and W borders
		XSSFCellStyle defaultNW_Style = workbook.createCellStyle();
		defaultNW_Style.setFont(defaultFont);
		defaultNW_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultNW_Style.setBorderTop(BorderStyle.THIN);
		defaultNW_Style.setBorderLeft(BorderStyle.THIN);
		
		// Style for default font with W border
		XSSFCellStyle defaultW_Style = workbook.createCellStyle();
		defaultW_Style.setFont(defaultFont);
		defaultW_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultW_Style.setBorderLeft(BorderStyle.THIN);
		
		// Style for default font with W and S borders
		XSSFCellStyle defaultWS_Style = workbook.createCellStyle();
		defaultWS_Style.setFont(defaultFont);
		defaultWS_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultWS_Style.setBorderLeft(BorderStyle.THIN);
		defaultWS_Style.setBorderBottom(BorderStyle.THIN);
		
		// Style for default font with S border
		XSSFCellStyle defaultS_Style = workbook.createCellStyle();
		defaultS_Style.setFont(defaultFont);
		defaultS_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultS_Style.setBorderBottom(BorderStyle.THIN);
		
		// Style for default font with S and E borders
		XSSFCellStyle defaultSE_Style = workbook.createCellStyle();
		defaultSE_Style.setFont(defaultFont);
		defaultSE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultSE_Style.setBorderBottom(BorderStyle.THIN);
		defaultSE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for default font with E border
		XSSFCellStyle defaultE_Style = workbook.createCellStyle();
		defaultE_Style.setFont(defaultFont);
		defaultE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for default font with N and E borders
		XSSFCellStyle defaultNE_Style = workbook.createCellStyle();
		defaultNE_Style.setFont(defaultFont);
		defaultNE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		defaultNE_Style.setBorderTop(BorderStyle.THIN);
		defaultNE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for default font with no borders
		XSSFCellStyle defaultNone_Style = workbook.createCellStyle();
		defaultNone_Style.setFont(defaultFont);
		defaultNone_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		
		// Style for green font with S and E borders
		XSSFCellStyle greenSE_Style = workbook.createCellStyle();
		greenSE_Style.setFont(greenFont);
		greenSE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		greenSE_Style.setBorderBottom(BorderStyle.THIN);
		greenSE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for green font with E border
		XSSFCellStyle greenE_Style = workbook.createCellStyle();
		greenE_Style.setFont(greenFont);
		greenE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		greenE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for green font with N and E borders
		XSSFCellStyle greenNE_Style = workbook.createCellStyle();
		greenNE_Style.setFont(greenFont);
		greenNE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		greenNE_Style.setBorderTop(BorderStyle.THIN);
		greenNE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for red font with S and E borders
		XSSFCellStyle redSE_Style = workbook.createCellStyle();
		redSE_Style.setFont(redFont);
		redSE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		redSE_Style.setBorderBottom(BorderStyle.THIN);
		redSE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for red font with E border
		XSSFCellStyle redE_Style = workbook.createCellStyle();
		redE_Style.setFont(redFont);
		redE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		redE_Style.setBorderRight(BorderStyle.THIN);
		
		// Style for red font with N and E border
		XSSFCellStyle redNE_Style = workbook.createCellStyle();
		redNE_Style.setFont(redFont);
		redNE_Style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		redNE_Style.setBorderTop(BorderStyle.THIN);
		redNE_Style.setBorderRight(BorderStyle.THIN);
		
		// Apply styles to header row cells
		row = inc_sheet.getRow(0);
		cell = row.getCell(0);
		cell.setCellStyle(headerNWS_Style);
		cell = row.getCell(1);
		cell.setCellStyle(headerNSE_Style);
		cell = row.getCell(2);
		cell.setCellStyle(headerNWS_Style);
		cell = row.getCell(3);
		cell.setCellStyle(headerNS_Style);
		cell = row.getCell(4);
		cell.setCellStyle(headerNSE_Style);
		cell = row.getCell(5);
		cell.setCellStyle(headerNWS_Style);
		cell = row.getCell(6);
		cell.setCellStyle(headerNS_Style);
		cell = row.getCell(7);
		cell.setCellStyle(headerNS_Style);
		cell = row.getCell(8);
		cell.setCellStyle(headerNSE_Style);
		cell = row.getCell(9);
		cell.setCellStyle(headerNWS_Style);
		cell = row.getCell(10);
		cell.setCellStyle(headerNS_Style);
		cell = row.getCell(11);
		cell.setCellStyle(headerNS_Style);
		cell = row.getCell(12);
		cell.setCellStyle(headerNSE_Style);
		cell = row.getCell(13);
		cell.setCellStyle(headerNWS_Style);
		cell = row.getCell(14);
		cell.setCellStyle(headerNS_Style);
		cell = row.getCell(15);
		cell.setCellStyle(headerNS_Style);
		cell = row.getCell(16);
		cell.setCellStyle(headerNSE_Style);
		
		// Apply styles to PROD, F1, ST1, and ST6 sections
		for (int i = 1; i <= inc_tableComparisonLimit; i++) {
			row = inc_sheet.getRow(i);
			if (i == 1) {		// N, NW, and NE borders
				cell = row.getCell(0);
				cell.setCellStyle(defaultNW_Style);
				cell = row.getCell(1);
				cell.setCellStyle(defaultNE_Style);
				cell = row.getCell(2);
				cell.setCellStyle(defaultNW_Style);
				cell = row.getCell(3);
				cell.setCellStyle(defaultN_Style);
				cell = row.getCell(4);
				cell.setCellStyle(defaultNE_Style);
				cell = row.getCell(5);
				cell.setCellStyle(defaultNW_Style);
				cell = row.getCell(6);
				cell.setCellStyle(defaultN_Style);
				cell = row.getCell(7);
				cell.setCellStyle(defaultN_Style);
				cell = row.getCell(8);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenNE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultNE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redNE_Style);
				}
				cell = row.getCell(9);
				cell.setCellStyle(defaultNW_Style);
				cell = row.getCell(10);
				cell.setCellStyle(defaultN_Style);
				cell = row.getCell(11);
				cell.setCellStyle(defaultN_Style);
				cell = row.getCell(12);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenNE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultNE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redNE_Style);
				}
				cell = row.getCell(13);
				cell.setCellStyle(defaultNW_Style);
				cell = row.getCell(14);
				cell.setCellStyle(defaultN_Style);
				cell = row.getCell(15);
				cell.setCellStyle(defaultN_Style);
				cell = row.getCell(16);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenNE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultNE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redNE_Style);
				}
			}
			else if (i > 1 && i < inc_tableComparisonLimit) {		// E, W, and no borders
				cell = row.getCell(0);
				cell.setCellStyle(defaultW_Style);
				cell = row.getCell(1);
				cell.setCellStyle(defaultE_Style);
				cell = row.getCell(2);
				cell.setCellStyle(defaultW_Style);
				cell = row.getCell(3);
				cell.setCellStyle(defaultNone_Style);
				cell = row.getCell(4);
				cell.setCellStyle(defaultE_Style);
				cell = row.getCell(5);
				cell.setCellStyle(defaultW_Style);
				cell = row.getCell(6);
				cell.setCellStyle(defaultNone_Style);
				cell = row.getCell(7);
				cell.setCellStyle(defaultNone_Style);
				cell = row.getCell(8);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redE_Style);
				}
				cell = row.getCell(9);
				cell.setCellStyle(defaultW_Style);
				cell = row.getCell(10);
				cell.setCellStyle(defaultNone_Style);
				cell = row.getCell(11);
				cell.setCellStyle(defaultNone_Style);
				cell = row.getCell(12);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redE_Style);
				}
				cell = row.getCell(13);
				cell.setCellStyle(defaultW_Style);
				cell = row.getCell(14);
				cell.setCellStyle(defaultNone_Style);
				cell = row.getCell(15);
				cell.setCellStyle(defaultNone_Style);
				cell = row.getCell(16);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redE_Style);
				}
			}
			else if (i == inc_tableComparisonLimit) {		 // WS, S, and SE borders
				cell = row.getCell(0);
				cell.setCellStyle(defaultWS_Style);
				cell = row.getCell(1);
				cell.setCellStyle(defaultSE_Style);
				cell = row.getCell(2);
				cell.setCellStyle(defaultWS_Style);
				cell = row.getCell(3);
				cell.setCellStyle(defaultS_Style);
				cell = row.getCell(4);
				cell.setCellStyle(defaultSE_Style);
				cell = row.getCell(5);
				cell.setCellStyle(defaultWS_Style);
				cell = row.getCell(6);
				cell.setCellStyle(defaultS_Style);
				cell = row.getCell(7);
				cell.setCellStyle(defaultS_Style);
				cell = row.getCell(8);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenSE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultSE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redSE_Style);
				}
				cell = row.getCell(9);
				cell.setCellStyle(defaultWS_Style);
				cell = row.getCell(10);
				cell.setCellStyle(defaultS_Style);
				cell = row.getCell(11);
				cell.setCellStyle(defaultS_Style);
				cell = row.getCell(12);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenSE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultSE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redSE_Style);
				}
				cell = row.getCell(13);
				cell.setCellStyle(defaultWS_Style);
				cell = row.getCell(14);
				cell.setCellStyle(defaultS_Style);
				cell = row.getCell(15);
				cell.setCellStyle(defaultS_Style);
				cell = row.getCell(16);
				if (getDelta(cell) < 0) {
					cell.setCellStyle(greenSE_Style);
				}
				else if (getDelta(cell) == 0) {
					cell.setCellStyle(defaultSE_Style);
				}
				else if (getDelta(cell) > 0) {
					cell.setCellStyle(redSE_Style);
				}
			}
		}
	}
	
	private int getDelta(Cell inc_cell) {
		return (int) inc_cell.getNumericCellValue();
	}
	
	private void setColumnWidths(XSSFSheet inc_sheet) {
		inc_sheet.setColumnWidth(0, 10 * 256);
		inc_sheet.setColumnWidth(1, 30 * 256);
		inc_sheet.setColumnWidth(2, 9 * 256);
		inc_sheet.setColumnWidth(3, 14 * 256);
		inc_sheet.setColumnWidth(4, 12 * 256);
		inc_sheet.setColumnWidth(5, 9 * 256);
		inc_sheet.setColumnWidth(6, 14 * 256);
		inc_sheet.setColumnWidth(7, 12 * 256);
		inc_sheet.setColumnWidth(8, 18 * 256);
		inc_sheet.setColumnWidth(9, 9 * 256);
		inc_sheet.setColumnWidth(10, 14 * 256);
		inc_sheet.setColumnWidth(11, 12 * 256);
		inc_sheet.setColumnWidth(12, 18 * 256);
		inc_sheet.setColumnWidth(13, 9 * 256);
		inc_sheet.setColumnWidth(14, 14 * 256);
		inc_sheet.setColumnWidth(15, 12 * 256);
		inc_sheet.setColumnWidth(16, 18 * 256);
	}
	
	//-----------------------------------------------------------------//
	
}
