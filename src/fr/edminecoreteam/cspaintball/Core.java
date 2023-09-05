package fr.edminecoreteam.cspaintball;

import fr.edminecoreteam.cspaintball.game.guis.BuyMenu;
import fr.edminecoreteam.cspaintball.game.teams.Teams;
import fr.edminecoreteam.cspaintball.listeners.connection.JoinEvent;
import fr.edminecoreteam.cspaintball.listeners.connection.LeaveEvent;
import fr.edminecoreteam.cspaintball.waiting.WaitingListeners;
import fr.edminecoreteam.cspaintball.waiting.guis.ChooseTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin
{

    private static Core instance;
    private State state;
    public MySQL database;
    private Teams teams;

    private int maxplayers;

    private static Plugin plugin;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        MySQLConnect();
        loadListeners();

        setState(State.WAITING);
        maxplayers = getConfig().getInt("teams.attacker.players") + getConfig().getInt("teams.defender.players");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void MySQLConnect() {
        instance = this;
        (this.database = new MySQL("jdbc:mysql://", this.getConfig().getString("mysql.host"), this.getConfig().getString("mysql.database"), this.getConfig().getString("mysql.user"), this.getConfig().getString("mysql.password"))).connexion();
    }

    private void loadListeners()
    {
        teams = new Teams();
        Bukkit.getPluginManager().registerEvents((Listener) new JoinEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new LeaveEvent(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new WaitingListeners(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new ChooseTeam(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new BuyMenu(), (Plugin)this);
        this.getCommand("buymenu").setExecutor((CommandExecutor) new TestCommand());
    }

    public Teams teams() { return this.teams; }

    public int getMaxplayers() { return this.maxplayers; }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isState(State state) {
        return this.state == state;
    }

    public static Core getInstance() { return Core.instance; }

    public static Plugin getPlugin() { return Core.plugin; }
}
