package me.waldxn.bounties.objects;

import org.bukkit.entity.Player;

public class Bounty {

    private final Player player;
    private final Player target;
    private final double price;

    public Bounty(Player player, Player target, double price){
        this.player = player;
        this.target = target;
        this.price = price;
    }

    public Player getPlayer(){
        return player;
    }

    public Player getTarget(){
        return target;
    }

    public double getPrice(){
        return price;
    }
}
