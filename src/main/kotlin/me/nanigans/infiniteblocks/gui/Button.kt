package me.nanigans.infiniteblocks.gui

import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class Button(val item: ItemStack, private val callback: (ItemStack) -> Unit, val playSound: Boolean){

    fun execute(){
        this.callback.invoke(this.item);
    }

}