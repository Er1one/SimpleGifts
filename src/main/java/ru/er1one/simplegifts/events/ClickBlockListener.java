package ru.er1one.simplegifts.events;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import ru.er1one.simplegifts.SimpleGifts;
import ru.er1one.simplegifts.managers.DataManager;
import ru.er1one.simplegifts.models.PlacedGift;

public class ClickBlockListener implements Listener {

    private final SimpleGifts instance = SimpleGifts.getInstance();

    NamespacedKey key = new NamespacedKey(SimpleGifts.getInstance(), "gift");

    @EventHandler
    private void onBlockClick(PlayerInteractEvent event) {
        if (!event.hasBlock()) {
            return;
        }

        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();

        Block block = event.getClickedBlock();

        if (instance.getGiftManager().getGiftFromLocation(block.getLocation()) == null) {
            return;
        }

        PlacedGift gift = instance.getGiftManager().getGiftFromLocation(block.getLocation());

        if (!gift.getPlayers().contains(player.getName())) {
            instance.getMessage("taken").replace("%gift%", gift.getGift().getDisplayName()).send(player);
            for (String command : gift.getGift().getCommands()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        command.replace("%player%", player.getName()));
            }
            player.playSound(player.getLocation(), gift.getGift().getSound(), 1f, 0.7f);
            instance.getGiftManager().addPlayer(block.getLocation(), player);
            DataManager.sync();
        } else {
            instance.getMessage("already-taken").send(player);
        }

    }

}
