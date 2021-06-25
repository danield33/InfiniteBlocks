package me.nanigans.infiniteblocks.commands

import me.nanigans.infiniteblocks.InfiniteBlocks
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Shop(): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, aliase: String, args: Array<out String>): Boolean {

        if(sender is Player && command.name.equals("shop", ignoreCase = true)){

            InfiniteBlocks.instance.shopGUI?.open(sender, 0);

        }else{
            sender.sendMessage("${ChatColor.RED} You are not a player");
        }
        return true;

    }
}