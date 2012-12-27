### Eclipse 中 Maven Project 的必要條件 ###
* project 根目錄下要有 `pom.xml`
* `.classpath` 要有

			<classpathentry kind="con" path="org.eclipse.m2e.MAVEN2_CLASSPATH_CONTAINER"/>
			<classpathentry kind="con" path="org.maven.ide.eclipse.MAVEN2_CLASSPATH_CONTAINER">
				<attributes>
					<attribute name="org.eclipse.jst.component.dependency" value="/WEB-INF/lib"/>
				</attributes>
			</classpathentry>

* `.project` 要有
	* `<buildSpec>` 區段
	
			<buildCommand>
				<name>org.maven.ide.eclipse.maven2Builder</name>
				<arguments>
				</arguments>
			</buildCommand>
			<buildCommand>
				<name>org.eclipse.m2e.core.maven2Builder</name>
				<arguments>
				</arguments>
			</buildCommand>	
			
	* `<natures>` 區段
	
			<nature>org.maven.ide.eclipse.maven2Nature</nature>
			<nature>org.eclipse.m2e.core.maven2Nature</nature>	

如果要跑 Web，在 `src` 下增加 `archive`（應該只是慣例，可以是別的），裡頭按照 JSP 慣例塞 `WEB-INF`、`web.xml` 即可。

如果要跑 ZK，只要 `pom.xml` 有 ZK 的 repository 跟 dependency，就可以了。