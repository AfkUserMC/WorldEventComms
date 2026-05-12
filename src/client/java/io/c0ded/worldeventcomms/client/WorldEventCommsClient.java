package io.c0ded.worldeventcomms.client;

import io.c0ded.worldeventcomms.client.command.WorldEventCommand;
import io.c0ded.worldeventcomms.client.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

public class WorldEventCommsClient implements ClientModInitializer {

    public static boolean hasWynntils;
    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        ClientCommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess) -> {
                    WorldEventCommand.register(dispatcher);
                }
        );
        hasWynntils = FabricLoader.getInstance().isModLoaded("wynntils");
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            ConfigManager.save();
        });
    }
}
