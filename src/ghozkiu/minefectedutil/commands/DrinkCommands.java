package ghozkiu.minefectedutil.commands;

import ghozkiu.minefectedutil.database.DrinksDatabase;
import ghozkiu.minefectedutil.diseases.Diseases;
import ghozkiu.minefectedutil.thirst.Drink;
import ghozkiu.minefectedutil.utilities.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public class DrinkCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(player.isOp()){
                PlayerInventory playerInventory = player.getInventory();
                ItemStack itemInMainHand = playerInventory.getItemInMainHand();
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////  DRINK COMMANDS - mfu reload  ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
                //drink create %name%
                //drink setres %name% %restorarionLevel%
                //drink addeffect %name% %effect%
                //drink get %name%
                //drink list
                //drink deleffect %name% %effect%
                //drink give %player% %drinkName%

                if (args.length > 0) {
                    switch (args[0]) {
                        case "create":
                            if (args[1] != null && itemInMainHand.hasItemMeta()) {
                                String name = args[1].trim();
                                Drink drink = new Drink(itemInMainHand);
                                DrinksDatabase.drinksList.put(name, drink);
                                player.sendMessage(ChatColor.GREEN + "Se ha creado la bebida");
                            } else {
                                player.sendMessage(ChatColor.RED + "Debes especificar un nombre sin espacios y tener un objeto en mano");
                            }
                            break;
                        case "setres":
                            try {
                                if (args[1] != null && args[2] != null) {
                                    try {
                                        int restores = Integer.parseInt(args[2]);
                                        Drink drink = DrinksDatabase.drinksList.get(args[1]);
                                        if (drink != null) {
                                            drink.setRestores(restores);
                                            player.sendMessage(ChatColor.GREEN + "Se ha modificado la bebida");
                                        } else {
                                            player.sendMessage(ChatColor.RED + "No se ha encontrado una bebida con ese nombre");
                                        }
                                    } catch (NumberFormatException exception) {
                                        player.sendMessage(ChatColor.RED + "Debes especificar un numero valido");
                                    }

                                } else {
                                    player.sendMessage(ChatColor.RED + "Debes especificar un nombre y numero");
                                }
                            } catch (ArrayIndexOutOfBoundsException exception) {
                                player.sendMessage(ChatColor.RED + "Debes ingresar una sintaxis correcta.");
                            }
                            break;
                        case "addeffect":
                            try {
                                if (args[1] != null && args[2] != null) {
                                    Drink drink = DrinksDatabase.drinksList.get(args[1]);
                                    if (drink != null) {
                                        drink.addEffect(args[2]);
                                        player.sendMessage(ChatColor.GREEN + "Se han agregado efectos a la bebida!");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No se ha encontrado una bebida con ese nombre");
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException exception) {
                                player.sendMessage(ChatColor.RED + "Debes ingresar una sintaxis correcta.");
                            }
                            break;
                        case "get":
                            if (args[1] != null) {
                                Drink drink = DrinksDatabase.drinksList.get(args[1]);
                                if(!giveDrink(player, drink)){
                                    player.sendMessage(ChatColor.RED + "No se ha encontrado una bebida con ese nombre.");
                                }
                            }
                            break;
                        case "list":
                            String drinks = "";
                            for (HashMap.Entry<String, Drink> entry : DrinksDatabase.drinksList.entrySet()) {
                                drinks += entry.getKey() + ", ";
                            }
                            player.sendMessage(ChatColor.GREEN + drinks);
                            break;

                        case "deleffect":
                            try {
                                if (args[1] != null && args[2] != null) {
                                    Drink drink = DrinksDatabase.drinksList.get(args[1]);
                                    if (drink != null) {
                                        drink.removeEffect(args[2]);
                                        player.sendMessage(ChatColor.GREEN + "Se han removido el efecto de la bebida!");
                                    } else {
                                        player.sendMessage(ChatColor.RED + "No se ha encontrado una bebida con ese nombre");
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException exception) {
                                player.sendMessage(ChatColor.RED + "Debes ingresar una sintaxis correcta.");
                            }
                            break;

                        case "give":
                            try {
                                if (args[1] != null && args[2] != null) {
                                    Drink drink = DrinksDatabase.drinksList.get(args[2]);
                                    Player playerToGiveItem = Bukkit.getPlayer(args[1]);
                                    if(!giveDrink(playerToGiveItem, drink)){
                                        player.sendMessage(ChatColor.RED + "No se ha encontrado una bebida o jugador con ese nombre.");
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException exception) {
                                player.sendMessage(ChatColor.RED + "Debes ingresar una sintaxis correcta.");
                            }
                            break;

                        case "dataSave":
                            DrinksDatabase drinksDatabase = new DrinksDatabase(Diseases.plugin);
                            drinksDatabase.saveDrinks();
                            player.sendMessage(ChatColor.GREEN + "Se ha guardado la base de datos");
                            break;
                    }
                } else {
                    player.sendMessage(ChatColor.AQUA + "-----[ " + ChatColor.GREEN + "MineFected " + ChatColor.YELLOW + "Drinks" + ChatColor.AQUA + " ]-----");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "create " + ChatColor.AQUA + "[name]");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "setres " + ChatColor.AQUA + "[name]" + ChatColor.GOLD + " [restoration]");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "addeffect " + ChatColor.AQUA + "[name]" + ChatColor.GOLD + " [effect,duration,level]");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "deleffect " + ChatColor.AQUA + "[name]" + ChatColor.GOLD + " [effect]");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "get " + ChatColor.AQUA + "[name]");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "give " + ChatColor.AQUA + "[name]" + ChatColor.GOLD + " [drink]");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "dataSave");
                    player.sendMessage(ChatColor.YELLOW + "/drink " + ChatColor.WHITE + "list");
                }
            }
        }else if(args.length > 0 && args[0].equals("give")){
            try {
                if (args[1] != null && args[2] != null) {
                    Drink drink = DrinksDatabase.drinksList.get(args[2]);
                    Player playerToGiveItem = Bukkit.getPlayer(args[1]);
                    if(!giveDrink(playerToGiveItem, drink)){
                        MessageManager.ConsoleBad("No se ha encontrado una bebida o jugador con ese nombre.");
                    }
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                MessageManager.ConsoleBad("No se ha encontrado una bebida o jugador con ese nombre.");
            }
        }
        return false;
    }


    public boolean giveDrink(Player player, Drink drink) {
        if (drink != null && player != null) {
            ItemStack newDrink = drink.getItemStack();
            newDrink.setItemMeta(drink.getItemStack().getItemMeta());
            player.getInventory().addItem(newDrink);
            return true;
        }
        return false;
    }
}
