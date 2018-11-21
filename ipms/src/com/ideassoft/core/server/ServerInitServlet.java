package com.ideassoft.core.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;

import com.ideassoft.util.SpringUtil;

public class ServerInitServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5306676887950496983L;

	public void init() throws ServletException {
        super.init();
        
        ApplicationContext app = SpringUtil.getApplicationContext();
        Server.init(app);

        Server.run();
    }
}
