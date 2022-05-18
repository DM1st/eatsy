package org.eatsy.appservice.service;


import org.eatsy.appservice.model.RecipeModel;

import java.util.List;

/**
 * Interface for interacting with recipes
 */
public interface RecipeFactory {

    /**
     * Retrieves all recipe model objects.
     * @return The list of all recipe model objects that exist.
     */
    List<RecipeModel> retrieveAllRecipes();

    /**
     * Creates a new recipe model object
     *
     * @param recipeModel the recipe model that will be used to create a recipe domain object
     * @return a recipe model object containing the data from the newly created recipe domain object
     */
    RecipeModel createRecipe(RecipeModel recipeModel);

}
