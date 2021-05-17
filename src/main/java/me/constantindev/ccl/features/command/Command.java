package me.constantindev.ccl.features.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Command {
    public final String displayName;
    public final String[] triggers;
    public final String description;

    public Command(@Nullable String dname, String description, @NotNull String[] triggers) {
        this.displayName = (dname == null) ? triggers[0] : dname;
        this.description = description;
        this.triggers = triggers;
    }

    public void onExecute(String[] args) {
    }
}
