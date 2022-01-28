package com.yuhtin.minecraft.twitter.dao.repository;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.yuhtin.minecraft.twitter.dao.repository.adapter.AccountAdapter;
import com.yuhtin.minecraft.twitter.model.TwitterAccountUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class AccountRepository {

    private static final String TABLE = "twitter_userdata";

    @Getter
    private final SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "owner CHAR(36) NOT NULL PRIMARY KEY," +
                "token LONGTEXT," +
                "tokenSecret TEXT," +
                "userID LONG" +
                ");"
        );
    }

    public void recreateTable() {
        sqlExecutor.updateQuery("DELETE FROM " + TABLE);
        createTable();
    }

    private TwitterAccountUser selectOneQuery(String query) {
        return sqlExecutor.resultOneQuery(
                "SELECT * FROM " + TABLE + " " + query,
                statement -> {
                },
                AccountAdapter.class
        );
    }

    public TwitterAccountUser selectOne(String owner) {
        return selectOneQuery("WHERE owner = '" + owner + "'");
    }

    public Set<TwitterAccountUser> selectAll(String query) {
        return sqlExecutor.resultManyQuery(
                "SELECT * FROM " + TABLE + " " + query,
                k -> {
                },
                AccountAdapter.class
        );
    }

    public void saveOne(TwitterAccountUser account) {
        this.sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?,?,?,?,?)", TABLE),
                statement -> {
                    statement.set(1, account.getUniqueId().toString());
                    statement.set(2, account.getToken());
                    statement.set(3, account.getTokenSecret());
                    statement.set(4, account.getUserID());
                }
        );
    }

}
