package me.samuel.estore.admin.mapper;

import me.samuel.estore.admin.entity.EStoreRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
public interface EStoreRoleMapper extends BaseMapper<EStoreRole> {

    @Select(" select * from e_store_role r left join e_store_admin_role ar on ar.role_id = r.id " +
            " where ar.admin_id = #{admin}")
    List<EStoreRole> queryByAdmin(@Param("admin") Integer admin);
}
