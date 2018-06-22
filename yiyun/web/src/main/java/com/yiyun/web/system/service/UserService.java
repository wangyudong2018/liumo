package com.yiyun.web.system.service;

import com.yiyun.domain.DeptDO;
import com.yiyun.domain.Tree;
import com.yiyun.domain.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {
    UserDO get(Long id);

    List<UserDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserDO user);

    int update(UserDO user);

    int remove(Long userId);

    int batchremove(Long[] userIds);

    boolean exit(Map<String, Object> params);

    Set<String> listRoles(Long userId);

    int resetPwd(UserDO user);

    Tree<DeptDO> getTree();

    UserDO getByInvestCode(String investCode);

    /**
     * 根据角色code获取用户
     * @param
     * @return
     */
    List<UserDO> getUserByRoleCode(Map<String, Object> map);
}
