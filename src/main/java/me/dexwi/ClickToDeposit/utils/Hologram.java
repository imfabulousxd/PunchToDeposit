package me.dexwi.ClickToDeposit.utils;

import com.andrei1058.bedwars.api.language.Language;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class Hologram implements Listener {
    private final CraftArmorStand armorStand1;
    private final CraftArmorStand armorStand2;
    private final Set<Player> displayingFor = new HashSet<>();
    private final String path1;
    private final String path2;

    public Hologram(Location location, String path1, String path2) {
        this.path1 = path1;
        this.path2 = path2;

        EntityArmorStand entityArmorStand1 = new EntityArmorStand(
                ((CraftWorld) location.getWorld()).getHandle()
        );
        entityArmorStand1.setLocation(
                location.getX() + 0.5,
                location.getY() + 1.2,
                location.getZ() + 0.5, 0, 0
        );
        armorStand1 = new CraftArmorStand(((CraftWorld) location.getWorld()).getHandle().getServer(),
                entityArmorStand1);
        armorStand1.setGravity(false);
        armorStand1.setRemoveWhenFarAway(false);
        armorStand1.setVisible(false);
        armorStand1.setCanPickupItems(false);
        armorStand1.setArms(false);
        armorStand1.setBasePlate(false);
        armorStand1.setCustomNameVisible(true);
        armorStand1.setMarker(true);

        EntityArmorStand entityArmorStand2 = new EntityArmorStand(
                ((CraftWorld) location.getWorld()).getHandle()
        );
        entityArmorStand2.setLocation(
                location.getX() + 0.5,
                location.getY() + 0.85,
                location.getZ() + 0.5, 0, 0
        );
        armorStand2 = new CraftArmorStand(((CraftWorld) location.getWorld()).getHandle().getServer(),
                entityArmorStand2);
        armorStand2.setGravity(false);
        armorStand2.setRemoveWhenFarAway(false);
        armorStand2.setVisible(false);
        armorStand2.setCanPickupItems(false);
        armorStand2.setArms(false);
        armorStand2.setBasePlate(false);
        armorStand2.setCustomNameVisible(true);
        armorStand2.setMarker(true);
    }

    public void displayFor(Player player) {
        armorStand1.setCustomName(
                Language.getMsg(player, path1)
        );
        PacketPlayOutSpawnEntityLiving packet1 = new PacketPlayOutSpawnEntityLiving(armorStand1.getHandle());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
        displayingFor.add(player);

        armorStand2.setCustomName(
                Language.getMsg(player, path2)
        );
        PacketPlayOutSpawnEntityLiving packet2 = new PacketPlayOutSpawnEntityLiving(armorStand2.getHandle());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
        displayingFor.add(player);
    }

    public void destroyFor(Player player) {
        boolean removed = displayingFor.remove(player);
        if (removed) {
            PacketPlayOutEntityDestroy packet1 = new PacketPlayOutEntityDestroy(armorStand1.getHandle().getId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);

            PacketPlayOutEntityDestroy packet2 = new PacketPlayOutEntityDestroy(armorStand2.getHandle().getId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
        }
    }

    private void sendHologramDeletionPacket(Player player) {
        PacketPlayOutEntityDestroy packet1 = new PacketPlayOutEntityDestroy(armorStand1.getHandle().getId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);

        PacketPlayOutEntityDestroy packet2 = new PacketPlayOutEntityDestroy(armorStand2.getHandle().getId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
    }

    public void destroy() {
        for (Player player: displayingFor) {
            sendHologramDeletionPacket(player);
        }
        displayingFor.clear();
    }
}