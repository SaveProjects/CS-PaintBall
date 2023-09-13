package fr.edminecoreteam.cspaintball.game.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class Other
{
    public static void teleportRandom(Player joueur, Location positionDeReference) {
        Random rand = new Random();

        // Générez des valeurs aléatoires pour X et Z (chacune entre -3 et 3 inclus)
        int xOffset = rand.nextInt(7) - 3;
        int zOffset = rand.nextInt(7) - 3;

        // Créez une nouvelle localisation basée sur la position de référence et les offsets aléatoires,
        // mais en conservant la coordonnée Y de la position de référence
        Location nouvelleLocalisation = new Location(
                positionDeReference.getWorld(),
                positionDeReference.getX() + xOffset,
                positionDeReference.getY(),
                positionDeReference.getZ() + zOffset,
                positionDeReference.getYaw(),
                positionDeReference.getPitch()
        );

        // Téléportez le joueur à la nouvelle localisation
        joueur.teleport(nouvelleLocalisation);
    }
}
