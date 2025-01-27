package me.dexwi.ClickToDeposit.utils;

import com.tomkeuper.bedwars.api.language.Language;
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
    private final CraftArmorStand armorStandLine1;
    private final CraftArmorStand armorStandLine2;
    private final Set<Player> displayingFor = new HashSet<>();
    private final String path1;
    private final String path2;

    public Hologram(Location location, String messagePath1, String messagePath2) {
        path1 = messagePath1;
        path2 = messagePath2;

        EntityArmorStand entityArmorStand1 = new EntityArmorStand(
                ((CraftWorld) location.getWorld()).getHandle()
        );
        entityArmorStand1.setLocation(location.getX() + 0.5,
                location.getY() + 1.2,
                location.getZ() + 0.5, 0, 0);
        armorStandLine1 = new CraftArmorStand(((CraftWorld) location.getWorld()).getHandle().getServer(),
                entityArmorStand1);
        setupArmorStand(armorStandLine1);
        // line 2
        EntityArmorStand entityArmorStand2 = new EntityArmorStand(
                ((CraftWorld) location.getWorld()).getHandle()
        );
        entityArmorStand2.setLocation(location.getX() + 0.5,
                location.getY() + 0.85,
                location.getZ() + 0.5, 0, 0);
        armorStandLine2 = new CraftArmorStand(((CraftWorld) location.getWorld()).getHandle().getServer(),
                entityArmorStand2);
        setupArmorStand(armorStandLine2);
    }

    private void setupArmorStand(CraftArmorStand armorStand) {
        armorStand.setGravity(false);
        armorStand.setRemoveWhenFarAway(false);
        armorStand.setVisible(false);
        armorStand.setCanPickupItems(false);
        armorStand.setArms(false);
        armorStand.setBasePlate(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setMarker(true);
    }

    public void displayFor(Player player) {
        armorStandLine1.setCustomName(Language.getMsg(player, path1));
        armorStandLine2.setCustomName(Language.getMsg(player, path2));

        PacketPlayOutSpawnEntityLiving packet1 = new PacketPlayOutSpawnEntityLiving(armorStandLine1.getHandle());
        PacketPlayOutSpawnEntityLiving packet2 = new PacketPlayOutSpawnEntityLiving(armorStandLine2.getHandle());

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);

        displayingFor.add(player);
    }

    public void destroyFor(Player player) {
        boolean removed = displayingFor.remove(player);
        if (removed) {
            PacketPlayOutEntityDestroy packet1 = new PacketPlayOutEntityDestroy(armorStandLine1.getHandle().getId());
            PacketPlayOutEntityDestroy packet2 = new PacketPlayOutEntityDestroy(armorStandLine2.getHandle().getId());

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
        }
    }

    public void destroy() {
        for (Player player : displayingFor) {
            destroyFor(player);
        }
        displayingFor.clear();
    }
}

