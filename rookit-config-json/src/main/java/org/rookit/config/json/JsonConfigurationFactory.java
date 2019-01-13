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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.rookit.config.Configuration;
import org.rookit.config.ConfigurationFactory;
import org.rookit.io.path.PathManager;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;

final class JsonConfigurationFactory implements ConfigurationFactory {

    private final PathManager pathManager;

    @Inject
    private JsonConfigurationFactory(@org.rookit.config.guice.Config final PathManager pathManager) {
        this.pathManager = pathManager;
    }

    @Override
    public Collection<String> supportedTypes() {
        return ImmutableList.of("json");
    }

    // TODO test against non-existent uris
    // TODO test against non-file uris
    @Override
    public Configuration fromURI(final URI uri) throws IOException {
        final Path path = this.pathManager.handleURI(uri);
        final Config config = ConfigFactory.parseFile(path.toFile());
        return new JsonConfiguration(config);
    }

    @Override
    public Configuration fromContent(final String content) {
        final Config config = ConfigFactory.parseReader(new StringReader(content));
        return new JsonConfiguration(config);
    }

    @Override
    public String toString() {
        return "JsonConfigurationFactory{" +
                "pathManager=" + this.pathManager +
                "}";
    }
}
