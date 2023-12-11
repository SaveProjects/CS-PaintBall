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


				if (core.isState(State.WAITING) || core.isState(State.STARTING))
				{
					if (!core.teams().getDefenser().contains(p) && !core.teams().getAttacker().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(0).getOrderTeam(), Teams.powerToTeam(0).getDisplayName(), Teams.powerToTeam(0).getSuffix());
					}

					if (core.teams().getDefenser().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(3).getOrderTeam(), Teams.powerToTeam(3).getDisplayName(), Teams.powerToTeam(3).getSuffix());
					}
					if (core.teams().getAttacker().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(2).getOrderTeam(), Teams.powerToTeam(2).getDisplayName(), Teams.powerToTeam(2).getSuffix());
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
						makePlayerNameInvisible(p);
					}
					if (core.teams().getAttacker().contains(p))
					{
						TeamsTagsManager.setNameTag(p, Teams.powerToTeam(2).getOrderTeam(), Teams.powerToTeam(2).getDisplayName(), Teams.powerToTeam(2).getSuffix());
						makePlayerNameInvisible(p);
					}
				}


		        
		        ++t;
                if (t == 50) {
                    run();
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 50L);
	}

	private void makePlayerNameInvisible(Player p) {
		String invisibleName = "‌‌"; // Ces caractères sont des caractères spéciaux, veuillez les copier directement.

		// Changer le pseudo du joueur
		p.setDisplayName(invisibleName);
	}
}
