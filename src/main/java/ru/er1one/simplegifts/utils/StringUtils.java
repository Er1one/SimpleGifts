package ru.er1one.simplegifts.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String LOCATION_FORMAT = "%d~%d~%d~%s";

    public static String color(String string) {
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}"); // #ffffff - &f
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            String color = string.substring(matcher.start() + 1, matcher.end());
            string = string.replace("&" + color, ChatColor.of(color) + "");
            matcher = pattern.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> list) {
        List<String> result = new ArrayList<>();
        list.forEach(key -> result.add(color(key)));
        return result;
    }

    public static void sendList(Player player, List<String> list) {
        list.forEach(message -> player.sendMessage(color(message)));
    }

    public static ItemStack setSkin(ItemStack itemStack, String value) {
        if (itemStack.getItemMeta() instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
            gameProfile.getProperties().put("textures", new Property("textures", value));
            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, gameProfile);
            } catch (ReflectiveOperationException exception) {
                exception.printStackTrace();
            }
            itemStack.setItemMeta(skullMeta);
            return itemStack;
        }
        return itemStack;
    }

    public static String getStringFromLocation(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String world = location.getWorld().getName();

        return String.format(LOCATION_FORMAT, x, y, z, world);
    }

    public static Location getLocationFromString(String str) {
        String[] parts = str.split("~");

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);

        World world = Bukkit.getWorld(parts[3]);

        return new Location(world, x, y, z);
    }
}
