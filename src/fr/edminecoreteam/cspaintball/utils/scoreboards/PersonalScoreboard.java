package fr.edminecoreteam.cspaintball.utils.scoreboards;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {
	
	private static Core core = Core.getInstance();
    private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
 
    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "Edmine");
 
        reloadData();
        objectiveSign.addReceiver(player);
    }
    
    public String convertTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02dm %02ds", minutes, seconds);
    }
    
 
    public void reloadData(){}
 
    public void setLines(String ip){

        objectiveSign.setDisplayName("§8● §6§lPaint-Ball §8●");
        objectiveSign.setLine(0, "§8");
        objectiveSign.setLine(1, " §f➡ §dInformations:");
    		if (core.isState(State.WAITING) || core.isState(State.STARTING))
    		{
                if (core.isState(State.WAITING))
                {
                    objectiveSign.setLine(2, "  §8• §7Statut: §fAttente...");

                    objectiveSign.setLine(7, "§2");
                    objectiveSign.setLine(8, "  §8• §7Carte: §e" + core.world);
                    objectiveSign.setLine(9, "§3");
                    objectiveSign.setLine(10, " §8➡ " + ip);
                }
                else if (core.isState(State.STARTING))
                {
                    objectiveSign.setLine(2, "  §8• §7Statut: Lancement");

                    objectiveSign.setLine(7, "§2");
                    objectiveSign.setLine(8, "  §8• §7Début dans: §a" + core.timers + "§as");
                    objectiveSign.setLine(9, "  §8• §7Carte: §e"+ core.world);
                    objectiveSign.setLine(10, "§3");
                    objectiveSign.setLine(11, " §8➡ " + ip);
                }
                objectiveSign.setLine(3, "§1");
                objectiveSign.setLine(4, "  §8• §7Joueurs: §a" + core.getServer().getOnlinePlayers().size() + "/§a" + core.getMaxplayers());
    			if (core.getConfig().getString("type").equalsIgnoreCase("ranked"))
                {
                    objectiveSign.setLine(5, "  §8• §7Mode: §6Compétitif");
                }
                else if (core.getConfig().getString("type").equalsIgnoreCase("unranked"))
                {
                    objectiveSign.setLine(5, "  §8• §7Mode: §eNon-Compétitif");
                }

                if (core.getConfig().getString("time").equalsIgnoreCase("long"))
                {
                    objectiveSign.setLine(6, "  §8• §7Durée: §bLongue");
                }
                else if (core.getConfig().getString("time").equalsIgnoreCase("short"))
                {
                    objectiveSign.setLine(6, "  §8• §7Durée: §bCourte");
                }
    		}
            if (core.isState(State.INGAME) || core.isState(State.FINISH))
    		{
                if (core.teams().getAttacker().contains(player))
                {
                    objectiveSign.setLine(2, "  §8• §7Score §cAttaquants §7(vous): §a" + core.pointsManager().getAttackerPoints());
                    objectiveSign.setLine(3, "  §8• §7Score §9Défenseurs§7: §a" + core.pointsManager().getDefenserPoints());
                }
                else if (core.teams().getDefenser().contains(player))
                {
                    objectiveSign.setLine(2, "  §8• §7Score §cAttaquants§7: §a" + core.pointsManager().getAttackerPoints());
                    objectiveSign.setLine(3, "  §8• §7Score §9Défenseurs §7(vous): §a" + core.pointsManager().getDefenserPoints());
                }
                else if (!core.teams().getAttacker().contains(player) || !core.teams().getAttacker().contains(player))
                {
                    objectiveSign.setLine(2, "  §8• §7Score §cAttaquants§7: §a" + core.pointsManager().getAttackerPoints());
                    objectiveSign.setLine(3, "  §8• §7Score §9Défenseurs§7: §a" + core.pointsManager().getDefenserPoints());
                }

                int getMaxRound = core.getConfig().getInt("timers.rounds-short") * 2;
                objectiveSign.setLine(4, "  §8• §7Manche: §f" + core.roundManager().getRound() + "§8/§f" + getMaxRound);
                objectiveSign.setLine(5, "§2");
                objectiveSign.setLine(6, "  §8• §7Bombe: §b");
                objectiveSign.setLine(7, "  §8• §7Temps Restant: §b" + core.timers);
                objectiveSign.setLine(8, "  §8• §7Carte: §e" + core.world);
                int getAlive = core.teams().getTeam(player).size() - core.teams().getDeathTeam(player).size();
                objectiveSign.setLine(9, "  §8• §7En vie(s) (équipe): §e" + getAlive);
                objectiveSign.setLine(10, "§3");
                objectiveSign.setLine(11, " §8➡ " + ip);
    		}
 
        objectiveSign.updateLines();
    }
 
    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }

    public void fakeLogout(){
        objectiveSign.removeReceiver(player);
    }
}