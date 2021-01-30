package com.backslide999.betterworldedit.services;

import com.backslide999.betterworldedit.BetterWorldEditPlugin;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

public class SettingsService {

    public static synchronized SettingsService getInstance(){
        if(instance == null){
            instance = new SettingsService();
        }
        return instance;
    }
    private static SettingsService instance;
    private SettingsService(){

    }

    public Material showMaterial;
    public Material worldEditTool;
    public int minimumDelay;
    public int maximumDelay;

    public void reload()
    {
        this.reload(null);
    }

    public void reload(@Nullable CommandSender sender)
    {
        try
        {
            this.showMaterial = Material.getMaterial(BetterWorldEditPlugin.getInstance().fetchConfigString("bwe.show.material"));
            this.worldEditTool = Material.getMaterial(BetterWorldEditPlugin.getInstance().fetchConfigString("bwe.tool"));
            this.minimumDelay = BetterWorldEditPlugin.getInstance().fetchConfigInteger("bwe.show.minimum_delay");
            this.maximumDelay = BetterWorldEditPlugin.getInstance().fetchConfigInteger("bwe.show.maximum_delay");
        }
        catch(Exception e)
        {
            if(sender != null)
            {
                BetterWorldEditPlugin.getInstance().sendPlayerDefaultWarning(sender, "reload_error");
                BetterWorldEditPlugin.getInstance().logWarning(e);
            }
        }
    }
}
