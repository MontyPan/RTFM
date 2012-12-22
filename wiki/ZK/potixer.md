> 這是針對__開發 ZK 的人__的相關 tip。  
> （aka: 正常人不需要知道這些 XD）


JavaScript
==========

### 純 JavaScript ###
* 要用 `var array = []`，不要用 `var array = new Array()`。原因是效率比較快。
* 盡量用 

		`array.push(p1, p2, p3);`

	不要用
	
		array.push(p1);
		array.push(p2);
		array.push(p3);

	也是效率問題。

* 在 OO 中，因為取得 `this.foo` 需要成本，所以如果要多次叫用 `this.foo` 那就

	var foo = this.foo;
	foo.method();
	foo.method();
	foo.method();
	
* 盡量少用 Document.getElementById()，在 IE 上效能不好（IE 去死吧！）

* 要帶參數 fuction 設定方式... WTF... （其實我已經忘記在寫什麼了）

	getPic(v).onmousedown = ( function(v) { return function() { downHandler(v); }; })(v);


### DnD 問題 ###
	<div id="block">
		<div id="title"> blahblah </div>
	</div>
	
如果希望 `#block` 在 `dragenter` 跟 `dragover` 的時候顯示 `#title`、`dragleave` 的時候隱藏。
值覺得寫會發現...... `#title` 會處於 blink 的狀態。
原因理論上是滑鼠 `dragover` 到 `#title` 的時候，瀏覽器會以為離開 `#block` 了，
於是 `#title` 消失之後瀏覽器又讓 `#block` 觸發 `dragover`。如此一來就會變成無窮迴圈...... Orz

目前的解法由 Jumper 大人提供：

	// ZK client, not pure JavaScript
	//jq("#block").bind("dragleave", _hideContent);
	_hideContent : function(evnt) {
		_preventDefault(evnt);
		if (!this._shallContentHide) {
			var self = this;
			setTimeout(
				function () {
					if (self && self._shallContentHide) {
						self._setContentVisible(false);
					}
				}, 
				50
			);
		}
		this._shallContentHide = true;
	},


### 還沒分類 ###
* `zk.log()` 比 `console.log()` 方便一點，因為可以直接在畫面上看到訊息、還可以幫你印出變數內容。
* `console.trace()` 可以印出 call stack。
* 在 ZUL 當中去改值 `zk.afterLoad('zul.inp', function () {zul.inp.validating=true;});`
* `zk.Widget.$()` 可以還原回 ZK widget
* `zk(HTML_ELEMENT)` 等於 `zk("#"+HTML_ELEMENT.id)`，之後可以呼叫 jqzk 的 function。
* `JSON.stringify(foo)` 會幫你把 foo 轉成 json 字串。


ZK
----

### zk.Widget ###
* `onSize()` 是由 ZK 底層呼叫的，所以執行時 `this.desktop()` 一定有值，
  不過如果裡頭包了 `setTimeout()` 就不能保證 timeout 之後 `this.desktop()` 還會有值，
  因為有可能該 widget 剛好 detach 了。
* `this.desktop` 就一定有 this.$n()、也就是 dom element 已經長好了


### zk.Object 相關 ###
	foo = zk.$extends(zk.Object, {
		//instance method/field
	},{
		//static method/field
		fooValue: 0;
	}

在 `foo` 當中可以用 `this.$class.fooValue` 存取


### Event 相關 ###
`new zk.Event(widget, name, data, opt)`：`data` 是回傳到 server side 的（不過 client 還是取得到 XD）


jsdoc
-----
步驟：
1. `./build jsdoc zk zul ../zkcml/zkex ../zkcml/zkmax;`
	* 會把相關的 .js 轉換成（假的） .java 檔
1. `./build doc jsdoc;`
	* 透過上一個步驟產生的（假的） .java 檔來吐出 javadoc


### 除錯 tip ###
* class 前頭（`foo.FooName = zk.$extends(zk.Object, {}`）前頭一定要有 javadoc comment block，
  不然步驟 1 就不會產生對應的 .java 檔、步驟 2 就會炸找不到 `FooName` 的錯誤。
* `$define` 當中 setter 的 `@param` 一定要記得給 data type，
  不然會被當成 getter 然後炸重複宣告的 error
* `@return` 打成 `@returns` 居然 Eclipse 跟 `./build jsdoc` 都照吃，
   然後在步驟 2 會把多的那個 s 當成 return 值的 data type... （WTF）

   
swipe
-----
主要邏輯在 ZK 的 zswipe.js（當然 widget.js 也有）

widget 的 `_swipe` 通常在 `bindSwipe_()` 當中設定，`_swipe` 會決定 `doSwipe_(evnt)` 的 `evnt.opts.dir` 的值。
簡單地說，就是決定到底是上下左右哪一個（`zswipe.js` 的 `_swipeEnd()`），而不是決定 swipe event 發不發。
也就是說，無論 `_swipe` 怎麼設定，`doSwipe_()` 還是會呼叫到。


ZUL、EL
======
* `${zk}` 的來源（應該）是 `Servlets.java` 的 `browserInfo()` 
  偷偷塞 `request.setAttribute("zk", zk = new HashMap<String, Object>(4));` 的結果


Taglib
------
* http://www.zkoss.org/dsp/web/core
	* TLD 檔：zweb/src/archive/web/WEB-INF/tld/web/core.dsp.tld


Tablet
======
* 複寫 js：`/src/archive/web/js/zk.wpd` 加上對應的 `<script>` 內容。
* 複寫 css.dsp:`/src/archive/web/zkmax/css/tablet.css.dsp`
	* 一兩行就解決可以直接塞在 `tablet.css.dsp` 裡頭
	* 反之則是加 `<c:include page="tablet/foo.css.dsp"/>`，然後在 `foo.css.dsp` 裡頭解決

在 `widget-touch.js` 把 `widget` 的 `setMold()` 覆寫成 `zk.$void`，
所以 component 必須要再覆寫一次 `setMold()` 才能讓 developer 設定 mold。


網頁 tools
==========

* 用關鍵字 search ZK 網站（provide by Jumper）

		javascript:void((function(){var loc=self.location.href,   txt = window.getSelection().toString(),  key = txt.length ? txt : window.prompt("Enter");if(key){ window.open("http://www.zkoss.org/doc/searchresult.jsp?cx=008321236477929467003%3A63kdpeqkkvw&cof=FORID%3A11&q="+key+"&sa=");}})());
		
* 輸入 issue 編號直接跳到 tracker 頁面

		javascript:void( (function(){ var key = window.prompt("ZK issue 直接打編號即可，其他請包含分類縮寫：").toUpperCase(); key = key.indexOf("-")==-1 ? "ZK-" + key : key; if(key){ window.open("http://tracker.zkoss.org/browse/"+key);}} )() );