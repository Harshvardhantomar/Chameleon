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
package dev.hypera.chameleon.core.exceptions.instantiation;

import dev.hypera.chameleon.core.exceptions.ChameleonException;

/**
 * {@link dev.hypera.chameleon.core.Chameleon} instantiation exception.
 */
public class ChameleonInstantiationException extends ChameleonException {

    private static final long serialVersionUID = 9018432641876465954L;

    /**
     * {@link ChameleonInstantiationException} constructor.
     */
    public ChameleonInstantiationException() {
        super();
    }

    /**
     * {@link ChameleonInstantiationException} constructor.
     *
     * @param message Exception message.
     */
    public ChameleonInstantiationException(String message) {
        super(message);
    }

    /**
     * {@link ChameleonInstantiationException} constructor.
     *
     * @param message Exception message.
     * @param cause   Exception cause.
     */
    public ChameleonInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@link ChameleonInstantiationException} constructor.
     *
     * @param cause Exception cause.
     */
    public ChameleonInstantiationException(Throwable cause) {
        super(cause);
    }

    protected ChameleonInstantiationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
