# 仕様変更

1. プロジェクト全体をmvp仕様に書き直しました。
  - Model -> Repository (implements DataSource)
  - View -> TimerFragment (implements TimerContract.View)
  - Presenter -> TimerPresenter (implements TimerContract.Presenter)
  - TimerActivityはその全体の管理的な意味合いを持つ

<参考>
- [android-architecture (Branch:todo-mvp-kotlin)](https://github.com/googlesamples/android-architecture/tree/todo-mvp-kotlin/)
- [Android Architecture Blueprintsで学んだアプリ実装(MVP)](https://qiita.com/HunterPigeon52/items/427417785563480cbc83)

2. 現存のアプリだとModelが薄くなりそうだったので、SharedPreferencesを使ってアプリ終了時のタイムとタイムラップを保存するようにしました。
