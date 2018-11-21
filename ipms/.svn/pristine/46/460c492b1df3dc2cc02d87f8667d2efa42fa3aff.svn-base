package com.ideassoft.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.RoomType;
import com.ideassoft.bean.UserRole;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class RightService extends GenericDAOImpl {

	public void removeRoleRelation(String roleId) {
		String hql = "delete RoleRelation where roleId = :ROLEID";
		// String hql = (String) findOneByProperties(SqlInfo.class, "sqlName",
		// "deleteroleid");
		executeUpdateHQL(hql, new String[] { "ROLEID" }, new String[] { roleId });
	}

	public Role findRoleById(String roleId) {
		Object result = findOneByProperties(Role.class, "roleId", roleId, new Object[] { "status", "1" });
		return result != null ? (Role) result : null;
	}

	@SuppressWarnings("unchecked")
	public List<RoleRelation> findRoleRelationByRoleId(String roleId) {
		return findByProperties(RoleRelation.class, "roleId", roleId, new Object[] { "status", "1" });
	}

	public UserRole findRoleIdByStaffId(String staffId) {
		Object result = findOneByProperties(UserRole.class, "userId", staffId, new Object[] { "status", "1" });
		return result != null ? (UserRole) result : null;
	}

	public List<?> findAllRoomType(String branchId) {
		List<?> result = findByProperties(RoomType.class, "roomTypeKey.branchId", branchId, new Object[] { "status", "1" });
		return result != null ?  result : null;
	}
}
