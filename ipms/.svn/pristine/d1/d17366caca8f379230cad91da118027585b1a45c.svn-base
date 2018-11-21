package com.ideassoft.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.core.bean.LoginUser;

public class RightUtil {

	/**
	 * @param args
	 */
	
	public static boolean hasRight(HttpServletRequest request, String funcid){
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		List<Role> sysRoles = loginUser.getRightsList();
		List<RoleRelation> roleRelations;
		List<String> funcrights = new ArrayList<String>();
		boolean flag = false;
		
		if(sysRoles != null && !sysRoles.isEmpty()){
			for (int i = 0; i < sysRoles.size(); i++) {
				roleRelations = sysRoles.get(i).getRoleRelation();
				
				if(roleRelations != null && !roleRelations.isEmpty()){
					for (int j = 0; j < roleRelations.size(); j++) {
						if(roleRelations.get(j) == null){
							continue;
						}
						funcrights.add(roleRelations.get(j).getFuncrightId());
					}
				}
			}
		}
		for (String string : funcrights) {
			if(funcid.equals(string)){
				flag = true;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
