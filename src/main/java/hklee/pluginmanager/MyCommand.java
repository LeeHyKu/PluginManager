package hklee.pluginmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class MyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()){
            sender.sendMessage(ChatColor.RED + "권한이 부족합니다!");
            return false;
        }

        if(args.length > 0){
            switch(args[0]) {
                case "li":
                case "list":
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        Inventory inv = Util.getPluginList();
                        if (inv != null) {
                            try {
                                p.openInventory(inv);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "콘솔은 사용할수 없습니다 pl을 이용해주세요");
                    }
                    break;

                case "en":
                case "enable":
                    if (args.length > 1) {
                        String a = args[1];
                        if (args.length > 2) {
                            for (int i = 2; i < args.length + 1; i++) {
                                a += " " + args[i];
                            }
                        }
                        Plugin Pl = Bukkit.getPluginManager().getPlugin(a);
                        if (Pl == null) {
                            File f = new File("./plugins/" + a + ".jar");
                            if (f.exists()) {
                                sender.sendMessage(ChatColor.YELLOW + "외부플러그인을 불러옵니다");
                                try {
                                    sender.sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                                    Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(f));
                                    sender.sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 활성화 하는데 성공했습니다!");
                                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 활성화 하는데 성공했습니다!");
                                } catch (InvalidPluginException IPE) {
                                    IPE.printStackTrace();
                                    sender.sendMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 불러오는데 실패했습니다 : 올바르지 않은 플러그인");
                                } catch (InvalidDescriptionException IDE) {
                                    IDE.printStackTrace();
                                    sender.sendMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 불러오는데 실패했습니다 : 올바르지 않은 정보파일");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    sender.sendMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 불러오는데 실패했습니다 : 알 수 없는 오류");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "알수없는 플러그인!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                            Bukkit.getPluginManager().enablePlugin(Pl);
                            sender.sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 활성화 하는데 성공했습니다!");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 활성화 하는데 성공했습니다!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                        sender.sendMessage(ChatColor.AQUA + "/pm list - 플러그인 목록을 봅니다");
                        sender.sendMessage(ChatColor.AQUA + "/pm enable <플러그인 이름/파일 이름> - 플러그인을 활성화 합니다");
                        sender.sendMessage(ChatColor.AQUA + "/pm disable <플러그인 이름> - 플러그인을 비활성화 합니다");
                        sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    }
                    break;

                case "di":
                case "disable":
                    if (args.length > 1) {
                        String b = args[1];
                        if (args.length > 2) {
                            for (int i = 2; i < args.length + 1; i++) {
                                b += " " + args[i];
                            }
                        }
                        Plugin Pl = Bukkit.getPluginManager().getPlugin(b);
                        if (Pl == null) {
                            sender.sendMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 비활성화 하는데 실패했습니다 : 플러그인을 찾을 수 없습니다!");
                            break;
                        } else if (!Pl.isEnabled()) {
                            sender.sendMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 비활성화 하는데 실패했습니다 : 플러그인이 오프라인입니다!");
                            break;
                        }
                        Plugin[] Pls = Bukkit.getPluginManager().getPlugins();
                        for (int a = 0; a < Pls.length; a++) {
                            if (
                                    Pls[a].getDescription().getDepend() != null |
                                            Pls[a].getDescription().getSoftDepend() != null
                            ) {
                                if (
                                        Pls[a].getDescription().getDepend().contains(Pl.getName()) |
                                                Pls[a].getDescription().getSoftDepend().contains(Pl.getName())
                                ) {
                                    sender.sendMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 비활성화 하는데 실패했습니다 : 이 플러그인에 의존하는 다른 플러그인이 있습니다!");
                                    break;
                                }
                            }
                        }
                        sender.sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 비활성화 하는중...");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 비활성화 하는중...");
                        Bukkit.getPluginManager().disablePlugin(Pl);
                        sender.sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 비활성화 하는데 성공했습니다!");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 비활성화 하는데 성공했습니다!");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                        sender.sendMessage(ChatColor.AQUA + "/pm list - 플러그인 목록을 봅니다");
                        sender.sendMessage(ChatColor.AQUA + "/pm enable <플러그인 이름/파일 이름> - 플러그인을 활성화 합니다");
                        sender.sendMessage(ChatColor.AQUA + "/pm disable <플러그인 이름> - 플러그인을 비활성화 합니다");
                        sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    }
                break;
            }
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
            sender.sendMessage(ChatColor.AQUA + "/pm list - 플러그인 목록을 봅니다");
            sender.sendMessage(ChatColor.AQUA + "/pm enable <플러그인 이름/파일 이름> - 플러그인을 활성화 합니다");
            sender.sendMessage(ChatColor.AQUA + "/pm disable <플러그인 이름> - 플러그인을 비활성화 합니다");
            sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
        }
        return false;
    }
}
