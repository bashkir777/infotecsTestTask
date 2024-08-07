package com.bashkir777.commands;

import com.bashkir777.interfaces.Command;
import com.bashkir777.services.StudentService;

public class Remove implements Command {

    private final StudentService studentService;
    private final String[] args;

    public Remove(StudentService studentService, String... args){
        this.studentService = studentService;
        this.args = args;
    }

    @Override
    public void execute() throws Exception {
        studentService.removeStudentById(Integer.parseInt(args[0]));
    }
}
