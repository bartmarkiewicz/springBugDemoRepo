# MockHttpServletResponse Spring bug
Example repo of the MockHttpResponse bug. 
There is a bug which appears occassionaly in tests on the MockHttpServletResponse in Spring, due to a non-concurrent LinkedCaseInsensitiveMap being used.

# ConcurrentModificationException 
![image](https://github.com/bartmarkiewicz/springBugDemoRepo/assets/39217312/f2cecd9d-6f0d-4e13-9d31-dde0c2c6408f)

While the bug only appears rarely, on a repo with a small/no custom filter chain, it appears about 1 in 10000 test runs on average. In bigger repos the bug occurs much more often, causing flaky tests
and necessiating the re-running of the tests. 

#How to replicate
- Pull the repository
- Run FileControllerTest with repeats, easiest done with Intellij and JUnit, however a while loop could potentially also be used, or some alternative set-up.
- ![image](https://github.com/bartmarkiewicz/springBugDemoRepo/assets/39217312/a81a9fb6-9889-4ea1-81bb-09a1facd73ec)
