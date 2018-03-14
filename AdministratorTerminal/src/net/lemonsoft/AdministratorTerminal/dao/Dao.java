package net.lemonsoft.AdministratorTerminal.dao;

import net.lemonsoft.AdministratorTerminal.annotation.EntityAnnotation;
import net.lemonsoft.AdministratorTerminal.annotation.EntityPropertyAnnotation;
import net.lemonsoft.AdministratorTerminal.exception.EntityAnnotationIncompleteException;
import net.lemonsoft.AdministratorTerminal.tool.DatabaseTool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DAO层 - 所有DAO层的父类,所有的DAO层都直接或间接的继承自本类
 * Created by LiuRi on 16/4/25.
 */
public class Dao<T> {

    private Class<T> entityClass;

    public Dao() {
        this.entityClass = null;
        Class clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) types[0];
        }
    }

    /**
     * 向数据库中插入一个实体对象记录
     *
     * @param entity 要插入的实体对象信息
     * @return 插入是否成功的boolean值
     * @throws IllegalAccessException
     */
    public boolean add(T entity) throws IllegalAccessException {
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder columnKeyStrBuilder = new StringBuilder();
        StringBuilder numberBuilder = new StringBuilder();
        ArrayList<Object> values = new ArrayList();
        for (Field field : fields) {
            EntityPropertyAnnotation property = field.getAnnotation(EntityPropertyAnnotation.class);
            if (property.autoIncrement()) {
                // 自增长列,不用赋值
                continue;
            }
            field.setAccessible(true);
            if (columnKeyStrBuilder.length() > 0) {
                columnKeyStrBuilder.append(",");
                numberBuilder.append(",");
            }
            columnKeyStrBuilder.append(property.columnName());
            numberBuilder.append("?");
            values.add(field.get(entity));
        }
        StringBuilder sqlStrBuilder = new StringBuilder(String.format("INSERT INTO %s (%s) VALUES (%s)", this.getTableName(), columnKeyStrBuilder.toString(), numberBuilder.toString()));
        return DatabaseTool.update(sqlStrBuilder.toString(), values.toArray()) > 0;
    }

    /**
     * 通过主键删除指定记录
     *
     * @param key 要删除的记录的主键值
     * @return 删除是否成功的boolean值
     * @throws EntityAnnotationIncompleteException
     */
    public boolean deleteByPrimaryKey(Object key) throws EntityAnnotationIncompleteException {
        return this.deleteBySQLCondition("WHERE %s = ?", this.getPrimaryKeyName(), key) > 0;
    }

    /**
     * 通过SQL条件语句来自删除记录
     *
     * @param sqlCondition SQL条件语句
     * @param objects      传入的对象
     * @return 删除的数据条数
     */
    public int deleteBySQLCondition(String sqlCondition, Object... objects) {
        return DatabaseTool.update(String.format("DELETE FROM %s %s", this.getTableName(), sqlCondition), objects);
    }

    /**
     * 更新指定主键的记录的信息
     *
     * @param entity 要更新的实体对象
     * @return 更新是否成功的boolean值
     */
    public boolean update(T entity) throws IllegalAccessException {
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder sqlBuilder = new StringBuilder();
        Object primaryValue = null;
        for (Field field : fields) {
            EntityPropertyAnnotation property = field.getAnnotation(EntityPropertyAnnotation.class);
            field.setAccessible(true);
            if (field.get(entity) != null) {
                if (property.primaryKey()) {
                    primaryValue = field.get(entity);
                }
                if (sqlBuilder.length() > 0) {
                    sqlBuilder.append(",");
                }
                sqlBuilder.append(String.format("%s='%s'", property.columnName(), field.get(entity)));
            } else if (field.get(entity) == null && property.primaryKey()) {
                // 主键属性为空
                return false;
            }
        }
        sqlBuilder.insert(0, String.format("UPDATE %s SET ", this.getTableName()));
        sqlBuilder.append(String.format(" WHERE %s = ?", this.getPrimaryKeyName()));
        return DatabaseTool.update(sqlBuilder.toString(), primaryValue) > 0;
    }

    /**
     * 通过主键获取记录的实体对象
     *
     * @param key 要获取的实体对象记录的主键值
     * @return 记录的实体对象
     * @throws Exception
     */
    public T getByPrimaryKey(Object key) throws Exception {
        String sqlStr = this.getSelectSqlPre() + " WHERE " + this.getPrimaryKeyName() + " = ?";
        Map<String, Object> result = DatabaseTool.query(sqlStr, key).get(0);
        return this.initByResultMap(result);
    }

    /**
     * 获取主键名称
     *
     * @return 主键名称
     */
    public String getPrimaryKeyName() {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            EntityPropertyAnnotation lanEntityProperty = field.getAnnotation(EntityPropertyAnnotation.class);
            if (lanEntityProperty.primaryKey() == true) {
                return lanEntityProperty.columnName();
            }
        }
        return null;
    }

    /**
     * 获取SELECT SQL语句的前半部分,包括 SELECT 所有列名 FROM 表明
     *
     * @return 生成的SELECT SQL语句前半部分
     * @throws Exception
     */
    public String getSelectSqlPre() throws Exception {
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder columnsStr = new StringBuilder();
        for (Field field : fields) {
            EntityPropertyAnnotation lanEntityProperty = field.getAnnotation(EntityPropertyAnnotation.class);
            if (columnsStr.length() > 0) {
                columnsStr.append(",");
            }
            columnsStr.append(lanEntityProperty.columnName());
        }
        if (this.getTableName() == null || this.getPrimaryKeyName() == null || columnsStr.length() <= 0) {
            throw new EntityAnnotationIncompleteException();
        }
        return "SELECT " + columnsStr.toString() + " FROM " + this.getTableName();
    }

    /**
     * 获取传入的若干个主键值的对象的链表
     *
     * @param keys 要查询的若干条记录的主键
     * @return 查询到的记录链表
     * @throws Exception
     */
    public ArrayList<T> getByPrimaryKeys(Object... keys) throws Exception {
        ArrayList<T> result = new ArrayList();
        for (Object key : keys) {
            result.add(this.getByPrimaryKey(key));
        }
        return result;
    }

    /**
     * 通过条件语句来进行查询,需提供SQL SELECT 列名 FROM 表名 之后的内容
     *
     * @param sqlCondition SQL查询语句后半部分,对象用?替代
     * @param objects      要传入的对象,替代?对象的数组
     * @return 查询到的数据
     */
    public ArrayList<T> getBySQLCondition(String sqlCondition, Object... objects) throws Exception {
        ArrayList<T> result = new ArrayList();
        List<Map<String, Object>> queryResult = DatabaseTool.query(this.getSelectSqlPre() + " " + sqlCondition, objects);
        for (Map<String, Object> item : queryResult) {
            result.add(this.initByResultMap(item));
        }
        return result;
    }

    /**
     * 通过条件语句来计算指定条件下数据的数量,需提供SQL SELECT COUNT(主键列名) FROM 表名 之后的内容
     *
     * @param sqlCondition SQL查询语句后半部分,对象用?替代
     * @param objects      要传入的对象,替代?对象的数组
     * @return 按条件来查询到的数据的数量
     */
    public int countBySQLCondition(String sqlCondition, Object... objects) {
        return DatabaseTool.count("SELECT COUNT(" + this.getPrimaryKeyName() + ") FROM " + this.getTableName() + " " + sqlCondition, objects);
    }

    /**
     * 将从数据库中查询出的数据Map转换成实体对象
     *
     * @param resultMap 从数据库中查询出的记录Map
     * @return 转换成的对应的实体对象
     * @throws Exception
     */
    public T initByResultMap(Map<String, Object> resultMap) throws Exception {
        T entity = entityClass.newInstance();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            EntityPropertyAnnotation property = field.getAnnotation(EntityPropertyAnnotation.class);
            String methodName = "set" + this.getMethodName(field.getName());
            Method method = entityClass.getMethod(methodName, field.getType());
            method.invoke(entity, resultMap.get(property.columnName()));
        }
        return entity;
    }

    /**
     * 获取当前实体对象对应的数据库表名称
     *
     * @return 数据库的表名称
     */
    public String getTableName() {
        return entityClass.getAnnotation(EntityAnnotation.class).tableName();
    }

    /**
     * 获取将方法的首字母转换为大写的方法名
     *
     * @param fieldName 方法名名称
     * @return 首字母转化为大写的方法名字符串
     * @throws Exception
     */
    private String getMethodName(String fieldName) throws Exception {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
