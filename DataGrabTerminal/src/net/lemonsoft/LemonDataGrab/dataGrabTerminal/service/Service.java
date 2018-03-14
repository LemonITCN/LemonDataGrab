package net.lemonsoft.LemonDataGrab.dataGrabTerminal.service;

import net.lemonsoft.LemonDataGrab.dataGrabTerminal.dao.Dao;
import net.lemonsoft.LemonDataGrab.dataGrabTerminal.exception.EntityAnnotationIncompleteException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 业务层 - 所有业务层的父类,所有的业务层都直接或间接的继承自本类
 * Created by LiuRi on 16/5/7.
 */
public class Service<T, DT> {

    private Class<T> entityClass;
    private Class<DT> daoClass;

    private Dao dao;

    public Service() {
        this.entityClass = null;
        Class clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) types[0];
            this.daoClass = (Class<DT>) types[1];
            try {
                this.dao = (Dao) daoClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public DT getDao() {
        return (DT) dao;
    }

    /**
     * 添加指定的实体对象到数据库中
     *
     * @param entity 要添加的实体类对象
     * @return 执行是否成功的boolean值
     */
    public boolean add(T entity) {
        try {
            return this.dao.add(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定的实体对象
     *
     * @param entity 要删除的实体对象,实体对象的主键属性必须不为空
     * @return 执行是否成功的boolean值
     */
    public boolean delete(T entity) {
        try {
            return this.dao.deleteByPrimaryKey(this.dao.getPrimaryKeyName());
        } catch (EntityAnnotationIncompleteException lexEntityAnnotationIncomplete) {
            lexEntityAnnotationIncomplete.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定的主键的数据记录
     *
     * @param primaryKey 要删除的记录的主键值
     * @return 执行是否成功的boolean值
     */
    public boolean deleteByPrimaryKey(String primaryKey) {
        try {
            return this.dao.deleteByPrimaryKey(primaryKey);
        } catch (EntityAnnotationIncompleteException lexEntityAnnotationIncomplete) {
            lexEntityAnnotationIncomplete.printStackTrace();
            return false;
        }
    }

    /**
     * 更新指定的实体对象到数据库
     *
     * @param entity 要更新的数据库实体对象记录
     * @return 执行是否成功的boolean值
     */
    public boolean update(T entity) {
        try {
            return this.dao.update(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过主键获取记录的实体对象
     *
     * @param primaryKey 要获取的实体对象记录的主键值
     * @return 记录的实体对象
     */
    public T getByPrimaryKey(Object primaryKey) {
        try {
            return (T) this.dao.getByPrimaryKey(primaryKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取传入的若干个主键值的对象的链表
     *
     * @param primaryKeys 要查询的若干条记录的主键
     * @return 查询到的记录链表
     */
    public ArrayList<T> getByPrimaryKeys(Object... primaryKeys) {
        ArrayList<T> result = null;
        try {
            result = this.dao.getByPrimaryKeys(primaryKeys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 按照范围查询所有的数据中的一部分
     *
     * @param start 开始的索引
     * @param count 获取的数量
     * @return 查询到的数据的链表
     */
    public ArrayList<T> getAllDataByScope(int start, int count) {
        ArrayList<T> result = null;
        try {
            result = this.dao.getBySQLCondition(String.format("LIMIT %s , %s", start, count));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
