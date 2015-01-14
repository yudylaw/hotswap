package com.yudylaw.hotswap.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;

/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public class AgentMain {

    private static ILoader loader;
    private final static Logger logger = LoggerFactory.getLogger(AgentMain.class);
    
    public static void hotswap(String args, Instrumentation inst){
        loader = new FileSystemLoader();
        List<ClassDefinition> defList = loader.loadClasses("/home/yudylaw/git/hotswap/logs");
        for(ClassDefinition def : defList){
            try {
                inst.redefineClasses(def);
                logger.info("redefine {} successful", def.getDefinitionClass());
            } catch (ClassNotFoundException e) {
                logger.error("redefine class error", e);
            } catch (UnmodifiableClassException e) {
                logger.error("redefine class error", e);
            }
        }
    }
    
}
