package com.backslide999.betterworldedit.commands;

import com.backslide999.betterworldedit.BetterWorldEditPlugin;
import org.bukkit.command.CommandSender;

public class Reload {

    public void run(CommandSender sender)
    {
        if(!sender.hasPermission("bwe.reload"))
        {
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(sender, "unauthorized");
            return;
        }
        try
        {
            BetterWorldEditPlugin.getInstance().reload(sender);
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultInfo(sender, "reload_success");
        } catch(Exception exc)
        {
            BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(sender, "reload_error");
        }
    }

}
