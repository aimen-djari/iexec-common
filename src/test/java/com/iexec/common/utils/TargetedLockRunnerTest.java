package com.iexec.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;

class TargetedLockRunnerTest {
    private static final int NUMBER_OF_THREADS = 100;
    private static final int NUMBER_OF_CALLS = 100;

    // region Check methods actually run what's expected
    @Test
    void runWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        Wrapper<Integer> wrappedValue = new Wrapper<>(0);
        locks.runWithLock(1, () -> wrappedValue.setValue(1));

        Assertions.assertThat(wrappedValue.getValue()).isEqualTo(1);
    }

    @Test
    void acceptWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        Wrapper<Integer> wrappedValue = new Wrapper<>(0);
        locks.acceptWithLock(1, wrappedValue::setValue);

        Assertions.assertThat(wrappedValue.getValue()).isEqualTo(1);
    }

    @Test
    void getWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        boolean hasRun = locks.getWithLock(1, () -> true);

        Assertions.assertThat(hasRun).isTrue();
    }

    @Test
    void applyWithLock() {
        final TargetedLockRunner<Integer> locks = new TargetedLockRunner<>();

        Wrapper<Integer> wrappedValue = new Wrapper<>(0);
        boolean hasRun = locks.applyWithLock(1, wrappedValue::setValueAndAcknowledge);

        Assertions.assertThat(hasRun).isTrue();
        Assertions.assertThat(wrappedValue.getValue()).isEqualTo(1);
    }

    @Data
    @AllArgsConstructor
    private static class Wrapper<T> {
        private T value;

        public boolean setValueAndAcknowledge(T value) {
            this.value = value;
            return true;
        }
    }
    // endregion

    // region Check synchronization works
    /**
     * A constant value is used to lock all operations:
     * final order should be a list within which each value should not interfere with others.
     * E.g. [2, 2, 2, 1, 1, 1, 0, 0, 0, 3, 3, 3] would be an expected result,
     * however [2, <b>1</b>, 2, 2, 1, 1, 0, 0, 0, 3, 3, 3] would not be an expected result as 1 interferes with 2.
     */
    @Test
    void runWithLockOnConstantValue() {
        runWithLock(i -> true);
        Assertions.assertThatThrownBy(() -> runWithoutLock(i -> true))
                .getRootCause()
                .hasMessageContaining("Synchronization failed");
    }

    /**
     * Parity is used to lock all operations:
     * final order should be a list within which each value should not interfere with others of same parity.
     * E.g. [2, 1, 2, 2, 0, 1, 1, 3, 0, 0, 0, 3, 3] would be an expected result,
     * however [2, 1, 2, 2, 1, <b>3</b>, 1, 0, 0, 0, 3, 3] would not be an expected result as 3 interferes with 1.
     */
    @Test
    void runWithLockOnParity() {
        runWithLock(i -> i % 2 == 0);
        Assertions.assertThatThrownBy(() -> runWithoutLock(i -> i % 2 == 0))
                .getRootCause()
                .hasMessageContaining("Synchronization failed");
    }

    /**
     * Operation's value are used to lock operations:
     * any order is then acceptable as we don't care about interferences between distinct operations.
     */
    @Test
    void runWithLockPerValue() {
        runWithLock(Function.identity());

        // As any order is acceptable, there should not be any exception there.
        Assertions.assertThatCode(() -> runWithoutLock(Function.identity()))
                .doesNotThrowAnyException();
    }

    /**
     * Parity is used to lock all operations:
     * final order should be a list within which each value should not interfere with others of same parity.
     * E.g. [2, 1, 2, 2, 0, 1, 1, 3, 0, 0, 0, 3, 3] would be an expected result,
     * however [2, 1, 2, 2, 1, <b>3</b>, 1, 0, 0, 0, 3, 3] would not be an expected result as 3 interferes with 1.
     */
    @Test
    void acceptWithLockOnParity() {
        acceptWithLock(i -> i % 2 == 0);
        Assertions.assertThatThrownBy(() -> runWithoutLock(i -> i % 2 == 0))
                .getRootCause()
                .hasMessageContaining("Synchronization failed");
    }

    /**
     * Parity is used to lock all operations:
     * final order should be a list within which each value should not interfere with others of same parity.
     * E.g. [2, 1, 2, 2, 0, 1, 1, 3, 0, 0, 0, 3, 3] would be an expected result,
     * however [2, 1, 2, 2, 1, <b>3</b>, 1, 0, 0, 0, 3, 3] would not be an expected result as 3 interferes with 1.
     */
    @Test
    void applyWithLockOnParity() {
        applyWithLock(i -> i % 2 == 0);
        Assertions.assertThatThrownBy(() -> applyWithoutLock(i -> i % 2 == 0))
                .getRootCause()
                .hasMessageContaining("Synchronization failed");
    }

    /**
     * Parity is used to lock all operations:
     * final order should be a list within which each value should not interfere with others of same parity.
     * E.g. [2, 1, 2, 2, 0, 1, 1, 3, 0, 0, 0, 3, 3] would be an expected result,
     * however [2, 1, 2, 2, 1, <b>3</b>, 1, 0, 0, 0, 3, 3] would not be an expected result as 3 interferes with 1.
     */
    @Test
    void getWithLockOnParity() {
        getWithLock(i -> i % 2 == 0);
    }
    // endregion

    // region Helpers
    /**
     * Runs an action a bunch of times
     *
     * @param <K> Type of the key associated to an action.
     */
    private <K> void run(BiConsumer<Integer, Map<K, Integer>> action) {
        final Map<K, Integer> remainingCallsPerKey = new ConcurrentHashMap<>();

        IntStream.range(0, NUMBER_OF_THREADS)
                .parallel()
                .forEach(threadPosition -> action.accept(threadPosition, remainingCallsPerKey));

        for (Integer remainingCalls : remainingCallsPerKey.values()) {
            // If we don't reach 0, the `remainingCalls` have been reset at some point.
            assertRemainingCallsIsZero(remainingCalls, "more than 0 remaining calls at the end.");
        }
    }

    /**
     * Runs an action a bunch of times
     * and checks it doesn't interfere with other runs of similar key.
     * <br>
     * It is known an action should be run {@code NUMBER_OF_CALLS} times
     * before the next action with a same key can be run.
     * So each action decrements {@code NUMBER_OF_CALLS} times the counter relative to its key.
     * If at the end of the run of an action, its counter is not 0,
     * that means the counter has been reset by another thread
     * - which shouldn't occur as there is a lock on the key.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void runWithLock(Function<Integer, K> keyProvider) {
        final TargetedLockRunner<K> locks = new TargetedLockRunner<>();
        run(
                (Integer threadPosition, Map<K, Integer> remainingCallsPerKey) -> locks.runWithLock(
                        keyProvider.apply(threadPosition),
                        () -> runAndDecrementRemainingCalls(
                                threadPosition,
                                keyProvider,
                                remainingCallsPerKey
                        )
                )
        );
    }

    /**
     * Runs an action a bunch of times
     * and checks it doesn't interfere with other runs of similar key.
     * <br>
     * It is known an action should be run {@code NUMBER_OF_CALLS} times
     * before the next action with a same key can be run.
     * So each action decrements {@code NUMBER_OF_CALLS} times the counter relative to its key.
     * If at the end of the run of an action, its counter is not 0,
     * that means the counter has been reset by another thread
     * - which shouldn't occur as there is a lock on the key.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void acceptWithLock(Function<Integer, K> keyProvider) {
        final TargetedLockRunner<K> locks = new TargetedLockRunner<>();
        run(
                (Integer threadPosition, Map<K, Integer> remainingCallsPerKey) -> locks.acceptWithLock(
                        keyProvider.apply(threadPosition),
                        k -> runAndDecrementRemainingCalls(
                                threadPosition,
                                keyProvider,
                                remainingCallsPerKey
                        )
                )
        );
    }

    /**
     * Runs an action a bunch of times
     * and checks it doesn't interfere with other runs of similar key.
     * <br>
     * It is known an action should be run {@code NUMBER_OF_CALLS} times
     * before the next action with a same key can be run.
     * So each action decrements {@code NUMBER_OF_CALLS} times the counter relative to its key.
     * If at the end of the run of an action, its counter is not 0,
     * that means the counter has been reset by another thread
     * - which shouldn't occur as there is a lock on the key.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void applyWithLock(Function<Integer, K> keyProvider) {
        final TargetedLockRunner<K> locks = new TargetedLockRunner<>();
        run(
                (Integer threadPosition, Map<K, Integer> remainingCallsPerKey) -> locks.applyWithLock(
                        keyProvider.apply(threadPosition),
                        k -> applyAndDecrementRemainingCalls(
                                threadPosition,
                                keyProvider,
                                remainingCallsPerKey
                        )
                )
        );
    }

    /**
     * Runs an action a bunch of times
     * and checks it doesn't interfere with other runs of similar key.
     * <br>
     * It is known an action should be run {@code NUMBER_OF_CALLS} times
     * before the next action with a same key can be run.
     * So each action decrements {@code NUMBER_OF_CALLS} times the counter relative to its key.
     * If at the end of the run of an action, its counter is not 0,
     * that means the counter has been reset by another thread
     * - which shouldn't occur as there is a lock on the key.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void getWithLock(Function<Integer, K> keyProvider) {
        final TargetedLockRunner<K> locks = new TargetedLockRunner<>();
        run(
                (Integer threadPosition, Map<K, Integer> remainingCallsPerKey) -> locks.getWithLock(
                        keyProvider.apply(threadPosition),
                        () -> applyAndDecrementRemainingCalls(
                                threadPosition,
                                keyProvider,
                                remainingCallsPerKey
                        )
                )
        );
    }

    /**
     * Runs an action a bunch of times
     * and checks it doesn't interfere with other runs of similar key.
     * <br>
     * It is known an action should be run {@code NUMBER_OF_CALLS} times
     * before the next action with a same key can be run.
     * So each action decrements {@code NUMBER_OF_CALLS} times the counter relative to its key.
     * If at the end of the run of an action, its counter is not 0,
     * that means the counter has been reset by another thread
     * - which should occur as there is no lock on key.
     * <br>
     * That's the same as {@link TargetedLockRunnerTest#runWithLock(Function)}
     * but there's no lock so synchronization should fail.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void runWithoutLock(Function<Integer, K> keyProvider) {
        run(
                (Integer threadPosition, Map<K, Integer> remainingCallsPerKey) -> runAndDecrementRemainingCalls(
                        threadPosition,
                        keyProvider,
                        remainingCallsPerKey
                ));
    }

    /**
     * Runs an action a bunch of times
     * and checks it doesn't interfere with other runs of similar key.
     * <br>
     * It is known an action should be run {@code NUMBER_OF_CALLS} times
     * before the next action with a same key can be run.
     * So each action decrements {@code NUMBER_OF_CALLS} times the counter relative to its key.
     * If at the end of the run of an action, its counter is not 0,
     * that means the counter has been reset by another thread
     * - which should occur as there is no lock on key.
     * <br>
     * That's the same as {@link TargetedLockRunnerTest#applyWithLock(Function)}
     * but there's no lock so synchronization should fail.
     *
     * @param <K> Type of the lock key.
     */
    private <K> void applyWithoutLock(Function<Integer, K> keyProvider) {
        run(
                (Integer threadPosition, Map<K, Integer> remainingCallsPerKey) -> applyAndDecrementRemainingCalls(
                        threadPosition,
                        keyProvider,
                        remainingCallsPerKey
                ));
    }

    /**
     * Basic action that sets {@code remainingCalls} to {@code NUMBER_OF_CALLS}
     * and decrements it {@code NUMBER_OF_CALLS} times.
     * <br>
     * Tests the final value of {@code remainingCalls} is 0.
     */
    private <K> void runAndDecrementRemainingCalls(int threadPosition,
                                                   Function<Integer, K> keyProvider,
                                                   Map<K, Integer> remainingCallsPerKey) {
        final K key = keyProvider.apply(threadPosition);
        remainingCallsPerKey.put(key, NUMBER_OF_CALLS);
        for (int i = 0; i < NUMBER_OF_CALLS; i++) {
            remainingCallsPerKey.computeIfPresent(key, (k, v) -> v - 1);
        }
        // If we don't reach 0, the `remainingCalls` have been reset.
        final Integer remainingCalls = remainingCallsPerKey.get(key);
        assertRemainingCallsIsZero(remainingCalls, "remaining calls have been reset.");
    }

    /**
     * Basic action that sets {@code remainingCalls} to {@code NUMBER_OF_CALLS}
     * and decrements it {@code NUMBER_OF_CALLS} times.
     * <br>
     * Tests the final value of {@code remainingCalls} is {@literal 0}.
     *
     * @return The final value of {@code remainingCalls} (should always be {@literal 0}).
     */
    private <K> int applyAndDecrementRemainingCalls(int threadPosition,
                                                    Function<Integer, K> keyProvider,
                                                    Map<K, Integer> remainingCallsPerKey) {
        final K key = keyProvider.apply(threadPosition);
        remainingCallsPerKey.put(key, NUMBER_OF_CALLS);
        for (int i = 0; i < NUMBER_OF_CALLS; i++) {
            remainingCallsPerKey.computeIfPresent(key, (k, v) -> v - 1);
        }
        // If we don't reach 0, the `remainingCalls` have been reset.
        final Integer remainingCalls = remainingCallsPerKey.get(key);
        assertRemainingCallsIsZero(remainingCalls, "remaining calls have been reset.");
        return remainingCalls;
    }

    private void assertRemainingCallsIsZero(Integer remainingCalls, String newErrorMessage) {
        Assertions.assertThat(remainingCalls)
                .withFailMessage("Synchronization failed: " + newErrorMessage)
                .isZero();
    }
    // endregion
}