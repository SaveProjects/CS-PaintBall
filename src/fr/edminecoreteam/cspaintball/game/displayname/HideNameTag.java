package fr.edminecoreteam.cspaintball.game.displayname;

import fr.edminecoreteam.cspaintball.Core;
import fr.edminecoreteam.cspaintball.State;
import fr.edminecoreteam.cspaintball.game.rounds.RoundInfo;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HideNameTag
{
    private final static Core core = Core.getInstance();

    private final Player p;

    private int getID;

    public HideNameTag(Player p)
    {
        this.p = p;
        this.getID = 0;
    }

    public void init()
    {
        TabListTeams tabListTeams = new TabListTeams();
        tabListTeams.refreshGamePlayerTag(p);

        /*Location loc = p.getLocation();
        WorldServer ws = ((CraftWorld)loc.getWorld()).getHandle();
        EntityArmorStand hideNameEntity = new EntityArmorStand((World)ws);
        PotionEffect invisibilityEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false);
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0, false, false);
        Silverfish hideNameEntity = (Silverfish)Bukkit.getWorld("game").spawnEntity(loc, EntityType.SILVERFISH);
        net.minecraft.server.v1_8_R3.Entity nms = ((CraftEntity) hideNameEntity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nms.c(tag);
        tag.setBoolean("Silent", true);
        nms.f(tag);
        hideNameEntity.setCustomName("hide" + p.getName());
        hideNameEntity.setMetadata("hide" + p.getName(), new FixedMetadataValue(core, "leash_" + p.getName()));
        hideNameEntity.setCustomNameVisible(false);
        hideNameEntity.addPotionEffect(invisibilityEffect);
        hideNameEntity.addPotionEffect(potionEffect);
        //PacketPlayOutSpawnEntityLiving sendPacket = new PacketPlayOutSpawnEntityLiving((EntityLiving)hideNameEntity);
        new BukkitRunnable() {
            int t = 0;
            public void run() {
                t++;

                if (!p.isOnline())
                {
                    desi();
                    cancel();
                }

                if (core.isRoundState(RoundInfo.END) || core.isRoundState(RoundInfo.BOMBDIFUSE) || core.isRoundState(RoundInfo.BOMBEXPLODE))
                {
                    desi();
                    cancel();
                }

                if (p.getPassenger() == null)
                {
                    //((CraftPlayer)p).getHandle().playerConnection.sendPacket(sendPacket);
                    p.setCustomNameVisible(false);
                    p.setPassenger(hideNameEntity);
                }

                if (t == 1)
                {
                    run();
                }

            }
        }.runTaskTimer((Plugin)core, 0L, 20L);*/
    }

    private void desi()
    {
        for (Silverfish hideNameEntity : core.getServer().getWorld(p.getWorld().getName()).getEntitiesByClass(Silverfish.class))
        {
            if (hideNameEntity.getCustomName().equalsIgnoreCase("hide" + p.getName()))
            {
                hideNameEntity.remove();
            }
        }
        //PacketPlayOutEntityDestroy removePacket = new PacketPlayOutEntityDestroy(this.getID);
        //((CraftPlayer) p).getHandle().playerConnection.sendPacket(removePacket);
    }

}
