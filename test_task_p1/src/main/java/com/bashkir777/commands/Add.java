package com.bashkir777.commands;

import com.bashkir777.entities.Student;
import com.bashkir777.interfaces.Command;
import com.bashkir777.services.StudentService;


public class Add implements Command {
    private final StudentService studentService;
    private final String[] args;

    public Add(StudentService studentService, String... args){
        this.studentService = studentService;
        this.args = args;
    }

    @Override
    public void execute() throws Exception {
        assert args.length == 1 : "add command accepts only one parameter - student name";
        int id = studentService.getPriorityQueue().stream().mapToInt(Student::getId).max().orElse(0) + 1;
        studentService.addStudent(new Student(id, args[0]));
    }

}
