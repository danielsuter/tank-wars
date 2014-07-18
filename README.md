# Tank Wars

## Architecture

## Technologies
* Java 8
* Servlet 3.1
* Tomcat 8
* Gradle

## Other

### How to get gradle working with eclipse
```
dependencies {
  providedCompile 'javax.servlet:javax.servlet-api:3.1.0'
}

eclipse {
	wtp {
		facet {
			facet name: 'jst.web', version: '3.1'
			facet name: 'java', version: '1.8'
		}
	}
}
```

The eclipse wtp plugin allows to generate all neccessary eclipse files. The facets need to be configured manually for later versions (such as Java 8 and servlet 3.1).

### Websocket tutorial from oracle
http://docs.oracle.com/javaee/7/tutorial/doc/websocket.htm
