package com.enjin.enjincraft.spigot.util;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>Message related operations.</p>
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will generally not be thrown for a {@code null} input.
 * Each method documents its behaviour in detail.</p>
 *
 * @since 1.0
 */
public class MessageUtils {

    @SuppressWarnings("deprecation")
    public static final BukkitAudiences AUDIENCES = BukkitAudiences.create(JavaPlugin.getProvidingPlugin(MessageUtils.class));
    public static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.legacy('&');
    public static final char TEXT_FORMAT_TOKEN = '&';
    public static final String PLUGIN_PREFIX = "&7[&EnjinCraft&7]";

    private MessageUtils() {
        super();
    }

    public static void logString(String message) {
        sendString(Bukkit.getConsoleSender(), String.format("%s &f%s", PLUGIN_PREFIX, message));
    }

    public static void logStrings(String... messages) {
        for (String message : messages)
            sendString(Bukkit.getConsoleSender(), message);
    }

    public static void logStrings(Iterable<String> messages) {
        for (String message : messages)
            sendString(Bukkit.getConsoleSender(), message);
    }

    public static void logComponent(TextComponent component) {
        AUDIENCES.console().sendMessage(component);
    }

    public static void logComponents(TextComponent... components) {
        if (components != null) {
            for (TextComponent component : components)
                sendComponent(Bukkit.getConsoleSender(), component);
        }
    }

    public static void logComponents(Iterable<TextComponent> components) {
        for (TextComponent component : components)
            sendComponent(Bukkit.getConsoleSender(), component);
    }

    public static void sendString(CommandSender sender, String message) {
        if (sender == null || message == null)
            return;
        TextComponent component = LEGACY_COMPONENT_SERIALIZER.deserialize(message);
        sendComponent(sender, component);
    }

    public static void sendStrings(CommandSender sender, String... messages) {
        for (String message : messages)
            sendString(sender, message);
    }

    public static void sendStrings(CommandSender sender, Iterable<String> messages) {
        for (String message : messages)
            sendString(sender, message);
    }

    public static void sendComponent(CommandSender sender, TextComponent component) {
        if (sender == null || component == null)
            return;
        AUDIENCES.sender(sender).sendMessage(component);
    }

    public static void sendComponents(CommandSender sender, TextComponent... components) {
        if (components != null) {
            for (TextComponent component : components)
                sendComponent(sender, component);
        }
    }

    public static void sendComponents(CommandSender sender, Iterable<TextComponent> components) {
        for (TextComponent component : components)
            sendComponent(sender, component);
    }

}
