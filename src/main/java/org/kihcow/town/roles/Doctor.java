package org.kihcow.town.roles;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.kihcow.town.StringUtils;

import java.util.ArrayList;

public class Doctor extends Townie {

    boolean usedSelfHeal;

    public Doctor(Player p){
        this.p = p;
        this.alignment = 't';
        this.type = 'p';
        this.defense = 0;
        this.canSelectAtNight = true;
        this.usedSelfHeal = false;
    }

    public void whenNightStarts(){
        super.whenNightStarts();
        if(!usedSelfHeal)
            p.sendMessage("You have 1 self heal left");
        else
            p.sendMessage("You have " + "0 self heals" + " left");
        p.sendMessage("You have " + StringUtils.plural("self heal", !usedSelfHeal ? 1:0) + " left");
        possibleTargets = new ArrayList<>();
        for(Townie townie : Townie.playing) {
            if(!townie.isDead)
                possibleTargets.add(townie);
        }
        if(usedSelfHeal)
            possibleTargets.remove(this);

    }



    public void whenNightEnds(){
        super.whenNightEnds();
        heal(this.target);
    }

    public void heal(Townie townie){
        if(townie == null)
            return;
        if(townie.isDead){
            this.p.sendMessage(ChatColor.RED + "Your target was attacked!");
            if(townie.whoAttacked != null && townie.whoAttacked.attack == 1) {
                townie.isDead = false;
                townie.p.sendMessage(ChatColor.GREEN + "You were nursed back to health!");
            }
        }
    }
}
