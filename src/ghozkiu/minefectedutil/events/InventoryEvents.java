package ghozkiu.minefectedutil.events;

import ghozkiu.minefectedutil.diseases.ItemInteract;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import ghozkiu.minefectedutil.utilities.metadata.OtherMetaData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class InventoryEvents implements Listener {
    private static final int OFFHAND_SLOT = 40;
    private final List<String> cascos;
    private Plugin plugin;

    public InventoryEvents(Plugin plugin) {
        this.plugin = plugin;
        this.cascos = plugin.getConfig().getStringList("Config.cascos");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        disableOffHand(e);
        hatOnClick(e);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        disableDragOffHand(e);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        cancelSwapOffHand(e);
    }

    private void cancelSwapOffHand(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if ((OtherMetaData.hasAmputatedArm(p, uuid) && !(OtherMetaData.hasArmProsthesis(p, uuid)))) {
            e.setCancelled(true);
        }
    }

    private void disableDragOffHand(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();
        UUID uuid = p.getUniqueId();
        if (e.getInventorySlots().contains(OFFHAND_SLOT) && (OtherMetaData.hasAmputatedArm(p, uuid) && !(OtherMetaData.hasArmProsthesis(p, uuid)))) {
            e.setCancelled(true);
        }
    }

    private void disableOffHand(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        UUID uuid = p.getUniqueId();
        if ((e.getSlot() == OFFHAND_SLOT) && (OtherMetaData.hasAmputatedArm(p, uuid) && !(OtherMetaData.hasArmProsthesis(p, uuid)))) {
            e.setCancelled(true);
        }
        if (e.getClickedInventory() instanceof HorseInventory) {
            e.setCancelled(true);
        }
    }

    private void hatOnClick(InventoryClickEvent e) {
        if (e.getClick() == ClickType.SHIFT_RIGHT) {
            Player p = (Player) e.getWhoClicked();
            PlayerInventory inventory = p.getInventory();
            ItemStack currentItem = e.getCurrentItem();
            for (String casco : cascos) {
                if (currentItem.getItemMeta().getDisplayName() != null) {
                    if (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).equals(casco)) {
                        if (inventory.getHelmet() == null) {
                            inventory.setItem(e.getSlot(), ItemInteract.air);
                            inventory.setHelmet(currentItem);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.HELMET_EQUIPED.get())));
                            e.setCancelled(true);
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.HELMET_FAILED.get())));
                        }
                    }
                }
            }
        }
    }
}

