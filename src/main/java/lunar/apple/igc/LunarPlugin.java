package lunar.apple.igc;

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

    @Override
    public void initialize() {
        registerAllConfigs();
    }

    @Override
    public Collection<PluginManagedModule> getModules() {
        return List.of(

        );
    }
}
