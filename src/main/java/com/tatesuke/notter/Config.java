package com.tatesuke.notter;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("file:./notter.properties")
public class Config extends WebMvcConfigurerAdapter {
	
	/**
	 * 静的リソースの設定
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/assets/**").addResourceLocations("classpath:/assets/");
	}

	/**
	 * ファイル接続するDB。ファイルロックを行わないSimpleDriverDataSourceを使う
	 * 
	 * @see #dataSource()
	 */
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(org.h2.Driver.class);
		ds.setUrl("jdbc:h2:file:./notter.db2");
		ds.setUsername("");
		ds.setPassword("");
		return ds;
	}

}