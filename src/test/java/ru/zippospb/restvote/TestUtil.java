package ru.zippospb.restvote;

import org.springframework.test.web.servlet.ResultActions;
import ru.zippospb.restvote.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;

public class TestUtil {
    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }
}
