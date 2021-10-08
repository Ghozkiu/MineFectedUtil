package ghozkiu.minefectedutil.utilities.armor;

import ghozkiu.minefectedutil.diseases.Diseases;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class ArmorValues implements Listener {
    private Plugin plugin;

    public ArmorValues(Plugin plugin) {
        this.plugin = plugin;
    }

    public int helmet(Player p) {
        PlayerInventory pInventory = p.getInventory();
        if (pInventory.getHelmet() != null &&
                pInventory.getHelmet().getType() != null) {
            switch (pInventory.getHelmet().getType()) {
                case DIAMOND_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.diamond_helmet");
                case IRON_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.iron_helmet");
                case GOLD_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.golden_helmet");
                case CHAINMAIL_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.chainmail_helmet");
                case LEATHER_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.leather_helmet");
            }
            return 0;
        }
        return 0;
    }

    public int chestplate(Player p) {
        PlayerInventory pInventory = p.getInventory();
        if (pInventory.getChestplate() != null &&
                pInventory.getChestplate().getType() != null) {
            switch (pInventory.getChestplate().getType()) {
                case DIAMOND_CHESTPLATE:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.diamond_chestplate");
                case IRON_CHESTPLATE:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.iron_chestplate");
                case GOLD_CHESTPLATE:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.golden_chestplate");
                case CHAINMAIL_CHESTPLATE:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.chainmail_chestplate");
                case LEATHER_CHESTPLATE:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.leather_chestplate");
            }
            return 0;
        }
        return 0;
    }

    public int leggings(Player p) {
        PlayerInventory pInventory = p.getInventory();
        if (pInventory.getLeggings() != null &&
                pInventory.getLeggings().getType() != null) {
            switch (pInventory.getLeggings().getType()) {
                case DIAMOND_LEGGINGS:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.diamond_legging s");
                case IRON_LEGGINGS:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.iron_leggings");
                case GOLD_LEGGINGS:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.golden_leggings");
                case CHAINMAIL_LEGGINGS:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.chainmail_leggings");
                case LEATHER_LEGGINGS:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.leather_leggings");
            }
            return 0;
        }
        return 0;
    }

    public int boots(Player p) {
        PlayerInventory pInventory = p.getInventory();
        if (pInventory.getLeggings() != null &&
                pInventory.getLeggings().getType() != null) {
            switch (pInventory.getLeggings().getType()) {
                case DIAMOND_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.diamond_boots");
                case IRON_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.iron_boots");
                case GOLD_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.golden_boots");
                case CHAINMAIL_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.chainmail_boots");
                case LEATHER_HELMET:
                    return Diseases.plugin.getConfig().getInt("ArmorValues-Values.leather_boots");
            }
            return 0;
        }
        return 0;
    }
}
