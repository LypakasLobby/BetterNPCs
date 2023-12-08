package com.lypaka.betternpcs.API;

import com.lypaka.betternpcs.Dialogue.DialogueNPC;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

/**
 * Called when BetterNPCs is about to build and open a dialogue box for a player
 * Cancelling the event will cause the dialogue box to not open
 */
@Cancelable
public class DialogueOpenEvent extends Event {

    private final ServerPlayerEntity player;
    private final DialogueNPC npc;
    private List<String> preOpenCommands;

    public DialogueOpenEvent (ServerPlayerEntity player, DialogueNPC npc) {

        this.player = player;
        this.npc = npc;
        this.preOpenCommands = npc.getPreOpenCommands();

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public DialogueNPC getNPC() {

        return this.npc;

    }

    public List<String> getPreOpenCommands() {

        return this.preOpenCommands;

    }

    public void setPreOpenCommands (List<String> commands) {

        this.preOpenCommands = commands;

    }

}
