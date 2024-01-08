package com.community.app.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // 구성(Configuration)클래스임을 나타냄
@PropertySource("classpath:/application.properties") // .properties의 프로퍼티 가져옴.(애플리케이션 설정 정보)
public class DBConfiguration {

    @Autowired
    private ApplicationContext applicationContext;
    // 스프링 컨테이너의 핵심 인터페이스. 애플리케이션의 컴포넌트 관리

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig(); // HikariCP db 커넥션 풀 설정 객체 생성후 반환
    }
    // 프리픽스가 spring.datasource.hikari인 프로퍼티 값 가져와 커넥션 풀 설정 생성
    // 커넥션풀: db와 연결된 커넥션을 미리 만들어놓고 pool형식으로 관리

    @Bean // 스프링 빈으로 등록되어 스프링 컨테이너에 의해 관리되는 객체
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    } // HikariCP db 커넥션 풀 설정 객체를 사용하여 데이터소스(db연결정보) 생성

    @Bean
    @ConfigurationProperties(prefix="mybatis.configutation")
    public org.apache.ibatis.session.Configuration mybatisConfig(){
        return new org.apache.ibatis.session.Configuration();
    } // MyBatis Configuration 객체 생성

    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"));
        factoryBean.setTypeAliasesPackage("com.community.app.mapper");
        factoryBean.setConfiguration(mybatisConfig());
        return factoryBean.getObject();
    } // MyBatis의 SqlSessionFactory를 생성하는 메서드. MyBatis에서 sql실행을 위해 필요

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}