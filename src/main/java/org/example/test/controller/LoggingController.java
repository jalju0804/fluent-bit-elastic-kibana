package org.example.test.controller;

import org.example.test.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/log")
public class LoggingController {

    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @Autowired
    private LoggingService loggingService;

    /**
     * 직접 TCP 로그 전송 테스트
     * @param count 로그 갯수 (기본값: 1000)
     * @return 테스트 결과
     */
    @PostMapping("/direct")
    public ResponseEntity<Map<String, Object>> testDirectLogging(
            @RequestParam(defaultValue = "1000") int count) {

        logger.info("Starting direct logging test with {} logs", count);
        CompletableFuture<String> future = loggingService.logDirect(count);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "started");
        response.put("type", "direct");
        response.put("count", count);

        return ResponseEntity.ok(response);
    }

    /**
     * 파일 기반 로그 전송 테스트
     * @param count 로그 갯수 (기본값: 1000)
     * @return 테스트 결과
     */
    @PostMapping("/file")
    public ResponseEntity<Map<String, Object>> testFileLogging(
            @RequestParam(defaultValue = "1000") int count) {

        logger.info("Starting file logging test with {} logs", count);
        CompletableFuture<String> future = loggingService.logFile(count);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "started");
        response.put("type", "file");
        response.put("count", count);

        return ResponseEntity.ok(response);
    }

    /**
     * 직접 TCP와 파일 기반 로그 전송 모두 테스트 (비교용)
     * @param count 로그 갯수 (기본값: 1000)
     * @return 테스트 결과
     */
    @PostMapping("/compare")
    public ResponseEntity<Map<String, Object>> compareLoggingMethods(
            @RequestParam(defaultValue = "1000") int count) {

        logger.info("Starting comparison test with {} logs for each method", count);

        CompletableFuture<String> directFuture = loggingService.logDirect(count);
        CompletableFuture<String> fileFuture = loggingService.logFile(count);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "started");
        response.put("count", count);

        return ResponseEntity.ok(response);
    }

    /**
     * 로깅 결과 확인 엔드포인트
     * @return 마지막 실행된 로깅 테스트의 결과
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "running");
        response.put("message", "Check application logs for detailed results");

        return ResponseEntity.ok(response);
    }
}