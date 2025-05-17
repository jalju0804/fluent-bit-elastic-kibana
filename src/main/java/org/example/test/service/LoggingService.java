package org.example.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class LoggingService {

    // 직접 TCP로 로그를 전송하는 로거
    private static final Logger directLogger = LoggerFactory.getLogger("com.example.logging.direct");

    // 파일 기반으로 로그를 기록하는 로거
    private static final Logger fileLogger = LoggerFactory.getLogger("com.example.logging.file");

    /**
     * 직접 TCP로 로그를 전송
     * @param count 로그 갯수
     * @return 완료 메시지
     */
    @Async
    public CompletableFuture<String> logDirect(int count) {
        long startTime = System.currentTimeMillis();

        try {
            MDC.put("loggingType", "direct");
            for (int i = 0; i < count; i++) {
                String requestId = UUID.randomUUID().toString();
                MDC.put("requestId", requestId);
                MDC.put("method", "POST");
                MDC.put("path", "/api/log/direct");

                directLogger.info("Direct logging test message " + i);

                // 너무 빠른 로그 생성 방지를 위한 최소 지연
                if (i % 1000 == 0 && i > 0) {
                    Thread.sleep(10);
                }
            }

            long duration = System.currentTimeMillis() - startTime;
            return CompletableFuture.completedFuture(
                    String.format("Direct logging completed: %d logs in %d ms", count, duration));
        } catch (Exception e) {
            directLogger.error("Error during direct logging: " + e.getMessage(), e);
            return CompletableFuture.completedFuture("Error during direct logging: " + e.getMessage());
        } finally {
            MDC.clear();
        }
    }

    /**
     * 파일 기반으로 로그를 기록
     * @param count 로그 갯수
     * @return 완료 메시지
     */
    @Async
    public CompletableFuture<String> logFile(int count) {
        long startTime = System.currentTimeMillis();

        try {
            MDC.put("loggingType", "file");
            for (int i = 0; i < count; i++) {
                String requestId = UUID.randomUUID().toString();
                MDC.put("requestId", requestId);
                MDC.put("method", "POST");
                MDC.put("path", "/api/log/file");

                fileLogger.info("File logging test message " + i);

                // 너무 빠른 로그 생성 방지를 위한 최소 지연
                if (i % 1000 == 0 && i > 0) {
                    Thread.sleep(10);
                }
            }

            long duration = System.currentTimeMillis() - startTime;
            return CompletableFuture.completedFuture(
                    String.format("File logging completed: %d logs in %d ms", count, duration));
        } catch (Exception e) {
            fileLogger.error("Error during file logging: " + e.getMessage(), e);
            return CompletableFuture.completedFuture("Error during file logging: " + e.getMessage());
        } finally {
            MDC.clear();
        }
    }
}
