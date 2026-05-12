package io.c0ded.worldeventcomms.client.compat;

import io.c0ded.worldeventcomms.client.text.ModTexts;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class WynntilsCompat {

    public static void sendWarning(FabricClientCommandSource source) {
        source.sendFeedback(ModTexts.wynntilsCompat());
    }
}
