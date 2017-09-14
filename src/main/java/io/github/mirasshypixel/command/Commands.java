package io.github.mirasshypixel.command;

//*Sam Colbourne//*

import io.github.mirasshypixel.Main;
import io.github.mirasshypixel.PartyObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        if (cmd.getName().equalsIgnoreCase("party")) {
            Player player = (Player) sender;

            if (args.length == 0) return true;

            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "create":
                        create(player);
                        break;
                    case "disband":
                        if (isInParty(player)) {
                            disband(player);
                        }
                        break;
                    case "list":
                        list(player);
                        break;
                }
                return true;
            }

            if (args.length == 2) {
                switch (args[0].toLowerCase()) {
                    case "invite": {

                    }
                    case "promote":
                        promote(player, args);
                        break;
                }
                return true;
            }
        }
        return true;
    }

    ///////////////////////// FUNCTIONS /////////////////////////

    private boolean isInParty(Player player) {
        return Main.partyRef.containsKey(player.getUniqueId());
    }

    private void disband(Player player) {

        PartyObject partyObjectToClear = Main.partyRef.get(player.getUniqueId());

        for (UUID uuid : partyObjectToClear.partyList) {
            Player playerToClear = Bukkit.getPlayer(uuid);
            playerToClear.sendMessage(ChatColor.BLUE + "[Party]" + ChatColor.WHITE + " Party  disbanded by the leader.");
            Main.partyRef.remove(uuid);
        }
    }

    private void list(Player player) {

        PartyObject partyObjectToList = Main.partyRef.get(player.getUniqueId());

        StringBuilder builder = new StringBuilder();

        int count = 0;

        for (UUID uuid : partyObjectToList.partyList) {
            count++;
            if (uuid == partyObjectToList.getLeader()) {
                builder.append(partyObjectToList.getLeaderString());
                break;
            } else {
                Player uuidPlayer = Bukkit.getPlayer(uuid);
                builder.append(uuidPlayer.getName());
            }
            if (count != partyObjectToList.partyList.size()) {
                builder.append(", ");
            }
        }

        player.sendMessage(ChatColor.BLUE + "[Party]" + ChatColor.WHITE + " Players: " + builder.toString());
    }

    private void promote(Player player, String[] args) {

        if (Main.partyRef.get(player.getUniqueId()).getLeader() != player.getUniqueId()) {
            player.sendMessage(ChatColor.BLUE + "[Party]" + ChatColor.WHITE + " You are not the party leader!");
        } else {
            PartyObject partyToPromote = Main.partyRef.get(player.getUniqueId());
            partyToPromote.setLeader(Bukkit.getPlayer(args[1]).getUniqueId());
        }
    }

    private void create(Player player) {

        if (isInParty(player)) {
            player.sendMessage(ChatColor.BLUE + "[Party]" + ChatColor.WHITE + " You are already in a party!");
        } else {
            PartyObject partyObjectToCreate = new PartyObject(player.getUniqueId());
            Main.partyRef.put(player.getUniqueId(), partyObjectToCreate);

            player.sendMessage(ChatColor.BLUE + "[Party]" + ChatColor.WHITE + " You created a party.");
        }
    }

}
