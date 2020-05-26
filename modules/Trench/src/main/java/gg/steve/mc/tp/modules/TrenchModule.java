package gg.steve.mc.tp.modules;

import gg.steve.mc.tp.modules.listener.JoinListener;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class TrenchModule extends ToolsPlusModule {

    @Override
    public ModuleType getModuleType() {
        return ModuleType.TRENCH;
    }

    public List<Listener> getListeners() {
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new JoinListener());
        return listeners;
    }
}
