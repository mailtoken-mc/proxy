package io.github.qgerman2.proxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Main extends Plugin implements Listener {
    @Override
    public void onEnable() {
        this.getProxy().registerChannel("BungeeCord");
        this.getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent ev) {
        if (!ev.getTag().equals("BungeeCord")) {
            return;
        }

        if (!(ev.getSender() instanceof Server)) {
            return;
        }

        ByteArrayInputStream stream = new ByteArrayInputStream(ev.getData());
        DataInputStream in = new DataInputStream(stream);
        try {
            String playerName = in.readUTF();
            ProxiedPlayer player = this.getProxy().getPlayer(playerName);
            this.getLogger().info(playerName);
            ServerInfo target = ProxyServer.getInstance().getServerInfo("main");
            player.connect(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}