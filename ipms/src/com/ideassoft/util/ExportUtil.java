package com.ideassoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.ideassoft.core.bean.pageConfig.ColumnConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;

 
public class ExportUtil {
		
	public static int exportFile(HttpServletResponse response, String[] files, String fileName) throws Exception {
		byte buff[] = new byte[8192];
		response.setContentType("application/octet-stream");
		
		StringBuffer temp = new StringBuffer(fileName).append(".zip");
		String targetName = temp.toString();
		targetName = new String(targetName.getBytes("gb2312"), "iso-8859-1");
		temp = new StringBuffer("attachment;filename=").append(targetName);
		
		response.setHeader("Content-disposition", temp.toString());
		
		ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
		ZipEntry entry = null;
		zipOut.setEncoding("GBK");
		
		for (int i = 0; i < files.length; i++) {
			File currentFile = new File(files[i]);
			InputStream in = new FileInputStream(currentFile);
			String strTemp = currentFile.getPath();	
			strTemp = strTemp.substring(strTemp.lastIndexOf("/") + 1, strTemp.length());
			entry = new ZipEntry(strTemp);
			zipOut.putNextEntry(entry);
			int len = 0;
			while ((len = in.read(buff)) != -1) {
				zipOut.write(buff, 0, len);
			}
			zipOut.closeEntry();
		}
		
		zipOut.flush();
		zipOut.close();
		
		return 1;
	}
	
	public static void exportExcel(PageConfig pageConfig, List<?> data, boolean isBean, OutputStream os) throws Exception {
		String prefixTitle = pageConfig.getName();
		
		List<ColumnConfig> columns = pageConfig.getColumns();
		String[] ids = new String[columns.size()],
			 names = new String[columns.size()],
			 types = new String[columns.size()];
		
		ColumnConfig column;
		for (int i = 0, pos = 0; i < columns.size(); i++) {
			column = columns.get(i);
			if (column.isDownloadable()) {
				ids[pos] = column.getColumnId();
				names[pos] = column.getName();
				types[pos] = column.getEditType();
				pos++;
			}
		}
		
		exportExcel(prefixTitle, names, ids, types, data, pageConfig.getSumCols(), isBean, os);
	}
	
	@SuppressWarnings("unchecked")
	public static void exportExcel(String prefixTitle, String[] names, String[] ids, String[] types, 
			List<?> data, String sumCols, boolean isBean, OutputStream os) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = null;
		XSSFRow row;
		Map<String, Map<String, BigDecimal>> sum = null;
		String value;
		
		if (sumCols != null) {
			sum = new HashMap<String, Map<String, BigDecimal>>();
			Map<String, BigDecimal> info;
			for (String col : sumCols.split(",")) {
				info = new HashMap<String, BigDecimal>();
				info.put("value", new BigDecimal(0d));
				info.put("position", new BigDecimal(0));
				
				sum.put(col, info);
			}
		}
		
		CellStyle csb = wb.createCellStyle();
		csb.setAlignment(CellStyle.ALIGN_CENTER);
		
		XSSFFont fontb = wb.createFont();
		fontb.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontb.setFontName("宋体");
		fontb.setFontHeight((short) 250);
		csb.setFont(fontb);
		
		CellStyle cs = wb.createCellStyle();
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		
		XSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cs.setFont(font);
		
		if (!data.isEmpty()) {
			Entry<String, Map<String, BigDecimal>> entry;
			for (int i = 0; i < data.size(); i++) {
				if(i % 5000 == 0) {
					sheet = wb.createSheet("sheet" + (i % 5000 + 1));
					
					XSSFRow title = sheet.createRow(i % 5000);
					
					CellRangeAddress region = new CellRangeAddress(0, 0, (short) 0, (short) (names.length - 1));
					sheet.addMergedRegion(region);
					
					title.createCell(0).setCellValue(prefixTitle);
					title.getCell(0).setCellStyle(csb);
					
					row = sheet.createRow(i % 5000 + 1);
					
					for (int j = 0; j < names.length; j++) {
						row.createCell(j).setCellValue(names[j]);
						row.getCell(j).setCellStyle(csb);
					}
				}
				
				row = sheet.createRow(i % 5000 + 2);
				
				for (int k = 0; k < ids.length; k++) {
					if (ids[k] == null) {
						break;
					}
					
					if(isBean) {
						value = ReflectUtil.getValue(data.get(i), ids[k]).toString();
					} else {
						Object cell = ((Map<String, Object>) data.get(i)).get(ids[k]);
						if(cell != null) {
							value = cell.toString();
						} else {
							value = "";
						}
					}
					
					if (types[k] != null && StringUtils.isEmpty(value)) {
						if ("number".equalsIgnoreCase(types[k])) {
							value = "0";
						}
					}
					
					if (sum != null && sum.containsKey(ids[k])) {
						sum.get(ids[k]).put("position", new BigDecimal(k));
						sum.get(ids[k]).put("value", new BigDecimal(sum.get(ids[k]).get("value").doubleValue()
								+ Double.parseDouble(value)));
					}
					
					row.createCell(k).setCellValue(value);
					row.getCell(k).setCellStyle(cs);
					sheet.setColumnWidth(k, (short) (value.length() * 450 < 3000 ? 3000 : value.length() * 500));
				}

				if (((i != 0 && (i + 1) % 5000 == 0) || i == data.size() - 1) && sum != null) {
					row = sheet.createRow(i % 5000 + 3);
					row.createCell(0).setCellValue("合计");
					row.getCell(0).setCellStyle(cs);
					
					for (Iterator<Entry<String, Map<String, BigDecimal>>> it = sum.entrySet().iterator(); it.hasNext();) {
						entry = it.next();
						row.createCell(entry.getValue().get("position").intValue())
							.setCellValue(entry.getValue().get("value").doubleValue());
						row.getCell(entry.getValue().get("position").intValue()).setCellStyle(cs);
					}
				}
			}
		} else {
			sheet = wb.createSheet("sheet1");
			
			XSSFRow title = sheet.createRow(0);
			
			CellRangeAddress region = new CellRangeAddress(0, 0, (short) 0, (short) (names.length - 1));
			sheet.addMergedRegion(region);
			
			title.createCell(0).setCellValue(prefixTitle);
			title.getCell(0).setCellStyle(cs);
			
			row = sheet.createRow(1);
			
			for (int j = 0; j < names.length; j++) {
				row.createCell(j).setCellValue(names[j]);
				row.getCell(j).setCellStyle(cs);
			}

			row = sheet.createRow(2);
			
			row.createCell(0).setCellValue("无相关记录！");
			row.getCell(0).setCellStyle(cs);
			
			sheet.setColumnWidth(0, (short) 5000);
		}
		
		wb.write(os);
		os.flush();
		os.close();
	}
}

