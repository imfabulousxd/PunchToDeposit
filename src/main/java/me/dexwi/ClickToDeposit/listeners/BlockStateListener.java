package me.dexwi.ClickToDeposit.listeners;

import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.language.Language;
import me.dexwi.ClickToDeposit.ClickToDeposit;
import me.dexwi.ClickToDeposit.Messages;
import me.dexwi.ClickToDeposit.utils.Bedwars;
import me.dexwi.ClickToDeposit.utils.Blocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

import static me.dexwi.ClickToDeposit.ClickToDeposit.gameChestLocations;

public class BlockStateListener implements Listener {
    private final HashMap<Material, String> materialNames = new HashMap<>();

    public BlockStateListener() {
        materialNames.put(Material.IRON_INGOT, Messages.ITEM_NAME_IRON_INGOT);
        materialNames.put(Material.GOLD_INGOT, Messages.ITEM_NAME_GOLD_INGOT);
        materialNames.put(Material.DIAMOND, Messages.ITEM_NAME_DIAMOND);
        materialNames.put(Material.EMERALD, Messages.ITEM_NAME_EMERALD);
    }

    @EventHandler
    public void blockBreakingEvent(BlockDamageEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.CHEST && block.getType() != Material.ENDER_CHEST) {
            return;
        }

        ItemStack item = event.getItemInHand();
        if (
                item == null ||
                item.getAmount() == 0 ||
                item.getType() == Material.WOOD_SWORD ||
                item.getType() == Material.GOLD_SWORD ||
                item.getType() == Material.STONE_SWORD ||
                item.getType() == Material.IRON_SWORD ||
                item.getType() == Material.DIAMOND_SWORD ||
                item.getType() == Material.COMPASS ||
                item.getType() == Material.WOOD_PICKAXE ||
                item.getType() == Material.GOLD_PICKAXE ||
                item.getType() == Material.STONE_PICKAXE ||
                item.getType() == Material.IRON_PICKAXE ||
                item.getType() == Material.DIAMOND_PICKAXE ||
                item.getType() == Material.WOOD_AXE ||
                item.getType() == Material.GOLD_AXE ||
                item.getType() == Material.IRON_AXE ||
                item.getType() == Material.DIAMOND_AXE ||
                item.getType() == Material.SHEARS
        ) {
            return;
        }

        Player player = event.getPlayer();
        if (player == null) {
            return;  // Maybe unnecessary?
        }
        BedWars.ArenaUtil arenaUtil = ClickToDeposit.bedwars.getArenaUtil();

        IArena arena = arenaUtil.getArenaByPlayer(player);
        if (arena == null || arena.isSpectator(player) || arena.getRespawnSessions().containsKey(player)) {
            return;
        }

        Inventory inv;
        if (event.getBlock().getType() == Material.CHEST) {
            if (!canOpenChest(arena, block, player)) {
                return;
            }
            Chest chest = (Chest) block.getState();
            inv = chest.getInventory();
        } else if (event.getBlock().getType() == Material.ENDER_CHEST) {
            inv = player.getEnderChest();
        } else {
            return;
        }

        if (!itemStackFitsInInventory(inv, item)) {
            player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_FAILURE)
                    .replace("{item_amount}", Integer.toString(item.getAmount()))
                    .replace("{item_name}", getReadableItemName(item, player))
                    .replace("{chest_type}", Language.getMsg(player, (block.getType() == Material.CHEST) ? Messages.CHEST_NAME : Messages.ENDER_CHEST_NAME))
            );
            return;
        }

        inv.addItem(new ItemStack(item));

        player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_SUCCESS)
                .replace("{item_amount}", Integer.toString(item.getAmount()))
                .replace("{item_name}", getReadableItemName(item, player))
                .replace("{chest_type}", Language.getMsg(player, (block.getType() == Material.CHEST) ? Messages.CHEST_NAME : Messages.ENDER_CHEST_NAME))
        );

        player.getInventory().setItemInHand(null);
    }

    private static boolean canOpenChest(IArena a, Block block, Player p) {
        ITeam playerTeam = a.getTeam(p);
        for (Map.Entry<ITeam, Location> entry: gameChestLocations.get(a).entrySet()) {
            if (
                    Blocks.locationEquals(entry.getValue(), block.getLocation())
            ) {
                return playerTeam == entry.getKey() || Bedwars.isEliminated(entry.getKey());
            }
        }
        return false;
    }

    private static boolean itemStackFitsInInventory(Inventory inventory, ItemStack item) {
        int remainingItemCount = item.getAmount();
        ItemStack[] inventoryItemStacks = inventory.getContents();
        for (ItemStack is: inventoryItemStacks) {
            if (is == null) {
                return true;
            } else if (is.getData() == item.getData()) {
                int maxAddable = is.getMaxStackSize() - is.getAmount();
                remainingItemCount -= Math.min(maxAddable, remainingItemCount);
            }

            if (remainingItemCount == 0) {
                return true;
            }
        }
        return false;
    }

    private String getReadableItemName(ItemStack item, Player player) {
        if (item == null || item.getType() == Material.AIR) {
            return "Nothing";
        }

        ItemMeta meta = item.getItemMeta();

        if (meta != null && meta.hasDisplayName()) {
            return meta.getDisplayName();
        }

        String rt = getMaterialName(item.getType(), player);
        if (rt == null) {
            return formatMaterialName(item.getType());
        } else {
            return rt;
        }
    }

    private String getMaterialName(Material material, Player player) {
        String path = materialNames.get(material);
        return (path != null) ? Language.getMsg(player, path) : null;
    }

    private static String formatMaterialName(Material material) {
        String name = material.toString().toLowerCase().replace('_', ' ');

        String[] words = name.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return formattedName.toString().trim();
    }
}
