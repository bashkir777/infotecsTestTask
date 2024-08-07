package com.bashkir777.commands;

import com.bashkir777.entities.Student;
import com.bashkir777.interfaces.Command;
import com.bashkir777.services.StudentService;

public class Info implements Command {

    private final StudentService studentService;
    private final String[] args;

    public Info(StudentService studentService, String[] args) {
        this.studentService = studentService;
        this.args = args;
    }

    @Override
    public void execute() throws Exception {
        Student student = studentService.getStudentById(Integer.parseInt(args[0]));
        if(student == null){
            System.out.println("There is no student with id " + args[0]);
        }else{
            System.out.println("Student name: " + student.getName());
        }
    }
}
