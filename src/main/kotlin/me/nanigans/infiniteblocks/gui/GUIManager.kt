package me.nanigans.infiniteblocks.gui

import org.bukkit.entity.Player
import java.util.*

class GUIManager {

    val GUIS: ArrayList<ShopGUI> = arrayListOf();
    val playerGUI: HashMap<UUID, Int> = hashMapOf();

    fun openGUI(player: Player, number: Int, pageNumber: Int){

        if(playerGUI.containsKey(player.uniqueId)){
            GUIS[playerGUI[player.uniqueId]!!].guiOpen.remove(player.uniqueId);
        }
        playerGUI[player.uniqueId] = number;

        val guiToOpen = GUIS[number];
        guiToOpen.open(player, pageNumber);

    }

    fun openGUI(player: Player, number: Int){
        this.openGUI(player, number, 0)
    }

}