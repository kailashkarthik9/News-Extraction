# News Extraction

News Extraction is the online phase of the News Extraction and Summarization project

## Tech
News Extraction uses a number of open source projects to work properly:

* [JAWS](https://github.com/jaytaylor/jaws) - an open source API for integrating WordNet in Java
* [Apache Tomcat](http://tomcat.apache.org/) - an open-source Java Servlet Container
* [HTTP Components](https://hc.apache.org/) - a toolset of low level Java components focused on HTTP and associated protocols

## Installation
News Crawler requires the following JARs to run
* jaws-1.3.1.jar
* All JARs present in HTTPComponents

## Instructions

```sh
- Make sure you have downloaded, configured and executed the NewsCrawler project
- Download the dependencies and import the project on eclipse
- Right click on project -> Build Path -> Configure Build Path -> Libraries 
- Add the JAR to the class path
- Verify that the database and relations are according to the schema diagram
- Modify the default file locations for summary template/generation and File Repository
- Run -> Run Configurations -> Server -> Classpath -> Click on User Entries -> Add External JAR
- Run Query.jsp on the server
```
### Authors
* [Abha Suman](mailto:abhasuman2@gmail.com?Subject=Hello%20again)
* [Hariprasad KR](mailto:krhp2236@gmail.com?Subject=Hello%20again)
* [Kailash Karthik](mailto:kailashkarthik9@gmail.com?Subject=Hello%20again)