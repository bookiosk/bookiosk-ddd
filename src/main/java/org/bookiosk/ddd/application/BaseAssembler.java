package org.bookiosk.ddd.application;

/**
 * Marker class for application-layer assemblers.
 * Converts between Client-layer DTOs and Domain-layer objects
 * (aggregates, entities, results). Pure field mapping — no business logic.
 *
 * <p>Each assembler defines its own conversion methods with
 * concrete source/target types. Naming: {@code {Aggregate}Assembler}.
 */
public abstract class BaseAssembler {
}
