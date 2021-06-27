package me.nanigans.infiniteblocks

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*
import kotlin.collections.HashMap

class BlockTracker {

    val playerBlocks: HashMap<UUID, HashMap<ItemStack, Int>> = hashMapOf();

    fun addToPlayer(uuid: UUID, itemStack: ItemStack, amount: Int){
        playerBlocks[uuid]?.set(itemStack, playerBlocks[uuid]!![itemStack]!! + amount);
    }

    fun setPlayer(uuid: UUID, itemStack: ItemStack, amount: Int){
        playerBlocks[uuid] = hashMapOf(itemStack to amount);
    }

    fun <T, Z> setNBT(item: ItemStack, key: String, type: PersistentDataType<T, Z>, value: Z) {
        val meta = item.itemMeta
        meta!!.persistentDataContainer
            .set(NamespacedKey(InfiniteBlocks.instance, key), type, value)
        item.itemMeta = meta
    }

    fun <T, Z> getNBT(item: ItemStack, key: String, type: PersistentDataType<T, Z>): Z? {
        return item.itemMeta!!
            .persistentDataContainer.get(NamespacedKey(InfiniteBlocks.instance, key), type)
    }

}