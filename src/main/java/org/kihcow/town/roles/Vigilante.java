package org.kihcow.town.roles;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Vigilante extends Townie {
    boolean attackedTownie = false;
    public Vigilante(Player p){
        this.p = p;
        this.alignment = 't';
        this.type = 'k';
        this.attack = 1;
        this.defense = 0;
        this.canSelectAtNight = true;
        this.attackedTownie = false;
    }

    @Override
    public void whenNightStarts() {
        super.whenNightStarts();
        possibleTargets = new ArrayList<>();
        for(Townie townie : Townie.playing) {
            if(!townie.isDead)
                possibleTargets.add(townie);
        }
    }

    @Override
    public void onNightOne() {
        super.onNightOne();
        possibleTargets = new ArrayList<>();
        p.sendMessage("");
    }
}
