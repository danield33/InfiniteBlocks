package me.nanigans.infiniteblocks.gui

import me.nanigans.infiniteblocks.InfiniteBlocks
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Shop {

    companion object{

        fun setupShopInventory(): ShopGUI{

            val pages: MutableList<Page> = mutableListOf();
            var page: Page = Page("Shop", 54);
            var i: Int = 0;
            for (material in Material.values()) {

                if(material.isBlock && !material.isAir && !material.isLegacy){

                    if(i < 45){
                        page.addButton(i, Button(ItemStack(material), { item: ItemStack, _: Player -> println(item) }, true));
                    }else{
                        i = -1;
                        pages.add(page);
                        page = Page("Shop", 54);
                    }

                    i++;
                }
            }
            if(i < 45) pages.add(page);

            val shopGUI = ShopGUI(pages.toTypedArray(), InfiniteBlocks.instance);

            shopGUI.pages.forEach { page ->
                run {
                    val itemStack = ItemStack(Material.COMPASS);
                    val itemMeta = itemStack.itemMeta
                    itemMeta?.setDisplayName("${ChatColor.GOLD}Page Back");
                    itemStack.itemMeta = itemMeta;

                    page.addButton(45, Button(itemStack.clone(), shopGUI::pageBackwards, true));
                    itemMeta?.setDisplayName("${ChatColor.GOLD}Page Forward");
                    itemStack.itemMeta = itemMeta;
                    page.addButton(53, Button(itemStack, shopGUI::pageForward, true));
                }
            }

            return shopGUI;

        }

    }

}