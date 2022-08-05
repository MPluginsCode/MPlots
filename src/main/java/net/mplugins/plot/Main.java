package net.mplugins.plot;

import net.mplugins.plot.commands.CreatePlot;
import net.mplugins.plot.model.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(Plot.class);
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("createplot").setExecutor(new CreatePlot());
        loadPlots();
    }

    private void loadPlots() {
        if (!getConfig().isSet("plots")) {
            return;
        }

        for (String uuid : getConfig().getConfigurationSection("plots").getValues(false).keySet()) {
            final Plot plot = getConfig().getSerializable("plots." + uuid, Plot.class);
            System.out.println(plot);
        }
    }

    public static Main getInstance() {
        return instance;
    }
}
