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
package dev.hypera.chameleon.platforms.velocity.user;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.hypera.chameleon.core.adventure.AbstractReflectedAudience;
import dev.hypera.chameleon.core.platform.proxy.Server;
import dev.hypera.chameleon.core.users.platforms.ProxyUser;
import dev.hypera.chameleon.platforms.velocity.VelocityChameleon;
import dev.hypera.chameleon.platforms.velocity.platform.objects.VelocityServer;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link ProxyUser} implementation.
 */
@Internal
public class VelocityUser extends AbstractReflectedAudience implements ProxyUser {

    private final @NotNull VelocityChameleon chameleon;
    private final @NotNull Player player;

    /**
     * {@link VelocityUser} constructor.
     *
     * @param chameleon {@link VelocityChameleon} instance.
     * @param player    {@link Player} to be wrapped.
     */
    @Internal
    public VelocityUser(@NotNull VelocityChameleon chameleon, @NotNull Player player) {
        super(player);
        this.chameleon = chameleon;
        this.player = player;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.player.getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPing() {
        return this.player.getPing() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) this.player.getPing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chat(@NotNull String message) {
        this.player.spoofChatInput(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        this.player.sendPluginMessage(MinecraftChannelIdentifier.from(channel), data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.player.hasPermission(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<Server> getServer() {
        return this.player.getCurrentServer().map(s -> new VelocityServer(this.chameleon, s.getServer()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server) {
        this.player.createConnectionRequest(((VelocityServer) server).getVelocity()).fireAndForget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        this.player.createConnectionRequest(((VelocityServer) server).getVelocity()).connect().whenComplete((result, ex) -> {
            callback.accept(result.isSuccessful(), ex);
        });
    }

}
