package me.chickenstyle.backpack;

import org.bukkit.inventory.ShapedRecipe;


public class Backpack {
	
	private int id;
	private String name;
	private String texture;
	private int slotsAmount;
	private RejectItems reject;
	private ShapedRecipe recipe;
	
	public Backpack(int id,String name,String texture,int slotsAmount,RejectItems reject,ShapedRecipe recipe) {
		this.name = name;
		this.slotsAmount = slotsAmount;
		this.recipe = recipe;
		this.texture = texture;
		this.id = id;
		this.setReject(reject);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSlotsAmount() {
		return slotsAmount;
	}

	public void setSlotsAmount(int slotsAmount) {
		this.slotsAmount = slotsAmount;
	}

	public ShapedRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(ShapedRecipe recipe) {
		this.recipe = recipe;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	@Override
	public String toString() {
		return id + ":" + slotsAmount + ":" + recipe.toString() + ":" + texture ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RejectItems getReject() {
		return reject;
	}

	public void setReject(RejectItems reject) {
		this.reject = reject;
	}
}
