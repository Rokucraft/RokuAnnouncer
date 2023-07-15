package com.rokucraft.rokuannouncer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class RokuAnnouncer extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        long interval = getConfig().getLong("interval");
        long intervalTicks = interval * 60 * 20;
        List<Component> messages = getConfig().getStringList("messages").stream()
                .map(MiniMessage.miniMessage()::deserialize)
                .toList();

        for (int i = 0; i < messages.size(); i++) {
            Component message = messages.get(i);
            this.getServer().getScheduler().scheduleSyncRepeatingTask(
                    this,
                    () -> this.getServer().getOnlinePlayers().forEach(p -> p.sendMessage(message)),
                    i * intervalTicks,
                    intervalTicks * messages.size()
            );
        }
    }
}
