package ghozkiu.minefectedutil.commands;

import ghozkiu.minefectedutil.MineFectedUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class Hordas implements CommandExecutor {
    private String alex = "Alexandria";
    private String sant = "Santuario";
    private String hill = "Hilltop";
    private String susu = "Susurradores";
    private String reino = "Reino";
    private ConsoleCommandSender console = Bukkit.getConsoleSender();
    private int id;
    private String[][] coords = {
            {"1562,65,-1806", "1560,65,-1793", "1634,65,-1809", "1561,65,-1760"},                  //Coords Alexandria, R=0, C=0-3
            {"-2339,66,-2509", "-2371,66,-2527", "-2366,66,-2469", "-2405,66,-2519"},                //Coords Santuario, R=1, C=0-3
            {"1170,67,-1309", "1154,67,-1290", "1167,67,-1347", "1110,67,-1387"},                 //Coords Hilltop, R=2, C=0-3
            {"-321,67,-4107", "-305,67,-4095", "-372,69,-4126", "-326,70,-4145"},                 //Coords Susurradores R=3 C=0-3
            {"-2253,69,-3190", "-2228,69,-3185", "-2275,69,-3182", "-2185,69,-3178"},            //Coords Reino R=4 C=0-3

    };
    private String banda;
    private String type;
    private String mobAmount;
    private MineFectedUtil plugin;   //Cambiar por nombre de la clase principal

    public Hordas(MineFectedUtil plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            switch (args[0]) {
                case "random":
                    int random1 = (int) (Math.random() * 4);

                    randomizer(random1);

                    return true;
                case "spawn":
                    if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
                        this.plugin.getLogger().info("[Hordas] No hay suficientes jugadores");
                        this.plugin.getLogger().info("[Hordas] abortando horda");
                    } else {
                        int random2 = (int) (Math.random() * 7);
                        mobSelector(random2);
                        arrived();
                    }
                    return true;

                case "announce":
                    String time = args[1];
                    announcer(time);
                    return true;
            }


        } else {
            if (args[0].equals("location")) {
                sender.sendMessage(ChatColor.YELLOW + "Ultima localizacion de horda: " + ChatColor.GREEN + this.banda);
                return true;
            }
        }
        return false;
    }


    public void randomizer(int n) {            //Elige el blanco (banda)
        switch (n) {
            case 0:
                this.banda = alex;
                id = 0;
                break;
            case 1:
                this.banda = sant;
                id = 1;
                break;
            case 2:
                this.banda = hill;
                id = 2;
                break;
            case 3:
                this.banda = susu;
                id = 3;
                break;
            case 4:
                this.banda = reino;
                id = 4;
        }


    }

    public void mobSelector(int n) {            //Elige el tipo de mob y lo spawnea
        switch (n) {
            case 0:
                this.type = "Caminante";
                this.mobAmount = "single";
                Caminantes(id, mobAmount);
                break;
            case 1:
                this.type = "Chasqueador";
                this.mobAmount = "single";
                Chasqueadores(id, mobAmount);
                break;
            case 2:
                this.type = "Bandido";
                this.mobAmount = "combo";
                SpawnBandidos(id);
                break;
            case 3:
                this.type = "Chasqueador";
                this.mobAmount = "combo";
                Chasqueadores(id, mobAmount);
                break;
            case 4:
                this.type = "HordaTrampa";
                this.mobAmount = "combo";
                Caminantes(id, mobAmount);
                break;
            case 5:
                this.type = "Chasqueador";
                this.mobAmount = "boss";
                Chasqueadores(id, mobAmount);
                break;
            case 6:
                if (!(this.banda.equals(susu))) {
                    this.type = "Susurradores";
                    this.mobAmount = "single";
                    Susurradores(id, mobAmount);
                } else {
                    this.type = "Chasqueador";
                    this.mobAmount = "single";
                    Chasqueadores(id, mobAmount);
                }
                break;
            case 7:
                this.type = "Peste";
                this.mobAmount = "combo";
                Peste(id);

        }

    }


    public String getType() {
        return this.type;
    }

    public String getBanda() {
        return this.banda;
    }

    public int getId() {
        return this.id;
    }

    public int getX(int n) {
        String cc = coords[n][0];
        return Integer.parseInt(cc.substring(0, cc.indexOf(",")));
    }

    public int getY(int n) {
        String cc = coords[n][0];
        return Integer.parseInt(cc.substring((cc.indexOf(",") + 1), (cc.lastIndexOf(","))));
    }

    public int getZ(int n) {
        String cc = coords[n][0];
        return Integer.parseInt(cc.substring(cc.lastIndexOf(",") + 1));
    }

//////////////////////////////////////////////////////////////////////////////////
//METODOS SPAWN									                       		   //           
////////////////////////////////////////////////////////////////////////////////


    public void SpawnBandidos(int rows) {
        Bukkit.dispatchCommand(console, "mm m s BandidoD " + 30 + " TWD2," + coords[rows][0]);   //Armas cuerpo a cuerpo
        Bukkit.dispatchCommand(console, "mm m s BandidoA " + 17 + " TWD2," + coords[rows][1]);   //ligeramente armados
        Bukkit.dispatchCommand(console, "mm m s BandidoC " + 5 + " TWD2," + coords[rows][2]);   //fuertemente armados
        nearTitle(coords[rows][0], 1);
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.dispatchCommand(console, "mm m s BandidoD " + 5 + " TWD2," + coords[rows][0]);
                Bukkit.dispatchCommand(console, "mm m s BandidoA " + 14 + " TWD2," + coords[rows][1]);   //ligeramente armados
                Bukkit.dispatchCommand(console, "mm m s BandidoC " + 3 + " TWD2," + coords[rows][2]);
                nearTitle(coords[rows][0], 2);
            }

        }.runTaskLater(this.plugin, 600);
    }

    public void Chasqueadores(int rows, String tipo) {
        if (tipo.equals("boss")) {
            //Bukkit.dispatchCommand(console, "mm m s Gordinflon "+1+" TWD2,"+coords[rows][0]);
            Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][1]);
            Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][2]);
            Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][3]);
            nearTitle(coords[rows][0], 1);
        } else {
            Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][0]);
            Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][1]);
            Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][2]);
            Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 10 + " TWD2," + coords[rows][3]);
            nearTitle(coords[rows][0], 1);
            new BukkitRunnable() {

                @Override
                public void run() {
                    Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][0]);
                    Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 20 + " TWD2," + coords[rows][1]);
                    Bukkit.dispatchCommand(console, "mm m s ChasqueadorH " + 10 + " TWD2," + coords[rows][3]);
                    nearTitle(coords[rows][0], 2);
                }
            }.runTaskLater(this.plugin, 600);
        }
    }

    public void Caminantes(int rows, String tipo) {
        if (tipo.equals("single")) {
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][0]);
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][1]);
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][2]);
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][3]);
            Bukkit.getServer().getLogger().info("a");
            new BukkitRunnable() {

                @Override
                public void run() {
                    Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][0]);
                    Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][1]);
                    Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][2]);
                    Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][3]);
                }

            }.runTaskLater(this.plugin, 600);


        } else if (tipo.equals("trampa")) {
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][0]);
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 50 + " TWD2," + coords[rows][1]);
            Bukkit.dispatchCommand(console, "mm m s Susurrador " + 20 + " TWD2," + coords[rows][2]);
            Bukkit.dispatchCommand(console, "mm m s Susurrador " + 20 + " TWD2," + coords[rows][3]);
        }
    }

    public void Peste(int rows) {
        Bukkit.dispatchCommand(console, "mm m s Peste1 " + 10 + " TWD2," + coords[rows][0]);
        Bukkit.dispatchCommand(console, "mm m s Peste2 " + 5 + " TWD2," + coords[rows][1]);
        Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 20 + " TWD2," + coords[rows][2]);
        Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 30 + " TWD2," + coords[rows][3]);
    }

    public void Susurradores(int rows, String tipo) {
        if (tipo.equals("single")) {
            Bukkit.dispatchCommand(console, "mm m s Susurrador " + 25 + " TWD2," + coords[rows][0]);
            Bukkit.dispatchCommand(console, "mm m s Susurrador " + 25 + " TWD2," + coords[rows][1]);
            Bukkit.dispatchCommand(console, "mm m s Susurrador " + 25 + " TWD2," + coords[rows][2]);
            Bukkit.dispatchCommand(console, "mm m s Susurrador " + 25 + " TWD2," + coords[rows][3]);


            new BukkitRunnable() {

                @Override
                public void run() {
                    Bukkit.dispatchCommand(console, "mm m s Susurrador " + 25 + " TWD2," + coords[rows][0]);
                    Bukkit.dispatchCommand(console, "mm m s Susurrador " + 25 + " TWD2," + coords[rows][1]);
                    Bukkit.dispatchCommand(console, "mm m s Susurrador2 " + 4 + " TWD2," + coords[rows][2]);
                }

            }.runTaskLater(this.plugin, 600);
        } else {
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 25 + " TWD2," + coords[rows][0]);
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 25 + " TWD2," + coords[rows][3]);
            Bukkit.dispatchCommand(console, "mm m s CaminanteH " + 25 + " TWD2," + coords[rows][2]);
            Bukkit.dispatchCommand(console, "mm m s Camisusu " + 3 + " TWD2," + coords[rows][1]);
            //Bukkit.dispatchCommand(console, "mm m s Beta "+1+" TWD2,"+coords[rows][2]);
        }
    }

    public void Crawler(int rows) {
        Bukkit.dispatchCommand(console, "mm m s Crawler2 " + 25 + " TWD2," + coords[rows][0]);
        Bukkit.dispatchCommand(console, "mm m s Crawler2 " + 25 + " TWD2," + coords[rows][1]);
        Bukkit.dispatchCommand(console, "mm m s Crawler2 " + 25 + " TWD2," + coords[rows][3]);
        //Bukkit.dispatchCommand(console, "mm m s CrawlerKing "+1+" TWD2,"+coords[rows][2]);
    }


    public void announcer(String time) {
        float n;
        if (time.equals("15")) {
            n = (float) 1.0;
        } else {
            n = (float) 1.2;
        }
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld().getName().equals("TWD2")) {
                p.sendMessage(ChatColor.DARK_RED + "ATENCION " + ChatColor.RED + " Una horda llegara a " + ChatColor.YELLOW + this.getBanda() + ChatColor.RED + " en " + time + " minutos");
                p.playSound(p.getLocation(), Sound.RECORD_WAIT, 150, n);
            }
        }
    }

    public void arrived() {
        String coordx = coords[this.id][0];

        int x1 = Integer.parseInt(coordx.substring(0, coordx.indexOf(",")));
        int y1 = Integer.parseInt(coordx.substring(coordx.indexOf(",") + 1, coordx.lastIndexOf(",")));
        int z1 = Integer.parseInt(coordx.substring(coordx.lastIndexOf(",") + 1));
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld().getName().equals("TWD2")) {
                boolean near = isNear(p, x1, y1, z1);
                if (near) {
                    p.playSound(p.getLocation(), Sound.RECORD_MELLOHI, 150, (float) 1.0);
                }
                p.sendMessage(ChatColor.DARK_RED + "ATENCION " + ChatColor.RED + "Una horda ha llegado a " + ChatColor.YELLOW + this.getBanda());
            }


        }
    }

    public boolean isNear(Player p, int x1, int y1, int z1) {
        Location loc = p.getLocation();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        double distance = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2) + Math.pow(z - z1, 2));
        //Esto deberia ser editable en la config
        return distance <= 700;
    }

    public void nearTitle(String coordx, int n) {
        int x1 = Integer.parseInt(coordx.substring(0, coordx.indexOf(",")));
        int y1 = Integer.parseInt(coordx.substring(coordx.indexOf(",") + 1, coordx.lastIndexOf(",")));
        int z1 = Integer.parseInt(coordx.substring(coordx.lastIndexOf(",") + 1));
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (isNear(p, x1, y1, z1)) {
                if (p.getWorld().getName().equals("TWD2")) {
                    p.sendTitle(ChatColor.DARK_RED + "Oleada #" + n, ChatColor.RED + "" + n + "/" + 3, 5, 10, 5);
                }
            }
        }
    }


}

