### Reference ###
* [Javadoc home]: http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/
	* standard doclet 的參數詳解，要點「Tools」底下的「Javadoc Tool Reference Page」，理論上應該任一均可...
* [Doclet API]: http://docs.oracle.com/javase/6/docs/jdk/api/javadoc/doclet/index.html

## Doclet ##

### Eclipse 下的 Doclet 開發方式 ###
1. 寫好 `fooPackage.FooDoclet`，假設 compile 後的 `FooDoclet.class` 放在 `Z:\fooProject\bin\fooPackage\` 底下
1. 對某個 project 作 `Export→JavaDoc` （有快速鍵可以設定）
	1. 改選 `Use custom doclet`
	1. `Doclet name` 輸入 `fooPackage.FooDoclet`
	1. `Doclet class path` 輸入 `Z:\fooProject\bin` （似乎只能指絕對路徑）
	1. 下一個畫面也不知道要幹麼，就直接按 Finish 就好 XD
