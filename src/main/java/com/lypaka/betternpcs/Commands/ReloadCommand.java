package com.lypaka.betternpcs.Commands;

import com.lypaka.betternpcs.BetterNPCs;
import com.lypaka.betternpcs.ConfigGetters;
import com.lypaka.betternpcs.Dialogue.DialogueRegistry;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterNPCsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "betternpcs.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 0;

                                                    }

                                                }

                                                try {

                                                    BetterNPCs.configManager.load();
                                                    ConfigGetters.load();
                                                    List<String> npcFiles = new ArrayList<>();
                                                    for (Map.Entry<String, String> entry : ConfigGetters.npcMap.entrySet()) {

                                                        npcFiles.add(entry.getValue());

                                                    }
                                                    BetterNPCs.npcConfigManager.setFileNames(npcFiles);
                                                    BetterNPCs.npcConfigManager.load();
                                                    DialogueRegistry.loadDialogues();
                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded BetterNPCs!"), true);

                                                } catch (ObjectMappingException e) {

                                                    e.printStackTrace();

                                                }

                                                return 1;

                                            })
                            )
            );

        }

    }

}
