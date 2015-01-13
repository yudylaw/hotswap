package com.yudylaw.hotswap.demo;


/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public abstract class AbstractLoader implements ILoader {

    public boolean isClassFile(String fileName){
        return fileName != null && fileName.endsWith(CLASS_FILE_EXT);
    }
    
}
