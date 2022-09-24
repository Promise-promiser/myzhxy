package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Teacher;
import com.atguigu.myzhxy.service.TeacherService;
import com.atguigu.myzhxy.util.MD5;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//CRUD
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /*
    *       GET
    *       sms/teacherController/getTeachers/1/3
    *       sms/teacherController/getTeachers/1/3?name=小启&clazzName=一年一班
    *       请求数据
    *       响应Result data= 分页
    * */
    @ApiOperation("分页获取教师信息,带搜索条件")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("页码数") @PathVariable("pageNo") Integer pageNo ,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize ,
            @ApiParam("查询条件") Teacher teacher){
        Page<Teacher> pageParam =new Page(pageNo,pageSize);
        IPage<Teacher> page = teacherService.getTeachersByOpr(pageParam,teacher);
        return Result.ok(page);
    }


    /*
    *       POST
    *       sms/teacherController/saveOrUpdateTeacher
    *       请求数据 Teacher
    *       响应Result data= null OK
    * */
    @ApiOperation("添加或者修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("要保存或者修改的JOSN格式的Teacher") @RequestBody Teacher teacher){
        //新增，密码转换为md5的形式
        if (teacher.getId() == null || teacher.getId() == 0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        //自带方法
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    /*
    *       DELETE
    *       sms/teacherController/deleteTeacher
    *       请求的数据 JSON 数组 [1,2,3]
    *       响应Result data =null  OK
    *
    * */
    @ApiOperation("删除单个或者多个教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
           @ApiParam("要删除的教师信息的id的JSON集合")  @RequestBody List<Integer> ids){
        //自带方法
        teacherService.removeByIds(ids);
        return Result.ok();
    }

}
