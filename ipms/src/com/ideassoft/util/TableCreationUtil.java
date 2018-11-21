package com.ideassoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TableCreationUtil {

	private static Logger logger = Logger.getLogger(TableCreationUtil.class);

	private static XSSFWorkbook wb;

	private static XSSFSheet sheet;

	private static XSSFRow row;

	private static StringBuilder tableStr;

	private static StringBuilder commectStr;

	private static String tableName;
	
	private static String columnName;
	
	private static String primaryKey;

	private static boolean isPartition;
	
	private static Integer[] positions = new Integer[6];
	
	private static void initTableStr() {
		tableStr = null;
		commectStr = null;
		primaryKey = null;
		isPartition = false;
	}
	
	private static void readExcelTitle(InputStream is, int sheetPos, int titlePos) throws Exception {
		wb = new XSSFWorkbook(is);
		sheet = wb.getSheetAt(sheetPos);
		row = sheet.getRow(titlePos);
		int colNum = row.getPhysicalNumberOfCells();
		
		String cellValue;
		XSSFCell cell;
		for (int i = 0; i < colNum; i++) {
			cell = row.getCell(i);
			if (cell != null) {
				cellValue = getStringCellValue(cell).trim();
				if (cellValue != null && !"null".equals(cellValue) && !"".equals(cellValue)) {
					for (int j = 0; j < TABLE_CONFIG.length; j++) {
						if (cellValue.equals(TABLE_CONFIG[j])) {
							positions[j] = i;
						}
					}
				}
			}
		}
	}
	
	private static void readExcelContent(InputStream is, int sheetPos, int rowPos) throws Exception {
		wb = new XSSFWorkbook(is);
		sheet = wb.getSheetAt(sheetPos);
		int rowNum = sheet.getLastRowNum();

		row = sheet.getRow(rowPos);
		for (int i = rowPos; i <= rowNum; i++) {
			row = sheet.getRow(i);
			
			if (row != null) {
				String cellValue = getStringCellValue(row.getCell(0)).trim();
				
				if (TABLE_BLOCK.equals(cellValue) || BOTTOM_TAG.equals(cellValue)) {
					if (tableStr != null) {
						tableStr.deleteCharAt(tableStr.toString().length() - 2);
						tableStr.append(")\n");
						if (isPartition) {
							tableStr.append(partitionStr.replace("*", primaryKey).replaceAll("#", DATA_TABLESPACE));
						} else {
							tableStr.append(tablespaceStr.replace("#", DATA_TABLESPACE));
						}
						writeFile(tableStr.toString());
						writeFile(commectStr.toString());
						if (primaryKey != null && primaryKey.length() > 0) {
							writeFile(primaryKeyStr.replace("#", primaryKey).replace("*", tableName)
									.replace("@", INDEX_TABLESPACE));
						} else {
							writeFile("\n");
						}
						initTableStr();
					}
					
					if (BOTTOM_TAG.equals(cellValue)) {
						break;
					}
					tableName = getStringCellValue(row.getCell(1)).trim().toUpperCase();

					tableStr = new StringBuilder("drop table ");
					tableStr.append(tableName).append(";\n");
					tableStr.append("create table ");
					commectStr = new StringBuilder("comment on table ");
					
					tableStr.append(tableName).append(" (\n");
					commectStr.append(tableName).append(" is '")
						.append(getStringCellValue(row.getCell(2)).trim()).append("';\n");
					if (!isCellEmpty(row.getCell(3))) {
						isPartition = true;
					}
					primaryKey = getStringCellValue(row.getCell(4)).trim().toLowerCase();
				} else {
					columnName = getStringCellValue(row.getCell(positions[0])).trim().toLowerCase();
					tableStr.append("\t").append(columnName).append(blockStr(columnName));
					tableStr.append("\t").append(getStringCellValue(row.getCell(positions[2])).trim().toUpperCase());
					
					if (positions.length > 5 && !isCellEmpty(row.getCell(positions[5]))) {
						tableStr.append("\tdefault\t").append(getStringCellValue(row.getCell(positions[5])).trim());
					} else if ("N".equalsIgnoreCase(getStringCellValue(row.getCell(positions[3])).trim())) {
						tableStr.append(" ").append(NOTNULL_TAG);
					} 
					tableStr.append(",\n");
					
					commectStr.append("comment on column ").append(tableName).append(".").append(columnName)
						.append(" is '").append(getStringCellValue(row.getCell(positions[1])).trim());
					if (!isCellEmpty(row.getCell(positions[4])) && 
							!getStringCellValue(row.getCell(positions[1])).trim()
								.equals(getStringCellValue(row.getCell(positions[4])).trim())) {
						commectStr.append("\t").append(getStringCellValue(row.getCell(positions[4])).trim());
					}
					commectStr.append("';\n");
				}
			}
		}
	}

	private static String getStringCellValue(XSSFCell cell) {
		String strCell = "";

		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				strCell = cell.getRichStringCellValue().toString();
				break;
				
			case HSSFCell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(cell.getNumericCellValue()).replace(".0", "");
				break;
				
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
				
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
				
			default:
				strCell = "";
				break;
		}
		
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		
		if (cell == null) {
			return "";
		}
		
		return strCell;
	}
	
	private static String blockStr(String str) {
		StringBuilder block = new StringBuilder();
		for (int i = 0; i < 15 - str.length(); i++) {
			block.append(" ");
		}
		return block.toString();
	}
	
	private static void writeFile(String content) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(DEFAULT_PATH + TAR_FILE), true);
			fw.write(content);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (fw != null) {
				try {
					fw.flush();
					fw.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
	
	private static boolean isCellEmpty(XSSFCell cell) {
		String cellValue = getStringCellValue(cell);
		return cellValue == null || "null".equalsIgnoreCase(cellValue.trim()) 
			|| "".equalsIgnoreCase(cellValue.trim());
	}
	
	public static void main(String[] args) {
		try {
			InputStream tis = new FileInputStream(new File(DEFAULT_PATH + SRC_FILE));
			readExcelTitle(tis, 0, 0);
			tis.close();
			InputStream cis = new FileInputStream(new File(DEFAULT_PATH + SRC_FILE));
			readExcelContent(cis, 0, 1);
			cis.close();
			System.out.println("sucess");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String TABLE_BLOCK = "TABLE";

	private static final String BOTTOM_TAG = "END";
	
	private static final String NOTNULL_TAG = "not null";
	
	private static final String DEFAULT_PATH = "./WebRoot/doc/";

	private static final String SRC_FILE = "table-bug.xlsx";

	private static final String TAR_FILE = "table-bug.sql";

	private static final String DATA_TABLESPACE = "IDEAS_DAT";
	
	private static final String INDEX_TABLESPACE = "IDEAS_IDX";
	
	private static final String[] TABLE_CONFIG = { "字段", "名称", "类型", "是否可空", "备注", "默认值" };
	
	private static final String tablespaceStr = "tablespace # \n\tpctfree 10 \n\tinitrans 1 \n\tmaxtrans 255 " +
			"storage(\n\tinitial 16K \n\tminextents 1 \n\tmaxextents unlimited\n);\n";
	
	private static String partitionStr = "partition by range(*)(" +
			"\n\tpartition t_range_p1 values less than (200000000) tablespace #_1," +
			"\n\tpartition t_range_p2 values less than (300000000) tablespace #_2," +
			"\n\tpartition t_range_p3 values less than (400000000) tablespace #_3," +
			"\n\tpartition t_range_p4 values less than (500000000) tablespace #_4," +
			"\n\tpartition t_range_p5 values less than (600000000) tablespace #_5," +
			"\n\tpartition t_range_p6 values less than (700000000) tablespace #_6," +
			"\n\tpartition t_range_p7 values less than (800000000) tablespace #_7," +
			"\n\tpartition t_range_p8 values less than (900000000) tablespace #_8," +
			"\n\tpartition t_range_p9 values less than (1000000000) tablespace #_9," +
			"\n\tpartition t_range_p10 values less than (1100000000) tablespace #_10," +
			"\n\tpartition t_range_p11 values less than (1200000000) tablespace #_11," +
			"\n\tpartition t_range_pmax values less than (maxvalue) tablespace #_12\n);\n";
	
	private static String primaryKeyStr = "alter table * \n\tadd constraint PRIMARYKEY_* primary key (#)" +
			"\n\tusing index \n\ttablespace @ \n\tpctfree 10 \n\tinitrans 2 \n\tmaxtrans 255" +
			"\n\tstorage \n\t( \n\t initial 64K \n\t minextents 1 \n\t maxextents unlimited \n\t);\n\n";
	
}
