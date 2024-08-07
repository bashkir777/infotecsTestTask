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
        boolean removed = studentService.removeStudentById(Integer.parseInt(args[0]));
        if(removed){
            System.out.println("Student has been removed. Don't forget to push changes.");
        }else{
            System.out.println("There is no student with id " + args[0]);
        }
    }
}
