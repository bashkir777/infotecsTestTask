package com.bashkir777.commands;

import com.bashkir777.interfaces.Command;
import com.bashkir777.services.CommandService;
import com.bashkir777.services.FTPClientActive;
import com.bashkir777.services.FTPClientPassive;

public class SwitchMode implements Command {
    private final CommandService commandService;

    public SwitchMode(CommandService commandService){
        this.commandService = commandService;
    }

    @Override
    public void execute() throws Exception {
        if(commandService.getFtpClient().getClass().equals(FTPClientActive.class)){
            commandService.setFtpClient(
                    new FTPClientPassive(
                            System.getenv("FTP_USER"), System.getenv("FTP_PASS")
                    )
            );
            System.out.println("Mode has been switched successfully. Current mode: Passive");
        }else{
            commandService.setFtpClient(
                    new FTPClientActive(
                            System.getenv("FTP_USER"), System.getenv("FTP_PASS")
                    )
            );
            System.out.println("Mode has been switched successfully. Current mode: Active");
        }

    }
}
