package me.dexwi.ClickToDeposit;

import com.andrei1058.bedwars.api.BedWars;
import me.dexwi.ClickToDeposit.Listener.BlockBreakingListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ClickToDeposit extends JavaPlugin {
    private static ClickToDeposit instance;
    public static BedWars bedwars;
    public static Logger log;

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

        getServer().getPluginManager().registerEvents(new BlockBreakingListener(), this);

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
