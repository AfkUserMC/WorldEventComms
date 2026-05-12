package io.c0ded.worldeventcomms.client.util;

import net.minecraft.client.MinecraftClient;

public class ServerUtils {
    public static boolean isOnWynncraft() {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            if (MinecraftClient.getInstance().getNetworkHandler().getBrand() != null) {
                return MinecraftClient.getInstance().getNetworkHandler().getBrand().equals("Wynn");
            } else return false;
        } else return false;
    }
}
