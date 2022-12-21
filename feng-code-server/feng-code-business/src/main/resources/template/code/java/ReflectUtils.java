import java.util.HashMap;

/**
 * @Auther: Likfees
 * @Date: 2022/12/20 15:56
 * @Description: 这是一个通过Java反射机制获取变量的一个工具类
 * 方便获取类的字段名称，同时极大地减少字段以常量字符串形式写死在程序里
 */
public class ReflectUtils {
    public static final HashMap<String, Class<?>> typeDic = new HashMap<>();

    static {
        typeDic.put(int.class.getName(), Integer.class);
        typeDic.put(double.class.getName(), Double.class);
        typeDic.put(float.class.getName(), Float.class);
        typeDic.put(long.class.getName(), Long.class);
        typeDic.put(short.class.getName(), Short.class);
        typeDic.put(byte.class.getName(), Byte.class);
        typeDic.put(boolean.class.getName(), Boolean.class);
        typeDic.put(char.class.getName(), Character.class);
    }

    /**
     * String 转 任意基本类型的包装类
     *
     * @param cls   基本类型
     * @param value String字符串
     * @return 基本类型的包装类的对象
     * @throws Exception 请务必保证目标类型有把String转成目标类型的构造方法
     */
    @SuppressWarnings("unchecked")
    public static <T> T conver(Class<T> cls, String value) throws Exception {
        Class<?> wrapperClass = typeDic.get(cls.getName());
        if (wrapperClass == null) {
            return cls.getConstructor(String.class).newInstance(value);
        }
        return (T) wrapperClass.getConstructor(String.class).newInstance(value);
    }
}
