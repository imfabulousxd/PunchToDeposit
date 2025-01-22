package me.dexwi.ClickToDeposit.listeners;

import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.gameplay.GameEndEvent;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import me.dexwi.ClickToDeposit.Messages;
import me.dexwi.ClickToDeposit.utils.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.HashSet;

import static me.dexwi.ClickToDeposit.ClickToDeposit.*;

public class GameStateListener implements Listener {
    @EventHandler
    public void onGameStart(GameStateChangeEvent event) {
        if (event.getNewState() != GameState.playing) {
            return;
        }

        IArena arena = event.getArena();
        World world = arena.getWorld();
        gameChestLocations.put(arena, new HashMap<>());
        enderChestLocations.put(arena, new HashSet<>());

        for (ITeam team: arena.getTeams()) {
            Location teamSpawnPoint = team.getSpawn();
            boolean chest=false, enderChest=false;
            for (int x=-7; x<=7; x++) {
                for (int y=-5; y<=5; y++) {
                    for (int z=-7; z<=7; z++) {
                        Block block = world.getBlockAt(teamSpawnPoint.getBlockX()+x, teamSpawnPoint.getBlockY()+y, teamSpawnPoint.getBlockZ()+z);
                        if (!chest && block.getType() == Material.CHEST) {
                            // Display hologram over it for team players
                            gameChestLocations.get(arena).put(team, block.getLocation());

                            Hologram chestHologram = new Hologram(block.getLocation(), Messages.CHEST_HOLOGRAM);
                            for (Player player: team.getMembers()) {
                                chestHologram.displayFor(player);
                            }
                            chestHolograms.put(block.getLocation(), chestHologram);

                            chest = true;
                        } else if (!enderChest && block.getType() == Material.ENDER_CHEST) {
                            // Display hologram over it all players
                            enderChestLocations.get(arena).add(block.getLocation());

                            Hologram enderChestHologram = new Hologram(block.getLocation(), Messages.CHEST_HOLOGRAM);
                            for (Player player: arena.getPlayers()) {
                                enderChestHologram.displayFor(player);
                            }
                            chestHolograms.put(block.getLocation(), enderChestHologram);

                            enderChest = true;
                        }

                        if (chest && enderChest) break;
                    }
                    if (chest && enderChest) break;
                }
                if (chest && enderChest) break;
            }
            if (!chest) log.warning(String.format("Couldn't find a team chest for team %s", team.getName()));
            if (!enderChest) log.warning(String.format("Couldn't find an ender chest for team %s", team.getName()));
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        IArena arena = event.getArena();

        for (Location chestLocation: gameChestLocations.get(arena).values()) {
            deleteHologramFor(chestLocation);
        }
        gameChestLocations.remove(arena);

        for (Location enderChestLocation: enderChestLocations.get(arena)) {
            deleteHologramFor(enderChestLocation);
        }
        enderChestLocations.remove(arena);
    }

    public void deleteHologramFor(Location chestLocation) {
        Hologram chestHologram = chestHolograms.get(chestLocation);
        chestHologram.destroy();
        chestHolograms.remove(chestLocation);
    }
}
