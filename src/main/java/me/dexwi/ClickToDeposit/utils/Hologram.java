package me.dexwi.ClickToDeposit.utils;

import com.andrei1058.bedwars.api.language.Language;
import me.dexwi.ClickToDeposit.ClickToDeposit;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.HashSet;
import java.util.Set;

public class Hologram implements Listener {
//    private final EntityArmorStand armorStand;
    private final CraftArmorStand armorStand;
    private final Set<Player> displayingFor = new HashSet<>();
    private final String path;

    public Hologram(Location location, String messagePath) {
        path = messagePath;
        EntityArmorStand entityArmorStand = new EntityArmorStand(
                ((CraftWorld) location.getWorld()).getHandle()
        );
        entityArmorStand.setLocation(location.getX() + 0.5,
                location.getY() + 1,
                location.getZ() + 0.5, 0, 0);
        armorStand = new CraftArmorStand(((CraftWorld) location.getWorld()).getHandle().getServer(),
                entityArmorStand);
        armorStand.setGravity(false);
        armorStand.setRemoveWhenFarAway(false);
        armorStand.setVisible(false);
        armorStand.setCanPickupItems(false);
        armorStand.setArms(false);
        armorStand.setBasePlate(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setMarker(true);

        Bukkit.getPluginManager().registerEvents(this, ClickToDeposit.getInstance());
    }

    public void displayFor(Player player) {
        armorStand.setCustomName(
                Language.getMsg(player, path)
        );
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armorStand.getHandle());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        displayingFor.add(player);
    }

    public void destroyFor(Player player) {
        boolean removed = displayingFor.remove(player);
        if (removed) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armorStand.getHandle().getId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    private void sendHologramDeletionPacket(Player player) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armorStand.getHandle().getId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
        for (Player player: displayingFor) {
            sendHologramDeletionPacket(player);
        }
        displayingFor.clear();
    }

    @EventHandler
    public void playerLeftWorld(PlayerChangedWorldEvent event) {
        if (event.getFrom() != armorStand.getHandle().world) {
            return;
        }
        displayingFor.remove(event.getPlayer());
    }
}
