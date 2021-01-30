package com.backslide999.betterworldedit.commands;

import com.backslide999.betterworldedit.BetterWorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class RevertRegion
{

    public void run(final Player player, final Region region, final boolean edgeOnly)
    {
        CuboidRegion cuboid = null;
        if(region instanceof CuboidRegion)
        {
            cuboid = (CuboidRegion) region;
        }
        for(BlockVector3 vector : region)
        {
            if(edgeOnly && cuboid != null && !isEdge(vector, cuboid))
            {
                continue;
            }
            Location location = new Location(player.getWorld(), vector.getX(), vector.getY(), vector.getZ());
            this.run(player, location);
        }
    }

    public void run(final Player player, final Location location)
    {
        BlockData data = player.getWorld().getBlockAt(location).getBlockData();
        this.run(player, location, data);
    }

    public void run(final Player player, final Location location, final BlockData data)
    {
        player.sendBlockChange(location, data);
    }

    private boolean isEdge(final BlockVector3 vec, final CuboidRegion region)
    {
        return
                region.getMinimumPoint().getBlockX() == vec.getBlockX() ||
                        region.getMinimumPoint().getBlockY() == vec.getBlockY() ||
                        region.getMinimumPoint().getBlockZ() == vec.getBlockZ() ||
                        region.getMaximumPoint().getBlockX() == vec.getBlockX() ||
                        region.getMaximumPoint().getBlockY() == vec.getBlockY() ||
                        region.getMaximumPoint().getBlockZ() == vec.getBlockZ();
    }


}
