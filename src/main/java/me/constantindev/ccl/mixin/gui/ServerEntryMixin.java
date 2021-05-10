package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.Renderer;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class ServerEntryMixin {

    private final Identifier infoImage = new Identifier("ccl", "gui/infoicon16x.png");
    @Shadow
    @Final
    private MultiplayerScreen screen;
    @Shadow
    @Final
    private ServerInfo server;
    private byte infoResolveState = 0;
    private String realIP;
    private String protection;

    @Inject(at = {@At("HEAD")}, method = "render")
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        Renderer.drawImage(matrices, this.infoImage, x + entryWidth - 15, y + 12, 10, 10, 1, 1, 1);
        GL11.glDisable(GL11.GL_BLEND);

        List<Text> list = new ArrayList<>(Collections.emptyList());
        list.add(Text.of("Host: " + server.address));
        wrapLong("Version: " + server.version.getString() + " - " + server.protocolVersion, list);
        if (infoResolveState == 0) {
            try {
                InetAddress address = java.net.InetAddress.getByName(server.address);
                realIP = address.getHostAddress();
                infoResolveState = 1;
                MultiplayerServerListWidgetAccessor.getServerPingerThreadPool().submit(() -> {
                    if (Cornos.minecraft.currentScreen instanceof MultiplayerScreen) {
                        MultiplayerScreen multiplayerScreen = (MultiplayerScreen) Cornos.minecraft.currentScreen;
                        ServerInfo serverInfo = new ServerInfo(address.getHostAddress(), address.getHostAddress(), false);
                        try {
                            multiplayerScreen.getServerListPinger().add(serverInfo, () -> {
                                if (!serverInfo.version.asString().equalsIgnoreCase(server.version.asString())) {
                                    protection = "\u00A7c" + serverInfo.version.asString();
                                } else {
                                    protection = "\u00A7aNone";
                                }
                            });
                        } catch (UnknownHostException e) {
                            protection = "\u00A7cUnknown (Might be using Cloudflare or something)";
                            e.printStackTrace();
                        }
                    }
                    infoResolveState = 2;
                });
                if (infoResolveState != 2) {
                    protection = "\u00A7cError";
                }
                infoResolveState = 2;
            } catch (UnknownHostException e) {
                realIP = "\u00A7cError";
                infoResolveState = 1;
                protection = "\u00A7cError";
                infoResolveState = 2;
            }
        }
        list.add(Text.of("Address: " + realIP));
        list.add(Text.of("Protection: " + protection));

        int t = mouseX - x;
        int u = mouseY - y;
        if (t >= entryWidth - 15 - 2 && t <= entryWidth + 10 - 15 - 2 && u >= 12 && u <= 24) {
            this.screen.setTooltip(list);
        }
    }

    private void wrapLong(String string, List<Text> list) {
        String string2 = WordUtils.wrap(string + " ", 24);
        String[] strings = string2.split("\n");
        for (String s : strings) {
            list.add(Text.of(s.substring(0, s.length() - 1)));
        }
    }
}
