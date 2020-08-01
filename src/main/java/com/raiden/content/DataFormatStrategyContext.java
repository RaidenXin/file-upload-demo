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


    private static final Map<String, Function<String, Object>> FUNCTION_CACHE;

    static {
        FUNCTION_CACHE = new HashMap<>();
        Function<String, Object> getTime = LocalDateUtils::getTime;
        FUNCTION_CACHE.put(LocalDateUtils.GET_TIME, getTime);
    }

    public static <T> T executeFunction(String functionName,String args){
        Function<String, Object> function = FUNCTION_CACHE.get(functionName);
        if (function == null){
            return null;
        }
        return (T) function.apply(args);
    }
}
