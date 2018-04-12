package com.wn.config;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 主从数据源获取
 * @author wun
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DbContextHolder.getDbType();
	}

}
