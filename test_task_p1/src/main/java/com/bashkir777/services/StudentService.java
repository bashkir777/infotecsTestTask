package com.bashkir777.services;

import com.bashkir777.entities.Student;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class StudentService {

    private PriorityQueue<Student> priorityQueue = new PriorityQueue<>();

    public void uploadQueue(String string) {
        Scanner scanner = new Scanner(string);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("\"id\":")) {
                String id = line.substring(line.indexOf(":") + 3, line.indexOf("\","));
                line = scanner.nextLine().trim();
                String name = line.substring(line.indexOf(":") + 3, line.lastIndexOf("\""));

                Student student = new Student(Integer.parseInt(id), name);
                priorityQueue.add(student);

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
        }
        scanner.close();
    }


    public String queueToString() {
        if(priorityQueue.isEmpty()) return "";
        PriorityQueue<Student> copy = new PriorityQueue<>(priorityQueue);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n\t\"students\": [\n");
        while (!copy.isEmpty()) {
            stringBuilder.append(copy.poll().toString()).append(",\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("\n  ]\n}");
        return stringBuilder.toString();
    }

    public void addStudent(Student student){
        priorityQueue.add(student);
    }

    public void removeStudentById(int id) {
        Iterator<Student> iterator = priorityQueue.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId() == id) {
                iterator.remove();
                break;
            }
        }
    }

    public void clean(){
        this.priorityQueue.clear();
    }

    public PriorityQueue<Student> getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(PriorityQueue<Student> priorityQueue) {
        assert priorityQueue != null : "Queue can't be null";
        this.priorityQueue = priorityQueue;
    }

    public Student getStudentById(int id) {
        for (Student student : priorityQueue) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
}
