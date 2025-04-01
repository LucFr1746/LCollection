package io.github.lucfr1746.lcollection;

import io.github.lucfr1746.llibrary.util.helper.FileAPI;
import io.github.lucfr1746.llibrary.util.helper.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public final class LCollection extends JavaPlugin {

    private static LCollection plugin;
    private static PluginLoader pluginLoader;

    @Override
    public void onLoad() {
        plugin = this;
        pluginLoader = new PluginLoader(this);
    }

    @Override
    public void onEnable() {
        if (pluginLoader == null)
            pluginLoader = new PluginLoader(this);
        pluginLoader.enable();
    }

    @Override
    public void onDisable() {
        if (pluginLoader == null)
            pluginLoader = new PluginLoader(this);
        pluginLoader.disable();
    }

    public static LCollection getInstance() {
        return plugin;
    }

    public static Logger getPluginLogger() {
        return pluginLoader.getLogger();
    }

    public static FileAPI getPluginFile() {
        return pluginLoader.getFile();
    }
}
