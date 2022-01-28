package com.yuhtin.minecraft.twitter;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.yuhtin.minecraft.twitter.dao.SQLProvider;
import com.yuhtin.minecraft.twitter.dao.repository.AccountRepository;
import lombok.Getter;
import lombok.val;
import org.bukkit.plugin.java.JavaPlugin;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Token;

@Getter
public final class TwitterPlugin extends JavaPlugin {

    private Twitter twitterClient;
    private AccountRepository accountRepository;

    @Override
    public void onEnable() {
        val connector = SQLProvider.of(this).setup(null);

        accountRepository = new AccountRepository(new SQLExecutor(connector));
        accountRepository.createTable();

        val twitterFactory = new TwitterFactory();
        twitterClient = twitterFactory.getInstance();

        val consumerKey = getConfig().getString("tokens.accessTokenKey");
        val consumerSecret = getConfig().getString("tokens.secretAccessToken");
        twitterClient.setOAuthConsumer(consumerKey, consumerSecret);

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
