package com.backslide999.betterworldedit.commands.executors;

import com.backslide999.betterworldedit.commands.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BWEListener implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length >= 1 && args[0].equalsIgnoreCase("reload"))
        {
            /* keep this check, as there will be more commands in the future.*/
            new Reload().run(sender);
            return true;
        }
        new Reload().run(sender);
        return true;
    }
}
