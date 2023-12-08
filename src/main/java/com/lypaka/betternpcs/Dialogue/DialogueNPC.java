package com.lypaka.betternpcs.Dialogue;

import java.util.List;

public class DialogueNPC {

    private final String npcName;
    private final List<String> preOpenCommands;
    private final List<DialogueInstance> instances;

    public DialogueNPC (String npcName, List<String> preOpenCommands, List<DialogueInstance> instances) {

        this.npcName = npcName;
        this.preOpenCommands = preOpenCommands;
        this.instances = instances;

    }

    public String getNPCName() {

        return this.npcName;

    }

    public List<String> getPreOpenCommands() {

        return this.preOpenCommands;

    }

    public List<DialogueInstance> getInstances() {

        return this.instances;

    }

}
