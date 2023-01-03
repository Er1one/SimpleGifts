package ru.er1one.simplegifts.models;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlacedGift {
    private final Gift gift;

    private final Location location;

    private final List<String> players;

    public PlacedGift(Gift gift, Location location, List<String> players) {
        this.gift = gift;
        this.location = location;
        this.players = players;
    }

    public PlacedGift(Gift gift, Location location) {
        this.gift = gift;
        this.location = location;
        this.players = new ArrayList<>();
    }

    public Gift getGift() {
        return gift;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player.getName());
    }
}
