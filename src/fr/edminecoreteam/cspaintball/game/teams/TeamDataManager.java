package fr.edminecoreteam.cspaintball.game.teams;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamDataManager {

    private final Map<UUID, TeamData> players = new HashMap<>();

    public TeamData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new TeamData(id, "none"));
    }

    public void addData(UUID playerId, String amount) {
        getPlayerData(playerId).TeamData(amount);
    }

    public void removeData(UUID playerId, String amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetTeam(UUID playerId) { getPlayerData(playerId).resetTeam(); }

    public String getTeam(UUID playerId) { return getPlayerData(playerId).getTeam(); }

    public Map<UUID, TeamData> returnPlayers() { return this.players; }

}
