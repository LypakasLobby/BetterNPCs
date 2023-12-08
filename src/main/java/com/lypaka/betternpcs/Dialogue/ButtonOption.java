package com.lypaka.betternpcs.Dialogue;

import java.util.List;

public class ButtonOption {

    private final String buttonID;
    private final List<String> commands;
    private final List<String> permissions;
    private final String text;
    private final int weight;

    public ButtonOption (String buttonID, List<String> commands, List<String> permissions, String text, int weight) {

        this.buttonID = buttonID;
        this.commands = commands;
        this.permissions = permissions;
        this.text = text;
        this.weight = weight;

    }

    public String getButtonID() {

        return this.buttonID;

    }

    public List<String> getCommands() {

        return this.commands;

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
