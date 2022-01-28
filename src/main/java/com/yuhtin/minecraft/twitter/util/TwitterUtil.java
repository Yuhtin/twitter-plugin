package com.yuhtin.minecraft.twitter.util;

import lombok.val;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class TwitterUtil {

    public static void sendLinkMessage(Player player, String authorizationURL) {
        val infoComponent = new TextComponent(" Vincule sua conta do twitter com o nosso servidor");
        val clickComponent = new TextComponent(ColorUtil.colored(" &aClique &lAQUI &apara vincular sua conta"));
        val pinComponent = new TextComponent(" Utilize /twitter pin <código> após autorizar o aplicativo");

        val hoverText = new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ColorUtil.colored("&7Este link irá levar até o twitter")));
        val clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, authorizationURL);

        infoComponent.setColor(ChatColor.GREEN);
        pinComponent.setColor(ChatColor.YELLOW);

        infoComponent.setHoverEvent(hoverText);
        clickComponent.setHoverEvent(hoverText);
        pinComponent.setHoverEvent(hoverText);

        infoComponent.setClickEvent(clickEvent);
        clickComponent.setClickEvent(clickEvent);
        pinComponent.setClickEvent(clickEvent);

        val spigotPlayer = player.spigot();

        player.sendMessage("");
        spigotPlayer.sendMessage(infoComponent);
        spigotPlayer.sendMessage(clickComponent);
        spigotPlayer.sendMessage(pinComponent);
        player.sendMessage("");
    }

}
