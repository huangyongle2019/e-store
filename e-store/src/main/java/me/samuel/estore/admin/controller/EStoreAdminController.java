package me.samuel.estore.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import me.samuel.estore.admin.entity.EStoreAdmin;
import me.samuel.estore.admin.service.IEStoreAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author Samuel
 * @since 2020-05-06
 */
@RestController
@RequestMapping("/e-store/admin")
public class EStoreAdminController {

    private Logger logger = LoggerFactory.getLogger(EStoreAdminController.class);
    @Autowired
    private IEStoreAdminService adminService;

    @GetMapping("/list")
    public R list(){
        List<EStoreAdmin> adminList = adminService.list(new QueryWrapper<EStoreAdmin>());
        return R.ok(adminList);
    }
}
