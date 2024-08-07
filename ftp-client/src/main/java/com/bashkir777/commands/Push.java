package com.bashkir777.commands;

import com.bashkir777.interfaces.Command;
import com.bashkir777.interfaces.FTPClient;
import com.bashkir777.services.StudentService;

public class Push implements Command {
    private final FTPClient ftpClient;
    private final StudentService studentService;

    public Push(FTPClient ftpClient, StudentService studentService) {
        this.ftpClient = ftpClient;
        this.studentService = studentService;
    }

    @Override
    public void execute() throws Exception {
        ftpClient.uploadFile(
                studentService.queueToString(), System.getProperty("REMOTE_FILE_NAME")
        );
        System.out.println("Changes has been pushed to ftp-server.");
    }
}
