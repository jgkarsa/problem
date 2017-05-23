package storage;

import java.util.ArrayList;

import other.Instance;

public class Solution {

	private int localNum;

	private Instance localBestAns;

	private ArrayList<Instance> otherBestAns;

	public Solution() {

		otherBestAns = new ArrayList<Instance>();

	}

	public void setLocalNum(int localNum) {

		this.localNum = localNum;
	}

	public void setLocalBestAns(Instance localBestAns) {

		this.localBestAns = localBestAns;

	}

	public void addOtherBestAns(Instance instance) {

		otherBestAns.add(instance);

	}

	public boolean checkOtherBestAns() {

		if (otherBestAns.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public int getLocalNum() {

		return localNum;

	}

	public Instance getLocalBestAns() {

		return localBestAns;

	}

	public ArrayList<Instance> getOtherBestAns() {

		return otherBestAns;
	}

	public void clearOtherBestAns() {

		otherBestAns.clear();
	}

}
