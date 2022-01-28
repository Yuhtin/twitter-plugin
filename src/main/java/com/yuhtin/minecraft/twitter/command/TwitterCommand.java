package com.yuhtin.minecraft.twitter.command;

import com.yuhtin.minecraft.twitter.TwitterPlugin;
import com.yuhtin.minecraft.twitter.cache.TwitterAccountsCache;
import com.yuhtin.minecraft.twitter.util.ColorUtil;
import com.yuhtin.minecraft.twitter.util.TwitterUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import twitter4j.TwitterException;

@RequiredArgsConstructor
public class TwitterCommand {

    private final TwitterAccountsCache cache;

    @Command(
            name = "twitter",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onTwitterCommand(Context<Player> context) {
        context.sendMessage(ColorUtil.colored(
                "",
                "&6&lTWITTER &8- &fComandos",
                " &f/twitter associar &8- &7Associe seu twitter ao nosso sistema",
                " &f/twitter pin <codigo> &8- &7Confirmar a vinculação de seu twitter",
                " &f/twitter recompensas &8- &7Fazer alguns passos para receber recompensas",
                ""
        ));
    }

    @Command(
            name = "twitter.associar",
            async = true
    )
    public void onAssociateCommand(Context<Player> context) {
        val player = context.getSender();
        val account = cache.findAccount(player);
        try {
            val userTwitter = account.getUserTwitter();
            if (userTwitter != null) {
                player.sendMessage(ColorUtil.colored("&cVocê já vinculou sua conta ao twitter &n" + userTwitter.getScreenName()));
                return;
            }

            val twitterClient = TwitterPlugin.getInstance().getTwitterClient();
            val requestToken = twitterClient.getOAuthRequestToken();

            val authorizationURL = requestToken.getAuthorizationURL();
            TwitterUtil.sendLinkMessage(player, authorizationURL);

        } catch (TwitterException exception) {
            player.sendMessage(ColorUtil.colored("&cA API do twitter se encontra offline no momento."));
        }

    }

    @Command(
            name = "twitter.pin",
            usage = "/twitter pin <código>",
            async = true
    )
    public void onPinCommand(Context<Player> context, int pinCode) {

    }
}
