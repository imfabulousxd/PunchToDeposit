package me.dexwi.ClickToDeposit.listeners;

import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.team.TeamEliminatedEvent;
import me.dexwi.ClickToDeposit.utils.Hologram;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.dexwi.ClickToDeposit.ClickToDeposit.*;

public class TeamListener implements Listener {
    @EventHandler
    public void teamEliminated(TeamEliminatedEvent event) {

        IArena arena = event.getArena();
        ITeam eliminatedTeam = event.getTeam();
        Hologram eliminatedTeamsChestHologram = chestHolograms.get(gameChestLocations.get(arena).get(eliminatedTeam));

        for (ITeam team: arena.getTeams()) {
            if (team == eliminatedTeam || (team.isBedDestroyed() && team.getMembers().isEmpty())) continue;
            for (Player player: team.getMembers()) {
                eliminatedTeamsChestHologram.displayFor(player);
            }
        }
    }
}
