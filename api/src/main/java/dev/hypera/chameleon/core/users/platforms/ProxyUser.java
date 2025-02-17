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
package dev.hypera.chameleon.core.users.platforms;

import dev.hypera.chameleon.core.annotations.PlatformSpecific;
import dev.hypera.chameleon.core.platform.Platform.Type;
import dev.hypera.chameleon.core.platform.proxy.Server;
import dev.hypera.chameleon.core.users.User;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

/**
 * In-game {@link User} on a {@link dev.hypera.chameleon.core.platform.proxy.ProxyPlatform}.
 *
 * @see dev.hypera.chameleon.core.platform.proxy.ProxyPlatform
 */
@PlatformSpecific(Type.PROXY)
public interface ProxyUser extends User {

    /**
     * Get the {@link Server} this user is currently on.
     *
     * @return optionally the {@link Server} this user is currently on, if available, otherwise empty.
     */
    @NotNull Optional<Server> getServer();

    /**
     * Attempt to switch this user to the given {@link Server}.
     *
     * @param server {@link Server} to switch this user to.
     */
    void connect(@NotNull Server server);

    /**
     * Attempt to switch this user to the given {@link Server} and then run the given callback.
     *
     * @param server   {@link Server} to switch this user to.
     * @param callback Callback to run afterwards.
     */
    void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback);

}
