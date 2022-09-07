package de.ngloader.asps.core.sql;

import java.util.concurrent.atomic.AtomicReference;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.provider.Settings;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.reactive.stage.Stage.Query;
import org.hibernate.reactive.stage.Stage.SessionFactory;

/**
 * @author Ingrim4
 */
public class SqlService implements AutoCloseable {

	private final AtomicReference<State> state = new AtomicReference<>(State.CONFIGURATION);
	private final Configuration configuration;

	private Stage.SessionFactory sessionFactory;

	public SqlService(SqlConfig config) {
		this.configuration = new Configuration()
				.setProperty(Settings.URL, String.format("jdbc:mariadb://%s/%s", config.host, config.database))
				.setProperty(Settings.USER, config.username).setProperty(Settings.PASS, config.password)
				.setProperty(Settings.FORMAT_SQL, "true").setProperty(Settings.HBM2DDL_AUTO, "none")
				.setProperty(Settings.STATEMENT_BATCH_SIZE, "128").setProperty(Settings.POOL_SIZE, "16")
				.setProperty(Settings.POOL_IDLE_TIMEOUT, "540000");
	}

	public void initialize() {
		if (this.state.compareAndSet(State.CONFIGURATION, State.OPEN)) {
			StandardServiceRegistry registry = new ReactiveServiceRegistryBuilder()
					.applySettings(this.configuration.getProperties()).build();

			try {
				this.sessionFactory = this.configuration.buildSessionFactory(registry)
						.unwrap(Stage.SessionFactory.class);
			} catch (Exception e) {
				e.printStackTrace();
				ReactiveServiceRegistryBuilder.destroy(registry);
			}
		}
	}

	public void addAnnotatedClass(Class<?> annotatedClass) {
		if (this.state.get() == State.CONFIGURATION) {
			this.configuration.addAnnotatedClass(annotatedClass);
		} else {
			throw new IllegalStateException("SqlService already open");
		}
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public <T> Query<T> createQuery(Class<T> entity, CriteriaQueryBuilder<T> queryBuilder) {
		CriteriaBuilder builder = this.sessionFactory.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entity);
		Root<T> from = criteria.from(entity);
		return queryBuilder.build(builder, criteria, from);
	}

	@Override
	public void close() throws Exception {
		if (this.state.compareAndSet(State.OPEN, State.CLOSED)) {
			if (this.sessionFactory != null) {
				this.sessionFactory.close();
			}
		}
	}

	public enum State {
		CONFIGURATION, OPEN, CLOSED;
	}
}