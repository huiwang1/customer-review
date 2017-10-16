package com.hui.hdtest.customerreview.services.impl;

import com.hui.hdtest.customerreview.services.CustomerReviewExtendedService;
import de.hybris.platform.customerreview.impl.DefaultCustomerReviewService;
import main.java.com.hui.hdtest.customerreview.model.Rating;
import org.springframework.beans.factory.annotation.Required;

/***
 * Implementation of {@link CustomerReviewExtendedService}.
 */
public class HuiCustomerReviewService extends DefaultCustomerReviewService implements CustomerReviewExtendedService {
    private String customerReviewBadWords;

    @Required
    public void setCustomerReviewBadWords(final String customerReviewBadWords) {
        this.customerReviewBadWords = customerReviewBadWords;
    }

    /***
     * Check if the comment param contains any bad words
     * @param comment
     * @return true if there is bad words, otherwise false
     */
    protected boolean isCommentClean(final String comment) {
        String badWordRegex = this.customerReviewBadWords.replace(",", "|");
        return !comment.matches(badWordRegex);
    }

    @Override
    public CustomerReviewModel createCustomerReview(final Double rating, final String headline, final String comment, final UserModel user, final ProductModel product) {
        Rating customerRating = new Rating(rating);

        if (customerRating.isRatingNegative()) {
            throw new IllegalArgumentException("Rating is negative: " + customerRating.getRating());
        }

        if (!isCommentClean(comment)) {
            throw new IllegalArgumentException("Comment contains bad words.");
        }

        return super.createCustomerReview(customerRating.getRating(), headline, comment, user, product);
    }

    @Override
    public int getReviewCountForProductWithinRating(ProductModel paramProductModel, Double lowestRating, Double highestRating) {
        ServicesUtil.validateParameterNotNullStandardMessage("product", product);
        return getCustomerReviewDao().getReviewCountForProductWithinRating(product, new Rating(lowestRating), new Rating(highestRating));
    }

}
