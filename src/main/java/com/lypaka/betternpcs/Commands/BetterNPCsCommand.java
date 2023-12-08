package com.lypaka.betternpcs.Commands;

import com.lypaka.betternpcs.BetterNPCs;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = BetterNPCs.MOD_ID)
public class BetterNPCsCommand {

    public static List<String> ALIASES = Arrays.asList("betternpcs", "bnpcs", "npcs");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
