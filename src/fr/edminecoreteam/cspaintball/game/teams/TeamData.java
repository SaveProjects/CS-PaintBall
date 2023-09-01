package fr.edminecoreteam.cspaintball.game.teams;

import java.util.UUID;

public class TeamData {

    private final UUID playerId;
    private String team;

    public TeamData(UUID playerId, String data) {
        this.playerId = playerId;
        this.team = data;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getTeam() {
        return team;
    }

    public void changeTeam(String newteam) {
        team = newteam;
    }

    public void resetTeam() { team = "none"; }
}
