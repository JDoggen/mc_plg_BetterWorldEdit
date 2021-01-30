package com.backslide999.betterworldedit.commands.executors;

import com.backslide999.betterworldedit.BetterWorldEditPlugin;
import com.backslide999.betterworldedit.commands.DisplayRegion;
import com.backslide999.betterworldedit.commands.Toggle;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ShowListener implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;
        if(sender instanceof Player)
        {
            player = (Player) sender;
        }
        if(player == null)
        {
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(sender,"no_command_line");
            return true;
        }
        if(!player.hasPermission("bwe.show"))
        {
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(sender,"unauthorized");
            return true;
        }
        if(hasArg(args, "-t"))
        {
            new Toggle().run(player);
            return true;
        }

        World selectionWorld = WorldEdit.getInstance().getSessionManager().findByName(player.getName()).getSelectionWorld();
        final boolean edgeOnly = !hasArg(args, "-f"); /* fill */
        try
        {
            Region region = WorldEdit
                    .getInstance()
                    .getSessionManager()
                    .findByName(player.getName())
                    .getSelection(selectionWorld);
            new DisplayRegion().run(player, region, edgeOnly);
            return true;
        }
        catch(NullPointerException | IncompleteRegionException npe)
        {
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(sender,"make_selection_first");
            return true;
        }
    }

    private boolean hasArg(String[] args, String arg)
    {
        for(String argument : args)
        {
            if(argument.equalsIgnoreCase(arg))
            {
                return true;
            }
        }
        return false;
    }
}
