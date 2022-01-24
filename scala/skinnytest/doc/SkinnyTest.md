# SkinnyでPrototypeを作るためのメモ

### Controllers
* scaffoldで作成されるcontrollersパッケージがskinnyの中のパッケージと被るのでリファクタしないとIDEAでうまくimportできない
* webページありならApplicationController,RESTならApiControllerをextendsする
* 自動生成されたソースがドキュメントと異なるのでわかりにくい。いや日本語情報を求めると作者の古いblogを見てしまうだけかも

### Resoures

* scaffoldで作るクラスにはid,createdAt,updatedAtが自動で入る
* scaffoldで失敗すると手で消したりして結構めんどくさい
* createWithParametersはimplicitにDBセッションをとって実際にDBに登録してIDを返す



## Scala(Skinny) vs Kotlin(SparkFW/Exposed)

### Scalaのメリット
* ServerSideの情報がいっぱいある（英語が多いけど)　
* 負荷テスト(Gatling)やブラウザ自動テスト(ScalaTest using Selenium)と言語を揃えられる
* Skinny scaffoldがCRUDのviewまで全部用意してくれるのでとりあえず管理画面はすぐ作れる
* Skinnyの日本語情報が多い

### Kotlinのメリット
* スマホアプリ隆盛だし今後Kotlinのシェアは伸びそう
* ビルドツールの標準がgradle
* Spark Framework/Exposedだとどっちもあまりメジャーじゃないけど、小さくて把握しやすそう

## 感想

* そこはかとないSeasar感というかオレオレ感
* 個人的見解ではリーダーの人はSeasarの時よりもかなり優秀
* リーダー以外の開発者が見えてこなくてトラックリスク不安
* 継承前提とか微妙に古臭いというか密結合が多くて改善案を考えるにしても影響範囲が大きそう
