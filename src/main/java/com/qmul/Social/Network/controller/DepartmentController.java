package com.qmul.Social.Network.controller;

import com.qmul.Social.Network.dto.CourseDTO;
import com.qmul.Social.Network.dto.DepartmentDTO;
import com.qmul.Social.Network.model.persistence.Course;
import com.qmul.Social.Network.model.persistence.Department;
import com.qmul.Social.Network.model.requests.CreateCourseRequest;
import com.qmul.Social.Network.model.requests.CreateDepartmentRequest;
import com.qmul.Social.Network.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/api/department")
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest)
    {

        String deptName = createDepartmentRequest.getName();
        Department department = departmentService.createDepartmentForCurrentInstitution(deptName);
        return ResponseEntity.ok(DepartmentDTO.convertEntityToDepartmentDTO(department));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment()
    {
        Set<Department> departmentSet = departmentService.getAllDepartmentofCurrentInstitute();
        return ResponseEntity.ok(DepartmentDTO.convertEntityListToDepartmentDTOList(departmentSet));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentByID(@PathVariable Long departmentId)
    {
        Department department = departmentService.getDepartmentByID(departmentId);
        return ResponseEntity.ok(DepartmentDTO.convertEntityToDepartmentDTO(department));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity deleteDepartment(@PathVariable Long departmentId)
    {
        departmentService.deleteDepartmentById(departmentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> editDepartmentName(@PathVariable Long departmentId, @RequestBody CreateDepartmentRequest createDepartmentRequest)
    {
        String newName = createDepartmentRequest.getName();
        Department department = departmentService.editDepartmentName(departmentId, newName);
        return ResponseEntity.ok(DepartmentDTO.convertEntityToDepartmentDTO(department));
    }


    @PostMapping("/{departmentId}/course")
    public ResponseEntity<CourseDTO> createCourse(@PathVariable Long departmentId, @RequestBody CreateCourseRequest createCourseRequest)
    {
        String courseName = createCourseRequest.getName();
        Course course = departmentService.createCourseInDepartment(departmentId, courseName);
        return ResponseEntity.ok(CourseDTO.convertEntityToCourseDTO(course));
    }

    @GetMapping("/{departmentId}/course}")
    public ResponseEntity<List<CourseDTO>> getAllCoursesOfDepartment(@PathVariable Long departmentId)
    {
        Set<Course> courses = departmentService.getAllCoursesOfDepartment(departmentId);
        return ResponseEntity.ok(CourseDTO.convertEntityListToCourseDTOList(courses));
    }

    @GetMapping("/{departmentId}/course/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long departmentId, @PathVariable Long courseId)
    {
        Course course = departmentService.getCourseById(courseId);
        return ResponseEntity.ok(CourseDTO.convertEntityToCourseDTO(course));
    }

    @PutMapping("/{departmentId}/course/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long departmentId, @PathVariable Long courseId, @RequestBody CreateCourseRequest createCourseRequest)
    {
        Course course = departmentService.editCourseName(courseId, createCourseRequest.getName());
        return ResponseEntity.ok(CourseDTO.convertEntityToCourseDTO(course));
    }

    @DeleteMapping("/{departmentId}/course/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable Long departmentId, @PathVariable Long courseId)
    {
        departmentService.deleteCourse(courseId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/peers")
    public ResponseEntity<DepartmentDTO> getCurrentDepartmentPeers()
    {
        Department department = departmentService.getCurrentUserPeers();
        return ResponseEntity.ok(DepartmentDTO.convertEntityToDepartmentDTO(department));
    }


}
