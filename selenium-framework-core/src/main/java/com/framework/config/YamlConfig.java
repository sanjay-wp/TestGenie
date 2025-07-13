package com.framework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * YAML configuration manager using Java 17 features
 */
@Log4j2
public final class YamlConfig {
    
    /**
     * Record for configuration source
     */
    public record ConfigSource(String name, Path path) {
        public static ConfigSource of(String name, String path) {
            return new ConfigSource(name, Path.of(path));
        }
    }
    
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    private static final Map<String, Map<String, Object>> configCache = new ConcurrentHashMap<>();
    
    /**
     * Base class for configuration exceptions
     */
    public static class ConfigException extends RuntimeException {
        public ConfigException(String message) {
            super(message);
        }
        
        public ConfigException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * Exception for configuration load failures
     */
    public static class ConfigLoadException extends ConfigException {
        public ConfigLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * Exception for configuration not found
     */
    public static class ConfigNotFoundException extends ConfigException {
        public ConfigNotFoundException(String message) {
            super(message);
        }
    }
    
    private YamlConfig() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Loads configuration from YAML file
     */
    public static Map<String, Object> loadConfig(ConfigSource source) throws ConfigException {
        try {
            if (!configCache.containsKey(source.name())) {
                File configFile = source.path().toFile();
                if (!configFile.exists()) {
                    throw new ConfigNotFoundException("Configuration file not found: " + source.path());
                }
                Map<String, Object> config = yamlMapper.readValue(configFile, Map.class);
                configCache.put(source.name(), config);
            }
            return configCache.get(source.name());
        } catch (IOException e) {
            throw new ConfigLoadException("Failed to load configuration: " + source.name(), e);
        }
    }
    
    /**
     * Gets a configuration value by key
     */
    public static Optional<Object> getValue(String configName, String key) {
        Map<String, Object> config = configCache.get(configName);
        if (config == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(config.get(key));
    }
    
    /**
     * Clears the configuration cache
     */
    public static void clearCache() {
        configCache.clear();
    }
} 