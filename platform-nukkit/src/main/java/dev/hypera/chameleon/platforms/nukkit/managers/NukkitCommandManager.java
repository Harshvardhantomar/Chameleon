/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.platforms.nukkit.managers;

import cn.nukkit.Server;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.platforms.nukkit.NukkitChameleon;
import dev.hypera.chameleon.platforms.nukkit.commands.NukkitCommand;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit {@link CommandManager} implementation.
 */
@Internal
public class NukkitCommandManager extends CommandManager {

    private final @NotNull NukkitChameleon chameleon;

    /**
     * {@link NukkitCommandManager} constructor.
     *
     * @param chameleon {@link NukkitChameleon} instance.
     */
    @Internal
    public NukkitCommandManager(@NotNull NukkitChameleon chameleon) {
        super(chameleon);
        this.chameleon = chameleon;
    }


    @Override
    protected void registerCommand(@NotNull Command command) {
        Server.getInstance().getCommandMap().register(command.getName(), new NukkitCommand(this.chameleon, command));
    }

    @Override
    protected void unregisterCommand(@NotNull Command command) {
        cn.nukkit.command.Command nukkitCommand = Server.getInstance().getCommandMap().getCommand(command.getName());
        if (null != nukkitCommand) {
            nukkitCommand.unregister(Server.getInstance().getCommandMap());
        } else {
            throw new IllegalStateException("Cannot find command with name '" + command.getName() + "'");
        }
    }

}
