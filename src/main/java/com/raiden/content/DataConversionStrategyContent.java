package com.raiden.content;

import com.raiden.util.LocalDateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 类型转换策略 上下文类
 */
public final class DataConversionStrategyContent {

    private static final Map<String, Function> STRATEGY_FUNCTION_CACHE;

    static {
        STRATEGY_FUNCTION_CACHE = new HashMap<>();
        Function<String, Object> getTime = LocalDateUtils::getTime;
        STRATEGY_FUNCTION_CACHE.put(LocalDateUtils.GET_TIME, getTime);
    }

    public static <T> T executeFunction(String functionName,Object args){
        Function<Object, Object> function = STRATEGY_FUNCTION_CACHE.get(functionName);
        if (function == null){
            return null;
        }
        return (T) function.apply(args);
    }
}
