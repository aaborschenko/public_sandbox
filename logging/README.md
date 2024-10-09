# Testing Log4j2 logging strategies

Build:
`mvn clean install`
 
You can run the program with the IS_DEBUG_ENABLED strategy as an argument:

` mvn exec:java -Dexec.mainClass="org.u268.publicsandbox.logging.App" -Dexec.args="STATIC_IS_DEBUG_ENABLED"`

Other strategies are:

IS_DEBUG_ENABLED, LAMBDA, LAMBDA_WITH_STRING_FORMAT, STATIC_IS_DEBUG_ENABLED