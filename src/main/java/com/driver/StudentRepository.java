package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private final Map<String, Student> studentMap;
    private final Map<String, Teacher> teacherMap;
    private final Map<String, List<String>> teacherStudentMapping;

    public StudentRepository() {
        this.studentMap = new HashMap<>();
        this.teacherMap = new HashMap<>();
        this.teacherStudentMapping = new HashMap<>();
    }

    public void saveStudent(Student student) {
        studentMap.put(student.getName(), student);
    }

    public void saveTeacher(Teacher teacher) {
        teacherMap.put(teacher.getName(), teacher);
    }

    public void saveStudentTeacherPair(String studentName, String teacherName) {
        if (studentMap.containsKey(studentName) && teacherMap.containsKey(teacherName)) {
            List<String> students = teacherStudentMapping.getOrDefault(teacherName, new ArrayList<>());
            if (!students.contains(studentName)) {
                students.add(studentName);
                teacherStudentMapping.put(teacherName, students);
            }
        }
    }

    public Student findStudent(String studentName) {
        return studentMap.get(studentName);
    }

    public Teacher findTeacher(String teacherName) {
        return teacherMap.get(teacherName);
    }

    public List<String> findStudentsFromTeacher(String teacherName) {
        return teacherStudentMapping.getOrDefault(teacherName, new ArrayList<>());
    }

    public List<String> findAllStudents() {
        return new ArrayList<>(studentMap.keySet());
    }

    public void deleteTeacher(String teacherName) {
        if (teacherMap.containsKey(teacherName)) {
            List<String> students = teacherStudentMapping.remove(teacherName);
            if (students != null) {
                for (String student : students) {
                    studentMap.remove(student);
                }
            }
            teacherMap.remove(teacherName);
        }
    }

    public void deleteAllTeachers() {
        teacherMap.clear();
        teacherStudentMapping.clear();
        studentMap.clear();
    }
}
