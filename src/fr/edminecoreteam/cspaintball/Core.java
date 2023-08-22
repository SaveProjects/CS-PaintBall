package fr.edminecoreteam.cspaintball;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin
{

    private static Core instance;

    private static Plugin plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Core getInstance() { return Core.instance; }

    public static Plugin getPlugin() { return Core.plugin; }
}
