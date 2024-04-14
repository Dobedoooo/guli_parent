package com.atguigu.cms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class CodeGenerateTest {
    @Test
    public void main1() {

        // 1. 创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2. 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("dobedoo");
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);

        /*
         * mp 生成的service层代码，默认接口名称第一个字母有 I
         * UcenterService
         */
        globalConfig.setServiceName("%sService");
        globalConfig.setIdType(IdType.ID_WORKER_STR);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setSwagger2(true);

        mpg.setGlobalConfig(globalConfig);

        // 3. 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://47.97.11.78:3306/guli_edu?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("_doA4dcB8gYG3");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4. 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("cms"); // 模块名
        pc.setParent("com.atguigu");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5.策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setInclude("crm_banner");
        sc.setNaming(NamingStrategy.underline_to_camel);    // 数据库表映射到实体的命名策略
        sc.setTablePrefix(pc.getModuleName() + "_");        // 生成实体时去掉表前缀

        sc.setColumnNaming(NamingStrategy.underline_to_camel);  //数据库表字段映射到实体属性的命名策略
        sc.setEntityLombokModel(true);  // Lombok 模型 @Accessors(chain=true) setter链式操作

        sc.setRestControllerStyle(true);
        sc.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(sc);

        // 6. 执行
        mpg.execute();

    }
}
