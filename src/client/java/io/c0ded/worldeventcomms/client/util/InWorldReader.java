package io.c0ded.worldeventcomms.client.util;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.DisplayEntity;

public class InWorldReader {
    public static InWorldResult<String> getTargets(ClientWorld world) {
        String targets;
        for (Entity entity : world.getEntities()) {
            if (entity instanceof DisplayEntity.TextDisplayEntity display) {
                if (display.getText().getString().matches("\\d+ targets? remains?")) {
                    targets = display.getText().getString().replaceAll("\\D", "");
                    return InWorldResult.ok(targets);
                }
            }
        }
        return InWorldResult.fail("Could not find target amount in world.");
    }
}
