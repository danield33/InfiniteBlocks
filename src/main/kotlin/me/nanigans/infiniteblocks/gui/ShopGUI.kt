package me.nanigans.infiniteblocks.gui

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.util.*
import kotlin.collections.HashMap

class ShopGUI(pagesArr: Array<Page>, plugin: Plugin): Listener {

    var pageNum: Int = 0
    set(value){
        field = 0.coerceAtLeast(pages.size.coerceAtMost(value));
    };

    var pages: Array<Page> = pagesArr
    private set

    private var currInventory: Inventory? = null;
    private val guiOpen: HashMap<UUID, Int> = HashMap();

    init{
        plugin.server.pluginManager.registerEvents(this, plugin);
    }

    fun open(player: Player, pageNum: Int){
        if(pageNum <= this.pages.size-1){
            this.guiOpen[player.uniqueId] = pageNum;
            val inv: Inventory = this.pages[pageNum].build();
            this.currInventory = inv;
            player.openInventory(inv);
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent){
        if(event.whoClicked is Player){
            val player: Player = event.whoClicked as Player;
            val uuid = player.uniqueId
            if(this.guiOpen.containsKey(uuid)){
                event.isCancelled = true;
                val currButton: Button? = this.pages[this.guiOpen[uuid]!!].buttons[event.slot];
                if(currButton != null){
                    if(currButton.playSound)
                        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f);
                    currButton.execute(player);
                }
            }
        }
    }

    fun pageForward(item: ItemStack, player: Player){
        var page = this.guiOpen[player.uniqueId]?.plus(1);
        if (page != null) {
            if(page >= this.pages.size)
                page = 0;
            this.guiOpen.replace(player.uniqueId, page);
            this.open(player, page);
        }
    }

    fun pageBackwards(item: ItemStack, player: Player){

        var page = this.guiOpen[player.uniqueId]?.minus(1);
        if (page != null) {
            if(page < 0)
                page = this.pages.size-1;
            this.guiOpen.replace(player.uniqueId, page);
            this.open(player, page);
        };

    }

}