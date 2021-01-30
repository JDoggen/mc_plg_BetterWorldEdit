package com.backslide999.betterworldedit.commands;

import com.backslide999.betterworldedit.BetterWorldEditPlugin;
import com.backslide999.betterworldedit.services.PlayerService;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import org.bukkit.entity.Player;

public class Toggle
{

    public void run(Player player)
    {
        if(PlayerService.getInstance().hasShowToggled(player))
        {

            BetterWorldEditPlugin.getInstance().sendPlayerDefaultInfo(player, "show.toggle_off");
            Region region = PlayerService.getInstance().getCurrentRegion(player);
            if(region != null)
            {
                new RevertRegion().run(player, region, true);
            }
            PlayerService.getInstance().toggleShowOff(player);
        }
        else
        {
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultInfo(player, "show.toggle_on");
            PlayerService.getInstance().toggleShowOn(player);
            try
            {
                World selectionWorld = WorldEdit.getInstance().getSessionManager().findByName(player.getName()).getSelectionWorld();
                Region region = WorldEdit
                        .getInstance()
                        .getSessionManager()
                        .findByName(player.getName())
                        .getSelection(selectionWorld);
                new DisplayRegion().run(player, region, true, false);
                Region newRegion = new CuboidRegion(region.getMinimumPoint(), region.getMaximumPoint());
                PlayerService.getInstance().setCurrentRegion(player, newRegion);
                return;
            }
            catch(NullPointerException | IncompleteRegionException npe)
            {
                return;
            }

        }
    }

}
