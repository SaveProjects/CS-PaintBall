package fr.edminecoreteam.cspaintball.utils.minecraft.holograms;

import fr.edminecoreteam.cspaintball.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.*;

public class HologramsBuilder
{
    private final static Core core = Core.getInstance();
    private final HashMap<String, List<ArmorStand>> armorStands = new HashMap<>();

    public void createBukkitHologram(String id, List<String> entry, Location location)
    {
        List<ArmorStand> aList = new ArrayList<ArmorStand>();
        for (String en : entry)
        {
            Location loc = new Location(location.getWorld(), location.getX(), location.getY() - 0.3f, location.getZ());
            ArmorStand armorStand = (ArmorStand) Bukkit.getWorld(location.getWorld().getName()).spawnEntity(loc, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            armorStand.setCustomName(en);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            aList.add(armorStand);
        }
        armorStands.put(id, aList);
        System.out.println("EDMINE-API: Load Hologram with ID: " + id);
    }

    public void removeBukkitHolgram(String id)
    {
        for (Map.Entry<String, List<ArmorStand>> en : armorStands.entrySet())
        {
            String key = en.getKey();
            if (key.equalsIgnoreCase(id))
            {
                for (ArmorStand stand : en.getValue())
                {
                    stand.remove();
                }
                armorStands.remove(id);
                System.out.println("EDMINE-API: Remove Hologram with ID: " + id);
                return;
            }
        }
    }

    public void updateLineBukkitHolograms(String id, int getLine, String newLine)
    {
        int line = getLine - 1;
        for (Map.Entry<String, List<ArmorStand>> en : armorStands.entrySet())
        {
            String key = en.getKey();
            if (key.equalsIgnoreCase(id))
            {
                ArmorStand armorStand = en.getValue().get(line);
                armorStand.setCustomName(newLine);
                System.out.println("EDMINE-API: Update Hologram Line (" + getLine + ") with ID: " + id);
                return;
            }
        }
    }

    public void removeLineBukkitHolograms(String id, int getLine)
    {
        for (Map.Entry<String, List<ArmorStand>> en : armorStands.entrySet())
        {
            String key = en.getKey();
            if (key.equalsIgnoreCase(id))
            {
                for(int i = 0; i < en.getValue().size(); i++)
                {
                    if (i > getLine)
                    {
                        ArmorStand armorStand = en.getValue().get(i);
                        Location loc = new Location(armorStand.getWorld(), armorStand.getLocation().getX(), armorStand.getLocation().getY() + 0.3f, armorStand.getLocation().getZ());
                        armorStand.teleport(loc);
                    }
                }
                en.getValue().remove(getLine);
                System.out.println("EDMINE-API: Remove Hologram Line (" + getLine + ") with ID: " + id);
            }
        }
    }
}
