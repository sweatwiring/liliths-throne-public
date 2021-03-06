package com.base.game.character.body;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.base.game.character.GameCharacter;
import com.base.game.character.body.types.OrificeInterface;
import com.base.game.character.body.valueEnums.Capacity;
import com.base.game.character.body.valueEnums.OrificeElasticity;
import com.base.game.character.body.valueEnums.OrificeModifier;
import com.base.game.character.body.valueEnums.OrificePlasticity;
import com.base.game.character.body.valueEnums.Wetness;
import com.base.game.dialogue.utils.UtilText;

public class OrificeNipples implements OrificeInterface, Serializable {
	private static final long serialVersionUID = 1L;
	
	private int elasticity, plasticity;
	private float capacity, stretchedCapacity;
	private boolean virgin;
	private Set<OrificeModifier> orificeModifiers;

	public OrificeNipples(int wetness, int capacity, int elasticity, int plasticity, boolean virgin, Collection<OrificeModifier> orificeModifiers) {
		this.capacity = capacity;
		stretchedCapacity = capacity;
		this.elasticity = elasticity;
		this.plasticity = plasticity;
		this.virgin = virgin;
		
		this.orificeModifiers = new HashSet<>();
		for(OrificeModifier om : orificeModifiers) {
			this.orificeModifiers.add(om);
		}
	}
	
	@Override
	public Wetness getWetness(GameCharacter owner) {
		return owner.getBreastLactation().getAssociatedWetness();
	}
	
	@Override
	/**<b>DO NOT USE THIS. NIPPLE WETNESS IS DETERMINED BY BREAST LACTATION.</b>*/
	public String setWetness(GameCharacter owner, int wetness) {
		throw new IllegalAccessError(":BlobPeek: (Nipple wetness was attempted to be set manually!)");
	}
	
	@Override
	public Capacity getCapacity() {
		return Capacity.getCapacityFromValue((int)capacity);
	}
	
	@Override
	public float getRawCapacityValue() {
		return capacity;
	}
	
	@Override
	public String setCapacity(GameCharacter owner, float capacity) {
		float initialCapacity = this.capacity;
		float capacityChange = 0;
		
		if (capacity <= 0) {
			if (this.capacity != 0) {
				capacityChange = 0 - this.capacity;
				this.capacity = 0;
			}
		} else if (capacity >= Capacity.SEVEN_GAPING.getMaximumValue()) {
			if (this.capacity != Capacity.SEVEN_GAPING.getMaximumValue()) {
				capacityChange = Capacity.SEVEN_GAPING.getMaximumValue() - this.capacity;
				this.capacity = Capacity.SEVEN_GAPING.getMaximumValue();
			}
		} else {
			if (this.capacity != capacity) {
				capacityChange = capacity - this.capacity;
				this.capacity = capacity;
			}
		}
		
		if(capacityChange == 0) {
			if(owner.isPlayer()) {
				return "<p style='text-align:center;'>[style.colourDisabled(The capacity of your [pc.nipples] doesn't change...)]</p>";
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The capacity of [npc.name]'s [pc.nipples] doesn't change...)]</p>");
			}
		}
		
		String capacityDescriptor = getCapacity().getDescriptor();
		if (capacityChange > 0) {
			if(initialCapacity == 0) { // Getting fuckable nipples:
				if (owner.isPlayer()) {
					return "<p>"
								+ "You squirm about uncomfortably as your [pc.nipples] grow unusually hard and sensitive."
								+ " A strange pressure starts to build up deep within your torso, and you let out a groan as you feel a drastic transformation taking place deep within your [pc.breasts]."
								+ " Your groan turns into [pc.a_moan+], which bursts out of your mouth as you feel your [pc.nipples] suddenly [style.boldGrow(spread open)], revealing a deep, fuckable passage that's formed behind them.</br>"
								+ "You now have [style.boldSex(" + capacityDescriptor + ", fuckable [pc.nipples])]!"
							+ "</p>";
				} else {
					return UtilText.parse(owner,
							"<p>" 
								+ "[npc.Name] squirms about uncomfortably as [npc.her] [npc.nipples] grow unusually hard and sensitive."
								+ " A strange pressure starts to build up deep within [npc.her] torso, and [npc.she] lets out a groan as a drastic transformation takes place deep within [npc.her] [npc.breasts]."
								+ " [npc.Her] groan turns into [npc.a_moan+], which bursts out of [npc.her] mouth as [npc.her] [npc.nipples] suddenly [style.boldGrow(spread open)], revealing a deep, fuckable passage that's formed behind them.</br>"
								+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + ", fuckable [npc.nipples])]!"
							+ "</p>");
				}
				
			} else { // Expanding fuckable nipples:
				if (owner.isPlayer()) {
					return "<p>"
								+ "You let out [pc.a_moan+] as you feel a familiar pressure building up behind your fuckable [pc.nipples], before they suddenly [style.boldGrow(grow)] both wider and deeper.</br>"
								+ "You now have [style.boldSex(" + capacityDescriptor + " [pc.nipples])]!"
							+ "</p>";
				} else {
					return UtilText.parse(owner,
							"<p>"
								+ "[npc.Name] lets out [npc.a_moan+] as [npc.she] feels a familiar pressure building up behind [npc.her] fuckable [npc.nipples], before they suddenly [style.boldGrow(grow)] both wider and deeper.</br>"
								+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + " [npc.nipples])]!"
							+ "</p>");
				}
			}
			
		} else {
			if(capacity == 0) { // Losing fuckable nipples:
				if (owner.isPlayer()) {
					return "<p>"
								+ "You squirm about uncomfortably as your [pc.nipples] grow unusually hard and sensitive."
								+ " A strange pressure starts to build up deep within your torso, and you let out a groan as you feel a drastic transformation taking place deep within your [pc.breasts]."
								+ " Your groan turns into a little sigh as you feel your [pc.nipples] suddenly [style.boldShrink(clench shut)], removing the ability for them to be fucked.</br>"
								+ "You now have [style.boldSex(" + capacityDescriptor + ", non-fuckable [pc.nipples])]!"
							+ "</p>";
				} else {
					return UtilText.parse(owner,
							"<p>" 
								+ "[npc.Name] squirms about uncomfortably as [npc.her] [npc.nipples] grow unusually hard and sensitive."
								+ " A strange pressure starts to build up deep within [npc.her] torso, and [npc.she] lets out a groan as a drastic transformation takes place deep within [npc.her] [npc.breasts]."
								+ " [npc.Her] groan turns into a little sigh as [npc.her] [npc.nipples] suddenly [style.boldShrink(clench shut)], removing the ability for them to be fucked.</br>"
								+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + ", non-fuckable [npc.nipples])]!"
							+ "</p>");
				}
				
			} else { // Shrinking fuckable nipples:
				if (owner.isPlayer()) {
					return "<p>"
								+ "You let out [pc.a_moan+] as you feel a familiar pressure building up behind your fuckable [pc.nipples], before they suddenly [style.boldShrink(shrink)] and become tighter.</br>"
								+ "You now have [style.boldSex(" + capacityDescriptor + " [pc.nipples])]!"
							+ "</p>";
				} else {
					return UtilText.parse(owner,
							"<p>"
								+ "[npc.Name] lets out [npc.a_moan+] as [npc.she] feels a familiar pressure building up behind [npc.her] fuckable [npc.nipples], before they suddenly [style.boldShrink(shrink)] and become tighter.</br>"
								+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + " [npc.nipples])]!"
							+ "</p>");
				}
			}
		}
	}
	
	@Override
	public float getStretchedCapacity() {
		return stretchedCapacity;
	}

	@Override
	public boolean setStretchedCapacity(float stretchedCapacity) {
		if (stretchedCapacity <= 0) {
			if (this.stretchedCapacity != 0) {
				this.stretchedCapacity = 0;
				return true;
			}
		} else if (stretchedCapacity >= Capacity.SEVEN_GAPING.getMaximumValue()) {
			if (this.stretchedCapacity != Capacity.SEVEN_GAPING.getMaximumValue()) {
				this.stretchedCapacity = Capacity.SEVEN_GAPING.getMaximumValue();
				return true;
			}
		} else {
			if (this.stretchedCapacity != stretchedCapacity) {
				this.stretchedCapacity = stretchedCapacity;
				return true;
			}
		}
		return false;
	}

	@Override
	public OrificeElasticity getElasticity() {
		return OrificeElasticity.getElasticityFromInt(elasticity);
	}

	@Override
	public String setElasticity(GameCharacter owner, int elasticity) {
		float elasticityChange = 0;
		
		if (elasticity <= 0) {
			if (this.elasticity != 0) {
				elasticityChange = 0 - this.elasticity;
				this.elasticity = 0;
			}
		} else if (elasticity >= OrificeElasticity.SEVEN_ELASTIC.getValue()) {
			if (this.elasticity != OrificeElasticity.SEVEN_ELASTIC.getValue()) {
				elasticityChange = OrificeElasticity.SEVEN_ELASTIC.getValue() - this.elasticity;
				this.elasticity = OrificeElasticity.SEVEN_ELASTIC.getValue();
			}
		} else {
			if (this.elasticity != elasticity) {
				elasticityChange = elasticity - this.elasticity;
				this.elasticity = elasticity;
			}
		}
		
		if(elasticityChange == 0) {
			if(owner.isPlayer()) {
				return "<p style='text-align:center;'>[style.colourDisabled(The elasticity of your [pc.nipples] doesn't change...)]</p>";
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The elasticity of [npc.name]'s [npc.nipples] doesn't change...)]</p>");
			}
		}
		
		String elasticityDescriptor = getElasticity().getDescriptor();
		if (elasticityChange > 0) {
			if (owner.isPlayer()) {
				return "<p>"
							+ "You let out a little gasp as you feel a strange slackening sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldGrow(elasticity increases)].</br>"
							+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [pc.nipples])]!"
						+ "</p>";
			} else {
				return UtilText.parse(owner, 
						"<p>"
							+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange slackening sensation pulsating deep within [npc.her] [npc.breasts] as your [npc.nipples]' [style.boldGrow(elasticity increases)].</br>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [npc.nipples])]!"
						+ "</p>");
			}
			
		} else {
			if (owner.isPlayer()) {
				return "<p>"
							+ "You let out a little gasp as you feel a strange clenching sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldShrink(elasticity decreases)].</br>"
							+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [pc.nipples])]!"
						+ "</p>";
			} else {
				return UtilText.parse(owner, 
						"<p>"
							+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange clenching sensation pulsating deep within [npc.her] [npc.breasts] as your [npc.nipples]' [style.boldShrink(elasticity decreases)].</br>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [npc.nipples])]!"
						+ "</p>");
			}
		}
	}
	
	@Override
	public OrificePlasticity getPlasticity() {
		return OrificePlasticity.getElasticityFromInt(plasticity);
	}

	@Override
	public String setPlasticity(GameCharacter owner, int plasticity) {
		float plasticityChange = 0;
		
		if (plasticity <= 0) {
			if (this.plasticity != 0) {
				plasticityChange = 0 - this.plasticity;
				this.plasticity = 0;
			}
		} else if (plasticity >= OrificePlasticity.SEVEN_MOULDABLE.getValue()) {
			if (this.plasticity != OrificePlasticity.SEVEN_MOULDABLE.getValue()) {
				plasticityChange = OrificePlasticity.SEVEN_MOULDABLE.getValue() - this.plasticity;
				this.plasticity = OrificePlasticity.SEVEN_MOULDABLE.getValue();
			}
		} else {
			if (this.plasticity != plasticity) {
				plasticityChange = plasticity - this.plasticity;
				this.plasticity = plasticity;
			}
		}
		
		if(plasticityChange == 0) {
			if(owner.isPlayer()) {
				return "<p style='text-align:center;'>[style.colourDisabled(Your [pc.nipples]'s plasticity doesn't change...)]</p>";
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The plasticity of [npc.name]'s [npc.nipples] doesn't change...)]</p>");
			}
		}
		
		String plasticityDescriptor = getPlasticity().getDescriptor();
		if (plasticityChange > 0) {
			if (owner.isPlayer()) {
				return "<p>"
							+ "You let out a little gasp as you feel a strange moulding sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldGrow(plasticity increases)].</br>"
							+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [pc.nipples])]!"
						+ "</p>";
			} else {
				return UtilText.parse(owner, 
						"<p>"
							+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange moulding sensation pulsating deep within [npc.her] [npc.breasts] as your [npc.nipples]' [style.boldGrow(plasticity increases)].</br>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [npc.nipples])]!"
						+ "</p>");
			}
			
		} else {
			if (owner.isPlayer()) {
				return "<p>"
							+ "You let out a little gasp as you feel a strange softening sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldShrink(plasticity decreases)].</br>"
							+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [pc.nipples])]!"
						+ "</p>";
			} else {
				return UtilText.parse(owner, 
						"<p>"
							+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange softening sensation pulsating deep within [npc.her] [npc.breasts] as your [npc.nipples]' [style.boldShrink(plasticity decreases)].</br>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [npc.nipples])]!"
						+ "</p>");
			}
		}
	}

	@Override
	public boolean isVirgin() {
		return virgin;
	}

	@Override
	public void setVirgin(boolean virgin) {
		this.virgin = virgin;
	}

	@Override
	public boolean hasOrificeModifier(OrificeModifier modifier) {
		return orificeModifiers.contains(modifier);
	}

	@Override
	public String addOrificeModifier(GameCharacter owner, OrificeModifier modifier) {
		if(hasOrificeModifier(modifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		orificeModifiers.add(modifier);
		
		switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within your [pc.breasts], and you can't help but let out [pc.a_moan+] as you feel a series of [style.boldGrow(extra muscles)] growing down into the lining of your [pc.nipples]."
								+ " You're shocked to discover that you have an incredible amount of control over them, allowing you to expertly grip and squeeze down on any penetrating object.</br>"
								+ "[style.boldSex(The interior of your [pc.nipples] are now lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within [npc.name]'s [npc.breasts], and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(extra muscles)]"
									+ " growing down into the lining of [npc.her] [npc.nipples]."
								+ " [npc.She]'s shocked to discover that [npc.she] has an incredible amount of control over them, allowing [npc.her] to expertly grip and squeeze down on any penetrating object.</br>"
								+ "[style.boldSex(The interior of [npc.name]'s [npc.nipples] are now lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within your [pc.breasts], and you can't help but let out [pc.a_moan+] as you feel a series of [style.boldGrow(fleshy, highly-sensitive ribs)]"
									+ " growing down into the lining of your [pc.nipples]."
								+ " Shifting your [pc.breasts] around a little, a jolt of pleasure shoots through your torso as you feel your new additions rub over one another, making you wonder how good it would feel to have your [pc.nipples] fucked.</br>"
								+ "[style.boldSex(The interior of your [pc.nipples] are now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within [npc.name]'s [npc.breasts], and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(fleshy, highly-sensitive ribs)]"
									+ " growing down into the lining of [npc.her] [npc.nipples]."
								+ " Shifting [npc.her] [npc.breasts] around a little, a jolt of pleasure shoots through [npc.her] torso as [npc.she] feels [npc.her] new additions rub over one another, causing [npc.herHim] to let out another [npc.moan+].</br>"
								+ "[style.boldSex(The interior of [npc.name]'s [npc.nipples] are now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within your [pc.breasts], and you can't help but let out [pc.a_moan+] as you feel a strange tingling sensation deep down in your [pc.nipples]."
								+ " The tingling sensation grows stronger, and a surprised cry bursts out from your mouth as you suddenly discover that the insides of your [pc.nipples] are now filled with"
									+ " [style.boldGrow(a series of little wriggling tentacles)], over which you have limited control.</br>"
								+ "[style.boldSex(The insides of your [pc.nipples] are now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within [npc.name]'s [npc.breasts], and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a strange tingling sensation deep down in [npc.her] [npc.nipples]."
								+ " The tingling sensation grows stronger, and a surprised cry bursts out from [npc.her] mouth as [npc.she] suddenly discovers that the insides of [npc.her] [npc.nipples] are now filled with"
										+ " [style.boldGrow(a series of little wriggling tentacles)], over which [npc.she] has limited control.</br>"
								+ "[style.boldSex(The interior of [npc.name]'s [npc.nipples] are now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your [pc.nipples], and you let out a little cry as you feel them swell out and [style.boldGrow(puff up)].</br>"
								+ "[style.boldTfSex(Your [pc.nipples] are now extremely puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.nipples], before they suddenly swell out and [style.boldGrow(puff up)].</br>"
								+ "[style.boldSex([npc.Name]'s [npc.nipples] are now extremely puffy!)]"
							+ "</p>";
				}
		}
		
		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}

	@Override
	public String removeOrificeModifier(GameCharacter owner, OrificeModifier modifier) {
		if(!hasOrificeModifier(modifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		orificeModifiers.remove(modifier);
		
		switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
								+ "A soothing warmth slowly washes up through your torso, and you can't help but let out a gentle [pc.moan] as you feel your [style.boldShrink(extra muscles)] melt back into the flesh of your [pc.breasts].</br>"
								+ "[style.boldSex(The interior of your [pc.nipples] are no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "A soothing warmth slowly washes up through [npc.name]'s torso, and a gentle [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] feels [npc.her] [style.boldShrink(extra muscles)]"
									+ " melt back into the flesh of [npc.her] [npc.breasts].</br>"
								+ "[style.boldSex(The interior of [npc.name]'s [npc.nipples] are no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "A soothing warmth slowly washes up through your torso, and you can't help but let out a gentle [pc.moan] as you feel your [style.boldShrink(fleshy, highly-sensitive ribs)] melt back into the flesh of your [pc.breasts].</br>"
								+ "[style.boldSex(The interior of your [pc.nipples] are no longer ribbed!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "A soothing warmth slowly washes up through [npc.name]'s torso, and a gentle [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] feels [npc.her] [style.boldShrink(fleshy, highly-sensitive ribs)]"
									+ " melt back into the flesh of [npc.her] [npc.breasts].</br>"
								+ "[style.boldSex(The interior of [npc.name]'s [npc.nipples] are no longer ribbed!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "A soothing warmth slowly washes up through your torso, and you can't help but let out a gentle [pc.moan] as you feel your [style.boldShrink(little wriggling tentacles)] melt back into the flesh of your [pc.breasts].</br>"
								+ "[style.boldSex(The interior of your [pc.nipples] are no longer filled with little tentacles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "A soothing warmth slowly washes up through [npc.name]'s torso, and a gentle [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] feels [npc.her] [style.boldShrink(little wriggling tentacles)]"
									+ " melt back into the flesh of [npc.her] [npc.breasts].</br>"
								+ "[style.boldSex(The interior of [npc.name]'s [npc.nipples] are no longer filled with little tentacles!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your [pc.nipples], and you let out a little sigh as you feel them [style.boldShrink(shrink down)], losing their puffyness.</br>"
								+ "[style.boldTfSex(Your [pc.nipples] are no longer extremely puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little sigh as [npc.her] [npc.nipples] [style.boldShrink(shrink down)] and lose their puffyness.</br>"
								+ "[style.boldSex([npc.Name]'s [npc.nipples] are no longer extremely puffy!)]"
							+ "</p>";
				}
		}
		
		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}

	public Set<OrificeModifier> getOrificeModifiers() {
		return orificeModifiers;
	}

}
