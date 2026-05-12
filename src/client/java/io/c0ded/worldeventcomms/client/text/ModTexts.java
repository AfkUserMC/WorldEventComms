package io.c0ded.worldeventcomms.client.text;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModTexts {
    public static Text help(String version) {
        return Text.empty()
                .append(Text.literal("WorldEventComms version " + version)
                        .formatted(Formatting.GREEN))
                .append(Text.literal("\n"))

                .append(Text.literal("Available Commands: (hover)")
                        .formatted(Formatting.YELLOW))
                .append(Text.literal("\n"))

                .append(Text.literal("- ")
                        .formatted(Formatting.GRAY))
                .append(Text.literal("/we next")
                        .formatted(Formatting.AQUA)
                        .styled(style -> style.withHoverEvent(
                                new HoverEvent.ShowText(
                                        Text.literal("Sends your currently tracked world event in chat.")
                                )
                        )))
                .append(Text.literal("\n"))

                .append(Text.literal("- ")
                        .formatted(Formatting.GRAY))
                .append(Text.literal("/we busy")
                        .formatted(Formatting.AQUA)
                        .styled(style -> style.withHoverEvent(
                                new HoverEvent.ShowText(
                                        Text.literal("Sends information about the ongoing world event to chat.")
                                )
                        )))
                .append(Text.literal("\n"))

                .append(Text.literal("- ")
                        .formatted(Formatting.GRAY))
                .append(Text.literal("/we none")
                        .formatted(Formatting.AQUA)
                        .styled(style -> style.withHoverEvent(
                                new HoverEvent.ShowText(
                                        Text.literal("Disappoint everyone by telling them there's no world events.")
                                )
                        )));
    }
    public static Text wynntilsCompat() {
        return Text.empty()
                .append(
                        Text.literal("This may be caused by Wynntils hiding required information.\n")
                                .formatted(Formatting.YELLOW)
                )
                .append(
                        Text.literal("Click here to try and resolve this issue.\n")
                                .formatted(Formatting.DARK_AQUA, Formatting.UNDERLINE)
                                .styled(style -> style
                                        .withClickEvent(new ClickEvent.RunCommand("we fix-wynntils"))
                                        .withHoverEvent(new HoverEvent.ShowText(
                                                Text.literal("As a bonus, permanently disables this message. :)")
                                        ))
                                )
                )
                .append(
                        Text.literal("This message will not show again for this session.")
                                .formatted(Formatting.GRAY, Formatting.ITALIC)
                );
    }
}
