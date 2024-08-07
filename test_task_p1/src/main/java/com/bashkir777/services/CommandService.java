package com.bashkir777.services;

import com.bashkir777.commands.*;
import com.bashkir777.interfaces.Command;
import com.bashkir777.interfaces.FTPClient;

public class CommandService {

    private final StudentService studentService;

    private FTPClient ftpClient;

    public CommandService(StudentService studentService, FTPClient ftpClient){
        this.studentService = studentService;
        this.ftpClient = ftpClient;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public CommandType getCommandTypeByName(String name){
        for(CommandType type : CommandType.values()){
            if(type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }

    public Command constructCommand(CommandType commandType, String... args){
        if(commandType.equals(CommandType.ADD)){
            return new Add(studentService, args);
        } else if (commandType.equals(CommandType.PUSH)) {
            return new Push(ftpClient, studentService);
        } else if (commandType.equals(CommandType.PRINT)) {
            return new Print(studentService);
        } else if (commandType.equals(CommandType.SWITCH_MODE)) {
            return new SwitchMode(this);
        } else if (commandType.equals(CommandType.REMOVE)) {
            return new Remove(studentService, args);
        } else if (commandType.equals(CommandType.HELP)) {
            return new Help();
        }else if (commandType.equals(CommandType.INFO)) {
            return new Info(studentService, args);
        } else {
            return null;
        }
    }
}
