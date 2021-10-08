package ghozkiu.minefectedutil.commands;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.diseases.ItemInteract;
import ghozkiu.minefectedutil.events.ItemConsumption;
import ghozkiu.minefectedutil.utilities.MessageManager;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import ghozkiu.minefectedutil.utilities.paths.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;


public class MainCommand implements CommandExecutor {
    private final MineFectedUtil plugin;

    public MainCommand(MineFectedUtil plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////  RELOAD COMMAND - mfu reload  ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
            if (args.length > 0 && args[0].equalsIgnoreCase("reload") && (p
                    .hasPermission(Permissions.RELOAD.get()) || p.isOp())) {
                this.plugin.reloadConfig();
                ItemConsumption itemConsumption = new ItemConsumption(plugin);
                try {
                    itemConsumption.drinksFile.load("drinks.yml");
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin
                        .getConfig().getString(Paths.CONFIG_RELOADED.get())));
                MessageManager.ConsoleInfo("MFU HAS BEEN RELOADED");
                return true;

///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////  HAT COMMAND - mfu hat  ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

            } else if (args.length > 0 && args[0].equalsIgnoreCase("hat")
                    && p.hasPermission(Permissions.HAT.get())) {
                PlayerInventory inventory = p.getInventory();
                if (inventory.getItemInMainHand() == null ||
                        inventory.getItemInMainHand() == ItemInteract.air) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDebes tener un objeto en tus manos."));
                    return false;
                }

                ItemStack item = inventory.getItemInMainHand();
                if (item != null) {
                    if (inventory.getHelmet() != null) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.HELMET_FAILED.get())));
                        return false;
                    }
                    inventory.setHelmet(item);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.HELMET_EQUIPED.get())));
                    inventory.setItemInMainHand(ItemInteract.air);
                    return true;
                }
            }
        }
        return false;
    }

}
