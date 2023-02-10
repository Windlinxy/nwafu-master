package pers.nwafumaster.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: json返回
 * @author: Windlinxy
 * @create: 2021-10-25 09:18
 **/
@Data
public class JsonResult<T> {


    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;


    public JsonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult() {
    }


    public JsonResult<T> fail() {
        return new JsonResult<>(1000, "操作失败", null);
    }

    public JsonResult<T> fail(String msg) {
        return new JsonResult<>(1001, msg, null);
    }

    public JsonResult<T> ok() {
        return new JsonResult<>(2000, "操作成功", null);
    }

    public JsonResult<T> ok(T data) {
        return new JsonResult<>(2001, "操作成功", data);
    }

    public JsonResult<T> jud(boolean condition) {
        return condition ? ok() : fail();
    }

    public static void returnMsg(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out;
        String jsonString;
        out = response.getWriter();
        jsonString = new ObjectMapper().writeValueAsString(new JsonResult<>().fail(msg));
        out.println(jsonString);
        out.flush();
    }
}
