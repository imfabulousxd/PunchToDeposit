package me.dexwi.ClickToDeposit.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReJoinEvent;
import me.dexwi.ClickToDeposit.utils.Bedwars;
import me.dexwi.ClickToDeposit.utils.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.dexwi.ClickToDeposit.ClickToDeposit.*;

public class PlayerListener implements Listener {
    @EventHandler
    public void playerRejoined(PlayerReJoinEvent event) {
        log.info(String.format("Player %s re-joined!", event.getPlayer().getName()));
        Player player = event.getPlayer();
        IArena arena = event.getArena();
        ITeam team = event.getArena().getExTeam(player.getUniqueId());

        if (team == null || Bedwars.isEliminated(team)) {
            return;
        }

        Hologram chestHologram = chestHolograms.get(gameChestLocations.get(arena).get(team));
        chestHologram.displayFor(player);

        for (Location enderChestLocation: enderChestLocations.get(arena)) {
            Hologram enderChestHologram = chestHolograms.get(enderChestLocation);
            enderChestHologram.displayFor(player);
        }
    }

    @EventHandler
    public void playerEliminated(PlayerKillEvent event) {
        Player victim = event.getVictim();
        ITeam victimTeam = event.getVictimTeam();

        if (!victimTeam.isBedDestroyed()) {
            return;
        }
        log.info(String.format("Player %s Eliminated!", victim.getName()));

        IArena arena = event.getArena();
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
