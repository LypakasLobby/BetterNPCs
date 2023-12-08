package com.lypaka.betternpcs.Dialogue;

import com.google.common.collect.Lists;
import com.lypaka.betternpcs.API.DialogueOpenEvent;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.pixelmonmod.pixelmon.api.dialogue.Choice;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

import java.util.*;

public class DialogueBuilder {

    public static void openDialogueBox (ServerPlayerEntity player, DialogueNPC npc, Event event) {

        DialogueOpenEvent openEvent = new DialogueOpenEvent(player, npc);
        MinecraftForge.EVENT_BUS.post(openEvent);
        if (openEvent.isCanceled()) return;
        if (!openEvent.getPreOpenCommands().isEmpty()) {

            for (String c : openEvent.getPreOpenCommands()) {

                player.getServer().getCommandManager().handleCommand(
                        player.getServer().getCommandSource(),
                        c.replace("%player%", player.getName().getString())
                );

            }

        }
        List<Integer> instanceWeights = new ArrayList<>();
        for (DialogueInstance instances : npc.getInstances()) {

            instanceWeights.add(instances.getWeight());

        }

        Collections.sort(instanceWeights);
        Map<Integer, Boolean> instanceMap = new HashMap<>();
        for (int i = 0; i < instanceWeights.size(); i++) {

            DialogueInstance instance = DialogueRegistry.getInstanceFromWeight(npc, i);
            if (instance != null) {

                instanceMap.put(i, true);
                for (String p : instance.getPermissions()) {

                    if (!PermissionHandler.hasPermission(player, p)) {

                        instanceMap.put(i, false);

                    }

                }

            }

        }

        List<DialogueInstance> instancesCanOpen = new ArrayList<>(instanceMap.size());
        int index = 0;
        for (Map.Entry<Integer, Boolean> entry : instanceMap.entrySet()) {

            if (entry.getValue()) {

                instancesCanOpen.add(index, DialogueRegistry.getInstanceFromWeight(npc, entry.getKey()));
                index++;

            }

        }

        if (instancesCanOpen.size() == 0) return;
        event.setCanceled(true);
        List<ButtonOption> buttons = new ArrayList<>();
        DialogueInstance selectedInstance = null;
        for (int i = 0; i < instancesCanOpen.size(); i++) {

            DialogueInstance instance = instancesCanOpen.get(i);
            for (DialogueButton button : instance.getButtons()) {

                List<Integer> optionWeights = new ArrayList<>();
                for (ButtonOption option : button.getOptions()) {

                    optionWeights.add(option.getWeight());

                }

                Collections.sort(optionWeights);
                for (int b = 0; b < optionWeights.size(); b++) {

                    ButtonOption option = DialogueRegistry.getButtonOptionFromWeight(button, b);
                    if (option != null) {

                        boolean add = true;
                        for (String p : option.getPermissions()) {

                            if (!PermissionHandler.hasPermission(player, p)) {

                                add = false;
                                break;

                            }

                        }

                        if (add) {

                            buttons.add(option);

                        }

                    }

                }

            }

            if (buttons.size() > 0) {

                selectedInstance = instance;
                break;

            }

        }

        if (buttons.size() == 0) return;
        Dialogue.DialogueBuilder builder = Dialogue.builder();
        builder.setName(FancyText.getFormattedString(npc.getNPCName()));
        builder.setText(FancyText.getFormattedString(selectedInstance.getText()));

        for (ButtonOption button : buttons) {

            builder.addChoice(new Choice(FancyText.getFormattedString(button.getText()), (choiceEvent) -> {

                player.getServer().deferTask(() -> {

                    for (String c : button.getCommands()) {

                        player.getServer().getCommandManager().handleCommand(
                                player.getServer().getCommandSource(),
                                c.replace("%player%", player.getName().getString())
                        );

                    }
                    player.closeContainer();

                });

            }));

        }

        Dialogue.setPlayerDialogueData(player, Lists.newArrayList(builder.build()), true);

    }

}
