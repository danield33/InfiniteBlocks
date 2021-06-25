package me.nanigans.infiniteblocks.gui

import me.nanigans.infiniteblocks.InfiniteBlocks
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class Shop {

    companion object{

        private fun openItem(item: ItemStack, player: Player){

            val inventory: Inventory = Bukkit.createInventory(player, 27, "Purchase ${item.type}");
            val itemClone = item.clone()
            val itemMeta = itemClone.itemMeta;
            val listOf: List<String> = listOf("Amount: 64")
            if (itemMeta != null) {
                itemMeta.lore = listOf
            };
            itemClone.itemMeta = itemMeta;
            inventory.setItem(13, itemClone);

            var position: Int = 9;
            for(j in 1..2) {

                var i: Int = 5000;
                val material: Material = if(j == 1) Material.RED_STAINED_GLASS_PANE
                                            else Material.LIME_STAINED_GLASS_PANE

                while (i > 20) {

                    val itemStack = ItemStack(material);
                    val itemMeta1 = itemStack.itemMeta;
                    if (itemMeta1 != null) {
                        itemMeta1.setDisplayName("${ChatColor.BOLD}${i}");
                        itemStack.itemMeta = itemMeta1;
                    };

                    inventory.setItem(position, itemStack);
                    i /= 5;
                    position++;
                    if(position == 13)
                        position++;
                }
            }

            player.openInventory(inventory);

        }

        fun setupShopInventory(): ShopGUI{

            val pages: MutableList<Page> = mutableListOf();
            var page: Page = Page("Shop", 54);
            var i: Int = 0;
            for (material in Material.values()) {

                if(material.isBlock && !material.isAir && !material.isLegacy){

                    if(i < 45){
                        page.addButton(i, Button(ItemStack(material), ::openItem, true));
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