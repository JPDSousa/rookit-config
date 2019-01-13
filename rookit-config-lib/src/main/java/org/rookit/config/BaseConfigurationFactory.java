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
package org.rookit.config;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.config.exception.UnsupportedConfigurationException;
import org.rookit.utils.string.StringUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Set;

import static org.slf4j.LoggerFactory.*;

final class BaseConfigurationFactory implements ConfigurationFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = getLogger(BaseConfigurationFactory.class);

    private final Collection<ConfigurationFactory> factories;
    private final Collection<String> supportedTypes;
    private final StringUtils stringUtils;

    @SuppressWarnings("TypeMayBeWeakened") // due to guice multibinder
    @Inject
    private BaseConfigurationFactory(final Set<ConfigurationFactory> factories,
                                     final StringUtils stringUtils) {
        this.factories = ImmutableSet.copyOf(factories);
        this.stringUtils = stringUtils;
        this.supportedTypes = StreamEx.of(this.factories.stream())
                .map(ConfigurationFactory::supportedTypes)
                .flatMap(Collection::stream)
                .toImmutableSet();
    }

    @Override
    public Collection<String> supportedTypes() {
        //noinspection AssignmentOrReturnOfFieldWithMutableType already immutable (see initialization)
        return this.supportedTypes;
    }

    @Override
    public Configuration fromURI(final URI uri) throws UnsupportedConfigurationException, IOException {
        for (final ConfigurationFactory factory : this.factories) {
            try {
                return factory.fromURI(uri);
            } catch (final UnsupportedConfigurationException e) {
                logger.info("Configuration in {} is not supported by: {}, due to: {}", uri,
                        factory.supportedTypes(), e);
            }
        }
        throw new UnsupportedConfigurationException(supportedTypes());
    }

    @Override
    public Configuration fromContent(final String content) throws UnsupportedConfigurationException {
        for (final ConfigurationFactory factory : this.factories) {
            try {
                return factory.fromContent(content);
            } catch (final UnsupportedConfigurationException e) {
                logger.info("Configuration in {} is not supported by: {}, due to: {}",
                        this.stringUtils.preview(content, 10),
                        factory.supportedTypes(), e);
            }
        }
        throw new UnsupportedConfigurationException(supportedTypes());
    }

    @Override
    public String toString() {
        return "BaseConfigurationFactory{" +
                "factories=" + this.factories +
                ", supportedTypes=" + this.supportedTypes +
                ", stringUtils=" + this.stringUtils +
                "}";
    }
}
