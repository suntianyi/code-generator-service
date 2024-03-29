package com.sun;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunzh
 */

public class CodeGenerator {
    private final static String outputDir = "F:/";
    private final static String parent = "com";
    private final static String moduleName = "sun";
    private final static String author = "sun";

    private final static String tables = "organization";


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(initGlobalConfig());
        generator.setDataSource(initDateSourceConfig());
        generator.setPackageInfo(initPackageConfig());
        generator.setStrategy(initStrategyConfig());
        generator.setTemplate(initTemplateConfig());
        generator.setTemplateEngine(new VelocityTemplateEngine());
        generator.setCfg(initInjectionConfig());

        generator.execute();
    }

    // 全局配置
    private static GlobalConfig initGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
//        gc.setBaseColumnList(true);
        gc.setServiceName("%sService");
        gc.setAuthor(author);
//        gc.setSwagger2(true);
        gc.setOpen(false);
        return gc;
    }

    // 数据源配置
    public static DataSourceConfig initDateSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/ksports?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("sun");
        dsc.setPassword("123456");
        return dsc;
    }

    // 包配置
    public static PackageConfig initPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(parent);
        return pc;
    }

    public static StrategyConfig initStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix("t_");// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tables.split(",")); // 需要生成的表
        strategy.setRestControllerStyle(true); //生成RestController控制器
        strategy.setEntityLombokModel(true);
        return strategy;
    }

    public static TemplateConfig initTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
        tc.setController("templates/controller.java");
        tc.setService("templates/service.java");
        tc.setServiceImpl("templates/serviceImpl.java");
        tc.setEntity("templates/entity.java");
        tc.setMapper("templates/mapper.java");
//        tc.setXml(null);
        return tc;
    }

    public static InjectionConfig initInjectionConfig() {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // 添加自定义参数，使用格式为cfg.xxx
                Map<String, Object> map = new HashMap<>();
                map.put("Query", parent + "." + moduleName + ".query");
                setMap(map);
            }
        };
        String templatePath = "/templates/query.java.vm";
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String str = outputDir + parent + "/" + moduleName + "/query/";
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdir();
                }
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return outputDir + parent + "/" + moduleName + "/query/" + tableInfo.getEntityName() + "Query" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }
}
