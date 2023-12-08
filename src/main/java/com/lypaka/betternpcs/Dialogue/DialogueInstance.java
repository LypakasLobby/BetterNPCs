package com.lypaka.betternpcs.Dialogue;

import java.util.List;

public class DialogueInstance {

    private final String dialogueID;
    private final List<DialogueButton> buttons;
    private final List<String> permissions;
    private final String text;
    private final int weight;

    public DialogueInstance (String dialogueID, List<DialogueButton> buttons, List<String> permissions, String text, int weight) {

        this.dialogueID = dialogueID;
        this.buttons = buttons;
        this.permissions = permissions;
        this.text = text;
        this.weight = weight;

    }

    public String getDialogueID() {

        return this.dialogueID;

    }

    public List<DialogueButton> getButtons() {

        return this.buttons;

    }

    public List<String> getPermissions() {

        return this.permissions;

    }

    public String getText() {

        return this.text;

    }

    public int getWeight() {

        return this.weight;

    }

}
