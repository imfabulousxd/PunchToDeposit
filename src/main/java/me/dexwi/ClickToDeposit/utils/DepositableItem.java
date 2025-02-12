package me.dexwi.ClickToDeposit.utils;

import com.andrei1058.bedwars.api.language.Language;
import me.dexwi.ClickToDeposit.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DepositableItem {
    public final String displayNamePath;
    public final int minimumAmount;

    public static final HashMap<Material, DepositableItem> items = new HashMap<>();
    static {
        items.put(Material.IRON_INGOT, new DepositableItem(Messages.ITEM_NAME_IRON_INGOT, 1));
        items.put(Material.GOLD_INGOT, new DepositableItem(Messages.ITEM_NAME_GOLD_INGOT, 1));
        items.put(Material.DIAMOND, new DepositableItem(Messages.ITEM_NAME_DIAMOND, 1));
        items.put(Material.EMERALD, new DepositableItem(Messages.ITEM_NAME_EMERALD, 1));

        items.put(Material.WOOL, new DepositableItem(Messages.ITEM_NAME_WOOL, 1));
        items.put(Material.STAINED_CLAY, new DepositableItem(Messages.ITEM_NAME_CLAY, 1));
        items.put(Material.STAINED_GLASS, new DepositableItem(Messages.ITEM_NAME_GLASS, 1));
        items.put(Material.ENDER_STONE, new DepositableItem(Messages.ITEM_NAME_ENDSTONE, 1));
        items.put(Material.LADDER, new DepositableItem(Messages.ITEM_NAME_LADDER, 1));
        items.put(Material.WOOD, new DepositableItem(Messages.ITEM_NAME_WOOD, 1));
        items.put(Material.OBSIDIAN, new DepositableItem(Messages.ITEM_NAME_OBSIDIAN, 1));

        items.put(Material.WOOD_SWORD, new DepositableItem(null, 2));
        items.put(Material.STONE_SWORD, new DepositableItem(null, 2));
        items.put(Material.IRON_SWORD, new DepositableItem(null, 2));
        items.put(Material.DIAMOND_SWORD, new DepositableItem(null, 2));
        items.put(Material.STICK, new DepositableItem(null, 2));

        items.put(Material.SHEARS, new DepositableItem(null, 2));
        items.put(Material.WOOD_PICKAXE, new DepositableItem(null, 2));
        items.put(Material.GOLD_PICKAXE, new DepositableItem(null, 2));
        items.put(Material.STONE_PICKAXE, new DepositableItem(null, 2));
        items.put(Material.IRON_PICKAXE, new DepositableItem(null, 2));
        items.put(Material.DIAMOND_PICKAXE, new DepositableItem(null, 2));
        items.put(Material.WOOD_AXE, new DepositableItem(null, 2));
        items.put(Material.GOLD_AXE, new DepositableItem(null, 2));
        items.put(Material.STONE_AXE, new DepositableItem(null, 2));
        items.put(Material.IRON_AXE, new DepositableItem(null, 2));
        items.put(Material.DIAMOND_AXE, new DepositableItem(null, 2));

        items.put(Material.POTION, new DepositableItem(Messages.ITEM_NAME_POTION, 1));

        items.put(Material.GOLDEN_APPLE, new DepositableItem(Messages.ITEM_NAME_GOLDEN_APPLE, 1));
        items.put(Material.SNOW_BALL, new DepositableItem(Messages.ITEM_NAME_SILVERFISH, 1));
        items.put(Material.MONSTER_EGG, new DepositableItem(Messages.ITEM_NAME_IRON_GOLEM, 1));  // TODO: CHECK IF THIS WORKS
        items.put(Material.FIREBALL, new DepositableItem(Messages.ITEM_NAME_FIREBALL, 1));  // TODO: CHECK IF THIS WORKS
        items.put(Material.TNT, new DepositableItem(Messages.ITEM_NAME_TNT, 1));
        items.put(Material.ENDER_PEARL, new DepositableItem(Messages.ITEM_NAME_PEARL, 1));
        items.put(Material.WATER_BUCKET, new DepositableItem(Messages.ITEM_NAME_WATER_BUCKET, 1));
        items.put(Material.EGG, new DepositableItem(Messages.ITEM_NAME_BRIDGE_EGG, 1));
        items.put(Material.MILK_BUCKET, new DepositableItem(Messages.ITEM_NAME_MILK_BUCKET, 1));
        items.put(Material.SPONGE, new DepositableItem(Messages.ITEM_NAME_SPONGE, 1));
        items.put(Material.CHEST, new DepositableItem(Messages.ITEM_NAME_POPUP_TOWER, 1));

        items.put(Material.COMPASS, new DepositableItem(null, 2));
    }

    public DepositableItem(String displayNamePath, int minimumAmount) {
        this.displayNamePath = displayNamePath;
        this.minimumAmount = minimumAmount;
    }

    public String getDisplayName(Player p) {
        return Language.getMsg(p, displayNamePath);
    }
}