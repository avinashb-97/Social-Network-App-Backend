package com.qmul.Social.Network.service;

import com.qmul.Social.Network.exception.DepartmentNotFoundException;
import com.qmul.Social.Network.model.persistence.Department;
import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private InstitutionService institutionService;

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
        departmentRepository.deleteById(id);
    }

    public Department editDepartmentName(Long id, String newName)
    {
        Department department = getDepartmentByID(id);
        department.setName(newName);
        departmentRepository.save(department);
        return department;
    }
}
