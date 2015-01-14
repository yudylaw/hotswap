package com.yudylaw.hotswap.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.ClassFile;

/**
 * @author liuyu3@xiaomi.com
 * @since 2015年1月13日
 */

public class FileSystemLoader extends AbstractLoader {

    private final static Logger logger = LoggerFactory.getLogger(FileSystemLoader.class);
    private static ByteBuffer buffer = null;
    private final static int MAGIC_SIZE = 4;//\ca\fe\ba\be
    
    public List<ClassDefinition> loadClasses(String path) {
        List<byte[]> byteLits = getByteList(path);
        List<ClassDefinition> list = new ArrayList<ClassDefinition>();
        if(byteLits == null || byteLits.size() == 0){
            return list;
        }
        for(byte[] data : byteLits){
            ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(data);
            DataInputStream input = new DataInputStream(byteArrayInput);
            try {
                ClassFile classFile = new ClassFile(input);
                logger.info("construct {} ClassFile successful", classFile.getName());
                ClassDefinition def = new ClassDefinition(Class.forName(classFile.getName()), data);
                list.add(def);
            } catch (Exception e) {
                logger.error("construct ClassFile errror", e);
            }
        }
        return list;
    }
    
    private List<byte[]> getByteList(String path){
        File file = new File(path);
        List<byte[]> byteList = new ArrayList<byte[]>();
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
                    if(fileSize < MAGIC_SIZE){
                        continue;
                    }
                    buffer = ByteBuffer.allocate(fileSize);
                    input = new FileInputStream(classFile);
                    int data = input.read();
                    while(data != -1){
                        buffer.put((byte) data);
                        data = input.read();
                    }
                    //position
                    byte[] fileData = new byte[buffer.position()];
                    //ready to read
                    buffer.flip();
                    //read
                    buffer.get(fileData);
                    byteList.add(fileData);
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
        return byteList;
    }

}
