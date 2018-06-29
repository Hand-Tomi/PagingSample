# Android Architecture Paging Sample

[Paging library](https://developer.android.com/topic/libraries/architecture/paging/)を使ったPodcast検索Application

## Components Used
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Paging](https://developer.android.com/topic/libraries/architecture/paging/)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [DataBinding](https://developer.android.com/topic/libraries/data-binding/)
* [Retrofit2](http://square.github.io/retrofit/)
* [RxKotlin](https://github.com/ReactiveX/RxKotlin)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Glide](https://github.com/bumptech/glide)


## Build方法
* [ListenNotes](https://market.mashape.com/listennotes/listennotes)で「Key」の発給を受けます。
* 「res/value/string.xml」で登録します。
```xml
<string name="key_listen_notes_api">xxxx</string>
```
* buildします。
