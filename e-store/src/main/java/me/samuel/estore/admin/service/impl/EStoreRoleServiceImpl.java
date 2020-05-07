package me.samuel.estore.admin.service.impl;

import me.samuel.estore.admin.entity.EStoreRole;
import me.samuel.estore.admin.mapper.EStoreRoleMapper;
import me.samuel.estore.admin.service.IEStoreRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
@Service
public class EStoreRoleServiceImpl extends ServiceImpl<EStoreRoleMapper, EStoreRole> implements IEStoreRoleService {

    @Autowired
    private EStoreRoleMapper roleMapper;

    @Override
    public Set<String> queryByAdmin(Integer admin) {
        List<EStoreRole> roleList = roleMapper.queryByAdmin(admin);
        Set<String> roleSet = new HashSet<>();
        roleList.forEach((item) -> {
            roleSet.add(item.getName());
        });
        return roleSet;
    }
}
