package com.yuhtin.minecraft.twitter.dao.repository.adapter;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import com.yuhtin.minecraft.twitter.model.TwitterAccountUser;

public final class AccountAdapter implements SQLResultAdapter<TwitterAccountUser> {

    @Override
    public TwitterAccountUser adaptResult(SimpleResultSet resultSet) {
        return TwitterAccountUser.builder().build();
    }

}
