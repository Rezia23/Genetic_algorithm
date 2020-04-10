package bi.zum.lab3;

import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.api.ga.AbstractIndividual;
import cz.cvut.fit.zum.data.Edge;
import cz.cvut.fit.zum.data.StateSpace;
import cz.cvut.fit.zum.util.Pair;

import java.util.List;
import java.util.Random;


/**
 * @author Your Name
 */
public class Individual extends AbstractIndividual {

    private double fitness = Double.NaN;
    private AbstractEvolution evolution;

    private boolean[] genotype = new boolean[StateSpace.nodesCount()];


    /**
     * Creates a new individual
     *
     * @param evolution  The evolution object
     * @param randomInit <code>true</code> if the individial should be
     *                   initialized randomly (we do wish to initialize if we copy the individual)
     */
    public Individual(AbstractEvolution evolution, boolean randomInit) {
        this.evolution = evolution;
        if (randomInit) {
            for (int i = 0; i < genotype.length; i++) {
                genotype[i] = new Random().nextBoolean();
            }

        }
    }

    @Override
    public boolean isNodeSelected(int j) {
        return genotype[j];
    }

    /**
     * Repairs the genotype to make it valid, i.e. ensures all the edges
     * are in the vertex cover.
     */
    private void repair() {

        /* We iterate over all the edges */
        for (Edge edge : StateSpace.getEdges()) {
            if (!genotype[edge.getFromId()] && !genotype[edge.getToId()]) {
                boolean rand = new Random().nextBoolean();
                if (rand) {
                    genotype[edge.getFromId()] = true;
                } else {
                    genotype[edge.getToId()] = true;
                }
            }
        }
    }


    /**
     * Evaluate the value of the fitness function for the individual. After
     * the fitness is computed, the <code>getFitness</code> may be called
     * repeatedly, saving computation time.
     */
    @Override
    public void computeFitness() {
        this.repair();

        int numUsedNodes = 0;
        for (int i = 0; i < genotype.length; i++) {
            if (genotype[i]) {
                numUsedNodes++;
            }
        }
        this.fitness = StateSpace.nodesCount() - numUsedNodes;
    }

    /**
     * Only return the computed fitness value
     *
     * @return value of fitness function
     */
    @Override
    public double getFitness() {
        return this.fitness;
    }

    /**
     * Does random changes in the individual's genotype, taking mutation
     * probability into account.
     *
     * @param mutationRate Probability of a bit being inverted, i.e. a node
     *                     being added to/removed from the vertex cover.
     */
    @Override
    public void mutate(double mutationRate) {
        for (int i = 0; i < genotype.length; i++) {
            double rand = new Random().nextDouble();
            if (rand <= mutationRate) {
                genotype[i] = !genotype[i];
            }
        }
    }

    /**
     * Crosses the current individual over with other individual given as a
     * parameter, yielding a pair of offsprings.
     *
     * @param other The other individual to be crossed over with
     * @return A couple of offspring individuals
     */
    @Override
    public Pair crossover(AbstractIndividual other) {
        Pair<Individual, Individual> result = new Pair<>();
        Individual otherIndividual = (Individual) other;
        result.a = new Individual(evolution, false);
        result.b = new Individual(evolution, false);

        for (int i = 0; i < genotype.length; i++) {
            if (i < genotype.length / 2) {
                result.a.genotype[i] = genotype[i];
                result.b.genotype[i] = otherIndividual.genotype[i];
            } else {
                result.b.genotype[i] = genotype[i];
                result.a.genotype[i] = otherIndividual.genotype[i];
            }

        }
        return result;
    }


    /**
     * When you are changing an individual (eg. at crossover) you probably don't
     * want to affect the old one (you don't want to destruct it). So you have
     * to implement "deep copy" of this object.
     *
     * @return identical individual
     */
    @Override
    public Individual deepCopy() {
        Individual newOne = new Individual(evolution, false);
        System.arraycopy(genotype, 0, newOne.genotype, 0, genotype.length);

        // TODO: at least you should copy your representation of search-space state

        // for primitive types int, double, ...
        // newOne.val = this.val;

        // for objects (String, ...)
        // for your own objects you have to implement clone (override original inherited from Objcet)
        // newOne.infoObj = thi.infoObj.clone();

        // for arrays and collections (ArrayList, int[], Node[]...)
        /*
         // new array of the same length
         newOne.pole = new MyObjects[this.pole.length];
         // clone all items
         for (int i = 0; i < this.pole.length; i++) {
         newOne.pole[i] = this.pole[i].clone(); // object
         // in case of array of primitive types - direct assign
         //newOne.pole[i] = this.pole[i];
         }
         // for collections -> make new instance and clone in for/foreach cycle all members from old to new
         */

        newOne.fitness = this.fitness;
        return newOne;
    }

    /**
     * Return a string representation of the individual.
     *
     * @return The string representing this object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        /* TODO: implement own string representation, such as a comma-separated
         * list of indices of nodes in the vertex cover
         */


        sb.append(super.toString());

        return sb.toString();
    }

}
