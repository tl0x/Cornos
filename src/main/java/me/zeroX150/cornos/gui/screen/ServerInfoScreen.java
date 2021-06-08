package me.zeroX150.cornos.gui.screen;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.etc.helper.STL;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerInfoScreen extends Screen {
    public ServerInfoScreen() {
        super(Text.of(""));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fill(matrices,0,0,width,height,0x50000000);

        List<String> text = new ArrayList<>();
        text.add("Connected to "+ Cornos.minecraft.getNetworkHandler().getConnection().getAddress());
        text.add("Average packets UP: "+ STL.roundToNTh(Cornos.minecraft.getNetworkHandler().getConnection().getAveragePacketsSent(),2));
        text.add("Average packets DOWN: "+ STL.roundToNTh(Cornos.minecraft.getNetworkHandler().getConnection().getAveragePacketsReceived(),2));
        text.add("The integrated server is "+(Cornos.minecraft.isIntegratedServerRunning()?"§arunning":"§offline"));
        text.add("TPS: "+HudElements.tps);
        text.add("");
        text.add("");
        text.add("");

        while(HudElements.tpsHistory.size()>92) HudElements.tpsHistory.remove(0);
        while(HudElements.tpsAvgHistory.size()>92) HudElements.tpsAvgHistory.remove(0);
        int y = ((int) text.stream().filter(s -> !s.isEmpty()).count())*10+30;
        int x = 5;
        double prevX = -1;
        double prevY = -1;
        for (Double aDouble : HudElements.tpsHistory.toArray(new Double[0])) {
            x+=2;
            double currY = y-aDouble;
            if (prevX == -1) prevX = x;
            if(prevY == -1) prevY = currY;
            Renderer.renderLineScreen(prevX,prevY,x,currY, Color.CYAN,2);
            prevX = x;
            prevY = currY;
        }

        text.add("Player count: "+ Cornos.minecraft.getNetworkHandler().getPlayerList().size());
        text.add("Players:");
        int i = 0;
        for (PlayerListEntry playerListEntry : new ArrayList<>(Cornos.minecraft.getNetworkHandler().getPlayerList())) {
            text.add("  "+playerListEntry.getProfile().getName()+" "+playerListEntry.getProfile().getId()+" ("+playerListEntry.getLatency()+" ms)");
            i++;
            if (i > 6) break;
        }
        if (i>6) text.add("  ... ("+(Cornos.minecraft.getNetworkHandler().getPlayerList().size()-7)+" more)");

        text.add("Your connection is "+(Cornos.minecraft.getNetworkHandler().getConnection().isEncrypted()?"§aencrypted":"§cnot encrypted"));

        int offset = -5;
        for (String s : text) {
            textRenderer.draw(matrices,s,5,offset+=10,0xFFFFFF);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
