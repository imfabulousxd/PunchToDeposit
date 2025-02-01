package me.dexwi.ClickToDeposit;

import com.andrei1058.bedwars.api.language.Language;

public class Messages {
    public static final String PATH = "addons.click-to-deposit.";
    public static final String DEPOSIT_SUCCESS_TEAM_CHEST = PATH + "deposit-success-team-chest";
    public static final String DEPOSIT_SUCCESS_OTHER_TEAM_CHEST = PATH + "deposit-success-other-team-chest";
    public static final String DEPOSIT_SUCCESS_ENDER_CHEST = PATH + "deposit-success-ender-chest";
    public static final String DEPOSIT_FAILURE_CHEST_FULL = PATH + "deposit-failure-chest-full";
    public static final String DEPOSIT_FAILURE_MINIMUM_ITEM = PATH + "deposit-failure-minimum-item";
    public static final String DEPOSIT_FAILURE_NOT_ELIMINATED_TEAM_CHEST = PATH + "deposit-failure-not-eliminated-team-chest";
    public static final String CHEST_HOLOGRAM_1 = PATH + "chest-hologram-1";
    public static final String CHEST_HOLOGRAM_2 = PATH + "chest-hologram-2";

    public static final String ITEM_NAMES_PATH = PATH + "items.";
    public static final String ITEM_NAME_IRON_INGOT = ITEM_NAMES_PATH + "iron-ingot";
    public static final String ITEM_NAME_GOLD_INGOT = ITEM_NAMES_PATH + "gold-ingot";
    public static final String ITEM_NAME_DIAMOND = ITEM_NAMES_PATH + "diamond";
    public static final String ITEM_NAME_EMERALD = ITEM_NAMES_PATH + "emerald";

    public static final String ITEM_NAME_WOOL = ITEM_NAMES_PATH + "wool";
    public static final String ITEM_NAME_CLAY = ITEM_NAMES_PATH + "clay";
    public static final String ITEM_NAME_GLASS = ITEM_NAMES_PATH + "glass";
    public static final String ITEM_NAME_ENDSTONE = ITEM_NAMES_PATH + "endstone";
    public static final String ITEM_NAME_LADDER = ITEM_NAMES_PATH + "ladder";
    public static final String ITEM_NAME_WOOD = ITEM_NAMES_PATH + "wood";
    public static final String ITEM_NAME_OBSIDIAN = ITEM_NAMES_PATH + "obsidian";

    public static final String ITEM_NAME_POTION = ITEM_NAMES_PATH + "potion";

    public static final String ITEM_NAME_GOLDEN_APPLE = ITEM_NAMES_PATH + "golden-apple";
    public static final String ITEM_NAME_SILVERFISH = ITEM_NAMES_PATH + "silverfish";
    public static final String ITEM_NAME_IRON_GOLEM = ITEM_NAMES_PATH + "iron-golem";
    public static final String ITEM_NAME_FIREBALL = ITEM_NAMES_PATH + "fireball";
    public static final String ITEM_NAME_TNT = ITEM_NAMES_PATH + "tnt";
    public static final String ITEM_NAME_PEARL = ITEM_NAMES_PATH + "pearl";
    public static final String ITEM_NAME_WATER_BUCKET = ITEM_NAMES_PATH + "water-bucket";
    public static final String ITEM_NAME_BRIDGE_EGG = ITEM_NAMES_PATH + "bridge-egg";
    public static final String ITEM_NAME_MILK_BUCKET = ITEM_NAMES_PATH + "milk-bucket";
    public static final String ITEM_NAME_SPONGE = ITEM_NAMES_PATH + "sponge";
    public static final String ITEM_NAME_POPUP_TOWER = ITEM_NAMES_PATH + "popup-tower";

    public static void setupMessages() {
        for (Language l: Language.getLanguages()) {
            addDefault(l, DEPOSIT_SUCCESS_TEAM_CHEST, "&7You deposited x{item_amount} &r{item_name}&r&7 into your &bTeam chest!");
            addDefault(l, DEPOSIT_SUCCESS_OTHER_TEAM_CHEST, "&7You deposited x{item_amount} &r{item_name}&r&7 into {team_color}{team_name} team&r&7's chest!");
            addDefault(l, DEPOSIT_SUCCESS_ENDER_CHEST, "&7You deposited x{item_amount} &r{item_name}&r&7 into your &dEnder chest!");
            addDefault(l, DEPOSIT_FAILURE_CHEST_FULL, "&7There's not enough space to deposit x{item_amount} &r{item_name}&r&7 into this chest!");
            addDefault(l, DEPOSIT_FAILURE_MINIMUM_ITEM, "&7You need at least {item_minimum_amount} of this type to deposit.");
            addDefault(l, DEPOSIT_FAILURE_NOT_ELIMINATED_TEAM_CHEST, "&cYou cannot deposit into this chest as {team_color}{team_name} Team&r&c has not been eliminated!");

            addDefault(l, CHEST_HOLOGRAM_1, "&7PUNCH TO");
            addDefault(l, CHEST_HOLOGRAM_2, "&7DEPOSIT");

            addDefault(l, ITEM_NAME_IRON_INGOT, "&r&fIron Ingot");
            addDefault(l, ITEM_NAME_GOLD_INGOT, "&r&6Gold Ingot");
            addDefault(l, ITEM_NAME_DIAMOND, "&r&bDiamond");
            addDefault(l, ITEM_NAME_EMERALD, "&r&aEmerald");

            addDefault(l, ITEM_NAME_WOOL, "&r&fWool");
            addDefault(l, ITEM_NAME_CLAY, "&r&fClay");
            addDefault(l, ITEM_NAME_GLASS, "&r&fGlass");
            addDefault(l, ITEM_NAME_ENDSTONE, "&r&fEnd Stone");
            addDefault(l, ITEM_NAME_LADDER, "&r&eLadder");
            addDefault(l, ITEM_NAME_WOOD, "&r&eWood");
            addDefault(l, ITEM_NAME_OBSIDIAN, "&r&0Obsidian");

            addDefault(l, ITEM_NAME_POTION, "&r&5Potion");

            addDefault(l, ITEM_NAME_GOLDEN_APPLE, "&r&6Golden Apple");
            addDefault(l, ITEM_NAME_SILVERFISH, "&r&8Silverfish");
            addDefault(l, ITEM_NAME_IRON_GOLEM, "&r&fIron Golem");
            addDefault(l, ITEM_NAME_FIREBALL, "&r&6Fireball");
            addDefault(l, ITEM_NAME_TNT, "&r&cT&fN&cT");
            addDefault(l, ITEM_NAME_PEARL, "&r&2Ender Pearl");
            addDefault(l, ITEM_NAME_WATER_BUCKET, "&r&9Water Bucket");
            addDefault(l, ITEM_NAME_BRIDGE_EGG, "&r&eBridge Egg");
            addDefault(l, ITEM_NAME_MILK_BUCKET, "&r&fMilk Bucket");
            addDefault(l, ITEM_NAME_SPONGE, "&r&eSponge");
            addDefault(l, ITEM_NAME_POPUP_TOWER, "&r&ePopup Tower");
        }
    }

    private static void addDefault(Language l, String path, Object english) {
        if (!l.exists(path)) {
            l.set(path, english);
        }
    }
}
