package com.HeroxWar.HeroxCore;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class Events implements Listener {

    public Events() {

    }

    public Events(Plugin pluginInstance, List<String> ignoredEvents) {
        method(pluginInstance, ignoredEvents);
    }

    public void method(Plugin pluginInstance, List<String> ignoredEvents) {
        System.out.println("Enabling EventLogger...");

        Listener listener = new Listener() {
        };

        EventExecutor executor = (ignored, event) -> {
            if (!ignoredEvents.contains(event.getEventName())) {
                System.out.println("Event got fired: " + event.getEventName());
            }
        };

        ScanResult scanResult = initializeScan().scan();

        try (scanResult) {
            ClassInfoList events = getListEvents(scanResult);

            try {
                for (ClassInfo event : events) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Event> eventClass =
                            (Class<? extends Event>) Class.forName(event.getName());

                    // Only register if the event class has getHandlers()
                    if (Arrays.stream(eventClass.getDeclaredMethods()).anyMatch(method ->
                            method.getParameterCount() == 0 && method.getName().equals("getHandlers"))) {

                        Bukkit.getPluginManager().registerEvent(
                                eventClass,
                                listener,
                                EventPriority.MONITOR,
                                executor,
                                pluginInstance,
                                false
                        );
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new AssertionError("Scanned class wasn't found", e);
            }

            // Logging discovered events
            discoveredEvents(events);
        }
    }

    public ClassGraph initializeScan() {
        // limit scan to Bukkit events
        return new ClassGraph()
                .enableClassInfo()
                .acceptPackages("org.bukkit.event");
    }

    public ClassInfoList getListEvents(ScanResult scanResult) {
        return scanResult.getClassInfo(Event.class.getName())
                .getSubclasses()
                .filter(info -> !info.isAbstract());
    }

    public void discoveredEvents(ClassInfoList events) {
        // Logging discovered events
        String[] eventNames = events.stream()
                .map(info -> info.getName().substring(info.getName().lastIndexOf('.') + 1))
                .toArray(String[]::new);

        System.out.println("List of events: " + String.join(", ", eventNames));
        System.out.println("Events found: " + events.size());
        System.out.println("HandlerList size: " + HandlerList.getHandlerLists().size());
    }

}
