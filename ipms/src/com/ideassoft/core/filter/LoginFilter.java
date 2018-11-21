package com.ideassoft.core.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ideassoft.bean.RightDefine;
import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.AlpConstants;
import com.ideassoft.core.constants.RightConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.session.MySessionContext;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RegExUtil;
import com.ideassoft.util.RequestUtil;

public class LoginFilter implements javax.servlet.Filter {
//    private static final Logger logger = Logger.getLogger(LoginFilter.class);
	
//    private static boolean timeOutFlg = false;
    
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
      
        String requestUrl = request.getRequestURI();
        
        if (RegExUtil.match(".*/(login|tologin|register|checkUser|reciveData|shiftTime|queryAllBranchInDB|queryStaffPwd|changStaffPwd|checkBackBoor|checkMemberImage|checkMemberImageMoho|addHouseComment|weipayCallBack|mohoGetMap|branchDialog|loadBranchDialogData)[.].*", requestUrl) 
        		|| requestUrl.equals(request.getContextPath()) || requestUrl.equals(request.getContextPath() + "/")) {
//        	timeOutFlg = false;
        	filterChain.doFilter(request, response);
            return;
        }
        
        String urls = requestUrl.substring(requestUrl.lastIndexOf("/"));
        
        if(AlpConstants.alpMap != null && AlpConstants.alpMap.containsKey(urls)){
        	filterChain.doFilter(request, response);
            return;
        }
        
        LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
        
//        boolean flag = false;
//        TimeoutSetConfig timeoutSetConfig = TimeoutSetConfig.getNewInstance();
//        String[] filterUrl = timeoutSetConfig.getFilterUrlConfigs();
//        
//        if (filterUrl != null && filterUrl.length > 0) {
//            for(String url : filterUrl){
//            	if(requestUrl.indexOf(url) != -1) {
//            		flag = true;
//            		break;
//            	}
//            }
//		}
        
        if (loginUser == null) {
            response.setContentType("text/html; charset=utf-8");
            
			response.sendRedirect(request.getContextPath() + "/page/system/tologin.jsp");
			return;
        } else {
        	MySessionContext sessionContext = MySessionContext.getInstance();
            HttpSession sessionTemp = sessionContext.getSession(loginUser.getSessionId());
            
            if (sessionTemp == null) {
                response.setContentType("text/html; charset=utf-8");
    			response.sendRedirect(request.getContextPath() + "/page/system/tologin.jsp");
    			return;
            }
            
//            if(!flag){
//            	loginUser.setRecordBeginTime(new Date().getTime());
//            	if(null != sessionTemp){
//            		sessionTemp.setAttribute("recordBeginTime", loginUser.getRecordBeginTime());
//            	}
//            }
//            
//            if(loginUser.getRecordBeginTime() == null){
//            	loginUser.setRecordBeginTime(null != sessionTemp ? Long.valueOf(String.valueOf(sessionTemp.getAttribute("recordBeginTime"))) : new Date().getTime());
//            }
//            
//            if(new Date().getTime() - loginUser.getRecordBeginTime() > 1000 * 60 * TimeoutSetConfig.timeoutDuration && !timeOutFlg) {
//            	TimeoutSetConfig.ReceiveFind = TimeoutSetConfig.ENABLE;
//            	request.getSession(true).removeAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
//            	logger.info(loginUser.getStaff().getStaffName() + "<---超时退出--->");
//            	
//				timeOutFlg = true;
//    			return ;
//    		}
        	
            if (loginUser.getStaff() != null && (loginUser.getStaff().getStaffId()).equals(SystemConstants.User.ADMIN_ID)) {
                filterChain.doFilter(request, response);
                return;
            }
            
            String url = requestUrl.substring(requestUrl.lastIndexOf("/"));
            if (RightConstants.rightDefineMap != null && RightConstants.rightDefineMap.containsKey(url)) {
        		List<Role> sysRoles = loginUser.getRightsList();
        		if (sysRoles != null && !sysRoles.isEmpty()) {
            		List<RoleRelation> roleRelations;
            		RoleRelation roleRelation;
            		boolean hasRight = false;
            		RightDefine rightDefine;
            		
        			outter: for (int i = 0; i < sysRoles.size(); i++) {
        				roleRelations = sysRoles.get(i).getRoleRelation();
        				if (roleRelations != null && !roleRelations.isEmpty()) {
        					for (int j = 0; j < roleRelations.size(); j++) {
        						roleRelation = roleRelations.get(j);
        						if (roleRelation == null) {
        							continue;
        						}
        						
        						rightDefine = RightConstants.rightDefineMap.get(url);
        						try {
									if (ReflectUtil.getFieldInitValue(RightConstants.RightType.class, roleRelation.getFuncrightId()).equals(rightDefine.getRightType())) {
										hasRight = true;
										break outter;
									}
								} catch (Exception e) {
								}
        					}
        				}
        			}
            		
            		if (!hasRight) {
                        response.setContentType("text/html; charset=utf-8");
            			response.sendRedirect(request.getContextPath() + "/page/system/illegal.jsp");
						return;
					}
        		}
			}
        }

        filterChain.doFilter(request, response);
    }


    public void destroy() {
    	
    }
    
}
