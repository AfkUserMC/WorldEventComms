package io.c0ded.worldeventcomms;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class WorldEventComms implements ModInitializer {
    public static final String VERSION = FabricLoader.getInstance()
            .getModContainer("worldeventcomms")
            .orElseThrow()
            .getMetadata()
            .getVersion()
            .getFriendlyString();
    @Override
    public void onInitialize() {
    }
}
