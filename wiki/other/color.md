### 暗底白字、亮底黑字邏輯 ###
取整數值作下列運算

	double a = 1 - ( 0.299 * color.R + 0.587 * color.G + 0.114 * color.B) / 255

`a` < 0.5 就是白底、反之就是黑底。這公式不知道哪來的、為什麼不用 `(R+G+B)/3/255` 就算了如此之類這樣那般。

__於是只好紀錄下來了 Orz__

reference:[stackoverflow][stackoverflow 1]

[stackoverflow 1]: http://stackoverflow.com/questions/1855884/determine-font-color-based-on-background-color