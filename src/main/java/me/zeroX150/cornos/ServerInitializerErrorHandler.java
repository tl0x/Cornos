package me.zeroX150.cornos;

import org.apache.logging.log4j.Level;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.client.MinecraftClient;

public class ServerInitializerErrorHandler implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		Cornos.log(Level.INFO,
				"This mod (Cornos) is designed to be used on a client. Please remove this mod from your mods folder immediately. Server will now shut down.");
		MinecraftClient.getInstance().stop();
	}
}
