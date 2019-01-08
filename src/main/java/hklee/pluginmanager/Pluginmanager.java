package hklee.pluginmanager;

import org.bukkit.plugin.java.JavaPlugin;

public final class Pluginmanager extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("pm").setExecutor(new MyCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
