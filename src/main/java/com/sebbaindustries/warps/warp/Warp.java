package com.sebbaindustries.warps.warp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * <b>Warp data type, containing all information about warp</b><br>
 * ID of the warp - ID<br>
 * Name of the warp - name<br>
 * Type of the warp - Enum Type<br>
 * Location of the warp - WarpLocation Class<br>
 * Owner of the warp - owner<br>
 * <br>
 * @author sebbaindustries
 * @version 1.0
 */
public class Warp {

    private final UUID ID;
    private String name;
    private Type type;

    private Player owner;

    private WarpLocation warpLocation;

    /**
     * Generates random UUID, frosty said that they won't repeat, so :/
     * @return random UUID
     */
    private UUID generateID() {
        return UUID.randomUUID();
    }

    /**
     * Warp constructor, for creating warp with direct location from the player.
     * @param type Type of warp
     * @param owner Owner of the warp and location
     * @param name Name of the warp, if name is null warp gets owners name
     */
    public Warp(final @NotNull Type type, final @NotNull Player owner, final String name) {
        this.ID = generateID();

        this.type = type;
        this.owner = owner;

        Location loc = owner.getLocation();
        warpLocation = new WarpLocation(loc.getWorld().getEnvironment(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        // check for name
        if (name == null) {
            this.name = owner.getName();
            return;
        }
        this.name = name;
    }

    /**
     * Warp constructor, for creating warp with custom parameters
     * @param type Type of warp
     * @param owner Owner of the warp
     * @param name Name of the warp
     * @param environment World.Environment
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @param yaw Yaw coordinate
     * @param pitch Pitch coordinate
     */
    public Warp(final @NotNull Type type, final @NotNull Player owner, final String name, final @NotNull World.Environment environment,
                final double x, final double y, final double z, final float yaw, final float pitch) {
        this.ID = generateID();

        this.type = type;
        this.owner = owner;

        warpLocation = new WarpLocation(environment, x, y, z, yaw, pitch);

        // check for name
        if (name == null) {
            this.name = owner.getName();
            return;
        }
        this.name = name;
    }

    /**
     * @return Warps name
     */
    public final String getName() {
        return name;
    }

    /**
     * Changes name of the warp
     * @param name Name of the warp
     */
    public final void setName(final @NotNull String name) {
        this.name = name;
    }

    /**
     * @return UUID of warp
     */
    public final String getID() {
        return ID.toString();
    }

    /**
     * @return Type of the warp
     */
    public final Type getType() {
        return type;
    }

    /**
     * Changes warp type
     * @param type Type of the warp
     */
    public final void setType(final @NotNull Type type) {
        this.type = type;
    }

    /**
     * @return Owner of the warp
     */
    public final Player getOwner() {
        return owner;
    }

    /**
     * Changes owner of the warp
     * @param owner Player instance
     */
    public final void setOwner(final @NotNull Player owner) {
        this.owner = owner;
    }

    /**
     * @see WarpLocation
     * @return Location of the warp
     */
    public final WarpLocation getLocation() {
        return warpLocation;
    }

    /**
     * Updates warp location, overrides old values
     * @param warpLocation New warp location
     */
    public final void setLocation(WarpLocation warpLocation) {
        this.warpLocation = warpLocation;
    }

    /**
     * Enum containing types of the warp
     */
    public enum Type {
        SERVER,
        PLAYER,
        ;
    }

}
