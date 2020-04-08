/**
 * @ClassName BeanUtil
 * @description: TODO
 * @author Argus
 * @Date 2020/3/6 17:50
 * @Version V1.0
 */
package com.test.api.utils;

import java.io.*;

/**
 * 深拷贝
 */
public class BeanUtil {

    private BeanUtil() {
        throw new AssertionError();
    }
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) throws Exception {
        // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(obj);
        // 将流序列化成对象
        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        return (T) ois.readObject();
    }

}
