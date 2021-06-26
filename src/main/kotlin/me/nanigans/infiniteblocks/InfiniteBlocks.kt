package me.nanigans.infiniteblocks

import me.nanigans.infiniteblocks.commands.Shop
import me.nanigans.infiniteblocks.gui.GUIManager
import me.nanigans.infiniteblocks.gui.ShopGUI
import org.bukkit.plugin.java.JavaPlugin

class InfiniteBlocks(): JavaPlugin() {

    public var shopGUI: ShopGUI? = null;

    companion object{
        lateinit var instance: InfiniteBlocks;
        val shopManager: GUIManager = GUIManager();
    }

    init{
        instance = this;
    }

    override fun onEnable() {

        this.getCommand("shop")?.setExecutor(Shop());
        shopGUI = me.nanigans.infiniteblocks.gui.Shop.setupShopInventory();
        instance = this;

    }

}