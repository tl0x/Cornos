package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.Rnd;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class RandomBook extends Command {
    public RandomBook() {
        super("RandomBook", "Creates random noise into a book", new String[]{"randombook","cbook","rndbook","wbook"});
    }

    @Override
    public void onExecute(String[] args) {
        if(Cornos.minecraft.player.inventory.getMainHandStack().getItem() != Items.WRITABLE_BOOK) {
            STL.notifyUser("Hold a book please thank you");
            return;
        }
        if (args.length == 0) {
            STL.notifyUser("Provide a maximum size please thank you (or \"all\" to write the entire book)");
            return;
        }
        if (!"all".equalsIgnoreCase(args[0]) && !STL.tryParseL(args[0])) {
            STL.notifyUser("Provide a **valid** maximum size please thank you");
            return;
        }
        long max = "all".equalsIgnoreCase(args[0])?0xFFFFFF:Long.parseLong(args[0]);
        ListTag pages = new ListTag();
        long currentSize = 0;
        StringBuilder a = new StringBuilder();
        while(currentSize < max && pages.size() < 100) {
            for(int i = 0;i<255;i++) {
                String current = Rnd.rndBinStr(1);
                int l = current.getBytes().length;
                currentSize+=l;
                if(currentSize > max) break;
                a.append(current);
            }
            pages.add(StringTag.of(a.toString()));
            a = new StringBuilder();
        }
        Cornos.minecraft.player.inventory.getMainHandStack().putSubTag("pages", pages);
        STL.notifyUser("done.");
    }
}
