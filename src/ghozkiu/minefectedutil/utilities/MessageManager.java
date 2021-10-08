package ghozkiu.minefectedutil.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MessageManager {
    public static final String pluginName = "[ MineFected ] ";
    public static void ConsoleGood(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + pluginName + message);
    }
    public static void ConsoleBad(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + pluginName + message);
    }
    public static void ConsoleInfo(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + pluginName + message);
    }
}
