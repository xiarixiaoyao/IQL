package cn.i4.report.system.service;

import cn.i4.report.bean.BaseBean;
import cn.i4.report.system.domain.EntityUser;
import cn.i4.report.system.domain.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {


    public JSONObject findUserList(BaseBean base);

    public JSONObject findUserById(EntityUser user);

    public User findByUsername(EntityUser user);

    @Transactional
    public Integer addUser(EntityUser user,String roles);

    @Transactional
    public Integer updateUser(EntityUser user,String roles);

    @Transactional
    public Integer delUser(EntityUser user);

    public Integer changePassWord(EntityUser user);

    public Integer changeFirstLoginAndPassWord(EntityUser user);

    public Integer changeFirstLoginFlag(EntityUser user);

    public JSONObject findUserSelect(EntityUser user);
	
}
