package me.samuel.estore.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.samuel.estore.admin.entity.EStoreAdmin;
import me.samuel.estore.admin.service.IEStoreAdminService;
import me.samuel.estore.admin.service.IEStorePermissionService;
import me.samuel.estore.admin.service.IEStoreRoleService;
import me.samuel.estore.common.utils.Md5Utils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private IEStoreAdminService adminService;
    @Autowired
    private IEStoreRoleService roleService;
    @Autowired
    private IEStorePermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null){
            throw new AuthenticationException("PrincipalCollection method argument cannot be null.");
        }
        EStoreAdmin admin = (EStoreAdmin) getAvailablePrincipal(principalCollection);
        Set<String> roles = roleService.queryByAdmin(admin.getId());
        Set<String> permissions = permissionService.queryByAdmin(admin.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());

        if (StringUtils.isEmpty(username)){
            throw new AccountException("用户名不为空!");
        }
        if (StringUtils.isEmpty(password)){
            throw new AccountException("密码不能为空!");
        }
        EStoreAdmin admin = adminService.getOne(new QueryWrapper<EStoreAdmin>().lambda().eq(EStoreAdmin::getUsername,username));
        if (admin == null){
            throw new UnknownAccountException("用户名或密码错误!");
        }
        if (!Md5Utils.matches(password,admin.getSalt(),admin.getPassword())){
            throw new IncorrectCredentialsException("用户名或密码错误!");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                admin,
                password,
                ByteSource.Util.bytes(admin.getSalt()),
                getName()
        );

        return info;
    }
}
