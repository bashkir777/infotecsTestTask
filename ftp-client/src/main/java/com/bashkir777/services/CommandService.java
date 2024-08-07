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
        assert ftpClient != null;
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
            if(args.length != 1) return null;
            return new Add(studentService, args);
        } else if (commandType.equals(CommandType.PUSH)) {
            if(args.length != 0) return null;
            return new Push(ftpClient, studentService);
        } else if (commandType.equals(CommandType.PRINT)) {
            if(args.length != 0) return null;
            return new Print(studentService);
        } else if (commandType.equals(CommandType.SWITCH_MODE)) {
            if(args.length != 0) return null;
            return new SwitchMode(this);
        } else if (commandType.equals(CommandType.REMOVE)) {
            if(args.length != 1) return null;
            return new Remove(studentService, args);
        } else if (commandType.equals(CommandType.HELP)) {
            if(args.length != 0) return null;
            return new Help();
        }else if (commandType.equals(CommandType.INFO)) {
            if(args.length != 1) return null;
            return new Info(studentService, args);
        } else {
            return null;
        }
    }
}
