package net.lemonsoft.LemonDataGrab.MainControlMachine.Core;

import java.util.HashMap;

/**
 * 核心类 - 类实例池
 * Created by LiuRi on 16/5/10.
 */
public class LCClassInstancePool {

    private static HashMap<String, Object> instancePool = new HashMap<String, Object>();

    /**
     * 主动向实例池中添加一个实例对象
     *
     * @param className 类的名称
     * @param instance  实例对象
     */
    public static void addInstance(String className, Object instance) {
        instancePool.put(className, instance);
    }

    /**
     * 通过类名移除实例池中的实例对象
     *
     * @param className 要移除的实例的类名
     */
    public static void removeInstanceByClassName(String className) {
        instancePool.remove(className);
    }

    /**
     * 将指定的类名对应的实例替换成指定的新的实例对象
     *
     * @param className   要替换的实例的类名
     * @param newInstance 新的实例对象
     */
    public static void replaceInstanceByClassName(String className, Object newInstance) {
        if (instancePool.containsKey(className)) {
            instancePool.put(className, newInstance);
        }
    }

    /**
     * 获取指定类名的实例对象,若不存在则尝试调用无参数构造方法来实例化这个对象,存储到实例池中之后返回
     *
     * @param className 要获取的实例的类名
     * @return 获取到的实例对象
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object getInstanceByClassName(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (!instancePool.containsKey(className)) {
            // 没有这个类名的类实例,尝试调用无参构造方法来实例化这个类的对象
            instancePool.put(className, Class.forName(className).newInstance());
        }
        return instancePool.get(className);
    }

    /**
     * 获取指定类的实例对象,若不存在在实例池中那么用这个实例生成实例放到实例池中,并返回实例对象
     *
     * @param clazz 要获取实例对象的类
     * @return 获取到的实例对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object getInstanceByClass(Class clazz) throws IllegalAccessException, InstantiationException {
        if (!instancePool.containsKey(clazz.getName())) {
            // 没有这个类的类名的对应实例,尝试调用无参构造方法来实例化这个类的对象
            instancePool.put(clazz.getName(), clazz.newInstance());
        }
        return instancePool.get(clazz.getName());
    }

}
