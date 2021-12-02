package org.eatsy.appservice.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;
import java.util.Set;

/**
 * The domain object for a Recipe to hold its information such as method, ingredients and name.
 */
public final class Recipe {

    //Recipe name.
    private final String name;

    //The list of ingredients for the recipe
    private Set<String> ingredientSet;

    //The method for creating the recipe from the ingredients
    private Map<Integer, String> method;

    //Constructor
    public Recipe(String name, Set<String> ingredientSet, Map<Integer, String> method) {
        this.name = name;
        this.ingredientSet = ingredientSet;
        this.method = method;
    }

    //Getters
    public String getName() {
        return name;
    }

    public Set<String> getIngredientSet() {
        return ingredientSet;
    }

    public Map<Integer, String> getMethod() {
        return method;
    }

    //Equals, HashCode toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        return new EqualsBuilder().append(name, recipe.name).append(ingredientSet, recipe.ingredientSet).append(method, recipe.method).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(ingredientSet).append(method).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("ingredientSet", ingredientSet)
                .append("method", method)
                .toString();
    }
}
