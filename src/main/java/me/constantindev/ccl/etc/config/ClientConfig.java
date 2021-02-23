package me.constantindev.ccl.etc.config;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class ClientConfig {
    public static String chatPrefix;
    public static boolean blockNextMainScreenCall;
    public static Block[] xrayBlocks;

    public static void init() {
        chatPrefix = "}";
        blockNextMainScreenCall = true;
        xrayBlocks = new Block[]{
                Blocks.DIAMOND_ORE,
                Blocks.COAL_ORE,
                Blocks.EMERALD_ORE,
                Blocks.GOLD_ORE,
                Blocks.IRON_ORE,
                Blocks.LAPIS_ORE,
                Blocks.ANCIENT_DEBRIS,
                Blocks.REDSTONE_ORE
        };
    }
}
