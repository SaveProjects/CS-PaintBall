package fr.edminecoreteam.cspaintball.game.displayname;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class TabListTeams implements Listener
{
	private static Core core = Core.getInstance();
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) 
	{
        Player p = e.getPlayer();
        refreshTablist(p);
    }
	
	private void refreshTablist(Player p) {
		
		new BukkitRunnable() {
            int t = 0;   
	        public void run() {
	        	
	        	if (!p.isOnline()) { cancel(); }


				if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH))
				{
					if (!core.teams().getDefenser().contains(p) && !core.teams().getAttacker().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(0).getOrderTeam(), Teams.powerToTeam(0).getDisplayName(), Teams.powerToTeam(0).getSuffix());
					}

					if (core.teams().getDefenser().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(3).getOrderTeam(), Teams.powerToTeam(3).getDisplayName(), Teams.powerToTeam(3).getSuffix());
						revealPlayerName(p);
					}
					if (core.teams().getAttacker().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(2).getOrderTeam(), Teams.powerToTeam(2).getDisplayName(), Teams.powerToTeam(2).getSuffix());
						revealPlayerName(p);
					}
				}

				if (core.isState(State.INGAME))
				{
					if (!core.teams().getDefenser().contains(p) && !core.teams().getAttacker().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(1).getOrderTeam(), Teams.powerToTeam(1).getDisplayName(), Teams.powerToTeam(1).getSuffix());
					}

					if (core.teams().getDefenser().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(3).getOrderTeam(), Teams.powerToTeam(3).getDisplayName(), Teams.powerToTeam(3).getSuffix());
						hidePlayerName(p);
					}
					if (core.teams().getAttacker().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(2).getOrderTeam(), Teams.powerToTeam(2).getDisplayName(), Teams.powerToTeam(2).getSuffix());
						hidePlayerName(p);
					}
				}


		        
		        ++t;
                if (t == 50) {
                    run();
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 50L);
	}

	private void hidePlayerName(Player p) {
		p.setCustomNameVisible(false);
		p.setCustomName("");
	}

	private void revealPlayerName(Player p) {
		p.setCustomNameVisible(true);
		p.setCustomName(p.getName());
	}
}
