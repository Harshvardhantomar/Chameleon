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
package dev.hypera.chameleon.core.users;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * An in-game player.
 */
public interface User extends ChatUser {

    /**
     * Get this {@link User}'s unique identifier.
     *
     * @return {@link User}'s unique identifier.
     */
    @NotNull UUID getUniqueId();

    /**
     * Get this {@link User}'s ping.
     *
     * @return {@link User}'s ping.
     */
    int getPing();

    /**
     * Send a chat message as this {@link User}.
     *
     * @param message Chat message to be sent.
     */
    void chat(@NotNull String message);

    /**
     * Send a plugin message to this {@link User}.
     *
     * @param channel Plugin message channel.
     * @param data    Data.
     */
    void sendData(@NotNull String channel, byte[] data);

}
