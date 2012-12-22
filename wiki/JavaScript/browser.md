>	主要紀錄瀏覽器之間的各種差異、詭異、特異的現象 Orz

CSS
===
文字大小
--------
在 iPhone(4s) 上，發現一個文字會莫名變大的問題，詳情參閱[這篇文章](../../blog/TextSizeIssueOnIphone.md)

focus
-----
在 Android 4.1 的 native browser，如果（JS）用

	//colorbox-touch.js 遇到的問題
	foo.style['WebkitTransition'] = '-webkit-transform 0.3s ease-in-out';
	foo.style['WebkitTransform'] = 'translate(0, -100px)';
	//基本上 foo 要由下往上浮出
	zk(foo.$n("orz")).focus();
	
就會導致 browser 往上捲動、且 foo 會多跳一段距離。拿掉 focus 就正常。

span
----
如果 `<span>` 裡頭塞其他 html 的 tag，在 Chrome（21.0.1180.79）中 offsetHeight 會是 0，
必須設定 `display:block` 或是 `display:inline-block` 才會有高度。
其他瀏覽器（Firefox、Opera、Safari、IE9 版號懶得查）沒有這個問題，不過取到的大小也不盡相同就是了 \[死]

placeholder
-----------
* Safari
	* 5.1.7：`<input type="number">` 不支援
* iPad
	* `<input type="number">` 輸入「-」不會消失
* Android（ASUS）
	* 不支援（`<input type="number">`，其他不確定）
