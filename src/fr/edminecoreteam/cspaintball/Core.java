package fr.edminecoreteam.cspaintball;

import fr.edminecoreteam.cspaintball.game.guis.BuyMenu;
import fr.edminecoreteam.cspaintball.game.guis.BuyPistolets;
import fr.edminecoreteam.cspaintball.game.points.PointsManager;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.game.rounds.RoundManager;
import fr.edminecoreteam.cspaintball.game.teams.Teams;
import fr.edminecoreteam.cspaintball.game.utils.LoadWorld;
import fr.edminecoreteam.cspaintball.game.weapons.WeaponsMap;
import fr.edminecoreteam.cspaintball.game.weapons.WeaponsSettings;
import fr.edminecoreteam.cspaintball.game.weapons.pistolets.BERETTAS;
import fr.edminecoreteam.cspaintball.game.weapons.pistolets.USPS;
import fr.edminecoreteam.cspaintball.listeners.connection.JoinEvent;
import fr.edminecoreteam.cspaintball.listeners.connection.LeaveEvent;
import fr.edminecoreteam.cspaintball.utils.TitleBuilder;
import fr.edminecoreteam.cspaintball.utils.scoreboards.JoinScoreboardEvent;
import fr.edminecoreteam.cspaintball.utils.scoreboards.LeaveScoreboardEvent;
import fr.edminecoreteam.cspaintball.utils.scoreboards.ScoreboardManager;
import fr.edminecoreteam.cspaintball.waiting.WaitingListeners;
import fr.edminecoreteam.cspaintball.waiting.guis.ChooseTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Core extends JavaPlugin
{

    private static Core instance;
    private State state;
    private RoundInfo roundInfo;
    public MySQL database;
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private Teams teams;
    private WeaponsMap weaponsMap;
    public TitleBuilder title;
    private List<String> playersInGame;
    private PointsManager pointsManager;
    private RoundManager roundManager;
    public String world;

    private int maxplayers;
    public boolean isForceStart = false;

    public int timers;
    public int timers(int i) { this.timers = i; return i; }


    private static Plugin plugin;

    @Override
    public void onEnable() {
        instance = this;
        playersInGame = new ArrayList<String>();
        saveDefaultConfig();
        loadListeners();
        loadGameWorld();
        ScoreboardManager();
        loadWeapons();

        MySQLConnect();

        setState(State.WAITING);
        maxplayers = getConfig().getInt("teams.attacker.players") + getConfig().getInt("teams.defenser.players");
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
        this.teams = new Teams();
        this.title = new TitleBuilder();
        this.pointsManager = new PointsManager();
        this.roundManager = new RoundManager();
        Bukkit.getPluginManager().registerEvents((Listener) new JoinEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new LeaveEvent(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new WaitingListeners(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new ChooseTeam(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new BuyMenu(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new BuyPistolets(), (Plugin)this);
        this.getCommand("buymenu").setExecutor((CommandExecutor) new TestCommand());
    }

    private void loadWeapons()
    {
        this.weaponsMap = new WeaponsMap();

        Bukkit.getPluginManager().registerEvents((Listener) new WeaponsSettings(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new USPS(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new BERETTAS(), (Plugin)this);
    }

    private void ScoreboardManager()
    {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new JoinScoreboardEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveScoreboardEvent(), this);

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();
    }

    private void loadGameWorld()
    {
        String world = LoadWorld.getRandomSubfolderName("gameTemplate/");
        LoadWorld.createGameWorld(world);
        this.world = world;
    }


    public List<String> getPlayersInGame() { return this.playersInGame; }
    public WeaponsMap weaponsMap() { return this.weaponsMap; }

    public RoundManager roundManager() { return this.roundManager; }
    public PointsManager pointsManager() { return this.pointsManager; }
    public Teams teams() { return this.teams; }

    public int getMaxplayers() { return this.maxplayers; }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isState(State state) {
        return this.state == state;
    }

    public void setRoundState(RoundInfo roundInfo) {
        this.roundInfo = roundInfo;
    }

    public boolean isRoundState(RoundInfo roundInfo) {
        return this.roundInfo == roundInfo;
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return this.executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

    public static Core getInstance() { return Core.instance; }

    public static Plugin getPlugin() { return Core.plugin; }
}
