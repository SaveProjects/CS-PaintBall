package fr.edminecoreteam.cspaintball.utils.game;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class GameUtils
{
    private final static Core core = Core.getInstance();

    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));
    private final Location attackerSpawn = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".attacker.t"));

    private final Location defenserSpawn = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".defenser.t"));

    public ItemStack haveBomb(Player player, String customName)
    {
        Inventory playerInventory = player.getInventory();
        for (ItemStack item : playerInventory.getContents())
        {
            if (item != null && item.getType() == Material.SKULL_ITEM)
            {
                ItemMeta meta = item.getItemMeta();
                if (meta instanceof SkullMeta)
                {
                    SkullMeta skullMeta = (SkullMeta) meta;
                    if (skullMeta.hasDisplayName() && skullMeta.getDisplayName().equals(customName))
                    {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    public void showAllPlayers(Player player)
    {
        for (Player pls : core.getServer().getOnlinePlayers())
        {
            player.showPlayer(pls);
        }
    }

    public double getPercentage(int actualNumber, int maxNumber) {
        if (maxNumber <= 0)
        {
            throw new IllegalArgumentException("Le nombre maximum doit être supérieur à zéro.");
        }
        return ((double) actualNumber / maxNumber) * 100;
    }

    public String convertTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02dm %02ds", minutes, seconds);
    }

    public String getRoundInfoMSG()
    {
        int getRound = core.roundManager().getRound();
        int longRound = core.getConfig().getInt("timers.rounds-long");
        int longMaxRound = longRound * 2;
        int shortRound = core.getConfig().getInt("timers.rounds-short");
        int shortMaxRound = shortRound * 2;
        String roundInfo = null;
        if (core.getConfig().getString("time").equalsIgnoreCase("short"))
        {
            roundInfo = "§fManche: §b" + getRound + "§8/§b" + shortMaxRound + " §8┃ ";
            return roundInfo;
        }
        else if (core.getConfig().getString("time").equalsIgnoreCase("long"))
        {
            roundInfo = "§fManche: §b" + getRound + "§8/§b" + longMaxRound + " §8┃ ";
            return roundInfo;
        }
        return null;
    }

    public Location getAttackerSpawn()
    {
        return this.attackerSpawn;
    }

    public Location getDefenserSpawn()
    {
        return this.defenserSpawn;
    }
    public Location getSpawn()
    {
        return this.spawn;
    }
}
