package ru.er1one.simplegifts;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.er1one.simplegifts.commands.GiftsCommand;
import ru.er1one.simplegifts.commands.GiftsCommandExecutor;
import ru.er1one.simplegifts.events.ClickBlockListener;
import ru.er1one.simplegifts.events.PlaceBlockListener;
import ru.er1one.simplegifts.events.RemoveBlockListener;
import ru.er1one.simplegifts.managers.GiftManager;
import ru.er1one.simplegifts.models.ConfigMessage;
import ru.er1one.simplegifts.utils.Configurations;

import java.util.HashMap;
import java.util.Map;

public final class SimpleGifts extends JavaPlugin {

    private static SimpleGifts instance;

    private Configurations configurations;

    private GiftManager giftManager;

    private final Map<String, ConfigMessage> messages = new HashMap<>();

    @Override
    public void onEnable() {

        instance = this;

        getCommand("gifts").setExecutor(new GiftsCommand());
        getCommand("gifts").setTabCompleter(new GiftsCommandExecutor());

        configurations = new Configurations(this, "config.yml", "data.yml");
        configurations.loadConfigurations();

        loadMessages();

        giftManager = new GiftManager(getConfig().getConfigurationSection("gifts"),
                                      getData().getConfigurationSection("gifts"));

        getServer().getPluginManager().registerEvents(new ClickBlockListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceBlockListener(), this);
        getServer().getPluginManager().registerEvents(new RemoveBlockListener(), this);

    }

    private void loadMessages() {
        messages.clear();
        for (String key : getConfig().getConfigurationSection("messages").getKeys(false)) {
            messages.put(key, new ConfigMessage(getConfig().getString("messages." + key)));
        }
    }

    public ConfigMessage getMessage(String key) {
        if(messages.containsKey(key)) {
            return messages.get(key).clone();
        } else {
            return ConfigMessage.getEmptyMessage();
        }
    }

    public void reloadModels() {
        saveData();
        configurations.reloadConfigurations();
        giftManager = new GiftManager(getConfig().getConfigurationSection("gifts"),
                getData().getConfigurationSection("gifts"));
        loadMessages();
    }

    public GiftManager getGiftManager() {
        return giftManager;
    }

    @Override
    public FileConfiguration getConfig() {
        return configurations.get("config.yml");
    }

    public FileConfiguration getData() {
        return configurations.get("data.yml");
    }

    public static SimpleGifts getInstance() {
        return instance;
    }

    public void saveData() {
        configurations.save("data.yml");
    }
}
