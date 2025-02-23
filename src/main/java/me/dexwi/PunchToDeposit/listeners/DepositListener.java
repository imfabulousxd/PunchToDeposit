package me.dexwi.PunchToDeposit.listeners;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import me.dexwi.PunchToDeposit.PunchToDeposit;
import me.dexwi.PunchToDeposit.Messages;
import me.dexwi.PunchToDeposit.utils.Bedwars;
import me.dexwi.PunchToDeposit.utils.Blocks;
import me.dexwi.PunchToDeposit.utils.DepositableItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

import static me.dexwi.PunchToDeposit.PunchToDeposit.gameChestLocations;

public class DepositListener implements Listener {
    private final HashMap<Material, String> materialNames = new HashMap<>();

    public DepositListener() {
        materialNames.put(Material.IRON_INGOT, Messages.ITEM_NAME_IRON_INGOT);
        materialNames.put(Material.GOLD_INGOT, Messages.ITEM_NAME_GOLD_INGOT);
        materialNames.put(Material.DIAMOND, Messages.ITEM_NAME_DIAMOND);
        materialNames.put(Material.EMERALD, Messages.ITEM_NAME_EMERALD);
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        boolean enderChest;
        if (!(enderChest = block.getType() == Material.ENDER_CHEST) && !(block.getType() == Material.CHEST)) {
            return;
        }

        ItemStack item = event.getPlayer().getItemInHand();
        if (item == null || item.getAmount() == 0) {
            return;
        }

        Player player = event.getPlayer();
        if (player == null) {
            return;  // Maybe unnecessary?
        }
        BedWars.ArenaUtil arenaUtil = PunchToDeposit.bedwars.getArenaUtil();

        IArena arena = arenaUtil.getArenaByPlayer(player);
        if (arena == null || arena.isSpectator(player) || arena.getRespawnSessions().containsKey(player)) {
            return;
        }

        boolean otherTeamChest = false;
        Inventory inv;
        ITeam chestOwner = null;
        if (block.getType() == Material.CHEST) {
            chestOwner = chestOwner(arena, block);
            if (chestOwner == null) {
                return;
            } else if ((otherTeamChest = (chestOwner != arena.getTeam(player))) && !Bedwars.isEliminated(chestOwner)) {
                player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_FAILURE_NOT_ELIMINATED_TEAM_CHEST)
                        .replace("{team_color}", chestOwner.getColor().chat().toString())
                        .replace("{team_name}", chestOwner.getDisplayName(Language.getPlayerLanguage(player)))
                );
                return;
            }
            Chest chest = (Chest) block.getState();
            inv = chest.getInventory();
        } else if (block.getType() == Material.ENDER_CHEST) {
            inv = player.getEnderChest();
        } else {
            return;
        }

        if (!itemStackFitsInInventory(inv, item)) {
            player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_FAILURE_CHEST_FULL)
                    .replace("{item_amount}", Integer.toString(item.getAmount()))
                    .replace("{item_name}", getReadableItemName(item, player))
            );
            return;
        }

        DepositableItem depositableItem = DepositableItem.items.get(item.getType());
        if (depositableItem != null && item.getAmount() < depositableItem.minimumAmount) {
            player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_FAILURE_MINIMUM_ITEM)
                    .replace("{item_minimum_amount}", Integer.toString(depositableItem.minimumAmount))
            );
            return;
        }

        inv.addItem(new ItemStack(item));

        String itemDisplayName = (depositableItem != null) ? depositableItem.getDisplayName(player) : getReadableItemName(item, player);

        if (enderChest) {
            player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_SUCCESS_ENDER_CHEST)
                    .replace("{item_amount}", Integer.toString(item.getAmount()))
                    .replace("{item_name}", itemDisplayName)
            );
        } else if (otherTeamChest) {
            player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_SUCCESS_OTHER_TEAM_CHEST)
                    .replace("{item_amount}", Integer.toString(item.getAmount()))
                    .replace("{item_name}", itemDisplayName)
                    .replace("{team_color}", chestOwner.getColor().chat().toString())
                    .replace("{team_name}", chestOwner.getDisplayName(Language.getPlayerLanguage(player)))
            );
        } else {
            player.sendMessage(Language.getMsg(player, Messages.DEPOSIT_SUCCESS_TEAM_CHEST)
                    .replace("{item_amount}", Integer.toString(item.getAmount()))
                    .replace("{item_name}", itemDisplayName)
            );
        }

        player.getInventory().setItemInHand(null);
        player.getWorld().playSound(block.getLocation(), Sound.CHEST_CLOSE, 1, 1);
    }

    private static ITeam chestOwner(IArena a, Block block) {
        for (Map.Entry<ITeam, Location> entry: gameChestLocations.get(a).entrySet()) {
            if (
                    Blocks.locationEquals(entry.getValue(), block.getLocation())
            ) {
                return entry.getKey();
            }
        }
        return null;
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
        //noinspection ReplaceNullCheck
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