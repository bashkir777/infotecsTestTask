package com.bashkir777;

import com.bashkir777.entities.Student;
import com.bashkir777.services.FTPClient;
import com.bashkir777.services.StudentService;

import java.io.IOException;
import java.util.PriorityQueue;


public class Main {
    public static void main(String[] args) {

        StudentService studentService = new StudentService();
        studentService.addStudent(new Student(1, "Антон"));
        studentService.addStudent(new Student(2, "Виктор"));
        studentService.addStudent(new Student(3, "Борис"));
        try {
            FTPClient ftpClient = new FTPClient("localhost", "test_user", "test_password");

            ftpClient.uploadFile(studentService.queueToString(), "someRemoteFile.txt");

            studentService.clean();
            studentService.uploadQueue((ftpClient.downloadFile("someRemoteFile.txt")));

            PriorityQueue<Student> queueReceived = studentService.getPriorityQueue();

            ftpClient.close();

            for(Student student: queueReceived){
                System.out.println(student);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}