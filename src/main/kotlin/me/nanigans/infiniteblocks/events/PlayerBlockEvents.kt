package me.nanigans.infiniteblocks.events

import me.nanigans.infiniteblocks.InfiniteBlocks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.persistence.PersistentDataType

class PlayerBlockEvents: Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent){
        val player = event.player
        val itemInHand = event.itemInHand
        val itemAmount = InfiniteBlocks.blockManager.getNBT(itemInHand, "itemAmount", PersistentDataType.INTEGER);
        if(itemAmount != null && itemAmount > 1){
            val itemMeta = itemInHand.itemMeta
            itemMeta!!.lore = arrayListOf("Amount: ${itemAmount-1}");
            itemInHand.itemMeta = itemMeta;
            InfiniteBlocks.blockManager.setNBT(itemInHand, "itemAmount", PersistentDataType.INTEGER, itemAmount-1);
            player.inventory.setItemInMainHand(itemInHand);
        }
    }

}