package io.c0ded.worldeventcomms.client.command;

import com.mojang.brigadier.CommandDispatcher;
import io.c0ded.worldeventcomms.WorldEventComms;
import io.c0ded.worldeventcomms.client.WorldEventCommsClient;
import io.c0ded.worldeventcomms.client.compat.WynntilsCompat;
import io.c0ded.worldeventcomms.client.config.ConfigManager;
import io.c0ded.worldeventcomms.client.session.SessionState;
import io.c0ded.worldeventcomms.client.text.ModTexts;
import io.c0ded.worldeventcomms.client.util.ScoreboardReader;
import io.c0ded.worldeventcomms.client.util.ScoreboardResult;
import io.c0ded.worldeventcomms.client.util.ServerUtils;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.util.Map;

public class WorldEventCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
                ClientCommandManager.literal("we")
                        .then(ClientCommandManager.literal("next")
                        .requires(source -> ServerUtils.isOnWynncraft())
                        .executes(context -> {
                            FabricClientCommandSource source = context.getSource();
                            if (source.getClient().world != null) {
                                ScoreboardResult<Map.Entry<String, String>> worldEvent = ScoreboardReader.getNextWorldEvent(source.getClient().world.getScoreboard());
                                if (!worldEvent.success()) {
                                    source.sendError(Text.literal(worldEvent.error()));
                                    if (worldEvent.error().equals("Could not find tracked world event information. Are you tracking a world event?") && WorldEventCommsClient.hasWynntils && !SessionState.wynntilsWarningShown && !ConfigManager.config.hideWynntilsWarningPermanently) {
                                        WynntilsCompat.sendWarning(source);
                                        SessionState.wynntilsWarningShown = true;
                                    }
                                    return 0;
                                } else {
                                    if (source.getClient().getNetworkHandler() != null) {
                                        source.getClient().getNetworkHandler().sendChatCommand("p Next World Event: " + worldEvent.value().getKey().strip() + " in " + worldEvent.value().getValue().strip());
                                        return 1;
                                    } else {
                                        return 0;
                                    }
                                }
                            } else {
                                return 0;
                            }
                        }))
                        .then(ClientCommandManager.literal("busy")
                        .requires(source -> ServerUtils.isOnWynncraft())
                        .executes(context -> {
                            FabricClientCommandSource source = context.getSource();
                            if (source.getClient().world != null) {
                                ScoreboardResult<Map.Entry<String, String>> worldEvent = ScoreboardReader.getCurrentWorldEvent(source.getClient().world.getScoreboard());
                                if (!worldEvent.success()) {
                                    source.sendError(Text.literal(worldEvent.error()));
                                    if (source.getClient().getNetworkHandler() != null) {
                                        source.getClient().getNetworkHandler().sendChatCommand("p We are currently in a world event. Further details are unavailable.");
                                    }
                                    return 0;
                                } else {
                                    if (source.getClient().getNetworkHandler() != null) {
                                        String wavesPlural;
                                        String targetsPlural;
                                        if (worldEvent.value().getKey().strip().equals("1")) {
                                            wavesPlural = "";
                                        } else {
                                            wavesPlural = "s";
                                        }
                                        if (worldEvent.value().getValue().strip().equals("1")) {
                                            targetsPlural = "";
                                        } else {
                                            targetsPlural = "s";
                                        }
                                        source.getClient().getNetworkHandler().sendChatCommand("p We are currently in a world event with " + worldEvent.value().getKey().strip() + " wave" + wavesPlural + " and " + worldEvent.value().getValue().strip() + " target" + targetsPlural + " left.");
                                        return 1;
                                    } else {
                                        return 0;
                                    }
                                }
                            } else {
                                return 0;
                            }
                        }))
                        .then(ClientCommandManager.literal("none")
                        .requires(source -> ServerUtils.isOnWynncraft())
                        .executes(context -> {
                            FabricClientCommandSource source = context.getSource();
                            if (source.getClient().getNetworkHandler() != null) {
                                source.getClient().getNetworkHandler().sendChatCommand("p There are currently no pending world events.");
                                return 1;
                            } else {
                                return 0;
                            }
                        }))
                        .then(ClientCommandManager.literal("fix-wynntils")
                                .requires(source -> ServerUtils.isOnWynncraft() && WorldEventCommsClient.hasWynntils && !ConfigManager.config.hideWynntilsWarningPermanently)
                                .executes(context -> {
                                    FabricClientCommandSource source = context.getSource();
                                    if (source.getClient().getNetworkHandler() != null) {
                                        source.getClient().getNetworkHandler().sendChatCommand("wynntils config set ContentTrackerOverlay overlay ContentTracker disableTrackerOnScoreboard false");
                                        source.sendFeedback(Text.literal("Fixed! I won't bug you about this anymore.").withColor(Colors.YELLOW));
                                        ConfigManager.config.hideWynntilsWarningPermanently = true;
                                        ConfigManager.save();
                                        return 1;
                                    } else {
                                        return 0;
                                    }
                                })
                        )
                        .requires(source -> ServerUtils.isOnWynncraft())
                        .executes(context -> {
                            context.getSource().sendFeedback(ModTexts.help(WorldEventComms.VERSION));
                            return 1;
                        })
        );
    }
}