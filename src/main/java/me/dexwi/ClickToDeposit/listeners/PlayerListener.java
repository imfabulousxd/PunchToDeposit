package me.dexwi.ClickToDeposit.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import me.dexwi.ClickToDeposit.utils.Bedwars;
import me.dexwi.ClickToDeposit.utils.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

import static me.dexwi.ClickToDeposit.ClickToDeposit.*;

public class PlayerListener implements Listener {
    @EventHandler
    public void playerRespawn(PlayerReSpawnEvent event) {
        Player player = event.getPlayer();
        IArena arena = event.getArena();
        ITeam team = event.getArena().getExTeam(player.getUniqueId());

        for (Map.Entry<ITeam, Location> entry: gameChestLocations.get(arena).entrySet()) {
            ITeam entryTeam = entry.getKey();
            if (entryTeam.equals(team) || Bedwars.isEliminated(entryTeam)) {
                Hologram chestHologram = chestHolograms.get(entry.getValue());
                chestHologram.displayFor(player);
            }
        }

        for (Location enderChestLocation: enderChestLocations.get(arena)) {
            Hologram enderChestHologram = chestHolograms.get(enderChestLocation);
            enderChestHologram.displayFor(player);
        }
    }

    @EventHandler
    public void playerEliminated(PlayerKillEvent event) {
        Player victim = event.getVictim();
        IArena arena = event.getArena();
        ITeam victimTeam = arena.getTeam(event.getVictim());

        if (!victimTeam.isBedDestroyed()) {
            return;
        }

        for (ITeam team: arena.getTeams()) {
            if (Bedwars.isEliminated(team)) {
                Hologram chestHologram = chestHolograms.get(gameChestLocations.get(arena).get(team));
                chestHologram.destroyFor(victim);
            }
        }

        for (Location enderChestLocation: enderChestLocations.get(arena)) {
            Hologram enderChestHologram = chestHolograms.get(enderChestLocation);
            enderChestHologram.destroyFor(victim);
        }
    }
}
