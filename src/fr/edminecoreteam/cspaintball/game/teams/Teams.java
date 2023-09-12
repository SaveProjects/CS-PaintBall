package fr.edminecoreteam.cspaintball.game.teams;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Teams
{
    private final List<Player> attacker;
    private final List<Player> defenser;
    private static Core core = Core.getInstance();

    public Teams()
    {
        this.attacker = new ArrayList<Player>();
        this.defenser = new ArrayList<Player>();
    }

    public void joinTeam(Player p, String team)
    {
        if (team.equalsIgnoreCase("attacker") || team.equalsIgnoreCase("defenser"))
        {
            if (team.equalsIgnoreCase("attacker"))
            {
                if (attacker.contains(p)) { return; }

                if (defenser.contains(p)) { defenser.remove(p); }

                attacker.add(p);
                p.sendMessage("§f§lPaintBall §8» §7Vous avez rejoint l'équipe des §cAttaquants§7.");
                return;
            }
            if (team.equalsIgnoreCase("defenser"))
            {
                if (defenser.contains(p)) { return; }

                if (attacker.contains(p)) { attacker.remove(p); }

                defenser.add(p);
                p.sendMessage("§f§lPaintBall §8» §7Vous avez rejoint l'équipe des §9Défenseurs§7.");
                return;
            }
        }
        else
        {
            System.out.println("CS PaintBall error, use attacker or defenser.");
            return;
        }
    }

    public void joinRandomTeam(Player p)
    {
        if (attacker.contains(p)) { attacker.remove(p); }
        if (defenser.contains(p)) { defenser.remove(p); }

        if (attacker.size() < core.getConfig().getInt("teams.attacker.players"))
        {
            attacker.add(p);
            p.sendMessage("§f§lPaintBall §8» §7Vous avez rejoint l'équipe des §cAttaquants§7.");
            return;
        }
        if (defenser.size() < core.getConfig().getInt("teams.defenser.players"))
        {
            defenser.add(p);
            p.sendMessage("§f§lPaintBall §8» §7Vous avez rejoint l'équipe des §9Défenseurs§7.");
            return;
        }
    }

    public void leaveTeam(Player p)
    {
        if (!attacker.contains(p) && !defenser.contains(p)) { return; }

        if (attacker.contains(p))
        {
            attacker.remove(p);
            //p.sendMessage("§f§lPaintBall §8» §7Vous avez quitté l'équipe des §cAttaquants§7.");
            return;
        }
        if (defenser.contains(p))
        {
            defenser.remove(p);
            //p.sendMessage("§f§lPaintBall §8» §7Vous avez quitté l'équipe des §9Défenseurs§7.");
            return;
        }
    }

    public List<Player> getTeam(Player p)
    {
        if (!attacker.contains(p) && !defenser.contains(p)) { return null; }

        if (attacker.contains(p))
        {;
            return this.attacker;
        }
        if (defenser.contains(p))
        {
            return this.defenser;
        }

        return null;
    }

    public List<Player> getAttacker()
    {
        return this.attacker;
    }

    public List<Player> getDefenser() { return this.defenser; }
}
