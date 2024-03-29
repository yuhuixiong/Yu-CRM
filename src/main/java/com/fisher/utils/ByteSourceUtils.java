package com.fisher.utils;

import com.fisher.web.shiro.SimpleByteSource;
import org.apache.shiro.util.ByteSource;

public class ByteSourceUtils {

    public static ByteSource bytes(byte[] bytes){
        return new SimpleByteSource(bytes);
    }
    public static ByteSource bytes(String arg0){
        return new SimpleByteSource(arg0.getBytes());
    }

}
