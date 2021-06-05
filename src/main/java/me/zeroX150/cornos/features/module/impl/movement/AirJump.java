package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.KeyBind;
import me.zeroX150.cornos.features.command.impl.Keybind;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class AirJump extends Module {
    KeyBind kb;
    public AirJump() {
        super("AirJump", "weeee²", ModuleType.MOVEMENT);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onExecute() {
        if (Cornos.minecraft.options.keyJump.isPressed()) {
            Cornos.minecraft.player.setOnGround(true);
            Cornos.minecraft.player.fallDistance = 0;
        }
    }
}
