package me.samuel.estore.admin.service;

import me.samuel.estore.admin.entity.EStorePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
public interface IEStorePermissionService extends IService<EStorePermission> {

    Set<String> queryByAdmin(Integer admin);
}
