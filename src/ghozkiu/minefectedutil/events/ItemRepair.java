package ghozkiu.minefectedutil.events;

import com.shampaggon.crackshot.CSUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemRepair implements Listener {
    private CSUtility ut = new CSUtility();
    private ItemStack cinta = new ItemStack(Material.FEATHER, 1);

    @EventHandler
    public void onClickRepair(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (e.getAction() == Action.RIGHT_CLICK_AIR && p.isSneaking()) {
            if (inv.getItemInOffHand().getType() == Material.FEATHER) {

                if ((inv.getItemInMainHand().getType() == Material.DIAMOND_HOE) || inv.getItemInMainHand().getType() == Material.SHULKER_SHELL || inv.getItemInMainHand().getType() == Material.RECORD_7) {

                    ItemStack weapon = inv.getItemInMainHand();
                    String title = ut.getWeaponTitle(weapon);
                    int index;
                    String subs;
                    try {
                        index = title.lastIndexOf("_");
                        subs = title.substring(index + 1);
                    } catch (IndexOutOfBoundsException ex) {
                        subs = "ignore";
                    }
                    if (!title.equals("Lanzacohetes") && (!subs.equals("u"))) {

                        ItemStack currentItem = inv.getItemInOffHand();
                        int amount = currentItem.getAmount();
                        if (amount > 1) {
                            ItemStack newItem = new ItemStack(currentItem.getType(), amount - 1);
                            inv.setItemInOffHand(newItem);
                        } else {
                            inv.setItemInOffHand(null);
                        }


                        inv.removeItem(weapon);
                        ut.giveWeapon(p, title.concat("_u"), 1);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 5, (float) 1.3);
                    } else {
                        p.sendMessage(ChatColor.RED + "Solo puedes reparar un arma una vez!");
                    }
                }
            }
        }
    }
}
