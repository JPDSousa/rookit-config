package org.rookit.config;

public interface Configuration {

    Configuration getConfig(String name);

    double getDouble(String name);

    boolean getBoolean(String name);

    int getInt(String name);

    String getString(String name);

}
