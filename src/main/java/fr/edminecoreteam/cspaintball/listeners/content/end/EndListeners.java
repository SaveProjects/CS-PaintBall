package fr.edminecoreteam.cspaintball.listeners.content.end;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.content.end.tasks.AutoStop;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EndListeners
{

    private final static Core core = Core.getInstance();
    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));

    public void end()
    {
        int attackerScore = core.pointsManager().getAttackerPoints();
        int defenserScore = core.pointsManager().getDefenserPoints();

        for (Player pls : core.getServer().getOnlinePlayers())
        {
            pls.getEquipment().setHelmet(null);
            pls.getEquipment().setChestplate(null);
            pls.getEquipment().setLeggings(null);
            pls.getEquipment().setBoots(null);
            pls.getInventory().clear();
            pls.setHealth(20);
            pls.setFoodLevel(20);
            pls.teleport(spawn);
            pls.setGameMode(GameMode.ADVENTURE);
        }

        core.getServer().broadcastMessage("");
        core.getServer().broadcastMessage("  §d§lCompte rendu:");
        core.getServer().broadcastMessage("   §7• §cAttaquants§7: §f" + attackerScore);
        core.getServer().broadcastMessage("   §7• §9Défenseurs§7: §f" + defenserScore);
        if (core.getConfig().getString("time").equalsIgnoreCase("short"))
        {
            core.getServer().broadcastMessage("   §7• §7Durée§7: §bCourte");
        }
        if (core.getConfig().getString("time").equalsIgnoreCase("long"))
        {
            core.getServer().broadcastMessage("   §7• §7Durée§7: §bLongue");
        }

        if (core.getConfig().getString("type").equalsIgnoreCase("unranked"))
        {
            core.getServer().broadcastMessage("   §7• §7Mode§7: §6Non-Compétitif");
        }
        if (core.getConfig().getString("type").equalsIgnoreCase("ranked"))
        {
            core.getServer().broadcastMessage("   §7• §7Mode§7: §6Compétitif");
        }

        if (attackerScore > defenserScore)
        {
            core.getServer().broadcastMessage("   §7• §7Vitoire: §cAttaquants");
            for (Player pls : core.teams().getAttacker())
            {
                pls.sendTitle("§e§lVictoire !", "§7Vous avez remporté la partie !");
            }
            for (Player pls : core.teams().getDefenser())
            {
                pls.sendTitle("§c§lPerdu...", "§7Peut-être une prochaine fois !");
            }
        }

        if (defenserScore > attackerScore)
        {
            core.getServer().broadcastMessage("   §7• §7Vitoire: §9Défenseurs");
            for (Player pls : core.teams().getDefenser())
            {
                pls.sendTitle("§e§lVictoire !", "§7Vous avez remporté la partie !");
            }
            for (Player pls : core.teams().getAttacker())
            {
                pls.sendTitle("§c§lPerdu...", "§7Peut-être une prochaine fois !");
            }
        }

        if (defenserScore == attackerScore)
        {
            core.getServer().broadcastMessage("   §7• §7Vitoire: §fÉgalité");
            for (Player pls : core.teams().getDefenser())
            {
                pls.sendTitle("§f§lÉgalité..", "§7Peut-être une prochaine fois !");
            }
            for (Player pls : core.teams().getAttacker())
            {
                pls.sendTitle("§f§lÉgalité..", "§7Peut-être une prochaine fois !");
            }
        }

        core.getServer().broadcastMessage("");
        core.getServer().broadcastMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
        core.getServer().broadcastMessage("");

        AutoStop autoStop = new AutoStop(core);
        autoStop.runTaskTimer((Plugin) core, 0L, 20L);
    }

}
