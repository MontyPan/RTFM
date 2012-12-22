Upload.js
=========
zul.Upload 是一個輔助的 class，不是實際的 widget。
在 `initContent()` 的時候會偷偷塞一個有 `<input type="file" />` 的 form 蓋在有設定 `upload="true"` 的 button 上。
所以完全不是 button 本身觸發選 file 的 dialog。