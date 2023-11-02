package com.example.springbugdemorepo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbugdemorepo.controller.FileController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FileController.class)
public class FileControllerTest {

  private static final String FILE_ID = "1";
  private static final String FILE_NAME = "name";
  private static final String FILE_SIZE = "4";

  @Autowired
  protected WebApplicationContext context;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    //need spring security, without a filter chain the bug won't occur
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  //bug occurs rarely in apps with few filters, so we need to run it many times
  //Instead of repeated test annotation a while loop or a JUnit run config can be used to run the test
  @RepeatedTest(50000)
  void testDownloadFile() throws Exception {
    //typical download file mockmvc test
    var mvcResult = mockMvc.perform(get("/rest/fish/%s".formatted(FILE_ID))
            .with(csrf())
            .with(user("valid")))
        .andExpect(request().asyncStarted()) //bug occurs in async requests
        .andDo(result -> result.getAsyncResult(3000L))
        .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult))
        .andExpect(header().string("Content-Disposition", "attachment; filename=\"%s\"".formatted(FILE_NAME)))
        .andExpect(header().string("Content-Length", FILE_SIZE))
        .andExpect(content().contentType("application/octet-stream"))
        .andExpect(status().isOk());
  }
}
