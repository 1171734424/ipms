package com.ideassoft.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.apache.log4j.Logger;

public class PrintUtil implements Printable {
	private static Logger logger = Logger.getLogger(PrintUtil.class);
	//总页数
	private int pageSize;
	//纸张宽度
	private double paperWidth = 0;
	//纸张高度
	private double paperHeight = 0;

	/**
	 * @param pageIndex 当前页
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex >= pageSize)
			return Printable.NO_SUCH_PAGE;
		else {
			Graphics2D g2 = (Graphics2D) graphics;
			g2.setColor(Color.BLUE);
			Paper p = new Paper();
			//设置可打印区域
			p.setImageableArea(0, 0, paperWidth, paperHeight);
			//设置纸张的大小
			p.setSize(paperWidth, paperHeight);
			pageFormat.setPaper(p);
			drawCurrentPageText(g2, pageFormat);
			return PAGE_EXISTS;
		}
	}

	private void drawCurrentPageText(Graphics2D g2, PageFormat pf) {
		Font font = new Font("宋体", Font.BOLD, 11);
		g2.setFont(font);
		//200,200意为纸张的-x,-y坐标,从该坐标开始打印,单位1/72(inch)
		g2.drawString("打印测试", 200, 200);
	}

	public void starPrint() {
		try {
			PrinterJob prnJob = PrinterJob.getPrinterJob();
			PageFormat pageFormat = new PageFormat();
			pageFormat.setOrientation(PageFormat.PORTRAIT);
			prnJob.setPrintable(this);
			//弹出打印对话框
			if (!prnJob.printDialog()) return;
			//获取目标打印机的进纸规格宽/高度,单位：1/72(inch)
			paperWidth = prnJob.getPageFormat(null).getPaper().getWidth();
			paperHeight = prnJob.getPageFormat(null).getPaper().getHeight();
			prnJob.print();
		} catch (PrinterException e) {
			logger.error("mail print occurs error：" + e);
		}
	}
	
	public static PrintService[] getPrintServices() {
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.PNG;
		return PrintServiceLookup.lookupPrintServices(flavor, pras);
	}
	
	public static PrintService getPrinter(String psName) {
		PrintService[] printService = getPrintServices();
		for (PrintService ps : printService) {
			if (ps.getName().equalsIgnoreCase(psName)) {
				return ps;
			}
		}
		
		return null;
	}
	
	public static String[] getPrinterNames() {
		PrintService[] printService = getPrintServices();
		
		if (printService != null) {
			Integer pos = 0;
			String[] names = new String[printService.length];
			for (PrintService ps : printService) {
				names[pos] = ps.getName();
				pos++;
			}
			
			return names;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		PrintUtil print = new PrintUtil();
		print.pageSize = 1;
		print.starPrint();
		
		PrintService ps = getPrinter("Fax");
		DocPrintJob job = ps.createPrintJob();
		System.out.println(job);
	}

}
