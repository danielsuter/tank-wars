package ch.tankwars.game;

import java.util.LinkedList;
import java.util.List;

public class Referee {
	
	private List<Tank> tanks = new LinkedList<>();
	
	public void addTank(Tank tank) {
		this.tanks.add(tank);
	}
	
	public void tankMadeHit(int tankId) {
		for (Tank tank : tanks) {
			if (tank.getId() == tankId) {
				tank.madeHit();
				return;
			}
		}
	}
	
	public void tankMadeKill(int tankId) {
		for (Tank tank : tanks) {
			if (tank.getId() == tankId) {
				tank.madeKill();
				return;
			}
		}
	}

}
