package org.bookiosk.ddd.domain.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Fluent DSL builder for constructing a {@link StateMachine}.
 *
 * <pre>{@code
 * StateMachine<Status, Event, Context> machine =
 *     StateMachineBuilder.<Status, Event, Context>create()
 *         .externalTransition()
 *             .from(Status.DRAFT).to(Status.SUBMITTED).on(Event.SUBMIT)
 *             .when(ctx -> ctx.isValid()).perform(ctx -> ctx.record())
 *             .build()
 *         .internalTransition()
 *             .from(Status.SUBMITTED).on(Event.NOTE)
 *             .perform(ctx -> ctx.addNote())
 *             .build()
 *         .build("order-workflow");
 * }</pre>
 *
 * @param <S> state type
 * @param <E> event type
 * @param <C> context type
 */
public class StateMachineBuilder<S, E, C> {

    private final Map<S, State<S, E, C>> stateMap;

    private StateMachineBuilder() {
        this.stateMap = new HashMap<>();
    }

    public static <S, E, C> StateMachineBuilder<S, E, C> create() {
        return new StateMachineBuilder<>();
    }

    public TransitionBuilder externalTransition() {
        return new TransitionBuilder(Transition.TransitionType.EXTERNAL, this);
    }

    public TransitionBuilder internalTransition() {
        return new TransitionBuilder(Transition.TransitionType.INTERNAL, this);
    }

    public StateMachine<S, E, C> build(String machineId) {
        return new StateMachine<>(machineId, stateMap);
    }

    private State<S, E, C> getOrCreateState(S stateId) {
        return stateMap.computeIfAbsent(stateId, State::new);
    }

    private void addTransition(S source, S target, E event,
                               Transition.TransitionType type,
                               Predicate<C> guard, Consumer<C> action) {
        Transition<S, E, C> t = new Transition<>(source, target, guard, action, type);
        getOrCreateState(source).addTransition(event, t);
    }

    // ========================================================================
    // TransitionBuilder
    // ========================================================================

    public class TransitionBuilder {

        private final Transition.TransitionType type;
        private final StateMachineBuilder<S, E, C> parent;

        private S from;
        private S[] fromAmong;
        private S to;
        private E on;
        private Predicate<C> when;
        private Consumer<C> perform;

        TransitionBuilder(Transition.TransitionType type, StateMachineBuilder<S, E, C> parent) {
            this.type = type;
            this.parent = parent;
        }

        public TransitionBuilder from(S state) {
            this.from = state;
            this.fromAmong = null;
            return this;
        }

        @SafeVarargs
        public final TransitionBuilder fromAmong(S... states) {
            this.fromAmong = states;
            this.from = null;
            return this;
        }

        public TransitionBuilder to(S state) {
            this.to = state;
            return this;
        }

        public TransitionBuilder on(E event) {
            this.on = event;
            return this;
        }

        public TransitionBuilder when(Predicate<C> guard) {
            this.when = guard;
            return this;
        }

        public TransitionBuilder perform(Consumer<C> action) {
            this.perform = action;
            return this;
        }

        @SuppressWarnings("unchecked")
        public StateMachineBuilder<S, E, C> build() {
            S[] sources;
            if (from != null) {
                sources = (S[]) new Object[]{from};
            } else if (fromAmong != null && fromAmong.length > 0) {
                sources = fromAmong;
            } else {
                throw new StateMachineException("Transition has no source. Call from() or fromAmong() before build().");
            }
            if (on == null) {
                throw new StateMachineException("Transition has no event. Call on() before build().");
            }
            for (S src : sources) {
                S target;
                if (type == Transition.TransitionType.INTERNAL) {
                    target = src;
                } else {
                    if (to == null) {
                        throw new StateMachineException("External transition has no target. Call to() before build().");
                    }
                    target = to;
                }
                parent.addTransition(src, target, on, type, when, perform);
            }
            return parent;
        }
    }
}
