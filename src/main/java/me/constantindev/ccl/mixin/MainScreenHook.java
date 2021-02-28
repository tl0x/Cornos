package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.gui.MainScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MainScreenHook extends Screen {
    public MainScreenHook() {
        super(Text.of(""));
    }

    @Inject(method = "init", cancellable = true, at = @At("HEAD"))
    public void init(CallbackInfo cbi) {
        if (ModuleRegistry.getByName("ClientConfig").mconf.getByName("homescreen").value.equals("client")) {
            MinecraftClient.getInstance().openScreen(new MainScreen());
            cbi.cancel();
            return;
        }
        this.addButton(new ButtonWidget(width - 121, 1, 120, 20, Text.of("Return to pog menu"), (b) -> {
            ModuleRegistry.getByName("ClientConfig").mconf.getByName("homescreen").setValue("client");
            MinecraftClient.getInstance().openScreen(this);
        }));
    }
}
