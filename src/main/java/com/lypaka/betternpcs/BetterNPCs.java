package com.lypaka.betternpcs;

import com.lypaka.betternpcs.Dialogue.DialogueRegistry;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("betternpcs")
public class BetterNPCs {

    public static final String MOD_ID = "betternpcs";
    public static final String MOD_NAME = "BetterNPCs";
    public static final Logger logger = LogManager.getLogger();
    public static BasicConfigManager configManager;
    public static ComplexConfigManager npcConfigManager;

    public BetterNPCs() throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/betternpcs"));
        String[] files = new String[]{"betternpcs.conf"};
        configManager = new BasicConfigManager(files, dir, BetterNPCs.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load();
        List<String> npcFiles = new ArrayList<>();
        for (Map.Entry<String, String> entry : ConfigGetters.npcMap.entrySet()) {

            npcFiles.add(entry.getValue());

        }
        npcConfigManager = new ComplexConfigManager(npcFiles, "dialogue-files", "dialogueTemplate.conf", dir, BetterNPCs.class, MOD_NAME, MOD_ID, logger);
        npcConfigManager.init();
        DialogueRegistry.loadDialogues();

    }

}
