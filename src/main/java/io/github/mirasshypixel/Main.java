package io.github.mirasshypixel;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static HashMap<UUID, PartyObject> partyRef = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("[Party] Plugin activated");
    }

    @Override
    public void onDisable() {
        getLogger().info("[Party] Plugin de-activated");
    }


}
