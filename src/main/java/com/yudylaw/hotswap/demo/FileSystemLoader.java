package com.yudylaw.hotswap.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public class FileSystemLoader extends AbstractLoader {

    private final static Logger logger = LoggerFactory.getLogger(FileSystemLoader.class);
    
    private static ByteBuffer buffer = null;
    
    public List<ClassDefinition> loadClasses(String path) {
        File file = new File(path);
        if(file.isDirectory()){
            
            File[] classFiles = file.listFiles(new FilenameFilter() {
                
                public boolean accept(File dir, String name) {
                    return isClassFile(name);
                }
                
            });
            
            logger.info("loaded {} classes from {}", classFiles.length, path);
            
            for(File classFile : classFiles){
                FileInputStream input = null;
                try {
                    int fileSize = (int) classFile.length();
                    buffer = ByteBuffer.allocate(fileSize);
                    input = new FileInputStream(classFile);
                    int data = input.read();
                    while(data != -1){
                        data = input.read();
                        buffer.put((byte) data);
                    }
                    //position
                    byte[] fileData = new byte[buffer.position()];
                    //ready to read
                    buffer.flip();
                    //read
                    buffer.get(fileData);
                    //TODO class 字节码校验
                    logger.info("read {} bytes from {}", fileData.length, classFile);
                    buffer.clear();
                    input.close();
                } catch (Exception e) {
                    logger.error("reading {} error {}", classFile, e);
                    e.printStackTrace();
                }finally{
                    try {
                        if(input != null){
                            input.close();
                        }
                    } catch (IOException e) {
                        logger.error("close {} input error {}", classFile, e);
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
