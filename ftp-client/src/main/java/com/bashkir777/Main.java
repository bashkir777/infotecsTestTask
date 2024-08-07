package com.bashkir777;

import com.bashkir777.commands.CommandType;

import com.bashkir777.commands.Help;
import com.bashkir777.interfaces.Command;
import com.bashkir777.interfaces.FTPClient;
import com.bashkir777.services.CommandService;
import com.bashkir777.services.FTPClientPassive;
import com.bashkir777.services.StudentService;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Attach to this container using: docker attach "
                + System.getenv("FTP_ACTIVE_HOST"));

        System.out.println("After connecting to the container, type 'start' and press Enter.");


        System.setProperty("REMOTE_FILE_NAME", "remoteFile.txt");

        Scanner scanner = new Scanner(System.in);

        while(!(scanner.nextLine()).equals("start")){
            System.out.println("Please type 'start' and press Enter to initiate the process.");
        }
        
        (new Help()).execute();

        StudentService studentService = new StudentService();

        // by default client is passive
        FTPClient ftpClient = new FTPClientPassive();

        try{
            String data = ftpClient.downloadFile(System.getProperty("REMOTE_FILE_NAME"));
            studentService.uploadQueue(data);
        }catch (IOException ioException){
            assert true;
        }


        CommandService commandService = new CommandService(studentService, ftpClient);

        while (true) {
            try{
                System.out.print("Enter command: ");
                String input = scanner.nextLine();
                if(input.equals("exit")){
                    break;
                }
                String[] commandArr = input.split(" ");

                if (commandArr.length == 0) {
                    throw new IllegalArgumentException();
                }

                CommandType commandType = commandService.getCommandTypeByName(commandArr[0]);

                if (commandType == null) {
                    throw new IllegalArgumentException();
                }

                String[] commandArgs = new String[commandArr.length - 1];
                System.arraycopy(commandArr, 1, commandArgs, 0, commandArr.length - 1);

                Command command = commandService.constructCommand(commandType, commandArgs);

                if(command == null){
                    throw new IllegalArgumentException();
                }

                command.execute();

            }catch (NoSuchElementException noSuchElementException){
                break;
            }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
                System.out.println("Wrong syntax. Use command 'help' to view available commands and their usage.");
            }
        }

    }
}