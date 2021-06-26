package me.nanigans.infiniteblocks.gui

import org.bukkit.inventory.ItemStack

class PurchasingItem(val item: ItemStack, amount: Int) {

    var itemAmount: Int = amount
    set(value) {
        field = 1.coerceAtLeast(value);
    }

    fun addAmount(amount: Int){
        this.itemAmount += amount;
    }

    fun subtractAmount(amount: Int){
        this.itemAmount -= amount;
    }

}