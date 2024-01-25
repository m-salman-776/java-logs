package LLD.CoupanManagement.Classes;

import org.joda.time.DateTime;

public class ApplicabilityRules {
    int id;
    int couponId;
    double minOrderAmount;

    int usageLimit;
    int useCount;

    boolean stackable;

    int applicableGroupId;

    DateTime validityStart;
    DateTime validityEnd;

}
