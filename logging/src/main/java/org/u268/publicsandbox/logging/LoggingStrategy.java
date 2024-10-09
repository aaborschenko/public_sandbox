package org.u268.publicsandbox.logging;

import java.util.List;
import java.util.function.Consumer;

enum LoggingStrategy {

    IS_DEBUG_ENABLED {
        @Override
        void execute(List<SampleDTO> dtoList, int multiplier) {

            simulateExtensiveLogging(multiplier, dtoList, sampleDTO -> {

                if (App.log.isDebugEnabled()) {
                    App.log.debug("SampleDTO object: {}", sampleDTO);
                }
            });
        }

        @Override
        String whoAmI() {
            return "Using traditional IF logging strategy";
        }
    },

    LAMBDA {
        @Override
        void execute(List<SampleDTO> dtoList, int multiplier) {

            simulateExtensiveLogging(multiplier, dtoList, sampleDTO ->

                    App.log.debug("SampleDTO object: {}", () -> sampleDTO));
        }

        @Override
        String whoAmI() {
            return "Using Lambda logging strategy";
        }
    },

    LAMBDA_WITH_STRING_FORMAT {
        @Override
        void execute(List<SampleDTO> dtoList, int multiplier) {

            simulateExtensiveLogging(multiplier, dtoList, sampleDTO ->

                    App.log.debug(() -> String.format("SampleDTO object: %s", sampleDTO)));
        }

        @Override
        String whoAmI() {
            return "Using Lambda with String.format logging strategy";
        }
    },

    STATIC_IS_DEBUG_ENABLED {
        @Override
        void execute(List<SampleDTO> dtoList, int multiplier) {

            simulateExtensiveLogging(multiplier, dtoList, sampleDTO -> {

                if (App.STATIC_VAR_IS_DEBUG_ENABLED) {
                    App.log.debug("SampleDTO object: {}", sampleDTO);
                }
            });
        }

        @Override
        String whoAmI() {
            return "Using Precomputed Flag for Debug Level logging strategy";
        }
    };

    abstract void execute(List<SampleDTO> dtoList, int multiplier);

    abstract String whoAmI();

    private static void simulateExtensiveLogging(int multiplier, List<SampleDTO> dtoList, Consumer<SampleDTO> action) {

        for (int i = 0; i < multiplier; i++) {

            for (SampleDTO sampleDTO : dtoList) {
                action.accept(sampleDTO);
            }
        }
    }
}