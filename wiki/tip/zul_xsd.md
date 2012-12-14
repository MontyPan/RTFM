### 在 ZK Studio 測試 zul.xsd ###
* Preferences→ZK→ZUL Editor：把 Use plugin build-in zul.xsd 取消
* Preferences→XML→XML Category，增加一個 User Specified Entries
	* Location：`zul/src/archive/metainfo/xml/zul.xsd`（File System 也可以啦）
	* Key type：選 `Schema location`
	* Key：`http://www.zkoss.org/2005/zul/zul.xsd`

[ref: ZK Studio 的 ZUL Editor setting](http://books.zkoss.org/wiki/ZK_Studio_Essentials/Preferences_of_ZK_Studio/Global_Preferences#ZUL_Editor)

### 傳統 XML 測試 zul.xsd ###
* 將寫好的 `zul.xsd` 放在某個執行中的 web 目錄下，假設可以用 `http://localhost:8080/foo/zul.xsd` 連得到。
* 在有 code assist 的 XML editor（例如 Eclipse）開一個 `foo.xml` 檔案，檔案內容是：

		<zk xmlns="http://www.zkoss.org/2005/zul" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
		 xsi:schemaLocation="http://www.zkoss.org/2005/zul http://localhost:8080/foo/zul.xsd ">
		</zk>

  理論上就可以讀到 zul.xsd 當中定義的語法。

如果在 Eclipse 下，有下列建議：
* 到 Preferences→Network Connections 下，把 Disable caching 給開啟。
* 每次修改 `zul.xsd` 之後，要關掉 `foo.xml`、重新開啟後（理論上）才會 load 到新的 `zul.xsd`。

[ref: 沒有 ZK Studio 的設定方式](http://books.zkoss.org/wiki/ZK_Installation_Guide/Setting_up_IDE/Eclipse_without_ZK_Studio)

### 缺少這些東西 ###
* toolbarbutton 跟 button 應該要一樣（toolbarbutton 還要比較多一點），但是好像沒有

### ZUL 人類補完計畫 ###
* 在 `<xs:attribute>` 當中加 

		<xs:annotation>
			<xs:documentation>DESCRIPTION</xs:documentation>
		</xs:annotation>

	就會在 IDE 當中出現該 attribute 的 description
* `<xs:annotation>` 必須加在（如果有） `<xs:simpleType>` 的前面
* `<xs:element>`
	* default：就 default 值
	* fixed：只能是這個值
