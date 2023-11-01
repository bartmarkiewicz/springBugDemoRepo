package com.example.springbugdemorepo.controller;

import java.io.ByteArrayInputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/rest/fish")
public class FileController {

  @GetMapping("/{fileId}")
  public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable String fileId) {
    final StreamingResponseBody body = outputStream -> {
      try (outputStream; var inputStream = new ByteArrayInputStream("2222".getBytes())) {
        inputStream.transferTo(outputStream);
      }
    };

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(4)
        .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", "name"))
        .body(body);
  }
}
