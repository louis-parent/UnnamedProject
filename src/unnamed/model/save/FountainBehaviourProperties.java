package unnamed.model.save;

import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.behaviour.FountainBehaviour;
import unnamed.model.element.map.tile.behaviour.TileBehaviour;

public class FountainBehaviourProperties extends BehaviourProperties
{
	private static final long serialVersionUID = 5584311748338486491L;
	
	private int cooldown;

	public FountainBehaviourProperties(FountainBehaviour behaviour)
	{
		super(behaviour);
		
		this.cooldown = behaviour.getCooldown();
	}

	public TileBehaviour getBehaviour(Tile tile)
	{
		FountainBehaviour fountain = (FountainBehaviour) super.getBehaviour(tile);
		
		fountain.setCooldwn(this.cooldown);
		
		return fountain;
	}
}
