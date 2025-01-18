package me.dexwi.ClickToDeposit;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.dexwi.ClickToDeposit.listeners.BlockStateListener;
import me.dexwi.ClickToDeposit.listeners.GameStateListener;
import me.dexwi.ClickToDeposit.listeners.PlayerListener;
import me.dexwi.ClickToDeposit.listeners.TeamListener;
import me.dexwi.ClickToDeposit.utils.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class ClickToDeposit extends JavaPlugin {
    private static ClickToDeposit instance;
    public static BedWars bedwars;
    public static Logger log;
    public static HashMap<IArena, HashMap<ITeam, Location>> gameChestLocations = new HashMap<>();
    public static HashMap<IArena, Set<Location>> enderChestLocations = new HashMap<>();
    public static HashMap<Location, Hologram> chestHolograms = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
        log = getLogger();
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        bedwars = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();

        Messages.setupMessages();

        getServer().getPluginManager().registerEvents(new BlockStateListener(), this);
        getServer().getPluginManager().registerEvents(new GameStateListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new TeamListener(), this);

        getLogger().info("ClickToDeposit Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ClickToDeposit Disabled!");
    }

    @SuppressWarnings("unused")
    public static ClickToDeposit getInstance() {
        return instance;
    }
}
