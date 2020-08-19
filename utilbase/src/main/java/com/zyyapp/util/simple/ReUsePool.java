package com.zyyapp.util.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 通用重用池;
 * 通过对象名自动适配 到对象的对象池进行分配释放;
 * Created by zyy on 2018-8-9.
 */
public class ReUsePool {
    private static final Logger log =  LoggerFactory.getLogger(ReUsePool.class);
    private static ConcurrentHashMap<String, Pool> poolsMap = new ConcurrentHashMap<>();
    private static int DEFAULT_POOL_SIZE = 256;       // 每个对象池默认大小;

    // 重设指定目标类重用池大小; 未设置的情况下 使用默认值 defaultPoolSize;
    public static void Resize(Class<?> clazz, int size) {
        Pool pool = GetPool(clazz);
        pool.setPoolSize(size);
    }

    // 获得重用池中的对象; 如果不存在, 自动生成一个新的对象进行返回;
    // 只支持带无参构造函数的类自动实例化;
    public static Object Pop(Class<?> clazz) {
        Pool pool = GetPool(clazz);
        try {
            return pool.poolPop(clazz, true);
        } catch (Exception e) {
            log.error(String.format("clazz: %s", clazz.getName()), e);
        }

        return null;
    }

    // 获得重用池中的对象; 如果不存在, 返回null;
    public static Object PopNotNew(Class<?> clazz) {
        Pool pool = GetPool(clazz);
        try {
            return pool.poolPop(clazz, false);
        } catch (Exception e) {
            log.error(String.format("clazz: %s", clazz.getName()), e);
        }

        return null;
    }

    public static void Push(Object obj) {
        Class clazz = obj.getClass();
        Pool pool = GetPool(clazz);
        pool.poolPush(obj);
    }

    private static Pool GetPool(Class<?> clazz) {
        String clazzName = clazz.getName();
        if (!poolsMap.containsKey(clazzName)) {
            poolsMap.putIfAbsent(clazzName, new Pool());
        }

        return poolsMap.get(clazzName);
    }


    static class Pool {
        private ConcurrentLinkedQueue<Object> pool = new ConcurrentLinkedQueue<>();     // 重用池;
        private int poolSize = ReUsePool.DEFAULT_POOL_SIZE;

        public Object poolPop(Class<?> clazz, boolean autoCreate) throws InstantiationException, IllegalAccessException{
            Object obj = pool.poll();
            if (obj == null && autoCreate) {
                obj = clazz.newInstance();
            }
            return obj;
        }
        //  主动调用该接口 还回重用池中, 可以达到重用的目的; 不调也没关系, 交给gc 进行回收, 每次使用时重新分配;
        public void poolPush(Object obj) {
            if (obj == null) {
                return;
            }
            if (pool.size() > poolSize) {
                // 元素已经过多, 不用再往池里面放了, 交给gc进行回收;
                return;
            }

            // 放入池中;
            pool.offer(obj);
        }

        public void setPoolSize(int size) {
            this.poolSize = size;
        }
    }
}
