package hklee.pluginmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Util {
    public static Inventory getPluginList(){
        return  getPluginList(0);
    }
    public static Inventory getPluginList(int i){
        Inventory Inv = Bukkit.createInventory(null,54,"Plugin List Page:" + (i + 1) );
        Plugin[] Pl = Bukkit.getPluginManager().getPlugins();
        int a = 45 * i;
        for(int b = 0; b < 45; b++){
            if(a < Pl.length) {
                Inv.addItem(getPluginInfo(Pl[a]));
            }
            a++;
        }
        ItemStack Pr = new ItemStack(Material.REDSTONE_TORCH_ON,1);
        ItemMeta Pri = Pr.getItemMeta();
        Pri.setDisplayName(ChatColor.GREEN + "이전");
        Pr.setItemMeta(Pri);
        ItemStack Ne = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
        ItemMeta Nei = Ne.getItemMeta();
        Nei.setDisplayName(ChatColor.GREEN + "다음");
        Ne.setItemMeta(Nei);
        Inv.setItem(48,Pr);
        Inv.setItem(50,Ne);
        if(Inv.getItem(1) == null){
            return null;
        }
        else {
            return Inv;
        }
    }

    public static ItemStack getPluginInfo(Plugin Pl) {
        Material mat = Pl.isEnabled() ?
                Material.GREEN_GLAZED_TERRACOTTA:
                Material.RED_GLAZED_TERRACOTTA;
        ItemStack I = new ItemStack(mat);
        ArrayList<String> Lore = new ArrayList<>();
        if(Pl.getDescription().getVersion() != null){
            Lore.add(ChatColor.AQUA + "버전: " + ChatColor.GREEN + Pl.getDescription().getVersion());
        }
        if(Pl.getDescription().getAuthors() != null){
            Lore.add(ChatColor.AQUA + "제작자: " + ChatColor.YELLOW + Pl.getDescription().getAuthors());
        }
        if(Pl.getDescription().getWebsite() != null){
            Lore.add(ChatColor.AQUA + "웹사이트: " + ChatColor.BLUE + Pl.getDescription().getWebsite());
        }
        if(Pl.getDescription().getDescription() != null){
            Lore.add(ChatColor.AQUA + "설명:" + ChatColor.WHITE + Pl.getDescription().getDescription());
        }
        ItemMeta i = I.getItemMeta();
        i.setDisplayName(
                Pl.isEnabled() ?
                    ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "정상" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.AQUA + Pl.getName() :
                    ChatColor.LIGHT_PURPLE + "[" + ChatColor.RED + "비활성화" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.AQUA + Pl.getName()
        );
        i.setLore(Lore);
        I.setItemMeta(i);
        return I;
    }
}