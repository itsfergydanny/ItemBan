package com.sysadamant.itemban.listeners;

import com.sysadamant.itemban.ItemBan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class BreakListener implements Listener {
    private final ItemBan plugin;
    public BreakListener(ItemBan plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission(plugin.getBypassPermission())) {
            return;
        }

        Material mat = event.getBlock().getType();

        if (plugin.getBreakBanned().containsKey(mat)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getBreakBanned().get(mat));
        }
    }
}
