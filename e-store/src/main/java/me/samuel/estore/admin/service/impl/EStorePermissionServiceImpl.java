package me.samuel.estore.admin.service.impl;

import me.samuel.estore.admin.entity.EStorePermission;
import me.samuel.estore.admin.mapper.EStorePermissionMapper;
import me.samuel.estore.admin.service.IEStorePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
@Service
public class EStorePermissionServiceImpl extends ServiceImpl<EStorePermissionMapper, EStorePermission> implements IEStorePermissionService {

    @Autowired
    private EStorePermissionMapper permissionMapper;

    @Override
    public Set<String> queryByAdmin(Integer admin) {
        List<EStorePermission> permissionList = permissionMapper.queryByAdmin(admin);
        Set<String> permissionSet = new HashSet<>();
        permissionList.forEach((item) -> {
            permissionSet.add(item.getPermission());
        });
        return permissionSet;
    }
}
