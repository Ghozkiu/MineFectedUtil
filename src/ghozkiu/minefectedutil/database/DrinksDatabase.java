package ghozkiu.minefectedutil.database;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.thirst.Drink;
import ghozkiu.minefectedutil.utilities.MFile;
import ghozkiu.minefectedutil.utilities.MessageManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class DrinksDatabase {
    private final MFile drinksFile;
    private final ConfigurationSection drinksSection;
    public static HashMap<String, Drink> drinksList = new HashMap<>();

    public DrinksDatabase(MineFectedUtil plugin) {
        this.drinksFile = new MFile(plugin, "drinks.yml");
        this.drinksSection = drinksFile.getConfigurationSection("drinks");
    }
    public void loadDrinks() {
        for(String key : getDrinksSection().getKeys(false)) {
            Drink drink = Drink.deserialize(getDrinksSection().getConfigurationSection(key).getValues(false));
            drinksList.put(key, drink);
        }
    }

    public void saveDrinks(){
        for(Map.Entry<String, Drink> entry : drinksList.entrySet()){
            Drink drink = entry.getValue();
            String key = entry.getKey();
            insert(key, drink);
        }
        MessageManager.ConsoleInfo("Drinks database has been updated");
    }
    private void insert(String key, Drink drink){
        try{
            drinksFile.set("drinks." + key, drink.serialize());
            drinksFile.save();
        }catch (Exception e){
            MessageManager.ConsoleInfo("there was a problem inserting a drink");
            e.printStackTrace();
        }
    }

    public ConfigurationSection getDrinksSection() {
        return drinksSection;
    }

}
