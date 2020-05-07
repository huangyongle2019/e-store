package me.samuel.estore.admin.service.impl;

import me.samuel.estore.admin.entity.EStoreAdmin;
import me.samuel.estore.admin.mapper.EStoreAdminMapper;
import me.samuel.estore.admin.service.IEStoreAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
@Service
public class EStoreAdminServiceImpl extends ServiceImpl<EStoreAdminMapper, EStoreAdmin> implements IEStoreAdminService {

}
