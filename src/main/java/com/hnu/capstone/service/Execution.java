package com.hnu.capstone.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Execution {
    private final static long TIMEOUT_LONG = 15000; // 15초

    public static Map<String, Object> timeOutCall(Object obj, String methodName) throws Exception {
        // return Map
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // Source를 만들때 지정한 Method
        Method objMethod;
        objMethod = obj.getClass().getMethod(methodName);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<Map<String, Object>> task = new Callable<Map<String, Object>>() {
            @Override
            public Map<String, Object> call() throws Exception {
                Map<String, Object> callMap = new HashMap<String, Object>();

                // Method 실행
                callMap.put("return", objMethod.invoke(obj));

                callMap.put("result", true);
                return callMap;
            }
        };

        Future<Map<String, Object>> future = executorService.submit(task);
        try {
            // 타임아웃 감시할 작업 실행
            returnMap = future.get(TIMEOUT_LONG, TimeUnit.MILLISECONDS); // timeout을 설정
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            // e.printStackTrace();
            returnMap.put("result", false);
        } finally {
            executorService.shutdown();
        }

        return returnMap;
    }
}
