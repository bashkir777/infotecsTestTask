package com.bashkir777;

import com.bashkir777.commands.CommandType;

import com.bashkir777.interfaces.Command;
import com.bashkir777.interfaces.FTPClient;
import com.bashkir777.services.CommandService;
import com.bashkir777.services.FTPClientActive;
import com.bashkir777.services.FTPClientPassive;
import com.bashkir777.services.StudentService;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        System.setProperty("REMOTE_FILE_NAME", "remoteFile.txt");

        Scanner scanner = new Scanner(System.in);

        StudentService studentService = new StudentService();

        // by default client is passive
        FTPClient ftpClient = new FTPClientPassive(System.getenv("FTP_USER"), System.getenv("FTP_PASS"));

        try{
            String data = ftpClient.downloadFile(System.getProperty("REMOTE_FILE_NAME"));
            studentService.uploadQueue(data);
        }catch (IOException ioException){
            assert true;
        }


        CommandService commandService = new CommandService(studentService, ftpClient);

        while (true) {
            try{
                String input = scanner.nextLine();
                if(input.equals("exit")){
                    break;
                }
                String[] commandArr = input.split(" ");

                if (commandArr.length == 0) {
                    System.out.println("Invalid command.");
                    continue;
                }

                CommandType commandType = commandService.getCommandTypeByName(commandArr[0]);

                if (commandType == null) {
                    System.out.println("Unknown command: " + commandArr[0]);
                    continue;
                }

                String[] commandArgs = new String[commandArr.length - 1];
                System.arraycopy(commandArr, 1, commandArgs, 0, commandArr.length - 1);

                Command command = commandService.constructCommand(commandType, commandArgs);

                command.execute();

            }catch (NoSuchElementException noSuchElementException){
                System.out.println("Please use command 'exit' if you want to finish session");
            }

        }

    }
}