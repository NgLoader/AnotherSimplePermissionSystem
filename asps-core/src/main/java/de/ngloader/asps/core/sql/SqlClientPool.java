package de.ngloader.asps.core.sql;

import java.net.URI;
import java.util.Collections;
import java.util.ServiceConfigurationError;

import org.hibernate.reactive.pool.impl.DefaultSqlClientPool;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.spi.MySQLDriver;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnectOptions;
import io.vertx.sqlclient.spi.Driver;

/**
 * @author Ingrim4
 */
@SuppressWarnings("serial")
public class SqlClientPool extends DefaultSqlClientPool {

	@Override
	protected Pool createPool(URI uri, SqlConnectOptions connectOptions, PoolOptions poolOptions, Vertx vertx) {
		try {
			return Pool.pool(vertx, connectOptions, poolOptions);
		} catch (ServiceConfigurationError e) {
			final Driver driver = new MySQLDriver();
			return driver.createPool(vertx, Collections.singletonList(connectOptions), poolOptions);
		}
	}
}