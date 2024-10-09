package com.example;

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
 * java com.example.App IS_DEBUG_ENABLED
 * <p>
 * Other strategies are:
 * <p>
 * IS_DEBUG_ENABLED, LAMBDA, LAMBDA_WITH_STRING_FORMAT, STATIC_IS_DEBUG_ENABLED
 */
public class App {

    private static final Logger log = LogManager.getLogger(App.class);

    /**
     * Precomputed flag for debug level - !!! may be a problem for applications that change the log level at runtime !!!
     * <p>
     * For now ist is just a matter of experiment :)
     */
    private static final boolean STATIC_VAR_IS_DEBUG_ENABLED = log.isDebugEnabled();

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

    private enum LoggingStrategy {

        IS_DEBUG_ENABLED {
            @Override
            void execute(List<SampleDTO> dtoList, int multiplier) {
                for (int i = 0; i < multiplier; i++) {
                    for (SampleDTO sampleDTO : dtoList) {
                        if (log.isDebugEnabled()) {
                            log.debug("SampleDTO object: {}", sampleDTO);
                        }
                    }
                }
            }

            @Override
            String whoAmI() {
                return "Using traditional IF logging strategy";
            }
        },

        LAMBDA {
            @Override
            void execute(List<SampleDTO> listOf1000, int multiplier) {
                for (int i = 0; i < multiplier; i++) {
                    for (SampleDTO sampleDTO : listOf1000) {
                        log.debug("SampleDTO object: {}", () -> sampleDTO);
                    }
                }
            }

            @Override
            String whoAmI() {
                return "Using Lambda logging strategy";
            }
        },

        LAMBDA_WITH_STRING_FORMAT {
            @Override
            void execute(List<SampleDTO> listOf1000, int multiplier) {
                for (int i = 0; i < multiplier; i++) {
                    for (SampleDTO sampleDTO : listOf1000) {
                        log.debug(() -> String.format("SampleDTO object: %s", sampleDTO));
                    }
                }
            }

            @Override
            String whoAmI() {
                return "Using Lambda with String.format logging strategy";
            }
        },

        STATIC_IS_DEBUG_ENABLED {
            @Override
            void execute(List<SampleDTO> listOf1000, int multiplier) {
                for (int i = 0; i < multiplier; i++) {
                    for (SampleDTO sampleDTO : listOf1000) {
                        if (STATIC_VAR_IS_DEBUG_ENABLED) {
                            log.debug("SampleDTO object: {}", sampleDTO);
                        }
                    }
                }
            }

            @Override
            String whoAmI() {
                return "Using Precomputed Flag for Debug Level logging strategy";
            }
        };

        abstract void execute(List<SampleDTO> listOf1000, int multiplier);

        abstract String whoAmI();
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

    static class SampleDTO {

        private String name;
        private String address;
        private String phoneNumber;
        private String email;
        private String city;
        private String country;
        private String postalCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        @Override
        public String toString() {
            return String.format("SampleDTO{name='%s', address='%s', phoneNumber='%s', email='%s', city='%s', country='%s', postalCode='%s'}",
                    name, address, phoneNumber, email, city, country, postalCode);
        }
    }
}