package com.ideassoft.core.taglib;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;

public class UDFileSupport extends BodyTagSupport {

	private static final long serialVersionUID = 5424354722166858321L;
	
	public UDFileSupport() {
		init();
	}

	private void init() {
		dealType = def = null;
		path = null;
		showList = true;
		sync = false;
	}

	public void release() {
		super.release();
		init();
	}

	public int doStartTag() throws JspException {
		bodyContent = null;
		try {
			if (dealType != null) {
				
				
				return 0;
			}
		} catch (Exception ex) {
			throw new JspException(ex.toString(), ex);
		}
		if (def == null) {
			
			return 2;
		}
		
		if (def != null) {
			
		}
			
		return 0;
	}

	public int doEndTag() throws JspException {
		if (bodyContent != null && bodyContent.getString() != null) {
			
		}
		return 6;
	}

	public static void out(PageContext pageContext, boolean escapeXml,
			Object obj) throws IOException {
		JspWriter w = pageContext.getOut();
		if (!escapeXml) {
			if (obj instanceof Reader) {
				Reader reader = (Reader) obj;
				char buf[] = new char[4096];
				int count;
				while ((count = reader.read(buf, 0, 4096)) != -1)
					w.write(buf, 0, count);
			} else {
				w.write(obj.toString());
			}
		} else if (obj instanceof Reader) {
			Reader reader = (Reader) obj;
			char buf[] = new char[4096];
			int count;
			while ((count = reader.read(buf, 0, 4096)) != -1)
				writeEscapedXml(buf, count, w);
		} else {
			String text = obj.toString();
			writeEscapedXml(text.toCharArray(), text.length(), w);
		}
	}

	private static void writeEscapedXml(char buffer[], int length, JspWriter w)
			throws IOException {
		int start = 0;
		for (int i = 0; i < length; i++) {
			char c = buffer[i];
			if (c > '>')
				continue;
			char escaped[] = Util.specialCharactersRepresentation[c];
			if (escaped == null)
				continue;
			if (start < i)
				w.write(buffer, start, i - start);
			w.write(escaped);
			start = i + 1;
		}

		if (start < length)
			w.write(buffer, start, length - start);
	}

	protected String dealType;
	
	protected String path;
	
	protected boolean showList;
	
	protected boolean sync;
	
	private String def;
	
	protected static final String UPLOAD = "upload";
	
	protected static final String DOWNLOAD = "download";
	
}

