@echo off
cls
title �����������ļ�--���
echo 1.���ڱ���...
javac -d . ReName.java
echo 2.��������...
java com.message.utils.ReName
echo 3������ɾ��class�ļ�
rd /s /q com
pause
