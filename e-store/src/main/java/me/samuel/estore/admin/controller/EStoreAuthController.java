package me.samuel.estore.admin.controller;

import com.baomidou.mybatisplus.extension.api.R;
import me.samuel.estore.admin.entity.EStoreAdmin;
import me.samuel.estore.admin.service.IEStoreAdminService;
import me.samuel.estore.admin.service.IEStorePermissionService;
import me.samuel.estore.admin.service.IEStoreRoleService;
import me.samuel.estore.admin.vo.LoginVo;
import me.samuel.estore.annotation.RequiresPermissionsDesc;
import me.samuel.estore.common.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/admin/auth")
public class EStoreAuthController {

    @Autowired
    private IEStoreAdminService adminService;
    @Autowired
    private IEStoreRoleService roleService;
    @Autowired
    private IEStorePermissionService permissionService;

    @PostMapping("/login")
    public Object login(@RequestBody String body, HttpServletRequest request){
        String username = JacksonUtil.parseString(body,"username");
        String password = JacksonUtil.parseString(body,"password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return ResponseUtil.badArgument();
        }

        Subject subject = SecurityUtils.getSubject();

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            subject.login(token);
        }catch (UnknownAccountException uae){
            return ResponseUtil.fail(ResponseCode.ADMIN_INVALID_ACCOUNT, "用户帐号或密码不正确");
        }catch (LockedAccountException lae){
            return ResponseUtil.fail(ResponseCode.ADMIN_INVALID_ACCOUNT, "用户帐号已锁定不可用");
        }catch (AuthenticationException ae){
            return ResponseUtil.fail(ResponseCode.ADMIN_INVALID_ACCOUNT, "认证失败");
        }

        subject = SecurityUtils.getSubject();
        EStoreAdmin admin = (EStoreAdmin) subject.getPrincipal();
        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        admin.setLastLoginTime(LocalDateTime.now());
        adminService.updateById(admin);

        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(admin.getUsername());
        loginVo.setAvatar(admin.getAvatar());
        loginVo.setToken(subject.getSession().getId().toString());
        return ResponseUtil.ok(loginVo);
    }

    @RequiresAuthentication
    @PostMapping("/logout")
    public Object logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseUtil.ok();
    }

    @GetMapping("/401")
    public Object page401() {
        return ResponseUtil.unlogin();
    }

    @GetMapping("/index")
    public Object pageIndex() {
        return ResponseUtil.ok();
    }

    @GetMapping("/403")
    public Object page403() {
        return ResponseUtil.unauthz();
    }

    @RequiresAuthentication
    @PostMapping("/info")
    public Object info() {
        Subject currentUser = SecurityUtils.getSubject();
        EStoreAdmin admin = (EStoreAdmin) currentUser.getPrincipal();

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar());

        //Integer[] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByAdmin(admin.getId());
        Set<String> permissions = permissionService.queryByAdmin(admin.getId());
        data.put("roles", roles);
        // NOTE
        // 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
        data.put("perms", toApi(permissions));
        return ResponseUtil.ok(data);
    }


    @Autowired
    private ApplicationContext context;
    private HashMap<String,String> permissionMap = null;

    private Collection<String> toApi(Set<String> permissionSet){
        if (permissionMap == null){
            permissionMap = new HashMap<>();
            final String basicPackage = "me.samuel.estore";
            List<Permission> permissionList = PermissionUtil.listPermission(context,basicPackage);
            permissionList.forEach((item) -> {
                String perm = item.getRequiresPermissions().value()[0];
                String api = item.getApi();
                permissionMap.put(perm,api);
            });
        }

        Collection<String> apis = new HashSet<>();
        for (String perm : permissionSet) {
            String api = permissionMap.get(perm);
            apis.add(api);
            if (perm.equals("*")){
                apis.clear();
                apis.add("*");
                return apis;
            }
        }
        return apis;
    }
}
