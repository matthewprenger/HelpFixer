package com.matthewprenger.helpfixer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import org.junit.Test;

import static com.matthewprenger.helpfixer.HelpFixer.validCompareTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HelpFixerTest {

    @Test
    public void testValidCompareTo() {
        final ICommand cmd1 = new CommandBase() {
            @Override
            public String getCommandName() {
                return "foo";
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return null;
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args) {
            }

            @Override
            public int compareTo(Object p_compareTo_1_) {
                return 0;
            }
        };

        final ICommand cmd2 = new CommandBase() {
            @Override
            public String getCommandName() {
                return "foo";
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return null;
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args) {
            }

            @Override
            public int compareTo(ICommand p_compareTo_1_) {
                return 0;
            }
        };

        final ICommand cmd3 = new CommandBase() {
            @Override
            public String getCommandName() {
                return "z";
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return null;
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args) {
            }
        };

        assertFalse(validCompareTo(cmd1));
        assertFalse(validCompareTo(cmd2));
        assertTrue(validCompareTo(cmd3));
    }
}
