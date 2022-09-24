package com.atguigu.myzhxy.controller;


import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.service.AdminService;
import com.atguigu.myzhxy.util.MD5;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//增删改查
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    private AdminService adminService ;

    //  GET
    //	localhost:9002/sms/adminController/getAllAdmin/1/3?  adminName=Lily
    @ApiOperation("分页带条件查询管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("管理员名字") String adminName){
        Page<Admin> pageParam =new Page(pageNo,pageSize);
        IPage<Admin> pageRes=adminService.getAdminsByOpr(pageParam,adminName);
        return Result.ok(pageRes);
    }

    //  POST
    //	localhost:9002/sms/adminController/saveOrUpdateAdmin  admin
    @ApiOperation("增加或修改管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("JSON格式的Admin对象") @RequestBody Admin admin){
        Integer id = admin.getId();
        //新增，密码转换为md5的形式
        if (id == null || 0 == id) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        //自带方法
        adminService.saveOrUpdate(admin);
        return Result.ok();

    }

    //  DELETE
    //	localhost:9002/sms/adminController/deleteAdmin List<Integer> ids
    @ApiOperation("删除单个或者多个管理员信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
           @ApiParam("要删除的管理员多个ID的JSON集合") @RequestBody List<Integer> ids){
        //自带方法
        adminService.removeByIds(ids);
        return Result.ok();
    }



}
