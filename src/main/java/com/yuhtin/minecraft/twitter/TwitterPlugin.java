package com.yuhtin.minecraft.twitter;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.yuhtin.minecraft.twitter.cache.TwitterAccountsCache;
import com.yuhtin.minecraft.twitter.command.TwitterCommand;
import com.yuhtin.minecraft.twitter.dao.SQLProvider;
import com.yuhtin.minecraft.twitter.dao.repository.AccountRepository;
import lombok.Getter;
import lombok.val;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.java.JavaPlugin;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Getter
public final class TwitterPlugin extends JavaPlugin {

    private Twitter twitterClient;
    private AccountRepository accountRepository;
    private TwitterAccountsCache cache;

    @Override
    public void onEnable() {
        val connector = SQLProvider.of(this).setup(null);

        accountRepository = new AccountRepository(new SQLExecutor(connector));
        cache = new TwitterAccountsCache(accountRepository);
        cache.init();

        val twitterFactory = new TwitterFactory();
        twitterClient = twitterFactory.getInstance();

        val consumerKey = getConfig().getString("tokens.accessTokenKey");
        val consumerSecret = getConfig().getString("tokens.secretAccessToken");
        twitterClient.setOAuthConsumer(consumerKey, consumerSecret);

        val bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(new TwitterCommand(cache));

        getLogger().info("Plugin started & TwitterClient connected successfully.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TwitterPlugin getInstance() {
        return JavaPlugin.getPlugin(TwitterPlugin.class);
    }
}
