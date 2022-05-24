package com.sysadamant.itemban.listeners;

import com.sysadamant.itemban.ItemBan;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PickupListener implements Listener {
    private final ItemBan plugin;
    private final Map<UUID, Long> messageCooldown = new HashMap<>();
    public PickupListener(ItemBan plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (player.hasPermission(plugin.getBypassPermission())) {
            return;
        }

        Material mat = event.getItem().getItemStack().getType();

        if (plugin.getPickupBanned().containsKey(mat)) {
            event.setCancelled(true);
            event.getItem().remove();
            if (messageCooldown.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis() - 5000) {
                return;
            }
            messageCooldown.put(player.getUniqueId(), System.currentTimeMillis());
            player.sendMessage(plugin.getPickupBanned().get(mat));
        }
    }
}
