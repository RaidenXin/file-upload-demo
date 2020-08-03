package com.raiden.content;

import com.raiden.util.LocalDateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 0:45 2020/8/1
 * @Modified By:
 */
public final class DataFormatStrategyContext {


    private static final Map<String, Function<Object, Object>> FUNCTION_CACHE;

    static {
        FUNCTION_CACHE = new HashMap<>();
        FUNCTION_CACHE.put(LocalDateUtils.GET_TIME, LocalDateUtils::getTime);
        FUNCTION_CACHE.put(LocalDateUtils.FORMAT, LocalDateUtils::format);
    }

    public static <T> T executeFunctions(String[] functionNames,Object args){
        Object result = args;
        for (String functionName : functionNames){
            Function<Object, Object> function = FUNCTION_CACHE.get(functionName);
            if (function == null){
                continue;
            }
            result = function.apply(result);
        }
        return (T) result;
    }
}
