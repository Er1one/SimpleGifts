package ru.er1one.simplegifts.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.er1one.simplegifts.SimpleGifts;
import ru.er1one.simplegifts.managers.DataManager;
import ru.er1one.simplegifts.models.PlacedGift;

public class RemoveBlockListener implements Listener {

    private final SimpleGifts instance = SimpleGifts.getInstance();

    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Location location = event.getBlock().getLocation();

        PlacedGift gift = instance.getGiftManager().getGiftFromLocation(location);

        if (gift == null) {
            return;
        }

        instance.getGiftManager().removeGift(event.getBlock().getLocation());
        DataManager.sync();
    }
}
