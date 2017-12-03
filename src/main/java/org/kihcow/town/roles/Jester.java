package org.kihcow.town.roles;

import org.bukkit.entity.Player;

public class Jester extends Townie {

    public Jester(Player p){
        this.p = p;
        this.alignment = 'n';
        this.type = 'e';
        this.attack = 2;
        this.defense = 0;
        this.canSelectAtNight = false;
    }
}
