package net.mplugins.plot.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/* hey */
@SerializableAs("plot")
public class Plot implements ConfigurationSerializable {
    private final List<UUID> members;
    private final OfflinePlayer owner;
    private final Location center;
    private final int radius;
    private final BoundingBox boundingBox;

    public Plot(OfflinePlayer owner, Location center, int radius) {
        this.owner = owner;
        this.center = center;
        this.radius = radius;
        final Location loc1 = center.clone().subtract(radius, 0, radius);
        loc1.setY(Integer.MIN_VALUE);
        final Location loc2 = center.clone().add(radius, 0, radius);
        loc2.setY(Integer.MAX_VALUE);
        this.members = new ArrayList<>();
        this.boundingBox = new BoundingBox(loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ());
    }

    public void addMember(OfflinePlayer player) {
        members.add(player.getUniqueId());
    }

    public boolean inside(Location location) {
        return boundingBox.contains(location.toVector());
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<>();

        map.put("owner", owner.getUniqueId().toString());
        map.put("members", members);
        map.put("center", center);
        map.put("radius", radius);

        return map;
    }

    @Override
    public String toString() {
        return "Plot{" +
                "members=" + members +
                ", owner=" + owner +
                ", center=" + center +
                ", radius=" + radius +
                ", boundingBox=" + boundingBox +
                '}';
    }

    public static Plot deserialize(Map<String, Object> args) {
        final OfflinePlayer owner = Bukkit.getOfflinePlayer(UUID.fromString((String) args.get("owner")));
        final List<UUID> members = (List<UUID>) args.get("members");
        final Location center = (Location) args.get("center");
        final int radius = (int) args.get("radius");
        final Plot plot = new Plot(owner, center, radius);

        members.forEach(member -> plot.addMember(Bukkit.getOfflinePlayer(member)));

        return plot;
    }
}
