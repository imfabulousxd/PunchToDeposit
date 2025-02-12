package me.dexwi.ClickToDeposit.utils;

import org.bukkit.Location;

public class Blocks {
    public static boolean locationEquals(Location l1, Location l2) {
        return (
                l1.getX() == l2.getX() &&
                        l1.getY() == l2.getY() &&
                        l1.getZ() == l2.getZ()
        );
    }
}