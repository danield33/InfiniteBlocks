package me.nanigans.infiniteblocks

import me.nanigans.infiniteblocks.commands.Shop
import me.nanigans.infiniteblocks.gui.Button
import me.nanigans.infiniteblocks.gui.Page
import me.nanigans.infiniteblocks.gui.ShopGUI
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPlugin.getPlugin

class InfiniteBlocks(): JavaPlugin() {

    val shopGUI: ShopGUI = this.setupShopInventory();

    override fun onEnable() {

        this.getCommand("shop")?.setExecutor(Shop());

    }

    fun setupShopInventory(): ShopGUI{

        val pages: MutableList<Page> = mutableListOf();
        var page: Page = Page("Shop", 54);
        var i: Int = 0;
        for (material in Material.values()) {
            if(material.isBlock){

                if(i < 54){
                    page.addButton(i, Button(ItemStack(material), { item: ItemStack -> println(item) }, true));
                }else{
                    i = -1;
                    pages.add(page);
                    page = Page("Shop", 54);
                }

                i++;
            }
        }

        return ShopGUI(pages.toTypedArray(), this);

    }

}