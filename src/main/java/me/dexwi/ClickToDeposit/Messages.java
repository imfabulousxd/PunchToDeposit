package me.dexwi.ClickToDeposit;

import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.ChatColor;

public class Messages {
    public static final String PATH = "addons.click-to-deposit.";
    public static final String DEPOSIT_SUCCESS = PATH + "deposit-success";
    public static final String DEPOSIT_FAILURE = PATH + "deposit-failure";
    public static final String CHEST_NAME = PATH + "chest-name";
    public static final String ENDER_CHEST_NAME = PATH + "ender-chest-name";
    public static final String CHEST_HOLOGRAM = PATH + "chest-hologram";
    public static final String ENDER_CHEST_HOLOGRAM = PATH + "ender-chest-hologram";

    public static void setupMessages() {
        for (Language l: Language.getLanguages()) {
            addDefault(l, DEPOSIT_SUCCESS, "&7You deposited x{item_amount} &r{item_name}&r&7 into your &r{chest_type}&r&7!");
            addDefault(l, DEPOSIT_FAILURE, "&7Couldn't deposit x{item_amount} &r{item_name}&r&7 into your &r{chest_type}&r&7!");
            addDefault(l, CHEST_NAME, ChatColor.AQUA + "Team Chest");
            addDefault(l, ENDER_CHEST_NAME, ChatColor.LIGHT_PURPLE + "Ender Chest");
            addDefault(l, CHEST_HOLOGRAM, "Punch to deposit!");
            addDefault(l, ENDER_CHEST_HOLOGRAM, "Punch to deposit!");
        }
    }

    private static void addDefault(Language l, String path, Object english) {
        if (!l.exists(path)) {
            l.set(path, english);
        }
    }
}
