package ru.er1one.simplegifts.models;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import ru.er1one.simplegifts.utils.StringUtils;

public class ConfigMessage  {
    private String message;

    public ConfigMessage(String message) {
        this.message = message;
    }

    public void send(CommandSender sender) {
        sender.sendMessage(StringUtils.color(message));
    }

    public ConfigMessage replace(String target, String replacement) {
        this.message = this.message.replace(target, replacement);
        return this;
    }

    public static ConfigMessage getEmptyMessage() {
        return new ConfigMessage("&cСообщение не найдено, обратитесь к администратору сервера.");
    }

    public ConfigMessage clone() {
        return new ConfigMessage(message);
    }
}
