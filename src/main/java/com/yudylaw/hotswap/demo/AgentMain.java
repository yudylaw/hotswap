package com.yudylaw.hotswap.demo;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.List;

/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public class AgentMain {

    private static ILoader loader;
    
    public static void hotswap(String args, Instrumentation inst){
        loader = new FileSystemLoader();
        List<ClassDefinition> defList = loader.loadClasses("/home/yudylaw/git/hotswap/logs");
    }
    
}
