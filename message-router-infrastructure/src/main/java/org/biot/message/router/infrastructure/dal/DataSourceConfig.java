package org.biot.message.router.infrastructure.dal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "org.biot.message.router.infrastructure.dal.mapper")
public class DataSourceConfig {
}
