package cn.i4.report.system.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cn.i4.report.system.dao.RoleDao;
import cn.i4.report.system.domain.Role;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Map> findRoleMenuByIds(List<Integer> ids) {
		String sql = "select b.id, b.menuname name, b.pid, b.url, b.icon from t_role_menu a"
				+ " left join t_menu b on a.menuid=b.id"
				+ " where a.roleid in (:ids)"
				+ " order by pid, orderBy";
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("ids", ids);
		List<Map> list = query.getResultList();
		return list;
	}

	@Override
	public List<Map> findAllRole() {
		String sql = "select id,rolename,descs from t_role";
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = query.getResultList();
		return list;
	}

	@Override
	public Integer addRole(Role role) {
		em.persist(role);
		em.flush();
		return 1;
	}

	@Override
	public Integer updateRole(Role role) {
		String sql="update t_role set rolename=?,descs=? where id=?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, role.getRolename());
		query.setParameter(2, role.getDescs());
		query.setParameter(3, role.getId());
		Integer result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer delRole(Role role) {
		String sql="delete from t_role where id=?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, role.getId());
		Integer result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer delRoleMenu(Role role) {
		String sql="delete from t_role_menu where roleid=?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, role.getId());
		Integer result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer addRoleMenu(Role role, String menus) {
		String[] menu = menus.split(",");
		String sqls = "";
		for(String m:menu){
			if(m!=null&&!m.equals("")){
				sqls+="("+role.getId()+","+m+")"+",";
			}
		}
		sqls = StringUtils.strip(sqls,",");
		String sql = "insert into t_role_menu (roleid,menuid) values "+sqls;
		Query query = em.createNativeQuery(sql);
		Integer result = query.executeUpdate();
		return result;
	}

	@Override
	public List<Map> findRoleMenuByRoleId(Role role) {
		String sql = "select m.id,m.name,m.pid from t_role r,t_role_menu rm,t_menu m where r.id=rm.roleid and rm.menuid=m.id and r.id=:roleid";
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("roleid", role.getId());
		List<Map> list = query.getResultList();
		return list;
	}
}
