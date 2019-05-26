package com.wn.config.dataSource;

import com.wn.common.DatabaseType;

/**
 * 
 * @author wun
 *
 */
public class DbContextHolder {

	private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

	public static void setDbType(DatabaseType dbType) {
		if (dbType == null)
			throw new NullPointerException();
		contextHolder.set(dbType);
	}

	public static DatabaseType getDbType() {
		return contextHolder.get() == null ? DatabaseType.MASTER : contextHolder.get();
	}

	public static void clearDbType() {
		contextHolder.remove();
	}
}
