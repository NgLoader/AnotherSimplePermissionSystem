package de.ngloader.asps.core.sql;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.reactive.stage.Stage;

/**
 * @author Ingrim4
 */
public interface CriteriaQueryBuilder<T> {

	Stage.Query<T> build(CriteriaBuilder builder, CriteriaQuery<T> criteria, Root<T> from);
}