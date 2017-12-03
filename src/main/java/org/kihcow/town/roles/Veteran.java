package org.kihcow.town.roles;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Veteran extends Townie {
    int alerts = 3;
    public Veteran(Player p){
        this.p = p;
        this.alignment = 't';
        this.type = 'k';
        this.attack = 2;
        this.defense = 0;
        this.canSelectAtNight = true;
    }

    @Override
    public void whenNightStarts() {
        super.whenNightStarts();
        this.defense = 0;
        if(alerts != 0)
            possibleTargets = new ArrayList<>(Collections.singleton(this));
    }

    public void onAlert() {
        this.defense = 2;
        for(Townie townie: whoVisited) {
            this.kill(townie, "Shot by Veteran");
        }
        alerts--;
    }

    @Override
    public void whenNightEnds() {
        super.whenNightEnds();
        if(target != null)
            onAlert();
    }
}

