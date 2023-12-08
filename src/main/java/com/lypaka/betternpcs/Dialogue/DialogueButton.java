package com.lypaka.betternpcs.Dialogue;

import java.util.List;

public class DialogueButton {

    private final List<ButtonOption> options;

    public DialogueButton (List<ButtonOption> options) {

        this.options = options;

    }

    public List<ButtonOption> getOptions() {

        return this.options;

    }

}
