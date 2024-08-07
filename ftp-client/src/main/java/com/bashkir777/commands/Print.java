package com.bashkir777.commands;

import com.bashkir777.entities.Student;
import com.bashkir777.interfaces.Command;
import com.bashkir777.services.StudentService;

import java.util.PriorityQueue;

public class Print implements Command {

    private final StudentService studentService;

    public Print(StudentService studentService){
        this.studentService = studentService;
    }

    @Override
    public void execute() throws Exception {
        PriorityQueue<Student> priorityQueue = studentService.getPriorityQueue();
        if(priorityQueue.isEmpty()){
            System.out.println("There are no students");
            return;
        }
        System.out.println("List of students sorted by name: ");
        PriorityQueue<Student> priorityQueueCopy
                = new PriorityQueue<>(priorityQueue);
        while(!priorityQueueCopy.isEmpty()){
            Student student = priorityQueueCopy.poll();
            System.out.println("id: " + student.getId() + " name: " + student.getName());
        }
    }
}
