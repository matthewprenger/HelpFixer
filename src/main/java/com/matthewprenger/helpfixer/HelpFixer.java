package com.matthewprenger.helpfixer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.CommandHelp;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Mod(modid = HelpFixer.MOD_ID, name = HelpFixer.MOD_ID, acceptableRemoteVersions = "*")
public class HelpFixer {

    public static final String MOD_ID = "HelpFixer";

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        event.registerServerCommand(new CommandHelp() {

            @SuppressWarnings("unchecked")
            @Override
            protected List<ICommand> getSortedPossibleCommands(ICommandSender sender) {
                List<ICommand> list = MinecraftServer.getServer().getCommandManager().getPossibleCommands(sender);
                Collections.sort(list, new Comparator<ICommand>() {

                    @Override
                    public int compare(ICommand o1, ICommand o2) {
                        return o1.getCommandName().compareTo(o2.getCommandName());
                    }
                });
                return list;
            }

        });
    }
}
