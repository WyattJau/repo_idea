package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(path = "/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO){ // 使用@RequestBody注解对前台传递过来的参数进行封装

        // 调用service
        List<Course> list = courseService.findCourseByCondition(courseVO);

        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", list);

        return responseResult;

    }

    /*
    *   课程图片上传
    * */
    @RequestMapping(path = "/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws Exception {

        // 1、判断接收到的文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
        }

        // 2、获取项目的部署路径  D:\study\JavaWeb\tomcat\apache-tomcat-8.5.55-windows-x64\apache-tomcat-8.5.55\webapps\ssm_web
        String realPath = request.getServletContext().getRealPath("/");
        // 截取路径 截成D:\study\JavaWeb\tomcat\apache-tomcat-8.5.55-windows-x64\apache-tomcat-8.5.55\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        // 3、获取原文件名
        // 比如原文件名为：test.jpg
        String originalFilename = file.getOriginalFilename();

        // 4、生成新文件名 System.currentTimeMillis()获取当前时间戳 originalFilename.substring(originalFilename.indexOf("."))将文件名中.号前面内容截掉，只获取后缀名
        // 假设此处时间戳为1234，那么新文件名为 1234.jpg
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 5、文件上传
        String uploadPath = substring + "upload\\";
        File filePath = new File(uploadPath, newFileName);

        // 如果目录不存在就创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }

        file.transferTo(filePath);

        // 6、将文件名和文件路径返回，进行响应
        Map<String, String> map = new HashMap<>();
        map.put("fileName", newFileName);
        // "filePath": "https://localhost:8080/upload/12352353.JPG"
        map.put("filePath", "https://localhost:8080/upload/" + newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "上传成功", map);

        return responseResult;

    }

    /*
    *   新增课程和讲师信息
    *       新增课程信息和 修改课程信息 要写在同一个方法中
    * */
    @RequestMapping(path = "/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        // 判断courseVO中的id值是否为空，如果为空则是新增课程信息和讲师信息
        if (courseVO.getId() == null) {
            // 调用service
            courseService.saveCourseOrTeacher(courseVO);

            ResponseResult responseResult = new ResponseResult(true, 200, "添加成功", null);// 保存操作，后台没有向前台返回的数据

            return responseResult;
        } else { // courseVO中的id值不为空则是更新信息
            courseService.updateCourseOrTeacher(courseVO);

            ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", null);// 保存操作，后台没有向前台返回的数据

            return responseResult;
        }




    }

    /*
    *   根据课程id查询课程信息及相关联的讲师信息
    * */
    @RequestMapping(path = "/findCourseById")
    public ResponseResult findCourseById(Integer id) {

        CourseVO courseById = courseService.findCourseById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "根据id查询课程信息成功", courseById);

        return responseResult;

    }

    /*
    *   修改课程状态信息
    * */
    @RequestMapping(path = "/updateCourseStatus")
    public ResponseResult updateCourseStatus(@RequestParam Integer id,@RequestParam Integer status) {

        // 调用service，传递参数，完成课程状态变更
        courseService.updateCourseStatus(id, status);

        // 将status封装到map集合
        Map<String, Integer> map = new HashMap<>();
        map.put("status", status);

        // 响应数据
        ResponseResult responseResult = new ResponseResult(true, 200, "课程状态变更成功", map);

        return responseResult;

    }

}
