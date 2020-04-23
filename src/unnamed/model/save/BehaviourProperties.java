package unnamed.model.save;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import unnamed.model.element.map.tile.Tile;
import unnamed.model.element.map.tile.behaviour.FountainBehaviour;
import unnamed.model.element.map.tile.behaviour.TileBehaviour;

public class BehaviourProperties implements Serializable
{
	private static final long serialVersionUID = 6246499339317614039L;
	
	private Class<? extends TileBehaviour> behaviourType;

	public BehaviourProperties(TileBehaviour behaviour)
	{
		this.behaviourType = behaviour.getClass();
	}

	public static BehaviourProperties getBehaviourProperties(TileBehaviour behaviour)
	{
		if(behaviour.getClass().equals(FountainBehaviour.class))
		{
			return new FountainBehaviourProperties((FountainBehaviour) behaviour);
		}
		else
		{
			return new BehaviourProperties(behaviour);
		}
	}

	public TileBehaviour getBehaviour(Tile tile)
	{
		TileBehaviour behaviour = null;
		
		try
		{
			behaviour = this.behaviourType.getConstructor(Tile.class).newInstance(tile);
		}
		catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		
		return behaviour;
	}
}
