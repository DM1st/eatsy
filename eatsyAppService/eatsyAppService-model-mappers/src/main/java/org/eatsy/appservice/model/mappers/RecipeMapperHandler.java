package org.eatsy.appservice.model.mappers;

import org.eatsy.appservice.domain.Recipe;
import org.eatsy.appservice.model.RecipeModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Recipe Mapper Handler to map between recipe domain and model objects.
 */
@Component
public class RecipeMapperHandler implements RecipeMapper {

    /**
     * Map the recipe domain object to a recipe model object.
     *
     * @param recipe the domain object to be mapped
     * @return the recipeModel object that has been created from the recipe domain object.
     */
    @Override
    public RecipeModel mapToModel(Recipe recipe) {

        RecipeModel recipeModel = new RecipeModel();

        //Map name.
        recipeModel.setName(recipe.getName());

        //Map set of ingredients.
        recipeModel.setIngredientSet(new HashSet<>());
        for (final String ingredient : recipe.getIngredientSet()) {

            recipeModel.getIngredientSet().add(ingredient);

        }

        //Map method.
        recipeModel.setMethod(new HashMap<>());
        recipe.getMethod().forEach((k, v) ->
                recipeModel.getMethod().put(k, v)
        );


        return recipeModel;

    }
}
