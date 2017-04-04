package io.github.mirasshypixel;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;

public class PartyObject
{
    public List<UUID> partyList;

    private UUID leader;

    public PartyObject(UUID player) {
        leader = player;
        partyList.add(player);
    }

    public void setLeader(UUID player) {
        leader = player;
    }

    public UUID getLeader() {
        return leader;
    }

    public String getLeaderString() {
        return ("*" + Bukkit.getPlayer(getLeader()).getName());
    }
}
