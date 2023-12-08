package com.lypaka.betternpcs;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;

public class ConfigGetters {

    public static Map<String, String> npcMap;

    public static void load() throws ObjectMappingException {

        npcMap = BetterNPCs.configManager.getConfigNode(0, "NPCs").getValue(new TypeToken<Map<String, String>>() {});

    }

}
