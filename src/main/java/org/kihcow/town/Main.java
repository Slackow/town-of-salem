package org.kihcow.town;

import net.minecraft.server.v1_12_R1.ChatClickable;
import net.minecraft.server.v1_12_R1.ChatHoverable;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.ChatModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.SkullMeta;
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
    public void whenPlayerInteract(PlayerInteractEvent e){
        Townie townie = Townie.fromPlayer(e.getPlayer());
        if(townie != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(e.getItem().getType() == Material.WRITTEN_BOOK) {
                townie.p.sendMessage("Opened Will, Hit Done to save.");
                townie.p.sendMessage("Move after closing to save.");
                townie.willOpen = true;
            }
            if(e.getItem().getType() == Material.NETHER_STAR){
                if(townie.possibleTargets.size() >= 1) {
                    Inventory inv = Bukkit.createInventory(null, 9 + (townie.possibleTargets.size() - 1) / 9 * 9, "Select A Player");
                    for(Townie targets : townie.possibleTargets){
                        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
                        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                        skullMeta.setOwningPlayer(targets.p);

                        if(townie.target == targets)
                            skull.addEnchantment(Enchantment.SILK_TOUCH, 0);

                        skull.setItemMeta(skullMeta);
                        inv.addItem(skull);
                    }
                    townie.p.openInventory(inv);
                }
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getInventory().getName().equals("Select A Player")){
            e.setCancelled(true);
            Townie t = Townie.fromPlayer((Player) e.getWhoClicked());
            if(t != null) {
                SkullMeta player = (SkullMeta) e.getCurrentItem().getItemMeta();
                t.target = Townie.fromPlayer((Player) player.getOwningPlayer());
            }
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Townie t = Townie.fromPlayer(e.getPlayer());
        if(t != null && t.willOpen) {
            t.willOpen = false;
            t.will = (BookMeta) t.p.getInventory().getItemInMainHand().getItemMeta();
        }
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
