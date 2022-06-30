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
package dev.hypera.chameleon.platforms.velocity;

import dev.hypera.chameleon.core.ChameleonBootstrap;
import dev.hypera.chameleon.core.ChameleonPlugin;
import dev.hypera.chameleon.core.data.PluginData;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.logging.ChameleonLogger;
import dev.hypera.chameleon.core.logging.impl.ChameleonSlf4jLogger;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link ChameleonBootstrap} implementation.
 */
public final class VelocityChameleonBootstrap extends ChameleonBootstrap<VelocityChameleon> {

    private final @NotNull Class<? extends ChameleonPlugin> chameleonPlugin;
    private final @NotNull VelocityPlugin velocityPlugin;
    private final @NotNull PluginData pluginData;

    @Internal
    VelocityChameleonBootstrap(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull VelocityPlugin velocityPlugin, @NotNull PluginData pluginData) {
        this.chameleonPlugin = chameleonPlugin;
        this.velocityPlugin = velocityPlugin;
        this.pluginData = pluginData;
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    protected @NotNull VelocityChameleon loadInternal() throws ChameleonInstantiationException {
        VelocityChameleon chameleon = new VelocityChameleon(this.chameleonPlugin, this.velocityPlugin, this.pluginData);
        chameleon.onLoad();
        return chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    protected @NotNull ChameleonLogger createLogger() {
        return new ChameleonSlf4jLogger(this.velocityPlugin.getLogger());
    }

}