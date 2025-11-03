package cn.yzdoit.purepanel.util;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树形列表转换工具类
 *
 * @author 闫政
 * @since 2025-06-17 17:06
 */
public class TreeListUtil {

    /**
     * 将list转换为树形列表
     *
     * @param collection          平铺的list
     * @param getIdSupplier       获取ID的函数
     * @param getPIdSupplier      获取父ID的函数
     * @param setChildrenConsumer 设置children的函数
     * @param rClass              转换后的类型
     * @param <T>                 原始数据类型
     * @param <R>                 转换后的数据类型
     * @param <ID_TYPE>           ID的数据类型
     * @return List
     */
    public static <T, R, ID_TYPE> List<R> toTree(Collection<T> collection
            , Function<T, ID_TYPE> getIdSupplier
            , Function<T, ID_TYPE> getPIdSupplier
            , BiConsumer<R, List<R>> setChildrenConsumer
            , Class<R> rClass) {
        return toTree(collection, getIdSupplier, getPIdSupplier, setChildrenConsumer, rClass, null);
    }

    /**
     * 将list转换为树形列表
     *
     * @param collection          平铺的list
     * @param getIdSupplier       获取ID的函数
     * @param getPIdSupplier      获取父ID的函数
     * @param setChildrenConsumer 设置children的函数
     * @param rClass              转换后的类型
     * @param additionalConsumer  附加逻辑函数，会在<code>BeanUtils.copyProperties(v, r);</code>后执行
     * @param <T>                 原始数据类型
     * @param <R>                 转换后的数据类型
     * @param <ID_TYPE>           ID的数据类型
     * @return List
     */
    public static <T, R, ID_TYPE> List<R> toTree(Collection<T> collection
            , Function<T, ID_TYPE> getIdSupplier
            , Function<T, ID_TYPE> getPIdSupplier
            , BiConsumer<R, List<R>> setChildrenConsumer
            , Class<R> rClass
            , BiConsumer<T, R> additionalConsumer) {
        return collection.stream().filter(v -> ObjectUtils.isEmpty(getPIdSupplier.apply(v)))
                .map(v -> copyAndSetChildren(rClass, v, getIdSupplier, collection, setChildrenConsumer, getPIdSupplier, additionalConsumer))
                .collect(Collectors.toList());
    }

    private static <T, R, ID_TYPE> R copyAndSetChildren(Class<R> rClass
            , T v
            , Function<T, ID_TYPE> getIdSupplier
            , Collection<T> collection
            , BiConsumer<R, List<R>> setChildrenConsumer
            , Function<T, ID_TYPE> getPIdSupplier
            , BiConsumer<T, R> additionalConsumer) {
        try {
            R r = rClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(v, r);
            if (ObjectUtils.isNotEmpty(additionalConsumer)) {
                additionalConsumer.accept(v, r);
            }
            setChildrenConsumer.accept(r, getChildrenList(getIdSupplier.apply(v), collection, rClass, getIdSupplier, setChildrenConsumer, getPIdSupplier, additionalConsumer));
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T, R, ID_TYPE> List<R> getChildrenList(ID_TYPE id
            , Collection<T> collection
            , Class<R> rClass
            , Function<T, ID_TYPE> getIdSupplier
            , BiConsumer<R, List<R>> setChildrenConsumer
            , Function<T, ID_TYPE> getPIdSupplier
            , BiConsumer<T, R> additionalConsumer) {
        return collection.stream().filter(v -> id.equals(getPIdSupplier.apply(v)))
                .map(v -> copyAndSetChildren(rClass, v, getIdSupplier, collection, setChildrenConsumer, getPIdSupplier, additionalConsumer))
                .collect(Collectors.toList());
    }
}
