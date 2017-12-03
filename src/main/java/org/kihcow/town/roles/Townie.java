package org.kihcow.town.roles;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class Townie {

    public static List<Townie> playing;

    public Player p;
    public char alignment;
    public char type;
    public int defense;
    public List<Townie> whoVisited;
    public Townie whoAttacked;
    public int attack;
    public boolean canSelectAtNight;
    public boolean isDead = false;
    public Townie target;
    public List<Townie> possibleTargets;
    public BookMeta will;
    public boolean willOpen;
    public StringBuilder deathMessage = new StringBuilder("");
    public boolean canChatAtNight;

    public void whenNightStarts(){
        p.sendMessage("Night Begun");
        possibleTargets = new ArrayList<>();
        whoVisited = new ArrayList<>();
    }

    public void whenChooseTarget(){
        if(this.target == null)
            p.sendMessage("You changed your mind");
    }

    public void whenNightEnds(){
        if(this.target == null && canSelectAtNight)
            p.sendMessage(ChatColor.RED + "You didn't use your night ability.");
        else if(canSelectAtNight)
            this.visit(this.target);
        p.sendMessage("Night Over");
    }

    public void onNightOne(){

    }

    public void visit(Townie townie){
        townie.whoVisited.add(this);
    }


    public static Townie fromPlayer(Player p){
        for(Townie townie : playing)
            if (townie.p == p)
                return townie;
        return null;
    }

    public void kill(Townie townie, String deathMessage) {
        if(townie == null) {
            return;
        }
        if(townie.defense < this.attack) {
            townie.isDead = true;
            townie.deathMessage.append(deathMessage);
        }
    }
}
