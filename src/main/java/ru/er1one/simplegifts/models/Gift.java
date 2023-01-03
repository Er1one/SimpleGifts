package ru.er1one.simplegifts.models;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import ru.er1one.simplegifts.SimpleGifts;
import ru.er1one.simplegifts.utils.StringUtils;

import java.util.List;

public class Gift {

    private final String displayName, headHash, name;

    private final List<String> commands;

    private final Sound sound;

    private final ItemStack item;

    public Gift(String name, String displayName, List<String> commands, Sound sound, String headHash) {
        this.name = name;
        NamespacedKey key = new NamespacedKey(SimpleGifts.getInstance(), "gift");
        this.displayName = displayName;
        this.commands = commands;
        this.sound = sound;
        this.headHash = headHash;

        this.item = new ItemStack(Material.PLAYER_HEAD);

        StringUtils.setSkin(item, headHash);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(StringUtils.color(displayName));
        meta.setLore(StringUtils.color(List.of("&8Можно поставить", "&7", "&dУстановите этот блок")));

        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(key, PersistentDataType.STRING, name);

        item.setItemMeta(meta);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHeadHash() {
        return headHash;
    }

    public List<String> getCommands() {
        return commands;
    }

    public Sound getSound() {
        return sound;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }
}
