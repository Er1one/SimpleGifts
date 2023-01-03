package ru.er1one.simplegifts.managers;


import ru.er1one.simplegifts.SimpleGifts;
import ru.er1one.simplegifts.models.PlacedGift;
import ru.er1one.simplegifts.utils.StringUtils;

public class DataManager {

    private static final SimpleGifts instance = SimpleGifts.getInstance();
    private static final GiftManager giftManager = instance.getGiftManager();

    public static void sync() {
        if (giftManager.getPlacedGifts().isEmpty()) {
            instance.getData().set("gifts", null);
        } else {
            instance.getData().set("gifts", null);
            for (PlacedGift gift : giftManager.getPlacedGifts().values()) {

                String key = "gifts." + StringUtils.getStringFromLocation(gift.getLocation());

                instance.getData().set(key + ".gift", gift.getGift().getName());
                instance.getData().set(key + ".players", gift.getPlayers());
            }
        }
        instance.saveData();
    }
}
