package hklee.pluginmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MyListener implements Listener {
    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e){
        if(e.getInventory().getName().startsWith("Plugin List")){
            e.setCancelled(true);
            if(e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)){
                return;
            }
            if(e.getClickedInventory().getItem(e.getSlot()) == null){
                return;
            }
            Player p = (Player)e.getWhoClicked();
            ItemStack i = e.getCurrentItem();
            if(i.getItemMeta().getDisplayName().startsWith(ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "정상" + ChatColor.LIGHT_PURPLE + "]")&i.getType().equals(Material.GREEN_GLAZED_TERRACOTTA)){
                if(e.isShiftClick()){
                    Plugin Pl = Bukkit.getPluginManager().getPlugin(i.getItemMeta().getDisplayName().split("]" + ChatColor.AQUA)[1]);
                    if (Pl == null) {
                        p.sendRawMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 리로드 하는데 실패했습니다 : 플러그인을 찾을 수 없습니다!");
                        return;
                    }
                    else if(!Pl.isEnabled()){
                        p.sendRawMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 리로드 하는데 실패했습니다 : 플러그인이 오프라인입니다!");
                        e.setCurrentItem(Util.getPluginInfo(Pl));
                        return;
                    }
                    p.sendRawMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 비활성화 하는중...");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 비활성화 하는중...");
                    Bukkit.getPluginManager().disablePlugin(Pl);
                    p.sendRawMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                    Bukkit.getPluginManager().enablePlugin(Pl);
                    p.sendRawMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 리로드 하는데 성공했습니다!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 리로드 하는데 성공했습니다!");
                    e.setCurrentItem(Util.getPluginInfo(Pl));
                    return;
                }
                else {
                    Plugin Pl = Bukkit.getPluginManager().getPlugin(i.getItemMeta().getDisplayName().split("]" + ChatColor.AQUA)[1]);
                    if (Pl == null) {
                        p.sendRawMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 비활성화 하는데 실패했습니다 : 플러그인을 찾을 수 없습니다!");
                        return;
                    }
                    else if(!Pl.isEnabled()){
                        p.sendRawMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 비활성화 하는데 실패했습니다 : 플러그인이 오프라인입니다!");
                        e.setCurrentItem(Util.getPluginInfo(Pl));
                        return;
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
                                if(Pls[a].isEnabled()) {
                                    p.sendRawMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 비활성화 하는데 실패했습니다 : 이 플러그인에 의존하는 다른 플러그인이 있습니다!");
                                    return;
                                }
                            }
                        }
                    }
                    p.sendRawMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 비활성화 하는중...");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 비활성화 하는중...");
                    Bukkit.getPluginManager().disablePlugin(Pl);
                    p.sendRawMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 비활성화 하는데 성공했습니다!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 비활성화 하는데 성공했습니다!");
                    e.setCurrentItem(Util.getPluginInfo(Pl));
                    return;
                }
            }
            else if(i.getItemMeta().getDisplayName().startsWith(ChatColor.LIGHT_PURPLE + "[" + ChatColor.RED + "비활성화" + ChatColor.LIGHT_PURPLE + "]")&i.getType().equals(Material.RED_GLAZED_TERRACOTTA)){
                Plugin Pl = Bukkit.getPluginManager().getPlugin(i.getItemMeta().getDisplayName().split("]" + ChatColor.AQUA)[1]);
                if(Pl == null){
                    p.sendRawMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 활성화 하는데 실패했습니다 : 플러그인을 찾을 수 없습니다!");
                    return;
                }
                else if(Pl.isEnabled()){
                    p.sendRawMessage(ChatColor.RED + "플러그인 " + Pl.getName() + "을 활성화 하는데 실패했습니다 : 플러그인이 온라인입니다!");
                    e.setCurrentItem(Util.getPluginInfo(Pl));
                    return;
                }
                p.sendRawMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "플러그인 " + Pl.getName() + "을 활성화 하는중...");
                Bukkit.getPluginManager().enablePlugin(Pl);
                p.sendRawMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 활성화 하는데 성공했습니다!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "플러그인 " + Pl.getName() + "을 활성화 하는데 성공했습니다!");
                e.setCurrentItem(Util.getPluginInfo(Pl));
                return;
            }
            else if(i.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "이전")&i.getType().equals(Material.REDSTONE_TORCH_ON)){
                int ind = Integer.parseInt(e.getClickedInventory().getName().split(":")[1]);
                if(!(ind < 2)){
                    Inventory Inv = Util.getPluginList(ind - 2);
                    if(Inv != null){
                        p.openInventory(Inv);
                    }
                }
            }
            else if(i.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "다음")&i.getType().equals(Material.REDSTONE_TORCH_ON)){
                int ind = Integer.parseInt(e.getClickedInventory().getName().split(":")[1]);
                Inventory Inv = Util.getPluginList(ind);
                if(Inv != null){
                    p.openInventory(Inv);
                }
            }
        }
    }
}
