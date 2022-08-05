package net.mplugins.plot.commands;

import net.mplugins.plot.Main;
import net.mplugins.plot.model.Plot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreatePlot implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof final Player player)) {
            sender.sendMessage("§cThis command can only be executed by players!");
            return true;
        }

        Main.getInstance().getConfig().set("plots." + player.getUniqueId(), new Plot(player, player.getLocation(), 20));
        Main.getInstance().saveConfig();

        return true;
    }
}
