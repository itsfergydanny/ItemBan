package com.sysadamant.itemban;

import com.sysadamant.itemban.listeners.BreakListener;
import com.sysadamant.itemban.listeners.CraftingListener;
import com.sysadamant.itemban.listeners.PickupListener;
import com.sysadamant.itemban.listeners.PlaceListener;
import com.sysadamant.itemban.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemBan extends JavaPlugin {
    public static int serverVersion = 0;
    private String bypassPermission;
    private final Map<Material, String> craftingBanned = new HashMap<>();
    private final Map<Material, String> pickupBanned = new HashMap<>();
    private final Map<Material, String> placeBanned = new HashMap<>();
    private final Map<Material, String> breakBanned = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            serverVersion = Integer.parseInt(Bukkit.getServer().getBukkitVersion().split("\\.")[1]);
        } catch (NumberFormatException ignore) {}

        bypassPermission = getConfig().getString("bypassPermission");

        loadConfig("craftingBanned");
        loadConfig("pickupBanned");
        loadConfig("placeBanned");
        loadConfig("breakBanned");

        loadListeners();
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CraftingListener(this), this);
        pm.registerEvents(new PickupListener(this), this);
        pm.registerEvents(new PlaceListener(this), this);
        pm.registerEvents(new BreakListener(this), this);
    }

    private void loadConfig(String section) {
        int count = 0;
        for (String line : getConfig().getStringList(section)) {
            String[] args = line.split(":");
            if (args.length != 2) {
                getLogger().warning("Invalid item specified in " + section + " section for line: \"" + line + "\". Item will be skipped!");
                continue;
            }

            Material mat;
            try {
                mat = Material.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException ignore) {
                getLogger().warning("Invalid material specified in " + section + " section for line: \"" + line + "\". Item will be skipped!");
                continue;
            }

            String msg = Chat.format(args[1]);

            if (section.equals("craftingBanned")) {
                craftingBanned.put(mat, msg);
            } else if (section.equals("pickupBanned")) {
                pickupBanned.put(mat, msg);
            } else if (section.equals("placeBanned")) {
                placeBanned.put(mat, msg);
            } else if (section.equals("breakBanned")) {
                breakBanned.put(mat, msg);
            }

            count++;
        }
        getLogger().info("Loaded " + count + " valid entries for the " + section + " section.");
    }

    public String getBypassPermission() {
        return bypassPermission;
    }

    public Map<Material, String> getCraftingBanned() {
        return craftingBanned;
    }

    public Map<Material, String> getPickupBanned() {
        return pickupBanned;
    }

    public Map<Material, String> getPlaceBanned() {
        return placeBanned;
    }

    public Map<Material, String> getBreakBanned() {
        return breakBanned;
    }
}
