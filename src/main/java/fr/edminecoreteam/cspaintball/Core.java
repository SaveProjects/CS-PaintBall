package fr.edminecoreteam.cspaintball;

import fr.edminecoreteam.api.EdmineAPISpigot;
import fr.edminecoreteam.cspaintball.content.game.guis.*;
import fr.edminecoreteam.cspaintball.content.game.weapons.lourdes.M249;
import fr.edminecoreteam.cspaintball.content.game.weapons.lourdes.NEGEV;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.MP7;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.MP9;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.P90;
import fr.edminecoreteam.cspaintball.content.game.weapons.pompes.MAG7;
import fr.edminecoreteam.cspaintball.listeners.content.game.GameListeners;
import fr.edminecoreteam.cspaintball.listeners.content.game.SpawnListeners;
import fr.edminecoreteam.cspaintball.content.game.displayname.ChatTeam;
import fr.edminecoreteam.cspaintball.content.game.displayname.TabListTeams;
import fr.edminecoreteam.cspaintball.content.game.pauses.Pauses;
import fr.edminecoreteam.cspaintball.content.game.points.PointsManager;
import fr.edminecoreteam.cspaintball.content.game.rounds.RoundInfo;
import fr.edminecoreteam.cspaintball.content.game.rounds.RoundManager;
import fr.edminecoreteam.cspaintball.content.game.spec.AttackerSpec;
import fr.edminecoreteam.cspaintball.content.game.spec.DefenserSpec;
import fr.edminecoreteam.cspaintball.content.game.teams.Teams;
import fr.edminecoreteam.cspaintball.content.game.weapons.pistolets.*;
import fr.edminecoreteam.cspaintball.utils.minecraft.LoadHolograms;
import fr.edminecoreteam.cspaintball.utils.minecraft.worlds.LoadWorld;
import fr.edminecoreteam.cspaintball.content.game.weapons.WeaponsMap;
import fr.edminecoreteam.cspaintball.content.game.weapons.WeaponsSettings;
import fr.edminecoreteam.cspaintball.content.game.weapons.bombe.Bombe;
import fr.edminecoreteam.cspaintball.content.game.weapons.pm.MAC10;
import fr.edminecoreteam.cspaintball.content.game.weapons.pompes.NOVA;
import fr.edminecoreteam.cspaintball.content.game.weapons.pompes.XM1014;
import fr.edminecoreteam.cspaintball.listeners.connection.JoinEvent;
import fr.edminecoreteam.cspaintball.listeners.connection.LeaveEvent;
import fr.edminecoreteam.cspaintball.utils.minecraft.TitleBuilder;
import fr.edminecoreteam.cspaintball.utils.scoreboards.JoinScoreboardEvent;
import fr.edminecoreteam.cspaintball.utils.scoreboards.LeaveScoreboardEvent;
import fr.edminecoreteam.cspaintball.utils.scoreboards.ScoreboardManager;
import fr.edminecoreteam.cspaintball.utils.scoreboards.WorldChangeScoreboardEvent;
import fr.edminecoreteam.cspaintball.listeners.content.waiting.WaitingListeners;
import fr.edminecoreteam.cspaintball.content.waiting.guis.ChooseTeam;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
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
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private Teams teams;
    private Pauses pauses;
    private WeaponsMap weaponsMap;
    public TitleBuilder title;
    private List<String> playersInGame;
    private PointsManager pointsManager;
    private RoundManager roundManager;
    public String world;

    private int maxplayers;
    public boolean isForceStart = false;

    public int timers;

    private DefenserSpec defenserSpec;
    private AttackerSpec attackerSpec;

    private SpawnListeners spawnListeners;

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
        loadHolograms();

        //MySQLConnect();

        setState(State.WAITING);
        maxplayers = getConfig().getInt("teams.attacker.players") + getConfig().getInt("teams.defenser.players");
    }

    @Override
    public void onDisable()
    {
        for (World worlds : Bukkit.getWorlds())
        {
            for (ArmorStand stand : worlds.getEntitiesByClass(ArmorStand.class))
            {
                stand.remove();
            }
        }
    }

    private void loadListeners()
    {
        this.teams = new Teams();
        this.pauses = new Pauses();
        this.spawnListeners = new SpawnListeners();
        this.title = new TitleBuilder();
        this.pointsManager = new PointsManager();
        this.roundManager = new RoundManager();
        this.attackerSpec = new AttackerSpec();
        this.defenserSpec = new DefenserSpec();
        Bukkit.getPluginManager().registerEvents((Listener) new JoinEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new LeaveEvent(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new WaitingListeners(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new ChooseTeam(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new GameListeners(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new AttackerSpec(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new DefenserSpec(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new BuyMenu(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new BuyPistolets(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new BuyPompes(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new BuyPMs(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new BuyLourdes(), (Plugin)this);

        Bukkit.getPluginManager().registerEvents((Listener) new TabListTeams(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new ChatTeam(), (Plugin)this);
    }

    private void loadWeapons()
    {
        this.weaponsMap = new WeaponsMap();

        Bukkit.getPluginManager().registerEvents((Listener) new WeaponsSettings(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new Bombe(), (Plugin)this);

        //Pistolets
        Bukkit.getPluginManager().registerEvents((Listener) new USPS(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new GLOCK18(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new BERETTAS(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new P250(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new TEC9(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new DESERTEAGLE(), (Plugin)this);

        //Fufils a pompe
        Bukkit.getPluginManager().registerEvents((Listener) new NOVA(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new XM1014(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new MAG7(), (Plugin)this);

        //PMs
        Bukkit.getPluginManager().registerEvents((Listener) new MAC10(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new MP9(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new MP7(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new P90(), (Plugin)this);

        //Lourdes
        Bukkit.getPluginManager().registerEvents((Listener) new M249(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener) new NEGEV(), (Plugin)this);
    }

    private void ScoreboardManager()
    {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new JoinScoreboardEvent(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveScoreboardEvent(), this);
        Bukkit.getPluginManager().registerEvents(new WorldChangeScoreboardEvent(), this);

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();
    }

    private void loadHolograms()
    {
        LoadHolograms loadHolograms = new LoadHolograms();
        loadHolograms.help();
        loadHolograms.rules();
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
    public Pauses pauses() { return this.pauses; }
    public AttackerSpec attackerSpec() { return this.attackerSpec; }
    public DefenserSpec defenserSpec() { return this.defenserSpec; }

    public SpawnListeners spawnListeners() { return this.spawnListeners; }

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
