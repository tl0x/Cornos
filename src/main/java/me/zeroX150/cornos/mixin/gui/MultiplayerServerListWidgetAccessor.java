package me.zeroX150.cornos.mixin.gui;

import java.util.concurrent.ThreadPoolExecutor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;

@Mixin(MultiplayerServerListWidget.class)
public interface MultiplayerServerListWidgetAccessor {
	@Accessor("SERVER_PINGER_THREAD_POOL")
	static ThreadPoolExecutor getServerPingerThreadPool() {
		throw new AssertionError();
	}
}
