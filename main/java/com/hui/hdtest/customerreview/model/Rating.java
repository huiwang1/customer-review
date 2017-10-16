package main.java.com.hui.hdtest.customerreview.model;

import de.hybris.platform.customerreview.constants.CustomerReviewConstants;

/***
 * Rating domain object.
 */
public class Rating {
    private Double rating;

    public Rating() {
        rating = 0d;
    }

    public Rating(final Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public boolean isRatingOk() {
        return this.rating.compareTo(CustomerReviewConstants.getInstance().MINRATING) >= 0
                && this.rating.compareTo(CustomerReviewConstants.getInstance().MAXRATING) <= 0;
    }

    public boolean isRatingNegative() {
        return this.rating.compareTo(0D) < 0;
    }

    public int compareTo(final Rating otherRating) {
        return this.rating.compareTo(otherRating.getRating());
    }
}
