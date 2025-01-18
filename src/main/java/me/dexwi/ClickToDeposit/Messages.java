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

    public static final String ITEM_NAMES_PATH = PATH + "items.";
    public static final String ITEM_NAME_IRON_INGOT = ITEM_NAMES_PATH + "iron-ingot";
    public static final String ITEM_NAME_GOLD_INGOT = ITEM_NAMES_PATH + "gold-ingot";
    public static final String ITEM_NAME_DIAMOND = ITEM_NAMES_PATH + "diamond";
    public static final String ITEM_NAME_EMERALD = ITEM_NAMES_PATH + "emerald";

    public static void setupMessages() {
        for (Language l: Language.getLanguages()) {
            addDefault(l, DEPOSIT_SUCCESS, "&7You deposited x{item_amount} &r{item_name}&r&7 into your &r{chest_type}&r&7!");
            addDefault(l, DEPOSIT_FAILURE, "&7Couldn't deposit x{item_amount} &r{item_name}&r&7 into your &r{chest_type}&r&7!");
            addDefault(l, CHEST_NAME, ChatColor.AQUA + "Team Chest");
            addDefault(l, ENDER_CHEST_NAME, ChatColor.LIGHT_PURPLE + "Ender Chest");
            addDefault(l, CHEST_HOLOGRAM, "&lPunch\nto deposit!");
            addDefault(l, ITEM_NAME_IRON_INGOT, "&r&fIron Ingot");
            addDefault(l, ITEM_NAME_GOLD_INGOT, "&r&6Gold Ingot");
            addDefault(l, ITEM_NAME_DIAMOND, "&r&bDiamond");
            addDefault(l, ITEM_NAME_EMERALD, "&r&aEmerald");
        }
    }

    private static void addDefault(Language l, String path, Object english) {
        if (!l.exists(path)) {
            l.set(path, english);
        }
    }
}
