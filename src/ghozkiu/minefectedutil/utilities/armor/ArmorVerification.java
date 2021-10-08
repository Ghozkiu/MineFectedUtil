package ghozkiu.minefectedutil.utilities.armor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArmorVerification {
    public static boolean hasGuts(Player p) {
        PlayerInventory playerInventory = p.getInventory();
        ItemStack chestplate = playerInventory.getChestplate();
        ItemStack GLUTS = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        if (chestplate != null) {
            if (chestplate.getType() != null) {
                return chestplate.getType() == GLUTS.getType();
            }
        }
        return false;
    }

    public static boolean hasMask(Player p) {
        PlayerInventory playerInventory = p.getInventory();
        ItemStack helmet = playerInventory.getHelmet();
        ItemStack MASK = new ItemStack(Material.LEATHER_HELMET, 1);
        if (helmet != null) {
            if (helmet.getType() != null) {
                return helmet.getType() == MASK.getType();
            }
        }
        return false;
    }
}
