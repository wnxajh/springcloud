package com.wn.config.dataSource;

import com.wn.common.DatabaseType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
/**
 * 数据源配置
 * @author wun
 *
 */
@Configuration
@MapperScan("com.wn.dao")
@EnableTransactionManagement
public class DruidDataSourceConfig {

	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;

	@Value("${mybatis.typeAliasesPackage}")
	private String typeAliasesPackage;

	@Value("${datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.master")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "datasource.slave")
	public DataSource slaveDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	/**
	 * 动态数据源配置
	 * @param masterDataSource
	 * @param slaveDataSource
	 * @return
	 */
	@Bean
	public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
			@Qualifier("slaveDataSource") DataSource slaveDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DatabaseType.MASTER, masterDataSource);
		targetDataSources.put(DatabaseType.SLAVE, slaveDataSource);

		DynamicDataSource dataSource = new DynamicDataSource();
		// 设置数据源
		dataSource.setTargetDataSources(targetDataSources);
		// 设置默认数据源
		dataSource.setDefaultTargetDataSource(masterDataSource);
		return dataSource;
	}

	@Bean
	public SqlSessionFactory createSqlSessionFactory(DynamicDataSource dataSource) throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setDataSource(dataSource);
		fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		fb.setTypeAliasesPackage(typeAliasesPackage);
		return fb.getObject();
	}

	/**
	 * 配置事务管理器
	 */
	// @Bean
	// public DataSourceTransactionManager transactionManager(DynamicDataSource
	// dataSource) throws Exception {
	// return new DataSourceTransactionManager(dataSource);
	// }

}
