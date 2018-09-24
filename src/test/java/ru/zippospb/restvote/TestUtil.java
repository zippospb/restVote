package ru.zippospb.restvote;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.zippospb.restvote.model.User;
import ru.zippospb.restvote.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.zippospb.restvote.web.json.JsonUtil.writeValue;

public class TestUtil {
    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }

    public static RequestPostProcessor userHttpBasic(User user){
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }

    @SafeVarargs
    public static <T> ResultMatcher contentJson(T... expected) {
        return content().json(writeValue(Arrays.asList(expected)));
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeValue(expected));
    }

}
