package bi.zum.lab3;

import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.api.ga.AbstractIndividual;
import cz.cvut.fit.zum.api.ga.AbstractPopulation;
import cz.cvut.fit.zum.data.StateSpace;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Your name
 */
public class Population extends AbstractPopulation {

    public Population(AbstractEvolution evolution, int size) {
        individuals = new Individual[size];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(evolution, true);
            individuals[i].computeFitness();
        }
    }

    /**
     * Method to select individuals from population
     *
     * @param count The number of individuals to be selected
     * @return List of selected individuals
     */
    public List<AbstractIndividual> selectIndividuals(int count) {
        ArrayList<AbstractIndividual> selected = new ArrayList<AbstractIndividual>();
        // example of random selection of N individuals
//        Random r = new Random();
//        AbstractIndividual individual = individuals[r.nextInt(individuals.length)];
//        while (selected.size() < count) {
//            selected.add(individual);
//            individual = individuals[r.nextInt(individuals.length)];
//        }
//
        // TODO: implement your own (and better) method of selection
        double sumFitnesses = 0;
        for (int i = 0; i < individuals.length; i++) {
            sumFitnesses += individuals[i].getFitness();
        }
        double [] probabilities = new double[individuals.length];
        double sumOfProbabilities = 0;
        for (int i = 0; i < individuals.length; i++) {
            probabilities[i] = sumOfProbabilities + (individuals[i].getFitness()/sumFitnesses);
            sumOfProbabilities+=probabilities[i];
        }
        while(selected.size() < count){
            double rand = new Random().nextDouble();
            for (int i = 0; i < probabilities.length; i++) {
                if(rand > probabilities[i%probabilities.length] && rand < probabilities[(i+1)%probabilities.length]){
                    selected.add(individuals[i]);
                }

            }
        }
        return selected;
    }
}
