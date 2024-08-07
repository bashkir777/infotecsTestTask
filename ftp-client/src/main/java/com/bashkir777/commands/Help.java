package com.bashkir777.commands;

import com.bashkir777.interfaces.Command;

public class Help implements Command {

    @Override
    public void execute() throws Exception {
        System.out.println("use 'add <name>' to create new student.");
        System.out.println("use 'remove <id>' to remove student by id.");
        System.out.println("use 'info <id>' to see student's info.");
        System.out.println("use 'print' to see the list of students sorted by name.");
        System.out.println("use 'switch_mode' to switch ftp mode (by default it is passive).");
        System.out.println("use 'push' to save your changes.");
        System.out.println("use 'exit' to finish session.");
    }
}
