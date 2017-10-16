package com.hui.hdtest.customerreview.services;

import  de.hybris.platform.customerreview.CustomerReviewService;

/***
 * Extended {@link CustomerReviewService}
 */
public interface CustomerReviewExtendedService extends CustomerReviewService {

    /***
     * see {@link com.hui.hdtest.customerreview.dao.CustomerReviewExtendedDao#getReviewCountForProductWithinRating}.
     * @param paramProductModel
     * @param lowestRating
     * @param highestRating
     * @return
     */
    int getReviewCountForProductWithinRating(ProductModel paramProductModel, Double lowestRating, Double highestRating);
}
