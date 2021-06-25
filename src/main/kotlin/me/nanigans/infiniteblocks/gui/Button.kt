package me.nanigans.infiniteblocks.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.reflect.KFunction2

class Button(val item: ItemStack, private val callback: (ItemStack, Player) -> Unit, val playSound: Boolean){

    fun execute(player: Player){
        this.callback.invoke(this.item, player);
    }

}