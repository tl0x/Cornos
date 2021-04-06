package me.constantindev.ccl.command;

import com.mojang.authlib.GameProfile;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.RandomHelper;
import net.minecraft.client.network.OtherClientPlayerEntity;

import java.util.UUID;

public class FakePlayer extends Command {
    public FakePlayer() {
        super("FakePlayer","Creates a totally real functional player",new String[]{"cplayer"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 2) {
            ClientHelper.sendChat("ima need an username and uuid");
            return;
        }
        String u = args[0];
        UUID uuid;
        try {
            uuid = UUID.fromString(args[1]);
        } catch (Exception ignored) {
            ClientHelper.sendChat("not sure if thats a valid uuid bruh");
            return;
        }
        OtherClientPlayerEntity o = new OtherClientPlayerEntity(Cornos.minecraft.world,new GameProfile(uuid,u));
        o.copyPositionAndRotation(Cornos.minecraft.player);
        Cornos.minecraft.world.addPlayer((int)RandomHelper.rndD(65535),o);
        super.onExecute(args);
    }
}
