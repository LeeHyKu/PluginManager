package hklee.pluginmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
            if(args[0].equals("li")|args[0].equals("list")){
                Plugin[] list = Bukkit.getPluginManager().getPlugins();
                if(args.length > 1){
                    int i = 0;
                    try {
                        i = Integer.parseInt(args[1]);
                        if(i <= 0){
                            sender.sendMessage(ChatColor.RED + "페이지를 찾을 수 없습니다.");
                            return false;
                        }
                        i--;
                        i = i * 10;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    if(list.length < i){
                        sender.sendMessage(ChatColor.RED + "페이지를 찾을 수 없습니다.");
                        return false;
                    }
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    for(int a = i; a < 10 + i; a++){
                        if (a < list.length) {
                            sender.sendMessage(ChatColor.YELLOW + "Name:" + ChatColor.WHITE + list[a].getName());
                        }
                    }
                    double b = (double)list.length/10;
                    int c = (int)b;
                    if(b > (double)c){
                        c++;
                    }
                    sender.sendMessage(ChatColor.WHITE + String.valueOf(c) + "페이지 중" + i +"페이지");
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                }
                else{
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    for(int a = 0; a < 10; a++) {
                        if (a < list.length) {
                            sender.sendMessage(ChatColor.YELLOW + "Name:" + ChatColor.WHITE + list[a].getName());
                        }
                    }
                    double b = (double)list.length/10;
                    int c = (int)b;
                    if(b > (double)c){
                        c++;
                    }
                    sender.sendMessage(ChatColor.WHITE + String.valueOf(c) + "페이지 중 1페이지");
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                }
                return true;
            }
            else if(args[0].equals("in")|args[0].equals("info")){
                if(args.length > 1) {
                    String a = args[1];
                    if(args.length > 2) {
                        for (int i = 2; i < args.length + 1; i++) {
                            a += " " + args[i];
                        }
                    }
                    Plugin Pl = Bukkit.getPluginManager().getPlugin(a);
                    if (Pl == null) {
                        sender.sendMessage(ChatColor.RED + "플러그인을 찾을 수 없습니다");
                        return false;
                    }
                    String en;
                    if (Pl.isEnabled()) {
                        en = ChatColor.GREEN + "정상";
                    } else {
                        en = ChatColor.RED + "멈춤";
                    }
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    sender.sendMessage(ChatColor.AQUA + "이름:" + ChatColor.WHITE + Pl.getName());
                    sender.sendMessage(ChatColor.AQUA + "상태:" + en);
                    if (Pl.getDescription().getDescription() != null) {
                        sender.sendMessage(ChatColor.AQUA + "설명:" + ChatColor.WHITE + Pl.getDescription().getDescription());
                    }
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                }
                else{
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    sender.sendMessage(ChatColor.AQUA + "/pm list [페이지] - 플러그인 목록을 봅니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm info <플러그인 이름> - 플러그인 정보를 봅니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm enable <플러그인 이름/파일 이름> - 플러그인을 활성화 합니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm disable <플러그인 이름> - 플러그인을 비활성화 합니다");
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                }
            }
            else if(
                args[0].equals("en")|
                args[0].equals("enable")
            ){
                if(args.length > 1){
                    String a = args[1];
                    if(args.length > 2) {
                        for (int i = 2; i < args.length + 1; i++) {
                            a += " " + args[i];
                        }
                    }
                    Plugin Pl = Bukkit.getPluginManager().getPlugin(a);
                    if(Pl == null){
                        File f = new File("./plugins/" + a + ".jar");
                        if(f.exists()){
                            sender.sendMessage(ChatColor.YELLOW + "외부플러그인을 불러옵니다");
                            try {
                                Bukkit.getPluginManager().loadPlugin(f);
                                sender.sendMessage(ChatColor.AQUA + "플러그인을 불러오는데 성공했습니다");
                            }
                            catch (InvalidPluginException IPE){
                                IPE.printStackTrace();
                                sender.sendMessage(ChatColor.RED + "플러그인을 불러오는데 실패했습니다 : 올바르지 않은 플러그인");
                            }
                            catch (InvalidDescriptionException IDE){
                                IDE.printStackTrace();
                                sender.sendMessage(ChatColor.RED + "플러그인을 불러오는데 실패했습니다 : 올바르지 않은 정보파일");
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                sender.sendMessage(ChatColor.RED + "플러그인을 불러오는데 실패했습니다 : 알 수 없는 오류");
                            }
                        }
                        else{
                            sender.sendMessage(ChatColor.RED + "알수없는 플러그인!");
                        }
                    }
                    else{
                        sender.sendMessage(ChatColor.AQUA + "플러그인 실행중...");
                        Bukkit.getPluginManager().enablePlugin(Pl);
                        sender.sendMessage(ChatColor.AQUA + "플러그인을 불러오는데 성공했습니다");
                    }
                    return true;
                }
                else{
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    sender.sendMessage(ChatColor.AQUA + "/pm list [페이지] - 플러그인 목록을 봅니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm info <플러그인 이름> - 플러그인 정보를 봅니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm enable <플러그인 이름/파일 이름> - 플러그인을 활성화 합니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm disable <플러그인 이름> - 플러그인을 비활성화 합니다");
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                }
            }
            else if(
                    args[0].equals("di")|
                    args[0].equals("disable")
            ){
                if(args.length > 1){
                    String a = args[1];
                    if (args.length > 2) {
                        for (int i = 2; i < args.length + 1; i++) {
                            a += " " + args[i];
                        }
                    }
                    Plugin Pl = Bukkit.getPluginManager().getPlugin(a);
                    if(Pl == null){
                        sender.sendMessage(ChatColor.RED + "알 수 없는 플러그인!");
                        return false;
                    }

                    sender.sendMessage(ChatColor.AQUA + "플러그인 비활성화중...");
                    Bukkit.getPluginManager().disablePlugin(Pl);
                    sender.sendMessage(ChatColor.AQUA + "플러그인을 비활성화하는데 성공했습니다");
                    return true;
                }
                else{
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                    sender.sendMessage(ChatColor.AQUA + "/pm list [페이지] - 플러그인 목록을 봅니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm info <플러그인 이름> - 플러그인 정보를 봅니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm enable <플러그인 이름/파일 이름> - 플러그인을 활성화 합니다");
                    sender.sendMessage(ChatColor.AQUA + "/pm disable <플러그인 이름> - 플러그인을 비활성화 합니다");
                    sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
                }
            }
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
            sender.sendMessage(ChatColor.AQUA + "/pm list [페이지] - 플러그인 목록을 봅니다");
            sender.sendMessage(ChatColor.AQUA + "/pm info <플러그인 이름> - 플러그인 정보를 봅니다");
            sender.sendMessage(ChatColor.AQUA + "/pm enable <플러그인 이름/파일 이름> - 플러그인을 활성화 합니다");
            sender.sendMessage(ChatColor.AQUA + "/pm disable <플러그인 이름> - 플러그인을 비활성화 합니다");
            sender.sendMessage(ChatColor.YELLOW + "---------------------------------------------");
        }
        return false;
    }
}
