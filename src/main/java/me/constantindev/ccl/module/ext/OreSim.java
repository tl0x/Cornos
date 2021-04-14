package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.mod.OreSimManager;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.client.util.math.MatrixStack;

public class OreSim extends Module {


    private final OreSimManager oreSimManager;


    public OreSim() {
        super("OreSim", "Worldseed + Math = Ores", MType.WORLD);
        oreSimManager = new OreSimManager(-3703612961109392421L);
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        oreSimManager.render();
        super.onRender(ms, td);
    }

    @Override
    public void onEnable() {
        oreSimManager.reload();
        //Notification.create("OreSim notice", new String[]{"Lets go"}, 5000);
        super.onEnable();
    }



    @Override
    public void onExecute() {

        super.onExecute();


    }





}
