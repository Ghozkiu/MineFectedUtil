package ghozkiu.minefectedutil.events;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.database.DrinksDatabase;
import ghozkiu.minefectedutil.thirst.Drink;
import ghozkiu.minefectedutil.thirst.PlayerThirst;
import ghozkiu.minefectedutil.thirst.Thirst;
import ghozkiu.minefectedutil.utilities.MFile;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class ItemConsumption implements Listener {
    public final MFile drinksFile;
    private final FileConfiguration config;
    public static final HashMap<String, PotionEffectType> effects = new HashMap<>();

    public ItemConsumption(MineFectedUtil plugin) {
        this.drinksFile = new MFile(plugin, "drinks.yml");
        this.config = plugin.getConfig();
    }

    @EventHandler
    public void onItemConsumption(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        PlayerThirst playerT = Thirst.playersT.get(player.getName());
        ItemStack itemConsumed = event.getItem();
        if (itemConsumed != null) {
            switch (itemConsumed.getType()) {
                case POTION:
                    if (itemConsumed.getDurability() == 0) {
                        Thirst.setBossBarProgress(player, playerT.getThirstLevel() + config.getInt(Paths.WATER_GAIN.get()));
                    }
                    break;
                case MILK_BUCKET:
                    Thirst.setBossBarProgress(player, playerT.getThirstLevel() + config.getInt(Paths.MILK_GAIN.get()));
                    break;
            }
        }
    }

    @EventHandler
    public void onDrinkInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        EquipmentSlot equipmentSlot = event.getHand();
        if (equipmentSlot != null && equipmentSlot.equals(EquipmentSlot.HAND)) {

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                Player player = event.getPlayer();
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if (itemInHand.hasItemMeta()) {
                    String itemDisplayName = itemInHand.getItemMeta().getDisplayName();
                    if(itemDisplayName != null){
                        for(HashMap.Entry<String, Drink> entry : DrinksDatabase.drinksList.entrySet()){
                            Drink drink = DrinksDatabase.drinksList.get(entry.getKey());
                            if (ChatColor.stripColor(itemDisplayName).equals(drink.getName())) {
                                PlayerThirst playerT = Thirst.playersT.get(player.getName());
                                Thirst.setBossBarProgress(player, playerT.getThirstLevel() + drink.getRestores());


                                //EFFECTS
                                if (drink.getEffects() != null) {
                                    for (String effect : drink.getEffects()) {
                                        String[] splitEffectData = effect.split(",", 3);
                                        PotionEffectType potionType = effects.get(splitEffectData[0].toUpperCase());
                                        if(player.hasPotionEffect(potionType)){
                                            player.removePotionEffect(potionType);
                                        }
                                       applyEffect(player, splitEffectData);
                                    }
                                }
                                removeItemInMainHand(player);
                                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1f, 1f);
                                event.setCancelled(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    private void removeItemInMainHand(Player player){
        PlayerInventory playerInv = player.getInventory();
        ItemStack itemInMainHand = playerInv.getItemInMainHand();
        ItemMeta oldMeta = itemInMainHand.getItemMeta();
        int amount = itemInMainHand.getAmount();
        if(amount > 1){
            ItemStack newItem = new ItemStack(itemInMainHand.getType(), amount - 1);
            newItem.setItemMeta(oldMeta);
            playerInv.setItemInMainHand(newItem);
        }else{
            playerInv.setItemInMainHand(null);
        }
    }

    public static void applyEffect(Player player, String[] splitEffectData){
        PotionEffectType potionType = ItemConsumption.effects.get(splitEffectData[0].toUpperCase());
        if(potionType != null){
            int duration = Integer.parseInt(splitEffectData[1]) * 20;
            int level = Integer.parseInt(splitEffectData[2]) - 1;
            player.addPotionEffect(new PotionEffect(potionType, duration, level));
        }
    }

    public static void loadEffects() {
        effects.put("SLOW", PotionEffectType.SLOW);
        effects.put("BLINDNESS", PotionEffectType.BLINDNESS);
        effects.put("CONFUSION", PotionEffectType.CONFUSION);
        effects.put("WITHER", PotionEffectType.WITHER);
        effects.put("ABSORPTION", PotionEffectType.ABSORPTION);
        effects.put("DAMAGE_RESISTANCE", PotionEffectType.DAMAGE_RESISTANCE);
        effects.put("FAST_DIGGING", PotionEffectType.FAST_DIGGING);
        effects.put("FIRE_RESISTANCE", PotionEffectType.FIRE_RESISTANCE);
        effects.put("GLOWING", PotionEffectType.GLOWING);
        effects.put("HARM", PotionEffectType.HARM);
        effects.put("HEAL", PotionEffectType.HEAL);
        effects.put("HEALTH_BOOST", PotionEffectType.HEALTH_BOOST);
        effects.put("HUNGER", PotionEffectType.HUNGER);
        effects.put("INCREASE_DAMAGE", PotionEffectType.INCREASE_DAMAGE);
        effects.put("INVISIBILITY", PotionEffectType.INVISIBILITY);
        effects.put("JUMP", PotionEffectType.JUMP);
        effects.put("LEVITATION", PotionEffectType.LEVITATION);
        effects.put("NIGHT_VISION", PotionEffectType.NIGHT_VISION);
        effects.put("POISON", PotionEffectType.POISON);
        effects.put("SATURATION", PotionEffectType.SATURATION);
        effects.put("SLOW_DIGGING", PotionEffectType.SLOW_DIGGING);
        effects.put("UNLUCK", PotionEffectType.UNLUCK);
        effects.put("WATER_BREATHING", PotionEffectType.WATER_BREATHING);
        effects.put("WEAKNESS", PotionEffectType.WEAKNESS);
        effects.put("SPEED", PotionEffectType.SPEED);
        effects.put("REGENERATION", PotionEffectType.REGENERATION);
    }

}
