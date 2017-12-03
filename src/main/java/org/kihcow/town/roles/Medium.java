package org.kihcow.town.roles;

import org.bukkit.entity.Player;

public class Medium extends Townie{

    public Medium(Player p){
        this.p = p;
        this.alignment = 't';
        this.type = 's';
        this.attack = 0;
        this.defense = 0;
        this.canSelectAtNight = false;
        this.canChatAtNight = true;
    }

    @Override
    public void whenNightStarts() {
        super.whenNightStarts();
    }
}
