package com.backslide999.betterworldedit.services;

import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerService {

    public static synchronized PlayerService getInstance(){
        if(instance == null){
            instance = new PlayerService();
        }
        return instance;
    }
    private static PlayerService instance;
    private PlayerService(){
        this.initializeSHowToggledPlayersList();
    }
    private ConcurrentHashMap<Player, Region> currentSelectedRegion;

    private List<Player> showToggledPlayers;

    public boolean hasShowToggled(Player player)
    {
        return this.showToggledPlayers.contains(player);
    }

    public void toggleShowOn(Player player)
    {
        if(hasShowToggled(player))
        {
            throw new RuntimeException("Played already has show toggled.");
        }
        this.showToggledPlayers.add(player);
    }

    public void toggleShowOff(Player player)
    {
        if(!hasShowToggled(player))
        {
            throw new RuntimeException("Player does not have show toggled.");
        }
        this.showToggledPlayers.remove(player);
        if(this.currentSelectedRegion.containsKey(player))
        {
            this.currentSelectedRegion.remove(player);
        }
    }

    public @Nullable Region getCurrentRegion(Player player)
    {
        return this.currentSelectedRegion.get(player);
    }

    public void setCurrentRegion(Player player, Region region)
    {
        this.currentSelectedRegion.put(player, region);
    }

    private void initializeSHowToggledPlayersList()
    {
        this.showToggledPlayers = Collections.synchronizedList(new ArrayList<Player>());
        this.currentSelectedRegion = new ConcurrentHashMap<>();
    }

}
