package com.backslide999.betterworldedit.commands;

import com.backslide999.betterworldedit.BetterWorldEditPlugin;
import com.backslide999.betterworldedit.services.SettingsService;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.Random;


public class DisplayRegion
{


    public void run(final Player player, final Region region, final boolean edgeOnly)
    {
        this.run(player, region, edgeOnly, true);
    }
    public void run(final Player player, final Region region, final boolean edgeOnly, final boolean setTimeout)
    {
        try
        {
            final Player finalPlayer = player;
            final int minimumDelay = SettingsService.getInstance().minimumDelay;
            final int maximumDelay = SettingsService.getInstance().maximumDelay;
            final Material showMaterial = SettingsService.getInstance().showMaterial;
            final World world = Bukkit.getWorld(region.getWorld().getName());
            CuboidRegion cuboid = null;
            if(region instanceof CuboidRegion)
            {
                cuboid = (CuboidRegion) region;
            }
            if(edgeOnly)
            {
                if(!(region instanceof CuboidRegion))
                {
                    BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(player, "edge_only_cuboid_regions");
                    return;
                }
            }
            for(BlockVector3 vec : region)
            {
                final long delay = minimumDelay + new Random().nextInt(maximumDelay - minimumDelay);
                Location loc = new Location(world, vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
                Block data = world.getBlockAt(loc);
                if (cuboid != null)
                {
                    if (isEdge(vec, cuboid) || (!edgeOnly && world.getBlockAt(loc).getType() == Material.AIR))
                    {
                        player.sendBlockChange(loc, showMaterial.createBlockData());
                        if(setTimeout)
                        {
                            this.scheduleRevertRegion(player, loc, data.getBlockData(), delay);
                        }
                    }
                }
                else if(world.getBlockAt(loc).getType() == Material.AIR)
                {
                    if(setTimeout)
                    {
                        player.sendBlockChange(loc, showMaterial.createBlockData());
                        this.scheduleRevertRegion(player, loc, data.getBlockData(), delay);
                    }
                }
            }
        }
        catch(NullPointerException npe)
        {
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(player,"make_selection_first");
            return;
        }
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

    private void scheduleRevertRegion(final Player player, final Location location, final BlockData data, final long delay)
    {
        Bukkit.getScheduler()
                .scheduleSyncDelayedTask(BetterWorldEditPlugin.getInstance(),
                        () -> new RevertRegion().run(player, location, data),
                delay);
    }

}
