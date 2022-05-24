package com.sysadamant.itemban.listeners;

import com.sysadamant.itemban.ItemBan;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlaceListener implements Listener {
    private final ItemBan plugin;
    public PlaceListener(ItemBan plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission(plugin.getBypassPermission())) {
            return;
        }

        Material mat = event.getBlockPlaced().getType();

        if (plugin.getPlaceBanned().containsKey(mat)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getPlaceBanned().get(mat));
        }
    }
}
