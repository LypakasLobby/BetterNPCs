package com.lypaka.betternpcs.Dialogue;

import com.google.common.reflect.TypeToken;
import com.lypaka.betternpcs.BetterNPCs;
import com.lypaka.betternpcs.ConfigGetters;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogueRegistry {

    public static Map<String, DialogueNPC> dialogueMap = new HashMap<>();

    public static void loadDialogues() throws ObjectMappingException {

        dialogueMap = new HashMap<>();
        List<String> npcFiles = new ArrayList<>();
        for (Map.Entry<String, String> entry : ConfigGetters.npcMap.entrySet()) {

            npcFiles.add(entry.getValue());

        }
        for (int i = 0; i < npcFiles.size(); i++) {

            String file = npcFiles.get(i);
            String npcName = BetterNPCs.npcConfigManager.getConfigNode(i, "General-Settings", "Name").getString();
            List<String> preOpenCommands = new ArrayList<>();
            if (!BetterNPCs.npcConfigManager.getConfigNode(i, "General-Settings", "Commands").isVirtual()) {

                preOpenCommands = BetterNPCs.npcConfigManager.getConfigNode(i, "General-Settings", "Commands").getList(TypeToken.of(String.class));

            }
            List<DialogueInstance> instances = new ArrayList<>();
            Map<String, Map<String, String>> map = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
            for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {

                String dialogueID = entry.getKey();
                Map<String, Map<String, String>> buttonMap = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Buttons").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
                List<DialogueButton> buttons = new ArrayList<>();
                List<ButtonOption> options = new ArrayList<>();
                int buttonOptionCount = 0;
                for (Map.Entry<String, Map<String, String>> buttonEntry : buttonMap.entrySet()) {

                    String buttonID = buttonEntry.getKey();
                    List<String> buttonCommands = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Buttons", buttonID, "Commands").getList(TypeToken.of(String.class));
                    List<String> buttonPermissions = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Buttons", buttonID, "Permissions").getList(TypeToken.of(String.class));
                    String buttonText = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Buttons", buttonID, "Text").getString();
                    int buttonWeight = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Buttons", buttonID, "Weight").getInt();
                    ButtonOption buttonOption = new ButtonOption(buttonID, buttonCommands, buttonPermissions, buttonText, buttonWeight);
                    options.add(buttonOption);
                    buttonOptionCount++;

                }

                DialogueButton button = new DialogueButton(options);
                buttons.add(button);

                List<String> instancePermissions = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Permissions").getList(TypeToken.of(String.class));
                String instanceText = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Text").getString();
                int instanceWeight = BetterNPCs.npcConfigManager.getConfigNode(i, "Messages", dialogueID, "Weight").getInt();

                DialogueInstance instance = new DialogueInstance(dialogueID, buttons, instancePermissions, instanceText, instanceWeight);
                instances.add(instance);

            }

            DialogueNPC npc = new DialogueNPC(npcName, preOpenCommands, instances);
            dialogueMap.put(file, npc);

        }

    }

    public static DialogueInstance getInstanceFromWeight (DialogueNPC npc, int weight) {

        DialogueInstance instance = null;
        for (DialogueInstance i : npc.getInstances()) {

            if (i.getWeight() == weight) {

                instance = i;
                break;

            }

        }

        return instance;

    }

    public static ButtonOption getButtonOptionFromWeight (DialogueButton button, int weight) {

        ButtonOption option = null;
        for (ButtonOption buttonOption : button.getOptions()) {

            if (buttonOption.getWeight() == weight) {

                option = buttonOption;
                break;

            }

        }

        return option;

    }

}
