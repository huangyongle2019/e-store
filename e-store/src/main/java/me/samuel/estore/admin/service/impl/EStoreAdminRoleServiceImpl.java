package me.samuel.estore.admin.service.impl;

import me.samuel.estore.admin.entity.EStoreAdminRole;
import me.samuel.estore.admin.mapper.EStoreAdminRoleMapper;
import me.samuel.estore.admin.service.IEStoreAdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员角色关联表 服务实现类
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
@Service
public class EStoreAdminRoleServiceImpl extends ServiceImpl<EStoreAdminRoleMapper, EStoreAdminRole> implements IEStoreAdminRoleService {

}
