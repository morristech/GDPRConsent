# GDPR Consent

https://streamable.com/b5odn

## Goals

* Providing a granular consent system. Each individual system should be disable-able.
* Consent items must be able to be marked as required, in the case where your App couldn't function. For exampple, navigation software that needs your location.
* Making it easy to see what a specific consent entails, what is stored, why it is stored.
* Details of consent are immutable. Once it has been saved once, it remains. The developer cannot change the language of consent without creating a new consent, that in turn must be consented to.
* New consent can be added at any time after release of the App. New items will be presented to the user the next time that they run the App after updating.
* User can revoke consent as easy as they gave it.
* Providing a neutral interface. It is designed with Material "2" in mind. Primarily white, colours used to indicate importance.
* Providing an external link to more information if the user needs it.

## Known issues

* Not built specifically for different display types. It works on tablets, certainly. However, it could be optimized.

## Doesn't attempt to solve:

* Automatically blocking access to services and APIs until consent is given. This must be done manually still. I am using a helper class to get the consent state of a key to determine whether a service is allowed to function.
* Correcting personal data. This should be provided by the App itself.
* Deleting personal data. This should be provided by the App itself.