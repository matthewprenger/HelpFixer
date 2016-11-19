package com.matthewprenger.helpfixer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.junit.Test;

import static com.matthewprenger.helpfixer.HelpFixer.validCompareTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HelpFixerTest {

    @Test
    public void testValidCompareTo() {
        final ICommand cmd1 = new CommandBase() {
            @Override
            public String getName() {
                return "foo";
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public String getUsage(ICommandSender sender) {
                return null;
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
            }

            @Override
            public int compareTo(ICommand command) {
                return 0;
            }
        };

        final ICommand cmd2 = new CommandBase() {
            @Override
            public String getName() {
                return "z";
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public String getUsage(ICommandSender sender) {
                return null;
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
            }
        };

        assertFalse(validCompareTo(cmd1));
        assertTrue(validCompareTo(cmd2));
    }
}
