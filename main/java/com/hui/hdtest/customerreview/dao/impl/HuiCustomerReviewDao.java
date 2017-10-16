package com.hui.hdtest.customerreview.dao.impl;

import com.hui.hdtest.customerreview.dao.CustomerReviewExtendedDao;
import de.hybris.platform.customerreview.constants.CustomerReviewConstants;
import de.hybris.platform.customerreview.dao.impl.DefaultCustomerReviewDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import main.java.com.hui.hdtest.customerreview.model.Rating;

import java.util.Collections;

/***
 * Implementation of {@link CustomerReviewExtendedDao}.
 */
public class HuiCustomerReviewDao extends DefaultCustomerReviewDao implements CustomerReviewExtendedDao {
    @Override
    public Integer getReviewCountForProductWithinRating(final ProductModel product, final Rating lowestRating, final Rating highestRating) {
        if (!validateLowestandHighestRating(lowestRating, highestRating)) {
            return 0;
        }

        String query = "SELECT COUNT(*) FROM {" + GeneratedCustomerReviewConstants.TC.CUSTOMERREVIEW + "} WHERE {" + "product" + "}=?product AND {" + "rating" + "}>=?lowestRating AND {" + "rating" + "}<=?highestRating";

        FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
        fsQuery.addQueryParameter("product", product);
        fsQuery.addQueryParameter("lowestRating", lowestRating.getRating());
        fsQuery.addQueryParameter("highestRating", highestRating.getRating());
        fsQuery.setResultClassList(Collections.singletonList(Integer.class));

        SearchResult<Integer> searchResult = getFlexibleSearchService().search(fsQuery);

        if (searchResult == null || searchResult.getCount() <= 0) {
            return 0;
        } else {
            return (Integer) searchResult.getResult().iterator().next();
        }
    }

    /***
     * Verifies lowestRating against highestRating.
     * @param lowestRating
     * @param highestRating
     * @return true if lowestRating <= highestRating, otherwise false
     */
    protected boolean validateLowestandHighestRating(final Rating lowestRating, final Rating highestRating) {
        return lowestRating.isRatingOk()
                && highestRating.isRatingOk()
                && lowestRating.compareTo(highestRating) <= 0;
    }

}
