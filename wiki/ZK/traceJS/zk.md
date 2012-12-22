widget.js
=========
6.5.0

flex 機制
---------
到 `widget.bind()` 的時候才會處理，會註冊 `onSize`、`beforeSize` 到 zFlex 的對應 function 下。

`widget._nvflex` 在設定 vflex 的時候會有值

### 問題區 ###
* vflex 跟 hflex 的 setter，邏輯不盡相同？

		if (this.desktop) //$define[vflex]
 		if (_binds[this.uuid] == this)  //setHflex_
		
  該不會 `this.desktop` 跟 `_binds[this.uuid]==this` 其實等意吧？ \[死]

* `_fixMinVflex()` 是設定 `hflex` 的、`_fixMinHflex()` 是設定 vflex 的，這不是很怪嗎？

* `onSize()` 為什麼要呼叫 zFlex.fixFlex() 而不改成直接呼叫 function（不是會比較快嗎？），
	又為什麼不直接把 fixFlex 的內容寫在 onSize 裡頭？

### `vflex="1"` 流程 ###


### `hflex="min"` 流程 ###
`this._nhflex` 的值取決於 `hflex` 的設定值（`setHFlex_`）：

* true:1
* min：-65500
* 負數：0

接著作 `_listenFlex(this)`。`_listenFlex()` 只會作一次，
主要是註冊 onSize（zFlex.onSize）、beforeSize（zFlex.beforeSize），
因為 hflex="min" 會加跑 `listenOnFitSize_()`（反之則跑 `unlistenOnFitSize_()`）。

* this._nhflex = -65500
* 註冊 listen
	* 在 `_listenFlex()`，onSzie : zFlex.onSize
	* 在 `_listenFlex()`，beforeSize : zFlex.beforeSize
	* 在 `widget.listenOnFitSize()`，onFitSize : zFlex.onFitSize

以實驗結果來看，會先執行

1. beforeSize
1. onFitSize→_fixMinHflex()→_fixMinVflex()
	* 理論上跑完這個 method，大小會調整好
1. onSize→fixFlex

#### 備註 ####
`setWidth()` 會先判斷 `!this._nhflex`，也就是說沒有設定 hflex 才會讓他設定 width，
`setHeight()` 邏輯雷同。

______________________________________________________________________________

### $n() ###
與 `jq(subId)[0]` 等價，只是它會把回傳值存一份在 `this._subnodes[subId]` 當中。
cleanCache() 可以清空。

### setTopmost() ###
我如果是要 floating，那麼我的小孩們如果也是 floating 的話，要比我更上面。

flex.js
=======
### zFlex.beforeSize() ###
1. 有給 cleanup 值則清空 `widget._hflexsz` 跟 `widget._vflexsz`。
1. 在 `!zk.mounting` 以及 `widget.isRealVisible()` 的前提下，
	\_h/vflex 不是 min 就會呼叫 `widget.resetSize_()`

### zFlex.fixFlex() ###
1. 如果 widget 沒有 `\_h/vflex` 的值、或是值為 `min` 而且也有 `_h/vflexsz`，則跳出。
1. `widget.parent.beforeChildrenFlex_(widget)` 回傳值必須為 true，不然就跳出。。
	`widget.beforeChildrenFlex_()` 預設回傳 true。
1. 如果 `widget._flexFixed` 是 true 或是 `widget._nvflex` 跟 `widget._nhflex` 都是 false/null，
	會刪除 `widget._flexFixed` 然後跳出。
1. 設定 `widget._flexFixed` 為 true
1. 計算 `hgh` _（wdh 跳過）_ 的值，一開始是 parent 的值
	1. 如果 parent 有 scrollbar，則減去 scrollbar 佔據的大小
	1. 從 parent 的第一個小孩開始找，找到第一個不是 text node 的 child，跑下面的迴圈
		1. 如果是 text node，則 pretxt 設為 true _（為什麼一開始要設成 false？）_，然後換鄰居續跑
		1. 如果 child 是有設 flex 的 zk widget（就會是自己） _（但是那個 cwgt !== wgt 又要幹麼？）_    
			反之如果 `child（widget）.isExcludedVflex_()` 不為 true（不是 absolute 定位），
			hgh 就會減去 child 的（zk(child)） offsetHeight() 以及 sumStyles("tb", jq.margins)
		
> trace 到 `hgh -= _getTextHeight(zkc, zkp, zkpOffset)`;
		
### _fixMinVflex() ###
如果有給 min
（也就是 `fixMinFlex()` 當中 `wgt.beforeMinFlex_()` 有回傳值、可是 `widget.beforeMinFlex_()` 並沒有寫任何東西，
也就是說是實際計算大小的 widget 必須覆寫這個 method），那麼中間那一大串（第二層 if-else）就會跳過，直接進入最後一段。

最後一段的重點在於 `wgt.setFlexSize_()`。
按照原本 widget.js 的寫法，真正影響 widget 寬度的，其實是 `widget.setFlexSize_()`。