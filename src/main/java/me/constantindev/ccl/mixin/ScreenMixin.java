package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.Friend;
import me.constantindev.ccl.etc.FriendsManager;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.KeybindMan;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.etc.reg.CommandRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow
    @Nullable
    protected MinecraftClient client;

    @Inject(method = "sendMessage(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    public void onChatMessageSent(String msg, CallbackInfo cbi) {
        KeybindMan.freezeTabGui = true;
        String prefix = Cornos.config.mconf.getByName("prefix").value;
        if (msg.toLowerCase().startsWith(prefix.toLowerCase())) {
            cbi.cancel();
            assert this.client != null;
            this.client.inGameHud.getChatHud().addToMessageHistory(msg);
            String trimmed = msg.substring(prefix.length());
            String[] args = trimmed.split(" +");
            String command = args[0].toLowerCase();
            String[] argsTrimmed = ArrayUtils.subarray(args, 1, args.length);
            STL.notifyUser(Formatting.DARK_BLUE + "> " + Formatting.BLUE + command);
            Command c = CommandRegistry.search(command);
            if (c == null) {
                STL.notifyUser(Formatting.RED + "That command was not found");
                return;
            }
            c.onExecute(argsTrimmed);
            return;
        }
        if (!Cornos.friendsManager.getFriends().isEmpty()) {
            if (msg.contains("-")) {
                String[] strings = msg.split(" ");
                for (String string : strings) {
                    if (string.contains("-")) {
                        for (Friend friend : Cornos.friendsManager.getFriends().values()) {
                            if (friend.getFakeName().equalsIgnoreCase(string.replace("-", ""))) {
                                msg = msg.replace(string, friend.getRealName());
                            }
                        }
                    }
                }
            }
        }
    }
}
