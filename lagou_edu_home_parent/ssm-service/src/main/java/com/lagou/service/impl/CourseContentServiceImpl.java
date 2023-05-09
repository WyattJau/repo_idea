package com.lagou.service.impl;

import com.lagou.dao.CourseContentMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;

    /*
    *   根据课程id查询对应的课程内容（章节+课时）
    * */
    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId) {

        List<CourseSection> list = courseContentMapper.findSectionAndLessonByCourseId(courseId);

        return list;

    }

    /*
    *   回显课程信息
    * */
    @Override
    public Course findCourseByCourseId(Integer courseId) {

        Course course = courseContentMapper.findCourseByCourseId(courseId);

        return course;

    }

    /*
    *   新增章节信息
    * */
    @Override
    public void saveSection(CourseSection section) {

        // 1、补全信息
        Date date = new Date();
        section.setCreateTime(date);
        section.setUpdateTime(date);

        // 2、调用mapper方法
        courseContentMapper.saveSection(section);

    }

    /*
    *   修改章节信息
    * */
    public void updateSection(CourseSection section){

        // 补全信息
        Date date = new Date();
        section.setUpdateTime(date);

        courseContentMapper.updateSection(section);

    }

    /*
    *   修改章节状态信息
    * */
    @Override
    public void updateSectionStatus(Integer id, Integer status){

        // 封装数据
        CourseSection courseSection = new CourseSection();

        // 补全数据
        courseSection.setStatus(status);
        courseSection.setUpdateTime(new Date());
        courseSection.setId(id);

        // 调用dao
        courseContentMapper.updateSectionStatus(courseSection);

    }

    /*
    *   新增课时信息
    * */
    @Override
    public void saveLesson(CourseLesson lesson) {

        // 补全信息
        Date date = new Date();
        lesson.setCreateTime(date);
        lesson.setUpdateTime(date);

        // 调用dao
        courseContentMapper.saveLesson(lesson);

    }

    /*
    *   修改课时信息
    * */
    @Override
    public void updateLesson(CourseLesson lesson) {

        // 补全信息
        lesson.setUpdateTime(new Date());

        // 调用dao
        courseContentMapper.updateLesson(lesson);

    }
}
