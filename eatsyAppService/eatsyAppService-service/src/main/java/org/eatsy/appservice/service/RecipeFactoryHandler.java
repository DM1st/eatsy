package org.eatsy.appservice.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eatsy.appservice.domain.Recipe;
import org.eatsy.appservice.model.RecipeModel;
import org.eatsy.appservice.model.mappers.RecipeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Recipe Factory implementation
 * Tagged with @Component for dependency injection
 */
@Component
public class RecipeFactoryHandler implements RecipeFactory {

    //logger
    private static final Logger logger = LogManager.getLogger();

    //Cache of recipes
    private final Map<String, Recipe> recipeCache = new LinkedHashMap<>();

    //Recipe Mapper implementation
    private final RecipeMapper recipeMapperHandler;

    //Inject the dependency of the recipeMapper implementation into the RecipeFactoryHandler during instantiation.
    public RecipeFactoryHandler(final RecipeMapper recipeMapperHandler) {
        this.recipeMapperHandler = recipeMapperHandler;
    }

    /**
     * Creates a new recipe model object
     *
     * @param recipeModel the recipe model that will be used to create a recipe domain object
     * @return a recipe model object containing the data from the newly created recipe domain object
     */
    @Override
    public RecipeModel createRecipe(final RecipeModel recipeModel) {

        RecipeModel newRecipeModel = null;

        //The recipeModel to create a Recipe object must not be null and the recipeModel must have a recipeName.
        if (null != recipeModel && StringUtils.isNotEmpty(recipeModel.getName().trim())) {

            logger.debug("Creating a new recipe domain object called " + recipeModel.getName());

            final Recipe recipe = recipeMapperHandler.mapToDomain(recipeModel);
            //Add the new recipe to the cache of recipes
            recipeCache.put(recipe.getKey(), recipe);

            newRecipeModel = recipeMapperHandler.mapToModel(recipe);

        }
        return newRecipeModel;
    }

    /**
     * Retrieves all recipe model objects.
     *
     * @return The list of all recipe model objects that exist.
     */
    @Override
    public List<RecipeModel> retrieveAllRecipes() {

        logger.debug("Retrieving all recipes to return to the controller");

        //map the updated recipeCache to a recipeModel list to be returned.
        final List<RecipeModel> allRecipesModel = getAllRecipeModels();

        return allRecipesModel;

    }

    /**
     * Deletes the requested recipeModel
     *
     * @param recipeKey of the recipe model that will be deleted from the recipe book
     * @return the list of existing recipe models that will have been updated to remove recipeModelToDelete
     */
    @Override
    public List<RecipeModel> deleteRecipe(final String recipeKey) {

        logger.debug("deleting recipe with key : " + recipeKey);

        //Remove the recipe for deletion from the recipe cache.
        recipeCache.remove(recipeKey);

        //map the updated recipeCache to a recipeModel list to be returned.
        final List<RecipeModel> allRecipesModel = getAllRecipeModels();

        return allRecipesModel;
    }

    /**
     * Replaces the existing recipe with the updated version supplied.
     *
     * @param recipeKey              the unique ID of the recipe. This will allow the recipe that needs to be
     *                               updated to be identified.
     * @param recipeModelWithUpdates the recipe model with the updated changes to be persisted.
     * @return the updated recipeModel with the new updates/changes applied.
     */
    @Override
    public RecipeModel updateRecipe(final String recipeKey, final RecipeModel recipeModelWithUpdates) {

        logger.debug("replacing recipe with key: " + recipeKey + " for the new updated version");

        //Create the updated Recipe domain object
        final Recipe updatedRecipe = recipeMapperHandler.mapToDomain(recipeModelWithUpdates);

        //replace the outdated recipe with the updated version in the recipeCache.
        recipeCache.replace(recipeKey, updatedRecipe);
        //The updatedRecipe domain object has a new key generated on creation, so the Maps Key value will need to be updated to correspond.
        recipeCache.put(updatedRecipe.getKey(), recipeCache.remove(recipeKey));

        //Map the updated recipe to a RecipeModel and return.
        final RecipeModel updatedRecipeModel = recipeMapperHandler.mapToModel(updatedRecipe);
        return updatedRecipeModel;
    }

    /**
     * Map the updated recipeCache to a recipeModel list to be returned.
     *
     * @return all recipe models.
     */
    private List<RecipeModel> getAllRecipeModels() {
        final List<RecipeModel> allRecipesModel = new ArrayList();
        recipeCache.forEach((key, value) -> {
                    allRecipesModel.add(recipeMapperHandler.mapToModel(value));
                }
        );
        return allRecipesModel;
    }
}
