package me.nanigans.infiniteblocks.gui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

class Page(
    val guiName: String,
    val slots: Int,
    public val buttons: MutableMap<Int, Button>
    ) {

    constructor(guiName: String, slots: Int) : this(guiName, slots, mutableMapOf<Int, Button>());

    public fun addButton(slot: Int, button: Button){
        buttons[slot] = button;
    }

    public fun addButtons(buttons: Map<Int, Button>){
        this.buttons.putAll(buttons);
    }

    public fun build(): Inventory{
        val inventory: Inventory = Bukkit.createInventory(null, this.slots, this.guiName);
        for (slot in this.buttons.keys) {
            if(!(inventory.size < slot || slot < 0)){
                inventory.setItem(slot, this.buttons[slot]?.item)
            }
        }
        return inventory;
    }


}