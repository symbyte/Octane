package edu.uco.schambers4.octane.DataAccessObjects.Ingredients;

import android.content.Context;
import android.util.Log;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import edu.uco.schambers4.octane.InternalStorageSerialization.InternalStorage;
import edu.uco.schambers4.octane.Models.MealPlanner.IIngredient;

/**
 * Created by Steven Chambers on 10/24/2015.
 */
public class InternalStorageIngredientRepository implements IngredientRepository
{
    private List<IIngredient> ingredients;
    private Context context;


    public InternalStorageIngredientRepository(Context context)
    {
        this.context = context;
        refreshData();
    }

    @Override
    public void addIngredientToCollection(IIngredient ingredient)
    {
        ingredients.add(ingredient);
    }

    @Override
    public void removeIngredientFromCollection(IIngredient ingredient)
    {
        ingredients.remove(ingredient);
    }

    @Override
    public List<IIngredient> getCollectionAsList()
    {
        return ingredients;
    }

    @Override
    public Stream<IIngredient> getCollectionAsStream()
    {
        return Stream.of(ingredients);
    }

    @Override
    public void saveChanges()
    {
        try
        {
            InternalStorage.writeObject(context,InternalStorage.STORAGE_KEY_INGREDIENTS, ingredients);
        }catch (Exception e)
        {
            Log.d("IngredientRepository", String.format("Unable to write ingredient list to disk. \n Exception: \n %s",e.toString()));
        }
    }

    @Override
    public void refreshData()
    {
        try
        {
            ingredients = (ArrayList<IIngredient>)InternalStorage.readObject(context, InternalStorage.STORAGE_KEY_INGREDIENTS);
        }catch (Exception e)
        {
            Log.d("IngredientRepository", "Ingredient list not found on disk. Replacing with empty list");
            ingredients = new ArrayList<>();
        }
    }
}
