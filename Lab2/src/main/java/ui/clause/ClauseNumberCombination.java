package ui.clause;

/**
 * Class representing two indexes of clauses from which another clause is generated.
 *
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
public class ClauseNumberCombination {
    private final Clause first;
    private final Clause second;

    public ClauseNumberCombination(Clause first, Clause second) {
        this.first = first;
        this.second = second;
    }

    public Clause getFirst() {
        return first;
    }

    public Clause getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClauseNumberCombination that = (ClauseNumberCombination) o;

        if (! first.equals(that.first)) return false;
        return second.equals(that.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }
}
