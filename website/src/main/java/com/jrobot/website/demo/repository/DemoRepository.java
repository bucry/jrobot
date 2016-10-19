package com.jrobot.website.demo.repository;

import com.jrobot.website.demo.web.response.User;
import com.jrobot.core.database.HibernateAccess;
import com.jrobot.core.database.JDBCAccess;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by twcn on 10/19/16.
 */

@Repository
public class DemoRepository {

    @Inject
    private JDBCAccess jdbcAccess;

    @Inject
    private HibernateAccess hibernateAccess;

    public String demo() {
        User u = new User();
        u.setUsername("bfc");
        //hibernateAccess.persist(u);
        //jdbcAccess.execute("insert into users(username)values('fff')");
        return "demo";
    }
}
