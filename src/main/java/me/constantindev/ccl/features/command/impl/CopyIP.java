/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: CopyIP
# Created by constantin at 23:00, Mär 04 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.command.Command;
import net.minecraft.client.util.Clipboard;

import java.util.Objects;

public class CopyIP extends Command {
    public CopyIP() {
        super("CopyIP", "Copies the current server IP address / domain", new String[]{"copyip", "copydomain", "copyaddress", "cpip", "ip"});
    }

    @Override
    public void onExecute(String[] args) {
        new Clipboard().setClipboard(Cornos.minecraft.getWindow().getHandle(), Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).getConnection().getAddress().toString());
        super.onExecute(args);
    }
}
