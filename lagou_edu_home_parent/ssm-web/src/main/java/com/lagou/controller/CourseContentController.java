package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    /*
    *   根据课程id查询对应的课程内容（章节+课时）
    * */
    @RequestMapping(path = "/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId){

        // 调用service
        List<CourseSection> list = courseContentService.findSectionAndLessonByCourseId(courseId);

        // 封装数据及内容
        ResponseResult responseResult = new ResponseResult(true, 200, "章节及课时内容查询成功", list);

        return responseResult;
    }

    /*
    *   回显课程信息
    * */
    @RequestMapping(path = "/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId){

        Course course = courseContentService.findCourseByCourseId(courseId);

        ResponseResult responseResult = new ResponseResult(true, 200, "回显课程信息成功", course);

        return responseResult;

    }

    /*
    *   新增章节信息
    * */
    @RequestMapping(path = "/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection section) {

        if (section.getId() == null) {
            courseContentService.saveSection(section);
            ResponseResult responseResult = new ResponseResult(true, 200, "新增章节信息成功", null);
            return responseResult;
        } else {
            courseContentService.updateSection(section);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改章节信息成功", null);
            return responseResult;
        }

    }

    /*
    *   修改章节状态信息
    * */
    @RequestMapping(path = "/updateSectionStatus")
    public ResponseResult updateSectionStatus(Integer id, Integer status) { // status：0已隐藏 1待更新 2已更新

        // 调用service
        courseContentService.updateSectionStatus(id, status);

        // 数据响应
        Map<String, Integer> map = new HashMap<>();
        map.put("status", status);

        ResponseResult responseResult = new ResponseResult(true, 200, "修改章节状态成功", map);

        return responseResult;

    }

    /*
    *   新增或修改课时信息
    * */
    @RequestMapping(path = "/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson lesson){

        if (lesson.getId() == null) {   // 如果id为null，则是新增课时信息
            courseContentService.saveLesson(lesson);

            return new ResponseResult(true, 200, "新增课时信息成功", null);
        } else {
            courseContentService.updateLesson(lesson);

            return new ResponseResult(true, 200, "修改课时信息成功", null);
        }

    }

}
