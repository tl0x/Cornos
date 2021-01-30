package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.reg.CommandRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ChatHook {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onChatMessageSent(String msg, CallbackInfo cbi) {
        if (msg.toLowerCase().startsWith(ClientConfig.chatPrefix.toLowerCase())) {
            cbi.cancel();
            String trimmed = msg.substring(ClientConfig.chatPrefix.length());
            String[] args = trimmed.split(" +");
            String command = args[0].toLowerCase();
            String[] argsTrimmed = ArrayUtils.subarray(args, 1, args.length);
            ClientHelper.sendChat(Formatting.DARK_BLUE + "> " + Formatting.BLUE + command);
            Command c = CommandRegistry.getByName(command);
            if (c == null) {
                ClientHelper.sendChat(Formatting.RED + "That command was not found");
                return;
            }
            c.onExecute(argsTrimmed);
        }
    }
}
