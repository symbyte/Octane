package edu.uco.schambers4.octane.Models.MealPlanner;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steven Chambers on 10/29/2015.
 */
public class Recipe implements IIngredient
{
    private String name;
    private Map<IIngredient, Double> ingredientAndAmountMap;

    public Recipe(String name)
    {
        this.name = name;
        ingredientAndAmountMap = new HashMap<>();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public int getCalories()
    {
        int calorieSum = 0;
        for (Map.Entry<IIngredient, Double> cursor : ingredientAndAmountMap.entrySet())
        {
            IIngredient ingredient = cursor.getKey();
            calorieSum += ingredientAndAmountMap.get(ingredient) * ingredient.getCalories();
        }
        return calorieSum;
    }

    public Map<IIngredient, Double> getIngredientQuantityMap()
    {
        return ingredientAndAmountMap;
    }

    public void addIngredient(IIngredient ingredient, double quantity)
    {
        ingredientAndAmountMap.put(ingredient, quantity);
    }
    public void removeIngredient(IIngredient ingredient)
    {
        ingredientAndAmountMap.remove(ingredient);
    }

    public List<String> getIngredientNames()
    {
       return Stream.of(ingredientAndAmountMap.keySet())
               .map(ingredient -> ingredient.getName() ).distinct().collect(Collectors.toList());
    }

    public Map<IIngredient, Double> getAllIngredientsAndQuantity(Double quantityModifier)
    {
        if(quantityModifier == null)
            quantityModifier = 1.0;

        Map<IIngredient,Double> toReturn = new HashMap<>();
        for(Map.Entry<IIngredient,Double> entry : ingredientAndAmountMap.entrySet())
        {
            if(entry.getKey() instanceof Ingredient)
            {
                toReturn.put(entry.getKey(), entry.getValue() * quantityModifier);
            }
            else
            {
                Recipe recipe = (Recipe) entry.getKey();
                toReturn.putAll(recipe.getAllIngredientsAndQuantity(entry.getValue()));
            }
        }
        return toReturn;
    }

    @Override
    public String getUnitOfMeasure()
    {
        return "Preparation";
    }

    @Override
    public String toString()
    {
        return String.format("%s, %d calories", name,getCalories());
    }
}
