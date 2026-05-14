package io.c0ded.worldeventcomms.client.util;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ScoreboardReader {
    public static ScoreboardResult<Map.Entry<String, String>> getNextWorldEvent(Scoreboard scoreboard) {
        if (scoreboard != null && scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR) != null) {
            Collection<ScoreboardEntry> entries = scoreboard.getScoreboardEntries(scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR));
            Optional<ScoreboardEntry> entry = entries.stream()
                .filter(e -> e.name() != null && e.name().getString().contains("Tracked World Event:"))
                .findFirst();
            if (entry.isPresent()) {
                int contentIndex = entry.get().value() - 1;
                Optional<ScoreboardEntry> eventEntry = entries.stream()
                    .filter(e -> e.name() != null && e.value() == contentIndex)
                    .findFirst();
                if (eventEntry.isPresent()) {
                    Text eventInfo = eventEntry.get().name();
                    String eventName = eventInfo.getString().replaceAll("§[0-9a-fA-Fklmnor]", "").replaceFirst(" \\(.*\\)", "");
                    String eventTime = eventInfo.getString().replaceAll("§[0-9a-fA-Fklmnor]", "").replaceFirst("^[^(]*", "").replaceFirst(" left\\)", "").replace("(", "");
                    // this is a horrible way to get the time, but i don't care.
                    if (eventTime.isEmpty()) return ScoreboardResult.fail("Could not get time left for tracked world event. Is the world event in progress?");
                    return ScoreboardResult.ok(Map.entry(eventName, eventTime));
                } else {
                    return ScoreboardResult.fail("Could not find full information for tracked world event.");
                }
            } else {
                return ScoreboardResult.fail("Could not find tracked world event information. Are you tracking a world event?");
            }
        } else {
            return ScoreboardResult.fail("Could not find the scoreboard, is it turned off?");
        }
    }

    public static ScoreboardResult<Map.Entry<String, String>> getCurrentWorldEvent(Scoreboard scoreboard) {
        if (scoreboard != null && scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR) != null) {
            Collection<ScoreboardEntry> entries = scoreboard.getScoreboardEntries(scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR));
            Optional<ScoreboardEntry> entry = entries.stream()
                    .filter(e -> e.name() != null && e.name().getString().contains("World Event:") && !e.name().getString().contains("Tracked"))
                    .findFirst();
            if (entry.isPresent()) {
                String wavesLeft;
                String targetsLeft;
                int waveIndex = entry.get().value() - 2;
                Optional<ScoreboardEntry> waveEntry = entries.stream()
                        .filter(e -> e.name() != null && e.value() == waveIndex)
                        .findFirst();
                if (waveEntry.isPresent()) {
                    Text eventInfo = waveEntry.get().name();
                    wavesLeft = eventInfo.getString().replaceAll("§[0-9a-fA-Fklmnor]", "").replaceAll("\\D", "");
                } else {
                    return ScoreboardResult.fail("Could not find waves left."); // what
                }
                int targetIndex = entry.get().value() - 3;
                Optional<ScoreboardEntry> targetEntry = entries.stream()
                        .filter(e -> e.name() != null && e.value() == targetIndex)
                        .findFirst();
                if (targetEntry.isPresent()) {
                    Text eventInfo = targetEntry.get().name();
                    targetsLeft = eventInfo.getString().replaceAll("§[0-9a-fA-Fklmnor]", "").replaceAll("\\D", "");
                } else {
                    return ScoreboardResult.ok(Map.entry(wavesLeft, "an unknown amount of")); // not a great way to handle this, but it'll do for the time being
                }
                return ScoreboardResult.ok(Map.entry(wavesLeft, targetsLeft));
            } else {
                return ScoreboardResult.fail("Could not find current world event information. Are you in a world event?");
            }
        } else {
            return ScoreboardResult.fail("Could not find the scoreboard, is it turned off?");
        }
    }
}
