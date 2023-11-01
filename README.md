# MockHttpServletResponse Spring bug
Example repo of the MockHttpResponse bug. 
There is a bug which appears occassionaly in tests on the MockHttpServletResponse in Spring, due to a non-concurrent LinkedCaseInsensitiveMap being used.

# ConcurrentModificationException 
![image](https://github.com/bartmarkiewicz/springBugDemoRepo/assets/39217312/f2cecd9d-6f0d-4e13-9d31-dde0c2c6408f)

While the bug only appears rarely on a repo with a small/no custom filter chain (like this repo), appearing about 1 in 10000 test runs on average on my machine. In bigger repos the bug occurs much more often, causing flaky tests and necessiating the re-running of the tests. 

# How to replicate
- Pull the repository
- Run FileControllerTest
- This will run the test 50000 times, on my machine it takes around 2 minutes, you should see some test failures.
- 
#Typical stack trace
```
java.util.ConcurrentModificationException
	at java.base/java.util.HashMap.computeIfAbsent(HashMap.java:1221)
	at org.springframework.util.LinkedCaseInsensitiveMap.computeIfAbsent(LinkedCaseInsensitiveMap.java:239)
	at org.springframework.util.LinkedCaseInsensitiveMap.computeIfAbsent(LinkedCaseInsensitiveMap.java:50)
	at org.springframework.mock.web.MockHttpServletResponse.doAddHeaderValue(MockHttpServletResponse.java:748)
	at org.springframework.mock.web.MockHttpServletResponse.setHeaderValue(MockHttpServletResponse.java:695)
	at org.springframework.mock.web.MockHttpServletResponse.setHeader(MockHttpServletResponse.java:669)
	at jakarta.servlet.http.HttpServletResponseWrapper.setHeader(HttpServletResponseWrapper.java:132)
	at org.springframework.security.web.firewall.FirewalledResponse.setHeader(FirewalledResponse.java:54)
	at jakarta.servlet.http.HttpServletResponseWrapper.setHeader(HttpServletResponseWrapper.java:132)
	at org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.writeHeaders(XFrameOptionsHeaderWriter.java:103)
	at org.springframework.security.web.header.HeaderWriterFilter.writeHeaders(HeaderWriterFilter.java:99)
	at org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterResponse.writeHeaders(HeaderWriterFilter.java:132)
	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:93)
	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
	at org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:82)
	at org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:69)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:62)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
	at org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42)
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:374)
	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:233)
	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:191)
	at org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurer$DelegateFilter.doFilter(SecurityMockMvcConfigurer.java:132)
	at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:132)
	at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:201)
	at com.example.springbugdemorepo.FileControllerTest.testDownloadFile(FileControllerTest.java:66)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
```
    
