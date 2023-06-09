package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentService {

    /*
    *   根据课程id查询对应的课程内容（章节+课时）
    * */
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /*
    *   回显课程信息
    * */
    public Course findCourseByCourseId(Integer courseId);

    /*
    *   新增章节信息
    * */
    public void saveSection(CourseSection section);

    /*
    *   修改章节信息
    * */
    public void updateSection(CourseSection section);

    /*
    *   修改章节状态信息
    * */
    public void updateSectionStatus(Integer id, Integer status);

    /*
    *   新增课时信息
    * */
    public void saveLesson(CourseLesson lesson);

    /*
    *   修改课时信息
    * */
    public void updateLesson(CourseLesson lesson);

}
