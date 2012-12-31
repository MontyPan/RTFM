> # ZKPushState：用純 Java 處理 History API 的 ZK addon #

簡介
----
HTML5 的規格中引入了新的 `History` API：

* `history` 可以呼叫 `pushState()` 把一些資料（稱為 state）設定進目前的 session（單純的名詞，不是 HTTP 的那個 session），
	並且可以更改目前的 URL（如果有給值的話）
* 當 `history` 有變動，browser 會發出一個 `onpopstate` event，在 `window.onpopstate` 可以接收到。

在 Ashish 的文章[〈History Management with HTML5 History API in ZK〉][Ashish blog] 中，
開發人員可以瞭解如何用 JavaScript 在 ZK 當中使用這個 API。
不過那太不「Java」了，所以我寫了一個叫 [ZKPushState] 的 ZK addon，
讓開發人員可以用更 ZK 的方式處理 browser 的 history，並且簡化 state 的 encode/decode 步驟。

所以，怎麼用呢？
--------------
在開始寫 code 之前，只有一件事情要作，就是下載 [ZKPS.jar]，然後放到 classpath 底下。
你就可以在 Java code 當中用 `PushState.push(Map, String, String)`，
然後在 ZUL 的 root element 掛上 `onPopupState`。

看不懂嗎？那看接下來實際範例會更清楚一點......

實際範例
--------
我還是沿用 [Ashish 文章][Ashish blog]當中的範例。
在這個範例當中，有三個 textbox 跟 button，在 textbox 輸入條件後按下 button，
底下的 listbox 就會顯示 filter 過後的資料。

我用 ZKPushState 跟 MVVM 改寫，首先 button 要改成這樣

	<button label="Filter" width="50px"
	 onClick='@command("filter", f1=filter1.value, f2=filter2.value, f3=filter3.value)' />

在 ViewModel 當中對應的 command method：

	@Command
	@NotifyChange("*")
	public void filter(@BindingParam("f1") String f1, @BindingParam("f2") String f2, @BindingParam("f3") String f3){
		doFilter(f1, f2, f3);
		Map<String, String> map = new HashMap<String, String>();
		map.put("f1", f1);
		map.put("f2", f2);
		map.put("f3", f3);
		PushState.push(map, "Search results", "?q="+f1+f2+f3);
	}

`doFilter()` 這個 method 會去重新建立 listbox 的 model，然後在 `filter()` 的最後
我呼叫 `PushState.push()`，它會自動轉換成 clinet（browser）的 `history.pushState()`，
end-user 就會發現 URL 改變、browser 的「回上一頁」也可以按了。

在幾次的 search 之後，browser 會儲存許多剛剛透過 `map` 塞進去的 state。
當 end-user 按「回上一頁」時，ZKPushState 會觸發一個 `PopupStateEvent`，
我必須在 ZUL 當中掛上 `onPopupState` 來接收這個 event。
所以 ZUL 的 root element 就改成這樣：

	<window onPopupState='@command("popupState", event=event)'>

在 ViewModel 當中對應的 command method 寫成這樣：

	@Command
	@NotifyChange("*")
	public void popupState(@BindingParam("event") PopupStateEvent event){
		Map<String, ?> state = event.getState();
		doFilter(
			state.get("f1").toString(),
			state.get("f2").toString(),
			state.get("f3").toString()
		);
	}

從傳入的 event 當中可以用 `event.getState()` 取得當初傳入的 `Map<String, ?>`。
所以用裡頭的值重新呼叫一次 `doFilter()`，包含 listbox 跟 textbox 都會還原成當初的樣子。

在 [Github][ZKPushState repo] 可以獲得這個範例、以及 ZKPushState 的完整原始碼。
如果有什麼意見也都非常歡迎 \囧/

[Ashish blog]: http://blog.zkoss.org/index.php/2012/03/30/history-management-with-html5-history-api-in-zk/
[ZKPS.jar]: http://zkpushstate.googlecode.com/files/ZKPS.jar
[ZKPushState repo]: https://github.com/MontyPan/ZKPushState
