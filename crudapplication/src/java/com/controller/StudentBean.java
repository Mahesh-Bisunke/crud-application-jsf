
package com.controller;

import java.io.Serializable;
import java.util.*;
import com.entity.Student;
import javax.annotation.*;
import com.util.JPAUtil;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import lombok.Data;

@Data
@ManagedBean
@ViewScoped
public class StudentBean  implements Serializable{
  
    private Student student = new Student();
    private List<Student> studentList;
    private String selectedClass;
    private List<String> subjectList;

    private static final Map<String, List<String>> classSubjects = new HashMap<>();

    static {
        classSubjects.put("Class 10", Arrays.asList("Math", "Science", "English"));
        classSubjects.put("Class 11", Arrays.asList("Physics", "Chemistry", "Biology"));
        classSubjects.put("Class 12", Arrays.asList("Accounts", "Economics", "Business"));
    }

    @PostConstruct
    public void init() {
        loadStudents();
    }

    public void onClassChange() {
        subjectList = classSubjects.getOrDefault(student.getStudentClass(), new ArrayList<>());
    }

    public void saveStudent() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (student.getId() == 0) em.persist(student);
        else em.merge(student);
        tx.commit();
        em.close();
        student = new Student();
        loadStudents();
    }

    public void deleteStudent(Student stu) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.find(Student.class, stu.getId()));
        tx.commit();
        em.close();
        loadStudents();
    }

    public void editStudent(Student stu) {
        this.student = stu;
    }

    public void loadStudents() {
        EntityManager em = JPAUtil.getEntityManager();
        studentList = em.createQuery("from Student", Student.class).getResultList();
        em.close();
    }
}
