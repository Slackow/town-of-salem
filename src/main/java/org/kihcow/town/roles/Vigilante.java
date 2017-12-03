package org.kihcow.town.roles;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Vigilante extends Townie {
    boolean attackedTownie = false;
    int bullets = 3;
    public Vigilante(Player p){
        this.p = p;
        this.alignment = 't';
        this.type = 'k';
        this.attack = 1;
        this.defense = 0;
        this.canSelectAtNight = true;
    }

    @Override
    public void whenNightStarts() {
        super.whenNightStarts();
        for(Townie townie : Townie.playing) {
            if(!townie.isDead)
                possibleTargets.add(townie);
        }
    }

    @Override
    public void whenNightEnds() {
        super.whenNightEnds();
        if(!attackedTownie) {
            kill(this.target, "Shot by Vigilante");
        } else {
            this.isDead = true;
        }
    }

    @Override
    public void kill(Townie townie, String deathMessage) {
        if(townie == null) {
            return;
        }
        if(townie.defense == 0) {
            super.kill(townie, deathMessage);
            bullets--;
            if(townie.alignment == 't') {
                attackedTownie = true;
            }
        }
    }

    @Override
    public void onNightOne() {
        super.onNightOne();
        possibleTargets = new ArrayList<>();
        p.sendMessage("You load your gun");
    }
}
