# ADR-001

## Title

Choose Modular Monolith as Initial Architecture

## Status

Accepted

## Context

The application is expected to grow significantly over time.

Building microservices from day one would introduce unnecessary operational complexity.

## Decision

Start with a modular monolith.

Each business module will have clear boundaries.

When scaling becomes necessary, modules can be extracted into independent microservices.

## Consequences

### Advantages

- Easier local development
- Faster delivery
- Simpler debugging
- Lower infrastructure cost

### Disadvantages

- Requires discipline to maintain module boundaries
