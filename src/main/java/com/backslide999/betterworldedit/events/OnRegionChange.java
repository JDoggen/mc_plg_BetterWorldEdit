package com.backslide999.betterworldedit.events;

import com.backslide999.betterworldedit.BetterWorldEditPlugin;
import com.backslide999.betterworldedit.commands.DisplayRegion;
import com.backslide999.betterworldedit.commands.RevertRegion;
import com.backslide999.betterworldedit.services.PlayerService;
import com.backslide999.betterworldedit.services.SettingsService;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.event.platform.BlockInteractEvent;
import com.sk89q.worldedit.event.platform.Interaction;
import com.sk89q.worldedit.event.platform.PlayerInputEvent;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class OnRegionChange {

    @Subscribe
    public void blockInteractEvent(BlockInteractEvent event) {
        Interaction interaction = event.getType();
        if(! (event.getCause() instanceof com.sk89q.worldedit.entity.Player)) {
            return;
        }
        Material bweTool = SettingsService.getInstance().worldEditTool;

        String name = event.getCause().getName();
        Player player = Bukkit.getPlayer(name);

        if(player.getInventory().getItemInMainHand().getType() != bweTool)
        {
            /* Action was not with WE tool */
            return;
        }

        if(!PlayerService.getInstance().hasShowToggled(player))
        {
            /* Player does not have show toggled on */
            return;
        }

        Region region = null;
        try
        {
            World selectionWorld = WorldEdit.getInstance().getSessionManager().findByName(player.getName()).getSelectionWorld();
            region = WorldEdit
                    .getInstance()
                    .getSessionManager()
                    .findByName(player.getName())
                    .getSelection(selectionWorld);
        }
        catch(NullPointerException | IncompleteRegionException npe)
        {
            /* No current selection */
            return;
        }

        Region oldRegion = PlayerService.getInstance().getCurrentRegion(player);
        if(oldRegion == null)
        {
            new DisplayRegion().run(player, region, true, false);
            Region newRegion = new CuboidRegion(region.getWorld(), region.getMinimumPoint(), region.getMaximumPoint());
            PlayerService.getInstance().setCurrentRegion(player, newRegion);
        }
        else
        {
            new RevertRegion().run(player, oldRegion, true);
            Region newRegion = new CuboidRegion(region.getWorld(), region.getMinimumPoint(), region.getMaximumPoint());
            PlayerService.getInstance().setCurrentRegion(player, newRegion);
            new DisplayRegion().run(player, newRegion, true, false);
        }


    }

}
