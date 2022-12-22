import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: Likfees
 * @Date: 2022/12/20 15:56
 * @Description:
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
        // 数组
        typeDic.put(int[].class.getName(), int.class);
        typeDic.put(double[].class.getName(), double.class);
        typeDic.put(float[].class.getName(), float.class);
        typeDic.put(long[].class.getName(), long.class);
        typeDic.put(short[].class.getName(), short.class);
        typeDic.put(byte[].class.getName(), byte.class);
        typeDic.put(boolean[].class.getName(), boolean.class);
        typeDic.put(char[].class.getName(), char.class);
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
        if (wrapperClass == int.class) {
            List<?> objects = JSON.parseArray(value, int.class);
            return (T) converIntArr(objects);
        } else if (wrapperClass == char.class) {
            List<?> objects = JSON.parseArray(value, char.class);
            return (T) converChatArr(objects);
        } else if (wrapperClass == long.class) {
            List<?> objects = JSON.parseArray(value, long.class);
            return (T) converLongArr(objects);
        } else if (wrapperClass == double.class) {
            List<?> objects = JSON.parseArray(value, double.class);
            return (T) converDoubleArr(objects);
        } else if (wrapperClass == float.class) {
            List<?> objects = JSON.parseArray(value, float.class);
            return (T) converFloatArr(objects);
        } else if (wrapperClass == boolean.class) {
            List<?> objects = JSON.parseArray(value, boolean.class);
            return (T) converBooleanArr(objects);
        } else if (wrapperClass == byte.class) {
            List<?> objects = JSON.parseArray(value, byte.class);
            return (T) converByteArr(objects);
        } else if (wrapperClass == short.class) {
            List<?> objects = JSON.parseArray(value, short.class);
            return (T) converShortArr(objects);
        }
        return (T) wrapperClass.getConstructor(String.class).newInstance(value);
    }

    public static int[] converIntArr(List<?> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (int) list.get(i);
        }
        return arr;
    }

    public static double[] converDoubleArr(List<?> list) {
        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (double) list.get(i);
        }
        return arr;
    }

    public static float[] converFloatArr(List<?> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (float) list.get(i);
        }
        return arr;
    }

    public static long[] converLongArr(List<?> list) {
        long[] arr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (long) list.get(i);
        }
        return arr;
    }

    public static short[] converShortArr(List<?> list) {
        short[] arr = new short[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (short) list.get(i);
        }
        return arr;
    }

    public static byte[] converByteArr(List<?> list) {
        byte[] arr = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (byte) list.get(i);
        }
        return arr;
    }

    public static boolean[] converBooleanArr(List<?> list) {
        boolean[] arr = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (boolean) list.get(i);
        }
        return arr;
    }

    public static char[] converChatArr(List<?> list) {
        char[] arr = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = (char) list.get(i);
        }
        return arr;
    }


}
