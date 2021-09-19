package in2021winter.controller;

/**自定义异常类，用于作提示信息的作用，配合异常处理器使用
 * @author HuangHai
 * @date 2021/2/21 13:24
 */
public class MyException extends Exception {
    private String message;//存储异常信息

    public MyException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
