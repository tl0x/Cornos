package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatMessageC2SPacket.class)
public class ChatMessageC2SPacketMixin {
    /* 𝐀𝐁𝐂𝐃𝐄𝐅𝐆𝐇𝐈𝐉𝐊𝐋𝐌𝐍𝐎𝐏𝐐𝐑𝐒𝐓𝐔𝐕𝐖𝐗𝐘𝐙 */
    @Shadow
    private String chatMessage;

    @Inject(method = "getChatMessage", at = @At("HEAD"))
    public void gCM(CallbackInfoReturnable<String> cir) {
        if (ModuleRegistry.getByName("fancychat").isEnabled()) {
            chatMessage = chatMessage.toUpperCase();

            for (String[] s : ClientConfig.dict) {
                chatMessage = chatMessage.replaceAll(s[0], s[1]);
            }
        }
    }
}
