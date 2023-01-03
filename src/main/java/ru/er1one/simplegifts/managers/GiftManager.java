package ru.er1one.simplegifts.managers;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.er1one.simplegifts.models.Gift;
import ru.er1one.simplegifts.models.PlacedGift;
import ru.er1one.simplegifts.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GiftManager {

    private final Map<Location, PlacedGift> placedGifts = new HashMap<>();

    private final Map<String, Gift> gifts = new HashMap<>();

    public GiftManager(ConfigurationSection config, ConfigurationSection data) {
        if (config == null) return;
        config.getKeys(false).forEach(key -> {
            gifts.put(key, new Gift(key,
                                    config.getString(key + ".name"),
                                    config.getStringList(key + ".commands"),
                                    Sound.valueOf(config.getString(key + ".sound")),
                                    config.getString(key + ".hash")));
        });

        if (data == null) return;
        data.getKeys(false).forEach(key -> {
            placedGifts.put(StringUtils.getLocationFromString(key),
                    new PlacedGift(getGiftByName(data.getString(key + ".gift")),
                                   StringUtils.getLocationFromString(key),
                                   data.getStringList(key + ".players"))
                    );
        });
    }

    public void addGift(Location location, Gift gift) {
        placedGifts.put(location, new PlacedGift(gift, location));
    }

    public PlacedGift removeGift(Location location) {
        return placedGifts.remove(location);
    }

    public PlacedGift getGiftFromLocation(Location location) {
        return placedGifts.get(location);
    }

    public Gift getGiftByName(String giftName) {
        return gifts.get(giftName);
    }

    public Map<Location, PlacedGift> getPlacedGifts() {
        return placedGifts;
    }

    public void addPlayer(Location location, Player player) {
        PlacedGift placedGift = getGiftFromLocation(location);
        placedGift.addPlayer(player);
        placedGifts.put(location, placedGift);
    }

    public Set<String> getGifts() {
        return gifts.keySet();
    }
}
