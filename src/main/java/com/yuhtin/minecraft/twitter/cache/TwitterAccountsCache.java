package com.yuhtin.minecraft.twitter.cache;

import com.sun.istack.internal.NotNull;
import com.yuhtin.minecraft.twitter.TwitterPlugin;
import com.yuhtin.minecraft.twitter.dao.repository.AccountRepository;
import com.yuhtin.minecraft.twitter.model.TwitterAccountUser;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.var;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;

@RequiredArgsConstructor
public class TwitterAccountsCache {

    private final AccountRepository accountRepository;
    private final HashMap<String, TwitterAccountUser> accounts = new HashMap<>();

    public void init() {
        accountRepository.createTable();
        TwitterPlugin.getInstance().getLogger().info("DAO do plugin iniciado com sucesso.");
    }

    /**
     * Save account in repository
     *
     * @param account to save
     */
    public void saveOne(@NotNull TwitterAccountUser account) {
        accountRepository.saveOne(account);
    }

    /**
     * Find a user in repository
     *
     * @param owner uuid
     * @return {@link TwitterAccountUser} found
     */
    @Nullable
    private TwitterAccountUser selectOne(@NotNull String owner) {
        return accountRepository.selectOne(owner);
    }

    /**
     * Used to get created accounts by name
     *
     * @param uuid player uuid
     * @return {@link TwitterAccountUser} found
     */
    @Nullable
    public TwitterAccountUser findAccountByName(@NotNull String uuid) {
        var accountUser = accounts.getOrDefault(uuid, null);
        if (accountUser == null) {
            accountUser = selectOne(uuid);
            if (accountUser != null) put(accountUser);
        }

        return accountUser;
    }

    /**
     * Used to get accounts
     * If player is online and no have account, we will create a new for them
     * but, if is offline, will return null
     *
     * @param offlinePlayer player
     * @return {@link TwitterAccountUser} found
     */
    @Nullable
    public TwitterAccountUser findAccount(@NotNull OfflinePlayer offlinePlayer) {
        if (offlinePlayer.isOnline()) {
            val player = offlinePlayer.getPlayer();
            if (player != null) return findAccount(player);
        }

        return findAccountByName(offlinePlayer.getUniqueId().toString());
    }

    /**
     * Used to get accounts
     *
     * @param player player to search
     * @return {@link TwitterAccountUser} found
     */
    @NotNull
    public TwitterAccountUser findAccount(@NotNull Player player) {
        var account = findAccountByName(player.getUniqueId().toString());
        if (account == null) {
            account = TwitterAccountUser.builder().uniqueId(player.getUniqueId()).build();

            saveOne(account);
            put(account);
        }

        return account;
    }

    /**
     * Put account directly in cache
     *
     * @param account of player
     */
    public void put(@NotNull TwitterAccountUser account) {
        accounts.put(account.getUniqueId().toString(), account);
    }

    /**
     * Flush data from cache
     */
    public void flushData() {
        accounts.values().forEach(accountRepository::saveOne);
    }

}
