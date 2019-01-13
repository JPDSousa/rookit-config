/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.config.json;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import org.rookit.config.Configuration;
import org.rookit.config.exception.NoSuchConfigException;

final class JsonConfiguration implements Configuration {

    private final Config config;

    JsonConfiguration(final Config config) {
        this.config = config;
    }

    @Override
    public Configuration getConfig(final String name) {
        try {
            return new JsonConfiguration(this.config.getConfig(name));
        } catch (final ConfigException.Missing e) {
            throw new NoSuchConfigException(e);
        }
    }

    @Override
    public double getDouble(final String name) {
        try {
            return this.config.getDouble(name);
        } catch (final ConfigException.Missing e) {
            throw new NoSuchConfigException(e);
        }
    }

    @Override
    public boolean getBoolean(final String name) {
        try {
            return this.config.getBoolean(name);
        } catch (final ConfigException.Missing e) {
            throw new NoSuchConfigException(e);
        }
    }

    @Override
    public int getInt(final String name) {
        try {
            return this.config.getInt(name);
        } catch (final ConfigException.Missing e) {
            throw new NoSuchConfigException(e);
        }
    }

    @Override
    public String getString(final String name) {
        try {
            return this.config.getString(name);
        } catch (final ConfigException.Missing e) {
            throw new NoSuchConfigException(e);
        }
    }

    @Override
    public String toString() {
        return "JsonConfiguration{" +
                "config=" + this.config +
                "}";
    }
}
