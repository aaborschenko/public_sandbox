package org.u268.publicsandbox.logging;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing Log4j2 logging strategies
 * <p>
 * Build:
 * mvn clean install
 * You can run the program with the IS_DEBUG_ENABLED strategy as an argument:
 * <p>
 *  mvn exec:java -Dexec.mainClass="com.example.App" -Dexec.args="STATIC_IS_DEBUG_ENABLED"
 * <p>
 * Other strategies are:
 * <p>
 * IS_DEBUG_ENABLED, LAMBDA, LAMBDA_WITH_STRING_FORMAT, STATIC_IS_DEBUG_ENABLED
 */
public class App {

    public static final Logger log = LogManager.getLogger(App.class);

    /**
     * Precomputed flag for debug level - !!! may be a problem for applications that change the log level at runtime !!!
     * <p>
     * For now ist is just a matter of experiment :)
     */
    public static final boolean STATIC_VAR_IS_DEBUG_ENABLED = log.isDebugEnabled();

    /**
     * Array of multipliers to test the performance of different logging strategies
     */
    private static final int[] NUMBER_OF_LOG4J_CALLS_WITHIN_SAME_THREAD = {10, 100, 1000, 10000, 100000, 1000000};

    public static void main(String[] args) {

        log.info("You are so eager to learn about logging tricks!");

        final List<SampleDTO> dtoList = createSampleDTOList(10000);

        log.trace("List of 10K SampleDTO objects created successfully");

        if (args.length < 1) {
            log.error("Please provide a logging strategy: IS_DEBUG_ENABLED, LAMBDA, LAMBDA_WITH_STRING_FORMAT, STATIC_IS_DEBUG_ENABLED");
            return;
        }

        LoggingStrategy loggingStrategy;
        try {
            loggingStrategy = LoggingStrategy.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
            log.error("Unknown logging strategy: {}", args[0]);
            return;
        }

        log.info("{}", loggingStrategy.whoAmI());

        for (int numberOfExecutions : NUMBER_OF_LOG4J_CALLS_WITHIN_SAME_THREAD) {

            log.info("Testing with NUMBER_OF_LOG4J_CALLS_WITHIN_SAME_THREAD = {}", numberOfExecutions);

            StopWatch stopWatch = StopWatch.createStarted();

            loggingStrategy.execute(dtoList, numberOfExecutions);

            stopWatch.stop();
            log.info("Time taken {} ms", stopWatch.getTime());
        }
    }

    /**
     * Creates a list of SampleDTO objects and takes the number of randomly created SampleDTO objects as input
     *
     * @param numberOfObjects
     * @return
     */
    static List<SampleDTO> createSampleDTOList(int numberOfObjects) {

        final List<SampleDTO> sampleDTOList = new ArrayList<>();

        for (int i = 0; i < numberOfObjects; i++) {
            SampleDTO sampleDTO = new SampleDTO();
            sampleDTO.setName("Name " + RandomStringUtils.randomAlphanumeric(20));
            sampleDTO.setAddress("Address " + RandomStringUtils.randomAlphanumeric(50));
            sampleDTO.setPhoneNumber(RandomStringUtils.randomNumeric(10));
            sampleDTO.setEmail(RandomStringUtils.randomAlphabetic(10) + "@example.com");
            sampleDTO.setCity("City " + RandomStringUtils.randomAlphabetic(10));
            sampleDTO.setCountry("Country " + RandomStringUtils.randomAlphabetic(10));
            sampleDTO.setPostalCode(RandomStringUtils.randomNumeric(6));
            sampleDTOList.add(sampleDTO);
        }

        return sampleDTOList;
    }

}