package com.hnu.capstone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hnu.capstone.CapstoneApplication;
import com.hnu.capstone.domain.ApiResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CompileBuilder {
    // 프로젝트 home directory 경로
    // private final String path = CapstoneApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    // private final String path = "C:/Users/USER/IdeaProjects/spring_web_ide/compiler/compileEX/";
    private final String path = "/home/ec2-user/app/step1/temp/";

    @SuppressWarnings({ "resource", "deprecation" })
    public Object compileCode(String body) throws Exception {
        String uuid = UUIDUtil.createUUID();
        String uuidPath = path + uuid + "/";

        // Source를 이용한 java file 생성
        File newFolder = new File(uuidPath);
        File sourceFile = new File(uuidPath + "DynamicClass.java");
        File classFile = new File(uuidPath + "DynamicClass.class");

        Class<?> cls = null;

        // compile System err console 조회용 변수
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream origErr = System.err;

        try {
            newFolder.mkdir();
            new FileWriter(sourceFile).append(body).close();

            // 만들어진 Java 파일을 컴파일
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            // System의 error outputStream을 ByteArrayOutputStream으로 받아오도록 설정
            System.setErr(new PrintStream(err));

            // compile 진행
            int compileResult = compiler.run(null, null, null, sourceFile.getPath());
            // compile 실패인 경우 에러 로그 반환
            if(compileResult == 1) {
                return err.toString();
            }

            // 컴파일된 Class를 Load
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {new File(uuidPath).toURI().toURL()});
            cls = Class.forName("DynamicClass", true, classLoader);

            // Load한 Class의 Instance를 생성
            return cls.newInstance();
        } catch (Exception e) {
            log.error("[CompileBuilder] 소스 컴파일 중 에러 발생 :: {}", e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            // Syetem error stream 원상태로 전환
            System.setErr(origErr);

/*            if(sourceFile.exists())
                sourceFile.delete();
            if(classFile.exists())
                classFile.delete();
            if(newFolder.exists())
                newFolder.delete();*/
        }
    }

    /**
     *
     * @param obj
     * @return Map<String, Object>
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> runObject(Object obj) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 실행할 메소드 명
        String methodName = "runMethod";
        // 파라미터 타입 개수만큼 지정

        /*
         * reflection method의 console output stream을 받아오기 위한 변수
         * reflection method 실행 시 System의 out, error outputStream을 ByteArrayOutputStream으로 받아오도록 설정
         * 실행 완료 후 다시 원래 System으로 전환
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        PrintStream origErr = System.err;
        try {
            // System의 out, error outputStream을 ByteArrayOutputStream으로 받아오도록 설정
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));

            // 메소드 timeout을 체크하며 실행(15초 초과 시 강제종료)
            Map<String, Object> result = new HashMap<String, Object>();
            result = Execution.timeOutCall(obj, methodName);

            // stream 정보 저장
            if((Boolean) result.get("result")) {
                returnMap.put("result", ApiResponseResult.SUCEESS.getText());
                returnMap.put("return", result.get("return"));
                if(err.toString() != null && !err.toString().equals("")) {
                    returnMap.put("SystemOut", err.toString());
                }else {
                    returnMap.put("SystemOut", out.toString());
                }
            }else {
                returnMap.put("result", ApiResponseResult.FAIL.getText());
                if(err.toString() != null && !err.toString().equals("")) {
                    returnMap.put("SystemOut", err.toString());
                }else {
                    returnMap.put("SystemOut", "제한 시간 초과");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // Syetem out, error stream 원상태로 전환
            System.setOut(origOut);
            System.setErr(origErr);
        }

        return returnMap;
    }
}