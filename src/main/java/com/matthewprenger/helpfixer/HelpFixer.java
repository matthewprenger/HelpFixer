package com.matthewprenger.helpfixer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandHelp;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Mod(
        modid = HelpFixer.MOD_ID,
        name = HelpFixer.MOD_NAME,
        acceptableRemoteVersions = "*"
)
public final class HelpFixer {

    private static final Logger log = LogManager.getLogger();

    static final String MOD_ID = "helpfixer";
    static final String MOD_NAME = "HelpFixer";

    @Mod.EventHandler
    public void onServerStarting(final FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandHelp() {

            @Override
            protected List<ICommand> getSortedPossibleCommands(final ICommandSender sender, final MinecraftServer server) {
                final List<ICommand> list = server.getCommandManager().getPossibleCommands(sender);
                final Iterator<ICommand> iterator = list.iterator();

                while (iterator.hasNext()) {
                    final ICommand command = iterator.next();

                    try {
                        if (command.getName() == null) {
                            log.warn("Identified command with null name, Ignoring: {}", command.getClass().getName());
                            iterator.remove();
                        } else if (command.getUsage(sender) == null) {
                            log.warn("Identified command with null usage, Ignoring: {}", command.getClass().getName());
                            iterator.remove();
                        }
                    } catch (Exception e) {
                        log.warn("Failed to test command '{}'", command, e);
                    }
                }

                Collections.sort(list, new Comparator<ICommand>() {

                    @Override
                    public int compare(ICommand o1, ICommand o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                return list;
            }

        });
    }

    @Mod.EventHandler
    public void onServerStarted(final FMLServerStartedEvent event) {
        Collection<ICommand> commands = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().getCommands().values();

        for (final ICommand command : commands) {
            if (!(validCompareTo(command))) {
                log.warn("Command {} incorrectly overrides compareTo: %s", command.getName(), command.getClass().getName());
            }
        }
    }

    /**
     * Checks to see if an {@link net.minecraft.command.ICommand ICommand} has a valid compareTo method
     *
     * @param command the command
     * @return {@code true} if the compareTo method is valid, {@code false} if not
     */

    static boolean validCompareTo(@Nonnull final ICommand command) {
        try {
            return command.compareTo(testCmd1) != command.compareTo(testCmd2);
        } catch (Exception e) {
            log.warn("Failed to test command '{}' for a valid compareTo", command, e);
            return true; // True because we don't know that the impl is bad, just that it throws an exception
        }
    }

    private static final ICommand testCmd1 = new CommandBase() {
        @Override
        public String getName() {
            return "a";
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public String getUsage(final ICommandSender sender) {
            return null;
        }

        @Override
        public void execute(final MinecraftServer server, final ICommandSender sender, String[] args) {
        }
    };

    private static final ICommand testCmd2 = new CommandBase() {
        @Override
        public String getName() {
            return "z";
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public String getUsage(final ICommandSender sender) {
            return null;
        }

        @Override
        public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        }
    };
}
