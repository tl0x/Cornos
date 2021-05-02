package me.constantindev.ccl.module.FUN;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.helper.RandomHelper;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.Text;

public class Clumsy extends Module {
    int i = 1;
    MultiOption name = new MultiOption("name", "randomEN", new String[]{"randomBin", "randomAscii", "randomEN", "none"});
    String[] enNames = new String[]{
            "Cornos on top",
            "suck my dick",
            "this item is worthless",
            "fucking retard",
            "i am dumb as fuck",
            "man.",
            "69420",
            "yep.",
            "just a random item",
            "sex is nice",
            "L",
            "retard moment"
    };

    public Clumsy() {
        super("Clumsy", "oops! drops all items in the game progressively", MType.FUN);
        this.mconf.add(name);
    }

    @Override
    public void onExecute() {
        if (!Cornos.minecraft.player.isCreative()) {
            ClientHelper.sendChat("you need creative");
            setEnabled(false);
            return;
        }
        Item current = Item.byRawId(i);
        if (current == Items.AIR) {
            i = 0;
        }
        ItemStack is = new ItemStack(current);
        String name = "";
        switch (this.name.value) {
            case "randomBin":
                name = RandomHelper.rndBinStr(10);
                break;
            case "randomAscii":
                name = RandomHelper.rndAscii(10);
                break;
            case "randomEN":
                int index = (int) Math.floor(Math.random() * enNames.length);
                name = enNames[index];
                break;
        }
        if (!this.name.value.equals("none")) is.setCustomName(Text.of(name));
        CreativeInventoryActionC2SPacket c = new CreativeInventoryActionC2SPacket(9, is);
        Cornos.minecraft.getNetworkHandler().sendPacket(c);
        ClientHelper.dropItem(9);
        i++;
        super.onExecute();
    }
}
