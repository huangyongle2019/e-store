package me.samuel.estore.admin.service;

import me.samuel.estore.admin.entity.EStoreRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
public interface IEStoreRoleService extends IService<EStoreRole> {

    Set<String> queryByAdmin(Integer admin);
}
