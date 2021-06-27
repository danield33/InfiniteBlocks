package me.nanigans.infiniteblocks

import me.nanigans.infiniteblocks.commands.Shop
import me.nanigans.infiniteblocks.events.PlayerBlockEvents
import me.nanigans.infiniteblocks.gui.GUIManager
import me.nanigans.infiniteblocks.gui.ShopGUI
import org.bukkit.plugin.java.JavaPlugin

class InfiniteBlocks : JavaPlugin() {

    var shopGUI: ShopGUI? = null
            private set;

    companion object{
        lateinit var instance: InfiniteBlocks;
        val shopManager: GUIManager = GUIManager();
        val blockManager: BlockTracker = BlockTracker();
    }

    init{
        instance = this;
    }

    override fun onEnable() {

        this.getCommand("shop")?.setExecutor(Shop());
        this.server.pluginManager.registerEvents(PlayerBlockEvents(), this);
        shopGUI = me.nanigans.infiniteblocks.gui.Shop.setupShopInventory();
        instance = this;

    }

}