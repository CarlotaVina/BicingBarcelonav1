 /usr/java/jdk1.6.0_32/bin/java -Dfile.encoding=UTF-8 -classpath /home/cloudera/EntregaProyectoBicing/classes:/home/cloudera/proyecto/jdom/jdom-2.0.5/jdom-2.0.5.jar:/usr/lib/hadoop/client/activation.jar:/home/cloudera/proyecto/commons-configuration/commons-configuration-1.10/commons-configuration-1.10-test-sources.jar:/usr/lib/hbase/lib/commons-lang-2.5.jar:/usr/lib/hbase/lib/commons-logging-1.1.1.jar:/usr/lib/hbase/lib/guava-11.0.2.jar:/usr/lib/hadoop/hadoop-auth.jar:/usr/lib/hadoop/hadoop-common.jar:/usr/lib/hadoop/client/hadoop-common.jar:/usr/lib/hadoop-0.20-mapreduce/hadoop-core.jar:/usr/lib/hadoop-hdfs/hadoop-hdfs.jar:/usr/lib/hadoop/client/hadoop-mapreduce-client-core.jar:/usr/share/cmf/lib/cdh3u4x/hadoop-core-0.20.2-cdh3u4a.jar:/usr/lib/hbase/hbase.jar:/home/cloudera/proyecto/logjar/log4j-1.2.16.jar.zip:/usr/lib/pig/pig-0.11.0-cdh4.4.0.jar:/usr/lib/hbase/lib/protobuf-java-2.4.0a.jar:/usr/lib/hbase/lib/zookeeper.jar:/home/cloudera/EntregaProyectoBicing/lib/bicingpig.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-client/2.0.0-mr1-cdh4.5.0/hadoop-client-2.0.0-mr1-cdh4.5.0.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-common/2.0.0-cdh4.5.0/hadoop-common-2.0.0-cdh4.5.0.jar:/home/cloudera/.m2/repository/org/apache/commons/commons-math/2.1/commons-math-2.1.jar:/home/cloudera/.m2/repository/org/apache/hadoop/cloudera-jets3t/2.0.0-cdh4.5.0/cloudera-jets3t-2.0.0-cdh4.5.0.jar:/home/cloudera/.m2/repository/org/mockito/mockito-all/1.8.5/mockito-all-1.8.5.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-auth/2.0.0-cdh4.5.0/hadoop-auth-2.0.0-cdh4.5.0.jar:/home/cloudera/.m2/repository/com/jcraft/jsch/0.1.42/jsch-0.1.42.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-hdfs/2.0.0-cdh4.5.0/hadoop-hdfs-2.0.0-cdh4.5.0.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-core/2.0.0-mr1-cdh4.5.0/hadoop-core-2.0.0-mr1-cdh4.5.0.jar:/home/cloudera/.m2/repository/org/apache/hbase/hbase/0.94.6-cdh4.5.0/hbase-0.94.6-cdh4.5.0.jar:/home/cloudera/.m2/repository/com/yammer/metrics/metrics-core/2.1.2/metrics-core-2.1.2.jar:/home/cloudera/.m2/repository/com/google/guava/guava/11.0.2/guava-11.0.2.jar:/home/cloudera/.m2/repository/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar:/home/cloudera/.m2/repository/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:/home/cloudera/.m2/repository/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:/home/cloudera/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:/home/cloudera/.m2/repository/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:/home/cloudera/.m2/repository/commons-beanutils/commons-beanutils/1.7.0/commons-beanutils-1.7.0.jar:/home/cloudera/.m2/repository/commons-beanutils/commons-beanutils-core/1.8.0/commons-beanutils-core-1.8.0.jar:/home/cloudera/.m2/repository/com/github/stephenc/high-scale-lib/high-scale-lib/1.1.1/high-scale-lib-1.1.1.jar:/home/cloudera/.m2/repository/commons-codec/commons-codec/1.4/commons-codec-1.4.jar:/home/cloudera/.m2/repository/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:/home/cloudera/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:/home/cloudera/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar:/home/cloudera/.m2/repository/org/apache/zookeeper/zookeeper/3.4.5-cdh4.5.0/zookeeper-3.4.5-cdh4.5.0.jar:/home/cloudera/.m2/repository/org/jruby/jruby-complete/1.6.5/jruby-complete-1.6.5.jar:/home/cloudera/.m2/repository/org/mortbay/jetty/jetty/6.1.26.cloudera.2/jetty-6.1.26.cloudera.2.jar:/home/cloudera/.m2/repository/org/mortbay/jetty/jetty-util/6.1.26.cloudera.2/jetty-util-6.1.26.cloudera.2.jar:/home/cloudera/.m2/repository/org/mortbay/jetty/jsp-2.1/6.1.14/jsp-2.1-6.1.14.jar:/home/cloudera/.m2/repository/org/eclipse/jdt/core/3.1.1/core-3.1.1.jar:/home/cloudera/.m2/repository/org/mortbay/jetty/jsp-api-2.1/6.1.14/jsp-api-2.1-6.1.14.jar:/home/cloudera/.m2/repository/org/mortbay/jetty/servlet-api-2.5/6.1.14/servlet-api-2.56.1.14.jar:/home/cloudera/.m2/repository/org/codehaus/jackson/jackson-core-asl/1.8.8/jackson-core-asl-1.8.8.jar:/home/cloudera/.m2/repository/org/codehaus/jackson/jackson-mapper-asl/1.8.8/jackson-mapper-asl-1.8.8.jar:/home/cloudera/.m2/repository/org/codehaus/jackson/jackson-jaxrs/1.8.8/jackson-jaxrs-1.8.8.jar:/home/cloudera/.m2/repository/org/codehaus/jackson/jackson-xc/1.8.8/jackson-xc-1.8.8.jar:/home/cloudera/.m2/repository/org/slf4j/slf4j-api/1.4.3/slf4j-api-1.4.3.jar:/home/cloudera/.m2/repository/org/slf4j/slf4j-log4j12/1.4.3/slf4j-log4j12-1.4.3.jar:/home/cloudera/.m2/repository/tomcat/jasper-compiler/5.5.23/jasper-compiler-5.5.23.jar:/home/cloudera/.m2/repository/tomcat/jasper-runtime/5.5.23/jasper-runtime-5.5.23.jar:/home/cloudera/.m2/repository/org/jamon/jamon-runtime/2.3.1/jamon-runtime-2.3.1.jar:/home/cloudera/.m2/repository/com/google/protobuf/protobuf-java/2.4.0a/protobuf-java-2.4.0a.jar:/home/cloudera/.m2/repository/com/sun/jersey/jersey-core/1.8/jersey-core-1.8.jar:/home/cloudera/.m2/repository/com/sun/jersey/jersey-json/1.8/jersey-json-1.8.jar:/home/cloudera/.m2/repository/org/codehaus/jettison/jettison/1.1/jettison-1.1.jar:/home/cloudera/.m2/repository/com/sun/xml/bind/jaxb-impl/2.2.3-1/jaxb-impl-2.2.3-1.jar:/home/cloudera/.m2/repository/com/sun/jersey/jersey-server/1.8/jersey-server-1.8.jar:/home/cloudera/.m2/repository/asm/asm/3.1/asm-3.1.jar:/home/cloudera/.m2/repository/javax/xml/bind/jaxb-api/2.1/jaxb-api-2.1.jar:/home/cloudera/.m2/repository/javax/activation/activation/1.1/activation-1.1.jar:/home/cloudera/.m2/repository/stax/stax-api/1.0.1/stax-api-1.0.1.jar:/home/cloudera/.m2/repository/commons-httpclient/commons-httpclient/3.1/commons-httpclient-3.1.jar:/home/cloudera/.m2/repository/commons-io/commons-io/2.1/commons-io-2.1.jar:/home/cloudera/.m2/repository/org/apache/thrift/libthrift/0.9.1/libthrift-0.9.1.jar:/home/cloudera/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:/home/cloudera/.m2/repository/org/apache/httpcomponents/httpclient/4.2.5/httpclient-4.2.5.jar:/home/cloudera/.m2/repository/org/apache/httpcomponents/httpcore/4.2.4/httpcore-4.2.4.jar:/home/cloudera/.m2/repository/org/apache/pig/pig/0.12.0/pig-0.12.0.jar:/home/cloudera/.m2/repository/xmlenc/xmlenc/0.52/xmlenc-0.52.jar:/home/cloudera/.m2/repository/commons-net/commons-net/1.4.1/commons-net-1.4.1.jar:/home/cloudera/.m2/repository/commons-el/commons-el/1.0/commons-el-1.0.jar:/home/cloudera/.m2/repository/net/java/dev/jets3t/jets3t/0.7.1/jets3t-0.7.1.jar:/home/cloudera/.m2/repository/net/sf/kosmosfs/kfs/0.3/kfs-0.3.jar:/home/cloudera/.m2/repository/junit/junit/4.8.1/junit-4.8.1.jar:/home/cloudera/.m2/repository/hsqldb/hsqldb/1.8.0.10/hsqldb-1.8.0.10.jar:/home/cloudera/.m2/repository/oro/oro/2.0.8/oro-2.0.8.jar:/home/cloudera/.m2/repository/org/antlr/antlr-runtime/3.4/antlr-runtime-3.4.jar:/home/cloudera/.m2/repository/org/antlr/stringtemplate/3.2.1/stringtemplate-3.2.1.jar:/home/cloudera/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/home/cloudera/.m2/repository/org/antlr/ST4/4.0.4/ST4-4.0.4.jar:/home/cloudera/.m2/repository/org/apache/avro/avro/1.7.4/avro-1.7.4.jar:/home/cloudera/.m2/repository/com/thoughtworks/paranamer/paranamer/2.3/paranamer-2.3.jar:/home/cloudera/.m2/repository/org/xerial/snappy/snappy-java/1.0.4.1/snappy-java-1.0.4.1.jar:/home/cloudera/.m2/repository/org/apache/commons/commons-compress/1.4.1/commons-compress-1.4.1.jar:/home/cloudera/.m2/repository/org/tukaani/xz/1.0/xz-1.0.jar:/home/cloudera/.m2/repository/org/apache/pig/pigunit/0.12.0/pigunit-0.12.0.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-mapreduce-client-core/2.2.0/hadoop-mapreduce-client-core-2.2.0.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-yarn-common/2.2.0/hadoop-yarn-common-2.2.0.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-yarn-api/2.2.0/hadoop-yarn-api-2.2.0.jar:/home/cloudera/.m2/repository/com/google/inject/guice/3.0/guice-3.0.jar:/home/cloudera/.m2/repository/javax/inject/javax.inject/1/javax.inject-1.jar:/home/cloudera/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:/home/cloudera/.m2/repository/com/sun/jersey/jersey-test-framework/jersey-test-framework-grizzly2/1.9/jersey-test-framework-grizzly2-1.9.jar:/home/cloudera/.m2/repository/com/sun/jersey/jersey-test-framework/jersey-test-framework-core/1.9/jersey-test-framework-core-1.9.jar:/home/cloudera/.m2/repository/javax/servlet/javax.servlet-api/3.0.1/javax.servlet-api-3.0.1.jar:/home/cloudera/.m2/repository/com/sun/jersey/jersey-client/1.9/jersey-client-1.9.jar:/home/cloudera/.m2/repository/com/sun/jersey/jersey-grizzly2/1.9/jersey-grizzly2-1.9.jar:/home/cloudera/.m2/repository/org/glassfish/grizzly/grizzly-http/2.1.2/grizzly-http-2.1.2.jar:/home/cloudera/.m2/repository/org/glassfish/grizzly/grizzly-framework/2.1.2/grizzly-framework-2.1.2.jar:/home/cloudera/.m2/repository/org/glassfish/gmbal/gmbal-api-only/3.0.0-b023/gmbal-api-only-3.0.0-b023.jar:/home/cloudera/.m2/repository/org/glassfish/external/management-api/3.0.0-b012/management-api-3.0.0-b012.jar:/home/cloudera/.m2/repository/org/glassfish/grizzly/grizzly-http-server/2.1.2/grizzly-http-server-2.1.2.jar:/home/cloudera/.m2/repository/org/glassfish/grizzly/grizzly-rcm/2.1.2/grizzly-rcm-2.1.2.jar:/home/cloudera/.m2/repository/org/glassfish/grizzly/grizzly-http-servlet/2.1.2/grizzly-http-servlet-2.1.2.jar:/home/cloudera/.m2/repository/org/glassfish/javax.servlet/3.1/javax.servlet-3.1.jar:/home/cloudera/.m2/repository/com/sun/jersey/contribs/jersey-guice/1.9/jersey-guice-1.9.jar:/home/cloudera/.m2/repository/org/apache/hadoop/hadoop-annotations/2.2.0/hadoop-annotations-2.2.0.jar:/usr/java/jdk1.6.0_32/lib/tools.jar:/home/cloudera/.m2/repository/com/google/inject/extensions/guice-servlet/3.0/guice-servlet-3.0.jar:/home/cloudera/.m2/repository/io/netty/netty/3.6.2.Final/netty-3.6.2.Final.jar bicing.hbase.Generadortabla
