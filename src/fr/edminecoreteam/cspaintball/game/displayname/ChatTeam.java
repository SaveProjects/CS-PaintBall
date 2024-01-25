package fr.edminecoreteam.cspaintball.game.displayname;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatTeam implements Listener
{

	private static Core core = Core.getInstance();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		if (core.isState(State.WAITING) || core.isState(State.STARTING) || core.isState(State.FINISH))
		{
			e.setCancelled(true);
			if (!core.teams().getDefenser().contains(p) && !core.teams().getAttacker().contains(p))
			{
				core.getServer().broadcastMessage("§7" + p.getName() + " §8» §f" + e.getMessage());
			}

			if (core.teams().getDefenser().contains(p))
			{
				core.getServer().broadcastMessage("§9§lDEF. §9" + p.getName() + " §8» §f" + e.getMessage());
			}
			if (core.teams().getAttacker().contains(p))
			{
				core.getServer().broadcastMessage("§c§lATTAQ. §c" + p.getName() + " §8» §f" + e.getMessage());
			}
		}

		if (core.isState(State.INGAME))
		{
			if (!core.teams().getDefenser().contains(p) && !core.teams().getAttacker().contains(p))
			{
				e.setCancelled(true);
				for (Player pls : core.getServer().getOnlinePlayers())
				{
					if (!core.teams().getDefenser().contains(pls) && !core.teams().getAttacker().contains(pls))
					{
						pls.sendMessage("§8§lSPEC. §7" + p.getName() + " §8» §f" + e.getMessage());
					}
				}
			}
			if (core.teams().getAttackerDeath().contains(p))
			{
				e.setCancelled(true);
				String message = e.getMessage();
				if (!message.isEmpty())
				{
					if (message.startsWith("!") || message.startsWith("@"))
					{
						message = message.substring(1);
						core.getServer().broadcastMessage("§7§lGÉNÉRAL §f┃ §8§lMORT(E) §f┃ §c§lATTAQ. §c" + p.getName() + " §8» §f" + e.getMessage());
					}
					else
					{
						for (Player pls : core.teams().getAttacker())
						{
							pls.sendMessage("§7§lÉQUIPE §f┃ §8§lMORT(E) §f┃ §c§lATTAQ. §c" + p.getName() + " §8» §f" + e.getMessage());
						}
					}
					return;
				}
			}
			if (core.teams().getDefenserDeath().contains(p))
			{
				e.setCancelled(true);
				String message = e.getMessage();
				if (!message.isEmpty())
				{
					if (message.startsWith("!") || message.startsWith("@"))
					{
						message = message.substring(1);
						core.getServer().broadcastMessage("§7§lGÉNÉRAL §f┃ §8§lMORT(E) §f┃ §9§lDEF. §9" + p.getName() + " §8» §f" + e.getMessage());
					}
					else
					{
						for (Player pls : core.teams().getDefenser())
						{
							pls.sendMessage("§7§lÉQUIPE §f┃ §8§lMORT(E) §f┃ §9§lDEF. §9" + p.getName() + " §8» §f" + e.getMessage());
						}
					}
					return;
				}
			}

			if (core.teams().getDefenser().contains(p))
			{
				e.setCancelled(true);
				String message = e.getMessage();
				if (!message.isEmpty())
				{
					if (message.startsWith("!") || message.startsWith("@"))
					{
						message = message.substring(1);
						core.getServer().broadcastMessage("§7§lGÉNÉRAL §f┃ §9§lDEF. §9" + p.getName() + " §8» §f" + e.getMessage());
					}
					else
					{
						for (Player pls : core.teams().getDefenser())
						{
							pls.sendMessage("§7§lÉQUIPE §f┃ §9§lDEF. §9" + p.getName() + " §8» §f" + e.getMessage());
						}
					}
				}
			}
			if (core.teams().getAttacker().contains(p))
			{
				e.setCancelled(true);
				String message = e.getMessage();
				if (!message.isEmpty())
				{
					if (message.startsWith("!") || message.startsWith("@"))
					{
						message = message.substring(1);
						core.getServer().broadcastMessage("§7§lGÉNÉRAL §f┃ §c§lATTAQ. §c" + p.getName() + " §8» §f" + e.getMessage());
					}
					else
					{
						for (Player pls : core.teams().getAttacker())
						{
							pls.sendMessage("§7§lÉQUIPE §f┃ §c§lATTAQ. §c" + p.getName() + " §8» §f" + e.getMessage());
						}
					}
				}
			}
		}
	}
}
