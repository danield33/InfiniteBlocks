package me.nanigans.infiniteblocks.gui

import me.nanigans.infiniteblocks.InfiniteBlocks
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class Shop {

    companion object{

        private fun getPurchasePage(shopGUI: ShopGUI): Page{

            val page: Page = Page("Purchase", 27);

            var position: Int = 9;
            for(j in -1..1 step 2) {

                var i: Int = 5000;
                val material: Material = if(j == -1) Material.RED_STAINED_GLASS_PANE
                                            else Material.LIME_STAINED_GLASS_PANE

                while (i > 20) {

                    val itemStack = ItemStack(material);
                    val itemMeta1 = itemStack.itemMeta;
                    if (itemMeta1 != null) {
                        itemMeta1.setDisplayName("${ChatColor.BOLD}${i}");
                        itemStack.itemMeta = itemMeta1;
                    };

                    val iCopy = i;
                    page.addButton(position, Button(itemStack,
                        {player ->
                            run {
                                InfiniteBlocks.shopManager.itemInPurchase[player.uniqueId]?.addAmount(iCopy * j)
                                this.openItem(shopGUI, player, InfiniteBlocks.shopManager.itemInPurchase[player.uniqueId]!!.item);
                            }
                        }, true))
                    i /= 5;
                    position++;
                    if(position == 13)
                        position++;
                }
            }

            return page;
        }

        fun purchaseItem(player: Player, itemStack: ItemStack){

            val amount = InfiniteBlocks.shopManager.itemInPurchase[player.uniqueId]!!.itemAmount
            InfiniteBlocks.blockManager.setNBT(itemStack, "itemAmount", PersistentDataType.INTEGER, amount);
            player.closeInventory();
            player.inventory.addItem(itemStack);

        }

        fun openItem(shopGUI: ShopGUI, player: Player, itemStack: ItemStack){

            val clone = itemStack.clone();
            val itemMeta = clone.itemMeta!!;
            val itemInPurchase = InfiniteBlocks.shopManager.itemInPurchase

            if(itemInPurchase.containsKey(player.uniqueId)){
                itemMeta.lore = arrayListOf("Amount: ${itemInPurchase[player.uniqueId]!!.itemAmount}")
            } else {
                itemMeta.lore = arrayListOf("Amount: 64");
                itemInPurchase[player.uniqueId] = PurchasingItem(itemStack, 64);
            };

            clone.itemMeta = itemMeta;

            shopGUI.pages[shopGUI.pages.size-1].addButton(13,
                Button(clone, {player -> this.purchaseItem(player, clone) }, true));

            shopGUI.open(player, shopGUI.pages.size-1);

        }

        fun setupShopInventory(): ShopGUI{

            val pages: ArrayList<Page> = arrayListOf();
            val shopGUI = ShopGUI(pages, InfiniteBlocks.instance);

            var page: Page = Page("Shop", 54);
            var i: Int = 0;
            for (material in Material.values()) {

                if(material.isBlock && !material.isAir && !material.isLegacy){

                    if(i < 45){
                        val item = ItemStack(material)
                        page.addButton(i, Button(item, { player -> this.openItem(shopGUI, player, item) }, true));
                    }else{
                        i = -1;
                        shopGUI.pages.add(page);
                        page = Page("Shop", 54);
                    }

                    i++;
                }
            }
            if(i < 45) pages.add(page);


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

            shopGUI.pages.add(this.getPurchasePage(shopGUI));

            return shopGUI;

        }

    }

}