package com.lypaka.betternpcs.Listeners;

import com.lypaka.betternpcs.ConfigGetters;
import com.lypaka.betternpcs.Dialogue.*;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class InteractionListener {

    @SubscribeEvent
    public void onEntityInteract (PlayerInteractEvent.EntityInteract event) {

        if (event.getSide() == LogicalSide.CLIENT) return;
        if (event.getHand() == Hand.OFF_HAND) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        String worldName = WorldMap.getWorldName(player);
        Entity entity = event.getTarget();
        int x = entity.getPosition().getX();
        int y = entity.getPosition().getY();
        int z = entity.getPosition().getZ();
        String location = worldName + "," + x + "," + y + "," + z;
        if (ConfigGetters.npcMap.containsKey(location)) {

            DialogueNPC npc = DialogueRegistry.dialogueMap.get(ConfigGetters.npcMap.get(location));
            DialogueBuilder.openDialogueBox(player, npc, event);

        }

    }

    @SubscribeEvent
    public void onBlockInteract (PlayerInteractEvent.RightClickBlock event) {

        if (event.getSide() == LogicalSide.CLIENT) return;
        if (event.getHand() == Hand.OFF_HAND) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        String worldName = WorldMap.getWorldName(player);
        int x = event.getPos().getX();
        int y = event.getPos().getY();
        int z = event.getPos().getZ();
        String location = worldName + "," + x + "," + y + "," + z;
        if (ConfigGetters.npcMap.containsKey(location)) {

            DialogueNPC npc = DialogueRegistry.dialogueMap.get(ConfigGetters.npcMap.get(location));
            DialogueBuilder.openDialogueBox(player, npc, event);

        }

    }

}
