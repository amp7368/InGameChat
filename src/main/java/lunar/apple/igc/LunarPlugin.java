package lunar.apple.igc;

import lunar.apple.igc.discord.PluginIgc;
import org.slf4j.Logger;
import plugin.util.plugin.plugin.util.plugin.PluginManaged;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.manage.PluginManagedConfigRegister;

import java.util.Collection;
import java.util.List;

public class LunarPlugin extends PluginManaged implements PluginManagedConfigRegister {
    private static LunarPlugin instance;

    public LunarPlugin() {
        instance = this;
    }

    public static LunarPlugin get() {
        return instance;
    }

    public static Logger logger() {
        return get().getSLF4JLogger();
    }

    @Override
    public void initialize() {
        registerAllConfigs();
    }

    @Override
    public Collection<PluginManagedModule> getModules() {
        return List.of(
                new PluginIgc()
        );
    }
}
