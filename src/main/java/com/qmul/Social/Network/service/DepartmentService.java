package com.qmul.Social.Network.service;

import com.qmul.Social.Network.exception.CourseNotFoundException;
import com.qmul.Social.Network.exception.DepartmentNotFoundException;
import com.qmul.Social.Network.model.persistence.Course;
import com.qmul.Social.Network.model.persistence.Department;
import com.qmul.Social.Network.model.persistence.User;
import com.qmul.Social.Network.model.persistence.enums.Role;
import com.qmul.Social.Network.model.repository.CourseRepository;
import com.qmul.Social.Network.model.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstitutionService institutionService;

    @Lazy
    @Autowired
    private UserService userService;

    public Department getCurrentUserPeers()
    {
        User user = userService.getCurrentUser();
        return user.getDepartment();
    }

    public Department createDepartmentForCurrentInstitution(String departmentName)
    {
        Department department = new Department();
        department.setName(departmentName);
        department.setInstitution(institutionService.getCurrentInstitution());
        return departmentRepository.save(department);
    }

    public Set<Department> getAllDepartmentofCurrentInstitute()
    {
        return institutionService.getCurrentInstitution().getDepartments();
    }

    public Department getDepartmentByID(Long id)
    {
        Department department = null;
        try {
            department =  departmentRepository.getReferenceById(id);
        }
        catch (Exception e)
        {
            throw new DepartmentNotFoundException("Department not found "+id);
        }
        return department;
    }

    public void deleteDepartmentById(Long id)
    {
        Department department = getDepartmentByID(id);
        departmentRepository.delete(department);
    }

    public Department editDepartmentName(Long id, String newName)
    {
        Department department = getDepartmentByID(id);
        department.setName(newName);
        departmentRepository.save(department);
        return department;
    }

    public Course createCourseInDepartment(Long deptId, String courseName)
    {
        Department department = getDepartmentByID(deptId);
        Course course = new Course();
        course.setName(courseName);
        course.setDepartment(department);
        course = courseRepository.save(course);
        department.getCourses().add(course);
        departmentRepository.save(department);
        return course;
    }

    public Course getCourseById(Long courseId)
    {
        Course course = null;
        try
        {
            course = courseRepository.getReferenceById(courseId);
        }
        catch (Exception e)
        {
            throw new CourseNotFoundException("Course Not Found " +courseId);
        }
        return course;
    }

    public Course editCourseName(Long courseId, String newName)
    {
        Course course = getCourseById(courseId);
        course.setName(newName);
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseId)
    {
        Course course = getCourseById(courseId);
        courseRepository.delete(course);
    }

    public Set<Course> getAllCoursesOfDepartment(Long deptId)
    {
        return getDepartmentByID(deptId).getCourses();
    }

    public  Set<User> getCurrentDepartmentStaffs() {
        Set<User> users = userService.getCurrentUser().getDepartment().getUsers();
        Set<User> staffs = users.stream().filter(user -> user.getRoles().contains(Role.STAFF)).collect(Collectors.toSet());
        return staffs;
    }
}
