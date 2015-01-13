package com.yudylaw.hotswap.demo;

import org.junit.Test;

/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public class TestFileSystemLoader {

    @Test
    public void loadClasses(){
        FileSystemLoader loader = new FileSystemLoader();
        loader.loadClasses("/home/yudylaw/git/hotswap/logs");
    }
    
}
