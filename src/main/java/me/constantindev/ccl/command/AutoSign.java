package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.gui.AutoSignEditor;

public class AutoSign extends Command {
    public AutoSign() {
        super("AutoSign", "GUI for autosign", new String[]{"autosign", "as"});
    }

    @Override
    public void onExecute(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Cornos.minecraft.openScreen(new AutoSignEditor());
        }).start();
        super.onExecute(args);
    }
}
