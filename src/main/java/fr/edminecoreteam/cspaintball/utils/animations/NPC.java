package fr.edminecoreteam.cspaintball.utils.animations;

import com.mojang.authlib.GameProfile;
import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.content.game.rounds.RoundInfo;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class NPC
{
    private final static Core core = Core.getInstance();

    public NPC()
    {

    }

    private void init(Player p)
    {
        Location loc = p.getLocation();
        EntityPlayer npc = new EntityPlayer(((CraftServer) core.getServer()).getServer(),
                ((CraftWorld) core.getServer().getPlayer(p.getName()).getWorld()).getHandle(),
                new GameProfile(core.getServer().getPlayer(p.getName()).getUniqueId(), core.getServer().getPlayer(p.getName()).getName()),
                new PlayerInteractManager((((CraftWorld) core.getServer().getPlayer(p.getName()).getWorld()).getHandle())));

        for (Player pls : core.getServer().getOnlinePlayers())
        {
            PlayerConnection connection = ((CraftPlayer) pls).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            new BukkitRunnable() {
                int t = 0;
                public void run() {
                    ++t;
                    if (!core.isState(State.INGAME))
                    {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
                        }
                        cancel();
                    }
                    if (core.isRoundState(RoundInfo.PREPARATION))
                    {
                        for (Player pls : core.getServer().getOnlinePlayers())
                        {
                            connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
                        }
                        cancel();
                    }

                    if (t == 1)
                    {
                        run();
                    }
                }
            }.runTaskTimer((Plugin) core, 0L, 20L);
        }
    }
}
