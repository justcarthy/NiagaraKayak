# NiagaraKayak

# Pattern that should be used to implement this app, NO SPAGHETTI CODE, PLEASE, IT WILL JUST HURT US LATER

The pattern we should try to employ to keep us sane while building this is MVP (Model, View, Presenter)

If you aren't familiar with this pattern, check out some code samples here

https://github.com/googlesamples/android-architecture#stable-samples

## API details

Simple explanation:

The reservations are simply stored as a "post" in WordPress. It's entirely textual.
This kinda sucks, but it will work fine.

It has some basic authentication for stopping randoms from creating posts, in the form of a nonce key.

The nonce key is the strings.xml file in the res/ directory. 

You shouldn't need it though..

In app/src/main there is a file called the [ApiUrlHelper](https://github.com/justcarthy/NiagaraKayak/blob/master/NiagaraKayakApp/app/src/main/java/com/niagarakayak/niagarakayakapp/Utility/ApiUrlHelper.java).

That class is for generating GET/POST urls to create/read/update/delete reservations.

It should be used in our controllers whenever we want to call the API to get/post/etc.

The idea is very simple, we generate these urls based on what we are trying to accomplish, fire off requests, get back JSON, and work with it.

## Example of API calls and returns

### GET all reservations `http://www.niagarakayak.com/api/get_posts

```json
{
	"status": "ok",
	"count": 0,
	"count_total": 0,
	"pages": 0,
	"posts": [],
	"query": {
		"ignore_sticky_posts": true
	}
}
```

### GET a reservation `http://www.niagarakayak.com/api/get_post/?id=${id}`


```json
{
	"status": "error",
	"error": "Not found."
}
```


### POST a reservation to the database `http://www.niagarakayak.com/api/create_post?nonce=xxx&title=reservation_string`

