package club.p6e.coat.file.error;

/**
 * @author lidashuang
 * @version 1.0
 */
public class MediaTypeException extends CustomException {

    /**
     * 默认的代码
     */
    public static final int DEFAULT_CODE = 2000;

    /**
     * 默认的简述
     */
    private static final String DEFAULT_SKETCH = "FILE_EXCEPTION";

    /**
     * 文件异常/文件操作异常
     *
     * @param sc      源 class
     * @param error   异常对象
     * @param content 描述内容
     */
    public MediaTypeException(Class<?> sc, String error, String content) {
        super(sc, TypeMismatchException.class, content, DEFAULT_CODE, DEFAULT_SKETCH, content);
    }

    /**
     * 文件异常/文件操作异常
     *
     * @param sc        源 class
     * @param throwable 异常对象
     * @param content   描述内容
     */
    public MediaTypeException(Class<?> sc, Throwable throwable, String content) {
        super(sc, TypeMismatchException.class, throwable, DEFAULT_CODE, DEFAULT_SKETCH, content);
    }

    /**
     * 文件异常/文件操作异常
     *
     * @param sc      源 class
     * @param error   异常内容
     * @param code    代码
     * @param sketch  简述
     * @param content 描述内容
     */
    public MediaTypeException(Class<?> sc, String error, int code, String sketch, String content) {
        super(sc, TypeMismatchException.class, content, code, sketch, content);
    }

    /**
     * 文件异常/文件操作异常
     *
     * @param sc        源 class
     * @param throwable 异常对象
     * @param code      代码
     * @param sketch    简述
     * @param content   描述内容
     */
    public MediaTypeException(Class<?> sc, Throwable throwable, int code, String sketch, String content) {
        super(sc, TypeMismatchException.class, throwable, code, sketch, content);
    }
}
