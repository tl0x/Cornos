package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.gui.screen.ServerInfoScreen;

public class ServerInfo extends Command {
    public ServerInfo() {
        super("ServerInfo", "Shows some info about the server", new String[]{"serverinfo", "servinfo", "sinfo", "server", "info"});
    }

    @Override
    public void onExecute(String[] args) {
        new Thread(()->{
            STL.sleep(10);
            Cornos.minecraft.openScreen(new ServerInfoScreen());
        }).start();
    }
}
