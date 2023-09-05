package fr.edminecoreteam.cspaintball;

import fr.edminecoreteam.cspaintball.game.guis.BuyMenu;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("buymenu"))
            {
                BuyMenu buyMenu = new BuyMenu();
                buyMenu.gui(player);
                player.playSound(player.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }
        return false;
    }
}
