package com.yudylaw.hotswap.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public class HotSwapAgent {

    private final static Logger logger = LoggerFactory.getLogger(HotSwapAgent.class);
    
    /**
     * hotswap before jvm start
     * 实现字节码替换，限制小，可以添加方法等
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst){
        
    }
    
    /**
     * hotswap after jvm started
     * 在JVM启动后进行hotswap，只能更新方法体，变量，常量池
     * 限制较多，无法实现例如：添加方法，改方法名等
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        logger.info("enter agentmain");
        if(!inst.isRedefineClassesSupported()){
            logger.info("redefine class not supported");
            throw new UnsupportedOperationException("redefine class not supported.");
        }
        AgentMain.hotswap(agentArgs, inst);
    }
    
}
