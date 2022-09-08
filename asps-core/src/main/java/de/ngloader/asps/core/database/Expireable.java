package de.ngloader.asps.core.database;

import java.time.OffsetDateTime;

public interface Expireable {

	/**
	 * Return the time when it is expired in milliseconds
	 * 
	 * @return time in milliseconds
	 */
	OffsetDateTime getExpire();

	/**
	 * Return true when it is expired else false
	 * 
	 * @return is it expired
	 */
	default boolean isExpired() {
		return System.currentTimeMillis() > this.getExpire().toEpochSecond();
	}
}