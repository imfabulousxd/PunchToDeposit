package me.dexwi.ClickToDeposit.utils;

import com.tomkeuper.bedwars.api.arena.team.ITeam;

public class Bedwars {
    public static boolean isEliminated(ITeam team) {
        return team.isBedDestroyed() && team.getMembers().isEmpty();
    }
}
