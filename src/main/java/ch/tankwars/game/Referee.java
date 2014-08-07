package ch.tankwars.game;

import java.util.LinkedList;
import java.util.List;

public class Referee {
	
	private List<Tank> tanks = new LinkedList<>();
	
	public void addTank(Tank tank) {
		this.tanks.add(tank);
	}
	
	public void tankMadeHit(int tankId, int tankIdOfVictim) {
		for (Tank tank : tanks) {
			if (tank.getId() == tankId) {
				tank.madeHit(tankIdOfVictim);
				return;
			}
		}
	}
	
	public void tankMadeKill(int tankId, int tankIdOfVictim) {
		for (Tank tank : tanks) {
			if (tank.getId() == tankId) {
				tank.madeKill(tankIdOfVictim);
				return;
			}
		}
	}

}
