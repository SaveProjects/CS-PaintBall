package fr.edminecoreteam.cspaintball.utils.dragonbar;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BarListener
{
    private static final Core core = Core.getInstance();
    private final Player p;
    public BarListener(Player p) {
        this.p = p;
    }

    public void launch()
    {
        /*Float healthBarStarting = convertToPercentage(core.timers, 15);
        Float healthBarRound = convertToPercentage(core.timers, core.getConfig().getInt("timer.round"));
        Float healthBarBombPlanted = convertToPercentage(core.timers, core.getConfig().getInt("timer.bomb"));
        Float healthBarBombExplode = convertToPercentage(core.timers, 6);
        Float healthBarBombDifuse = convertToPercentage(core.timers, 6);
        Float healthBarBombNoPlanted = convertToPercentage(core.timers, 6);*/

        String attackerStarting = encode(core.getConfig().getString("dragonbar.game.attacker.starting"));
        String attackerRound = encode(core.getConfig().getString("dragonbar.game.attacker.round"));
        String attackerBombPlanted = encode(core.getConfig().getString("dragonbar.game.attacker.bomb-planted"));
        String attackerBombExplode = encode(core.getConfig().getString("dragonbar.game.attacker.bomb-explode"));
        String attackerBombDifuse = encode(core.getConfig().getString("dragonbar.game.attacker.bomb-difuse"));
        String attackerBombNoPlanted = encode(core.getConfig().getString("dragonbar.game.attacker.bomb-no-planted"));

        String defenserStarting = encode(core.getConfig().getString("dragonbar.game.defenser.starting"));
        String defenserRound = encode(core.getConfig().getString("dragonbar.game.defenser.round"));
        String adefenserBombPlanted = encode(core.getConfig().getString("dragonbar.game.defenser.bomb-planted"));
        String defenserBombExplode = encode(core.getConfig().getString("dragonbar.game.defenser.bomb-explode"));
        String defenserBombDifuse = encode(core.getConfig().getString("dragonbar.game.defenser.bomb-difuse"));
        String defenserBombNoPlanted = encode(core.getConfig().getString("dragonbar.game.defenser.bomb-no-planted"));
        new BukkitRunnable() {
            int t = 0;
            public void run() {
                ++t;
                if (!Bukkit.getServer().getOnlinePlayers().contains(p))
                {
                    cancel();
                }

                if (core.isState(State.WAITING))
                {
                    BarUtil.updateText(p, "§aEn attente de joueurs...");
                }
                if (core.isState(State.STARTING))
                {
                    BarUtil.updateText(p, "§dPréparez vous !");
                }
                if (core.isState(State.INGAME))
                {
                    if (core.isRoundState(RoundInfo.PREPARATION))
                    {
                        if (core.teams().getAttacker().contains(p))
                        {
                            BarUtil.updateText(p, attackerStarting);
                        }
                        if (core.teams().getDefenser().contains(p))
                        {
                            BarUtil.updateText(p, defenserStarting);
                        }
                    }
                    if (core.isRoundState(RoundInfo.START))
                    {
                        if (core.teams().getAttacker().contains(p))
                        {
                            BarUtil.updateText(p, attackerRound);
                        }
                        if (core.teams().getDefenser().contains(p))
                        {
                            BarUtil.updateText(p, defenserRound);
                        }
                    }
                    if (core.isRoundState(RoundInfo.BOMBPLANTED))
                    {
                        if (core.teams().getAttacker().contains(p))
                        {
                            BarUtil.updateText(p, attackerBombPlanted);
                        }
                        if (core.teams().getDefenser().contains(p))
                        {
                            BarUtil.updateText(p, adefenserBombPlanted);
                        }
                    }
                    if (core.isRoundState(RoundInfo.BOMBEXPLODE))
                    {
                        if (core.teams().getAttacker().contains(p))
                        {
                            BarUtil.updateText(p, attackerBombExplode);
                        }
                        if (core.teams().getDefenser().contains(p))
                        {
                            BarUtil.updateText(p, defenserBombExplode);
                        }
                    }
                    if (core.isRoundState(RoundInfo.BOMBDIFUSE))
                    {
                        if (core.teams().getAttacker().contains(p))
                        {
                            BarUtil.updateText(p, attackerBombDifuse);
                        }
                        if (core.teams().getDefenser().contains(p))
                        {
                            BarUtil.updateText(p, defenserBombDifuse);
                        }
                    }
                    if (core.isRoundState(RoundInfo.END))
                    {
                        if (core.teams().getAttacker().contains(p))
                        {
                            BarUtil.updateText(p, attackerBombNoPlanted);
                        }
                        if (core.teams().getDefenser().contains(p))
                        {
                            BarUtil.updateText(p, defenserBombNoPlanted);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)Core.getInstance(), 0L, 10L);
    }


    private String encode(String s) {
        String encoded = s
                .replace("&", "§")
                .replace("{e1}", "é")
                .replace("{e2}", "è")
                .replace("{e3}", "ê")
                .replace("{e4}", "É")
                .replace("{e5}", "È")
                .replace("{e6}", "Ê")
                .replace("{i1}", "î")
                .replace("{i2}", "Î")
                .replace("{a1}", "à")
                .replace("{a2}", "â")
                .replace("{a3}", "À")
                .replace("{a4}", "Â")
                .replace("{o1}", "ô")
                .replace("{o2}", "Ô")
                .replace("{love}", "❤")
                .replace("{valide}", "✔")
                .replace("{unvalid}", "✘")
                .replace("{flocon}", "✵")
                .replace("{eclats}", "❁")
                .replace("{money}", "✪")
                .replace("{star}", "✯")
                .replace("{right}", "➡")
                .replace("{left}", "⬅")
                .replace("{up}", "⬆")
                .replace("{down}", "⬇")
                .replace("{right_select}", "➟")
                .replace("{alert}", "⚠")
                .replace("{separator}", "»")
                .replace("{inverseseparator}", "«")
                .replace("{player_name}", p.getName())
                .replace("{euro}", "€")
                .replace("{timer}", "" + core.timers);

        return encoded;
    }

}
