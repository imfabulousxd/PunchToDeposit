package me.dexwi.ClickToDeposit.Listener;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import me.dexwi.ClickToDeposit.ClickToDeposit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.dexwi.ClickToDeposit.ClickToDeposit.log;

public class BlockBreakingListener implements Listener {
    @EventHandler
    public void blockBreakingEvent(BlockDamageEvent event) {
        ItemStack item = event.getItemInHand();
        if (
                item == null ||
                item.getAmount() == 0 ||
                item.getType() != Material.WOOD_SWORD ||
                item.getType() != Material.GOLD_SWORD ||
                item.getType() != Material.STONE_SWORD ||
                item.getType() != Material.IRON_SWORD ||
                item.getType() != Material.DIAMOND_SWORD ||
                item.getType() != Material.COMPASS
        ) {
            return;
        }

        Player player = event.getPlayer();
        if (player == null) {
            return;  // Maybe unnecessary?
        }
        Block block = event.getBlock();
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
            player.sendMessage(String.format(
                "Not enough space to deposit x%d %s to your %s!",
                item.getAmount(),
                (item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName()
                        : item.getType().toString(),
                (event.getBlock().getType() == Material.CHEST) ? "Team Chest" : "Ender Chest")
            );
            return;
        }

        inv.addItem(new ItemStack(item));

        player.sendMessage(String.format(
                "You deposited x%d %s to your %s!",
                item.getAmount(),
                (item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName()
                        : item.getType().toString(),
                (event.getBlock().getType() == Material.CHEST) ? "Team Chest" : "Ender Chest")
        );

        player.getInventory().remove(item);
    }

    private static boolean canOpenChest(IArena a, Block block, Player p) {
        ITeam owner = null;
        int isRad = a.getConfig().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
        for (ITeam t : a.getTeams()) {
            if (t.getSpawn().distance(block.getLocation()) <= isRad) {
                owner = t;
            }
        }
        if (owner != null) {
            if (!owner.isMember(p)) {
                return owner.getMembers().isEmpty() && owner.isBedDestroyed();
            }
        }
        return true;
    }

    private static boolean itemStackFitsInInventory(Inventory inventory, ItemStack item) {
        int remainingItemCount = item.getAmount();
        ItemStack[] inventoryItemStacks = inventory.getContents();
        for (ItemStack is: inventoryItemStacks) {
            if (is == null) {
                return true;
            } else if (is.getData() == item.getData()) {
                int maxAddable = is.getMaxStackSize() - is.getAmount();
                log.info(String.format("%d", maxAddable));
                remainingItemCount -= Math.min(maxAddable, remainingItemCount);
            }

            if (remainingItemCount == 0) {
                return true;
            }
        }
        return false;
    }
}
