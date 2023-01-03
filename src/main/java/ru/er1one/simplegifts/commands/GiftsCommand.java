package ru.er1one.simplegifts.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ru.er1one.simplegifts.SimpleGifts;
import ru.er1one.simplegifts.models.Gift;
import ru.er1one.simplegifts.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GiftsCommand implements CommandExecutor {

    private final SimpleGifts instance = SimpleGifts.getInstance();


    private final List<Player> removeMode = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command available only for in-game players");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            instance.getMessage("no-args").send(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "give":
                processGive(player, args);
                break;
            case "reload":
                processReloadPlugin(player);
                break;
            case "info":
                processInfo(player);
                break;
            case "help":
                processHelp(player);
                break;
        }

        return true;
    }


    private void processGive(Player player, String[] args) {
        if (args.length == 1) {
            instance.getMessage("no-args").send(player);
            return;
        }

        if (!player.hasPermission("simplegifts.give")) {
            instance.getMessage("no-perms").send(player);
            return;
        }

        Gift gift = instance.getGiftManager().getGiftByName(args[1]);

        if (gift == null) {
            instance.getMessage("gift-not-exists").send(player);
            return;
        }

        if (getEmptySlots(player) == 0) {
            instance.getMessage("no-space").send(player);
            return;
        }

        player.getInventory().addItem(gift.getItem());
        instance.getMessage("gift-given").send(player);
    }

    private void processReloadPlugin(Player player) {
        instance.reloadModels();
        instance.getMessage("reloaded").send(player);
    }


    private final List<String> INFO_MESSAGE = List.of(
            "&f",
            "   &6&lSimpleGifts &8v1.0",
            " &6Автор: &fer1one",
            " &6Версия игры: &f1.16.5",
            " &6Описание: &fЛёгкий плагин на подарки!"
    );

    private final List<String> HELP_MESSAGE = List.of(
            "&f",
            "   &6&lSimpleGifts &8| &6Команды",
            " &6/gifts give <ПОДАРОК> &8- &fВыдать подарок",
            " &6/gifts reload &8- &fПерезагрузить плагин",
            " &6/gifts info &8- &fПоказать информацию о плагине",
            " &6/gifts help &8- &fПоказать это сообщение"
    );


    private void processInfo(Player player) {
        StringUtils.sendList(player, INFO_MESSAGE);
    }

    private void processHelp(Player player) {
        StringUtils.sendList(player, HELP_MESSAGE);
    }

    public int getEmptySlots(Player p) {
        PlayerInventory inventory = p.getInventory();
        ItemStack[] cont = inventory.getContents();
        int i = 0;
        for (ItemStack item : cont)
            if (item != null && item.getType() != Material.AIR) {
                i++;
            }
        return 36 - i;
    }
}
