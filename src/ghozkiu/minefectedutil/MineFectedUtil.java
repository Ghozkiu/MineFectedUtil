package ghozkiu.minefectedutil;

import ghozkiu.minefectedutil.commands.*;
import ghozkiu.minefectedutil.database.DrinksDatabase;
import ghozkiu.minefectedutil.database.ThirstDatabase;
import ghozkiu.minefectedutil.diseases.Diseases;
import ghozkiu.minefectedutil.diseases.ItemInteract;
import ghozkiu.minefectedutil.diseases.Whisperers;
import ghozkiu.minefectedutil.events.*;
import ghozkiu.minefectedutil.factions.FriendlyFire;
import ghozkiu.minefectedutil.thirst.Drink;
import ghozkiu.minefectedutil.utilities.Percentages;
import ghozkiu.minefectedutil.utilities.armor.ArmorValues;
import ghozkiu.minefectedutil.utilities.metadata.OtherMetaData;
import ghozkiu.minefectedutil.utilities.metadata.WhisperersMetaData;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;


public class MineFectedUtil extends JavaPlugin {
    public String configPath;

    static {
        ConfigurationSerialization.registerClass(Drink.class, "Drink");
    }


    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "[MineFectedUtil] Enabled");
        try {
            registerEvents();
            commandsRegister();
            configRegister();
            dataInitialize();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void onDisable() {
        getLogger().info(ChatColor.YELLOW + "[MineFectedUtil] Disabling...");
        ThirstDatabase.save();
    }

    public void registerEvents() throws SQLException {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Diseases(this), this);
        pm.registerEvents(new OtherMetaData(this), this);
        pm.registerEvents(new FriendlyFire(this), this);
        pm.registerEvents(new ItemInteract(this), this);
        pm.registerEvents(new DeathAndRespawn(this), this);
        pm.registerEvents(new InventoryEvents(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new WorldChange(this), this);
        pm.registerEvents(new ItemRepair(), this);
        pm.registerEvents(new BaitListener(this), this);
        pm.registerEvents(new BengalaListener(this), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new WhisperersMetaData(this), this);
        pm.registerEvents(new ArmorValues(this), this);
        pm.registerEvents(new Percentages(this), this);
        pm.registerEvents(new Whisperers(this), this);
        pm.registerEvents(new ItemConsumption(this), this);
    }

    private void dataInitialize() throws SQLException, ClassNotFoundException {
        ItemConsumption.loadEffects();
        ThirstDatabase.createLoadTable();
        ThirstDatabase.loadPlayersT();
        long thirstCooldown = this.getConfig().getInt(Paths.THIRST_SAVE_COOLDOWN.get()) * 20L;
        Bukkit.getScheduler().runTaskTimer(this, ThirstDatabase::save, thirstCooldown, thirstCooldown);
        DrinksDatabase drinksDatabase = new DrinksDatabase(this);
        drinksDatabase.loadDrinks();
        long drinksCooldown = this.getConfig().getInt(Paths.DRINKS_DATA_SAVE_COOLDOWN.get()) * 20L;
        Bukkit.getScheduler().runTaskTimer(this, drinksDatabase::saveDrinks, drinksCooldown, drinksCooldown);
    }



    public void commandsRegister() {
        getCommand("mfu").setExecutor(new MainCommand(this));
        getCommand("mapa").setExecutor(new MapCommand(this));
        getCommand("volver").setExecutor(new VolverCommand(this));
        getCommand("bengshoot").setExecutor(new Bengala(this));
        getCommand("hordas").setExecutor(new Hordas(this));
        getCommand("mfdatasave").setExecutor(new DataSave());
        getCommand("drink").setExecutor(new DrinkCommands());
    }

    public void configRegister() {
        File config = new File(getDataFolder(), "config.yml");
        this.configPath = config.getPath();
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}
