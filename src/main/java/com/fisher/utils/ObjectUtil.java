package com.fisher.utils;

import java.io.*;

/**
 * 序列化与反序列化
 */
public class ObjectUtil {

    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            System.out.println(value.getClass().getName());
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("serialize error");
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }

    public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }

    public static <T> T deserialize(byte[] in, Class<T>...requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("deserialize error");
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }

    private static void close(Closeable closeable) {
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("close stream error");
            }
        }

    }

    public static void main(String[] args) {
        byte[] b = serialize("nanc-shiro-session:703de992-b1f7-4d77-a699-8fba7cdaf4ca");
        System.out.println(deserialize(b));
        System.out.println(b);
    }

}
