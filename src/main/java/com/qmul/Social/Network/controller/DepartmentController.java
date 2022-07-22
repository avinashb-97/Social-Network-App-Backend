package com.qmul.Social.Network.controller;

import com.qmul.Social.Network.dto.DepartmentDTO;
import com.qmul.Social.Network.model.persistence.Department;
import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.requests.CreateDepartmentRequest;
import com.qmul.Social.Network.service.DepartmentService;
import com.qmul.Social.Network.utils.HelperUtil;
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
        Institution institution = HelperUtil.getCurrentInstitution();
        Department department = departmentService.createDepartment(institution, deptName);
        return ResponseEntity.ok(DepartmentDTO.convertEntityToDepartmentDTO(department));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartMent()
    {
        Institution institution = HelperUtil.getCurrentInstitution();
        Set<Department> departmentSet = institution.getDepartments();
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


}
