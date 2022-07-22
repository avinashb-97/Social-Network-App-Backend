package com.qmul.Social.Network.service;

import com.qmul.Social.Network.exception.DepartmentNotFoundException;
import com.qmul.Social.Network.model.persistence.Department;
import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.model.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Institution institution, String departmentName)
    {
        Department department = new Department();
        department.setName(departmentName);
        department.setInstitution(institution);
        return departmentRepository.save(department);
    }

    public Department getDepartmentByID(Long id)
    {
        Department department = departmentRepository.getReferenceById(id);
        if(department == null)
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
