package fr.edminecoreteam.cspaintball.game.pauses;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Pauses
{
    private final List<String> attackerWait;
    private final List<String> defenserWait;

    private boolean isPause;
    private static Core core = Core.getInstance();

    public Pauses()
    {
        this.attackerWait = new ArrayList<String>();
        this.defenserWait = new ArrayList<String>();
        this.isPause = false;
    }

    public List<String> getAttackerWait()
    {
        return this.attackerWait;
    }
    public List<String> getDefenserWait()
    {
        return this.defenserWait;
    }

    public boolean getIsPause()
    {
        return this.isPause;
    }
    public void activePause()
    {
        this.isPause = true;
    }

    public void desactivePause()
    {
        this.isPause = false;
    }
}
