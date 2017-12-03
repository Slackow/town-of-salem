package org.kihcow.town;

import net.minecraft.server.v1_12_R1.ChatClickable;
import net.minecraft.server.v1_12_R1.ChatHoverable;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.ChatModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.kihcow.town.roles.Townie;

public class Main extends JavaPlugin implements Listener {

    public String state = "none";
    public int counter = 0;
    public void onEnable(){
        System.out.println("Plugin Started");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run(){
                if(state.equals("day")){
                    if(counter == 0) {
                        state = "night";
                    }
                        counter--;
                }
                if (state.equals("night")){

                }
            }
        }, 0L, 20L);
    }

    public void onDisable(){
        System.out.println("Plugin Ended");
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent e){
        if(!state.equals("none"))
            e.setCancelled(true);
    }

    @EventHandler
    public void whenOpenWill(PlayerInteractEvent e){
        Townie townie = Townie.fromPlayer(e.getPlayer());
        if(townie != null && e.getItem().getType() == Material.WRITTEN_BOOK && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR)){
            townie.p.sendMessage("Opened Will, Hit Done to save.");
            townie.p.sendMessage("Move after closing to save.");
            townie.willOpen = true;
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Townie t = Townie.fromPlayer(e.getPlayer());
        if(t != null && t.willOpen)
            t.willOpen = false;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("startgame") && state.equals("none")){

            ChatModifier chatModifier = new ChatModifier();
            chatModifier.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.RUN_COMMAND, "joingame " + sender.getName()));
            chatModifier.setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_TEXT, new ChatMessage("Join " + sender.getName() + "'s game.")));

            Bukkit.broadcastMessage(sender.getName() + "Has Started a game, ");
            state = "joining";


            return true;
        }
        return false;
    }
}
