package me.samuel.estore.admin.mapper;

import me.samuel.estore.admin.entity.EStorePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
public interface EStorePermissionMapper extends BaseMapper<EStorePermission> {

    @Select(" select * from e_store_permission p " +
            " left join e_store_role_permission rp on rp.role_id = p.id " +
            " left join e_store_role r on r.id = rp.role_id " +
            " left join e_store_admin_role ar on ar.role_id = r.id " +
            " where ar.admin_id = #{admin}")
    List<EStorePermission> queryByAdmin(@Param("admin") Integer admin);
}
