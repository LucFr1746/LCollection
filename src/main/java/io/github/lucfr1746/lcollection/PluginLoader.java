package io.github.lucfr1746.lcollection;

import io.github.lucfr1746.lcollection.menu.FarmingMenu;
import io.github.lucfr1746.lcollection.menu.MainMenu;
import io.github.lucfr1746.llibrary.LLibrary;
import io.github.lucfr1746.llibrary.inventory.InventoryBuilder;
import io.github.lucfr1746.llibrary.util.helper.Logger;
import io.github.lucfr1746.shade.commandapi.*;
import io.github.lucfr1746.shade.commandapi.arguments.ArgumentSuggestions;
import io.github.lucfr1746.shade.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

class PluginLoader {

    private final LCollection plugin;

    private final Logger logger;

    private final List<InventoryBuilder> pluginGUis = new ArrayList<>();

    public PluginLoader(LCollection plugin) {
        this.plugin = plugin;
        this.logger = new Logger(this.plugin);

        registerCommands();
    }

    public void enable() {
        this.pluginGUis.add(new MainMenu());
        this.pluginGUis.add(new FarmingMenu());
    }

    public void disable() {
        this.pluginGUis.forEach(gui -> LLibrary.getInventoryManager().unregisterInventoryBuilder(gui));
    }

    public Logger getLogger() {
        return this.logger;
    }

    private void registerCommands() {
        new CommandAPICommand("lcollection")
                .withAliases("lcol")
                .withArguments(new StringArgument("action")
                        .replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(info ->
                                new IStringTooltip[]{
                                        StringTooltip.ofString("reload", "Reload the plugin")
                                }
                        )))
                .executes((sender, args) -> {
                    boolean isPlayer = sender instanceof Player;
                    LCollection.getPluginLogger().info("Reloading LCollection...");
                    if (isPlayer) sender.sendMessage("Reloading LCollection...");

                    Bukkit.getScheduler().runTask(this.plugin, () -> {
                        try {
                            disable();
                            enable();
                            LCollection.getPluginLogger().success("Successfully reloaded LCollection!");
                            if (isPlayer) sender.sendMessage(ChatColor.GREEN + "Successfully reloaded LCollection!");
                        } catch (Exception e) {
                            LCollection.getPluginLogger().error("There was an error while reloading LCollection!");
                            LCollection.getPluginLogger().error(e.getMessage());
                            if (isPlayer) sender.sendMessage(ChatColor.RED + "There was an error while reloading LCollection!");
                        }
                    });
                })
                .register();
    }
}
