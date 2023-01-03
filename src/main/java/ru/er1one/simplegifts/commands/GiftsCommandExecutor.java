package ru.er1one.simplegifts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import ru.er1one.simplegifts.SimpleGifts;

import java.util.ArrayList;
import java.util.List;

public class GiftsCommandExecutor implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("give", "info", "help", "reload");
        } else {
            if (args[0].equalsIgnoreCase("give")) {
                return new ArrayList<>(SimpleGifts.getInstance().getGiftManager().getGifts());
            }
        }
        return new ArrayList<>();
    }
}
