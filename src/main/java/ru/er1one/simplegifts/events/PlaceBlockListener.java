package ru.er1one.simplegifts.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import ru.er1one.simplegifts.SimpleGifts;
import ru.er1one.simplegifts.managers.DataManager;
import ru.er1one.simplegifts.models.Gift;

public class PlaceBlockListener implements Listener {

    private final SimpleGifts instance = SimpleGifts.getInstance();

    NamespacedKey key = new NamespacedKey(SimpleGifts.getInstance(), "gift");

    @EventHandler
    private void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        if (!dataContainer.has(key, PersistentDataType.STRING)) {
            return;
        }

        String name = dataContainer.get(key, PersistentDataType.STRING);

        Gift gift = instance.getGiftManager().getGiftByName(name);

        instance.getGiftManager().addGift(event.getBlock().getLocation(), gift);
        DataManager.sync();
    }
}
