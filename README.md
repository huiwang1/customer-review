# customer-review

## Thought process

---

### Investigation

I spent about 2 hours reviewing the provided ```customerreviewserver.jar.src``` to understand the various components and how they relate to each other.  I also read up on hybris and jalo to get some background on the framework.

From my preliminary investigation, I observed the main entry point is provided by the ```DefaultCustomerReviewService``` which implements the interface ```CustomerReviewService```.  The ```DefaultCustomerReviewService``` implements the service layer and calls into the data layer using the ```DefaultCustomerReviewDao```, as well as, using the jalo layer provider ```CustomerReviewManager```.

### Requirements

I spent about 4 hours coding up the solution, and another 2 hours documenting and trying to unit test.

[customerreview-spring.xml]: https://github.com/huiwang1/customer-review/blob/master/customerreview-spring.xml
[HuiCustomerReviewDao]: https://github.com/huiwang1/customer-review/blob/master/main/java/com/hui/hdtest/customerreview/dao/impl/HuiCustomerReviewDao.java
[CustomerReviewExtendedDao]: https://github.com/huiwang1/customer-review/blob/master/main/java/com/hui/hdtest/customerreview/dao/CustomerReviewExtendedDao.java
[Rating]: https://github.com/huiwang1/customer-review/blob/master/main/java/com/hui/hdtest/customerreview/model/Rating.java
[customer-review.properties]: https://github.com/huiwang1/customer-review/blob/master/main/resources/customer-review.properties
[CustomerReviewExtendedService]: https://github.com/huiwang1/customer-review/blob/master/main/java/com/hui/hdtest/customerreview/services/CustomerReviewExtendedService.java
[HuiCustomerReviewService]: https://github.com/huiwang1/customer-review/blob/master/main/java/com/hui/hdtest/customerreview/services/impl/HuiCustomerReviewService.java

1. Provide a way to get a product’s total number of customer reviews whose ratings are within a given range (inclusive).

   For this requirement, I decided to extend the ```DefaultCustomerReviewDao``` (via [HuiCustomerReviewDao]) and the ```CustomerReviewDao``` interface (via [CustomerReviewExtendedDao]).  I opted to go with the DAO as research seems to imply Jalo is the old persistence mechanism and it's better to go with the functionality provided by the newer Service layer.

   I opted to create a [Rating] model object to encapsulate the logic around ratings.  I'm using it to ensure the rating ranges are valid.

   I am a little hesitant around the code to retrieve the result of the ```FlexibleSearchService``` search call. I noticed similar sql query code from ```CustomerReviewManager::getNumberOfReviews()``` and decided to follow suit.

   The new extended class is defined as a bean in the [customerreview-spring.xml] and access to the functionality is provided in the extended [CustomerReviewExtendedService] interface.

1. Add the following additional checks before creating a customer review. If all the rules are passed, go ahead and create the customer review.

   To handle the additional rules to check before creating a customer review, I decided to extend the ```DefaultCustomerReviewService``` (via [HuiCustomerReviewService]) and the ```CustomerReviewService``` interface (via [CustomerReviewExtendedService]).  The service seemed like a better candidate to extend than the Jalo manager classes.

   1. Your service should read a list of curse words. This list should not be defined in Java class.

       I defined the list of curse words in the [customer-review.properties] file and injected it via the bean properties in [customerreview-spring.xml]

   1. Check if Customer’s comment contains any of these curse words. If it does, throw an exception with a message.

      I defined a new helper method that uses regex to detect if the comment string contains any of the predefined curse words.

   1. Check if the rating is not < 0.  If it is < 0, throw an exception with a message.

      I'm using the [Rating] model object to define the logic to check if rating is negative.

   If either of the validation fails then an ```IllegalArgumentException``` is thrown, otherwise customer review is created via the super class method ```DefaultCustomerReviewService::createCustomerReview()```.

### Testing

I tried using Mockito mock and test the new methods. However, ran into too many hurdles trying to fake out the missing objects referenced by the classes in ```customerreviewserver.jar.src```.