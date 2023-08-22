package fr.edminecoreteam.cspaintball;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin
{

    private static Core instance;
    private State state;

    public MySQL database;

    private static Plugin plugin;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        MySQLConnect();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void MySQLConnect() {
        instance = this;
        (this.database = new MySQL("jdbc:mysql://", this.getConfig().getString("mysql.host"), this.getConfig().getString("mysql.database"), this.getConfig().getString("mysql.user"), this.getConfig().getString("mysql.password"))).connexion();
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isState(State state) {
        return this.state == state;
    }

    public static Core getInstance() { return Core.instance; }

    public static Plugin getPlugin() { return Core.plugin; }
}
