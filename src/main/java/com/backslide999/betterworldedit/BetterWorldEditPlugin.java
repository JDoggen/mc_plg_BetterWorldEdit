package com.backslide999.betterworldedit;

import com.backslide999.betterworldedit.commands.executors.BWEListener;
import com.backslide999.betterworldedit.commands.executors.ShowListener;
import com.backslide999.betterworldedit.events.OnRegionChange;
import com.backslide999.betterworldedit.services.SettingsService;
import com.backslide999.library.BasePlugin;
import com.sk89q.worldedit.WorldEdit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfigurationOptions;

import java.util.logging.Logger;

public class BetterWorldEditPlugin extends BasePlugin {

    private static BetterWorldEditPlugin instance;
    public static BetterWorldEditPlugin getInstance(){
        return instance;
    }
    public final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        instance = this;

        // Read config file
        this.logInfo("Reading Config file");
        this.reload();

        // Register Commands
        this.logInfo("Registering Commands");
        this.getCommand("bwe").setExecutor(new BWEListener());
        this.getCommand("/show").setExecutor(new ShowListener());

        // Register Events
        this.logInfo("Registering Events");

        // Register World Edit Events
        WorldEdit.getInstance().getEventBus().register(new OnRegionChange());

    }

    public void reload(){
        this.reload(null);
    }

    public void reload(CommandSender sender) {
        this.reloadConfig();
        FileConfigurationOptions config = getConfig().options().copyDefaults(true);
        saveConfig();
        SettingsService.getInstance().reload(sender);
    }
}
