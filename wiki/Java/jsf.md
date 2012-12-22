> # 不務正業之 JSF 心得 #

* JSF：毫無反應，就是一組規格。
	* [JSF Javadoc]→其實不太懂這個跟 Mojarra 的差別？
	* [RenderKit Javadoc]
* Mojarra：根據 JSF 所實做出來的 web 作品
	* [Javadoc][Mojarra Javadoc]
* PrimeFaces：毫無反應，就是一沱 base on Mojarra 的 Component 集合（還有一些附帶產品）

[JSF Javadoc]: http://docs.oracle.com/javaee/6/javaserverfaces/2.1/docs/vdldocs/facelets/
[RenderKit Javadoc]: http://docs.oracle.com/javaee/6/javaserverfaces/2.1/docs/renderkitdocs/
[Mojarra Javadoc]: http://javaserverfaces.java.net/users.html

### Component Attribute ###
* rendered→visible (ZK)
* style→style (ZK)
* styleClass→sclass (ZK)

### EL 保留字 ###
* [EL 保留字列表][EL Reserved Word]
* cc：composite component 的保留字

[EL Reserved Word]: http://docs.oracle.com/javaee/6/tutorial/doc/bnail.html

### Composite Component ###
樣本檔案必須放在 resources 底下，如果是 `resources/foo` 那麼套用的頁面（官方名詞：using page）的 xmlns 要宣告成

	<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:em="http://java.sun.com/jsf/composite/foo/">

### 雜項 ###
* 標準來說 `<h:form>` 的 action（這樣講有點怪）改變 or 不改變 url 的方式

		<h:form>
			<h:commandButton action="foo?faces-redirect=true" value="網址改成 foo" />
			<h:commandButton action="foo" value="網址不變" />
		</h:form>

* `<resource-bundle>` 是以 classpath 為基礎來找，跟 resources（也叫 library） 不一樣 （WTF）
	* faces-config.xml 的 
	
			<resource-bundle>
				<base-name>fooPackage.fooName</base-name>
				<var>fooVar</var>
			</resource-bundle>
	
	如果只是要一頁面使用就改成 `<f:loadBundle basename="fooPackage.fooName" var="fooVar"/>`