package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.FriendsManager;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.STL;

import java.util.Map;

public class Friend extends Command {
    public Friend() {
        super("friend", "Manage friends", new String[]{"friend"});
    }

    @Override
    public void onExecute(String[] args) {

        if (args.length <= 0) {
            STL.notifyUser("Subcommands: add, remove, list, rename");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                try {
                    String name = args[1];
                    String fakeName = args[2];
                    if (Cornos.friendsManager.getFriends().containsKey(name)) {
                        STL.notifyUser("This player is already added as a friend!");
                        return;
                    }
                    assert Cornos.minecraft.player != null;
                    if (name.equalsIgnoreCase(Cornos.minecraft.player.getName().asString())) {
                        STL.notifyUser("You can't add yourself as a friend!");
                        return;
                    }
                    Cornos.friendsManager.getFriends().put(name, new me.constantindev.ccl.etc.Friend(name, fakeName));
                    STL.notifyUser("Added " + name + " as a friend!");
                } catch (ArrayIndexOutOfBoundsException e) {
                    STL.notifyUser("Usage: " + Cornos.config.mconf.getByName("prefix").value + "friend add <Player> <FakeName>");
                }
                break;
            case "remove":
                try {
                    String name = args[1];
                    for (Map.Entry<String, me.constantindev.ccl.etc.Friend> entry : Cornos.friendsManager.getFriends().entrySet()) {
                        if (entry.getValue().getRealName().equalsIgnoreCase(name) || entry.getValue().getFakeName().equalsIgnoreCase(name)) {
                            Cornos.friendsManager.getFriends().remove(entry.getKey());
                            STL.notifyUser("Removed " + name + " from your friends!");
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    STL.notifyUser("Usage: " + Cornos.config.mconf.getByName("prefix").value + "friend remove <Player/FakeName>");
                }
                break;
            case "list":
                try {
                    for (Map.Entry<String, me.constantindev.ccl.etc.Friend> entry : Cornos.friendsManager.getFriends().entrySet()) {
                        STL.notifyUser(entry.getValue().getRealName());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    STL.notifyUser("Usage: " + Cornos.config.mconf.getByName("prefix").value + "friend list");
                }
                break;
            case "rename":
                try {
                    String name = args[1];
                    for (Map.Entry<String, me.constantindev.ccl.etc.Friend> entry : Cornos.friendsManager.getFriends().entrySet()) {
                        if (entry.getValue().getRealName().equalsIgnoreCase(name) || entry.getValue().getFakeName().equalsIgnoreCase(name)) {
                            entry.getValue().setFakeName(name);
                            STL.notifyUser("Renamed " + name + "!");
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    STL.notifyUser("Usage: " + Cornos.config.mconf.getByName("prefix").value + "friend rename <Player/FakeName> <NewName>");
                }
                break;
            default:
                STL.notifyUser("Subcommands: add, remove, list, rename");
                break;
        }

        super.onExecute(args);
    }
}
