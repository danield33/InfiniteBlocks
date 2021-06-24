package me.nanigans.infiniteblocks.gui

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin
import java.util.*
import kotlin.collections.HashMap

class ShopGUI(private var pages: Array<Page>, plugin: Plugin): Listener {

    private var pageNum: Int = 0;
    private var currInventory: Inventory? = null;
    private val guiOpen: HashMap<UUID, Int> = HashMap();

    init{
        plugin.server.pluginManager.registerEvents(this, plugin);
    }

    fun open(player: Player, pageNum: Int){
        if(pageNum <= this.pages.size-1){
            this.pageNum = pageNum;
            this.guiOpen[player.uniqueId] = pageNum;
            val inv: Inventory = this.pages[pageNum].build();
            this.currInventory = inv;
            player.openInventory(inv);
        }else this.open(player, 0);
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
                    currButton.execute();
                }
            }
        }
    }

}