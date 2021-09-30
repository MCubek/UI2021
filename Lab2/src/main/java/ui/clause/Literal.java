package ui.clause;

import java.util.Objects;

/**
 * Literal in logic operations.
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public class Literal {
    private final String name;
    private final boolean isNegated;

    public static Literal NIL_LITERAL = new Literal("NIL", false);

    public Literal(String name, boolean negated) {
        this.name = Objects.requireNonNull(name);
        this.isNegated = negated;
    }

    /**
     * Method that generates a new literal from this one but that is negated.
     *
     * @return new literal object negated.
     */
    public Literal negateAndCopy() {
        return new Literal(this.name, ! this.isNegated);
    }

    public String getName() {
        return name;
    }

    public boolean equalsNegated(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        if (isNegated == literal.isNegated) return false;
        return name.equalsIgnoreCase(literal.name);
    }

    @Override
    public String toString() {
        return (isNegated ? "~" : "") + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        if (isNegated != literal.isNegated) return false;
        return name.equalsIgnoreCase(literal.name);
    }

    @Override
    public int hashCode() {
        int result = name.toLowerCase().hashCode();
        result = 31 * result + (isNegated ? 1 : 0);
        return result;
    }
}
