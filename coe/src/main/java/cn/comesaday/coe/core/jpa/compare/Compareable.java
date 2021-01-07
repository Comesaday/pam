package cn.comesaday.coe.core.jpa.compare;

/**
 * <描述> 实体比较器interface
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-01-07 19:26
 */
public interface Compareable<T> {

    <S extends T> S create(S s);
}
