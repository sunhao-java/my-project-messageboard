@echo off
cls
title 批量重命名文件--孙昊
echo 1.正在编译...
javac -d . ReName.java
echo 2.启动程序...
java com.message.utils.ReName
echo 3、正在删除class文件
rd /s /q com
pause
