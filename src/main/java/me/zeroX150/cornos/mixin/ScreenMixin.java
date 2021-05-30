package me.zeroX150.cornos.mixin;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.Friend;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.etc.manager.KeybindManager;
import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.features.command.CommandRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Formatting;

@Mixin(Screen.class)
public class ScreenMixin {
	@Shadow
	@Nullable
	protected MinecraftClient client;

	@Inject(method = "sendMessage(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
	public void onChatMessageSent(String msg, CallbackInfo cbi) {
		KeybindManager.freezeTabGui = true;
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
