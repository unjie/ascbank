@echo off
::��ȡ���� ����ʽ����Ϊ��20110820
set datevar=%date:~0,4%%date:~5,2%%date:~8,2%
::��ȡʱ���е�Сʱ ����ʽ����Ϊ��24Сʱ��
set timevar=%time:~0,2%
if /i %timevar% LSS 10 (
set timevar=0%time:~1,1%
)
::��ȡʱ���еķ֡��� ����ʽ����Ϊ��3220 ����ʾ 32��20��
set timevar=%timevar%%time:~3,2%%time:~6,2%
@echo ��ǰʱ�䣺%datevar%%timevar%
pause