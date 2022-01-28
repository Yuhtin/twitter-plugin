package com.yuhtin.minecraft.twitter.command;

import com.yuhtin.minecraft.twitter.util.ColorUtil;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

public class TwitterCommand {

    @Command(
            name = "twitter",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onTwitterCommand(Context<Player> context) {
        context.sendMessage("", ColorUtil.colored(""), "");
    }

}
