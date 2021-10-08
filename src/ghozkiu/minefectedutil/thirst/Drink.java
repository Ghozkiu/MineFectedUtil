package ghozkiu.minefectedutil.thirst;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("Drink")
public class Drink implements ConfigurationSerializable {
    private String name;
    private int restores;
    private ItemStack itemStack;
    private List<String> effects;

    public Drink(String name, int restores, ItemStack itemStack, List<String> effects) {
        this.name = name;
        this.restores = restores;
        this.itemStack = itemStack;
        this.effects = effects;
    }

    public Drink(ItemStack itemStack){
        this.name = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        this.restores = 0;
        this.itemStack = itemStack;
        this.effects = new ArrayList<>();
    }

    public static Drink deserialize(Map<String, Object> args) {
        String name = (String) args.get("name");
        int restores = (int) args.get("restores");
        ItemStack itemStack = (ItemStack) args.get("itemStack");
        List<String> effects = (List<String>) args.get("effects");
        return new Drink(name, restores, itemStack, effects);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestores() {
        return restores;
    }

    public void setRestores(int restores) {
        this.restores = restores;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public List<String> getEffects() {
        return effects;
    }

    public void addEffect(String effect){
        this.effects.add(effect);
    }

    public void setEffects(List<String> effects) {
        this.effects = effects;
    }

    public void removeEffect(String effect){
        effects.removeIf(effectToRemove -> effectToRemove.contains(effect));
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.getName());
        result.put("restores", this.getRestores());
        result.put("itemStack", this.getItemStack());
        result.put("effects", this.getEffects());
        return result;
    }


}
