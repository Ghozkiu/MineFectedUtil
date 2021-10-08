package ghozkiu.minefectedutil.diseases;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.utilities.metadata.OtherMetaData;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class ItemInteract implements Listener {
    public ItemStack antibiotics = new ItemStack(Material.INK_SACK, 1, (short) 10);
    public ItemStack knife = new ItemStack(Material.IRON_SWORD, 1);
    public ItemStack bandage = new ItemStack(Material.PAPER, 1);
    public ItemStack armProsthesis = new ItemStack(Material.SUGAR, 1);
    public ItemStack legProsthesis = new ItemStack(Material.CHORUS_FRUIT_POPPED, 1);
    public static ItemStack air = new ItemStack(Material.AIR);
    public MineFectedUtil plugin;

    private Player p;

    private UUID uuid;

    public ItemInteract(MineFectedUtil plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent e) {
        this.p = e.getPlayer();
        this.uuid = this.p.getUniqueId();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR && this.p.isSneaking()) || (e.getAction() == Action.RIGHT_CLICK_BLOCK && this.p.isSneaking())) {
            switch (this.p.getInventory().getItemInMainHand().getType()) {
                case IRON_SWORD:
                    ItemMeta metaKnife = p.getInventory().getItemInMainHand().getItemMeta();
                    knife.getItemMeta();
                    knife.setItemMeta(metaKnife);
                    if (OtherMetaData.hasArmInfection(this.p, this.uuid)) {
                        if (!OtherMetaData.hasAmputatedArm(this.p, this.uuid)) {
                            amputateArm();
                        } else {
                            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.NOMORE.get())));
                        }
                    } else if (OtherMetaData.hasLegInfection(this.p, this.uuid)) {
                        if (!OtherMetaData.hasAmputatedLeg(this.p, this.uuid)) {
                            amputateLeg();
                        } else {
                            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.NOMORE.get())));
                        }
                    }
                    break;
                case PAPER:
                    bandage();
                    break;
                case CHORUS_FRUIT_POPPED:
                    legProsthesis();
                    break;
                case SUGAR:
                    armProsthesis();
                    break;
                default:
                    //Comprobar durabilidad tambien
                    if (this.p.getInventory().getItemInMainHand().getType().equals(this.antibiotics.getType())) {
                        Antibiotics();
                    }
                    break;
            }
        }
    }

    private void amputateLeg() {
        if (this.p.getHealth() > this.plugin.getConfig().getInt("Config.amputation_damage")) {
            ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
            knife.getItemMeta();
            knife.setItemMeta(meta);
            this.p.getInventory().removeItem(this.knife);
            this.p.removePotionEffect(PotionEffectType.WITHER);
            this.p.sendTitle(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.JustCutTittle.get())), ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.JustCutSubtittle.get())), 10, 40, 10);
            this.p.damage(this.plugin.getConfig().getInt("Config.amputation_damage"));
            this.p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, this.plugin.getConfig().getInt("Config.amputation_blindness_duration") * 20, this.plugin
                    .getConfig().getInt("Config.amputation_blindness_level")));
            this.p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, this.plugin.getConfig().getInt("Config.amputation_confusion_duration") * 20, this.plugin
                    .getConfig().getInt("Config.amputation_confusion_level")));
            this.p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, this.plugin.getConfig().getInt("Config.amputation_slowness_duration") * 20, this.plugin
                    .getConfig().getInt("Config.amputation_slowness_level")));
            OtherMetaData.removeLegInfection(this.p, this.uuid);
            OtherMetaData.setBleeding(this.p, this.uuid);
            OtherMetaData.setAmputatedLeg(this.p, this.uuid);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.LEG_PROTHESIS.get())));
        } else {
            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin
                    .getConfig().getString(Paths.CANT_AMPUTATE.get())));
        }
    }

    private void amputateArm() {
        if (this.p.getHealth() > this.plugin.getConfig().getInt("Config.amputation_damage")) {
            ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
            knife.getItemMeta();
            knife.setItemMeta(meta);
            if (p.getInventory().getItemInOffHand() != null && p.getInventory().getItemInOffHand().getType() != Material.AIR) {
                ItemStack OffHandItem = p.getInventory().getItemInOffHand();
                ItemMeta OffHandItemMeta = OffHandItem.getItemMeta();
                OffHandItem.setItemMeta(OffHandItemMeta);
                p.getInventory().setItemInOffHand(air);
                p.getWorld().dropItem(p.getLocation().add(0, 1, 0), OffHandItem);
            }
            this.p.getInventory().removeItem(this.knife);
            this.p.removePotionEffect(PotionEffectType.WITHER);
            this.p.sendTitle(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.JustCutTittle.get())), ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.JustCutSubtittle.get())), 10, 40, 10);
            this.p.damage(this.plugin.getConfig().getInt("Config.amputation_damage"));
            this.p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, this.plugin.getConfig().getInt("Config.amputation_blindness_duration") * 20, this.plugin
                    .getConfig().getInt("Config.amputation_blindness_level")));
            this.p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, this.plugin.getConfig().getInt("Config.amputation_confusion_duration") * 20, this.plugin
                    .getConfig().getInt("Config.amputation_confusion_level")));
            OtherMetaData.removeArmInfection(this.p, this.uuid);
            OtherMetaData.setBleeding(this.p, this.uuid);
            OtherMetaData.setAmputatedArm(this.p, this.uuid);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.ARM_PROTHESIS.get())));
        } else {
            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin
                    .getConfig().getString(Paths.CANT_AMPUTATE.get())));
        }
    }

    public void bandage() {
        if (OtherMetaData.hasBleeding(this.p, this.uuid)) {
            ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
            this.bandage.getItemMeta();
            this.bandage.setItemMeta(meta);
            this.p.getInventory().removeItem(this.bandage);
            OtherMetaData.removeBleeding(this.p, this.uuid);
            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.BLEEDING_STOPED.get())));
        }
    }

    public void Antibiotics() {
        ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
        this.antibiotics.getItemMeta();
        this.antibiotics.setItemMeta(meta);
        if (OtherMetaData.hasBodyInfection(this.p, this.uuid)) {
            this.p.getInventory().removeItem(this.antibiotics);
            this.p.removePotionEffect(PotionEffectType.WITHER);
            OtherMetaData.removeBodyInfection(this.p, this.uuid);
            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.WERE_CURE.get())));
        } else if (OtherMetaData.hasArmInfection(this.p, this.uuid)) {
            this.p.getInventory().removeItem(this.antibiotics);
            this.p.removePotionEffect(PotionEffectType.WITHER);
            OtherMetaData.removeArmInfection(this.p, this.uuid);
            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.WERE_CURE.get())));
        } else if (OtherMetaData.hasLegInfection(this.p, this.uuid)) {
            this.p.getInventory().removeItem(this.antibiotics);
            this.p.removePotionEffect(PotionEffectType.WITHER);
            OtherMetaData.removeLegInfection(this.p, this.uuid);
            this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.WERE_CURE.get())));
        }
    }

    public void legProsthesis() {
        if (OtherMetaData.hasAmputatedLeg(p, uuid) && !(OtherMetaData.hasLegProsthesis(p, uuid))) {
            ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
            this.legProsthesis.getItemMeta();
            this.legProsthesis.setItemMeta(meta);
            p.getInventory().removeItem(legProsthesis);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.PROSTHESIS.get())));
            OtherMetaData.setLegProsthesis(p, uuid);
            this.p.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    public void armProsthesis() {
        if (OtherMetaData.hasAmputatedArm(p, uuid) && !(OtherMetaData.hasArmProsthesis(p, uuid))) {
            ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
            this.armProsthesis.getItemMeta();
            this.armProsthesis.setItemMeta(meta);
            p.getInventory().removeItem(armProsthesis);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.PROSTHESIS.get())));
            OtherMetaData.setArmProsthesis(p, uuid);
        }
    }
}
