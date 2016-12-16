# NiagaraKayak

# Pattern that should be used to implement this app, NO SPAGHETTI CODE, PLEASE, IT WILL JUST HURT US LATER

The pattern we should try to employ to keep us sane while building this is MVP (Model, View, Presenter)

If you aren't familiar with this pattern, check out some code samples here

https://github.com/googlesamples/android-architecture#stable-samples

# API details

Simple explanation:

The reservations are simply stored as a "post" in WordPress. It's entirely textual.
This kinda sucks, but it will work fine.

It has some basic authentication for stopping randoms from creating posts, in the form of a nonce key.

The nonce key is the strings.xml file in the res/ directory. 

You shouldn't need it though..

In app/src/main there is a file called the ApiUrlHelper.

That class is for generating GET/POST urls to create/read/update/delete reservations.

The idea is very simple, we generate these urls based on what we are trying to accomplish, fire off requests, get back JSON, and work with it.

## TODO Update with various request responses from API.
