> 這裡是針對__使用 ZK 的人__的相關 tip。

ZUL
===
* `&#36;`→$

EL
--
* 取得 browser 資訊：`${zk}`

Tablet
------
下面這段程式碼會產生異常

	<window contentStyle="overflow:auto" height="500px">
		<listbox vflex="1">
			<!-- 後略 -->

以運作邏輯來說，`Window` 的 iScroll 會以 `Listbox` 的高度來判斷，而這時 `Listbox` 的高度是根據內容數量而決定。
等到 `Listbox` 要作 `vflex="1"` 時，會回頭去要 `Window` 的高度。
所以結果就是 `Listbox` 完全沒辦法滾，因為無論 `Listbox` 還是 `Window` 的大小都跟 `Listbox` 原本該有的高度一樣高。

結論就是 `Listbox` 拿掉 `vflex="1"`、或是給定高度，亦或是 `Window` 的 `contentStyle="overflow:auto"`。
其他 use case 基本上都跟 vflex="1" 的原意衝突（有空待補）

MVVM
----
有一個 `Listbox` 要設定 `selectedIndex`，但是有可能是空的，所以得要在空的時候設定成 -1，那麼寫成這樣是不行的：
	
	<listbox selectedIndex='@load(vm.listEmpty ? vm.emptyIndex : vm.selectedIndex) @save(vm.selectedIndex)' >

必須得要寫成

	<listbox model="@load(vm.list)" emptyMessage="Is Empty!"
	 selectedIndex='@load(vm.listEmpty ? vm.emptyIndex : vm.selectedIndex) @save(vm.selectedIndex)' >

還蠻合情合理的。另外還有兩個哏：

* 奇怪，明明 `vm.list` 是個 `java.util.List` 有 `isEmpty()`，但是卻不能用 `vm.list.empty`
* 寫成 `@load(vm.listEmpty ? 1 : vm.selectedIndex)` 會炸「不能從 long 轉成 integer」的錯誤訊息

Component
---------
### Listbox ###
* 如果 item 很多，則還沒有顯示到的 item 內容會是空白，等捲到才會實際 render。
	* 這跟 ROD 無關，啟動 ROD 是連 item 外頭的 `<tr><td>` 都不會 render。

### Tabbox ###
* `tabscroll` 屬性不是定義 `tabpanel` 可不可以滾（`tabpanel`：關我屁事），
	而是 `tabs` 裡頭的 `tab` 太多的時候，到底是全部排出來，還是依寬度只顯示某幾個。（WTF......）

Tag Library
-----------
在 zweb 這個 project 當中...... 

Common Java
===========
### Utility ###
* `Path.getComponent()` 可以用 id space 的路徑方式取得指定的 component。
* `Objects.equals()` 比對值是否相等，減少判斷 null 以及一些其他小細節的困擾。
* `Clients` 有許多 method 可以從 server side 要求 client side 作一些事情。包含偉大萬惡的 `Clients.evalJAvaScript()`（真xx的幹得好啊 XD）

### Tabelt ###
* 在 .java 當中判斷是不是 tablet。第一行的條件必須滿足，正常情況（VM、Model）應該都取得到。

	ServletRequest request = ServletFns.getCurrentRequest();
	mobile = Servlets.getBrowser(request, "mobile") != null;

ZK Studio / Maven
-----------------

Maven
======
* SNAPSHOT：還沒 release 的 version 加上 `-SNAPSHOT`，例如「52.87.0-SNAPSHOT」
* Freshly：到 [download freshly] 看版號，如果是 `ZK-52.87.0-FL-2099-12-31`，就改成 `ZK.52.87.0.FL.2099.12.31`。（未必每天都會出 FL）

[download freshly]: http://www.zkoss.org/download/freshly/