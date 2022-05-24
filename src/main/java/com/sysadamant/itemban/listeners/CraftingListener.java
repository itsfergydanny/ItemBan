package com.sysadamant.itemban.listeners;

import com.sysadamant.itemban.ItemBan;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftingListener implements Listener {
    private final ItemBan plugin;
    public CraftingListener(ItemBan plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        if (event.getWhoClicked().hasPermission(plugin.getBypassPermission())) {
            return;
        }

        Material mat = event.getRecipe().getResult().getType();

        if (plugin.getCraftingBanned().containsKey(mat)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(plugin.getCraftingBanned().get(mat));
        }
    }
}
