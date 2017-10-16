package com.hui.hdtest.customerreview.dao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.dao.CustomerReviewDao;
import main.java.com.hui.hdtest.customerreview.model.Rating;

/***
 * Extended {@link CustomerReviewDao}.
 */
public interface CustomerReviewExtendedDao extends CustomerReviewDao {

    /***
     * Gets total counts of all reviews for a given product that has ratings within the provided range (inclusive).
     * @param product
     * @param lowestRating
     * @param highestRating
     * @return Review count
     */
    Integer getReviewCountForProductWithinRating(ProductModel product, Rating lowestRating, Rating highestRating);

}
