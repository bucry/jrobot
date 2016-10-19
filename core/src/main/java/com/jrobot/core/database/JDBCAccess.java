package com.jrobot.core.database;

import com.jrobot.core.utils.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * @author bfc
 */
public class JDBCAccess {
    private final Logger logger = LoggerFactory.getLogger(JDBCAccess.class);
    private JdbcTemplate jdbcTemplate;

    public <T> List<T> find(String sql, RowMapper<T> rowMapper, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.query(sql, params, rowMapper);
        } finally {
            logger.debug("find, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public <T> T findUniqueResult(String sql, RowMapper<T> rowMapper, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } finally {
            logger.debug("findUniqueResult, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public int findInteger(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            Number number = jdbcTemplate.queryForObject(sql, params, Integer.class);
            return (number != null ? number.intValue() : 0);
        } finally {
            logger.debug("findInteger, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public boolean findBooleanFiled(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForObject(sql, params, new RowMapper<Boolean>() {
                @Override
                public Boolean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return resultSet.getBoolean(1);
                }
            });
        } catch(Exception e) {
        	return false;
        } finally {
            logger.debug("findString, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }


    public Long findLongFiled(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForObject(sql, params, new RowMapper<Long>() {
                @Override
                public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return resultSet.getLong(1);
                }
            });
        } finally {
            logger.debug("findString, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public int findIntegerFiled(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForObject(sql, params, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return resultSet.getInt(1);
                }
            });
        } catch (Exception e) {
        	return -1;
        }  finally {
            logger.debug("findString, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public String findString(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForObject(sql, params, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return resultSet.getString(1);
                }
            });
        } finally {
            logger.debug("findString, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public int execute(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.update(sql, params);
        } finally {
            logger.debug("execute, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }

    public int[] batchExecute(String sql, List<Object[]> params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.batchUpdate(sql, params);
        } finally {
            logger.debug("batchExecute, sql={}, params={}, elapsedTime={}", sql, params, watch.elapsedTime());
        }
    }


    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
