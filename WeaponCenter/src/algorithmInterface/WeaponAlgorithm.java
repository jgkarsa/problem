package algorithmInterface;

import java.util.ArrayList;
import other.Instance;

public interface WeaponAlgorithm {
	
	void findAnswer();
	void setPopulation(double [][] population);
	void setStopCondition(int time);
	void setGlobalBestParticle(Instance instance);
	ArrayList<Instance> getPopulation();
	double getIndividualPos();
	Instance getBestLocalAnswer();
	

}
