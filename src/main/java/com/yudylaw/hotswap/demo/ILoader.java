package com.yudylaw.hotswap.demo;

import java.lang.instrument.ClassDefinition;
import java.util.List;

/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public interface ILoader {

    public final static String CLASS_FILE_EXT = ".class";
    
    public List<ClassDefinition> loadClasses(String path);
    
}
