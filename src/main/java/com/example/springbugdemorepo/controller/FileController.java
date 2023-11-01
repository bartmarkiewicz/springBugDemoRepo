package com.example.springbugdemorepo.controller;

import com.example.springbugdemorepo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
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

  private final FileService fileService;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("/{fileId}")
  public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable String fileId) {
    var fileObj = fileService.getTestFileObjById(fileId);

    final StreamingResponseBody body = outputStream -> {
      try (outputStream; var inputStream = fileService.getFileContents(fileObj)) {
        inputStream.transferTo(outputStream);
      }
    };

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(fileObj.getSizeBytes())
        .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileObj.getFilename()))
        .body(body);
  }
}
